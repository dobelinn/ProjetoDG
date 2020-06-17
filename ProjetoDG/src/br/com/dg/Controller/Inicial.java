package br.com.dg.Controller;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import br.com.dg.Model.Dao.GerenciadorConexoes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Inicial extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			
			GerenciadorConexoes.criarConexao();
			launch(args);
			
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(null, "Verifique os arquivos de conexão com o banco de dados", "SQLException", JOptionPane.ERROR_MESSAGE);			
			e.printStackTrace();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		
		}		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(getClass().getResource("/br/com/dg/view/JFXLoginLayout.fxml"));
		
		Pane nodeRoot =  loader.load();
		Scene scene = new Scene(nodeRoot);
		
		primaryStage.setScene(scene);
		 
		JFXLoginControle loginControle = loader.getController();	
		
		loginControle.setPalcoLogin(primaryStage);
		
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

}
