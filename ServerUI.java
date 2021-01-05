package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Window.Type;
import javax.swing.JComboBox;
import javax.swing.JDialog;

//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;
//import net.miginfocom.swing.MigLayout;
import javax.swing.JToggleButton;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.EtchedBorder;
import javax.swing.JEditorPane;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.border.MatteBorder;


public class ServerUI extends JFrame{

	private JPanel contentPane;
	private JTextPane txt;
	private JButton btnUser;
	private JButton btnConnect;
	private JButton btnStop;
	private JButton btnExit;
	private JButton btnSend;
	private JButton btnHelp;
	private JTextPane textArea;
	
	private boolean isStart = false;
	private List<ClientThread> client = null;
	private ServerSocket serverSocket;
	private ServerThread serverThread;
	private DefaultListModel listModel;
	private JList userList;
	//�ļ�����λ��
	private File file;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerUI frame = new ServerUI();
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
	public ServerUI() {
		setResizable(false);
		setTitle("�����ҷ����");
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
		
		btnUser = new JButton("�˿�����");
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PortConfig pc = new PortConfig();
				pc.setVisible(true);
			}
		});
		
		
		btnUser.setVerticalAlignment(SwingConstants.BOTTOM);
		btnUser.setFont(new Font("΢���ź�", Font.BOLD, 16));
		panel.add(btnUser);
		
		btnConnect = new JButton("��������");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
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
		
		btnStop = new JButton("ֹͣ����");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		
		btnStop.setFont(new Font("΢���ź�", Font.BOLD, 16));
		panel_1.add(btnStop);
		
		JLabel label_1 = new JLabel(" ");
		panel_1.add(label_1);
		
		btnExit = new JButton("�˳�");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		panel_1.add(btnExit);
		btnExit.setFont(new Font("΢���ź�", Font.BOLD, 16));
		
		JPanel message_panel = new JPanel();
		contentPane.add(message_panel, BorderLayout.SOUTH);
		message_panel.setLayout(new GridLayout(2, 1, 0, 3));
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		panel_3.setBorder(new CompoundBorder());
		message_panel.add(panel_3);
		
		JLabel send = new JLabel("������Ϣ��");
		panel_3.add(send);
		send.setFont(new Font("΢���ź�", Font.BOLD, 16));
		
		txt = new JTextPane();
		txt.setEditable(false);
		txt.setFont(new Font("΢���ź�", Font.BOLD, 14));
		txt.setBackground(new Color(255, 255, 255));
		panel_3.add(txt);
		txt.setPreferredSize(new Dimension(200,20));
		//���ı������ӻس����͹���
		//txt.addActionListener(new ActionListener() {
			//public void actionPerformed(ActionEvent e) {
				//send();
			//}
		//});
		
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
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 1));
		
		
		JPanel user_panel = new JPanel();
		user_panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(user_panel, BorderLayout.CENTER);
		user_panel.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextPane();
		textArea.setEditable(false);
		textArea.setFont(new Font("΢���ź�", Font.BOLD, 14));
		JScrollPane scrollPane = new JScrollPane();
		textArea.setBounds(23, 217, 650, 266);
		scrollPane.setViewportView(textArea);
		user_panel.add(scrollPane);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPane.setRowHeaderView(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JLabel label_3 = new JLabel("�����û�");		
		label_3.setBackground(new Color(255, 255, 255));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("΢���ź�", Font.BOLD, 18));
		panel_5.add(label_3, BorderLayout.NORTH);
		
		listModel = new DefaultListModel();
		userList = new JList(listModel);
		
		userList.setForeground(new Color(0, 0, 0));
		userList.setFont(new Font("΢���ź�", Font.BOLD, 12));
		panel_5.add(userList, BorderLayout.CENTER);

	}
	
		public synchronized void send() {
			if (!isStart) {
				JOptionPane.showMessageDialog(this, "��������δ��������������������");
				return;
			}
			if (client.size() == 0) {
				JOptionPane.showMessageDialog(this, "û���û����ߣ����ܷ�����Ϣ��");
				return;
			}
			String message = txt.getText().trim(); // ȥ���ַ���ͷ����β���Ŀ��ַ���
			if (message == null || message.equals("")) {
				JOptionPane.showMessageDialog(this, "��Ϣ����Ϊ�գ�");
				return;
			}
			sendServerMessage(message); // Ⱥ����Ϣ
			textArea.setText(textArea.getText() +"[ϵͳ֪ͨ] " + txt.getText() + "\r\n");
			txt.setText(null);
		}
	 
		public synchronized void sendServerMessage(String message) {
			for (int i = client.size() - 1; i >= 0; i--) {
				client.get(i).getWrite().println("[ϵͳ֪ͨ] " + message);
				client.get(i).getWrite().flush();
			}
		}
	 
		public void start() {
			if (isStart) {
				JOptionPane.showMessageDialog(this, "�������Ѿ�����������");
				return;
			}
			int port = -1;
			try {
				port = Integer.parseInt(PortConfig.textPort.getText());
				if (port < 0) {
					JOptionPane.showMessageDialog(this, "�˿ں�Ϊ��������");
					return;
				}
				serverStart(port);
				textArea.setText(textArea.getText() +"���������������˿ںţ�" + port + "\r\n");
				JOptionPane.showMessageDialog(this, "�����������ɹ���");
				
				btnConnect.setEnabled(false);
				btnUser.setEnabled(false);
				btnStop.setEnabled(true); 
				txt.setEditable(true);
				btnSend.setEnabled(true);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "�˿ں�Ϊ��������");
			}
		}
	 
		public void stop() {
			if (!isStart) {
				JOptionPane.showMessageDialog(this, "��������δ����������ֹͣ��");
				return;
			}
			try {
				closeServer();
				btnConnect.setEnabled(true);
				btnUser.setEnabled(true);
				btnStop.setEnabled(false);
				txt.setEditable(false);
				btnSend.setEnabled(false);
				
				textArea.setText(textArea.getText() +"�������ѳɹ�ֹͣ��\r\n");
				JOptionPane.showMessageDialog(this, "��������ֹͣ��");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "ֹͣ�����������쳣!");
			}
		}
	 
		public void serverStart(int port) {
			client = new ArrayList<ClientThread>();  //�ſͻ����߳�
			try {
				serverSocket = new ServerSocket(port);
				serverThread = new ServerThread(serverSocket);
				serverThread.start();
				isStart = true;
			} catch (BindException e) {
				isStart = false;
				JOptionPane.showMessageDialog(this, "�˿ںű�ռ�ã�");
			} catch (Exception e) {
				e.printStackTrace();
				isStart = false;
				JOptionPane.showMessageDialog(this, "�����������쳣");
			}
		}
	 
		public void closeServer() {
			try {
				if (serverThread != null) {
					serverThread.stop(); // ֹͣ�����߳�
				}
	 
				// ���������������û����ߵ���Ϣ
				for (int i = client.size() - 1; i >= 0; i--) {
					client.get(i).getWrite().println("CLOSE");
					client.get(i).getWrite().flush();
					// �ͷ���Դ
					client.get(i).stop(); // ֹͣ����Ϊ�ͻ�������߳�
					client.get(i).read.close();
					client.get(i).write.close();
					client.get(i).socket.close();
					client.remove(i);
				}
				// �رշ���������
				if (serverSocket != null) {
					serverSocket.close();
				}
				
				listModel.removeAllElements();  //����û��б�
				isStart = false;
			} catch (IOException e) {
				e.printStackTrace();
				isStart = true;
			}
		}
	 
		class ServerThread extends Thread {
			private ServerSocket serverSocket;
	 
			public ServerThread(ServerSocket serverSocket) {
				this.serverSocket = serverSocket;
			}
			@Override
			public void run() {
				// ��ͣ�صȴ��ͻ�����
				while (true) {
					try {
						Socket socket = serverSocket.accept();
						ClientThread clients = new ClientThread(socket);
						clients.start(); // �����ͻ��˷����߳�
						
						client.add(clients);
						
						listModel.addElement(clients.getUser().getName());  //���������б�
						
						textArea.setText(textArea.getText() +"[ϵͳ֪ͨ] " + clients.getUser().getName() + "�����ˣ�\r\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	 
		}
		class UpLoadThread extends Thread{
			public UpLoadThread()
			{
				super();
			}
		}
		public void run()
		{
			try {
				ServerSocket serversocket = new ServerSocket(9997);
				Socket socket = serversocket.accept();
				
				byte [] sendByte = new byte[1024];
				int length = 0;
				FileInputStream fis = new FileInputStream(file);
				
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				while((length = fis.read(sendByte)) > 0) {
					dos.write(sendByte, 0, length);
					dos.flush();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		class DownLoadThread extends Thread
		{
			private String filename;
			public DownLoadThread(String filename)
			{
				super();
				this.filename = filename;
			}
			public void run()
			{
				try {
					ServerSocket downloadsocket = new ServerSocket(9999);
					
					Socket socket = downloadsocket.accept();
					DataInputStream is = new DataInputStream(socket.getInputStream());
					
					filename = "D:\\Test\\Server\\" + filename;
					
					FileOutputStream fos = new FileOutputStream(filename);
					byte[] b = new byte[1024];
					int length = 0;
					while((length = is.read(b)) != -1) {
						fos.write(b, 0, length);
					}
					fos.flush();
					fos.close();
					is.close();
					downloadsocket.close();
					ServerUI.this.file = new File(filename);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		class ClientThread extends Thread {
			private Socket socket;
			private BufferedReader read;
			private PrintWriter write;
			private User user;
			
			//Ϊ�û��ṩ�����������getter����
			public BufferedReader getRead() {
				return read;
			}
	 
			public PrintWriter getWrite() {
				return write;
			}
	 
			public User getUser() {
				return user;
			}
			
			public ClientThread(Socket socket) {
				super();
				this.socket = socket;
				try {
					read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					write = new PrintWriter(socket.getOutputStream());
					// �����û���Ϣ
					String info = read.readLine();
					StringTokenizer st = new StringTokenizer(info, "@");
					user = new User(st.nextToken(), st.nextToken());
					// �������ӳɹ�����Ϣ
					write.print("[ϵͳ֪ͨ] " + user.getName() + "����������ӳɹ���\r\n");
					write.flush();
					// ������ǰ���������û���Ϣ
					if (client.size() > 0) {
						String temp = "";
						for (int i = client.size() - 1; i >= 0; i--) {
							temp += (client.get(i).getUser().getName() + "/" + client
									.get(i).getUser().getIp())
									+ "@";
						}
						write.print("USERLIST@" + client.size() + "@" + temp);
						write.flush();
					}
	 
					// �����е��û����͸��û����ߵ���Ϣ
					for (int i = client.size() - 1; i >= 0; i--) {
						client.get(i).getWrite()
								.println("ADD@" + user.getName());
						client.get(i).getWrite().flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	 
			public void run() {
				String message = null;
				while (true) {
					try {
						message = read.readLine(); // ������Ϣ
						if (message.equals("CLOSE")) { // ��������
							textArea.setText(textArea.getText() +"[ϵͳ֪ͨ] " + this.getUser().getName() + "����!\r\n");
							// �Ͽ����ӵ���Դ
							read.close();
							write.close();
							socket.close();
	 
							// �����������û����͸��û����ߵ���Ϣ
							for (int i = client.size() - 1; i >= 0; i--) {
								client.get(i).getWrite()
								.println("DELETE@"+ client.get(i).getUser().getName());
								client.get(i).getWrite().flush();
							}
							
							listModel.removeElement(user.getName());
							
							// ɾ�������ͻ��˵ķ����߳�
							for (int i = client.size() - 1; i >= 0; i--) {
								if (client.get(i).getUser() == user) {
									ClientThread temp = client.get(i);
									client.remove(i); // ɾ�����û��ķ����߳�
									temp.stop(); // ֹͣ�����߳�
									return;
								}
							}
						} else {
							sendMessage(message);
						}
	 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	 
		public synchronized void sendMessage(String message) {
			StringTokenizer st = new StringTokenizer(message, "@");//��@��Ϊ�ָ����ָ���Ϣ
			String name = st.nextToken();		//����ʽȡ����һ���û�����
			String owner = st.nextToken();		//�ж�Ⱥ������˽��
			String contant = st.nextToken();	//��Ϣ����
			if (owner.equals("ALL")) { // Ⱥ��
				message = name + "˵��" + contant;
				textArea.setText(textArea.getText() +message + "\r\n");
				for (int i = client.size() - 1; i >= 0; i--) {
					client.get(i).getWrite().println(message);  //��ȡ�û������������ӡ��Ϣ
					client.get(i).getWrite().flush();	//��ջ�����
				}
			}
			else if(owner.equals("ONE")) {//˽��
				String privateName = st.nextToken(); //��ȡ˽�Ķ��������	
				for (int i = client.size() - 1; i >= 0; i--) {
					if (client.get(i).getUser().getName() .equals(privateName) ) {	
					    //System.out.println("˽��");
						message = name + "��" + privateName +"˵��" + contant;					
						client.get(i).getWrite().println(message);
					    client.get(i).getWrite().flush();
				}
					if (client.get(i).getUser().getName() .equals(name) )
						{
						message = name + "��" + privateName +"˵��" + contant;					
					    client.get(i).getWrite().println(message);
				        client.get(i).getWrite().flush();
						}
				}
			}else if(owner.equals("Upload"))
			{
				message = name + "�ϴ����ļ���" + contant;
				//ִ���߳�
				DownLoadThread downthread = new DownLoadThread(contant);
				downthread.start();
				
				textArea.setText(textArea.getText() +message + "\r\n");
				for (int i = client.size() - 1; i >= 0; i--) {
					client.get(i).getWrite().println(message);  //��ȡ�û������������ӡ��Ϣ
					client.get(i).getWrite().flush();	//��ջ�����
				}
			}else if(owner.equals("Download"))
			{
				if(file == null) {
					message = "��û�����ϴ����ļ�";
					for(int i = client.size() - 1; i >= 0; i--) {
						if(client.get(i).getUser().getName() .equals(name))
						{
							client.get(i).getWrite().println(message);  //��ȡ�û������������ӡ��Ϣ
							client.get(i).getWrite().flush();
							break;
						}
					}
				}else {
					UpLoadThread uploadthread = new UpLoadThread();
					uploadthread.start();
					message = name + "��ʼ�����ļ�" + file.getName();
					textArea.setText(textArea.getText() +message + "\r\n");
					for(int i = client.size() - 1; i >= 0; i--) {
						client.get(i).getWrite().println(message);
						client.get(i).getWrite().flush();
					}
				}
				
			}else if(owner.equals("Finish"))
			{
				message = name + "�������";
				textArea.setText(textArea.getText() +message + "\r\n");
				for(int i = client.size() - 1; i >= 0; i--) {
					client.get(i).getWrite().println(message);
					client.get(i).getWrite().flush();
				}
			}
	}

}