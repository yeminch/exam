package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import entity.Request;
import entity.Response;

public class ServerThread extends Thread{
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Controller con;
	
	public ServerThread(Socket s){
		con=new Controller();//生成一个新的控制器处理请求和回复问题
		this.s=s;
		try {
			ois=new ObjectInputStream(s.getInputStream());
			oos=new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		Request req=null;
		try {
			//服务器一直在读数据
			req=(Request)ois.readObject();
			//全部任务都交给控制器去执行,该execute()方法 通用
			Response res=con.execute(req);
			
			oos.writeObject(res);
			oos.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(oos!=null){
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(ois!=null){
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
