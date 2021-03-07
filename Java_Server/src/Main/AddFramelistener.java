package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

import com.sun.org.apache.bcel.internal.generic.NEW;

import Frame.Frame;
import Frame.Port_frame;
import Pojo.Store_User;
import Pojo.User;

public class AddFramelistener {
		public static int port=8888;
		public ServerSocket serverSocket;
		public Frame frame;
		public Store_User store_User;
		public AddFramelistener(Frame frame)
		{
			this.frame=frame;
			frame.start_server.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					StartServer();
				}
			});
			frame.set_port.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Port_frame port_frame=new Port_frame(frame);
				}
			});
			frame.send.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					SendMessage();
				}
			});
			
			frame.stop_server.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Stop();
				}
			});
		}
		
		
		/*
		 * 关闭服务器
		 */
		public void Stop() 
		{
			try {
				serverSocket.close();

				for(int i=0;i<store_User.getnumbers();i++)
				{
					User u=store_User.get_UserByIndex(i);
					if(u==null)continue;
					
					u.getOop().writeUTF("服务器关闭消息");
					u.getOop().flush();
					u.getOop().writeUTF("服务器已经关闭,断开连接");
					u.getOop().flush();
				}
			} catch (IOException e) {
				// TODO: handle exception
			}
			System.exit(0);
			
		}
		
		/*
		 * 服务器进行群聊
		 */
		public void SendToAll(String message)
		{
			for(int i=0;i<store_User.getnumbers();i++)
			{
				User user=store_User.get_UserByIndex(i);
				if(user!=null&&!user.getSocket().isClosed())
				{
					try {
						user.getOop().writeUTF("服务器聊天");
						user.getOop().flush();
						user.getOop().writeUTF(message);
						user.getOop().flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
				}
			}
		}
		/*
		 * 服务器私聊
		 */
		public void SendtoSingle(String message)
		{
			String username=frame.comboBox.getSelectedItem().toString();
			User user=store_User.get_UserByName(username);
			if(user!=null&&!user.getSocket().isClosed())
			{
				try {
					user.getOop().writeUTF("服务器聊天");
					user.getOop().flush();
					user.getOop().writeUTF(message);
					user.getOop().flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}
			}
		}
		/*
		 * 服务器群发消息
		 */
		public void SendMessage()
		{
			String option_send=(String)frame.comboBox.getSelectedItem();
			String message=frame.messagefield.getText();
			
			frame.messageArea.append("服务器信息:"+message+"\r\n");
			
			if(option_send.equals("群聊"))
			{
				SendToAll(message);
				frame.messagefield.setText("");
			}
			else {
				SendtoSingle(message);
				frame.messagefield.setText("");
			}
		}
		
		/*
		 * 开启监听服务
		 */
		public void StartServer()
		{
			try {
				serverSocket=new ServerSocket(port);
				frame.messageArea.append("服务器已启动,正在监听"+port+"端口的客户连接\r\n");
				
				frame.start_server.setEnabled(false);
				frame.set_port.setEnabled(false);
				frame.stop_server.setEnabled(true);
				frame.messagefield.setEnabled(true);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			store_User=new Store_User();
			new Accept_Client(frame, serverSocket, store_User).start();
		}
}
