package Frame;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Main.AddFramelistener;

public class Connect_config_frame extends JDialog{
	
	public JButton save;
	public JButton cancle;
	public JLabel label;
	public JLabel label2;
	public JLabel label3;
	public JPanel panel;
	public JPanel panel2;
	public JPanel panel3;
	public JPanel panel4;
	public JTextField ipfield;
	public JTextField portfield;
	public Frame frame;

	
	public Connect_config_frame(Frame frame) {
		// TODO Auto-generated constructor stub
		this.frame=frame;
		
		init();
		this.setTitle("连接信息配置");
		this.setSize(new Dimension(300, 170));
		this.setLocation(500, 200);
		this.setVisible(true);
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	}
	
	public void init()
	{
		this.setLayout(new BorderLayout());
		
		panel3=new JPanel(new BorderLayout());
		
		panel=new JPanel(new FlowLayout());
		label=new JLabel("请输入服务器的ip地址:");
		ipfield=new JTextField(10);
		ipfield.setText(AddFramelistener.ip);
		panel.add(label);
		panel.add(ipfield);
		panel3.add(panel,BorderLayout.NORTH);
		
		panel2=new JPanel(new FlowLayout());
		label2=new JLabel("请输入服务器的端口号:");
		portfield=new JTextField(10);
		portfield.setText(""+AddFramelistener.port);
		panel2.add(label2);
		panel2.add(portfield);
		panel3.add(panel2,BorderLayout.CENTER);
		
		this.add(panel3,BorderLayout.NORTH);
		
		label3=new JLabel("              默认连接:127.0.0.1:8888");
		this.add(label3,BorderLayout.CENTER);
		
		panel4=new JPanel(new FlowLayout());
		save=new JButton("保存");
		cancle=new JButton("取消");
		panel4.add(save);
		panel4.add(cancle);
		
		this.add(panel4,BorderLayout.SOUTH);
		
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					AddFramelistener.ip=InetAddress.getByName(ipfield.getText()).getHostAddress();
					int port1=Integer.parseInt(portfield.getText());
					if(port1<1||port1>65535)
					{
						JOptionPane.showMessageDialog(frame, "端口号的范围是:1-65535");
						portfield.setText(Integer.toString(AddFramelistener.port));
					}
					else {
						AddFramelistener.port=port1;
						dispose();
					}
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(frame, "请输入正确的服务器的ip地址");
				ipfield.setText(AddFramelistener.ip);
				}
				catch (NumberFormatException e2) {
					// TODO: handle exception
				JOptionPane.showMessageDialog(frame, "请输入数字");
				portfield.setText(Integer.toString(AddFramelistener.port));
				}
			}
		});
		cancle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
	}
}
