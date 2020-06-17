package br.com.dg.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.dg.Model.Produto;
import br.com.dg.Model.Dao.GerenciadorConexoes;
import br.com.dg.Model.Dao.ProdutoDAO;
import br.com.dg.Model.Dao.UsuarioDAO;
import br.com.dg.Model.TableView.ProdutosTableViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class JFXProdutosControle implements Initializable {
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	ProdutoDAO produtoDAO = new ProdutoDAO();
	// Atributos
	private Stage palcoProdutos;
	private ArrayList<ProdutosTableViewModel> listaProdutosTvModel = new ArrayList<ProdutosTableViewModel>(); 
	private ArrayList<ProdutosTableViewModel> listaDescProdutosTvModel = new ArrayList<ProdutosTableViewModel>(); 
	private String operacao;


	// Componentes JavaFX
	@FXML Button bInserir;
	@FXML Button bAlterar;
	@FXML Button bExcluir;
	@FXML Button bConfirmar;
	@FXML Button bCancelar;
	@FXML Label lFechar;
	@FXML TextField tfCodigo;
	@FXML TableView<ProdutosTableViewModel> tvProdutos;
	@FXML TableColumn<ProdutosTableViewModel, String> colDesc;
	@FXML TableColumn<ProdutosTableViewModel, String> colPreco;
	@FXML TextField tfPesquisa;
	@FXML TextField tfPreco;
	@FXML TextField tfDesc;

	int i=-1;
	
    private final ObservableList<ProdutosTableViewModel> dataList = FXCollections.observableArrayList();
	// Metodos de acesso
	public Stage getPalcoProdutos() {
		return palcoProdutos;
	}

	public void setPalcoProdutos(Stage palcoProdutos) {
		this.palcoProdutos = palcoProdutos;
	}


	public ArrayList<ProdutosTableViewModel> getListaProdutosTvModel() {
		return listaProdutosTvModel;
	}

	// Sobrecarga do m�todo setListaProdutos
	public void setListaProdutosTvModel(ProdutosTableViewModel produtoTvModel) {
		this.listaProdutosTvModel.add(produtoTvModel);
	}
	public void setListaProdutosTvModel(ArrayList<ProdutosTableViewModel> listaProdutosTvModel) {
		this.listaProdutosTvModel.addAll(listaProdutosTvModel);
	}
	/* -------------------------------------------------------------------- */
	
	public ArrayList<ProdutosTableViewModel> getListaDescProdutosTvModel() {
		return listaDescProdutosTvModel;
	}

	// Sobrecarga do m�todo setListaProdutos
	public void setListaDescProdutosTvModel(ProdutosTableViewModel descprodutoTvModel) {
		this.listaDescProdutosTvModel.add(descprodutoTvModel);
	}
	public void setListaDescProdutosTvModel(ArrayList<ProdutosTableViewModel> listaDescProdutosTvModel) {
		this.listaDescProdutosTvModel.addAll(listaDescProdutosTvModel);
	}
	private String getOperacao() {
		return operacao;
	}

	private void setOperacao(String operacao) {
		this.operacao = operacao;
	}


	ProdutoDAO pesquisac = new ProdutoDAO();
	Produto produto = new Produto();

	@FXML TableColumn<ProdutosTableViewModel, String> colCod;
	@FXML TableColumn<ProdutosTableViewModel, String> colDescBanco;
	@FXML ImageView bReload;
	@FXML Button bInserirVendaSemanal;
	@FXML Button bAcessarTabelaSemanal;
	@FXML TableColumn<ProdutosTableViewModel, String> colQuantidade;
	@FXML TextField tfQuantidade;


	public void carregarTv(){
		
		// Sincroniza o modelo do tvUsuarios com o Banco de Dados
		this.carregaProdutosTableViewModel();
		
		// Vincula o atributo listaUsuarios como fonte de dados para a tabela
		tvProdutos.setItems(FXCollections.observableArrayList(this.getListaProdutosTvModel()));

		// Atualiza visualiza��o dos dados 
		tvProdutos.refresh();
	}
	public void atualizarTv(){
		// Sincroniza o modelo do tvUsuarios com o Banco de Dados
		this.carregarTv();
		
		// Vincula o atributo listaUsuarios como fonte de dados para a tabela
		//tvProdutos.setItems(FXCollections.observableArrayList(this.getListaProdutosTvModel()));

		// Atualiza visualiza��o dos dados 
		//this.carregarTv();
		tvProdutos.refresh();
	}
	public void prepararPesquisa() {
		dataList.addAll(this.getListaProdutosTvModel());
		FilteredList<ProdutosTableViewModel> filteredData = new FilteredList<>(dataList, b -> true);
        
		dataList.removeAll(dataList);
		
		this.carregarTv();

		//tfPesquisa.requestFocus();
		dataList.addAll(this.getListaProdutosTvModel());
		// Preenche o TableView com os Usuarios armazenados no banco de dados		
		//this.atualizarTableView();
		 // Wrap the ObservableList in a FilteredList (initially display all data).
   

      

		
		// 2. Set the filter Predicate whenever the filter changes.
		tfPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(produto -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (produto.getpDescricao().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (produto.getpCodigo().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				else if (String.valueOf(produto.getpPreco()).indexOf(lowerCaseFilter)!=-1)
				     return true;
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<ProdutosTableViewModel> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tvProdutos.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tvProdutos.setItems(sortedData);
		tvProdutos.refresh();
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
				
	

		colDescBanco.setCellValueFactory(  new PropertyValueFactory<>( "pDescricao" ) );		
		colCod.setCellValueFactory(  new PropertyValueFactory<>( "pCodigo" ) );		
		colPreco.setCellValueFactory(  new PropertyValueFactory<>( "pPreco" ) );
		colQuantidade.setCellValueFactory(  new PropertyValueFactory<>( "pQuantidadeTotal" ) );
		
		bConfirmar.setVisible(false);
		bCancelar.setVisible(false);
		this.prepararPesquisa();
	
	}
	@FXML public void reloadTabela() {
		//VOLTAR AQUI 
		//tvProdutos.
		//this.listarProdutos().clear();
		//this.carregarTv();
		this.prepararPesquisa();
		
	}

	@FXML public void acessarVendaSemanal() throws IOException {
				this.palcoProdutos.close();
				// Cria um novo palco
				Stage stage = new Stage();
				// Objeto FMXLLoader que carrega o arquivo fxml
				FXMLLoader loader = new FXMLLoader();
				// Carregamento o arquivo fxml
				loader.setLocation(getClass().getResource("/br/com/dg/view/JFXProdutosSemanaisLayout.fxml"));
				// Cria��o do Layout Pane (gerenciador de layout), que ser� o node/n�/componente raiz, e vinculo com o arquivo fxml
				Pane node =  loader.load();
				// Atribui��o do componente a cena
				Scene scene = new Scene(node);
				// Atribui��o da cena ao novo palco
				stage.setScene(scene);

				// O objeto loader possui a refer�ncia da classe JFXusuariosControle 
				JFXProdutosSemanaisControle produtosSemanaisControle = loader.getController();	
				// E acesso a seus m�todos.
				// A refer�ncia do palco criado � passada para posterior acesso (fechamento)
				produtosSemanaisControle.setPalcoProdutosSemanais(stage);
				
				// Retira a barra superior da janela (icone, titulo, minimizar, maximizar e fechar)
				stage.initStyle(StageStyle.UNDECORATED);
				// N�o permite o redimensionamento
				stage.setResizable(false);
				// Centraliza a apresenta��o
				stage.centerOnScreen();
				// Define o comportamento Modal (bloqueia os demais formul�rios enquanto ele estiver aberto)
				stage.initModality(Modality.WINDOW_MODAL);
				// Indica que esse formul�rio (principal) ficar� bloqueado enquanto o formul�rio de nativos estiver ativo
				stage.initOwner(this.getPalcoProdutos());
				// Esconde esta tela

				// Apresenta o formul�rio
				stage.show();				
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
			
			sql = "select codproduto,descproduto,valorproduto,qtdProdutos from produtos";
			      
			   
			
			rs = stmt.executeQuery(sql);

			while(rs.next()){
				// Objeto que receber� cada usuario encontrado (cada linha do ResultSet)
				Produto umProduto = new Produto();
				// Popula o objeto com os dados do ResultSet

				umProduto.setDescricao(rs.getString("descproduto"));
				umProduto.setCod(rs.getString("codproduto"));
				umProduto.setPreco(Double.parseDouble(rs.getString("valorproduto")));
				umProduto.setQuantidadeTotal(Double.parseDouble(rs.getString("qtdProdutos")));
				
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
public Produto verificarProdutosSemanais(){		
		
		// Declara��o de refer�ncias JDBC
		Connection con = null; 
		ResultSet rs = null;	
		Statement stmt = null;
		
		// Declara��o de vari�vel
		String sql = "";
				
		// Declara��o do ArrayList que receber� o retorno do Select
		ArrayList<Produto> listaProdutos = new ArrayList<Produto>();
		Produto umProduto = new Produto();
		try {
			con = GerenciadorConexoes.criarConexao();
			stmt = con.createStatement();
			// Defini��o da String SQL
			
			sql = "select codproduto from produtos where produtos.codproduto ='" + tfCodigo.getText() + "';" ;
			      
			   
			
			rs = stmt.executeQuery(sql);

			while(rs.next()){
				// Objeto que receber� cada usuario encontrado (cada linha do ResultSet)

				// Popula o objeto com os dados do ResultSet

				umProduto.setCod(rs.getString("codproduto"));
				
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
		return umProduto;
	}
	@FXML public void carregaDescDados() {
			if(tvProdutos.getSelectionModel().getSelectedIndex() < 0) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Opera��o inv�lida");
			alert.setHeaderText("Detalhamento:");
			alert.setContentText( "N�o h� produtos selecionados" );
			alert.showAndWait();

		}else {

			// Obt�m o objeto selecionado na tabela
			ProdutosTableViewModel produtoTvModelSelecionado = tvProdutos.getSelectionModel().getSelectedItem();

			tfCodigo.setText(String.valueOf(produtoTvModelSelecionado.getpCodigo()));
			tfDesc.setText(produtoTvModelSelecionado.getpDescricao());
			tfPreco.setText(String.valueOf(produtoTvModelSelecionado.getpPreco()));
			tfQuantidade.setText(String.valueOf(produtoTvModelSelecionado.getpQuantidadeTotal()));
			
		}
	}
	


	
	public ArrayList<ProdutosTableViewModel> carregaProdutosTableViewModel(){
		
		this.getListaProdutosTvModel().clear();
		
		// Percorre o ArrayList de Usuarios retornado pelo m�todo listarusuarios do objeto usuarioDAO
		// carregando um ArrayList de usuarioTableViewModel (modelo da TableView) 
		for (Produto produto : this.listarProdutos()) {
			
			ProdutosTableViewModel umProdutoTvModel = new ProdutosTableViewModel();
			umProdutoTvModel.setpCodigo(produto.getCod());
			umProdutoTvModel.setpDescricao(produto.getDescricao());
			umProdutoTvModel.setpPreco(produto.getPreco());
			umProdutoTvModel.setpQuantidadeTotal(produto.getQuantidadeTotal());
			
	
			this.setListaProdutosTvModel(umProdutoTvModel);
		}	
		return this.getListaProdutosTvModel();	 
	}
	
	/*
	public void atualizarTableView() {
		
		// Sincroniza o modelo do tvUsuarios com o Banco de Dados
		this.carregaProdutosTableViewModel();

		// Vincula o atributo listaUsuarios como fonte de dados para a tabela
		tvProdutos.setItems(FXCollections.observableArrayList(this.getListaProdutosTvModel()));

		// Atualiza visualiza��o dos dados 
		tvProdutos.refresh();
		
	}
*/
	
	
	

	
	@FXML public void inserir() {

		this.habilitarCampos();
		this.limparCampos();
		this.ajustarBotoesInserir();

		tfPesquisa.requestFocus();

		// Identifica a opera��o e ajusta os bot�es
		this.setOperacao("inserir");
		this.ajustarBotoesInserir();			
	}

	@FXML public void inserirVendaSemanal() {
		
		this.desabilitarCampos();
		this.ajustarBotoesInserir();

		tfPesquisa.requestFocus();

		// Identifica a opera��o e ajusta os bot�es
		this.setOperacao("inserirProdutoSemanal");
		this.ajustarBotoesInserir();			
	}


	@FXML public void alterar() {

		try {

			// Verifica se nenhum usuario foi selecionado
			if(tvProdutos.getSelectionModel().getSelectedIndex() < 0) {
				throw new IOException();
			}

			this.habilitarCampos();
			this.ajustarBotoesAlterar();
			tfPesquisa.requestFocus();
	
			// Identifica a opera��o e ajusta os bot�es		
			this.setOperacao("alterar");			

		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Ops!");
			alert.setHeaderText("Opera��o n�o pode ser realizada");
			alert.setContentText("Selecione um produto para altera��o");
			alert.showAndWait();				
		} catch (Exception e) {
			// A exce��o ocorre quando uma exception diferente das tratadas acima � gerada.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Opera��o n�o realizada");
			alert.setHeaderText("Ocorreu um problema no processo de entrada");
			alert.setContentText("Favor entrar em contato com o desenvolvedor!");
			alert.showAndWait();
			// Detalhes do erro imprevisto
			e.printStackTrace();
		}				
	}		
	


	@FXML public void excluir() {
	
		// Verifica se um usuario foi selecionado
		if(tvProdutos.getSelectionModel().getSelectedIndex() >= 0) {
			
			// Identifica a opera��o e ajusta os bot�es		
			this.setOperacao("excluir");
			this.ajustarBotoesExcluir();

			
		}else {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alerta");
			alert.setHeaderText("Exclus�o n�o realizada");
			alert.setContentText("Selecione um produto para exclus�o");
			alert.showAndWait();
		}		

	}

	
	@SuppressWarnings("unlikely-arg-type")
	@FXML public void confirmar() {

		try {

			// Verifica campos obrigat�rios
			if (tfCodigo.getText().isEmpty() ||
				tfDesc.getText().isEmpty() ||   
				tfPreco.getText().isEmpty()) {

				// Define a mensagem de erro
				throw new IOException("Todos os campos s�o obrigat�rios.");	
			}

			// Se a opera��o for inserir
			if(this.getOperacao().equals("inserir")) {
				int resposta = JOptionPane.showConfirmDialog(null, "Confirma a inser��o?", "Aten��o!", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
				// Cria um objeto Agente para receber os dados lidos pelo formul�rio
				Produto novoProduto = new Produto();

				// Cria um Array para receber a latitude e longitude lidos pelo formul�rio
				
				
				novoProduto.setCod( tfCodigo.getText() );
				novoProduto.setDescricao( tfDesc.getText() );
				novoProduto.setPreco(Double.parseDouble(tfPreco.getText()) );
				novoProduto.setQuantidadeTotal(Double.parseDouble(tfQuantidade.getText()) );
				

				// Invoca o m�todo inserir do objeto agenteDAO
				produtoDAO.inserir(novoProduto);
			
				// Atualiza a tabela
				this.prepararPesquisa();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("A��o Conclu�da");
				alert.setHeaderText("Produto inserido no Banco de Dados com sucesso!");
				alert.showAndWait();
				
				
				}
				this.limparCampos();
				this.ajustarBotoesInicial();
				this.desabilitarCampos();
				this.prepararPesquisa();
			}
			// Se a opera��o for inserir
			if(this.getOperacao().equals("inserirProdutoSemanal")) {
				int resposta = JOptionPane.showConfirmDialog(null, "Confirma a inser��o na Tabela Semanal?", "Aten��o, Confira bem os Campos!", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					
					// Cria um objeto Agente para receber os dados lidos pelo formul�rio
					Produto novoProdutoSemanal = new Produto();
					Produto produtoDescontado = new Produto();
					Produto novaQuantidadeSemanal = new Produto();
					Produto atualizarQuantidadeSemanal = new Produto();
					double qtdSemanal=100 - Double.parseDouble(tfQuantidade.getText()) ;
					//double i=0;
					novoProdutoSemanal.setCodSemanal( tfCodigo.getText() );
					produtoDescontado.setCod(tfCodigo.getText());	
					
					if(novoProdutoSemanal.getCodSemanal().equals(produtoDAO.verificarProdutosSemanais(novoProdutoSemanal))) {
						
					
						produtoDAO.excluirProdutos(novoProdutoSemanal);
						novoProdutoSemanal.setCodSemanal( tfCodigo.getText() );
						novoProdutoSemanal.setDescricaoSemanal( tfDesc.getText() );
						novoProdutoSemanal.setPrecoSemanal(Double.parseDouble(tfPreco.getText()) );
						novoProdutoSemanal.setQuantidadeSemanal(qtdSemanal);
					
						
						produtoDAO.inserirProdSemanal(novoProdutoSemanal);
			
						novaQuantidadeSemanal.setCodSemanal( tfCodigo.getText() );
						novaQuantidadeSemanal.setDescricaoSemanal( tfDesc.getText() );
						
						produtoDAO.adicionarQtdProdSemanal(novaQuantidadeSemanal);

						produtoDAO.descontarProdutos(produtoDescontado);
				
					
						qtdSemanal= qtdSemanal+1.0;
					}else {
						
						
						
						novoProdutoSemanal.setDescricaoSemanal( tfDesc.getText() );
						novoProdutoSemanal.setPrecoSemanal(Double.parseDouble(tfPreco.getText()) );
						novoProdutoSemanal.setQuantidadeSemanal(qtdSemanal);
						
						produtoDAO.inserirProdSemanal(novoProdutoSemanal);
						atualizarQuantidadeSemanal.setCodSemanal(tfCodigo.getText());
					
						
						novaQuantidadeSemanal.setCodSemanal( tfCodigo.getText() );
						novaQuantidadeSemanal.setDescricaoSemanal( tfDesc.getText() );
					
						produtoDAO.adicionarQtdProdSemanal(novaQuantidadeSemanal);
						
						produtoDAO.descontarProdutos(produtoDescontado);
						
						
						
					}
					
					//Atualiza a tabela
					this.prepararPesquisa();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("A��o Conclu�da");
					alert.setHeaderText("Produto adicionado � tabela semanal!");
					alert.showAndWait();
				}
				
				this.ajustarBotoesInicial();
				this.desabilitarCampos();
				this.prepararPesquisa();
				
			
			}

			// Se a opera��o for alterar
			if(this.getOperacao().equals("alterar")) {
				int resposta = JOptionPane.showConfirmDialog(null, "Confirma a altera��o?", "Aten��o!", JOptionPane.YES_NO_OPTION);
				
				if (resposta == JOptionPane.YES_OPTION) {

				// Cria um objeto agente para receber os dados lidos pelo formul�rio
				Produto alteracaoProduto = new Produto();

			
				// Obt�m o objeto selecionado na tabela
				ProdutosTableViewModel produtoSelecionado = tvProdutos.getSelectionModel().getSelectedItem();

				// Obt�m o c�digo do agente a ser alterado
				alteracaoProduto.setCod(produtoSelecionado.getpCodigo());
				alteracaoProduto.setDescricao( tfDesc.getText() );
				alteracaoProduto.setPreco( Double.parseDouble(tfPreco.getText()) );
				alteracaoProduto.setQuantidadeTotal( Double.parseDouble(tfQuantidade.getText()) );		
			
			

				// Invoca o m�todo alterar do objeto agenteDAO
				produtoDAO.alterar(alteracaoProduto);
				this.prepararPesquisa();
				// Atualiza a tabela
				//this.atualizarTv();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("A��o Conclu�da");
				alert.setHeaderText("Produto alterado com sucesso!");
				alert.showAndWait();
				
				}
				//this.prepararPesquisa();
				//tvProdutos.refresh();
				this.limparCampos();
				this.ajustarBotoesInicial();
				this.habilitarCampos();
			
			}

			if(this.getOperacao().equals("excluir")) {

				int resposta = JOptionPane.showConfirmDialog(null, "Confirma a exclus�o?", "Aten��o!", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {

					// Cria um objeto Agente para receber os dados lidos pelo formul�rio
					Produto exclusaoProduto = new Produto();

					// Obt�m o objeto selecionado na tabela
					ProdutosTableViewModel produtoSelecionado = tvProdutos.getSelectionModel().getSelectedItem();

					// Obt�m o c�digo do agente a ser excluido
					exclusaoProduto.setCod(produtoSelecionado.getpCodigo());

					// Invoca o m�todo excluir do objeto agenteDAO 
					produtoDAO.excluir(exclusaoProduto);
					this.prepararPesquisa();
					// Atualiza a tabela
					//this.atualizarTv();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("A��o Conclu�da");
					alert.setHeaderText("Produto excluido desta tabela!");
					alert.showAndWait();
					
				}
				
				// Ajusta os campos e bot�es para a pr�xima opera��o
				this.limparCampos();
				this.ajustarBotoesInicial();
				this.desabilitarCampos();
				this.prepararPesquisa();
			}

		} catch (IOException e) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Ops!");
			alert.setHeaderText("Opera��o n�o pode ser realizada");
			// Existem dois erros poss�veis que gerar�o uma IOException.
			// Ser� exibida a mensagem definida na cria��o da exception
			alert.setContentText(e.getMessage());
			alert.showAndWait();				

		} catch (Exception e) {
			// A exce��o ocorre quando uma exception diferente das tratadas acima � gerada.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Opera��o n�o realizada");
			alert.setHeaderText("Ocorreu um problema no processo de entrada");
			alert.setContentText("Favor entrar em contato com os desenvolvedores!");
			alert.showAndWait();
			// Detalhes do erro imprevisto
			e.printStackTrace();
		}				


	}


	@FXML public void cancelar() {

		// Ajusta os bot�es para a pr�xima opera��o
		this.ajustarBotoesInicial();
		this.limparCampos();
		this.desabilitarCampos();
	}


	@FXML public void fechar() {
		this.getPalcoProdutos().close();
	}


	// M�todos internos para as funcionalidades do formul�rio

	// Carrega os dados do objeto selecionado na tabela nos campos do formul�rio
	// Este m�todo � invocado a cada clique do mouse e a cada usuario pelo teclado na tabela
	




	// Ajustes de bot�es e campos

	public void ajustarBotoesInicial() {	
		bInserir.setVisible(true);
		bAlterar.setVisible(true);
		bExcluir.setVisible(true);
		bConfirmar.setVisible(false);
		bCancelar.setVisible(false);
		lFechar.setVisible(true);
		bInserir.setDisable(false);
		bAlterar.setDisable(false);
		bExcluir.setDisable(false);
	}

	public void ajustarBotoesInserir() {
		bInserir.setVisible(true);
		bInserir.setDisable(true);
		bAlterar.setVisible(false);
		bExcluir.setVisible(false);
		bConfirmar.setVisible(true);
		bCancelar.setVisible(true);
		lFechar.setVisible(false);
	}

	public void ajustarBotoesAlterar() {
		bInserir.setVisible(false);
		bAlterar.setVisible(true);
		bAlterar.setDisable(true);
		bExcluir.setVisible(false);
		bConfirmar.setVisible(true);
		bCancelar.setVisible(true);
		lFechar.setVisible(false);
	}

	public void ajustarBotoesExcluir() {
		bInserir.setVisible(false);
		bAlterar.setVisible(false);
		bExcluir.setVisible(true);
		bExcluir.setDisable(true);
		bConfirmar.setVisible(true);
		bCancelar.setVisible(true);
		lFechar.setVisible(false);
	}

	public void habilitarCampos() {
		tfCodigo.setDisable(true);
		tfDesc.setDisable(false);
		tfPreco.setDisable(false);			
		tfQuantidade.setDisable(true);
		
		tfQuantidade.setOpacity(0.8);
		tfCodigo.setOpacity(0.8);
		tfDesc.setOpacity(1);
		tfPreco.setOpacity(1);			
		
		
		tfPesquisa.requestFocus();
	}
	
	public void desabilitarCampos() {
		tfCodigo.setDisable(true);
		tfDesc.setDisable(true);
		tfPreco.setDisable(true);			
		tfQuantidade.setDisable(true);
		
		tfQuantidade.setOpacity(0.8);
		tfCodigo.setOpacity(0.8);
		tfDesc.setOpacity(0.8);
		tfPreco.setOpacity(0.8);			
		
	}
	
	public void limparCampos() {

	
		tfCodigo.clear();
		tfDesc.clear();
		tfPreco.clear();
		tfPesquisa.clear();
		tfPesquisa.requestFocus();
	}

}

