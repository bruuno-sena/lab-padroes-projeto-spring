package one.digitalinnovation.gof.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.Pet;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.model.PetRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.PetService;
import one.digitalinnovation.gof.service.ViaCepService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 *
 * @author falvojr
 */
@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ViaCepService viaCepService;

	@Autowired
	private PetRepository petRepository;

	@Autowired
	private PetService petService;

	@Override
	public Iterable<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	@Override
	public void inserir(Cliente cliente) {
		salvarClienteComCep(cliente);
		salvarClienteComPets(cliente);
	}

	@Override
	public void atualizar(Long id, Cliente cliente) {
		if (!clienteRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
		}
		cliente.setId(id);
		salvarClienteComCep(cliente);
	}

	@Override
	public void deletar(Long id) {
		if (!clienteRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
		}
		clienteRepository.deleteById(id);
	}

    @Override
    public List<Pet> buscarPetsPorCliente(Long clienteId) {
    	Cliente cliente = buscarPorId(clienteId);
    	if (cliente == null) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
    	}
    	return cliente.getPets();
    }

	@Override
	public Pet adicionarPetAoCliente(Long clienteId, String petId) {
		Cliente cliente = buscarPorId(clienteId);
		if (cliente == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
		}

		Pet pet = new Pet();
		pet.setNome("Novo Pet");
		pet.setTipo("Dog");
		pet.setBreed(petId);

		pet.setCliente(cliente);
		petRepository.save(pet);

		cliente.getPets().add(pet);
		clienteRepository.save(cliente);

		return pet;
	}

	private void salvarClienteComPets(Cliente cliente) {
		// 1. Salvar o cliente e seu endereço
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep)
				.orElseGet(() -> {
					Endereco novoEndereco = viaCepService.consultarCep(cep);
					enderecoRepository.save(novoEndereco);
					return novoEndereco;
				});
		cliente.setEndereco(endereco);

		// Salvar o cliente inicialmente, para garantir que o ID esteja disponível
		clienteRepository.save(cliente);

		// 2. Associa e salva todos os pets relacionados ao cliente
		if (cliente.getPets() != null) {
			for (Pet pet : cliente.getPets()) {
				pet.setCliente(cliente); // Associa o cliente ao pet
				petRepository.save(pet); // Salva o pet
			}
		}

		// Atualiza o cliente com a lista de pets
		clienteRepository.save(cliente);
	}


	private void salvarClienteComCep(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep)
				.orElseGet(() -> {
					Endereco novoEndereco = viaCepService.consultarCep(cep);
					enderecoRepository.save(novoEndereco);
					return novoEndereco;
				});
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
	}

}
