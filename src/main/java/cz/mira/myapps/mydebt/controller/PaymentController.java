package cz.mira.myapps.mydebt.controller;

import cz.mira.myapps.mydebt.model.dto.PaymentDTO;
import cz.mira.myapps.mydebt.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public List<PaymentDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @PostMapping("/{paymentId}")
    public PaymentDTO updatePayment(@PathVariable long paymentId, @RequestBody PaymentDTO paymentDTO) {
        return paymentService.updatePayment(paymentId, paymentDTO);
    }
}
