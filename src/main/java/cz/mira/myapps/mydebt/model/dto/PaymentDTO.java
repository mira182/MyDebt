package cz.mira.myapps.mydebt.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentDTO {

    private Long id;

    private int amount;

    private LocalDate paymentDate;
}
