package Pojo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User {
	private String username;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oop;
	
	
	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}
	
	public void setOop(ObjectOutputStream oop) {
		this.oop = oop;
	}
	
	public ObjectInputStream getOis() {
		return ois;
	}
	
	public ObjectOutputStream getOop() {
		return oop;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public String getUsername() {
		return username;
	}
}
