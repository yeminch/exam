package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import entity.Student;

public class StudentDaoFromFile implements StudentDao {
	private File file;// 这里的file 即为 "student.dat"

	public StudentDaoFromFile(File f) {
		this.file = f;
	}

	public Student getStudent(String id, String passwd) {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);

			// 读取配置文件
			String str = null;
			while ((str = br.readLine()) != null) {
				String s[] = str.split(":");
				String i = s[0]; // 学生的编号id
				String p = s[2]; // 学生的密码
				// 如果输入的编号和密码在文件中找得到
				if (i.equals(id + "") && p.equals(passwd)) {
					Student stu = new Student(s[1], Integer.parseInt(s[0]),
							s[2], Integer.parseInt(s[3]));

					// 构造了当前学生对象，并return
					return stu;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 没有找到对应的账户和密码，则返回空
		return null;
	}

	public boolean updateScore(String id, int score) {
		// Code12 完成层updateScore方法的实现 (15%)
		List<String> list = new ArrayList<String>();

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;

		try {
			// 修改分数到一个集合
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);

			String str = null;
			while ((str = br.readLine()) != null) {
				String s[] = str.split(":");
				if (s[0].equals(id + "")) {
					// 将要修改的那一行数据做修改
					list.add(s[0] + ":" + s[1] + ":" + s[2] + ":" + score);
				} else {
					list.add(str);
				}
			}

			// 从集合将数据读回来
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos);
			bw = new BufferedWriter(osw);

			for (int i = 0; i < list.size(); i++) {
				bw.write(list.get(i));
				bw.newLine();
				bw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// 修改成功则true
		return true;
	}

}
