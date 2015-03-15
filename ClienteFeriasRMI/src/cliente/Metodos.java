package cliente;
import java.io.File;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface Metodos extends Remote{
	
	public byte[] compressArchive() throws RemoteException, IOException;
	
	public String digaOla() throws RemoteException;

	public boolean UploadFiles(byte[] array, String string ) throws RemoteException;

	public void UploadForCompact(byte[] fileToByte, String name) throws RemoteException;

}
