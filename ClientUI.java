package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.net.Socket;
import java.util.*;
import java.io.*;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import client.UserConfig;


public class ClientUI extends JFrame{

	private JPanel contentPane;
	private JTextField textSend;	
	private JTextArea textShow;		
	private JButton btnLogin;
	private JButton btnLogout;
	private JButton btnUser;
	private JButton btnConnect;
	private JButton btnSend;
	private JComboBox comboBox;
		
	private boolean isConnect = false;
	private BufferedReader read;
	private PrintWriter write;
	private Socket socket;	
	private Map<String, User> onLineUser = new HashMap<String, User>(); // �������ߵ��û�
	private MessageThread messageThread;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientUI frame = new ClientUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public ClientUI() {
		setResizable(false);
		setTitle("�����ҿͻ���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel menu_panel = new JPanel();
		menu_panel.setBorder(new CompoundBorder());
		contentPane.add(menu_panel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setBorder(new CompoundBorder());
		menu_panel.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		btnUser = new JButton("�û�����");
		
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserConfig c = new UserConfig();
				c.setVisible(true);
			}
		});	
		btnUser.setVerticalAlignment(SwingConstants.BOTTOM);
		btnUser.setFont(new Font("΢���ź�", Font.BOLD, 16));
		panel.add(btnUser);
		
		btnConnect = new JButton("��������");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConnectUI c = new ConnectUI();
				c.setVisible(true);
			}
		});
		btnConnect.setFont(new Font("΢���ź�", Font.BOLD, 16));
		panel.add(btnConnect);
		
		JLabel label = new JLabel(" ");
		menu_panel.add(label);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new CompoundBorder());
		menu_panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnLogin = new JButton("��¼");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnLogin.setFont(new Font("΢���ź�", Font.BOLD, 16));
		panel_1.add(btnLogin);
		
		btnLogout = new JButton("ע��");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});
		btnLogout.setFont(new Font("΢���ź�", Font.BOLD, 16));
		btnLogout.setEnabled(false);
		panel_1.add(btnLogout);
		
		JLabel label_1 = new JLabel(" ");
		panel_1.add(label_1);
		
		JButton btnExit = new JButton("�˳�");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel_1.add(btnExit);
		btnExit.setFont(new Font("΢���ź�", Font.BOLD, 16));
		
		JPanel message_panel = new JPanel();
		contentPane.add(message_panel, BorderLayout.SOUTH);
		message_panel.setLayout(new GridLayout(3, 1, 0, 3));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new CompoundBorder());
		message_panel.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		comboBox = new JComboBox();
		comboBox.addItem("������");
		comboBox.setSelectedIndex(0);
		comboBox.setFont(new Font("΢���ź�", Font.BOLD, 14));		
		panel_2.add(comboBox);
		
		JLabel label_3 = new JLabel("                        ");
		panel_2.add(label_3);
		
		JButton btnPrivate = new JButton("˽��");
		btnPrivate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendToOne();
			}
		});
		btnPrivate.setFont(new Font("΢���ź�", Font.BOLD, 14));
		panel_2.add(btnPrivate);

		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		panel_3.setBorder(new CompoundBorder());
		message_panel.add(panel_3);
		
		JLabel send = new JLabel("������Ϣ��");
		panel_3.add(send);
		send.setFont(new Font("΢���ź�", Font.BOLD, 16));
		
		textSend = new JTextField();
		textSend.setEditable(false);
		textSend.setFont(new Font("΢���ź�", Font.BOLD, 14));
		textSend.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(textSend);
		textSend.setColumns(20);
		//���ı������ӻس����͹���
		textSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
		
		JLabel label_2 = new JLabel("     ");
		panel_3.add(label_2);
		
		//��������
		btnSend = new JButton("����");
		btnSend.setEnabled(false);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
		btnSend.setFont(new Font("΢���ź�", Font.BOLD, 16));
		panel_3.add(btnSend);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new CompoundBorder());
		message_panel.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnHelp = new JButton("�ϴ�");
		panel_4.add(btnHelp);
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			
			}
		});
		btnHelp.setFont(new Font("΢���ź�", Font.BOLD, 16));
		
		JPanel user_panel = new JPanel();
		user_panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(user_panel, BorderLayout.CENTER);
		user_panel.setLayout(new BorderLayout(0, 0));
		
		textShow = new JTextArea();
		textShow.setEditable(false);
		textShow.setFont(new Font("΢���ź�", Font.BOLD, 14));
		JScrollPane scrollPane = new JScrollPane();
		user_panel.add(scrollPane);
		scrollPane.setBounds(23, 217, 650, 266);
		textShow.setBounds(23, 217, 650, 266);
		scrollPane.setViewportView(textShow);
	}
	

	public synchronized void send(){
		if(!isConnect){
			JOptionPane.showMessageDialog(this, "��û�����ӷ��������޷�������Ϣ��");
			return;
		}
		String message = textSend.getText().trim();
		if(message == null || message.equals("")){
			JOptionPane.showMessageDialog(this, "��Ϣ����Ϊ��");
			return;
		}
		sendMessage(this.getTitle() + "@" + "ALL" + "@" + message);
		textSend.setText(null);
	}
	
	public synchronized void sendToOne() {
		if(!isConnect){
			JOptionPane.showMessageDialog(this, "��û�����ӷ��������޷�������Ϣ��");
			return;
		}
		String message = textSend.getText().trim();
		if(message == null || message.equals("")){
			JOptionPane.showMessageDialog(this, "��Ϣ����Ϊ��");
			return;
		}
		String name = comboBox.getSelectedItem().toString();
		sendMessage(this.getTitle() + "@" + "ONE" + "@" + message + "@" + name);
		textSend.setText(null);
	}
	

	public void login() {
		int port = -1;
		if(isConnect){
			JOptionPane.showMessageDialog(this, "�Ѿ���������״̬�������ظ����ӣ�");
			return;
		}
		try {
			try {
				port = Integer.parseInt(ConnectUI.portNumber.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "����Ķ˿ںŲ��淶��Ҫ��Ϊ������");
			}
			String hostIp = ConnectUI.IpNumber.getText().trim();
			String name = UserConfig.textName.getText().trim();
			if(hostIp.equals("") || name.equals("")){
				JOptionPane.showMessageDialog(this, "Ip��ַ���û���������Ϊ�գ�");
				return;
			}
			boolean flag = connecServer(port, hostIp, name);
			if(flag == false){
				JOptionPane.showMessageDialog(this, "�����������ʧ�ܣ�");
				return;
			}
			this.setTitle(name);  //���ÿͻ��˴��ڱ���Ϊ�û���
			JOptionPane.showMessageDialog(this, "�ɹ����ӣ�");
			
			comboBox.addItem(name);
			comboBox.revalidate();
			
			btnConnect.setEnabled(false);
			btnUser.setEnabled(false);
			btnLogin.setEnabled(false);
			btnLogout.setEnabled(true);
			textSend.setEditable(true);
			btnSend.setEnabled(true);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.toString());
		}
	}
	

	public void logout(){
		
		String hostIp = ConnectUI.IpNumber.getText().trim();
		String name = UserConfig.textName.getText().trim();
		
		if(!isConnect){
			JOptionPane.showMessageDialog(this, "�Ѿ��ǶϿ�״̬��Ŷ��");
			return;
		}
		try {
			boolean flag = closeConnect();		//�Ͽ�����
			if(!flag){
				JOptionPane.showMessageDialog(this, "�Ͽ����ӷ����쳣��");
				return;
			}
			JOptionPane.showMessageDialog(this,"�Ͽ��ɹ���");				                
			
			comboBox.removeAllItems();
			comboBox.revalidate();
			
			btnConnect.setEnabled(true);
			btnUser.setEnabled(true);
			btnLogin.setEnabled(true);
			btnLogout.setEnabled(false);
			textSend.setEditable(false);
			btnSend.setEnabled(false);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.toString());
		}
			
	}

	public boolean connecServer(int port, String hostIp, String name) {
		try {
			socket = new Socket(hostIp, port); // ���ݶ˿ںźźͷ�����
			write = new PrintWriter(socket.getOutputStream());
			read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//���Ϳͻ��˵Ļ�����Ϣ
			sendMessage(name+"@"+socket.getLocalAddress().toString());
			//����������Ϣ���߳�
			messageThread = new MessageThread();
			messageThread.start();
			isConnect = true;		//״̬��Ϊ��������
			
			return true;
		} catch (Exception e) {
			textShow.append("��˿ں�Ϊ��"+port+",   Ip��ַΪ��"+hostIp+"�ķ���������ʧ�ܣ�\r\n");
			isConnect = false;		//״̬Ϊ��δ����
			return false;
		}
	}
 

	public synchronized boolean closeConnect() {
		try {
			sendMessage("CLOSE"); // ���ͶϿ����������������
			messageThread.stop(); // ֹͣ������Ϣ���߳�
			// �ͷ���Դ
			if (read != null) {
				read.close();
			}
			if (write != null) {
				write.close();
			}
			if (socket != null) {
				socket.close();
			}
			isConnect = false;
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			isConnect = true;
			return false;
		}
 
	}
 

	public synchronized void sendMessage(String message) {
		write.println(message);
		write.flush();
	}

	class MessageThread extends Thread {
 
		// ������Ϣ�̵߳Ĺ��췽��
		public MessageThread() {
			super();
		}
 
		public synchronized void closeConnect() throws Exception {
			//����û��б�
			comboBox.removeAllItems();
			
			// �����ر������ͷ���Դ
			if (read != null) {
				read.close();
			}
			if (write != null) {
				write.close();
			}
			if (socket != null) {
				socket.close();
			}
			isConnect = false; // ��״̬��Ϊδ����״̬
			btnConnect.setEnabled(true);
			btnUser.setEnabled(true);
			btnLogin.setEnabled(true);
			btnLogout.setEnabled(false);
			textSend.setEditable(false);
			btnSend.setEnabled(false);
		}
 
		public void run() { // ���Ͻ�����Ϣ
			String message = "";
			while (true) {
				try {
					message = read.readLine();
					StringTokenizer st = new StringTokenizer(message, "/@");
					String command = st.nextToken(); 
					if (command.equals("CLOSE")) { // �ر�����
						textShow.append("�������ѹرգ�\r\n");
						closeConnect(); // �����ر�����
						return; // �����߳�
					} else if (command.equals("ADD")) { // ���û����߸����б�
						String userName = "";
						String userIp = "";
						if ((userName = st.nextToken()) != null) {
							User user = new User(userName, userIp);
							onLineUser.put(userName, user);
							comboBox.addItem(userName);
							comboBox.revalidate();
						}
						textShow.append("[ϵͳ֪ͨ] " + userName + "�����ˣ�\r\n");
					} else if (command.equals("DELETE")) { // ���û����߸����б�
						String userName = st.nextToken();
						User user = (User) onLineUser.get(userName);
						onLineUser.remove(userName);					
						
						comboBox.removeItem(userName);
						comboBox.revalidate();
						textShow.append("[ϵͳ֪ͨ] " + userName + "�����ˣ�\r\n");
					} else if (command.equals("USERLIST")) {  //�����û��б�
						int size = Integer.parseInt(st.nextToken());
						String userName = null;
						String userIp = null;
						for (int i = 0; i < size; i++) {
							userName = st.nextToken();
							userIp = st.nextToken();
							User user = new User(userName, userIp);
							onLineUser.put(userName, user);							
							comboBox.addItem(userName);
							comboBox.revalidate();
						}
					}
					else if(command.equals("ONE")){
						String msg = st.nextToken();
						textShow.append(msg + "\r\n");
					}
					else { // ��ͨ��Ϣ
						textShow.append(message + "\r\n");
					}
 
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}