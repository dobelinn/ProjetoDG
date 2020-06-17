package br.com.dg.Model.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.dg.Model.Produto;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ProdutoDAO {
public ResultSet rs = null;
	
	public ArrayList<String> ListarProdutos() throws SQLException{	
		ArrayList<String> ListaProdutos = new ArrayList<String>();
		try {
			
			
			Connection con = GerenciadorConexoes.criarConexao();
			Statement stmt = con.createStatement();
		
			String sql = "SELECT * FROM produtos";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
			
				ListaProdutos.add(rs.getString("descproduto"));
			
			}
		}catch(Exception erro) {
			erro.printStackTrace();
		}
		return ListaProdutos;
	}
	public Produto buscaProduto (Produto mod) throws SQLException {
	
	
		GerenciadorConexoes conector = new GerenciadorConexoes();
		conector.criarConexao();
		conector.executaSql("select * from produtos where descproduto like'%" + mod.getPesquisa() + "%'");
		conector.rs.first();
		mod.setCod(conector.rs.getString("codproduto"));
		mod.setDescricao(conector.rs.getString("descproduto"));
		mod.setPreco(conector.rs.getDouble("valorproduto"));
		return mod;
		
	}
	public void executaSql(String sql) throws SQLException {
		Connection con = GerenciadorConexoes.criarConexao();
		ResultSet rs = null;
		// stmt.executeQuery(sql);
		Statement stmt = con.createStatement();
		rs = stmt.executeQuery(sql);
	}
	public void inserir(Produto produto) {

		// Declaração de referências JDBC
		Connection con = null; 
		PreparedStatement pstmt = null;
		
		// Declaração de variável
		String sql = "";

		try {			
			// Estabelece a conexão
			con = GerenciadorConexoes.criarConexao();
			
			// A classe PreparedStatement permite a inserção 
			// de parâmetros (?) na construção da string de SQL			
			sql = "insert into dg.produtos(codproduto, " +
   			      "		                   descproduto, " +
			      "		                   valorproduto, " +
			      "		                   qtdProdutos) " +
			      "		           values (?, " + 
			      "		                   ?, " +
			      "		                   ?, " +
			      "		                   ?); ";
			
			
			// Cria um objeto prepareStatement
			pstmt = con.prepareStatement(sql);		
			
			// Define os valores dos parâmetros
			pstmt.setString(1, produto.getCod());				
			pstmt.setString(2, produto.getDescricao());
			pstmt.setDouble(3, produto.getPreco());
		
			
			// Executa a string SQL
			pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro!");
			alert.setHeaderText("Operação não pode ser realizada");
			alert.setContentText("A inserção não pôde ser concluida.");
			alert.showAndWait();				
			
			e.printStackTrace();

		} catch (Exception erro) { 
			erro.printStackTrace();
		} finally{ 			
			try{
				// Finaliza o PreparedStatement
				if(pstmt != null) pstmt.close();
				// Finaliza a Connection 
				if(con != null) con.close();					
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
public void alterar(Produto produto) {
		
		// Declaração de referências JDBC
		Connection con = null; 
		PreparedStatement pstmt = null;
		
		// Declaração de variável
		String sql = "";

		try {
			// Estabelece a conexão
			con = GerenciadorConexoes.criarConexao();	

			// A classe PreparedStatement permite a inserção de 
			// parâmetros (?) na construção da string de SQL
			sql = "update dg.produtos set produtos.descproduto = ?, " +
			      "                        produtos.valorproduto = ? " +
			      "                        produtos.qtdProdutos = ? " +
			      "where produtos.codproduto = ?;" ;
			
			// Cria um objeto prepareStatement
			pstmt = con.prepareStatement(sql);	
			
			// Define os valores dos parâmetros
			pstmt.setString(1, produto.getDescricao());				
			pstmt.setDouble(2, produto.getPreco());
			pstmt.setDouble(3, produto.getQuantidadeTotal());
			pstmt.setString(4, produto.getCod());
			
		
			
			// Executa a string SQL
			pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro!");
			alert.setHeaderText("Operação não pode ser realizada");
			alert.setContentText("A alteração não pôde ser concluida.");
			alert.showAndWait();				
			
			e.printStackTrace();
			
		} catch (Exception erro) { 
			erro.printStackTrace();
			
		} finally{ 			
			try{
				// Finaliza o PreparedStatement
				if(pstmt != null) pstmt.close();
				// Finaliza a Connection 
				if(con != null) con.close();					
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
public void excluir(Produto produto) {
	
	// Declaração de referências JDBC
	Connection con = null; 
	PreparedStatement pstmt = null;
	
	// Declaração de variável
	String sql = "";

	try {
		// Estabelece a conexão
		con = GerenciadorConexoes.criarConexao();
		
		// A classe PreparedStatement permite a inserção de 
		// parâmetros (?) na construção da string de SQL
		sql = "delete from dg.produtos where produtos.codproduto = ?;";	

		// Cria um objeto prepareStatement
		pstmt = con.prepareStatement(sql);				
		pstmt.setString(1, produto.getCod());			
		
		// Executa a string SQL
		pstmt.executeUpdate();
		pstmt.close();
		
	} catch (SQLException e) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro!");
		alert.setHeaderText("Operação não pode ser realizada");
		alert.setContentText("A exclusão não pôde ser concluida.");
		alert.showAndWait();				
		
		e.printStackTrace();
		
	} catch (Exception erro) { 
		erro.printStackTrace();
		
	} finally{ 			
		try{
			// Finaliza o PreparedStatement
			if(pstmt != null) pstmt.close();
			// Finaliza a Connection 
			if(con != null) con.close();					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
public void inserirProdSemanal(Produto produto) {

	// Declaração de referências JDBC
	Connection con = null; 
	PreparedStatement pstmt = null;
	
	// Declaração de variável
	String sql = "";

	try {			
		// Estabelece a conexão
		con = GerenciadorConexoes.criarConexao();
		
		// A classe PreparedStatement permite a inserção 
		// de parâmetros (?) na construção da string de SQL			
		sql = "insert into dg.produtosSemana(codprodutoSemanal, " +
				  "		                   descprodutoSemanal, " +
				  "		                   valorprodutoSemanal, " +
			      "		                   qtdSemana) " +
			      "		           values (?, " + 
			      "		                   ?, " +
			      "		                   ?, " +
			      "		                   ?); ";
			
			// Cria um objeto prepareStatement
			pstmt = con.prepareStatement(sql);		
			
			// Define os valores dos parâmetros
			pstmt.setString(1, produto.getCodSemanal());				
			pstmt.setString(2, produto.getDescricaoSemanal());
			pstmt.setDouble(3, produto.getPrecoSemanal());
			pstmt.setDouble(4, produto.getQuantidadeSemanal());

		
		
		// Executa a string SQL
		pstmt.executeUpdate();
		pstmt.close();
		
	} catch (SQLException e) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro!");
		alert.setHeaderText("Operação não pode ser realizada");
		alert.setContentText("A inserção não pôde ser concluida.");
		alert.showAndWait();				
		
		e.printStackTrace();

	} catch (Exception erro) { 
		erro.printStackTrace();
	} finally{ 			
		try{
			// Finaliza o PreparedStatement
			if(pstmt != null) pstmt.close();
			// Finaliza a Connection 
			if(con != null) con.close();					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
public void alterarProdSemanal(Produto produto) {
	
	// Declaração de referências JDBC
	Connection con = null; 
	PreparedStatement pstmt = null;
	
	// Declaração de variável
	String sql = "";

	try {
		// Estabelece a conexão
		con = GerenciadorConexoes.criarConexao();	

		// A classe PreparedStatement permite a inserção de 
		// parâmetros (?) na construção da string de SQL
		sql = "update dg.produtosSemana set produtosSemana.descprodutoSemanal = ?, " +
		      "                        produtosSemana.valorprodutoSemanal = ?, " +
		      "                        produtosSemana.qtdSemana = ? " +
		      "where produtosSemana.codprodutoSemanal = ?;" ;
		
		// Cria um objeto prepareStatement
		pstmt = con.prepareStatement(sql);	
		
		// Define os valores dos parâmetros
		pstmt.setString(1, produto.getDescricao());				
		pstmt.setDouble(2, produto.getPreco());
		pstmt.setDouble(3, produto.getQuantidadeSemanal());
		pstmt.setString(4, produto.getCod());
		
	
		
		// Executa a string SQL
		pstmt.executeUpdate();
		pstmt.close();

	} catch (SQLException e) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro!");
		alert.setHeaderText("Operação não pode ser realizada");
		alert.setContentText("A alteração não pôde ser concluida.");
		alert.showAndWait();				
		
		e.printStackTrace();
		
	} catch (Exception erro) { 
		erro.printStackTrace();
		
	} finally{ 			
		try{
			// Finaliza o PreparedStatement
			if(pstmt != null) pstmt.close();
			// Finaliza a Connection 
			if(con != null) con.close();					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
public void excluirProdSemanal(Produto produto) {

// Declaração de referências JDBC
Connection con = null; 
PreparedStatement pstmt = null;

// Declaração de variável
String sql = "";

try {
	// Estabelece a conexão
	con = GerenciadorConexoes.criarConexao();
	
	// A classe PreparedStatement permite a inserção de 
	// parâmetros (?) na construção da string de SQL
	sql = "delete from dg.produtosSemana where produtosSemana.codprodutoSemanal = ?;";	

	// Cria um objeto prepareStatement
	pstmt = con.prepareStatement(sql);				
	pstmt.setString(1, produto.getCod());			
	
	// Executa a string SQL
	pstmt.executeUpdate();
	pstmt.close();
	
} catch (SQLException e) {
	
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Erro!");
	alert.setHeaderText("Operação não pode ser realizada");
	alert.setContentText("A exclusão não pôde ser concluida.");
	alert.showAndWait();				
	
	e.printStackTrace();
	
} catch (Exception erro) { 
	erro.printStackTrace();
	
} finally{ 			
	try{
		// Finaliza o PreparedStatement
		if(pstmt != null) pstmt.close();
		// Finaliza a Connection 
		if(con != null) con.close();					
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
public void descontarProdutos(Produto produto) {
	
	// Declaração de referências JDBC
	Connection con = null; 
	PreparedStatement pstmt = null;
	
	// Declaração de variável
	String sql = "";

	try {
		// Estabelece a conexão
		con = GerenciadorConexoes.criarConexao();	

		// A classe PreparedStatement permite a inserção de 
		// parâmetros (?) na construção da string de SQL
		sql = "update dg.produtos set produtos.qtdProdutos = qtdProdutos - 1 " +
		      "where produtos.codproduto = ?;" ;
		
		// Cria um objeto prepareStatement
		pstmt = con.prepareStatement(sql);	
		
		// Define os valores dos parâmetros			
		pstmt.setString(1, produto.getCod());
		
	
		
		// Executa a string SQL
		pstmt.executeUpdate();
		pstmt.close();

	} catch (SQLException e) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro!");
		alert.setHeaderText("Operação não pode ser realizada");
		alert.setContentText("A alteração não pôde ser concluida.");
		alert.showAndWait();				
		
		e.printStackTrace();
		
	} catch (Exception erro) { 
		erro.printStackTrace();
		
	} finally{ 			
		try{
			// Finaliza o PreparedStatement
			if(pstmt != null) pstmt.close();
			// Finaliza a Connection 
			if(con != null) con.close();					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
public void adicionarQtdProdSemanal(Produto produto) {
	
	// Declaração de referências JDBC
	Connection con = null; 
	PreparedStatement pstmt = null;
	
	// Declaração de variável
	String sql = "";

	try {
		con = GerenciadorConexoes.criarConexao();	

		// A classe PreparedStatement permite a inserção de 
		// parâmetros (?) na construção da string de SQL
		
		sql="update dg.produtosSemana set produtosSemana.qtdSemana = produtosSemana.qtdSemana + 1.0 "
				+ "where produtosSemana.codprodutoSemanal like ? and produtosSemana.descprodutoSemanal like ?;";  
				
		// Cria um objeto prepareStatement
		pstmt = con.prepareStatement(sql);	
		
		// Define os valores dos parâmetros
		pstmt.setString(1, produto.getCodSemanal());
		pstmt.setString(2, produto.getDescricaoSemanal());

		// Executa a string SQL
		pstmt.executeUpdate();
		pstmt.close();

	} catch (SQLException e) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro!");
		alert.setHeaderText("Operação não pode ser realizada");
		alert.setContentText("A alteração não pôde ser concluida.");
		alert.showAndWait();				
		
		e.printStackTrace();
		
	} catch (Exception erro) { 
		erro.printStackTrace();
		
	} finally{ 			
		try{
			// Finaliza o PreparedStatement
			if(pstmt != null) pstmt.close();
			// Finaliza a Connection 
			if(con != null) con.close();					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
public void adicionarQtdProdSemanalExistente(Produto produto) {
	
	// Declaração de referências JDBC
	Connection con = null; 
	PreparedStatement pstmt = null;
	
	// Declaração de variável
	String sql = "";

	try {
		con = GerenciadorConexoes.criarConexao();	

		// A classe PreparedStatement permite a inserção de 
		// parâmetros (?) na construção da string de SQL
		sql = "update dg.produtosSemana set produtosSemana.qtdSemana = produtosSemana.qtdSemana + 1.0 " +
		      "where produtosSemana.codprodutoSemanal = ?;" ;
		
		// Cria um objeto prepareStatement
		pstmt = con.prepareStatement(sql);	
		
		// Define os valores dos parâmetros
		pstmt.setString(1, produto.getCodSemanal());
		
	
		
			
				
			

		// Executa a string SQL
		pstmt.executeUpdate();
		pstmt.close();

	} catch (SQLException e) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro!");
		alert.setHeaderText("Operação não pode ser realizada");
		alert.setContentText("A alteração não pôde ser concluida.");
		alert.showAndWait();				
		
		e.printStackTrace();
		
	} catch (Exception erro) { 
		erro.printStackTrace();
		
	} finally{ 			
		try{
			// Finaliza o PreparedStatement
			if(pstmt != null) pstmt.close();
			// Finaliza a Connection 
			if(con != null) con.close();					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
public String verificarProdutosSemanais(Produto produto) {
	
	// Declaração de referências JDBC
	Connection con = null; 
	Statement stmt = null;
	ResultSet rs = null;	
	// Declaração de variável
	String sql = "";

	Produto umProduto = new Produto();
	try {
		con = GerenciadorConexoes.criarConexao();
		stmt = con.createStatement();
		// Definição da String SQL
		
		sql = "select codprodutoSemanal from produtosSemana where produtosSemana.codprodutoSemanal ='" + produto.getCodSemanal() + "';" ;
		      
		   
		
		rs = stmt.executeQuery(sql);

		while(rs.next()){
			// Objeto que receberá cada usuario encontrado (cada linha do ResultSet)

			// Popula o objeto com os dados do ResultSet

			umProduto.setCodSemanal(rs.getString("codprodutoSemanal"));
			
			// Armazena o objeto no ArrayList
			//listaProdutos.add(umProduto);
			// Elimina a referência do objeto para ser reutilizado no próximo registro
			//umProduto = null;
		}
		
	} catch (SQLException e) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro!");
		alert.setHeaderText("Operação não pode ser realizada");
		alert.setContentText("A alteração não pôde ser concluida.");
		alert.showAndWait();				
		
		e.printStackTrace();
		
	} catch (Exception erro) { 
		erro.printStackTrace();
		
	} finally{ 			
		try{
			// Finaliza o PreparedStatement
			if(stmt != null) stmt.close();
			// Finaliza a Connection 
			if(con != null) con.close();					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	return umProduto.getCodSemanal();
}
public void attQtdProdSemanal(Produto produto) {
	
	// Declaração de referências JDBC
	Connection con = null; 
	PreparedStatement pstmt = null;
	
	// Declaração de variável
	String sql = "";

	try {
		// Estabelece a conexão
		con = GerenciadorConexoes.criarConexao();	

		// A classe PreparedStatement permite a inserção de 
		// parâmetros (?) na construção da string de SQL
		sql = "update dg.produtosSemana set produtosSemana.qtdSemana = produtosSemana.qtdSemana + 1.0 where produtosSemana.codprodutoSemanal = ?; ";
		
		// Cria um objeto prepareStatement
		pstmt = con.prepareStatement(sql);	
		
		// Define os valores dos parâmetros
		//pstmt.setDouble(1, produto.getQuantidadeSemanal());
		pstmt.setString(1, produto.getCodSemanal());
	
		
		// Executa a string SQL
		pstmt.executeUpdate();
		pstmt.close();

	} catch (SQLException e) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro!");
		alert.setHeaderText("Operação não pode ser realizada");
		alert.setContentText("A alteração não pôde ser concluida.");
		alert.showAndWait();				
		
		e.printStackTrace();
		
	} catch (Exception erro) { 
		erro.printStackTrace();
		
	} finally{ 			
		try{
			// Finaliza o PreparedStatement
			if(pstmt != null) pstmt.close();
			// Finaliza a Connection 
			if(con != null) con.close();					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
public void excluirProdutos(Produto produto) {

	// Declaração de referências JDBC
	Connection con = null; 
	PreparedStatement pstmt = null;

	// Declaração de variável
	String sql = "";

	try {
		// Estabelece a conexão
		con = GerenciadorConexoes.criarConexao();
		
		// A classe PreparedStatement permite a inserção de 
		// parâmetros (?) na construção da string de SQL
		sql = "delete from dg.produtosSemana where produtosSemana.codprodutoSemanal = ?;";	

		// Cria um objeto prepareStatement
		pstmt = con.prepareStatement(sql);				
		pstmt.setString(1, produto.getCodSemanal());			
		
		// Executa a string SQL
		pstmt.executeUpdate();
		pstmt.close();
		
	} catch (SQLException e) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro!");
		alert.setHeaderText("Operação não pode ser realizada");
		alert.setContentText("A exclusão não pôde ser concluida.");
		alert.showAndWait();				
		
		e.printStackTrace();
		
	} catch (Exception erro) { 
		erro.printStackTrace();
		
	} finally{ 			
		try{
			// Finaliza o PreparedStatement
			if(pstmt != null) pstmt.close();
			// Finaliza a Connection 
			if(con != null) con.close();					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	}
}
