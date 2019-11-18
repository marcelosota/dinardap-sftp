package ec.gob.dinardap.sftp.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import ec.gob.dinardap.sftp.exception.FtpException;

public class GestionSFTP {

	/**
	 * Método para subir un archivo vía SFTP
	 * 
	 * @param contentFile
	 * @param credenciales
	 * @return
	 * @throws FtpException
	 */
	public static void subirArchivo(byte[] contentFile, CredencialesSFTP credenciales) throws FtpException{
		JSch sftp = new JSch();
		Session session = null;
		ChannelSftp channelSftp = null;
		
		try {
			session = sftp.getSession(credenciales.getUsuario(), 
					credenciales.getHost(), credenciales.getPuerto());
			session.setPassword(credenciales.getContrasena());
			Properties prop = new Properties();
			prop.put("StrictHostKeyChecking", "no");
			session.setConfig(prop);

			session.connect();
			if(session.isConnected()){
				channelSftp = (ChannelSftp)session.openChannel("sftp");
				if (channelSftp != null) {
					channelSftp.connect();
					InputStream is = new ByteArrayInputStream(contentFile);
					System.out.println(credenciales.getDirDestino());
					channelSftp.put(is, credenciales.getDirDestino());
				}else{
					throw new FtpException("No se pudo subir el archivo");
				}
			}else{
				throw new FtpException("No se pudo conectar al servidor sftp");
			}
		} catch (JSchException e) {
			e.printStackTrace();
			throw new FtpException("No se pudo conectar al servidor sftp");
		} catch (SftpException e) {
			e.printStackTrace();
			throw new FtpException("No se pudo conectar al servidor sftp");
		} finally{
			if(channelSftp != null && channelSftp.isConnected())
				channelSftp.disconnect();
			if(session.isConnected())
				session.disconnect();
		}
	}
	
	/**
	 * Método para descargar un archivo del servidor SFTP
	 * 
	 * @param credenciales
	 * @return
	 * @throws FtpException
	 */
	public static byte[] descargarArchivo(CredencialesSFTP credenciales) throws FtpException{
		JSch sftp = new JSch();
		// Instancio el objeto session para la transferencia
		Session session = null;
		// instancio el canal sftp
		ChannelSftp channelSftp = null;
		try {
			// Inciciamos el JSch con el usuario, host y puerto
			session = sftp.getSession(credenciales.getUsuario(), credenciales.getHost(), credenciales.getPuerto());

			// Seteamos el password
			session.setPassword(credenciales.getContrasena());

			// El SFTP requiere un intercambio de claves
			// con esta propiedad le decimos que acepte la clave
			// sin pedir confirmación
			Properties prop = new Properties();
			prop.put("StrictHostKeyChecking", "no");
			session.setConfig(prop);

			session.connect();

			// Abrimos el canal de sftp y conectamos
			channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			// Convertimos el archivo a transferir en un byte array
			return IOUtils.toByteArray(channelSftp.get(credenciales.getDirOrigen()));
		} catch (JSchException jschEx) {
			jschEx.printStackTrace();
			throw new FtpException("No se pudo conectar al servidor sftp");
		} catch (SftpException sftpEx) {
			sftpEx.printStackTrace();
			throw new FtpException("No se pudo conectar al servidor sftp");
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			throw new FtpException("Error al descargar archivo: "
					+ ioEx.getMessage());
		} finally {
			// Cerramos el canal y session
			if (channelSftp.isConnected())
				channelSftp.disconnect();

			if (session.isConnected())
				session.disconnect();
		}
	}
	
	
	public static void borrarArchivo(CredencialesSFTP credenciales) throws FtpException{
		JSch sftp = new JSch();
		Session session = null;
		ChannelSftp channelSftp = null;
		
		try {
			session = sftp.getSession(credenciales.getUsuario(), 
					credenciales.getHost(), credenciales.getPuerto());
			session.setPassword(credenciales.getContrasena());
			Properties prop = new Properties();
			prop.put("StrictHostKeyChecking", "no");
			session.setConfig(prop);

			session.connect();
			if(session.isConnected()){
				channelSftp = (ChannelSftp)session.openChannel("sftp");
				if (channelSftp != null) {
					channelSftp.connect();
					channelSftp.rm(credenciales.getDirOrigen());
				}else{
					throw new FtpException("No se pudo eliminar el archivo");
				}
			}else{
				throw new FtpException("No se pudo conectar al servidor sftp");
			}
		} catch (JSchException e) {
			e.printStackTrace();
			throw new FtpException("No se pudo conectar al servidor sftp");
		} catch (SftpException e) {
			e.printStackTrace();
			throw new FtpException("No se pudo conectar al servidor sftp");
		} finally{
			if(channelSftp != null && channelSftp.isConnected())
				channelSftp.disconnect();
			if(session.isConnected())
				session.disconnect();
		}
	}
	
}
