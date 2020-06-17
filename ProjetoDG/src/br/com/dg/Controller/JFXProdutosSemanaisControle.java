package br.com.dg.Controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.dg.Model.Produto;
import br.com.dg.Model.Dao.GerenciadorConexoes;
import br.com.dg.Model.Dao.ProdutoDAO;
import br.com.dg.Model.TableView.ProdutosTableViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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

public class JFXProdutosSemanaisControle implements Initializable {
	private Stage palcoProdutosSemanais;
	private ArrayList<ProdutosTableViewModel> listaProdSemanalTvModel = new ArrayList<ProdutosTableViewModel>(); 
	private ArrayList<Produto> listaProdutos = new ArrayList<Produto>();
	private String operacao;
	private final ObservableList<ProdutosTableViewModel> dataList = FXCollections.observableArrayList();
	ProdutoDAO produtoDAO = new ProdutoDAO();
	int i = 0;
	
	 Document document = new Document();
	@FXML Button bAlterar;
	@FXML DatePicker dpInicio;
	@FXML Button bExcluir;
	@FXML Button bConfirmar;
	@FXML Button bCancelar;
	@FXML DatePicker dpTermino;
	@FXML Label lFechar;
	@FXML TextField tfPreco;
	@FXML TextField tfCodigo;
	@FXML TableColumn<ProdutosTableViewModel, String> colPreco;
	@FXML TableColumn<ProdutosTableViewModel, String> colDescBanco;
	@FXML TableColumn<ProdutosTableViewModel, String> colCod;
	@FXML TableView<ProdutosTableViewModel> tvProdutosSemanais;
	@FXML TextField tfDesc;
	@FXML Button bVoltar;
	@FXML ImageView bAtualizar;
	@FXML ImageView bGerarPdf;	
	@FXML Button bBaixarPDF;
	@FXML TextField tfQuantidadeVendaSemanal;
	@FXML TableColumn<ProdutosTableViewModel, String> colQuantidade;
	
	public Stage getPalcoProdutosSemanais() {
		return palcoProdutosSemanais;
	}

	public void setPalcoProdutosSemanais(Stage palcoProdutosSemanais) {
		this.palcoProdutosSemanais = palcoProdutosSemanais;
	}
	
	public ArrayList<ProdutosTableViewModel> getListaProdSemanalTvModel() {
		return listaProdSemanalTvModel;
	}
	
	public void setListaProdSemanalTvModel(ProdutosTableViewModel produtoTvModel) {
		this.listaProdSemanalTvModel.add(produtoTvModel);
	}
	
	public void setListaProdSemanalTvModel(ArrayList<ProdutosTableViewModel> listaProdSemanalTvModel) {
		this.listaProdSemanalTvModel.addAll(listaProdSemanalTvModel);
	}


	public ArrayList<Produto> getListaProdutos() {
		return listaProdutos;
	}

	// Sobrecarga do m�todo setListaProfessores
	public void setListaProdutos(Produto produto) {
		this.listaProdutos.add(produto);
	}
	public void setListaProdutos(ArrayList<Produto> listaProdutos) {
		this.listaProdutos.addAll(listaProdutos);
	}

	private String getOperacao() {
		return operacao;
	}

	private void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public void carregarTv(){
		
		// Sincroniza o modelo do tvUsuarios com o Banco de Dados
		this.carregaProdutosSemanaisTableViewModel();
		
		// Vincula o atributo listaUsuarios como fonte de dados para a tabela
		tvProdutosSemanais.setItems(FXCollections.observableArrayList(this.getListaProdSemanalTvModel()));

		// Atualiza visualiza��o dos dados 
		tvProdutosSemanais.refresh();
	}
	public void prepararTabela() {
		dataList.addAll(this.getListaProdSemanalTvModel());
		
        
		dataList.removeAll(dataList);
		
		this.carregarTv();

		//tfPesquisa.requestFocus();
		dataList.addAll(this.getListaProdSemanalTvModel());
		// Preenche o TableView com os Usuarios armazenados no banco de dados		
		//this.atualizarTableView();
		 // Wrap the ObservableList in a FilteredList (initially display all data).
   

	// Funcionalidades do formul�rio
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		

		
		dpInicio.setValue(LocalDate.now());
		dpTermino.setValue(LocalDate.now());

		colCod.setCellValueFactory( new PropertyValueFactory<>("pCodigo"));
		colDescBanco.setCellValueFactory(  new PropertyValueFactory<>( "pDescricao" ) );		
		colPreco.setCellValueFactory( new PropertyValueFactory<>("pPreco") );
		colQuantidade.setCellValueFactory( new PropertyValueFactory<>("pQuantidadeSemanal") );
		

		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		/*colInicio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DisciplinasTableViewModel,String>, ObservableValue<String>>() {
			
            public ObservableValue<String> call(CellDataFeatures<DisciplinasTableViewModel, String> cell) {
                final DisciplinasTableViewModel disciplinasTvModel = cell.getValue();
                final SimpleObjectProperty<String> dataInicioFormatada = new SimpleObjectProperty<String>(formatador.format(disciplinasTvModel.getpInicio()));
            return dataInicioFormatada;
            }        
        });

		colTermino.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DisciplinasTableViewModel,String>, ObservableValue<String>>() {
			
            public ObservableValue<String> call(CellDataFeatures<DisciplinasTableViewModel, String> cell) {
                final DisciplinasTableViewModel disciplinasTvModel = cell.getValue();
                final SimpleObjectProperty<String> dataTerminoFormatada = new SimpleObjectProperty<String>(formatador.format(disciplinasTvModel.getpTermino()));
            return dataTerminoFormatada;
            }        
        });
        */
		this.carregarTv();
		bConfirmar.setVisible(false);
		bCancelar.setVisible(false);
		tfDesc.requestFocus();
	}
	public ArrayList<Produto> listarProdutosSemanais(){		
		
		// Declara��o de refer�ncias JDBC
		Connection con = null; 
		ResultSet rs = null;	
		Statement stmt = null;
		
		// Declara��o de vari�vel
		String sql = "";
				
		// Declara��o do ArrayList que receber� o retorno do Select
		ArrayList<Produto> listaProdutosSemanais = new ArrayList<Produto>();
		
		try {
			con = GerenciadorConexoes.criarConexao();
			stmt = con.createStatement();
			// Defini��o da String SQL
			
			sql = "select codprodutoSemanal,descprodutoSemanal,valorprodutoSemanal,qtdSemana from produtosSemana";
			      
			   
			
			rs = stmt.executeQuery(sql);

			while(rs.next()){
				i++;
				// Objeto que receber� cada usuario encontrado (cada linha do ResultSet)
				Produto umProdutoSemanal = new Produto();
				// Popula o objeto com os dados do ResultSet

				umProdutoSemanal.setDescricao(rs.getString("descprodutoSemanal"));
				umProdutoSemanal.setCod(rs.getString("codprodutoSemanal"));
				umProdutoSemanal.setPreco(Double.parseDouble(rs.getString("valorprodutoSemanal")));
				umProdutoSemanal.setQuantidadeSemanal(Double.parseDouble(rs.getString("qtdSemana")));
				
				// Armazena o objeto no ArrayList
				listaProdutosSemanais.add(umProdutoSemanal);
				// Elimina a refer�ncia do objeto para ser reutilizado no pr�ximo registro
				umProdutoSemanal = null;
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
		return listaProdutosSemanais;
	}

	public ArrayList<ProdutosTableViewModel> carregaProdutosSemanaisTableViewModel(){
		
		this.getListaProdSemanalTvModel().clear();
		
		// Percorre o ArrayList de Usuarios retornado pelo m�todo listarusuarios do objeto usuarioDAO
		// carregando um ArrayList de usuarioTableViewModel (modelo da TableView) 
		for (Produto produto : this.listarProdutosSemanais()) {
			
			ProdutosTableViewModel umProdutoTvModel = new ProdutosTableViewModel();
			umProdutoTvModel.setpCodigo(produto.getCod());
			umProdutoTvModel.setpDescricao(produto.getDescricao());
			umProdutoTvModel.setpPreco(produto.getPreco());
			umProdutoTvModel.setpQuantidadeSemanal(produto.getQuantidadeSemanal());
			
			
			
	
			this.setListaProdSemanalTvModel(umProdutoTvModel);
		}	
		return this.getListaProdSemanalTvModel();	 
	}
	
	@FXML public void carregaDescDados() {
		if(tvProdutosSemanais.getSelectionModel().getSelectedIndex() < 0) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Opera��o inv�lida");
			alert.setHeaderText("Detalhamento:");
			alert.setContentText( "N�o h� produtos selecionados" );
			alert.showAndWait();

		}else {

			// Obt�m o objeto selecionado na tabela
			ProdutosTableViewModel produtoSemanalTvModelSelecionado = tvProdutosSemanais.getSelectionModel().getSelectedItem();

			tfCodigo.setText(String.valueOf(produtoSemanalTvModelSelecionado.getpCodigo()));
			tfDesc.setText(produtoSemanalTvModelSelecionado.getpDescricao());
			tfPreco.setText(String.valueOf(produtoSemanalTvModelSelecionado.getpPreco()));
			tfQuantidadeVendaSemanal.setText(String.valueOf(produtoSemanalTvModelSelecionado.getpQuantidadeSemanal()));
			
		}
	
	}

	@FXML public void reloadTabela() {
		this.prepararTabela();	
	}


	@FXML public void voltar() throws IOException {
				this.palcoProdutosSemanais.close();
				// Cria um novo palco
				Stage stage = new Stage();
				// Objeto FMXLLoader que carrega o arquivo fxml
				FXMLLoader loader = new FXMLLoader();
				// Carregamento o arquivo fxml
				loader.setLocation(getClass().getResource("/br/com/dg/view/JFXProdutosLayout.fxml"));
				// Cria��o do Layout Pane (gerenciador de layout), que ser� o node/n�/componente raiz, e vinculo com o arquivo fxml
				Pane node =  loader.load();
				// Atribui��o do componente a cena
				Scene scene = new Scene(node);
				// Atribui��o da cena ao novo palco
				stage.setScene(scene);

				// O objeto loader possui a refer�ncia da classe JFXusuariosControle 
				JFXProdutosControle produtosControle = loader.getController();	
				// E acesso a seus m�todos.
				// A refer�ncia do palco criado � passada para posterior acesso (fechamento)
				produtosControle.setPalcoProdutos(stage);
				
				// Retira a barra superior da janela (icone, titulo, minimizar, maximizar e fechar)
				stage.initStyle(StageStyle.UNDECORATED);
				// N�o permite o redimensionamento
				stage.setResizable(false);
				// Centraliza a apresenta��o
				stage.centerOnScreen();
				// Define o comportamento Modal (bloqueia os demais formul�rios enquanto ele estiver aberto)
				stage.initModality(Modality.WINDOW_MODAL);

				// Apresenta o formul�rio
				stage.show();				
	}


	@FXML public void alterar() {

		try {

			// Verifica se nenhum usuario foi selecionado
			if(tvProdutosSemanais.getSelectionModel().getSelectedIndex() < 0) {
				throw new IOException();
			}

			this.habilitarCampos();
			this.ajustarBotoesAlterar();
			tfDesc.requestFocus();
	
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
		if(tvProdutosSemanais.getSelectionModel().getSelectedIndex() >= 0) {
			
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

	
	@FXML public void confirmar() {

		try {

			// Verifica campos obrigat�rios
			if (tfCodigo.getText().isEmpty() ||
				tfDesc.getText().isEmpty() ||   
				tfPreco.getText().isEmpty()) {

				// Define a mensagem de erro
				throw new IOException("Todos os campos s�o obrigat�rios.");	
			}

			

			// Se a opera��o for alterar
			if(this.getOperacao().equals("alterar")) {
				int resposta = JOptionPane.showConfirmDialog(null, "Confirma a altera��o?", "Aten��o!", JOptionPane.YES_NO_OPTION);
				
				if (resposta == JOptionPane.YES_OPTION) {

				// Cria um objeto agente para receber os dados lidos pelo formul�rio
				Produto alteracaoProdutoSemanal = new Produto();

			
				// Obt�m o objeto selecionado na tabela
				ProdutosTableViewModel produtoSelecionado = tvProdutosSemanais.getSelectionModel().getSelectedItem();

				// Obt�m o c�digo do agente a ser alterado
				alteracaoProdutoSemanal.setCod(produtoSelecionado.getpCodigo());
				alteracaoProdutoSemanal.setDescricao( tfDesc.getText() );
				alteracaoProdutoSemanal.setPreco( Double.parseDouble(tfPreco.getText()) );				
			
			

				// Invoca o m�todo alterar do objeto agenteDAO
				produtoDAO.alterarProdSemanal(alteracaoProdutoSemanal);
				
				// Atualiza a tabela
				this.atualizarTableView();
		
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
					Produto exclusaoProdutoSemanal = new Produto();

					// Obt�m o objeto selecionado na tabela
					ProdutosTableViewModel produtoSelecionado = tvProdutosSemanais.getSelectionModel().getSelectedItem();

					// Obt�m o c�digo do agente a ser excluido
					exclusaoProdutoSemanal.setCod(produtoSelecionado.getpCodigo());

					// Invoca o m�todo excluir do objeto agenteDAO 
					produtoDAO.excluirProdSemanal(exclusaoProdutoSemanal);
					//this.prepararPesquisa();
					// Atualiza a tabela
					this.atualizarTableView();;
					
					
				}
				
				// Ajusta os campos e bot�es para a pr�xima opera��o
				this.limparCampos();
				this.ajustarBotoesInicial();
				this.desabilitarCampos();
				//this.prepararPesquisa();
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
		this.getPalcoProdutosSemanais().close();
	}


	// M�todos internos para as funcionalidades do formul�rio

	// Carrega os dados do objeto selecionado na tabela nos campos do formul�rio
	// Este m�todo � invocado a cada clique do mouse e a cada disciplina pelo teclado na tabela
	@FXML public void carregarDados() {

		if(tvProdutosSemanais.getSelectionModel().getSelectedIndex() < 0) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Opera��o inv�lida");
		alert.setHeaderText("Detalhamento:");
		alert.setContentText( "N�o h� produtos selecionados" );
		alert.showAndWait();

	}else {

		// Obt�m o objeto selecionado na tabela
		ProdutosTableViewModel produtoTvModelSelecionado = tvProdutosSemanais.getSelectionModel().getSelectedItem();

		tfCodigo.setText(String.valueOf(produtoTvModelSelecionado.getpCodigo()));
		tfDesc.setText(produtoTvModelSelecionado.getpDescricao());
		tfPreco.setText(String.valueOf(produtoTvModelSelecionado.getpPreco()));
		tfQuantidadeVendaSemanal.setText(String.valueOf(produtoTvModelSelecionado.getpPreco()));
		
	}
	}


	public void atualizarTableView() {
		// Vincula o atributo listaDisciplinas como fonte de dados para a tabela
		tvProdutosSemanais.setItems(FXCollections.observableArrayList(this.getListaProdSemanalTvModel()));
		tvProdutosSemanais.refresh();
	}
	

    
        
    
	
		 
		


	// Ajustes de bot�es e campos

	public void ajustarBotoesInicial() {	
		bAlterar.setVisible(true);
		bExcluir.setVisible(true);
		bConfirmar.setVisible(false);
		bCancelar.setVisible(false);
		lFechar.setVisible(true);
		bAlterar.setDisable(false);
		bExcluir.setDisable(false);
	}



	public void ajustarBotoesAlterar() {
	
		bAlterar.setVisible(true);
		bAlterar.setDisable(true);
		bExcluir.setVisible(false);
		bConfirmar.setVisible(true);
		bCancelar.setVisible(true);
		lFechar.setVisible(false);
	}

	public void ajustarBotoesExcluir() {
		bAlterar.setVisible(false);
		bExcluir.setVisible(true);
		bExcluir.setDisable(true);
		bConfirmar.setVisible(true);
		bCancelar.setVisible(true);
		lFechar.setVisible(false);
	}

	public void habilitarCampos() {
		tfDesc.setDisable(false);
		tfCodigo.setDisable(true);
		tfPreco.setDisable(false);
		dpInicio.setDisable(true);
		dpTermino.setDisable(true);
		
		tfDesc.setOpacity(1);
		tfCodigo.setOpacity(0.8);
		tfPreco.setOpacity(1);
		dpInicio.setOpacity(0.8);
		dpTermino.setOpacity(0.8);
		
		tfDesc.requestFocus();
	}
	
	public void desabilitarCampos() {
		tfDesc.setDisable(true);
		tfCodigo.setDisable(true);
		tfPreco.setDisable(true);
		dpInicio.setDisable(true);
		dpTermino.setDisable(true);
		
		tfDesc.setOpacity(0.8);
		tfCodigo.setOpacity(0.8);
		tfPreco.setOpacity(0.8);
		dpInicio.setOpacity(0.8);
		dpTermino.setOpacity(0.8);
	}
	
	public void limparCampos() {

		tfDesc.clear();
		tfCodigo.clear();
		tfPreco.clear();
		dpInicio.setValue(LocalDate.now());
		dpTermino.setValue(LocalDate.now());

		tfDesc.requestFocus();
	}
	public void gerarPdf() throws IOException {
	  	int k = 0;
        /*  try {
        	  //document.close();
        	  		document.open();
        		//while( k < i ){
        			 k++;
        			
    				// Objeto que receber� cada usuario encontrado (cada linha do ResultSet)
        			 document.add(new Paragraph(listarProdutos().toString()));
    				// Elimina a refer�ncia do objeto para ser reutilizado no pr�ximo registro
    				
    			//}
              PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\bruno\\OneDrive\\�rea de Trabalho\\pdfs\\PDF_Produtos_Semanais.pdf"));
              //Image figura = Image.getInstance("C:\\Users\\bruno\\OneDrive\\�rea de Trabalho\\aaaaaaaaaa\\aaaaaaaaa\\imagens\\cerebro.jpg");
              //document.add(figura);
              //document.add(new Paragraph(listaProdutos.toString()));
              //document.close();
              /*Image figura = Image.getInstance("C:\\Users\\bruno\\OneDrive\\�rea de Trabalho\\aaaaaaaaaa\\aaaaaaaaa\\imagens\\cerebro.jpg");
              document.add(figura);
          }
          catch(DocumentException de) {
              System.err.println(de.getMessage());
          }
          catch(IOException ioe) {
              System.err.println(ioe.getMessage());
          }
        
          return document;*/
	  	 JFileChooser fileChooser = new JFileChooser();
  		fileChooser.setCurrentDirectory(new java.io.File("."));
  		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
  		File arquivo = new File(fileChooser.getSelectedFile().toString());
         try {
        	 
        	
             PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\bruno\\OneDrive\\�rea de Trabalho\\pdfs\\PDF_TabelaSemanal.pdf"));
             document.open();
             //while( k < i ){
    			 k++;
    			 String a = "    | R$ ";
    			 String b = "    |    ";
    			 Image figura = Image.getInstance("C:\\Users\\bruno\\OneDrive\\�rea de Trabalho\\aaaaaaaaaa\\aaaaaaaaa\\imagens\\bar.jpg");
                 document.add(figura);
				// Objeto que receber� cada usuario encontrado (cada linha do ResultSet)
                 Paragraph espaco = new Paragraph();
                 Paragraph colunas = new Paragraph();
                 espaco.add("-------------------------------------------------------------------------------------------------------------------------------");
                 Paragraph vendas = new Paragraph();
                 //vendas.add("Tabela de Vendas Semanais:");
                 PdfPTable tabela = new PdfPTable(4);
                 tabela.addCell("C�digo do Produto");
                 tabela.addCell("Descri��o do Produto");
                 tabela.addCell("Pre�o do Produto");
                 tabela.addCell("Quantidade");
                 /*colunas.add("C�digo do Produto");
                 colunas.add(b);
                 colunas.add("Descri��o do Produto");
                 colunas.add(b);
                 colunas.add("Pre�o do Produto");*/
                 document.add(vendas);
                 document.add(colunas);
                 document.add(espaco);
    			 Paragraph linhaPDF = new Paragraph();
    			 
    			 for (Produto produto : this.listarProdutosSemanais()) {
    					
    					ProdutosTableViewModel umProdutoTvModel = new ProdutosTableViewModel();
    					umProdutoTvModel.setpCodigo(produto.getCod());
    					umProdutoTvModel.setpDescricao(produto.getDescricao());
    					umProdutoTvModel.setpPreco(produto.getPreco());
    					
    					
    					
    			
    					this.setListaProdSemanalTvModel(umProdutoTvModel);
    					String cod= produto.getCodSemanal();
    					String desc= produto.getDescricaoSemanal();
    				    String preco= String.valueOf(produto.getPrecoSemanal());
    				    
    				    tabela.addCell(cod);
    				    tabela.addCell(desc);
    				    tabela.addCell(preco);
    				    
    				    /*
    					linhaPDF.add(produto.getCod());
    					linhaPDF.add(b);
    					linhaPDF.add(produto.getDescricao());
    					linhaPDF.add(a);
    					linhaPDF.add(Double.toString(produto.getPreco()));
    					linhaPDF.add(espaco);
    					*/
    				}
    			 document.add(tabela);
    			 //document.add(linhaPDF);
			
   }
         catch(DocumentException de) {
             System.err.println(de.getMessage());
         }
         catch(IOException ioe) {
             System.err.println(ioe.getMessage());
         }
        
         Desktop.getDesktop().open(new File("C:\\Users\\bruno\\OneDrive\\�rea de Trabalho\\pdfs\\PDF_TabelaSemanal.pdf"));
         document.close();
      }
	@FXML public void baixarPDF() throws IOException {
		
		//arquivo = fileChooser.getSelectedFile();
			String caminho = "";
			JFileChooser fileChooser = new JFileChooser();
			Paragraph valorTotalSemana = new Paragraph();
	  		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	  		int x = fileChooser.showSaveDialog(fileChooser);
	  		
	  			if(x == fileChooser.APPROVE_OPTION) {
	  				caminho = fileChooser.getSelectedFile().getPath();
	  			}
	         try {
	        	 
	        	
	             PdfWriter.getInstance(document, new FileOutputStream(caminho + "\\PDF_TabelaSemanal.pdf"));
	             document.open();
	             
	    			
	    			 String a = "R$ ";
	    			
	    			 Image figura = Image.getInstance("C:\\Users\\bruno\\OneDrive\\�rea de Trabalho\\aaaaaaaaaa\\aaaaaaaaa\\imagens\\bar.jpg");
	                 document.add(figura);
				
	                
	        
	                 PdfPTable tabela = new PdfPTable(4);
	                 tabela.addCell("C�digo do Produto");
	                 tabela.addCell("Descri��o do Produto");
	                 tabela.addCell("Pre�o do Produto");
	                 tabela.addCell("Quantidade Vendida");
	                 double precoTotal=0;
	    			 double quantidadeTotal=0;
	    			 double valorTotalProduto=0;
	    			 double valorTotalSemanal=0;
	    			 
	    			 for (Produto produto : this.listarProdutosSemanais()) {
	    					
	    					
	    					String cod= produto.getCod();
	    					String desc= produto.getDescricao();
	    				    String preco= a + String.valueOf(produto.getPreco());
	    				    String quantidade = String.valueOf(produto.getQuantidadeSemanal());
	    				    
	    				    
	    				    precoTotal= produto.getPreco();
	    				    quantidadeTotal= produto.getQuantidadeSemanal();
	    				    valorTotalProduto= (quantidadeTotal*precoTotal);
	    				    tabela.addCell(cod);
	    				    tabela.addCell(desc);
	    				    tabela.addCell(preco);
	    				    tabela.addCell(quantidade);
	    				    valorTotalSemanal = valorTotalSemanal + valorTotalProduto;
	    				    
	    				   
	    			 }
	    			 tabela.setWidthPercentage(100);
	    			 //valorTotalSemanal= quantidadeTotal*precoTotal;
	    			 valorTotalSemana.add("----------------------------------------------------------------------------------------------------------------------------------");
	    			 valorTotalSemana.add("Valor Total dos Produtos Vendidos Nesta Semana: ");
	    			 valorTotalSemana.add(a);
 				     valorTotalSemana.add(String.valueOf(valorTotalSemanal));
	    			 document.add(tabela);
	    			 //document.add(linhaPDF);
	    			 
	    			 document.add(valorTotalSemana);
	   }
	         catch(DocumentException de) {
	             System.err.println(de.getMessage());
	         }
	         catch(IOException ioe) {
	             System.err.println(ioe.getMessage());
	         }
	         
	         JOptionPane.showMessageDialog(null, "PDF Gerado e Baixado Com Sucesso! O programa ir� ABRIR O PDF AUTOMATICAMENTE");
	         Desktop.getDesktop().open(new File(caminho + "\\PDF_TabelaSemanal.pdf"));
	         document.close();
	         
	  
	      }
		
	
	@FXML public void gerarPdfSemanal() throws IOException {
		
		
		//arquivo = fileChooser.getSelectedFile();
		//this.gerarPdf();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("A��o Conclu�da");
		alert.setHeaderText("Download Do PDF Conclu�do");
		alert.showAndWait();
	}   
	public List content() {
		
		List lista = new List();
		lista.add(this.getListaProdSemanalTvModel().toString());
		return lista;
	}
	

	

	
	}





