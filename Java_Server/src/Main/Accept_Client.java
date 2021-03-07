package Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Frame.Frame;
import Pojo.Store_User;
import Pojo.User;

public class Accept_Client extends Thread{
	
	public ServerSocket serverSocket;
	public Store_User store_User;
	public Frame frame;
	public User user;
	public Accept_Client(Frame frame,ServerSocket serverSocket,Store_User store_User) {
		// TODO Auto-generated constructor stub
		this.frame=frame;
		this.serverSocket=serverSocket;
		this.store_User=store_User;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!serverSocket.isClosed())
		{
			try {
				user=new User();
				Socket socket=serverSocket.accept();
				user.setSocket(socket);
				user.setOis(new ObjectInputStream(socket.getInputStream()));
				user.setOop(new ObjectOutputStream(socket.getOutputStream()));
				user.setUsername(user.getOis().readUTF());//获取用户发来的用户名
				
				store_User.add_User(user);
				frame.comboBox.addItem(user.getUsername());
				frame.messageArea.append("用户  "+user.getUsername()+"  已上线\r\n");
				
				new SendOrReceive_Client_Message(frame,
						store_User, user).start();;
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
			
		}
	}
}
