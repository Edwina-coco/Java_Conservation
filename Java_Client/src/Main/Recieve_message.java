package Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Frame.Frame;

public class Recieve_message extends Thread {
	public Socket socket;
	public ObjectInputStream ois;
	public ObjectOutputStream oop;
	public Frame frame;

	public Recieve_message(Frame frame, Socket socket, ObjectInputStream ois, ObjectOutputStream oop) {
		this.frame = frame;
		this.socket = socket;
		this.ois = ois;
		this.oop = oop;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				String option = ois.readUTF();
				if (option.equals("客户端聊天")) {
					String message = ois.readUTF();
//					System.out.println(message);
					frame.messageArea.append(message+"\r\n");
				}
				if(option.equals("用户列表")) {
//					System.out.println(1);
					String[] userlist=ois.readUTF().split("\r\n");
					frame.comboBox.removeAllItems();
					frame.comboBox.addItem("群聊");
					for(int i=0;i<userlist.length;i++)
					{
						frame.comboBox.addItem(userlist[i]);
					}
					frame.comboBox.setSelectedIndex(0);
				}
				if(option.equals("服务器聊天"))
				{
					String message=ois.readUTF();
					frame.messageArea.append("服务器信息:"+message+"\r\n");
				}
				if(option.equals("服务器关闭消息"))
				{
					String message=ois.readUTF();
					frame.messageArea.append(message);
					ois.close();
					oop.close();
					socket.close();
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}

	}

}
