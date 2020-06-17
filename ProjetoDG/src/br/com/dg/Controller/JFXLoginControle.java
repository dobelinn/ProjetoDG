package br.com.dg.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.dg.Model.Usuario;
import br.com.dg.Model.Dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class JFXLoginControle implements Initializable {


		// TODO Auto-generated method stub

		// Objeto para armazenar os dados do usuário
		private Usuario usuario = new Usuario();	
		// Objeto DAO para manipular usuários no BD
		private UsuarioDAO usuarioDAO = new UsuarioDAO();

		// Atributo
		private Stage palcoLogin;

		// Métodos de acesso ao atributo
		public void setPalcoLogin(Stage palcoLogin) {
			this.palcoLogin = palcoLogin;
		}	
		public Stage getPalcoLogin() {
			return palcoLogin;
		}
	

		@FXML Button bEntrar;
		@FXML TextField tfLogin;
		@FXML PasswordField pfSenha;
		@FXML Button bSair;

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			
			tfLogin.requestFocus();
		}
		
		
		@FXML public void entrar() throws IOException {

			// Armazena o login e senha informados no formulário
			usuario.setLogin( tfLogin.getText() ); 
			usuario.setSenha( pfSenha.getText() );

			// Situação inicial da autenticação
			usuario.setNome("Usuário não identificado!");

			// Executa a autenticação
			usuario = usuarioDAO.autenticar(usuario);
			
			// Verifica o retorno da autenticação
			// Se o nome do usuário continuar como na situação inicial
			if(usuario.getNome().equals("Usuário não identificado!")) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Acesso negado!");
				alert.setHeaderText(usuario.getNome());
				alert.setContentText("Login ou senha incorreto!");
				alert.showAndWait();

			} else {	

				// Se a autenticação for confirmada invoca o formulário principal

				// Cria um novo palco
				Stage stage = new Stage();
				// Criação de um objeto FMXLLoader para carregar o arquivo fxml (layout)
				FXMLLoader loader = new FXMLLoader();
				// Carregamento o arquivo fxml
				loader.setLocation(getClass().getResource("/br/com/dg/view/JFXProdutosLayout.fxml"));
				// Criação do Layout Pane (gerenciador de layout) que será o node root (nó ou componente raiz)
				// recebendo o layout (arquivo fxml)
				Pane node =  loader.load();
				// Atribuição do componente raiz (e do layout) a cena
				Scene scene = new Scene(node);
				// Atribuição da cena ao palco
				stage.setScene(scene);

				// O objeto loader possui a referência da classe JFXPrincipalControle 
				JFXProdutosControle produtosControle = loader.getController();	
				// E acesso a seus métodos.
				// A referência do palco criado é passada para posterior acesso
				produtosControle.setPalcoProdutos(stage);
				// O objeto do usuario logado é passado para o JFXPrincipalControle
				
				

				// Retira a barra superior da janela (icone, titulo, minimizar, maximizar e fechar)
				stage.initStyle(StageStyle.UNDECORATED);
				// Não permite o redimensionamento
				stage.setResizable(false);
				// Centraliza a apresentação
				
				
				// Alinha o formulário no topo da tela
				stage.setX(0);
				stage.setY(0);
				// Ajusta a largura do formulário a largura da tela
				stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth()); 	
				
				// Apresenta o formulário
				stage.show();

				// Fecha esse formulário
				this.getPalcoLogin().close();
			}

		}

		@FXML public void sair() {
			System.exit(0);
		}
	}


