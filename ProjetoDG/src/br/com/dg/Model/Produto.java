package br.com.dg.Model;

public class Produto {
	private String descricao;
	private Double preco;
	private String cod;
	private String pesquisa;
	private String descricaoSemanal;
	private Double quantidadeTotal;
	private Double quantidadeSemanal;
	private Double quantidadeUmProduto;
	
	public Double getQuantidadeTotal() {
		return quantidadeTotal;
	}

	public void setQuantidadeTotal(Double quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}

	public Double getQuantidadeSemanal() {
		return quantidadeSemanal;
	}

	public void setQuantidadeSemanal(Double quantidadeSemanal) {
		this.quantidadeSemanal = quantidadeSemanal;
	}

	public Double getQuantidadeUmProduto() {
		return quantidadeUmProduto;
	}

	public void setQuantidadeUmProduto(Double quantidadeUmProduto) {
		this.quantidadeUmProduto = quantidadeUmProduto;
	}

	public String getDescricaoSemanal() {
		return descricaoSemanal;
	}

	public void setDescricaoSemanal(String descricaoSemanal) {
		this.descricaoSemanal = descricaoSemanal;
	}

	public Double getPrecoSemanal() {
		return precoSemanal;
	}

	public void setPrecoSemanal(Double precoSemanal) {
		this.precoSemanal = precoSemanal;
	}

	public String getCodSemanal() {
		return codSemanal;
	}

	public void setCodSemanal(String codSemanal) {
		this.codSemanal = codSemanal;
	}

	private Double precoSemanal;
	private String codSemanal;


	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
	
	public Produto() {
		this.descricao = "";
		this.cod = "";
		this.preco = 0.0;
		this.pesquisa = "";
	}

	public Produto(String descricao, String cod, Double preco, String pesquisa) {
		this.descricao = descricao;
		this.cod = "";
		this.preco = preco;
		this.pesquisa = pesquisa;
		
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

}
