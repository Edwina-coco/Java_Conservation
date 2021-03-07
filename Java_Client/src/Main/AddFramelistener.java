package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import Frame.Connect_config_frame;
import Frame.Frame;
import Frame.User_config_frame;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPSize;

public class AddFramelistener {
	public static String ip = "127.0.0.1";
	public int type = 0;
	public static int port = 8888;
	public static String username = "贾西腾";
	public Frame frame;
	public Socket socket;
	ObjectInputStream ois = null;
	ObjectOutputStream oop = null;

	public AddFramelistener(Frame frame) {
		this.frame = frame;

		frame.set_userconfig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				User_config_frame user_config_frame = new User_config_frame(frame);
			}
		});

		frame.set_connectconfig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Connect_config_frame config_frame = new Connect_config_frame(frame);
			}
		});

		frame.login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Connect();
			}
		});

		frame.send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Send();
				frame.messagefield.setText("");
			}
		});

		frame.exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int exit = JOptionPane.showConfirmDialog(frame, "是否退出");// 是返回0,不是返回1
				if (exit == 0) {
					if(type==1)
					Exit();
				System.exit(0);
				}

			}
		});
	}

	/*
	 * 退出服务
	 */
	public void Exit() {
		frame.login.setEnabled(true);
		frame.set_connectconfig.setEnabled(true);
		frame.set_userconfig.setEnabled(true);
		frame.exit.setEnabled(false);
		frame.messagefield.setEditable(false);

		try {
			oop.writeUTF("客户端退出");
			oop.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} finally {
			try {
				ois.close();
				oop.close();
				socket.close();
				frame.messageArea.append("已成功退出\r\n");
				type=0;
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}

		}
	}

	/*
	 * 发送给其他用户消息（在建立完连接获取到socket的输入输出流后）
	 */
	public void Send() {
		String user = frame.comboBox.getSelectedItem().toString();
		String message = frame.messagefield.getText();
		try {
			if(oop!=null)
			{
				oop.writeUTF("客户端聊天");
				oop.flush();
				oop.writeUTF(user);
				oop.flush();
				oop.writeUTF(message);
				oop.flush();	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}

	}

	
	/*
	 * 开启连接服务
	 */
	public void Connect() {

		try {
			socket = new Socket(ip, port);

			oop = new ObjectOutputStream(socket.getOutputStream());
			oop.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			new Recieve_message(frame, socket, ois, oop).start();
			oop.writeUTF(username);
			oop.flush();
			frame.login.setEnabled(false);
			frame.set_connectconfig.setEnabled(false);
			frame.set_userconfig.setEnabled(false);
			frame.exit.setEnabled(true);
			frame.send.setEnabled(true);
			frame.messagefield.setEnabled(true);
			frame.messageArea.append("您已连接成功!\r\n");
			type=1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(frame, "无法连接至制定服务器.请重新进行连接信息配置");
		}
	}
}
