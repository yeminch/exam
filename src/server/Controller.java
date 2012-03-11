package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import dao.PaperDao;
import dao.PaperDaoFromFile;
import dao.StudentDao;
import dao.StudentDaoFromFile;
import entity.Paper;
import entity.Question;
import entity.Request;
import entity.Response;
import entity.Student;

public class Controller {
	private StudentDao stuDao;
	private PaperDao papDao;
	private BufferedReader br;
	private String stuFile,papFile;
	
	public Controller(){
		try {
			br=new BufferedReader(new InputStreamReader(new FileInputStream("server.cfg")));
			stuFile=br.readLine();//拿到学生信息存储的内容
			papFile=br.readLine();//拿到考卷信息存储的内容
		} catch (Exception e) {
			e.printStackTrace();
		}
		//得到了学生信息存放的地址,并实现了接口
		stuDao=new StudentDaoFromFile(new File(stuFile.split("=")[1]));
		papDao=new PaperDaoFromFile();
	}
	
	//接收全部任务，判断后交给不同的方法去执行
	public Response execute(Request req){
		Response res=null;
		//先判断请求的类型,交给不同的方法去执行
		switch (req.getType()) {
		case Request.LOGIN_REQ: res=login(req); break;
		case Request.STARTEXAM_REQ:res=startExam(req);break;
		case Request.FINISH_REQ:res=finish(req);break;
		default: break;
		}
		
		return res;
	}
	
	//交卷，将得分写入到学生信息里
	private Response finish(Request req){
		int score=(Integer)req.getParameter("score");
		String id=(String)req.getParameter("stuId");
	
		////问接口拿数据-是否将得分写入到配置文件
		boolean flag = stuDao.updateScore(id, score);
		
		Response res = new Response(Response.FINISH_RES);
		res.addParameter("do",flag);
		
		return res;
	}
	
	//开始考试，得到试卷的方法
	private Response startExam(Request req){
		//考试卷的名字
		String subjectname=(String)req.getParameter("subjectname");
		
		String subjectPath=null;//考试卷的地址
		BufferedReader br=null;
		try {
			br=new BufferedReader(new InputStreamReader(new FileInputStream("subject.cfg")));
			String str=null;
			while((str=br.readLine())!=null){
				//如果科目名一致则加载相对应的试卷
				if(str.split("=")[0].equals(subjectname)){
					subjectPath = str.split("=")[1];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		//问接口拿数据-试卷
		Paper p = papDao.getPaper(subjectname,subjectPath);
		
		Response res = new Response(Response.STARTEXAM_RES);
		res.addParameter("examPaper",p);
		
		
		return res;
	}
	
	//登录的一些方法
	private Response login(Request req){
		String id=(String)req.getParameter("studentId");
		String passwd=(String)req.getParameter("studentPasswd");
		
		//问接口拿数据-学生
		Student s = stuDao.getStudent(id, passwd);
		
		Response res=new Response(Response.LOGIN_RES);
		res.addParameter("stu",s);
		
		return res;
	}
	
	

}

