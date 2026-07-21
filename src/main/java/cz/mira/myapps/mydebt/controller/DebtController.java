package cz.mira.myapps.mydebt.controller;

import cz.mira.myapps.mydebt.model.dto.DebtDTO;
import cz.mira.myapps.mydebt.model.dto.PaymentDTO;
import cz.mira.myapps.mydebt.service.debt.DebtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/debts")
@RequiredArgsConstructor
public class DebtController {

    private final DebtService debtService;

    @GetMapping
    public List<DebtDTO> getAllDebts() {
        List<DebtDTO> debts = debtService.getAllDebts();
        log.debug("Returned debts in controller: {}", debts);
        return debts;
    }

    @GetMapping("/{debtId}")
    public DebtDTO getDebt(@PathVariable Long debtId) {
        return debtService.getDebt(debtId);
    }

    @GetMapping("/{debtId}/payments")
    public List<PaymentDTO> getPaymentsForDebt(@PathVariable Long debtId) {
        return debtService.getPaymentsForDebt(debtId);
    }

    @PostMapping
    public DebtDTO createDebt(@Valid @RequestBody DebtDTO debtDTO) {
        return debtService.createDebt(debtDTO);
    }

    @PutMapping("/{debtId}")
    public DebtDTO updateDebt(@PathVariable Long debtId, @Valid @RequestBody DebtDTO debtDTO) {
        return debtService.updateDebt(debtId, debtDTO);
    }

    @DeleteMapping("/{debtId}")
    public boolean deleteDebt(@PathVariable long debtId) {
        return debtService.deleteDebt(debtId);
    }

    @PostMapping("/{debtId}/addPayment")
    public boolean addPaymentForDebt(@PathVariable Long debtId, @Valid @RequestBody PaymentDTO paymentDTO) {
        return debtService.addPayment(debtId, paymentDTO);
    }

    @DeleteMapping("/{debtId}/payments/{paymentId}")
    public boolean deletePayment(@PathVariable Long debtId, @PathVariable Long paymentId) {
        return debtService.deletePayment(debtId, paymentId);
    }

}
