package br.com.dg.Model.TableView;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProdutosTableViewModel {
	private final SimpleStringProperty pDescricao;
	private final SimpleStringProperty pCodigo;
	private final SimpleDoubleProperty pPreco;
	private final SimpleDoubleProperty pQuantidadeTotal;
	private final SimpleDoubleProperty pQuantidadeSemanal;
	private final SimpleDoubleProperty pQuantidadeUmProduto;
	
	
	public ProdutosTableViewModel() {
		this.pDescricao = new SimpleStringProperty("");
		this.pCodigo = new SimpleStringProperty("");
		this.pPreco = new SimpleDoubleProperty(0);
		this.pQuantidadeTotal = new SimpleDoubleProperty(0);
		this.pQuantidadeSemanal = new SimpleDoubleProperty(0);
		this.pQuantidadeUmProduto = new SimpleDoubleProperty(1);
		
	}

	public ProdutosTableViewModel(SimpleStringProperty pDescricao, 
									SimpleStringProperty pCodigo,
			                         SimpleDoubleProperty pPreco,
			                         SimpleDoubleProperty pQuantidadeTotal,
			                         SimpleDoubleProperty pQuantidadeSemanal,
			                         SimpleDoubleProperty pQuantidadeUmProduto) {
		this.pDescricao = pDescricao;
		this.pCodigo = pCodigo;
		this.pPreco = pPreco;
		this.pQuantidadeTotal = pQuantidadeTotal;
		this.pQuantidadeSemanal = pQuantidadeSemanal;
		this.pQuantidadeUmProduto = pQuantidadeUmProduto;

	}

	public String getpDescricao() {
		return pDescricao.get();
	}
	
	public SimpleStringProperty pDescricaoProperty() {
		return pDescricao;
	}

	public void setpDescricao(String pDescricao) {
		this.pDescricao.set(pDescricao);
	}

	
	public SimpleStringProperty pCodigoProperty() {
		return pCodigo;
	}

	public String getpCodigo() {
		return pCodigo.get();
	}

	public void setpCodigo(String pCodigo) {
		this.pCodigo.set(pCodigo);
	}

	public SimpleDoubleProperty pPrecoProperty() {
		return pPreco;
	}

	public Double getpPreco() {
		return pPreco.get();
	}

	public void setpPreco(Double pPreco) {
		this.pPreco.set(pPreco);
	}
	

	public Double getpQuantidadeTotal() {
		return pQuantidadeTotal.get();
	}

	public void setpQuantidadeTotal(Double pQuantidadeTotal) {
		this.pQuantidadeTotal.set(pQuantidadeTotal);
	}
	public SimpleDoubleProperty pQuantidadeTotalProperty() {
		return pQuantidadeTotal;
	}

	public Double getpQuantidadeSemanal() {
		return pQuantidadeSemanal.get();
	}

	public void setpQuantidadeSemanal(Double pQuantidadeSemanal) {
		this.pQuantidadeSemanal.set(pQuantidadeSemanal);
	}
	public SimpleDoubleProperty pQuantidadeSemanalProperty() {
		return pQuantidadeSemanal;
	}
	
	public SimpleDoubleProperty pQuantidadeUmProdutoProperty() {
		return pQuantidadeUmProduto;
	}

	public Double getpQuantidadeUmProduto() {
		return pQuantidadeUmProduto.get();
	}

	public void setpQuantidadeUmProduto(Double pQuantidadeUmProduto) {
		this.pQuantidadeUmProduto.set(pQuantidadeUmProduto);
	}
}
