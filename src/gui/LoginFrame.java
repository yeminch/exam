package gui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.Action;
import entity.Request;
import entity.Response;
import entity.Student;

public class LoginFrame extends JFrame implements ActionListener{
	private JLabel title;
	private JLabel idLabel,passwdLabel;
	private JTextField idField;
	private JPasswordField passwdField;
	private JButton ok,cancel;
	private Action action ;
	
	public LoginFrame(){
		super("考生登录");
		
		action=new Action();
		title=new JLabel("考生登录");
		idLabel=new JLabel("学号:");
		passwdLabel=new JLabel("密码:");
		idField=new JTextField(15);
		passwdField=new JPasswordField(15);
		ok=new JButton("登录");
		cancel=new JButton("取消");
		
		init();
		addEventHandler();
	}
	
	/**
	 * 该方法设置界面布局
	 *
	 */
	private void init(){
		JPanel northPanel=new JPanel();
		northPanel.add(title);
		JPanel centerPanel=new JPanel();
		centerPanel.add(idLabel);
		centerPanel.add(idField);
		centerPanel.add(passwdLabel);
		centerPanel.add(passwdField);
		JPanel southPanel=new JPanel();
		southPanel.add(ok);
		southPanel.add(cancel);
		
		this.add(southPanel,BorderLayout.SOUTH);
		this.add(centerPanel,BorderLayout.CENTER);
		this.add(northPanel,BorderLayout.NORTH);
	}
	
	/**
	 * 该方法为图形组件添加事件监听
	 *
	 */
	private void addEventHandler(){
		ok.addActionListener(this);
		cancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("登录")){
			login();
			
		}else if(e.getActionCommand().equals("取消")){
			System.exit(0);
		}
		
	}
	
	private void login(){
		//		判断输入框中ID和密码是否为空，若为空则返回。
		if(idField.getText().trim().equals("") || new String(passwdField.getPassword()).trim().equals("")){
			JOptionPane.showMessageDialog(this,"ID 和密码不能为空！");
			return;
		}
		String id=idField.getText().trim();
		String passwd=new String(passwdField.getPassword()).trim();
		
		//code1:完成客户端登录功能 (16%)
		Request req = new Request(Request.LOGIN_REQ);
		req.addParameter("studentId",id);
		req.addParameter("studentPasswd",passwd);
		Response res = action.doAction(req);
		//得到数据
		Student s =(Student)res.getParameter("stu");
		
		if(s==null){
			JOptionPane.showMessageDialog
			(LoginFrame.this,"账户或密码错误");
			
		}else{
			new WelcomeFrame(s).showMe();
			LoginFrame.this.dispose();
			
		}
	}
	
	/**
	 * 该方法设置界面大小，可见性及默认关闭操作。
	 *
	 */
	public void showMe(){
		this.setSize(240,180);
		this.setResizable(false);
		this.setLocation(400,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
}
