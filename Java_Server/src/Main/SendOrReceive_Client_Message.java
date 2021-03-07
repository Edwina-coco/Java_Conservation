package Main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

import Frame.Frame;
import Pojo.Store_User;
import Pojo.User;

/*
 * 从客户端接收消息，并进行转发
 */
public class SendOrReceive_Client_Message extends Thread {
	public User user;
	public Store_User store_User;
	public Frame frame;

	public SendOrReceive_Client_Message(Frame frame, Store_User store_User, User user) {
		this.frame = frame;
		this.store_User = store_User;
		this.user = user;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		SendUserList();
		while (!user.getSocket().isClosed()) {
			try {
				String option = user.getOis().readUTF();
				if (option.equals("客户端聊天")) {
					String username = user.getOis().readUTF(); // 客户端指定发送的用户名
					String message = user.getOis().readUTF();// 客户端发送的信息
					
					message = user.getUsername() + " 发送至 " + username + ":" + message;
					frame.messageArea.append(message + "\r\n");

					if (username.equals("群聊")) {
						SendtoAll(message);
					}
					// 先回送给客户端让信息显示，然后再发送给指定用户
					else {
						user.getOop().writeUTF("客户端聊天");
						user.getOop().flush();
						user.getOop().writeUTF(message);
						user.getOop().flush();

						// 通过username获取到当前请按用户
						User user1 = store_User.get_UserByName(username);
						SendtoSingle(user1, message);
					}
				}
				else if(option.equals("客户端退出"))
				{
					User delete_user=store_User.get_UserByName(user.getUsername());
					String message="用户: "+delete_user.getUsername()+" 退出";
					store_User.delete_User(delete_user);
					frame.comboBox.removeAllItems();
					frame.comboBox.addItem("群聊");
					for(int i=0;i<store_User.getnumbers();i++)
					{
						User user1=store_User.get_UserByIndex(i);
						if(user1==null)
						{
							continue;
						}
						frame.comboBox.addItem(user1.getUsername());
					}
					frame.comboBox.setSelectedIndex(0);
					frame.messageArea.append(message+"\r\n");
					SendtoAll(message);
					SendUserList();
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				System.out.println(e.getMessage());
			}
		}
	}

	/*
	 * 向所有用户发送用户列表
	 */
	public void SendUserList() {
		User user1;
		String userstring = "";
		ObjectOutputStream oop;
		for (int i = 0; i < store_User.getnumbers(); i++) {
			user1 = store_User.get_UserByIndex(i);
			if (user1 == null) {
				continue;
			}
			userstring += user1.getUsername() + "\r\n";
//			System.out.println(userstring);
		}
		for (int i = 0; i < store_User.getnumbers(); i++) {
			user1 = store_User.get_UserByIndex(i);
			if (user1 == null) {
				continue;
			}
			oop = user1.getOop();
			try {
				oop.writeUTF("用户列表");
				oop.flush();
				oop.writeUTF(userstring);
				oop.flush();
			} catch (IOException e) {
				// TODO: handle exception
			}
		}
	}

	/*
	 * 如果客户端要群发此消息
	 */
	public void SendtoAll(String message) {
		User user;
		ObjectOutputStream oop;
		for (int i = 0; i < store_User.getnumbers(); i++) {
			user = store_User.get_UserByIndex(i);
			oop = user.getOop();
			if (user != null) {
				try {
					oop.writeUTF("客户端聊天");
					oop.flush();
					oop.writeUTF(message);
					oop.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	/*
	 * 私聊
	 */
	public void SendtoSingle(User user, String message) {
		ObjectOutputStream oop = user.getOop();
		if (user != null) {
			try {
				oop.writeUTF("客户端聊天");
				oop.flush();
				oop.writeUTF(message);
				oop.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
