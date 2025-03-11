package com.poseidoncapitalsolutions.trading.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.poseidoncapitalsolutions.trading.dto.UserDTO;
import com.poseidoncapitalsolutions.trading.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    public UserDTO toDto(User bidLists);

    public User toEntity(UserDTO bidDTO);
}
