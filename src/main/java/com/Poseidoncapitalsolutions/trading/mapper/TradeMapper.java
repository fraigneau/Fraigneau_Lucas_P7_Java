package com.poseidoncapitalsolutions.trading.mapper;

import org.mapstruct.Mapper;

import com.poseidoncapitalsolutions.trading.dto.TradeDTO;
import com.poseidoncapitalsolutions.trading.model.Trade;

@Mapper
public interface TradeMapper {

    public TradeDTO toDto(Trade bidLists);

    public Trade toEntity(TradeDTO bidDTO);
}
