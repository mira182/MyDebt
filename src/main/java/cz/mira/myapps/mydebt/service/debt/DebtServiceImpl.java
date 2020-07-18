package cz.mira.myapps.mydebt.service.debt;

import cz.mira.myapps.mydebt.model.dto.DebtDTO;
import cz.mira.myapps.mydebt.model.dto.PaymentDTO;
import cz.mira.myapps.mydebt.model.entity.Debt;
import cz.mira.myapps.mydebt.model.mapper.DebtMapper;
import cz.mira.myapps.mydebt.model.mapper.PaymentMapper;
import cz.mira.myapps.mydebt.repository.DebtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DebtServiceImpl implements DebtService {

    private final DebtRepository debtRepository;

    private final DebtMapper debtMapper;

    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public List<DebtDTO> getAllDebts() {
        return debtRepository.findAll().stream()
                .map(debtMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DebtDTO getDebt(Long debtId) {
        return debtMapper.entityToDto(debtRepository.findById(debtId).orElseThrow(() -> new EntityNotFoundException("Debt with id: " + debtId + " was not found.")));
    }

    @Override
    @Transactional
    public DebtDTO createDebt(DebtDTO debtDto) {
        if (debtDto.getId() == null)
            return debtMapper.entityToDto(debtRepository.save(debtMapper.dtoToEntity(debtDto)));
        else
            return debtMapper.entityToDto(debtRepository.save(debtRepository.findById(debtDto.getId()).orElseGet(() -> debtMapper.dtoToEntity(debtDto))));
    }

    @Override
    @Transactional
    public boolean addPayment(Long debtId, PaymentDTO paymentDTO) {
        final Debt debt = debtRepository.findById(debtId).orElseThrow(() -> new EntityNotFoundException("Debt with id: " + debtId + " was not found."));
        debt.addPayment(paymentMapper.dtoToEntity(paymentDTO));
        debt.setCurrentDebt(debt.getCurrentDebt() - paymentDTO.getAmount());
        return true;
    }

    @Override
    public boolean deletePayment(Long debtId, Long paymentId) {
        final Debt debt = debtRepository.findById(debtId).orElseThrow(() -> new EntityNotFoundException("Debt with id: " + debtId + " was not found."));
        debt.removePayment(paymentId);
        return true;
    }

    @Override
    @Transactional
    public List<PaymentDTO> getPaymentsForDebt(Long debtId) {
        final Debt debt = debtRepository.findById(debtId).orElseThrow(() -> new EntityNotFoundException("Debt with id: " + debtId + " was not found."));
        return debt.getPayments().stream()
                .map(paymentMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
