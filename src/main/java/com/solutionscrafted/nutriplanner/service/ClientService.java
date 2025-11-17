package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.dto.ClientDto;
import com.solutionscrafted.nutriplanner.mappers.NutriMapper;
import com.solutionscrafted.nutriplanner.repository.ClientRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

  private final ClientRepository clientRepository;
  private final NutriMapper nutriMapper;

  public List<ClientDto> getClients() {
    return nutriMapper.toClientDtoList(clientRepository.findAll());
  }

  public ClientDto getClientById(int id) {
    return null;
  }

  public ClientDto createClient(ClientDto clientDto) {
    return null;
  }
}
