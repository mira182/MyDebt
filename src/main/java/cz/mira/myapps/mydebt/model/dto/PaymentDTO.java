package cz.mira.myapps.mydebt.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentDTO {

    private Long id;

    @Positive(message = "Amount must be greater than 0")
    private int amount;

    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;
}
