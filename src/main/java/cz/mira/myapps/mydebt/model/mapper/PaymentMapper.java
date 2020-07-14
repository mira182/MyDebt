package cz.mira.myapps.mydebt.model.mapper;

import cz.mira.myapps.mydebt.model.dto.PaymentDTO;
import cz.mira.myapps.mydebt.model.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    public PaymentDTO entityToDto(Payment payment);
}
