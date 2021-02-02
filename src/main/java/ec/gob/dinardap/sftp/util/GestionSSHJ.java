package ec.gob.dinardap.sftp.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.UserAuthException;
import net.schmizz.sshj.xfer.InMemorySourceFile;

public class GestionSSHJ {

	private static SSHClient sshClient;
	private static SFTPClient sftpClient;
	private static SSHClient client;
	
	private static SSHClient establecerConexion(CredencialesSFTP credenciales) {
		try {
			client = new SSHClient();
			client.addHostKeyVerifier(new PromiscuousVerifier());
			client.connect(credenciales.getHost());
			client.authPassword(credenciales.getUsuario(), credenciales.getContrasena());
			return client;
		} catch (UserAuthException e) {
			// TODO Auto-generated catch block
			System.out.println("Error autenticacion");
			e.printStackTrace();
			return null;
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private static String verificarDirectorioExiste(String rutaDestino) {
        try {
        	if(rutaDestino != null && sftpClient.statExistence(rutaDestino) == null) 
        		sftpClient.mkdirs(rutaDestino);
			return rutaDestino;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static void subirArchivo(byte[] contenido, CredencialesSFTP credenciales) throws IOException {
	    sshClient = establecerConexion(credenciales);
	    sftpClient = sshClient.newSFTPClient();
	    String nombre = credenciales.getDirDestino().split("/")[credenciales.getDirDestino().split("/").length - 1];
	    String rutaDestino = credenciales.getDirDestino().replace(credenciales.getDirDestino().split("/")[credenciales.getDirDestino().split("/").length - 1],"");
	    rutaDestino = verificarDirectorioExiste(rutaDestino);
	    if(rutaDestino != null) {
		    sftpClient.put(new InMemorySourceFile() {
				@Override
				public String getName() {
					return nombre;
				}
				
				@Override
				public long getLength() {
					return contenido.length;
				}
				
				@Override
				public InputStream getInputStream() throws IOException {
					return new ByteArrayInputStream(contenido);
				}
			}, rutaDestino);
		 
		    sftpClient.close();
		    sshClient.disconnect();
		}
	}

	public static boolean descargarArchivo(CredencialesSFTP credenciales) {
		try {
			sshClient = establecerConexion(credenciales);
			sftpClient = sshClient.newSFTPClient();
			sftpClient.get(credenciales.getDirOrigen(), credenciales.getDirDestino());
			sftpClient.close();
			sshClient.disconnect();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
