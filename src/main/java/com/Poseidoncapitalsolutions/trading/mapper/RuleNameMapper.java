package com.poseidoncapitalsolutions.trading.mapper;

import org.mapstruct.Mapper;

import com.poseidoncapitalsolutions.trading.dto.RuleNameDTO;
import com.poseidoncapitalsolutions.trading.model.RuleName;

@Mapper(componentModel = "spring")
public interface RuleNameMapper {

    public RuleNameDTO toDto(RuleName bidLists);

    public RuleName toEntity(RuleNameDTO bidDTO);
}
