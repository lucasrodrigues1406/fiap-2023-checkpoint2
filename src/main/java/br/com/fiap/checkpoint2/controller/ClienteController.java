package br.com.fiap.checkpoint2.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.checkpoint2.dto.cliente.ClienteRequestCreate;
import br.com.fiap.checkpoint2.dto.cliente.ClienteRequestUpdate;
import br.com.fiap.checkpoint2.dto.cliente.SearchedCliente;
import br.com.fiap.checkpoint2.model.Cliente;
import br.com.fiap.checkpoint2.repository.ClienteRepository;
import br.com.fiap.checkpoint2.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController  {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping
	public ResponseEntity<List<SearchedCliente>> listAll(){
		List<SearchedCliente> result = clienteService.list()
				.stream().map(SearchedCliente::toDto)
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(result);
	}
	
	@PostMapping
	public ResponseEntity<Cliente> create(@RequestBody ClienteRequestCreate dto) {
		Cliente cliente = new Cliente();
		cliente.setNome(dto.getNome());
		cliente.setCep(dto.getCep());
		cliente.setCpf(dto.getCpf());
		
		Cliente result = clienteService.save(cliente);

		return ResponseEntity
				.status(HttpStatus.CREATED)
		        .body(result);
	}
	
	@PutMapping
	public ResponseEntity<Cliente> update(@RequestBody ClienteRequestUpdate dto) {
		boolean exists = clienteRepository.existsById(dto.getCodigo_cliente());
		
		if (!exists) {
			return ResponseEntity.notFound().build();
		}
		
		Cliente cliente = new Cliente();
		
		cliente.setCodigo_cliente(dto.getCodigo_cliente());
		cliente.setNome(dto.getNome());
		cliente.setCep(dto.getCep());
		cliente.setCpf(dto.getCep());
		
		Cliente result = clienteService.save(cliente);
		
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
		        .body(result);
	}
	
	@DeleteMapping(value = "{Codigo_cliente}")
	public ResponseEntity<String> delete(@PathVariable Long Codigo_cliente) {
		boolean exists = clienteRepository.existsById(Codigo_cliente);
		
		if (!exists) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("id não encontrado " + Codigo_cliente);
						
		}	
		
		clienteRepository.deleteById(Codigo_cliente);
		
		return ResponseEntity.accepted().build();
	}
	
}
