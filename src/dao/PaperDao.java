package dao;
import entity.Paper;

public interface PaperDao {
	/**
	 * 根据给定的科目，返回该科目的一份试卷。
	 * @param subject 科目
	 * @return 如果访问成功则返回Paper对象，否则返回null。
	 */
	public Paper getPaper(String subjectname,String subjectpath);
}
