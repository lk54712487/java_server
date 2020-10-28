//package socket;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class socket_server{
	public static void main(String [] args) throws IOException{
		socket_server socketService = new socket_server();
        socketService.oneServer();	
	}
	
	public void oneServer(){
		try{
			ServerSocket server = null;
			try{
				server = new ServerSocket(12345);
				System.out.println("开启服务成功");	//成功
			}catch(Exception e){
				System.out.println("not accept"+e);
			}
			Socket socket = null;
			try{
				socket = server.accept();
			}catch(Exception e){
				System.out.println("Error."+e);
			}
			
			byte[] buf = new byte[10240]; 
			int len = 0;
			BufferedInputStream readbuff = new BufferedInputStream(socket.getInputStream());
			len = readbuff.read( buf );
			if(len != -1){
				System.out.println(buf.toString());
			};
//			System.out.println(buf.toString());
			System.out.println("退出。。。");
			socket.close();
			readbuff.close();
			server.close();
		}catch(Exception e){
			System.out.println("Error."+e);
		}
	}
}