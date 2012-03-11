package client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import entity.Request;
import entity.Response;

public class Action {
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Response res;
	
	private BufferedReader br;
	private String ip,port;//ip地址和端口
	
	public Action(){
		try {
			//从配置文件读取ip地址和端口，而不直接写入
			br=new BufferedReader(new InputStreamReader(new FileInputStream("client.cfg")));
			ip=br.readLine();
			port=br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//每次需要向服务器访问的时候，调用该方法
	public Response doAction(Request req) {
			try {
				//1.构建一个Socket
				s = new Socket(ip,Integer.parseInt(port));
				
				//2.先把数据发过去，请求服务器
				oos = new ObjectOutputStream(s.getOutputStream());
				oos.writeObject(req);
				oos.flush();
				
				//3.服务器消息发回来了
				ois = new ObjectInputStream(s.getInputStream());
				res = (Response) ois.readObject();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			finally{
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			}
		//4.要将服务器的回复 返回
		return res;
	}

}
