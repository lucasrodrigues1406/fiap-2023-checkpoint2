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

import br.com.fiap.checkpoint2.dto.pedido.PedidoRequestCreate;
import br.com.fiap.checkpoint2.dto.pedido.PedidoRequestUpdate;
import br.com.fiap.checkpoint2.dto.pedido.SearchedPedido;
import br.com.fiap.checkpoint2.model.Pedido;
import br.com.fiap.checkpoint2.repository.ClienteRepository;
import br.com.fiap.checkpoint2.repository.PedidoRepository;
import br.com.fiap.checkpoint2.service.PedidoService;


@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping
	public ResponseEntity<List<SearchedPedido>> listAl(){
		List<SearchedPedido> result = pedidoService.list()
				.stream().map(SearchedPedido::toDto)
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(result);
	}
	
	@PostMapping
	public ResponseEntity<Pedido> create(@RequestBody PedidoRequestCreate dto)
	{
		Pedido pedido = new Pedido();
		pedido.setData_pedido(dto.getData_pedido());
		pedido.setCodigo_cliente(dto.getCodigo_cliente());
		boolean exists = clienteRepository.existsById(dto.getCodigo_cliente());
		
		if (!exists) {
			return ResponseEntity.notFound().build();
			}
		
		Pedido result = pedidoService.save(pedido);

		return ResponseEntity
				.status(HttpStatus.CREATED)
		        .body(result);
	}
	
	@PutMapping
	public ResponseEntity<Pedido> update(@RequestBody PedidoRequestUpdate dto){
		boolean existsPedido = pedidoRepository.existsById(dto.getNumero_pedido());
		
		if(!existsPedido) {
			return ResponseEntity.notFound().build();
		}
		boolean existsCodigoCliente = clienteRepository.existsById(dto.getCodigo_cliente());
		
		if (!existsCodigoCliente) {
			return ResponseEntity.notFound().build();
		}
		
		Pedido pedido = new Pedido();
		
		pedido.setCodigo_cliente(dto.getCodigo_cliente());
		
		
		pedido.setData_pedido(dto.getData_pedido());
		pedido.setNumero_pedido(dto.getNumero_pedido());
		
		Pedido result = pedidoService.save(pedido);
		
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
		        .body(result);
	}
	
	@DeleteMapping(value = "{Numero_pedido}")
	public ResponseEntity<String> delete(@PathVariable Long Numero_pedido) {
		boolean exists = pedidoRepository.existsById(Numero_pedido);

		if (!exists) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("id não encontrado " + Numero_pedido);					
		}
		
		pedidoRepository.deleteById(Numero_pedido);
		
		return ResponseEntity.accepted().build();
	}
	
}
