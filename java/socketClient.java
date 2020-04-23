/**
 * @Description: socket客户端
 * @author: LuShao
 * @create: 2019-02-19 9:52
 **/
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class socketClient {
	public static void main(String[] args){
		try {
			//创建socket客户端，指定服务器地址和端口
			Socket socket=new Socket("localhost",10086);
			//获取输出流，并输出内容
			OutputStream os = socket.getOutputStream();
			PrintWriter pw=new PrintWriter(os);
			pw.write("I am client,plase help me!");
			pw.flush();
			//关闭输出流
			socket.shutdownOutput();

			//
			InputStream is = socket.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			String content=null;
			while ((content=br.readLine())!=null){
				System.out.println("server ansewer:"+content);
			}

			pw.close();
			is.close();
			br.close();
			os.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
