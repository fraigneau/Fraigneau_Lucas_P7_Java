package com.poseidoncapitalsolutions.trading.mapper;

import org.mapstruct.Mapper;

import com.poseidoncapitalsolutions.trading.dto.BidListDTO;
import com.poseidoncapitalsolutions.trading.model.BidList;

@Mapper
public interface BidListMapper {

    public BidListDTO toDto(BidList bidLists);

    public BidList toEntity(BidListDTO bidDTO);
}