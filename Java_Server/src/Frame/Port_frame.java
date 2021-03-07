package Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Main.AddFramelistener;

public class Port_frame extends JDialog {
	public JButton saveButton;
	public JButton cancleButton;

	public JTextField portfield;
	public JPanel panel;
	public JPanel panel2;
	public JLabel label;
	public JLabel label2;
	public Frame frame;
	public Port_frame(Frame frame) {
		this.frame=frame;
		init();
		this.setTitle("端口配置");
		this.setSize(new Dimension(300, 130));
		this.setLocation(500, 200);
		this.setVisible(true);
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	}

	public void init() {
		panel = new JPanel();
		panel2 = new JPanel();
		label = new JLabel("请输入端口号:");
		label2 = new JLabel("              默认端口号为:8888");
		portfield = new JTextField(10);
		portfield.setText(""+AddFramelistener.port);
		saveButton = new JButton("保存");
		cancleButton = new JButton("取消");
		panel.setLayout(new FlowLayout());
		panel.add(label);
		panel.add(portfield);

		panel2.setLayout(new FlowLayout());
		panel2.add(saveButton);
		panel2.add(cancleButton);

		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.NORTH);
		this.add(label2, BorderLayout.CENTER);
		this.add(panel2, BorderLayout.SOUTH);

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					int port = Integer.parseInt(portfield.getText());
					if (port < 1 || port > 65535) {
						JOptionPane.showMessageDialog(frame, "端口号的范围是1-65535");
						portfield.setText("");
					}
					else {
						AddFramelistener.port=port;	
						dispose(); //关闭窗口，释放资源
					}
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(frame, "请输入数字");
					portfield.setText("");
				}
			}
		});
		cancleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});

	}

}
