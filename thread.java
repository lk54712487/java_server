
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class thread extends Thread{
	public static void main(String [] args) {
		thread th = new thread();
		th.start();
	}
	
	public void run(){
		System.out.println("nihao");
	}
}
