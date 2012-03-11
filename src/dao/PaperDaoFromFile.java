package dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import entity.Paper;
import entity.Question;

public class PaperDaoFromFile implements PaperDao{
	
	public Paper getPaper(String subjectname,String subjectpath) {
		BufferedReader br1=null;
		
		Paper paper = new Paper();//构造一份考卷
		paper.setSubject(subjectname);//设置试卷的名字
		paper.setLimitTime(3600);//给试卷设置时间：一小时
		
		try {
			//读取文本生成考试卷  subjectpath即为 “corejava.exm”
			br1=new BufferedReader(new InputStreamReader(new FileInputStream(subjectpath)));
			String str = null;
			
			while(true){
				//先读一行，如果是空的，表示题目没了,那么直接跳出循环
				if((str=br1.readLine())==null){
					break;
				}
				
				//如果不是空的，那么构造一个考试题
				Question question = new Question();
				//设置该题目的标题
				question.setTitle(str);
				
				//循环   设置题目的内容
				for(int i=0;i<4;i++){
					str=br1.readLine();
					if(str.indexOf("<T>")==0){
						str=str.substring(3);//抛掉前面的<T>
						question.setAnswer((char)('A'+i));//将正确答案写入到题目的 
					}
					//把内容加到了题目的集合中去存储
					question.getItems().add(str);
				}
				question.setScore(10);//自己随意定义了个分数
				//将题目加入到试卷当中
				paper.getAllQuestions().add(question);
			}
			
		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}
		
		//将考试卷 return
		return paper;
	}
	

}
