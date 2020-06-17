package br.com.dg.Model;

public class Usuario {

	private Integer Codigo;
	private String nome;
	private String tipo;
	private String email;
	private String telefone;
	private String login;
	private String senha;
	
	
	public Usuario() {
		this.Codigo = 0;
		this.nome = "";
		this.tipo = "";
		this.email = "";
		this.telefone = "";
		this.login = "";
		this.senha = "";
	
	}
	
	public Usuario(Integer codigo, String nome, String tipo, String email, String telefone, String login, String senha) {
		this.Codigo = codigo;
		this.nome = nome;
		this.tipo = tipo;
		this.email = email;
		this.telefone = telefone;
		this.login = login;
		this.senha = senha;
		
	}

	public Integer getCodigo() {
		return Codigo;
	}
	public void setCodigo(Integer codigo) {
		this.Codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	
	public String identificar() {
		
		return "";
	}
}
