package cz.mira.myapps.mydebt.model.mapper;

import cz.mira.myapps.mydebt.model.dto.DebtDTO;
import cz.mira.myapps.mydebt.model.entity.Debt;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, uses = {PaymentMapper.class})
public interface DebtMapper {

    DebtDTO entityToDto(Debt debt);

    Debt dtoToEntity(DebtDTO debtDTO);
}
