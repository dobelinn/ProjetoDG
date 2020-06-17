package br.com.dg.Model.TableView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UsuariosTableViewModel {
	private final SimpleIntegerProperty pCodigo;
	private final SimpleStringProperty pNome;
	private final SimpleStringProperty pTipo;
	private final SimpleStringProperty pEmail;
	private final SimpleStringProperty pTelefone;
	private final SimpleStringProperty pLogin;
	private final SimpleStringProperty pSenha;
	
	public UsuariosTableViewModel() {
		this.pCodigo = new SimpleIntegerProperty(0);
		this.pNome = new SimpleStringProperty("");
		this.pTipo = new SimpleStringProperty("");
		this.pEmail = new SimpleStringProperty("");
		this.pTelefone = new SimpleStringProperty("");
		this.pLogin = new SimpleStringProperty("");
		this.pSenha = new SimpleStringProperty("");
	}

	public UsuariosTableViewModel(SimpleIntegerProperty pCodigo,
								  SimpleStringProperty pNome,
			                      SimpleStringProperty pTipo, 
			                      SimpleStringProperty pEmail,
			                      SimpleStringProperty pTelefone,
			                      SimpleStringProperty pLogin,
			                      SimpleStringProperty pSenha) {
		this.pCodigo = pCodigo;
		this.pNome = pNome;
		this.pTipo = pTipo;
		this.pEmail = pEmail;
		this.pTelefone = pTelefone;
		this.pLogin = pLogin;
		this.pSenha = pSenha;
	}

	
	public Integer getpCodigo() {
		return pCodigo.get();
	}
	
	public SimpleIntegerProperty pCodigoProperty() {
		return pCodigo;
	}

	public void setpCodigo(Integer pCodigo) {
		this.pCodigo.set(pCodigo);
	}

	
	public String getpNome() {
		return pNome.get();
	}
	
	public SimpleStringProperty pNomeProperty() {
		return pNome;
	}

	public void setpNome(String pNome) {
		this.pNome.set(pNome);
	}

		
	public SimpleStringProperty pTipoProperty() {
		return pTipo;
	}

	public String getpTipo() {
		return pTipo.get();
	}

	public void setpTipo(String pTipo) {
		this.pTipo.set(pTipo);
	}
	
	
	public SimpleStringProperty pEmailProperty() {
		return pEmail;
	}

	public String getpEmail() {
		return pEmail.get();
	}

	public void setpEmail(String pEmail) {
		this.pEmail.set(pEmail);
	}
	
	
	public SimpleStringProperty pTelefoneProperty() {
		return pTelefone;
	}

	public String getpTelefone() {
		return pTelefone.get();
	}

	public void setpTelefone(String pTelefone) {
		this.pTelefone.set(pTelefone);
	}

	
	
	public SimpleStringProperty pLoginProperty() {
		return pLogin;
	}

	public String getpLogin() {
		return pLogin.get();
	}

	public void setpLogin(String pLogin) {
		this.pLogin.set(pLogin);
	}

	
	public SimpleStringProperty pSenhaProperty() {
		return pSenha;
	}

	public String getpSenha() {
		return pSenha.get();
	}

	public void setpSenha(String pSenha) {
		this.pSenha.set(pSenha);
	}
}
