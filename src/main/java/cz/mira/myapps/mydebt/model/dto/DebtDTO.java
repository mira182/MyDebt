package cz.mira.myapps.mydebt.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DebtDTO {

    private Long id;

    private int debt;

    private LocalDate paymentsStartDate;
}
