package com.poseidoncapitalsolutions.trading.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.poseidoncapitalsolutions.trading.dto.TradeDTO;
import com.poseidoncapitalsolutions.trading.model.Trade;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TradeMapper {

    public TradeDTO toDto(Trade bidLists);

    public Trade toEntity(TradeDTO bidDTO);
}
