package cz.mira.myapps.mydebt.service.debt;

import cz.mira.myapps.mydebt.model.dto.DebtDTO;
import cz.mira.myapps.mydebt.model.dto.PaymentDTO;

import java.util.List;

public interface DebtService {

    List<DebtDTO> getAllDebts();

    DebtDTO getDebt(Long debtId);

    DebtDTO createDebt(DebtDTO debt);

    boolean deleteDebt(long debtId);

    boolean addPayment(Long debtId, PaymentDTO paymentDTO);

    boolean deletePayment(Long debtId, Long paymentId);

    List<PaymentDTO> getPaymentsForDebt(Long debtId);
}
