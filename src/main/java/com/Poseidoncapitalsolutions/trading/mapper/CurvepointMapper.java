package com.poseidoncapitalsolutions.trading.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.poseidoncapitalsolutions.trading.dto.CurvePointDTO;
import com.poseidoncapitalsolutions.trading.model.CurvePoint;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CurvepointMapper {

    public CurvePointDTO toDto(CurvePoint bidLists);

    public CurvePoint toEntity(CurvePointDTO bidDTO);
}
