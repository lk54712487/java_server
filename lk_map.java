package likai.Main.lk_HashMap;

import java.io.*;
//import java.io.FilterOutputStream;  

import java.lang.Integer;  
import java.io.DataOutputStream;  
import java.io.PrintWriter;  
import java.net.ServerSocket;  
import java.net.Socket; 
import java.util.*; 
import java.text.SimpleDateFormat;
import java.lang.Math;

import likai.Main.des.DES;
/*
public class lk_map{
	
	String [] body;
	String [] body2;
	String keys = "";
	String values = "";
	String new_str = "";
	
    public static void main(String [] args){
		map aa = new map();
		aa.set("a","111");
	//	aa.set("a","222");
		aa.set("b","333");
		System.out.println(aa.values);
	}
	
}
*/
public class lk_map{
	public String [] body;
	public String [] body2;
	public String keys = "";
	public String values = "";
	public String new_str = "";
	public String fengefu = "#";
	public lk_map(){
	//	this.fengefu = this.get_header(this.values);
	}
    public void set(String key , String value){
		if(keys.equals("")){
			keys = key;
			values = value;
		}else{
			body = keys.split(this.fengefu);
			body2 = values.split(this.fengefu);
			boolean ins = false;
			new_str = "";
			for(int i = 0 ; i < body.length ; i++){
				if(body[i].equals(key)){
					ins = true;
					body2[i] = value;
				}
				if(new_str.equals("")){
					new_str = body2[i];
				}else{
					new_str += this.fengefu + body2[i];
				}
			}
			if(ins == false){
				keys += this.fengefu + key;
				values += this.fengefu + value;
			}else{
				values = new_str;
			}
		}
    }
	
	public String get(String key){
		String res = "";
		body = keys.split(this.fengefu);
		body2 = values.split(this.fengefu);
		for(int i = 0 ; i < body.length ; i++){
			if(body[i].equals(key)){
				return body2[i];
			}
		}
		return res;
	}
	
	private String get_header(String str) {
		String ret = "";
		SimpleDateFormat c_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = c_time.format(new Date());
		DES dd = new DES();
		ret = dd.MD5(date);
		ret = dd.encrypt(ret,"abcdef");
		ret = ret.substring(0, 5);
  //      String a =  new DES().encrypt(new DES().MD5(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")  ,  "abcdef").substring(0, 5);   
        int be = str.indexOf(ret);
        if (be == -1)
        {
            return ret;
        }
        else
        {
            return this.get_header(str);
        }
    }
	
	public boolean remove(String key){
		boolean ret = false;
		body = keys.split(this.fengefu);
		body2 = values.split(this.fengefu);
		new_str = "";
		for(int i = 0 ; i < body.length ; i++){
			if(body[i].equals(key)){
				continue;
			}
			if(new_str.equals("")){
				new_str = body2[i];
			}else{
				new_str += this.fengefu + body2[i];
			}
		}
		values = new_str;
		ret = true;
		return ret;
	}
}
