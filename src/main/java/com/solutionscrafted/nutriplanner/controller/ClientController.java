package com.solutionscrafted.nutriplanner.controller;

import com.solutionscrafted.nutriplanner.dto.ClientDto;
import com.solutionscrafted.nutriplanner.controller.requestbody.ClientRequestDto;
import com.solutionscrafted.nutriplanner.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDto>> getClients() {
        log.info("Get clients request /clients");

        return ResponseEntity.ok(clientService.getClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) {
        log.info("Get client by id request /clients/{}", id);

        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientRequestDto request) {
        log.info("Create client request: /clients/{}", request);

        var client = clientService.createClient(request);
        return ResponseEntity.created(URI.create(String.format("/clients/%s", client.id())))
                .body(client);
    }
}
