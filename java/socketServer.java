import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description: socket服务端
 * @author: LuShao
 * @create: 2019-02-19 9:52
 **/
public class socketServer {
	public static void main(String[] args){
		ServerSocket serverSocket= null;
		try {
			// 创建服务端指定端口号
			serverSocket = new ServerSocket(10086);
			Socket accept = serverSocket.accept();
			InputStream inputStream = accept.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
			String clentStr=null;
			while ((clentStr=br.readLine())!=null){
				System.out.println("clent say:"+clentStr);
			}
			accept.shutdownInput();

			OutputStream os = accept.getOutputStream();
			PrintWriter pw=new PrintWriter(os);
			pw.write("请求已回应~");
			pw.flush();

			inputStream.close();
			br.close();
			os.close();
			pw.close();
			accept.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
