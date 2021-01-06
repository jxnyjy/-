package server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class UserConfig extends JFrame{

	private JPanel contentPane;
	public static JTextField textName;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserConfig frame = new UserConfig();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public UserConfig() {
		setResizable(false);
		setTitle("用户设置");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new CompoundBorder());
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 4, 4));
		
		JLabel label = new JLabel("请输入用户名：");
		label.setFont(new Font("微软雅黑", Font.BOLD, 18));
		panel.add(label);
		
		JLabel label_2 = new JLabel("");
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("");
		panel.add(label_3);
		
		JLabel label_1 = new JLabel("");
		panel.add(label_1);
		
		textName = new JTextField();
		textName.setFont(new Font("微软雅黑", Font.BOLD, 16));
		textName.setText("");
		panel.add(textName);
		textName.setColumns(5);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setVgap(2);
		flowLayout.setHgap(2);
		panel_2.setBorder(new CompoundBorder());
		contentPane.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new CompoundBorder());
		panel_2.add(panel_3);
		
		JButton btnSave = new JButton("保存");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField textName = new JTextField();
				textName.setText(textName.getText());
				UserConfig.this.dispose();
			}
		});
		btnSave.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel_3.add(btnSave);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new CompoundBorder());
		panel_2.add(panel_4);
		
		JButton btnCancel = new JButton("取消");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserConfig.this.dispose();
			}
		});
		btnCancel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel_4.add(btnCancel);
	}
}