import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Description: 客户端
 * @author: LuShao
 * @create: 2019-02-19 13:27
 **/
public class NioClient {

	// 通道管理器
	private Selector selector;

	public NioClient init(int port,String ip) throws IOException {
		// 获取客户端通道
		SocketChannel socketChannel=SocketChannel.open();
		// 设置通道为非阻塞
		socketChannel.configureBlocking(false);
		// 获取通道管理器
		selector = Selector.open();
		// 客户端连接服务器 需要调用 channel.finishConnect()才能完成实际连接
		socketChannel.connect(new InetSocketAddress(ip,port));
		// 为通道注册SelectionKey.OP_CONNECT事件
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		return this;
	}

	public void listen() throws IOException {
		System.out.println("***client start***");

		// 轮询
		while (true){
			// 选择注册过的io操作的事件（第一次为SelectionKey.OP_CONNECT）
			selector.select();
			Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
			while (selectionKeys.hasNext()){
				SelectionKey selectionKey = selectionKeys.next();
				// 移除已选的key  防止重复
				selectionKeys.remove();
				if (selectionKey.isConnectable()){
					SocketChannel channel= (SocketChannel) selectionKey.channel();
					// 如果正在连接  则继续完成连接
					if (channel.isConnectionPending()){
						channel.finishConnect();
					}
					channel.configureBlocking(false);
					//channel.write(ByteBuffer.wrap(new String("send message to server").getBytes()));
					//channel.write(ByteBuffer.wrap(Utils.getHexBytes("123123123")));
					System.out.println("客户端连接成功~");
					// 连接成功后注册 接受服务器消息的事件
					channel.register(selector,SelectionKey.OP_READ);
				}else if (selectionKey.isReadable()){
					SocketChannel channel= (SocketChannel) selectionKey.channel();
					ByteBuffer buffer = ByteBuffer.allocate(10);
					channel.read(buffer);
					byte[] data = buffer.array();
					int size = buffer.position() * 2;
					String msg = Utils.bytesToHexString(data).substring(0,size);
					System.out.println("来自于服务器端的消息："+msg+",长度："+size);
				}
			}
		}
	}

	//public static void main(String[] args) throws IOException {
	//    new NioClient().init(10086,"192.168.0.126").listen();
	//}
}
