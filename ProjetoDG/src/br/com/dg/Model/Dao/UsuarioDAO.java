package br.com.dg.Model.Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.dg.Model.Produto;
import br.com.dg.Model.Usuario;

public class UsuarioDAO {
	// Método responsável pela autenticação de acesso
		public Usuario autenticar(Usuario usuario){		
			
			// Declaração de referências JDBC
			Connection con = null;
			ResultSet rs = null;
			Statement stmt = null;
			// Declaração de variável
			String sql = "";
			
			try {
				// Faz a conexão com o Banco
				con = GerenciadorConexoes.criarConexao();
				// Cria um objeto do tipo Statement				
				stmt = con.createStatement();
				// Define a string de SQL que deve ser executada
				sql = "select usuarios.nomeuser from dg.usuarios where usuarios.loginuser = '" + usuario.getLogin() + "' and usuarios.senhauser = '" + usuario.getSenha() + "'";	
				// Executa a string SQL e atribui os dados resultantes em um ResultSet
				rs = stmt.executeQuery(sql);
				
				// Tenta posicionar o ponteiro no próximo registro do rs
				// Se conseguir (o select retornou um resultado) retorna true, senão (se o rs estiver vazio) ocorrerá uma exception
				if(rs.next()){
					// Recupera o nome do registro encontrado no banco de dados e armazena no objeto usuario
					usuario.setNome(rs.getString(1));
				}			
				
			} catch (SQLException e) {  // Captura erros referente a instruções SQL
				e.printStackTrace();			
				
			} catch (Exception e) { // Captura erros imprevistos
				e.printStackTrace();		
				
			} finally{ // Será executado ocorrendo ou não um erro no bloco try	
				
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
			
			// Declaração de referências JDBC
			Connection con = null; 
			ResultSet rs = null;	
			Statement stmt = null;
			
			// Declaração de variável
			String sql = "";
					
			// Declaração do ArrayList que receberá o retorno do Select
			ArrayList<Produto> listaProdutos = new ArrayList<Produto>();
			
			try {
				con = GerenciadorConexoes.criarConexao();
				stmt = con.createStatement();
				// Definição da String SQL
				
				sql = "select produtos.codproduto, " +
					  "       produtos.descproduto, " +			
					  "       produtos.valorproduto " +
				      "from produtos";
				   
				
				rs = stmt.executeQuery(sql);

				while(rs.next()){
					// Objeto que receberá cada usuario encontrado (cada linha do ResultSet)
					Produto umProduto = new Produto();
					// Popula o objeto com os dados do ResultSet
					umProduto.setCod(rs.getString("codproduto"));
					umProduto.setDescricao(rs.getString("descproduto"));
					umProduto.setPreco(rs.getDouble("valorproduto"));
					
					// Armazena o objeto no ArrayList
					listaProdutos.add(umProduto);
					// Elimina a referência do objeto para ser reutilizado no próximo registro
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
