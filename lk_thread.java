package likai.Main.lk_thread;

import java.io.*;
//import java.io.FilterOutputStream;  
/*
	ÈÝÆ÷Àà
*/
import java.lang.Integer;  
import java.io.DataOutputStream;  
import java.io.PrintWriter;  
import java.net.ServerSocket;  
import java.net.Socket; 
import java.util.*; 
import java.text.SimpleDateFormat;
import java.lang.Math;
import java.util.concurrent.ConcurrentHashMap;

import likai.Main.des.DES;
import likai.Main.index.socket_data_processing;

public class lk_thread extends Thread{
	public socket_data_processing out_str = new socket_data_processing();
	public String type = "";
	public ConcurrentHashMap<String,String> s_container = new ConcurrentHashMap<String,String>();
	public ConcurrentHashMap<String,String> c_container = new ConcurrentHashMap<String,String>();
	public lk_thread(){
		this.start();
	}
	public String [] body;
	public Date date;
	public String key;
	public String value;
	public void run(){
		
		while(true){
			String str = "";
			for(Map.Entry<String, String> entry : s_container.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				/*
				if(type.equals("zidan")){
					body = value.split(",");
					int f_time = Integer.parseInt(body[2]) ;
				//	int life = Integer.parseInt(body[8]);
					date = new Date();
					int c_time = (int)(date.getTime() / 1000);
					if(c_time - f_time > 20){
						s_container.remove(key);
						continue;
					}
				}
				*/
				if(type.equals("role_move_and_action")){
					body = value.split(",");
					int f_time = Integer.parseInt(body[14]) ;
					date = new Date();
					int c_time = (int)(date.getTime() / 1000);
//					System.out.println(c_time + "|" + f_time + "|" + (c_time - f_time));
					if(c_time - f_time  > 10){
						if(c_time - f_time > 30){
							s_container.remove(key);
							continue;
						}
						if(body[8].equals("0")){
							String chongzu = "";
							body[8] = "1";
							for(int z = 0 ; z < body.length ; z++){
								if(chongzu.equals("")){
									chongzu += body[z];
								}else{
									chongzu += "," + body[z];
								}
							}
							value = chongzu;
						}
						
					}
				}
				
				//System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				if(str.equals("")){
					str = value;
				}else{
					str += "#" + value;
				}
			}
			
			if(!type.equals("")){
				str = out_str.return_one_str(str,type);
		//		System.out.println("s:" +  str);
				c_container.put(type,str);
			}
			
			try{
				Thread.sleep(1);
		//		System.out.println("s:" +  str);
			}catch (Exception e) { 
			
			}
			
		}
	}
	
	public String get(String type){
		/*
		if(type.equals("zidan")){
			System.out.println(c_container.get(type));
		}
		*/
		return c_container.get(type);
	}
}
