package entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Paper类的对象代表一套试卷，包含若干Question（试题）
 */
public class Paper implements java.io.Serializable{
	private String subject;//考试科目名称
	private int limitTime;//考试限制时间（单位:分钟）
	private List<Question> allQuestions=new ArrayList<Question>();//所有试题对象
	private char[] answers = new char[10];//学生的选择,数组默认大小10
	public static final long serialVersionUID=56701230908070210L;
	
	public Paper(){
		
	}
	
	public Paper(String subject ,int limitTime,List<Question> allQuestions){
		this.subject=subject;
		this.limitTime=limitTime;
		this.allQuestions=allQuestions;
		answers=new char[getQuestionNumber()];
	}
	
	public Paper(String subject,int limitTime){
		this(subject,limitTime,new ArrayList<Question>());
	}
	
	public boolean  addQuestion(Question q){
		return allQuestions.add(q);
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<Question> getAllQuestions() {
		return allQuestions;
	}

	public void setAllQuestions(List<Question> allQuestions) {
		this.allQuestions = allQuestions;
	}

	public char[] getAnswers() {
		return answers;
	}

	public void setAnswers(char[] answers) {
		this.answers = answers;
	}
	
	/**
	 * 返回这套试卷的总试题数。
	 * @return
	 */
	public int getQuestionNumber(){
		return allQuestions.size();
	}
	
	/**
	 * 设置第index道试题的考生选择的答案。
	 * @param index
	 * @param answer
	 */
	public void setAnswerAt(int index,char answer){
		answers[index]=answer;
	}
	
	
	
	public int getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(int limitTime) {
		this.limitTime = limitTime;
	}

	//输考卷对象时候,覆盖toString方法，就会按照覆盖后的方法输出
	public String toString(){
		StringBuffer sb=new StringBuffer(subject+"考试试题\n");
		for(int i=0;i<allQuestions.size();i++){
			sb.append((i+1)+","+allQuestions.get(i));
		}
		return sb.toString();
	}
	
	/**
	 * 该方法根据考生选择的答案，求出该考生的得分（答对题的个数）。
	 * @return
	 */
	public int getScore(){
		int score = 0;
		for(int i=0;i<getAllQuestions().size();i++){
			Question q = getAllQuestions().get(i);
			//比较试卷里的正确答案和学生选择的答案
			if(q.getAnswer()==getAnswers()[i]){
				score=score+10;
			}
		}
		
		return score;
	}

}
