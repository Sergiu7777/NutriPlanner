package com.solutionscrafted.nutriplanner.controller;

import com.solutionscrafted.nutriplanner.dto.ClientDto;
import com.solutionscrafted.nutriplanner.service.ClientService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

  private final ClientService clientService;

  @GetMapping
  public ResponseEntity<List<ClientDto>> getAllClients() {
    return ResponseEntity.ok(clientService.getClients());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClientDto> getClient(@PathVariable Long id) {
    return ResponseEntity.ok(clientService.getClientById(id));
  }

  @PostMapping
  public ResponseEntity<ClientDto> addClient(@RequestBody ClientDto request) {
    var client = clientService.createClient(request);
    return ResponseEntity.created(URI.create(String.format("/clients/%s", client.id())))
        .body(client);
  }
}
