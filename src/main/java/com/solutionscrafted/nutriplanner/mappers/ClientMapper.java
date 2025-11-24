package com.solutionscrafted.nutriplanner.mappers;

import com.solutionscrafted.nutriplanner.dto.ClientDto;
import com.solutionscrafted.nutriplanner.entity.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto toPlanDto(Client client);

    Client toClient(ClientDto clientDto);

    List<ClientDto> toClientDtoList(List<Client> clients);
}
