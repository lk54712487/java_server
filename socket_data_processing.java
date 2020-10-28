package likai.Main.index;

import java.io.*;
//import java.io.FilterOutputStream;  

import java.lang.Integer;  
import java.io.DataOutputStream;  
import java.io.PrintWriter;  
import java.net.ServerSocket;  
import java.net.Socket; 
import java.util.*; 
import java.text.SimpleDateFormat;
import likai.Main.des.DES;
import java.lang.Math;
/*
接收数据格式
一共6段
分隔符5位+  加密字符串+ 分隔符+ 客户端时间戳+ 分隔符+ 内容md5+ 分隔符+ 类型  + 分隔符+ 发送次数+ 分隔符+ 服务器时间戳+ 分隔符+内容
abcde    +  des_str   + abcde + time        + abcde + md5    + abcde + other + abcde + len     + abcde + server_time + abcde + content
*/
public class socket_data_processing{
	
	public String socket_out_str = "";
	//new DES().encrypt(new DES().MD5(DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff"))  ,  "ab").Substring(1, 4); 
	public int fengeweizhi = 5;	//分隔符位置
	public String fengefu = "";	//分隔符
	public String des_str = "";	//des加密字符串
	public String time = "";	//时间戳
	public String md5 = "";		//md5加密字符串和des解密可以做对比
	public String other = "";	//附加内容，可以拿来做类型type
	public String len = "";		//客户端发送的次数
	public String server_time = "";	//发送过来服务器上次时间的内容
	public String content = "";	//发送过来的内容
	
	public socket_data_processing( String str ){
		if(!str.equals("")){
			this.socket_out_str = str; 
			this.chuli(); 
		}
	}
	
	public socket_data_processing( ){
		
	}
	
	public void chuli(){
		if(!this.socket_out_str.equals("")){
			fengefu = socket_out_str.substring(0,fengeweizhi);
			socket_out_str = socket_out_str.substring( fengeweizhi , socket_out_str.length());
			String [] body;
			body = socket_out_str.split(fengefu); 
			if(body.length >= 6){
				des_str = body[0];
				time 	= body[1];
				md5 	= body[2];
				other 	= body[3];
				len 	= body[4];
				server_time = body[5];
				content = body[6];
			}
		}
	}
	
	
	/*
	public String return_str(String str){
		String ret = "";
		String fengefu = this.get_header(str);
		ret = fengefu + str;
		byte [] buff = ret.getBytes();
		double len = buff.length;
		int l = 1;
		if(len > 8000){
			len = 7.0;
			l = (int)Math.ceil((len+15.0)/8192);
		}
		String server_time = this.get_server_time();
		String head = String.format("%010d", l); 
		ret = fengefu + head + fengefu + server_time + fengefu +ret;
		return ret;
	}
	
	public String return_one_str(String str){
		String ret = "";
		String fengefu = this.get_header(str);
		ret = str;
		int l = 1;
		String server_time = this.get_server_time();
		String head = String.format("%010d", l); 
		ret = fengefu + head + fengefu + server_time + fengefu + ret;
		return ret;
	}
	*/
	/*
	服务器返回数据处理
	str  返回的数据
	返回byte长度超过8000字节就循环发送
	10.0代表开头的返回长度加10位，因为要返回一个循环数，低于8000返回1大于8000以上的用循环返回
	分隔符长度为5位
	例子：
	最终返回的字符串  分隔符  +   长度  + 分隔符 +服务器返回服务器时间戳+ 返回内容
	*/
	public String return_one_str(String str , String type){
		String ret = "";
		String fengefu = this.get_header(str);
		ret = str;
		int l = 1;
		String server_time = this.get_server_time();
		String head = String.format("%010d", l); 
		ret = fengefu + head + fengefu + server_time + fengefu + type + fengefu + ret;
		return ret;
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
	
	private String get_server_time(){
		return System.currentTimeMillis() + "";
	}
	
	public void chuli(String str){
		socket_out_str = str;
		this.chuli();
	}
	
	public String get_len(){
		return len;
	}
	
	public String get_des(){
		return des_str;
	}
	
	public String get_time(){
		return time;
	}
	
	public String get_md5(){
		return md5;
	}
	
	public String get_content(){
		return content;
	}
	
	public String get_other(){
		return other;
	}
	
	public String get_type(){
		return other;
	}
}

