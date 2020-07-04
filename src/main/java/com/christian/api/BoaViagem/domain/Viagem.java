package com.christian.api.BoaViagem.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

/**
*
* @author Christian
*/

@Entity
@Table(name="tb_viagem")
public class Viagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_viagem")
    private Integer idViagem;
	
	@NotNull
    @Column(length = 500)
    private String destino;
	
    @OneToMany(mappedBy = "viagem")
    private List<Gasto> gastos;
    
	@NotNull
    private Integer tipoViagem;
    
	@NotNull
	@Column(length = 20)
    private Date dataChegada;
	
	@Column(length = 20)
    private Date dataPartida;
	
	@NotNull
	private Double orcamento;
	
	@NotNull
	@Column(name="quantidade_pessoas")
	private Integer quantidadePessoas;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	@NotNull    
    private Usuario usuario;	
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getIdViagem() {
		return idViagem;
	}

	public void setIdViagem(Integer idViagem) {
		this.idViagem = idViagem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public List<Gasto> getGastos() {
		return gastos;
	}

	public void setGastos(List<Gasto> gastos) {
		this.gastos = gastos;
	}

	public Integer getTipoViagem() {
		return tipoViagem;
	}

	public void setTipoViagem(Integer tipoViagem) {
		this.tipoViagem = tipoViagem;
	}

	public Date getDataChegada() {
		return dataChegada;
	}

	public void setDataChegada(Date dataChegada) {
		this.dataChegada = dataChegada;
	}

	public Date getDataPartida() {
		return dataPartida;
	}

	public void setDataPartida(Date dataPartida) {
		this.dataPartida = dataPartida;
	}

	public Double getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Double orcamento) {
		this.orcamento = orcamento;
	}

	public Integer getQuantidadePessoas() {
		return quantidadePessoas;
	}

	public void setQuantidadePessoas(Integer quantidadePessoas) {
		this.quantidadePessoas = quantidadePessoas;
	}
	

}
