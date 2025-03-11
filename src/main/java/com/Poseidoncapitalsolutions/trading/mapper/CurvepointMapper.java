package com.poseidoncapitalsolutions.trading.mapper;

import org.mapstruct.Mapper;

import com.poseidoncapitalsolutions.trading.dto.CurvePointDTO;
import com.poseidoncapitalsolutions.trading.model.CurvePoint;

@Mapper
public interface CurvepointMapper {

    public CurvePointDTO toDto(CurvePoint bidLists);

    public CurvePoint toEntity(CurvePointDTO bidDTO);
}
