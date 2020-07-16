package cz.mira.myapps.mydebt.model.mapper;

import cz.mira.myapps.mydebt.model.dto.PaymentDTO;
import cz.mira.myapps.mydebt.model.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDTO entityToDto(Payment payment);

    Payment dtoToEntity(PaymentDTO paymentDTO);
}
