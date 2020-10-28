package likai.Main.login;

import java.util.Random;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;

import java.security.MessageDigest;
import java.io.*;
import likai.Main.des.DES;
//import likai.Main.string.DES;

public class lk_login{
	
	public String body = "";
	public String footer = "";
	private int delay = 0;	//delay_login
	
	public String is_login(){
		String ret = "type:success|id:0,u_key:-|code:3,msg:-";
		String username = this.get_fiexd(body,"username");
		String password = this.get_fiexd(body,"password");
		
		if(username.equals("lk54712487") && password.equals("123456")
		||	username.equals("lk54712488") && password.equals("123456")
		){
			Random rand = new Random();
			int id = rand.nextInt(100);
	//		id = 1;
			String strsss = username + password;
			DES des = new DES(strsss);
			String u_key = des.encrypt();
			ret = "type:success|id:"+id+",u_key:"+u_key+"|code:1,msg:success";
		}
		
		try {  
            Thread.sleep(this.delay); 
			return ret;			
		} catch (Exception e) {  
			e.printStackTrace();  
			return ret;
		}  
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
