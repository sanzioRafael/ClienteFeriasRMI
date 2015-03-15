package cliente;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.sql.Savepoint;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

public class ConnectedClient {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnCompact;

	@FXML
	private Button btnEnterChat;

	@FXML
	private Button btnSelectArchiveToSend;

	@FXML
	private Button btnSelectCompactArchive;

	@FXML
	private Button btnSelectRedVideo;

	@FXML
	private Button btnSelectVideo;

	@FXML
	private Button btnSendArchive;

	@FXML
	private Button btnSendRedVideo;

	@FXML
	private Button btnSendVideo;

	@FXML
	private Tab tabChat;

	@FXML
	private Tab tabCompactArchive;

	@FXML
	private Tab tabRedVideo;

	@FXML
	private Tab tabSendArchive;

	@FXML
	private Tab tabSendVideo;

	@FXML
	private TextField tfChatName;

	@FXML
	private TextField tfSendArchive;

	@FXML
	private TextField tfCompactFiles;

	FileChooser chooser;
	List<File> files;

	@FXML
	void SendArchiveOnAction(ActionEvent event) {
		String path = tfSendArchive.getText();
		File file = new File(path);

		try {
			System.out.println(stub.digaOla());
			boolean status = stub.UploadFiles(fileToByte(file), file.getName());
			if (status) {
				JOptionPane.showMessageDialog(null,	"Arquivo enviado com sucesso");
			} else {
				JOptionPane.showMessageDialog(null, "Falha ao enviar arquivo");
			}
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void btnSelectArchiveToCompactOnAction(ActionEvent event) {
		chooser = new FileChooser();
		chooser.setTitle("Arquivos para compactar");
		files = chooser.showOpenMultipleDialog(null);
		String aux = null;

		tfCompactFiles.setText(aux);
	}

	@FXML
	void btnCompactOnAction(ActionEvent event) throws IOException {
		try {
			for (File file : files) {
				stub.UploadForCompact(fileToByte(file), file.getName());
			}
			stub.compressArchive();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, "Falha ao enviar arquivo");
		}

	}

	@FXML
	void btnEnterChatOnAction(ActionEvent event) {
	}

	@FXML
	void btnSelectArchiveToSendOnAction(ActionEvent event) {
		chooser = new FileChooser();
		chooser.setTitle("Fazer Upload");
		File selectedDirectory = chooser.showOpenDialog(null);

		if (selectedDirectory != null)
			tfSendArchive.setText(selectedDirectory.getPath());
	}

	@FXML
	void btnSelectRedVideoOnAction(ActionEvent event) {
	}

	@FXML
	void btnSelectVideoOnAction(ActionEvent event) {
	}

	@FXML
	void btnSendRedVideoOnAction(ActionEvent event) {
	}

	@FXML
	void btnSendVideoOnAction(ActionEvent event) {
	}

	Metodos stub = Client.stub;
	Registry registry = Client.registry;
	FileInputStream fis;
	FileOutputStream fos;

	@FXML
	void initialize() {
	}

	public void start(Stage stage) throws Exception {
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass()
					.getResource("Interface.fxml"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.getIcons().add(new Image("img/Filesystem-socket-icon-big.png"));
			stage.show();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public byte[] fileToByte(File file) {
		FileInputStream fileInputStream = null;

		byte[] bFile = new byte[(int) file.length()];

		try {
			// convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

			for (int i = 0; i < bFile.length; i++) {
				System.out.print((char) bFile[i]);
			}

			System.out.println("Done");
			return bFile;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void byteToFile(String path, byte[] bytes) {
		try {
			fos = new FileOutputStream(new File(path));
			fos.write(bytes);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
