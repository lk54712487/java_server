package likai.Main.string;

import java.lang.Math;

import java.security.MessageDigest;
import java.io.*;
import java.io.DataOutputStream;  
import java.net.ServerSocket;  
import java.net.Socket; 
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.*; 
import java.util.HashMap;

import likai.Main.des.DES;
import likai.Main.update.lk_update;
import likai.Main.update.SuperFile;
import likai.Main.login.lk_login;
import likai.Main.auth.lk_auth;
import likai.Main.user.lk_user_register;
import likai.Main.hash.lk_hashthread;
import likai.Main.chat.lk_chat;

import likai.Main.index.socket_data_processing;
import likai.Main.game.game_list;

public class lk_string{
	private static String public_data = "";
	private static String private_data = "";
	private static Map<String,String> username_map=new HashMap<String,String>();
	private static socket_data_processing socket_data;
	 
	private String r_header = "";
	private String r_body = "";
	private String r_footer = ""; 
	private String r_type = "string";	
	private byte [] buff ;	
	private String r_ret = "";	//return data
	private int analysis = 2; 	//1.yes		2.no	3.auto
	public int ret_byte_size = 0;	//return data
	private String KEY = "likai1991";	//return data
	private SuperFile fs = null;	//return data
	
	public void data_processing(Socket th,int client_num, String str , DataOutputStream byte_out ){
		
		String ret = "-";
		socket_data_processing out_str = new socket_data_processing(str);
		String type = out_str.get_other();
		String content = out_str.get_content();
		
		String error_msg = "";
	//	ret = out_str.return_str(ret);
		game_list game = new game_list();
		boolean is_return = true;
//		System.out.println(str + "(" + Thread.currentThread().getName() + ")");
//		System.out.println("close_thread:" + that.getName());
		switch(type){
			case "send_yzm":	
				lk_user_register role_register = new lk_user_register();
				role_register.send_yzm(content);
				ret = out_str.return_one_str("success",type);
		//		System.out.println(ret);
			break;
			
			case "user_register":	
				lk_user_register role_register2 = new lk_user_register();
				error_msg = role_register2.user_register(content);
				ret = out_str.return_one_str(error_msg,type);
		//		System.out.println(ret);
			break;
			
			case "user_login":	
				lk_user_register role_login = new lk_user_register();
				error_msg = role_login.user_login(content);
				ret = out_str.return_one_str(error_msg,type);
			break;
			
			case "is_update_log":	
				lk_update update2 = new lk_update();
				update2.set_content(content);
				ret = update2.is_update_log();
				ret = out_str.return_one_str(ret,type);
			break;
			
			
			
			case "get_file":	//download
				System.out.println(content);
				lk_update up2 = new lk_update();
				up2.body = content;
				this.fs = up2.get_file();
				ret = this.fs.file_len() + "";
				System.out.println("file_len" + ret);
				byte[] buff = ret.getBytes();
				try{
					byte_out.write(buff);
					byte_out.flush();
				}catch (Exception e) { } finally { }  
				this.byte_output_return(byte_out);
				is_return = false;
			break;
			case "role_move_and_action":
				game.clint_time = out_str.get_time();
				ret = game.auto_register_and_update_role(type,content);
			break;
			case "get_name":
				String name = game.get_name();
				ret = out_str.return_one_str(name,type);
			break;
			
			case "zidan":
				Date date = new Date();
				long time = date.getTime();
				String timer = String.valueOf(time);
				ret = game.auto_register_and_update_zidan(type,timer,content);
				ret = "-";
			break;
			
			case "send_chat":	//liao tian jie shou
				lk_chat chat = new lk_chat();
				ret = chat.processing(content , out_str.get_len());
				ret = out_str.return_one_str(ret,type);
			break;
			
			case "get_info":	//liao tian fa song
				lk_chat chat2 = new lk_chat();
				ret = chat2.get_info( content , out_str.get_len());
				ret = out_str.return_one_str(ret,type);
			break;
			
			case "get_tiaoshu":	//liao tian fa song
				lk_chat chat3 = new lk_chat();
				ret = chat3.get_tiaoshu( content );
				ret = out_str.return_one_str(ret,type);
			break;
			
			case "app_quit":
				game.clint_time = out_str.get_time();
				ret = game.app_quit(type,content);
			break;
			
			case "get_zidan":
				ret = game.get_zidan_list("zidan");
			break;
			case "test":
				ret = out_str.return_one_str(content,type);
			break;
		}
		
		try{
			if(is_return){
				String is_close = Thread.currentThread().getName();
				String [] close_arr = is_close.split("_");
				
				if(close_arr[0].equals("close")){
//					System.out.println("close_thread:" + Thread.currentThread().getName());
					ret = out_str.return_one_str("close" , "Repeat_login");
					lk_hashthread.Threadres.get(close_arr[0] + "_" + close_arr[1]).interrupt();
				}
				
		//		System.out.println("OUT");
				byte[] buff = ret.getBytes();
				int file_len = buff.length ; 
				int size = 1024 * 8;
				if(file_len < size){
					byte_out.write(buff);
					byte_out.flush();
				}else{
					int for_len = 0;
					if(file_len % size == 0){
						for_len = file_len / size;
					}else{
						for_len = file_len / size + 1;
					}
					int shengyu = buff.length;
					int offset = size;
					for(int i = 0 ; i < for_len ; i ++){
						shengyu = buff.length - i * offset;
						if (shengyu  < size && shengyu > 0) {
							size = shengyu;
						}
						byte_out.write( buff , i * offset , size);
						byte_out.flush();
					}
				}
			}
		}catch (Exception e) { } finally { }  
		
//		 out.println(ret);
//		 out.flush();
		 /*
		 String len = this.get_fiexd(first,"len");
		 int s_len = Integer.parseInt(len);
		 String str = "";
		 for(int i = 0 ; i < s_len ; i++){
			 try{
				str += in.readLine();
			 }catch (Exception e) {
				 
			 }
		 }
		 		
		 switch(this.analysis){
			 case 1:
				str = this.analysis_str(str);
			 break;
			 case 3:
				str = this.analysis_str(str);
			 break;
		 }
		 if(!str.isEmpty()){
			 String [] st = str.split("\\|");
			 String header = st[0];
			 String body = st[1];
			 String footer = st[2];
			 
			 String address = th.getInetAddress().toString();	
			 int port = th.getPort();	
	//		 String msg = address + ":" + port + "header:{" +header+"} body:{" + body +"}" + "footer{"+footer+"}"; 
			 this.public_data += body;
			 this.private_data = body;
	//		 String msg = "{"+address + "}:" + port + "header:{" +header+"} body:{" + body +"}" + "footer{"+footer+"}"; 
			 
			 String type = this.get_fiexd(header,"type");
			 
			 switch(type){
				 case "is_update_log":
					lk_update update2 = new lk_update();
					update2.body = body;
					update2.footer = footer;
					this.r_type = "string";
					this.r_ret = update2.is_update_log();
				 break;
				 case "login":
					lk_login login = new lk_login();
					login.body = body;
					login.footer = footer;
					this.r_type = "string";
					this.r_ret = login.is_login();
//					System.out.println(this.r_ret);
					String [] user = this.r_ret.split("\\|");
					String u = user[1];
					String id = this.get_fiexd(u,"id");
					String u_key = this.get_fiexd(u,"u_key");
					username_map.put(id,u_key);
	//				System.out.println(username_map);
				 break;
				 case "get_file":
					lk_update up2 = new lk_update();
					up2.body = body;
					up2.footer = footer;
					up2.f_size = this.ret_byte_size;
					this.r_type = "byte";
					this.fs = up2.get_file();
				 break;
				 case "get_time":	

					lk_auth FirstAuth = new lk_auth();
					String code = this.get_fiexd(body,"key");
					boolean is_auth = FirstAuth.first_auth(code);
					this.r_type = "string";
					if(is_auth == true){
						this.r_ret = "aa|-|-";
					}
					
				 break;
				 case "test1":	//语言
					this.r_type = "string";
//					System.out.println(body);
					this.r_ret = body;
				 break;
				 case "position":	//位置
				 	this.r_type = "string";
					System.out.println(str);
					this.r_ret = body;
				 break;
			 }	 
			 
		 }
		 */
	}
	 
	private String analysis_str(String str){
//	 	String KEY = "likai1991";
		String ret = "";
		String type = "";
		String header = "";
		String [] st = str.split("\\|");
		header = st[0];
		if(!header.isEmpty()){
			type = this.get_fiexd(header,"type");
			if(!type.isEmpty()){
				
				ret = str;
				return ret;
			}
		}
		 
		String strsss = str;
		DES des = new DES(strsss);
		String str2 = des.decrypt(strsss);
//		System.out.println(str2);
		 
		st = str2.split("\\|");
		header = st[0];
		if(!header.isEmpty()){
			type = this.get_fiexd(header,"type");
			if(!type.isEmpty()){
				ret = str2;
				return ret;
			}
		}
	//	System.out.println(ret);
		return ret;
	}
	 
	private String get_fiexd(String str , String fiexd){
		String ret = "";
		String [] tt = str.split(",");
		boolean is_break = false;
		if(tt.length > 0){
		 	for(int i = 0 ; i < tt.length; i++){
				if(is_break){
					break;
				}
				String [] ttt = tt[i].split(":");
				if(ttt.length > 0){
					for(int j = 0 ; j < ttt.length; j++){
						if(is_break){
							break;
						}
						if(fiexd.equals(ttt[j])){
							ret = ttt[j + 1];
							is_break = true;
							break;
						}
					}
				}
			}
		}
		return ret;
	}
	public String get_rettype(){
		return this.r_type;
	}
	public String data_return(){
		String str = this.r_ret;
		if(this.analysis == 1 || this.analysis == 3){
			DES des = new DES(str,KEY);
			str = des.encrypt();
		}
		return str;
	}
	 /*
	public byte [] byte_data_return(){
	 //	byte [] b = new byte[this.ret_byte_size];
		return this.buff;
	}
	 */
	public int file_len(){
	 	return fs.file_len();
	}

	public void byte_output_return(DataOutputStream output){
		int len = this.fs.getPart();
		try{
			byte [] buff = null;
			for(int i = 0 ; i < len ; i ++){
	//			System.out.println(i+"");
				if((buff  = this.fs.read()) != null ){
					
					output.write(buff);
					output.flush();
				}
			}

			this.fs.close();
		}catch(Exception e){
			System.out.println("send fail");
		}
		
	}

	private void set_r_header(String str){
		this.r_header = str;
	}
	private void set_r_body(String str){
		this.r_body = str;
	}
	private void set_r_footer(String str){
		this.r_footer = str;
	}
	private void auto_r_ret(){
		this.r_ret = this.r_header + "|" + this.r_body + "|" + this.r_footer;
	}
}

/*
		String KEY = "abcdef";
		
		String strsss = "abcdef";
		
		DES des = new DES(strsss,KEY);
		
		String str = des.encrypt();
		//jia mi hou
		System.out.println(str);
		
		String str2 = des.decrypt(str,KEY);
		//jie mi de
		System.out.println(str2);
*/
