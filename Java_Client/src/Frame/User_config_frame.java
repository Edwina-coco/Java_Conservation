package Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Main.AddFramelistener;

/*
 * 新的用户信息窗口
 */
public class User_config_frame extends JDialog {
	
	
	public JLabel label;
	public JLabel label2;
	public JPanel  panel;
	public JPanel  panel2;
	public JTextField usernamefield;
	public JButton save;
	public JButton cancle;
	public Frame frame;
	
	
	public User_config_frame(Frame frame) {
		// TODO Auto-generated constructor stub
		this.frame=frame;
		init();
		this.setTitle("用户信息配置");
		this.setSize(new Dimension(300, 130));
		this.setLocation(500, 200);
		this.setVisible(true);
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	}
	public void init()
	{
		panel = new JPanel();
		panel2 = new JPanel();
		label = new JLabel("请输入用户名:");
		label2 = new JLabel("              默认用户名为:贾西腾");
		usernamefield=new JTextField(10);
		usernamefield.setText(AddFramelistener.username);
		save=new JButton("保存");
		cancle=new JButton("取消");
		
		panel=new JPanel(new FlowLayout());
		panel.add(label);
		panel.add(usernamefield);
		
		
		panel2=new JPanel(new FlowLayout());
		panel2.add(save);
		panel2.add(cancle);
		
		this.setLayout(new BorderLayout());
		this.add(panel,BorderLayout.NORTH);
		this.add(label2,BorderLayout.CENTER);
		this.add(panel2,BorderLayout.SOUTH);
		
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String username=usernamefield.getText();
				if(username.equals(""))
				{
					JOptionPane.showMessageDialog(frame, "用户名不能为空");
					usernamefield.setText(AddFramelistener.username);
					return ;
				}
				else {
					AddFramelistener.username=username;
					frame.setTitle(AddFramelistener.username+"的客户端");
					dispose();
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
//	public static void main(String[] args) {
//		User_config user_config=new User_config(new Frame(),"dwa");
//	}
}
