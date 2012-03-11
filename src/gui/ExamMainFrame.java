package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import client.Action;
import entity.Paper;
import entity.Question;
import entity.Request;
import entity.Response;
import entity.Student;

public class ExamMainFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel studentName = null;//显示学生姓名的Label
	private JLabel studentID = null;//显示学生ID的Label
	private JLabel subject = null;//显示科目名称的Label
	private JTextArea jTextArea = null;//显示试题的文本框
	private JRadioButton aRadio = null;//A选项按钮
	private JRadioButton bRadio = null;//B选项按钮
	private JRadioButton cRadio = null;//C选项按钮
	private JRadioButton dRadio = null;//D选项按钮
	private JButton previous = null;//上一题按钮
	private JButton next = null;//下一题按钮
	private JButton handIn = null;//交卷按钮
	private JLabel jLabel7 = null;
	private JLabel jLabel8 = null;
	private JLabel remainQuestions = null;//显示剩余试题数的Label
	private JLabel remainTime = null;//显示剩余时间的Label
	private Student student;//参加考试的考生
	private Paper paper;//考试试卷 
	
	private Action action;
	/**
	 * This is the default constructor
	 */
	public ExamMainFrame(Student stu,Paper paper) {
		super();
		action = new Action();
		this.student=stu;
		this.paper=paper;
		initialize();
		addEventHandler();
	}
	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBounds(new Rectangle(14, 75, 738, 269));
		}
		return jTextArea;
	}

	/**
	 * This method initializes aRadio	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getARadio() {
		if (aRadio == null) {
			aRadio = new JRadioButton();
			aRadio.setPreferredSize(new Dimension(35, 30));
			aRadio.setSize(new Dimension(50, 25));
			aRadio.setLocation(new Point(262, 367));
			aRadio.setText("A");
		}
		return aRadio;
	}

	/**
	 * This method initializes bRadio	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getBRadio() {
		if (bRadio == null) {
			bRadio = new JRadioButton();
			bRadio.setPreferredSize(new Dimension(35, 30));
			bRadio.setSize(new Dimension(50, 25));
			bRadio.setLocation(new Point(322, 367));
			bRadio.setText("B");
		}
		return bRadio;
	}

	/**
	 * This method initializes cRadio	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getCRadio() {
		if (cRadio == null) {
			cRadio = new JRadioButton();
			cRadio.setText("C");
			cRadio.setLocation(new Point(382, 367));
			cRadio.setSize(new Dimension(50, 25));
		}
		return cRadio;
	}

	/**
	 * This method initializes dRadio	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getDRadio() {
		if (dRadio == null) {
			dRadio = new JRadioButton();
			dRadio.setText("D");
			dRadio.setLocation(new Point(442, 367));
			dRadio.setSize(new Dimension(50, 25));
		}
		return dRadio;
	}

	/**
	 * This method initializes previous	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getPrevious() {
		if (previous == null) {
			previous = new JButton();
			previous.setText("<<上一题");
			previous.setLocation(new Point(220, 412));
			previous.setSize(new Dimension(90, 35));
		}
		return previous;
	}

	/**
	 * This method initializes next	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getNext() {
		if (next == null) {
			next = new JButton();
			next.setText("下一题>>");
			next.setLocation(new Point(350, 412));
			next.setSize(new Dimension(90, 35));
		}
		return next;
	}

	/**
	 * This method initializes handIn	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getHandIn() {
		if (handIn == null) {
			handIn = new JButton();
			handIn.setText("交卷");
			handIn.setSize(new Dimension(90, 35));
			handIn.setLocation(new Point(470, 412));
		}
		return handIn;
	}
	
	/**
	 * 为图形组件添加事件的方法
	 */
	public void addEventHandler(){
		handIn.addActionListener(this);
		previous.addActionListener(this);
		next.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {
				int op=JOptionPane.showConfirmDialog(
					ExamMainFrame.this,
					"确认要退出程序吗?","确认退出",JOptionPane.YES_NO_OPTION);
				if(op==JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});
		
		aRadio.addActionListener(this);
		bRadio.addActionListener(this);
		cRadio.addActionListener(this);
		dRadio.addActionListener(this);
	}
	
	public void showMe(){
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
		//启动一个新的线程--倒计时
		new TimeRefreshThread().start();
	}

	/**
	 * 本类实现ActionListener，实现actionPerformed方法
	 */
	
	int count=0; //用来记忆 题号
	
	public void actionPerformed(ActionEvent e) {
		String str=e.getActionCommand();
		if(str.equals("交卷")){
			submitPaper();

		}else if(str.equals("<<上一题")){
			//code5:完成上一题切换功能 (5%)
			if(count==0){
				//如果是第一题，那么要提示下
				JOptionPane.showMessageDialog(this,"已经是第一题");
			}else{
				count--;
				//文本框的题目要改变,刷新掉
				getJTextArea().setText(
						(count+1)+"."+paper.getAllQuestions().get(count).toString()
				);
				//剩余的题目数
				remainQuestions.setText(paper.getQuestionNumber()-count+"");
				
				//使每个按钮置空,根据选定的答案设置它
				aRadio.setSelected(false);
				bRadio.setSelected(false);
				cRadio.setSelected(false);
				dRadio.setSelected(false);
				if(paper.getAnswers()[count]=='A'){
					aRadio.setSelected(true);
				} else if(paper.getAnswers()[count]=='B'){
					bRadio.setSelected(true);
				} else if(paper.getAnswers()[count]=='C'){
					cRadio.setSelected(true);
				} else if(paper.getAnswers()[count]=='D'){
					dRadio.setSelected(true);
				}
			}
			
		}else if(str.equals("下一题>>")){
			//code6:完成下一题切换功能 (5%)
			if(count==9){
				JOptionPane.showMessageDialog(this,"已经是最后一题");
			}else{
				count++;
				getJTextArea().setText(
						(count+1)+"."+paper.getAllQuestions().get(count).toString()
				);
				
				remainQuestions.setText(paper.getQuestionNumber()-count+"");
			
				//使每个按钮置空,根据选定的答案设置它
				aRadio.setSelected(false);
				bRadio.setSelected(false);
				cRadio.setSelected(false);
				dRadio.setSelected(false);
				if(paper.getAnswers()[count]=='A'){
					aRadio.setSelected(true);
				} else if(paper.getAnswers()[count]=='B'){
					bRadio.setSelected(true);
				} else if(paper.getAnswers()[count]=='C'){
					cRadio.setSelected(true);
				} else if(paper.getAnswers()[count]=='D'){
					dRadio.setSelected(true);
				}
			}
			
			
		}else if(str.equals("A")){
			//每次点击按钮,要将其余的置空,因为只能选取一个答案
			paper.getAnswers()[count]='A';
			bRadio.setSelected(false);
			cRadio.setSelected(false);
			dRadio.setSelected(false);
		}else if(str.equals("B")){
			paper.getAnswers()[count]='B';
			aRadio.setSelected(false);
			cRadio.setSelected(false);
			dRadio.setSelected(false);
		}else if(str.equals("C")){
			paper.getAnswers()[count]='C';
			aRadio.setSelected(false);
			bRadio.setSelected(false);
			dRadio.setSelected(false);
		}else if(str.equals("D")){
			paper.getAnswers()[count]='D';
			aRadio.setSelected(false);
			bRadio.setSelected(false);
			cRadio.setSelected(false);
		}
	}
	
	/**
	 * 完成交卷功能
	 *
	 */
	private void submitPaper(){
		//code 4:完成客户端交卷功能
		
		int score = paper.getScore();//调用考卷的评分方法,得到最后得分
		
		Request req = new Request(Request.FINISH_REQ);
		req.addParameter("score",score);
		req.addParameter("stuId",student.getId());
		Response res = action.doAction(req);
		
		boolean flag =(Boolean)res.getParameter("do");
		
		if(flag){
			//在客户端显示下成绩
			JOptionPane.showMessageDialog(ExamMainFrame.this,"您最终的成绩为:"+score);
			System.exit(0);
				
		}else{
			JOptionPane.showMessageDialog(ExamMainFrame.this,"提交失败，请稍后再提交");
		}
	}
	
	
	/**
	 * 内部类，用来刷新考试剩余时间，每秒刷新一次。
	 */
	class TimeRefreshThread extends Thread{
		public void run() {
			long allTime = paper.getLimitTime();//秒
			while (allTime >= 0) {
				try {
					allTime--;
					Thread.sleep(1000);
					remainTime.setText(allTime/60/60+":"+allTime/60
							%60+":"+allTime%60);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	


	/**
	 * 初始化方法，该方法完成对界面的初始化，并将界面中的显示的部分数据初始化。
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(775, 515);
		this.setContentPane(getJContentPane());
		this.setTitle("达内科技标准化考试系统主界面--"+student.getName()+"--"+paper.getSubject());
		
		studentName.setText(student.getName());
		studentID.setText(student.getId()+"");
		subject.setText(paper.getSubject());
		
		//设置剩余试题数初始值
		remainQuestions.setText(paper.getQuestionNumber()+"");
		
		//设置剩余时间初始值 以及 颜色
		remainTime.setText(paper.getLimitTime()/60/60+":"+paper.getLimitTime()/60
				%60+":"+paper.getLimitTime()%60);
		remainTime.setForeground(Color.red);
		
		
		//设置初始的第一道考试题目 以及 题目的字体和颜色
		jTextArea.setText(
			"1."+paper.getAllQuestions().get(0).toString()
		);
		jTextArea.setFont(new Font("宋体",Font.BOLD,20));
		jTextArea.setForeground(Color.pink);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			remainTime = new JLabel();
			remainTime.setBounds(new Rectangle(644, 393, 106, 36));
			remainTime.setText("");
			remainQuestions = new JLabel();
			remainQuestions.setBounds(new Rectangle(25, 396, 107, 35));
			remainQuestions.setText("");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(643, 372, 98, 21));
			jLabel8.setText("剩余时间：");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(24, 373, 94, 23));
			jLabel7.setText("剩余试题：");
			subject = new JLabel();
			subject.setBounds(new Rectangle(531, 46, 85, 19));
			subject.setText("");
			studentID = new JLabel();
			studentID.setBounds(new Rectangle(378, 47, 79, 18));
			studentID.setText("");
			studentName = new JLabel();
			studentName.setBounds(new Rectangle(226, 45, 80, 19));
			studentName.setText("");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(150, 45, 77, 19));
			jLabel3.setText("考生姓名：");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(314, 46, 65, 19));
			jLabel2.setText("考生编号：");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(468, 46, 64, 19));
			jLabel1.setText("考试科目:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(172, 4, 415, 33));
			jLabel.setFont(new Font("Dialog", Font.BOLD, 24));
			jLabel.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel.setText("welcome to tarena exam center");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(studentName, null);
			jContentPane.add(studentID, null);
			jContentPane.add(subject, null);
			jContentPane.add(getJTextArea(), null);
			jContentPane.add(getARadio(), null);
			jContentPane.add(getBRadio(), null);
			jContentPane.add(getCRadio(), null);
			jContentPane.add(getDRadio(), null);
			jContentPane.add(getPrevious(), null);
			jContentPane.add(getNext(), null);
			jContentPane.add(getHandIn(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(remainQuestions, null);
			jContentPane.add(remainTime, null);
		}
		return jContentPane;
	}

}  
