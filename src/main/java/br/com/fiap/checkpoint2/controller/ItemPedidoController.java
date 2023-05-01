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

import br.com.fiap.checkpoint2.dto.itempedido.ItemPedidoRequestCreate;
import br.com.fiap.checkpoint2.dto.itempedido.ItemPedidoRequestUpdate;
import br.com.fiap.checkpoint2.dto.itempedido.SearchedItemPedido;
import br.com.fiap.checkpoint2.model.ItemPedido;
import br.com.fiap.checkpoint2.repository.ItemPedidoRepository;
import br.com.fiap.checkpoint2.repository.PedidoRepository;
import br.com.fiap.checkpoint2.repository.ProdutoRepository;
import br.com.fiap.checkpoint2.service.ItemPedidoService;

@RestController
@RequestMapping("item_pedidos")
public class ItemPedidoController {
	
	@Autowired
	private ItemPedidoService itemPedidoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping
	public ResponseEntity<List<SearchedItemPedido>> listAll(){
		List<SearchedItemPedido> result = itemPedidoService.list()
				.stream().map(SearchedItemPedido::toDto)
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(result);
	}

	
	@PostMapping
	public ResponseEntity<ItemPedido> create(@RequestBody ItemPedidoRequestCreate dto){
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setQuantidade(dto.getQuantidade());
		itemPedido.setValor_total(dto.getValor_total());
		itemPedido.setCodigo_produto(dto.getCodigo_produto());
		itemPedido.setNumero_pedido(dto.getNumero_pedido());
		
		
		
		boolean existsProduto = produtoRepository.existsById(dto.getCodigo_produto());
		
		if (!existsProduto) {
			return ResponseEntity.notFound().build();
			}
		
		boolean existsPedido = pedidoRepository.existsById(dto.getNumero_pedido());
		
		if (!existsPedido) {
			return ResponseEntity.notFound().build();
			}
		
		
		ItemPedido result = itemPedidoService.save(itemPedido);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
		        .body(result);
	}
	
	@PutMapping
	public ResponseEntity<ItemPedido> update (@RequestBody ItemPedidoRequestUpdate dto){
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setSequencia(dto.getSequencia());
		itemPedido.setQuantidade(dto.getQuantidade());
		itemPedido.setValor_total(dto.getValor_total());
		itemPedido.setCodigo_produto(dto.getCodigo_produto());
		itemPedido.setNumero_pedido(dto.getNumero_pedido());
		
		
		
		
		
		
		boolean existsProduto = produtoRepository.existsById(dto.getCodigo_produto());
		if (!existsProduto) {
			return ResponseEntity.notFound().build();
			}
		
		boolean existsPedido = pedidoRepository.existsById(dto.getNumero_pedido());
		if (!existsPedido) {
			return ResponseEntity.notFound().build();
			}
		
		boolean existsItemPedido = itemPedidoRepository.existsById(dto.getSequencia());
		if(!existsItemPedido) {return ResponseEntity.notFound().build();}
		
		ItemPedido result = itemPedidoService.save(itemPedido);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
		        .body(result);
	}
	
	@DeleteMapping(value = "{Sequencia}")
	public ResponseEntity<String> delete(@PathVariable Long Sequencia) {
		boolean exists = itemPedidoRepository.existsById(Sequencia);

		if (!exists) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("id não encontrado " + Sequencia);					
		}
		
		itemPedidoRepository.deleteById(Sequencia);
		
		return ResponseEntity.accepted().build();
	}
}
