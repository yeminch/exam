package entity;

/**
 * 该类的实例用来描述一位考生。
 * @author Administrator
 *
 */
public class Student implements java.io.Serializable{
	public static final long serialVersionUID=171890254638190L;
	
	private String name;
	private int id;
	private String passwd;
	private int score;
	
	public Student() {
		super();
	}

	public Student(String name, int id, String passwd,int score) {
		super();
		this.name = name;
		this.id = id;
		this.passwd = passwd;
		this.score=score;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
