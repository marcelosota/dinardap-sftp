package ec.gob.dinardap.sftp.util;

public class CredencialesSFTP {
	private String usuario;
	private String contrasena;
	private String host; 
	private int puerto;
	private String dirDestino;
	private String dirOrigen;
		
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPuerto() {
		return puerto;
	}
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
	public String getDirDestino() {
		return dirDestino;
	}
	public void setDirDestino(String dirDestino) {
		this.dirDestino = dirDestino;
	}
	public String getDirOrigen() {
		return dirOrigen;
	}
	public void setDirOrigen(String dirOrigen) {
		this.dirOrigen = dirOrigen;
	}
	
	
}
