package cz.mira.myapps.mydebt.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class DebtDTO {

    private Long id;

    private int initialDebt;

    private int currentDebt;

    private String title;

    private String description;

    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate debtStartDate;

    private List<PaymentDTO> payments;
}
