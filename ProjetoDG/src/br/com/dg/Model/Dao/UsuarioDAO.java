package br.com.dg.Model.Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.dg.Model.Produto;
import br.com.dg.Model.Usuario;

public class UsuarioDAO {
	// M�todo respons�vel pela autentica��o de acesso
		public Usuario autenticar(Usuario usuario){		
			
			// Declara��o de refer�ncias JDBC
			Connection con = null;
			ResultSet rs = null;
			Statement stmt = null;
			// Declara��o de vari�vel
			String sql = "";
			
			try {
				// Faz a conex�o com o Banco
				con = GerenciadorConexoes.criarConexao();
				// Cria um objeto do tipo Statement				
				stmt = con.createStatement();
				// Define a string de SQL que deve ser executada
				sql = "select usuarios.nomeuser from dg.usuarios where usuarios.loginuser = '" + usuario.getLogin() + "' and usuarios.senhauser = '" + usuario.getSenha() + "'";	
				// Executa a string SQL e atribui os dados resultantes em um ResultSet
				rs = stmt.executeQuery(sql);
				
				// Tenta posicionar o ponteiro no pr�ximo registro do rs
				// Se conseguir (o select retornou um resultado) retorna true, sen�o (se o rs estiver vazio) ocorrer� uma exception
				if(rs.next()){
					// Recupera o nome do registro encontrado no banco de dados e armazena no objeto usuario
					usuario.setNome(rs.getString(1));
				}			
				
			} catch (SQLException e) {  // Captura erros referente a instru��es SQL
				e.printStackTrace();			
				
			} catch (Exception e) { // Captura erros imprevistos
				e.printStackTrace();		
				
			} finally{ // Ser� executado ocorrendo ou n�o um erro no bloco try	
				
				try{
					// Finaliza o ResultSet
					if(rs != null)  rs.close();
					// Finaliza o Statement
					if(stmt != null) stmt.close();
					// Finaliza a Connection 
					if(con != null) con.close();		
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}		
			// Retorna o objeto admProcurado
			return usuario;
		}

		
		
		public ArrayList<Produto> listarProdutos(){		
			
			// Declara��o de refer�ncias JDBC
			Connection con = null; 
			ResultSet rs = null;	
			Statement stmt = null;
			
			// Declara��o de vari�vel
			String sql = "";
					
			// Declara��o do ArrayList que receber� o retorno do Select
			ArrayList<Produto> listaProdutos = new ArrayList<Produto>();
			
			try {
				con = GerenciadorConexoes.criarConexao();
				stmt = con.createStatement();
				// Defini��o da String SQL
				
				sql = "select produtos.codproduto, " +
					  "       produtos.descproduto, " +			
					  "       produtos.valorproduto " +
				      "from produtos";
				   
				
				rs = stmt.executeQuery(sql);

				while(rs.next()){
					// Objeto que receber� cada usuario encontrado (cada linha do ResultSet)
					Produto umProduto = new Produto();
					// Popula o objeto com os dados do ResultSet
					umProduto.setCod(rs.getString("codproduto"));
					umProduto.setDescricao(rs.getString("descproduto"));
					umProduto.setPreco(rs.getDouble("valorproduto"));
					
					// Armazena o objeto no ArrayList
					listaProdutos.add(umProduto);
					// Elimina a refer�ncia do objeto para ser reutilizado no pr�ximo registro
					umProduto = null;
				}
				
			} catch (SQLException erro) { // Trata possiveis erros de SQL 
				erro.printStackTrace();
			} catch (Exception erro) { 
				erro.printStackTrace();
			} finally{ 			
				try{
					// Finaliza o ResultSet
					if(rs != null)  rs.close();
					// Finaliza o Statement
					if(stmt != null) stmt.close();
					// Finaliza a Connection 
					if(con != null) con.close();					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			// Retorna o ArrayList carregado com objetos contento os dados do ResultSet
			return listaProdutos;
		}
		
}
