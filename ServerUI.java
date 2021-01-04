package server;

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
	private JTextField txt;
	private JButton btnUser;
	private JButton btnConnect;
	private JButton btnStop;
	private JButton btnExit;
	private JButton btnSend;
	private JButton btnHelp;
	private JTextArea textArea;
	
	private boolean isStart = false;
	private List<ClientThread> client = null;
	private ServerSocket serverSocket;
	private ServerThread serverThread;
	private DefaultListModel listModel;
	private JList userList;
	//文件储存位置
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
		setTitle("聊天室服务端");
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
		
		btnUser = new JButton("端口设置");
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PortConfig pc = new PortConfig();
				pc.setVisible(true);
			}
		});
		
		
		btnUser.setVerticalAlignment(SwingConstants.BOTTOM);
		btnUser.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel.add(btnUser);
		
		btnConnect = new JButton("启动服务");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
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
		
		btnStop = new JButton("停止服务");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		
		btnStop.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel_1.add(btnStop);
		
		JLabel label_1 = new JLabel(" ");
		panel_1.add(label_1);
		
		btnExit = new JButton("退出");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		panel_1.add(btnExit);
		btnExit.setFont(new Font("微软雅黑", Font.BOLD, 16));
		
		JPanel message_panel = new JPanel();
		contentPane.add(message_panel, BorderLayout.SOUTH);
		message_panel.setLayout(new GridLayout(2, 1, 0, 3));
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		panel_3.setBorder(new CompoundBorder());
		message_panel.add(panel_3);
		
		JLabel send = new JLabel("发送信息：");
		panel_3.add(send);
		send.setFont(new Font("微软雅黑", Font.BOLD, 16));
		
		txt = new JTextField();
		txt.setEditable(false);
		txt.setFont(new Font("微软雅黑", Font.BOLD, 14));
		txt.setBackground(new Color(255, 255, 255));
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(txt);
		txt.setColumns(20);
		//给文本框增加回车发送功能
		txt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
		
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
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 1));
		
		
		JPanel user_panel = new JPanel();
		user_panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(user_panel, BorderLayout.CENTER);
		user_panel.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("微软雅黑", Font.BOLD, 14));
		JScrollPane scrollPane = new JScrollPane();
		textArea.setBounds(23, 217, 650, 266);
		scrollPane.setViewportView(textArea);
		user_panel.add(scrollPane);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPane.setRowHeaderView(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JLabel label_3 = new JLabel("在线用户");		
		label_3.setBackground(new Color(255, 255, 255));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("微软雅黑", Font.BOLD, 18));
		panel_5.add(label_3, BorderLayout.NORTH);
		
		listModel = new DefaultListModel();
		userList = new JList(listModel);
		
		userList.setForeground(new Color(0, 0, 0));
		userList.setFont(new Font("微软雅黑", Font.BOLD, 12));
		panel_5.add(userList, BorderLayout.CENTER);

	}
	
		public synchronized void send() {
			if (!isStart) {
				JOptionPane.showMessageDialog(this, "服务器还未启动，请先启动服务器");
				return;
			}
			if (client.size() == 0) {
				JOptionPane.showMessageDialog(this, "没有用户在线，不能发送消息！");
				return;
			}
			String message = txt.getText().trim(); // 去掉字符串头部和尾部的空字符串
			if (message == null || message.equals("")) {
				JOptionPane.showMessageDialog(this, "消息不能为空！");
				return;
			}
			sendServerMessage(message); // 群发消息
			textArea.append("[系统通知] " + txt.getText() + "\r\n");
			txt.setText(null);
		}
	 
		public synchronized void sendServerMessage(String message) {
			for (int i = client.size() - 1; i >= 0; i--) {
				client.get(i).getWrite().println("[系统通知] " + message);
				client.get(i).getWrite().flush();
			}
		}
	 
		public void start() {
			if (isStart) {
				JOptionPane.showMessageDialog(this, "服务器已经启动过啦！");
				return;
			}
			int port = -1;
			try {
				port = Integer.parseInt(PortConfig.textPort.getText());
				if (port < 0) {
					JOptionPane.showMessageDialog(this, "端口号为正整数！");
					return;
				}
				serverStart(port);
				textArea.append("服务器已启动！端口号：" + port + "\r\n");
				JOptionPane.showMessageDialog(this, "服务器启动成功！");
				
				btnConnect.setEnabled(false);
				btnUser.setEnabled(false);
				btnStop.setEnabled(true); 
				txt.setEditable(true);
				btnSend.setEnabled(true);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "端口号为正整数！");
			}
		}
	 
		public void stop() {
			if (!isStart) {
				JOptionPane.showMessageDialog(this, "服务器还未启动，无需停止！");
				return;
			}
			try {
				closeServer();
				btnConnect.setEnabled(true);
				btnUser.setEnabled(true);
				btnStop.setEnabled(false);
				txt.setEditable(false);
				btnSend.setEnabled(false);
				
				textArea.append("服务器已成功停止！\r\n");
				JOptionPane.showMessageDialog(this, "服务器已停止！");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "停止服务器发生异常!");
			}
		}
	 
		public void serverStart(int port) {
			client = new ArrayList<ClientThread>();  //放客户端线程
			try {
				serverSocket = new ServerSocket(port);
				serverThread = new ServerThread(serverSocket);
				serverThread.start();
				isStart = true;
			} catch (BindException e) {
				isStart = false;
				JOptionPane.showMessageDialog(this, "端口号被占用！");
			} catch (Exception e) {
				e.printStackTrace();
				isStart = false;
				JOptionPane.showMessageDialog(this, "服务器启动异常");
			}
		}
	 
		public void closeServer() {
			try {
				if (serverThread != null) {
					serverThread.stop(); // 停止服务线程
				}
	 
				// 发布给所有在线用户下线的消息
				for (int i = client.size() - 1; i >= 0; i--) {
					client.get(i).getWrite().println("CLOSE");
					client.get(i).getWrite().flush();
					// 释放资源
					client.get(i).stop(); // 停止此条为客户服务的线程
					client.get(i).read.close();
					client.get(i).write.close();
					client.get(i).socket.close();
					client.remove(i);
				}
				// 关闭服务器连接
				if (serverSocket != null) {
					serverSocket.close();
				}
				
				listModel.removeAllElements();  //清空用户列表
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
				// 不停地等待客户连接
				while (true) {
					try {
						Socket socket = serverSocket.accept();
						ClientThread clients = new ClientThread(socket);
						clients.start(); // 开启客户端服务线程
						
						client.add(clients);
						
						listModel.addElement(clients.getUser().getName());  //更新在线列表
						
						textArea.append("[系统通知] " + clients.getUser().getName() + "上线了！\r\n");
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
			
			//为用户提供输入输出流的getter方法
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
					// 接受用户信息
					String info = read.readLine();
					StringTokenizer st = new StringTokenizer(info, "@");
					user = new User(st.nextToken(), st.nextToken());
					// 反馈连接成功的信息
					write.print("[系统通知] " + user.getName() + "与服务器连接成功！\r\n");
					write.flush();
					// 反馈当前所有在线用户信息
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
	 
					// 向所有的用户发送该用户上线的消息
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
						message = read.readLine(); // 接受消息
						if (message.equals("CLOSE")) { // 下线命令
							textArea.append("[系统通知] " + this.getUser().getName() + "下线!\r\n");
							// 断开连接的资源
							read.close();
							write.close();
							socket.close();
	 
							// 向所有在线用户发送该用户下线的消息
							for (int i = client.size() - 1; i >= 0; i--) {
								client.get(i).getWrite()
								.println("DELETE@"+ client.get(i).getUser().getName());
								client.get(i).getWrite().flush();
							}
							
							listModel.removeElement(user.getName());
							
							// 删除此条客户端的服务线程
							for (int i = client.size() - 1; i >= 0; i--) {
								if (client.get(i).getUser() == user) {
									ClientThread temp = client.get(i);
									client.remove(i); // 删除此用户的服务线程
									temp.stop(); // 停止该条线程
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
			StringTokenizer st = new StringTokenizer(message, "@");//用@作为分隔符分隔信息
			String name = st.nextToken();		//按格式取，第一个用户名称
			String owner = st.nextToken();		//判断群发还是私聊
			String contant = st.nextToken();	//消息内容
			if (owner.equals("ALL")) { // 群发
				message = name + "说：" + contant;
				textArea.append(message + "\r\n");
				for (int i = client.size() - 1; i >= 0; i--) {
					client.get(i).getWrite().println(message);  //获取用户的输出流并打印信息
					client.get(i).getWrite().flush();	//清空缓存区
				}
			}
			else if(owner.equals("ONE")) {//私聊
				String privateName = st.nextToken(); //获取私聊对象的名字	
				for (int i = client.size() - 1; i >= 0; i--) {
					if (client.get(i).getUser().getName() .equals(privateName) ) {	
					    //System.out.println("私聊");
						message = name + "对" + privateName +"说：" + contant;					
						client.get(i).getWrite().println(message);
					    client.get(i).getWrite().flush();
				}
					if (client.get(i).getUser().getName() .equals(name) )
						{
						message = name + "对" + privateName +"说：" + contant;					
					    client.get(i).getWrite().println(message);
				        client.get(i).getWrite().flush();
						}
				}
			}else if(owner.equals("Upload"))
			{
				message = name + "上传了文件：" + contant;
				//执行线程
				DownLoadThread downthread = new DownLoadThread(contant);
				downthread.start();
				
				textArea.append(message + "\r\n");
				for (int i = client.size() - 1; i >= 0; i--) {
					client.get(i).getWrite().println(message);  //获取用户的输出流并打印信息
					client.get(i).getWrite().flush();	//清空缓存区
				}
			}else if(owner.equals("Download"))
			{
				if(file == null) {
					message = "还没有人上传过文件";
					for(int i = client.size() - 1; i >= 0; i--) {
						if(client.get(i).getUser().getName() .equals(name))
						{
							client.get(i).getWrite().println(message);  //获取用户的输出流并打印信息
							client.get(i).getWrite().flush();
							break;
						}
					}
				}else {
					UpLoadThread uploadthread = new UpLoadThread();
					uploadthread.start();
					message = name + "开始下载文件" + file.getName();
					textArea.append(message + "\r\n");
					for(int i = client.size() - 1; i >= 0; i--) {
						client.get(i).getWrite().println(message);
						client.get(i).getWrite().flush();
					}
				}
				
			}else if(owner.equals("Finish"))
			{
				message = name + "下载完成";
				textArea.append(message + "\r\n");
				for(int i = client.size() - 1; i >= 0; i--) {
					client.get(i).getWrite().println(message);
					client.get(i).getWrite().flush();
				}
			}
	}

}

