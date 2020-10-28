package likai.Main.chat;

import java.lang.Math;
import java.io.*;
import java.lang.String;




import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class lk_chat{
	public chat_info in = new chat_info();
	public chat_info out = new chat_info();
	public static ConcurrentHashMap<String ,chat_info> chatinfo = new ConcurrentHashMap<String,chat_info>();
	public String processing(String connect , String id ){
		String ret = "fail";
		if(!chatinfo.containsKey(id)){
			in = new chat_info();
			in.chat.put("0",connect);
//			System.out.println(in.chat.get(in.num + "") + "--start");
			in.num+=1;
			chatinfo.put(id,in);
			ret = "success";
		}else{
			in = chatinfo.get(id);
			in.chat.put(in.num+"",connect);
//			System.out.println(in.chat.get(in.num + "") + "--two");
			in.num+=1;
			if(in.num >= in.max_num){
				in.num = 0;
			}
			chatinfo.put(id,in);
			ret = "success";
		}
//		System.out.println(id + "|" + (in.num -1) + "|" + chatinfo.get(id).chat.get( (in.num -1) + "" ) + "--1");
		return ret; 
	}
	
	public String get_info(String id,String num){
		String ret = "";
//		System.out.println(id+ "|" + num);
		
		if(chatinfo.containsKey(id)){
			out = chatinfo.get(id);
			if(out.chat.containsKey(num+"")){
				ret = chatinfo.get(id).chat.get(num);
			}	
		}
		if(ret != ""){
			System.out.println(id + "|" + num + "|ÄãºÃ--" + "ret:" + ret);
		}
		return ret; 
	}
	
	//huo qu tiao shu
	public String get_tiaoshu(String id){
		String ret = "";
		int ret_num = 0;
		int s_num = 20;
		if(chatinfo.containsKey(id)){
			out = chatinfo.get(id);
			if(out.num - s_num < 0){
				ret_num = 0;
			}else
			if(out.num - s_num >= 0){
				ret_num = out.num - s_num;
			}
			ret = ret_num + "_" + out.max_num;
		}else{
			in = new chat_info();
			chatinfo.put(id,in);
			if(in.num - s_num < 0){
				ret_num = 0;
			}else
			if(in.num - s_num >= 0){
				ret_num = in.num - s_num;
			}
			ret = ret_num + "_" + in.max_num;
		}
		return ret; 
	}
}

class chat_info{
	public int num = 0;
	public int max_num = 100;	//zui da tiao shu
	public ConcurrentHashMap<String,String> chat = new ConcurrentHashMap<String,String>();
}
