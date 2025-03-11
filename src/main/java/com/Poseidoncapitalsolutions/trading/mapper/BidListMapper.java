package com.poseidoncapitalsolutions.trading.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.poseidoncapitalsolutions.trading.dto.BidListDTO;
import com.poseidoncapitalsolutions.trading.model.BidList;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BidListMapper {

    public BidListDTO toDto(BidList bidLists);

    public BidList toEntity(BidListDTO bidDTO);
}