package br.com.dg.Model.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GerenciadorConexoes {

	// url que identifica o banco
	private static String url = "jdbc:mysql://localhost:3306/dg?useSSL=false&useTimezone=true&serverTimezone=UTC";
	// login de usuario no banco
	private static String usuario = "root";
	// senha do usuario no banco
	private static String senha = "";

	public ResultSet rs = null;
	
	public static Connection criarConexao() throws SQLException {
		
		Connection conexao = null;
		// Solicita a conexão
		conexao = DriverManager.getConnection(url, usuario, senha);		
		// Retorna o objeto de conexão ou gera uma exceção  
		return conexao;
	}
	public void executaSql(String sql) throws SQLException {
		Connection con = GerenciadorConexoes.criarConexao();
		ResultSet rs = null;
		// stmt.executeQuery(sql);
		Statement stmt = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE,rs.CONCUR_READ_ONLY);
		rs = stmt.executeQuery(sql);
	}
}
