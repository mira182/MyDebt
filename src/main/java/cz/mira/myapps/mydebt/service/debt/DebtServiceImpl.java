package cz.mira.myapps.mydebt.service.debt;

import cz.mira.myapps.mydebt.model.dto.DebtDTO;
import cz.mira.myapps.mydebt.model.entity.Debt;
import cz.mira.myapps.mydebt.repository.DebtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DebtServiceImpl implements DebtService {

    private final DebtRepository debtRepository;

    @Override
    public DebtDTO getDebt() {
//        return debtRepository.getOne()
        return null;
    }
}
