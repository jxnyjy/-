package server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PortConfig extends JFrame implements ActionListener{

	private JPanel contentPane;
	public static JTextField textPort;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PortConfig frame = new PortConfig();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public PortConfig() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 191);
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		JLabel label = new JLabel("请输入侦听的端口号：");
		label.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel.add(label);
		
		textPort = new JTextField("9343");
		textPort.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel.add(textPort);
		textPort.setColumns(7);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		
		JLabel label_1 = new JLabel("默认端口号为：9343");
		label_1.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel_1.add(label_1);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		
		JButton btnSave = new JButton("保存");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PortConfig.this.dispose();
			}
		});
		btnSave.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel_2.add(btnSave);
		
		JButton btnCancle = new JButton("取消");
		btnCancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PortConfig.this.dispose();
			}
		});
		btnCancle.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel_2.add(btnCancle);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}

}