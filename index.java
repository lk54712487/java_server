package likai.Main.index;

import java.io.*;
//import java.io.FilterOutputStream;  

import java.lang.Integer;  
import java.io.DataOutputStream;  
import java.io.PrintWriter;  
import java.net.ServerSocket;  
import java.net.Socket; 
import java.nio.channels.*;

import likai.Main.des.DES;
import likai.Main.string.lk_string;
import likai.Main.index.socket_data_processing;
import likai.Main.sql.mysql;

import java.util.*; 

class lk_start{
	public static void main(String[] args){
	//	mysql mq = new mysql();
	//	mq.connect();
	//	System.out.print("----");
		lk_server st = new lk_server();
		st.lk_start();
	}
}

class lk_server extends Thread {  
    private Socket client;
	private int port = 10888;
	
	public lk_string lk_str = new lk_string();
	private static int client_num = 0;
	
	public lk_server(){
		
	}
	//你好
	public lk_server(Socket c) {  
        this.client = c;  
    } 
	
	public void set_port(int port){
		this.port = port;
	}
	
	public void lk_start(){
		try {
			this.lk_run();
		} catch (IOException ex) { 
			System.out.println("start server fail");
		}
	}
	
    public void lk_run() throws IOException {  
        ServerSocket server = new ServerSocket(this.port);  
		String hello = "欢迎...";
		System.out.println(hello);
        while (true) {  
			//accept
            lk_server mc = new lk_server(server.accept());
			mc.port = this.port;
            mc.start();
        }
    }  
	
	public void run() {
        try {  
            BufferedReader in = new BufferedReader(new InputStreamReader(  
                    client.getInputStream())); 
   //         FilterOutputStream byte_out;
			DataOutputStream byte_out = new DataOutputStream(client.getOutputStream()); ;
			this.client_num++;
			System.out.println("连接成功");
            // Mutil User but can't parallel 
			String str = "";
	//		in.reset();
			while (true) {	//
                str = in.readLine();
				
				lk_str.data_processing(client , this.client_num , str   , byte_out );
				
                if (str.equals("end") || str.equals("quit") || str.equals("exit")) 
                    break;  
            }  
            client.close();		//exit socket
			this.interrupt();	//exit thread
			
        } catch (Exception e) { 
			System.out.println(Thread.currentThread().getName() + "|duankai");
		} finally { }  
    }  
}

