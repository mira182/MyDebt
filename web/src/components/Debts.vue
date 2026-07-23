<template>
    <div>
        <!-- Rendered into the app bar, but kept here so it keeps this component's
             save handler. `defer` lets the target element exist first. -->
        <Teleport defer to="#app-bar-actions">
            <CreateDebtDialog v-on:save-debt="createDebt($event)"/>
        </Teleport>

        <div id="debts" v-if="debts != null && debts.length > 0">
            <!-- height must be set on v-tabs itself: it drives --v-tabs-height,
                 which each v-tab inherits. Styling the tab alone just overflows. -->
            <v-tabs v-model="tab" show-arrows hide-slider height="84">
                <v-tab v-for="debt in sortedDebts" :key="debt.id" :value="debt.id" class="debt-tab">
                    <span class="debt-tab__label">
                        <v-icon v-if="isPaidOff(debt)" size="x-small" color="success" class="mr-1">mdi-check-circle</v-icon>
                        {{ debt.title }}
                    </span>
                    <span class="debt-tab__amount">
                        {{ $formatPrice(paidAmount(debt)) }} / {{ $formatPrice(debt.initialDebt) }} Kč ({{ roundedPercentage(debt) }} %)
                    </span>
                </v-tab>
            </v-tabs>

            <v-window v-model="tab">
                <v-window-item v-for="debt in sortedDebts" :key="debt.id" :value="debt.id">
                    <v-card v-if="debt" class="pa-4">
                        <div class="summary-head">
                            <div class="summary-group">
                                <h3 class="summary-title">
                                    {{ $t('debts.summary') }}
                                    <span v-if="isPaidOff(debt)" class="paid-badge">
                                        <v-icon size="x-small" color="success">mdi-check</v-icon> {{ $t('debts.paidOff') }}
                                    </span>
                                </h3>
                                <div class="summary-line">
                                    <span>{{ $t('debts.debtAmount') }}: <strong>{{ $formatPrice(debt.initialDebt) }} Kč</strong></span>
                                    <span>{{ $t('debts.paid') }}: <strong>{{ $formatPrice(paidAmount(debt)) }} Kč</strong> ({{ roundedPercentage(debt) }} %)</span>
                                    <span :class="{ ok: isPaidOff(debt) }">{{ $t('debts.remaining') }}: <strong>{{ $formatPrice(remainingAmount(debt)) }} Kč</strong></span>
                                    <span>{{ $t('debts.created') }}: <strong>{{ debt.debtStartDate }}</strong></span>
                                </div>
                                <p v-if="debt.description" class="summary-description">{{ debt.description }}</p>
                            </div>

                            <div class="summary-actions">
                                <CreatePaymentDialog v-on:save-payment="addPayment(debt.id, $event)"/>
                                <EditDebtDialog v-bind:debt="debt" v-on:save-debt="updateDebt($event)"/>
                                <v-btn variant="text" size="small" color="error" @click="deleteDebt(debt)">
                                    <v-icon size="small" class="mr-1">mdi-delete</v-icon>{{ $t('debts.deleteDebt') }}
                                </v-btn>
                            </div>
                        </div>

                        <Payments class="mt-4" :debt-id="debt.id" :reload-key="reloadKeyFor(debt.id)"
                                  v-on:payment-deleted="refreshDebt(debt.id)"
                                  v-on:payment-edited="refreshDebt(debt.id)"/>
                    </v-card>
                </v-window-item>
            </v-window>
        </div>
    </div>
</template>

<script>
import Payments from "./Payments.vue";
import DebtRestService from "../services/DebtRestService";
import CreateDebtDialog from "@/components/dialogs/CreateDebtDialog.vue";
import CreatePaymentDialog from "@/components/dialogs/CreatePaymentDialog.vue";
import EditDebtDialog from "@/components/dialogs/EditDebtDialog.vue";

export default {
    name: 'Debts',
    components: {Payments, CreateDebtDialog, CreatePaymentDialog, EditDebtDialog},
    data() {
        return {
            tab: null,
            debts: null,
            // Bumped per debt id to tell the matching Payments table to reload
            // after a payment is added from the summary panel.
            reloadKeys: {}
        }
    },
    computed: {
        // Unpaid first (oldest created first), fully paid ones pushed to the end.
        // debtStartDate is an ISO date, so it sorts chronologically as a string.
        sortedDebts() {
            if (!this.debts) return [];
            return [...this.debts].sort((a, b) => {
                const paidDiff = (this.isPaidOff(a) ? 1 : 0) - (this.isPaidOff(b) ? 1 : 0);
                if (paidDiff !== 0) return paidDiff;
                return (a.debtStartDate || '').localeCompare(b.debtStartDate || '');
            });
        }
    },
    mounted() {
        this.getDebts();
    },
    methods: {
        refreshDebt(debtId) {
            DebtRestService.getDebt(debtId).then(result => {
                if (result && result.data) {
                    const idx = this.debts.findIndex(d => d.id === result.data.id);
                    if (idx !== -1) this.debts[idx] = result.data;
                }
            });
        },
        createDebt(debt) {
            DebtRestService.createDebt(debt).then(response => {
                if (response) {
                    this.getDebts();
                }
            }).catch(() => this.notifyError("Saving debt failed."));
        },
        updateDebt(debt) {
            DebtRestService.updateDebt(debt.id, debt).then(response => {
                if (response && response.data) {
                    const idx = this.debts.findIndex(d => d.id === response.data.id);
                    if (idx !== -1) this.debts[idx] = response.data;
                }
            }).catch(() => this.notifyError("Updating debt failed."));
        },
        getDebts() {
            DebtRestService.getDebts().then(response => {
                if (response.status === 200) {
                    this.debts = response.data;
                    if (this.debts.length && !this.debts.find(d => d.id === this.tab)) {
                        this.tab = this.sortedDebts[0].id;
                    }
                } else {
                    console.log("Error getting debts");
                    console.log("Response:", response);
                }
            });
        },
        addPayment(debtId, payment) {
            DebtRestService.createPayment(debtId, payment).then(response => {
                if (response) {
                    this.refreshDebt(debtId);
                    this.bumpReload(debtId);
                }
            }).catch(() => this.notifyError("Saving payment failed."));
        },
        deleteDebt(debt) {
            this.$confirm('Do you really want to delete debt?', {title: 'Warning'}).then(res => {
                if (res) {
                    DebtRestService.deleteDebt(debt.id).then(deleted => {
                        if (deleted) {
                            const idx = this.debts.findIndex(d => d.id === debt.id);
                            if (idx !== -1) this.debts.splice(idx, 1);
                            if (!this.debts.find(d => d.id === this.tab)) {
                                this.tab = this.debts.length ? this.sortedDebts[0].id : null;
                            }
                        }
                    }).catch(() => this.notifyError("Deleting debt failed."));
                }
            });
        },
        bumpReload(debtId) {
            this.reloadKeys[debtId] = (this.reloadKeys[debtId] || 0) + 1;
        },
        reloadKeyFor(debtId) {
            return this.reloadKeys[debtId] || 0;
        },
        isPaidOff(debt) {
            return debt.currentDebt <= 0;
        },
        paidAmount(debt) {
            return debt.initialDebt - debt.currentDebt;
        },
        remainingAmount(debt) {
            return Math.max(0, debt.currentDebt);
        },
        paidPercentage(debt) {
            if (!debt.initialDebt) return 0;
            const p = ((debt.initialDebt - debt.currentDebt) / debt.initialDebt) * 100;
            return Math.min(100, Math.max(0, p));
        },
        roundedPercentage(debt) {
            return Math.round(this.paidPercentage(debt));
        },
        notifyError(message) {
            this.$store.dispatch('setSnackbar', {show: true, color: 'error', message});
        }
    }
}
</script>

<style scoped>
.debt-tab {
    padding: 12px 20px 16px;
    text-transform: none;
    position: relative;
    border-radius: 8px 8px 0 0;
    opacity: 0.68;
    transition: background-color 0.2s ease, opacity 0.2s ease;
}

.debt-tab :deep(.v-btn__content) {
    height: 100%;
    justify-content: center;
    overflow: visible;
}

.debt-tab:hover {
    opacity: 1;
    background: rgba(var(--v-theme-on-surface), 0.06);
}

.debt-tab.v-tab--selected {
    opacity: 1;
}

.debt-tab.v-tab--selected::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 3px;
    background: rgb(var(--v-theme-primary));
}

.debt-tab.v-tab--selected .debt-tab__amount {
    opacity: 0.85;
}

.debt-tab :deep(.v-btn__content) {
    flex-direction: column;
    align-items: flex-start;
    gap: 2px;
}

.debt-tab__label {
    display: flex;
    align-items: center;
    font-weight: 600;
    font-size: 0.95rem;
    line-height: 1.2;
}

.debt-tab__amount {
    font-size: 0.78rem;
    opacity: 0.65;
}

/* Summary panel */
.summary-head {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    flex-wrap: wrap;
    gap: 16px;
}

.summary-title {
    display: flex;
    align-items: center;
    gap: 10px;
    font-size: 1.05rem;
    margin-bottom: 8px;
}

.paid-badge {
    display: inline-flex;
    align-items: center;
    gap: 4px;
    font-size: 0.8rem;
    font-weight: 600;
    color: #4caf50;
    background: rgba(76, 175, 80, 0.12);
    border-radius: 12px;
    padding: 2px 10px;
}

.summary-line {
    display: flex;
    flex-wrap: wrap;
    gap: 8px 24px;
    font-size: 0.95rem;
    opacity: 0.9;
}

.summary-line .ok strong {
    color: #4caf50;
}

.summary-description {
    margin-top: 10px;
    margin-bottom: 0;
    font-style: italic;
    opacity: 0.7;
}

.summary-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: 4px;
}
</style>
