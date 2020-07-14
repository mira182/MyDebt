package cz.mira.myapps.mydebt.controller;

import cz.mira.myapps.mydebt.model.dto.DebtDTO;
import cz.mira.myapps.mydebt.service.debt.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/debts")
@RequiredArgsConstructor
public class DebtController {

    private final DebtService debtService;

    @GetMapping
    public DebtDTO getDebt() {
        return debtService.getDebt();
    }

}
