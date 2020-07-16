package cz.mira.myapps.mydebt.service.debt;

import cz.mira.myapps.mydebt.model.dto.DebtDTO;
import cz.mira.myapps.mydebt.model.dto.PaymentDTO;

import java.util.List;

public interface DebtService {

    List<DebtDTO> getAllDebts();

    DebtDTO getDebt(Long debtId);

    DebtDTO createDebt(DebtDTO debt);

    boolean addPayment(Long debtId, PaymentDTO paymentDTO);

    List<PaymentDTO> getPaymentsForDebt(Long debtId);
}
