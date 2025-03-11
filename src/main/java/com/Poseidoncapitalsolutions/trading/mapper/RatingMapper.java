package com.poseidoncapitalsolutions.trading.mapper;

import org.mapstruct.Mapper;

import com.poseidoncapitalsolutions.trading.dto.RatingDTO;
import com.poseidoncapitalsolutions.trading.model.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    public RatingDTO toDto(Rating bidLists);

    public Rating toEntity(RatingDTO bidDTO);
}
