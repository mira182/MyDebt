package cz.mira.myapps.mydebt.repository;

import cz.mira.myapps.mydebt.model.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
}
