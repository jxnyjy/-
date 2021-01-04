package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConnectUI extends JFrame{

	private JPanel contentPane;
	public static JTextField IpNumber;
	public static JTextField portNumber;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectUI frame = new ConnectUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConnectUI() {
		setResizable(false);
		setTitle("Á¬½ÓÉèÖÃ");
		setBackground(Color.PINK);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 194);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new CompoundBorder());
		panel.setBackground(Color.PINK);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("ÇëÊäÈë·þÎñÆ÷µÄIPµØÖ·£º");
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		IpNumber = new JTextField();
		IpNumber.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 12));
		IpNumber.setText("127.0.0.1");
		panel.add(IpNumber);
		IpNumber.setColumns(8);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new CompoundBorder());
		panel_2.setBackground(Color.PINK);
		contentPane.add(panel_2);
		
		JLabel label = new JLabel("ÇëÊäÈë·þÎñÆ÷µÄ¶Ë¿ÚºÅ£º");
		label.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
		panel_2.add(label);
		
		portNumber = new JTextField();
		portNumber.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 12));
		portNumber.setText("9343");
		panel_2.add(portNumber);
		portNumber.setColumns(8);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new CompoundBorder());
		panel_1.setBackground(Color.PINK);
		contentPane.add(panel_1);
		
		JButton btnSave = new JButton("±£´æ");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				portNumber.setText(portNumber.getText());
				IpNumber.setText(IpNumber.getText());
				ConnectUI.this.dispose();
			}
		});
		btnSave.setBackground(Color.PINK);
		btnSave.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16));
		panel_1.add(btnSave);
		
		JButton btnCancle = new JButton("È¡Ïû");
		btnCancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConnectUI.this.dispose();
			}
		});
		btnCancle.setBackground(Color.PINK);
		btnCancle.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16));
		panel_1.add(btnCancle);
	}

}