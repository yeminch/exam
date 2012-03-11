package dao;
import entity.Student;

public interface StudentDao {
	/**
	 * 该方法根据给定的学号和密码从考生数据中获得某个考生对象。
	 * @param id 学号
	 * @param passwd 密码
	 * @return 如果底层数据中有匹配的考生则返回该考生对象；如果没有匹配的考生则返回null。
	 */
	public Student getStudent(String id,String passwd);
	
	/**
	 * 该方法更新底层数据中考生的成绩
	 * @param id 考生编号
	 * @param score 新的成绩
	 * @return 如果更新成功返回true，否则返回false
	 */
	public boolean updateScore(String id,int score);
}
