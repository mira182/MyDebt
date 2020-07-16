package cz.mira.myapps.mydebt.service.payment;

import cz.mira.myapps.mydebt.model.dto.PaymentDTO;

import java.util.List;

public interface PaymentService {

    List<PaymentDTO> getAllPayments();
}
