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

		// Objeto para armazenar os dados do usu�rio
		private Usuario usuario = new Usuario();	
		// Objeto DAO para manipular usu�rios no BD
		private UsuarioDAO usuarioDAO = new UsuarioDAO();

		// Atributo
		private Stage palcoLogin;

		// M�todos de acesso ao atributo
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

			// Armazena o login e senha informados no formul�rio
			usuario.setLogin( tfLogin.getText() ); 
			usuario.setSenha( pfSenha.getText() );

			// Situa��o inicial da autentica��o
			usuario.setNome("Usu�rio n�o identificado!");

			// Executa a autentica��o
			usuario = usuarioDAO.autenticar(usuario);
			
			// Verifica o retorno da autentica��o
			// Se o nome do usu�rio continuar como na situa��o inicial
			if(usuario.getNome().equals("Usu�rio n�o identificado!")) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Acesso negado!");
				alert.setHeaderText(usuario.getNome());
				alert.setContentText("Login ou senha incorreto!");
				alert.showAndWait();

			} else {	

				// Se a autentica��o for confirmada invoca o formul�rio principal

				// Cria um novo palco
				Stage stage = new Stage();
				// Cria��o de um objeto FMXLLoader para carregar o arquivo fxml (layout)
				FXMLLoader loader = new FXMLLoader();
				// Carregamento o arquivo fxml
				loader.setLocation(getClass().getResource("/br/com/dg/view/JFXProdutosLayout.fxml"));
				// Cria��o do Layout Pane (gerenciador de layout) que ser� o node root (n� ou componente raiz)
				// recebendo o layout (arquivo fxml)
				Pane node =  loader.load();
				// Atribui��o do componente raiz (e do layout) a cena
				Scene scene = new Scene(node);
				// Atribui��o da cena ao palco
				stage.setScene(scene);

				// O objeto loader possui a refer�ncia da classe JFXPrincipalControle 
				JFXProdutosControle produtosControle = loader.getController();	
				// E acesso a seus m�todos.
				// A refer�ncia do palco criado � passada para posterior acesso
				produtosControle.setPalcoProdutos(stage);
				// O objeto do usuario logado � passado para o JFXPrincipalControle
				
				

				// Retira a barra superior da janela (icone, titulo, minimizar, maximizar e fechar)
				stage.initStyle(StageStyle.UNDECORATED);
				// N�o permite o redimensionamento
				stage.setResizable(false);
				// Centraliza a apresenta��o
				
				
				// Alinha o formul�rio no topo da tela
				stage.setX(0);
				stage.setY(0);
				// Ajusta a largura do formul�rio a largura da tela
				stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth()); 	
				
				// Apresenta o formul�rio
				stage.show();

				// Fecha esse formul�rio
				this.getPalcoLogin().close();
			}

		}

		@FXML public void sair() {
			System.exit(0);
		}
	}


