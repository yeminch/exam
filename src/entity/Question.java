package entity;

import java.util.ArrayList;
import java.util.List;
/**
 * Question对象代表一道试题。包含题干和四个选项以及正确答案
 * @author new
 *
 */
public class Question implements java.io.Serializable{
	public static final long serialVersionUID=9080302019871213L;
	
	private String title;//题干
	private int score;//分值
	private List<String> items=new ArrayList<String>();//若干选项
	private char answer;//正确答案
	
	public Question(){
		
	}

	
	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public char getAnswer() {
		return answer;
	}

	public List<String> getItems() {
		return items;
	}

	public String getTitle() {
		return title;
	}
	
	public void setAnswer(char answer) {
		this.answer = answer;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	//要得到题目内容,直接输出该问题对象即可显示内容,因为已经覆盖了toString方法
	public String toString(){
		StringBuffer sb=new StringBuffer(title+"\n");
		for(int i=0;i<items.size();i++){
			sb.append((char)(i+'A')+","+items.get(i)+"\n");
		}
		return sb.toString();
	}
}
