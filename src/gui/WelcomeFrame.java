package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.Action;
import entity.Paper;
import entity.Request;
import entity.Response;
import entity.Student;

/**
 * 欢迎界面
 */
public class WelcomeFrame extends JFrame implements ActionListener {
	private JLabel title;
	private JLabel text;
	private JButton ok, cancel;
	private Student stu;

	private JComboBox box;
	private Action action;

	/**
	 * 构造方法
	 * 
	 * @param stu
	 *            成功登录的考生
	 */
	public WelcomeFrame(Student stu) {
		super("欢迎参加考试");

		action = new Action();
		this.stu = stu;

		title = new JLabel(stu.getName() + ",欢迎进入JAVA考试系统");
		// 如果考生未考试,那么成绩为-1
		if (stu.getScore() == -1) {
			text = new JLabel("请选择考试科目！");
			box = new JComboBox(new String[] { "corejava", "c++", "JavaWeb",
					"EJB" });

		} else {
			text = new JLabel("您已经完成了考试，不能重复考试!\n您的得分是：" + stu.getScore());
		}

		ok = new JButton("开始考试");
		cancel = new JButton("退出");
		init();
		addEventHandler();
	}

	private void init() {
		JPanel northPanel = new JPanel();
		northPanel.add(title);
		JPanel centerPanel = new JPanel();
		centerPanel.add(text);
		JPanel southPanel = new JPanel();

		// 没考试的话，才需要加载开始 考试按钮 和 下拉选择框
		if (stu.getScore() == -1) {
			southPanel.add(ok);
			centerPanel.add(box);
		}

		southPanel.add(cancel);

		this.add(southPanel, BorderLayout.SOUTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(northPanel, BorderLayout.NORTH);
	}

	private void addEventHandler() {
		ok.addActionListener(this);
		cancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("开始考试")) {
			startExam();

		} else if (e.getActionCommand().equals("退出")) {
			System.exit(0);
		}
	}

	/**
	 * 完成客户端开始考试功能
	 * 
	 */
	private void startExam() {
		// code3:完成客户端开始考试的功能 (18%)

		// 得到选中下拉框内容的名字 --考试卷的名字
		String s = (String) box.getSelectedItem();

		Request req = new Request(Request.STARTEXAM_REQ);
		req.addParameter("subjectname", s);

		Response res = action.doAction(req);
		Paper p = (Paper) res.getParameter("examPaper");

		// 收到一份试卷，判断是否为空
		if (p == null) {
			JOptionPane.showMessageDialog(WelcomeFrame.this, "试卷加载错误，请稍后再试！");
		} else {
			// 将 学生 和 试卷 一起传到考试界面
			new ExamMainFrame(stu, p).showMe();
			WelcomeFrame.this.dispose();
		}
	}

	public void showMe() {
		this.setSize(400, 300);
		this.setResizable(false);
		this.setLocation(400, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

}
