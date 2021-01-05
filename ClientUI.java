package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Window.Type;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import javax.swing.JToggleButton;
import javax.swing.ListModel;

import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.border.EtchedBorder;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.net.Socket;
import java.util.*;
import java.io.*;
import javax.swing.JList;

public class ClientUI extends JFrame{

	//面板设置
	private JPanel contentPane;
	private JTextPane textSend;	
	private JTextPane textShow;		
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
	private Map<String, User> onLineUser = new HashMap<String, User>(); // 所有在线的用户
	//发送信息的线程
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
		setBackground(Color.PINK);
		setTitle("聊天室客户端");
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
		
		btnUser = new JButton("用户设置");
		
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserConfig c = new UserConfig();
				c.setVisible(true);
			}
		});	

		btnUser.setVerticalAlignment(SwingConstants.BOTTOM);
		btnUser.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel.add(btnUser);
		
		btnConnect = new JButton("连接设置");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConnectUI c = new ConnectUI();
				c.setVisible(true);
			}
		});

		btnConnect.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel.add(btnConnect);
		
		JLabel label = new JLabel(" ");
		menu_panel.add(label);
		
		JPanel panel_1 = new JPanel();

		panel_1.setBorder(new CompoundBorder());
		menu_panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		//登录函数
		btnLogin = new JButton("登录");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		btnLogin.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel_1.add(btnLogin);
		//注销函数
		btnLogout = new JButton("注销");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});
		btnLogout.setFont(new Font("微软雅黑", Font.BOLD, 16));
		btnLogout.setEnabled(false);
		panel_1.add(btnLogout);
		
		JLabel label_1 = new JLabel(" ");
		panel_1.add(label_1);
		//退出函数
		JButton btnExit = new JButton("退出");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel_1.add(btnExit);

		btnExit.setFont(new Font("微软雅黑", Font.BOLD, 16));
		
		JPanel message_panel = new JPanel();

		contentPane.add(message_panel, BorderLayout.SOUTH);
		message_panel.setLayout(new GridLayout(3, 1, 0, 3));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new CompoundBorder());
		message_panel.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		comboBox = new JComboBox();
		comboBox.addItem("所有人");
		comboBox.setSelectedIndex(0);
		comboBox.setFont(new Font("微软雅黑", Font.BOLD, 14));		
		panel_2.add(comboBox);
		
		JLabel label_3 = new JLabel("                        ");
		panel_2.add(label_3);
		//私聊功能
		JButton btnPrivate = new JButton("私聊");
		btnPrivate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendToOne();
			}
		});
		btnPrivate.setFont(new Font("微软雅黑", Font.BOLD, 14));
		panel_2.add(btnPrivate);

		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		panel_3.setBorder(new CompoundBorder());
		message_panel.add(panel_3);
		
		JLabel send = new JLabel("发送信息：");
		panel_3.add(send);
		send.setFont(new Font("微软雅黑", Font.BOLD, 16));
		
		textSend = new JTextPane();
		textSend.setEditable(false);
		textSend.setFont(new Font("微软雅黑", Font.BOLD, 14));
		panel_3.add(textSend);
		textSend.setPreferredSize(new Dimension(200,20));
		//给文本框增加回车发送功能
		//textSend.addActionListener(new ActionListener() {
			//public void actionPerformed(ActionEvent e) {
				//send();
			//}
		//});
		
		JLabel label_2 = new JLabel("     ");
		panel_3.add(label_2);
		
		//单击发送
		btnSend = new JButton("发送");
		btnSend.setEnabled(false);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
		btnSend.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel_3.add(btnSend);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new CompoundBorder());

		message_panel.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnUpload = new JButton("上传");
		JButton btnDownload = new JButton("下载");
		panel_4.add(btnUpload);
		panel_4.add(btnDownload);
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					upload();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//上传函数
			}
		});
		btnUpload.setFont(new Font("微软雅黑", Font.BOLD, 16));
		
		btnDownload.setFont(new Font("微软雅黑", Font.BOLD, 16));
		
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				download();
			}
		});
		JPanel user_panel = new JPanel();
		user_panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(user_panel, BorderLayout.CENTER);
		user_panel.setLayout(new BorderLayout(0, 0));
		
		textShow = new JTextPane();
		textShow.setEditable(false);
		textShow.setFont(new Font("微软雅黑", Font.BOLD, 14));
		JScrollPane scrollPane = new JScrollPane();
		user_panel.add(scrollPane);
		scrollPane.setBounds(23, 217, 650, 266);
		textShow.setBounds(23, 217, 650, 266);
		scrollPane.setViewportView(textShow);
	}
	//上传功能
	public synchronized void upload() throws IOException
	{
		JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "doc","txt","docx");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(chooser);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
        	//发送文件发送人的文件名
			File file = chooser.getSelectedFile();
			sendMessage(this.getTitle() + "@" + "Upload" + "@" + file.getName());
			//传送文件
			FileThread filethread = new FileThread(file);
			filethread.start();
        }
        	
	}
	public synchronized void download()
	{
		//发送下载信息
		sendMessage(this.getTitle() + "@" + "Download" + "@" + "null");
		
		DownLoadThread downloadthread = new DownLoadThread();
		downloadthread.start();
	}
	class DownLoadThread extends Thread{
		public DownLoadThread()
		{
			super();
		}
		public void run()
		{
			try {
				Socket downloadsocket = new Socket(socket.getInetAddress(), 9997);

				DataInputStream is = new DataInputStream(downloadsocket.getInputStream());
				OutputStream os = socket.getOutputStream();
				
				String filename = "D:\\Test\\Client\\download.doc";
				FileOutputStream fos = new FileOutputStream(filename);
				byte[] b = new byte[1024];
				int length = 0;
				while((length = is.read(b)) != -1)
				{
					fos.write(b, 0, length);
				}
				fos.flush();
				fos.close();
				is.close();
				downloadsocket.close();
				ClientUI.this.sendMessage(ClientUI.this.getTitle() + "@" + "Finish" + "@" + "null");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	//发送信息
	public synchronized void send(){
		if(!isConnect){
			JOptionPane.showMessageDialog(this, "还没有连接服务器，无法发送消息！");
			return;
		}
		String message = textSend.getText().trim();
		if(message == null || message.equals("")){
			JOptionPane.showMessageDialog(this, "消息不能为空");
			return;
		}
		sendMessage(this.getTitle() + "@" + "ALL" + "@" + message);
		textSend.setText(null);
	}
	//私聊发送
	public synchronized void sendToOne() {
		if(!isConnect){
			JOptionPane.showMessageDialog(this, "还没有连接服务器，无法发送消息！");
			return;
		}
		String message = textSend.getText().trim();
		if(message == null || message.equals("")){
			JOptionPane.showMessageDialog(this, "消息不能为空");
			return;
		}
		String name = comboBox.getSelectedItem().toString();
		sendMessage(this.getTitle() + "@" + "ONE" + "@" + message + "@" + name);
		textSend.setText(null);
	}

	
	//登录操作
	public void login() {
		int port = -1;
		if(isConnect){
			JOptionPane.showMessageDialog(this, "已经处于连接状态，不能重复连接！");
			return;
		}
		try {
			try {
				port = Integer.parseInt(ConnectUI.portNumber.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "输入的端口号不规范，要求为整数！");
			}
			String hostIp = ConnectUI.IpNumber.getText().trim();
			String name = UserConfig.textName.getText().trim();
			if(hostIp.equals("") || name.equals("")){
				JOptionPane.showMessageDialog(this, "Ip地址和用户名都不能为空！");
				return;
			}
			boolean flag = connecServer(port, hostIp, name);
			if(flag == false){
				JOptionPane.showMessageDialog(this, "与服务器连接失败！");
				return;
			}
			this.setTitle(name);  //设置客户端窗口标题为用户名
			JOptionPane.showMessageDialog(this, "成功连接！");
			
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
	
	//注销操作
	public void logout(){
		
		String hostIp = ConnectUI.IpNumber.getText().trim();
		String name = UserConfig.textName.getText().trim();
		
		if(!isConnect){
			JOptionPane.showMessageDialog(this, "已经是断开状态了哦！");
			return;
		}
		try {
			boolean flag = closeConnect();		//断开连接
			if(!flag){
				JOptionPane.showMessageDialog(this, "断开连接发生异常！");
				return;
			}
			JOptionPane.showMessageDialog(this,"断开成功！");				                
			
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
 
	//连接服务器
	public boolean connecServer(int port, String hostIp, String name) {
		try {
			socket = new Socket(hostIp, port); // 根据端口号号和服务器
			write = new PrintWriter(socket.getOutputStream());
			read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//发送客户端的基本信息
			sendMessage(name+"@"+socket.getLocalAddress().toString());
			//开启接收消息的线程
			messageThread = new MessageThread();
			messageThread.start();
			isConnect = true;		//状态改为：已连接
			
			return true;
		} catch (Exception e) {
			textShow.setText(textShow.getText() +"与端口号为："+port+",   Ip地址为："+hostIp+"的服务器连接失败！\r\n");
			isConnect = false;		//状态为：未连接
			return false;
		}
	}
 
	//关闭连接
	public synchronized boolean closeConnect() {
		try {
			sendMessage("CLOSE"); // 发送断开连接命令给服务器
			messageThread.stop(); // 停止接受消息的线程
			// 释放资源
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
 
	//发送信息
	public synchronized void sendMessage(String message) {
		write.println(message);
		write.flush();
	}
	//发送文件的线程
	class FileThread extends Thread{
		private File file;
		public FileThread(File f){
			super();
			this.file = f;
		}
		public void run()
		{
			try {
				Socket filesocket = new Socket(socket.getInetAddress(), 9999);
				byte [] sendByte = new byte[1024];
				int length = 0;
				FileInputStream fis = new FileInputStream(file);
				
				DataOutputStream dos = new DataOutputStream(filesocket.getOutputStream());
				while((length = fis.read(sendByte)) > 0) {
					dos.write(sendByte, 0, length);
					dos.flush();
				}
				filesocket.close();
				fis.close();
				dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//发送信息的线程
	class MessageThread extends Thread {
 
		// 接收消息线程的构造方法
		public MessageThread() {
			super();
		}
		public synchronized void closeConnect() throws Exception {
			//清空用户列表
			comboBox.removeAllItems();
			
			// 被动关闭连接释放资源
			if (read != null) {
				read.close();
			}
			if (write != null) {
				write.close();
			}
			if (socket != null) {
				socket.close();
			}
			isConnect = false; // 将状态改为未连接状态
			btnConnect.setEnabled(true);
			btnUser.setEnabled(true);
			btnLogin.setEnabled(true);
			btnLogout.setEnabled(false);
			textSend.setEditable(false);
			btnSend.setEnabled(false);
		}
 
		public void run() { // 不断接受消息
			String message = "";
			while (true) {
				try {
					message = read.readLine();
					StringTokenizer st = new StringTokenizer(message, "/@");
					String command = st.nextToken(); 
					if (command.equals("CLOSE")) { // 关闭命令
						textShow.setText(textShow.getText() +"服务器已关闭！\r\n");
						closeConnect(); // 被动关闭连接
						return; // 结束线程
					} else if (command.equals("ADD")) { // 有用户上线更新列表
						String userName = "";
						String userIp = "";
						if ((userName = st.nextToken()) != null) {
							User user = new User(userName, userIp);
							onLineUser.put(userName, user);
							comboBox.addItem(userName);
							comboBox.revalidate();
						}
						textShow.setText(textShow.getText() +"[系统通知] " + userName + "上线了！\r\n");
					} else if (command.equals("DELETE")) { // 有用户下线更新列表
						String userName = st.nextToken();
						User user = (User) onLineUser.get(userName);
						onLineUser.remove(userName);					
						
						comboBox.removeItem(userName);
						comboBox.revalidate();
						textShow.setText(textShow.getText() +"[系统通知] " + userName + "下线了！\r\n");
					} else if (command.equals("USERLIST")) {  //更新用户列表
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
//					else if(command.equals("ONE")){
//						String msg = st.nextToken();
//						textShow.append(msg + "\r\n");
//					}
					else { // 普通消息
						textShow.setText(textShow.getText() +message + "\r\n");
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