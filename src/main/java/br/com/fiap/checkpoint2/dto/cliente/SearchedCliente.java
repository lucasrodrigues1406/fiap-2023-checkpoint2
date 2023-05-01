package br.com.fiap.checkpoint2.dto.cliente;

import br.com.fiap.checkpoint2.model.Cliente;

public class SearchedCliente {

	private Long codigo_cliente;
	
	private String nome, cpf, cep;

	public Long getCodigo_cliente() {
		return codigo_cliente;
	}

	public void setCodigo_cliente(Long codigo_cliente) {
		this.codigo_cliente = codigo_cliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public static SearchedCliente toDto(Cliente cliente) {
		SearchedCliente dto = new SearchedCliente();
		dto.setCep(cliente.getCep());
		dto.setCodigo_cliente(cliente.getCodigo_cliente());
		dto.setCpf(cliente.getCpf());
		dto.setNome(cliente.getNome());
		
		return dto;
	}
}
