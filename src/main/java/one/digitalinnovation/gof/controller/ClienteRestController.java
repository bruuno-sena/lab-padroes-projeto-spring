package one.digitalinnovation.gof.controller;

import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.service.ClienteService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda
 * a complexidade de integrações (Banco de Dados H2 e API do ViaCEP) em uma
 * interface simples e coesa (API REST).
 * 
 * @author falvojr
 */
@RestController
@RequestMapping("clientes")
public class ClienteRestController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public ResponseEntity<Iterable<Cliente>> buscarTodos() {
		return ResponseEntity.ok(clienteService.buscarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(clienteService.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<Cliente> inserir(@RequestBody Cliente cliente) {
		clienteService.inserir(cliente);
		return ResponseEntity.ok(cliente);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
		clienteService.atualizar(id, cliente);
		return ResponseEntity.ok(cliente);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		clienteService.deletar(id);
		return ResponseEntity.ok().build();
	}
	@GetMapping("/{id}/pets")
	public ResponseEntity<List<Pet>> getPets(@PathVariable Long id) {
		List<Pet> pets = clienteService.buscarPetsPorCliente(id);
		return ResponseEntity.ok(pets);
	}

	@PostMapping("/{id}/pets/{petId}")
	public ResponseEntity<Pet> adicionarPet(@PathVariable Long id, @PathVariable String petId) {
		Pet novoPet = clienteService.adicionarPetAoCliente(id, petId);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoPet);
	}
}
