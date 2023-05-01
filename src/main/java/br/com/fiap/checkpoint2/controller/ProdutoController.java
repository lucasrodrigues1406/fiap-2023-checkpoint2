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

import br.com.fiap.checkpoint2.dto.produto.ProdutoRequestCreate;
import br.com.fiap.checkpoint2.dto.produto.ProdutoRequestUpdate;
import br.com.fiap.checkpoint2.dto.produto.SearchedProduto;
import br.com.fiap.checkpoint2.model.Produto;
import br.com.fiap.checkpoint2.repository.ProdutoRepository;
import br.com.fiap.checkpoint2.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping
	public ResponseEntity<List<SearchedProduto>> listAll(){
		List<SearchedProduto> result = produtoService.list()
				.stream().map(SearchedProduto::toDto)
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(result);
	}
	
	@PostMapping
	public ResponseEntity<Produto> create (@RequestBody ProdutoRequestCreate dto){
		Produto produto = new Produto();
		produto.setNome(dto.getNome());
		produto.setPreco(dto.getPreco());
		produto.setData_validade(dto.getData_garantia());
		produto.setData_garantia(dto.getData_garantia());
		produto.setEm_estoque(dto.isEm_estoque());
		
		Produto result = produtoService.save(produto);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
		        .body(result);
	}
	
	@PutMapping
	public ResponseEntity<Produto> update (@RequestBody ProdutoRequestUpdate dto){
		
		boolean exists = produtoRepository.existsById(dto.getCodigo_produto());
		
		if (!exists) {
			return ResponseEntity.notFound().build();
		}
		
		Produto produto = new Produto();
		produto.setCodigo_produto(dto.getCodigo_produto());
		produto.setNome(dto.getNome());
		produto.setPreco(dto.getPreco());
		produto.setData_validade(dto.getData_garantia());
		produto.setData_garantia(dto.getData_garantia());
		produto.setEm_estoque(dto.isEm_estoque());
		
		Produto result = produtoService.save(produto);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
		        .body(result);
	}
	
	@DeleteMapping(value = "{Codigo_produto}")
	public ResponseEntity<String> delete (@PathVariable Long Codigo_produto){
		boolean exists = produtoRepository.existsById(Codigo_produto);
		
		if (!exists) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("id não encontrado " + Codigo_produto);
						
		}
		
		produtoRepository.deleteById(Codigo_produto);
		
		return ResponseEntity.accepted().build();
	}
	
	
	
}
