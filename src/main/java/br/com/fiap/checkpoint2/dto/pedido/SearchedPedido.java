package br.com.fiap.checkpoint2.dto.pedido;

import java.time.LocalDate;

import br.com.fiap.checkpoint2.model.Pedido;

public class SearchedPedido {

	private Long numero_pedido;
	
	private Long codigo_cliente;
	private LocalDate data_pedido;
	
	public Long getNumero_pedido() {
		return numero_pedido;
	}
	public void setNumero_pedido(Long numero_pedido) {
		this.numero_pedido = numero_pedido;
	}
	public Long getCodigo_cliente() {
		return codigo_cliente;
	}
	public void setCodigo_cliente(Long codigo_cliente) {
		this.codigo_cliente = codigo_cliente;
	}
	public LocalDate getData_pedido() {
		return data_pedido;
	}
	public void setData_pedido(LocalDate data_pedido) {
		this.data_pedido = data_pedido;
	}
	
	public static SearchedPedido toDto(Pedido pedido) {
		SearchedPedido dto = new SearchedPedido();
		dto.setNumero_pedido(pedido.getNumero_pedido());
		dto.setCodigo_cliente(pedido.getCodigo_cliente());
		dto.setData_pedido(pedido.getData_pedido());
		
		return dto;
	}
	
	
}
