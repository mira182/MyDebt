package cz.mira.myapps.mydebt.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int initialDebt;

    private int currentDebt;

    private String title;

    private String description;

    private LocalDate debtStartDate;

    @OneToMany(mappedBy = "debt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    public void addPayment(Payment payment) {
        payments.add(payment);
        payment.setDebt(this);
    }

    public void removePayment(Payment payment) {
        payments.remove(payment);
        payment.setDebt(null);
    }
}
