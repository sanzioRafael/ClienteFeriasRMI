package cliente;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

public class Client extends Application {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnConnect;

	@FXML
	private TextField tfServerAddress;

	@FXML
	private TextField tfServerPort;

	public static Registry registry;

	Stage stage;
	static Metodos stub;

	@FXML
	void btnConnectOnAction(ActionEvent event) {

		if (verifyTextField()) {
			try {
				registry = LocateRegistry.getRegistry(
						tfServerAddress.getText(),
						Integer.parseInt(tfServerPort.getText()));
				stub = (Metodos) registry.lookup("server");
				try {
					System.setSecurityManager(new RMISecurityManager());
					Stage stage = new Stage();
					AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass()
							.getResource("ConnectedInterface.fxml"));
					Scene scene = new Scene(pane);
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (RemoteException | NotBoundException e) {
				JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
			}
		}
	}

	private boolean verifyTextField() {
		String address = tfServerAddress.getText();
		String port = tfServerPort.getText();

		if ((address.length() == 0) || (port.length() == 0)) {
			tfServerAddress.requestFocus();
			return false;
		} else {
			return true;
		}

	}

	@FXML
	void initialize() {
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			this.stage = stage;
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass()
					.getResource("Interface.fxml"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.getIcons().add(
					new Image("img/Filesystem-socket-icon-big.png"));
			stage.show();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
