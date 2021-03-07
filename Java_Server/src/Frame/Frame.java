package Frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import Main.AddFramelistener;

/*
 * 服务器窗口
 */
public class Frame extends JFrame {

	public JComboBox comboBox;
	public JTextArea messageArea; // 显示信息区域
	public JTextField messagefield; // 显示输入信息的区域
	public JScrollPane scrollPane;
	public JLabel option_tosend;
	public JLabel messagelabel;
	public JButton send; // 发送按钮

	// 建立工具栏及其按钮
	public JToolBar toolBar;
	public JButton set_port;
	public JButton start_server;
	public JButton stop_server;
	
	public AddFramelistener addFramelistener;
	public Frame() {
		init();
		this.setVisible(true);
		this.setSize(new Dimension(500, 500));
		this.setLocation(500, 200);
		this.setResizable(false);
		this.setTitle("服务器");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init() {

		this.setLayout(new BorderLayout());
		

		toolBar = new JToolBar();

		set_port = new JButton("连接端口");
		start_server = new JButton("开启服务");
		stop_server = new JButton("关闭服务");

		stop_server.setEnabled(false);

		toolBar.add(set_port);
		toolBar.addSeparator(); // 添加分隔符
		toolBar.add(start_server);
		toolBar.add(stop_server);
		toolBar.addSeparator();
		this.add(toolBar, BorderLayout.NORTH);

		comboBox = new JComboBox();
		comboBox.insertItemAt("群聊", 0);
		comboBox.setSelectedIndex(0);


		send = new JButton("发送");

		messagefield = new JTextField(20);
		messagefield.setEnabled(false);

		messageArea = new JTextArea();
		messageArea.setLineWrap(true);
		messageArea.setEditable(false);
		scrollPane = new JScrollPane(messageArea);
		this.add(scrollPane, BorderLayout.CENTER);

		option_tosend = new JLabel("发送至:");
		messagelabel = new JLabel("发送消息");

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(option_tosend);
		panel.add(comboBox);
		panel.add(messagelabel);
		panel.add(messagefield);
		panel.add(send);
		this.add(panel, BorderLayout.SOUTH);
		
		
		addFramelistener=new AddFramelistener(this);
	}

	public static void main(String[] args) {
		Frame frame = new Frame();

	}

}
