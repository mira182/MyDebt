package cz.mira.myapps.mydebt.controller;

import cz.mira.myapps.mydebt.model.dto.DebtDTO;
import cz.mira.myapps.mydebt.model.dto.PaymentDTO;
import cz.mira.myapps.mydebt.service.debt.DebtService;
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
        return debtService.getAllDebts();
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
    public DebtDTO createDebt(@RequestBody DebtDTO debtDTO) {
        log.info("Incoming DTO: {}", debtDTO);
        return debtService.createDebt(debtDTO);
    }

    @PostMapping("/{debtId}/addPayment")
    public boolean addPaymentForDebt(@PathVariable Long debtId, @RequestBody PaymentDTO paymentDTO) {
        return debtService.addPayment(debtId, paymentDTO);
    }

}
