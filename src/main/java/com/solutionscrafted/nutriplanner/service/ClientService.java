package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.controller.requestbody.ClientRequestDto;
import com.solutionscrafted.nutriplanner.dto.ClientDto;
import com.solutionscrafted.nutriplanner.entity.Client;
import com.solutionscrafted.nutriplanner.mappers.ClientMapper;
import com.solutionscrafted.nutriplanner.repository.ClientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper mapper;

    public List<ClientDto> getClients() {

        return mapper.toClientDtoList(clientRepository.findAll());
    }

    public ClientDto getClientById(Long id) {

        var client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        return mapper.toClientDto(client);
    }

    public ClientDto createClient(ClientRequestDto requestDto) {

        Client client = mapper.toClient(requestDto);
        return mapper.toClientDto(clientRepository.save(client));
    }

    public ClientDto updateClient(Long id, @Valid ClientRequestDto requestDto) {
        var client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found for id: " + id));

        client.setName(requestDto.name());
        client.setAge(requestDto.age());
        client.setWeight(requestDto.weight());
        client.setHeight(requestDto.height());
        client.setGoal(requestDto.goal());
        client.setDailyCalories(requestDto.dailyCalories());

        return mapper.toClientDto(clientRepository.save(client));
    }

    public void deleteClient(Long id) {
        log.info("Deleting client for clientId: {}.", id);

        clientRepository.findById(id).ifPresentOrElse(clientRepository::delete, () -> {
            log.warn("Client not found with id: {}", id);
        });
    }
}
