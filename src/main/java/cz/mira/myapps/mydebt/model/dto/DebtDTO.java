package cz.mira.myapps.mydebt.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Positive(message = "Initial debt must be greater than 0")
    private int initialDebt;

    // Not validated: it is derived server-side on update, and can legitimately
    // go negative when a debt is overpaid.
    private int currentDebt;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Debt start date is required")
    private LocalDate debtStartDate;

    // Intentionally not annotated with @Valid: existing payments travel back with
    // an edited debt and must not be re-validated here.
    private List<PaymentDTO> payments;
}
