package likai.Main.game;

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
import java.util.concurrent.ConcurrentHashMap;

import likai.Main.lk_HashMap.lk_map;
import likai.Main.lk_thread.lk_thread;
import likai.Main.lk_thread.container;
import likai.Main.lk_thread.lk_thread;

public class game_list{
/*
public static void main(String[] args){
	HashMap<String,String> map = new HashMap<String,String>();
	map.put("a","123");
	map.put("a","456");
	for(String value : map.values()){
		System.out.println(value);
	}
}
*/
public static int number = 0;		//1,2,3
public static lk_map map = new lk_map();
public static lk_map name = new lk_map();
public static lk_thread th = new lk_thread();
public static lk_thread zidan = new lk_thread();
public static container cont = new container();
public static ConcurrentHashMap<String,lk_thread> scenehashmap = new ConcurrentHashMap<String,lk_thread>();
public static container zidan_cont = new container();

	public String auto_register_and_update_role( String type  , String style ){
		String [] d = style.split(",");
		if(!scenehashmap.containsKey(d[10])){
			scenehashmap.put(d[10],new lk_thread());
//			System.out.println("CREATE THREAD..");
		}
		scenehashmap.get(d[10]).type = type;
		style = style + "," + clint_time;
		scenehashmap.get(d[10]).s_container.put(d[7],style);
//		System.out.println(scenehashmap.get(d[10]).get(type));
		return scenehashmap.get(d[10]).get(type);
	}

	public String clint_time = "";
		
	public static String dateToStamp(String s) throws Exception {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = simpleDateFormat.parse(s);
		long time = date.getTime();
		res = String.valueOf(time);
		return res;
	}
	public static String stampToTime(String s) throws Exception{
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt * 1000l);
		res = simpleDateFormat.format(date);
		return res;
	}

	public void destroy (String id){
		map.remove(id);
	}

	public String get_name(){
		String res = "";
		number ++;
		name.set("g"+number,"g"+number);
		res = "g"+number;
		return res;
	}
	
	public String app_quit(String type , String style){
		String [] d = style.split(",");
		if(!scenehashmap.containsKey(d[10])){
			scenehashmap.put(d[10],new lk_thread());
		}
		scenehashmap.get(d[10]).type = type;
		style = style + "," + clint_time;
		scenehashmap.get(d[10]).s_container.put(d[7],style);
		return "";
	}
	
	private static int zidan_num = 0;
	public String auto_register_and_update_zidan(String type , String timer, String style){
		zidan_num ++;
	//	String [] d = style.split(",");
		zidan_cont.s_container.put("z" + zidan_num , style + "," + timer + "," + "z"+zidan_num);
	//	System.out.println("MINGZI1" + d[7] + "|" + style);
		zidan.type = type;
	//	System.out.println("ss1" + cont.c_container.get(type));
		zidan.s_container = zidan_cont.s_container;
		zidan_cont.c_container = zidan.c_container;
	//	System.out.println("MINGZI1" + d[7] + "|" + style);
	//	System.out.println("ss2" + cont.c_container.get(type));
	return "-";
	//	return zidan.get(type);
	}
	
	public String get_zidan_list(String type){
		return zidan.get(type);
	}
}

/*
一共四中方法
Map <String,String>map = new HashMap<String,String>();
map.put("熊大", "棕色");
map.put("熊二", "黄色");
for(Map.Entry<String, String> entry : map.entrySet()){
    String mapKey = entry.getKey();
    String mapValue = entry.getValue();
    System.out.println(mapKey+":"+mapValue);
}

Map <String,String>map = new HashMap<String,String>();
map.put("熊大", "棕色");
map.put("熊二", "黄色");
//key
for(String key : map.keySet()){
    System.out.println(key);
}
//value
for(String value : map.values()){
    System.out.println(value);
}

Iterator<Entry<String, String>> entries = map.entrySet().iterator();
while(entries.hasNext()){
    Entry<String, String> entry = entries.next();
    String key = entry.getKey();
    String value = entry.getValue();
    System.out.println(key+":"+value);
}

for(String key : map.keySet()){
    String value = map.get(key);
    System.out.println(key+":"+value);
}
*/

