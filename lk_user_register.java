package likai.Main.user;

import java.util.ArrayList;
import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import likai.Main.sql.mysql;
import likai.Main.hash.lk_hashthread;
/*
class lk_start{
	public static void main(String[] args){
		lk_update ff = new lk_update();
		String ss = ff.is_update();
		
	}
}
*/

public class lk_user_register{
	
	public String code = "1234";
	
	public void send_yzm(String content){
		System.out.println(content + ",send_yzm...");
	}
	
	public String user_login(String content){
		String ret = "success";
//		System.out.println( content );
		String [] arr = content.split(",");
		if(arr.length >= 2 ){
			if(arr[0].equals("")){
				ret = "user empty"; return ret;
			}
			if(arr[1].equals("")){
				ret = "password empty"; return ret;
			}
		}else{
			ret = "user or password error";
		}
		
		try{
			ret = "new mysql";
			mysql mq = new mysql();
			ret = "connect mysql..";
			mq.connect();
			ret = "create stmt";
			if(mq.conn != null){
				Statement stmt = mq.conn.createStatement();
				String sql;
				ret = "sql..";
				sql = "SELECT * FROM "+ mq.affix + "user where user = '"+arr[0]+"' limit 1";
				ret = sql;
	//			System.out.println( sql );
				ret = "search sql";
				
				ResultSet rs = stmt.executeQuery(sql);
	//			int len = stmt.getMaxRows();
	//			System.out.println( len );
				int num = 0;
				int id = 0;
				String user_name = "";
				String password = "";
				int is_state = 0;
				String id_card = "";
				ret = "while..";
				
				while(rs.next()){
					ret = "while.limian.";
					num ++;
					id  = rs.getInt("id");
					user_name = rs.getString("user");
					password = rs.getString("password");
					is_state = rs.getInt("is_state");
					id_card = rs.getString("id_card");
				}
				ret = "num.." + num;
				if(num == 0){
					ret = "user is null"; return ret;
				}
				if(num == 1){
					if(!arr[1].equals(password)){
						ret = "password error"; return ret;
					}
					if(is_state > 0){
						ret = "Disable login"; return ret;
					}
					ret = "id_card is null"; 
					if(id_card == null){
						
					}
					ret = "id_card is null"; 
					if(id_card != null){
	//					String string = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
						String year = new SimpleDateFormat("yyyy").format(new Date()).toString();
						String user_year = id_card.substring(6,10);
						String user_month = id_card.substring(10,12);
						String user_day = id_card.substring(12,14);
						
						SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						String times = user_year + "-" + user_month + "-" + user_day + " 00:00:00";						
	//					times = "2015-09-29 16:39:00";
						Date date = format.parse(times);  
						 //日期转时间戳（毫秒）
						long time = date.getTime() / 1000;
						long c_time = System.currentTimeMillis() / 1000;
						//567648000
						//159328894
	//					System.out.println( "jian:" + (c_time - time) );
						long max_age = 18 * 365 * 86400 ;
						if(( c_time - time ) > max_age){
							
						}else{
							ret = "age 18 error"; return ret;
						}
					}
					
					String c_thread_name = "u_" + id;
//					ret = "search thread"; 
					if(lk_hashthread.Threadres.containsKey(c_thread_name)){
						lk_hashthread.Threadres.get(c_thread_name).setName("close_" + id);
						lk_hashthread.Threadres.remove(c_thread_name);
					}
					ret = "set thread_name"; 
					Thread.currentThread().setName(c_thread_name);
					lk_hashthread.Threadres.put(
									Thread.currentThread().getName()
									,Thread.currentThread());	//push thread
	//				System.out.println(Thread.currentThread().getName());
	//				lk_hashthread.Threadres.containsKey(name);
	//				System.out.println(lk_hashthread.Threadres.containsKey("123"));
					rs.close();
					stmt.close();
					mq.conn.close();
					ret = "login success"; return ret;
				}
			}
			
		}catch(Exception e){
			ret = "mysql..err";
		}
		
		return ret;
	}
	
	public String user_register(String content){
		String ret = "success";
		String [] arr = content.split(",");
		if(arr[0].equals("")){
			ret = "phone empty"; return ret;
		}
		if(arr[1].equals("")){
			ret = "code empty"; return ret;
		}
		if(!arr[1].equals(code)){
			ret = "code error"; return ret;
		}
		try{
//			System.out.println( content );
			mysql mq = new mysql();
			mq.connect();
			Statement stmt = mq.conn.createStatement();
			String sql;
			sql = "SELECT * FROM "+ mq.affix + "user where user = '"+arr[0]+"'";
//			System.out.println( sql );
			ResultSet rs = stmt.executeQuery(sql);
//			int len = stmt.getMaxRows();
//			System.out.println( len );
			int num = 0;
			while(rs.next()){
				num ++;
			}
			if(num > 0){
				ret = "phone is success"; return ret;
			}
			int max=999999,min=100000;
			int password = (int) (Math.random()*(max-min)+min); 
			String create_time = System.currentTimeMillis() + "";
			create_time = create_time.substring(0,10);
			String ins_str = "INSERT INTO `"+mq.DB+"`.`"+mq.affix+"user` ( `user`, `password`, `create_time`) VALUES ( '"+arr[0]+"' , '"+password+"' , '"+create_time+"')";
//			System.out.print(ins_str);
			int ins_bool = stmt.executeUpdate(ins_str);
			if(ins_bool == 1){
				ret = "register success"; 
				
			}else{
				ret = "register fail"; 
			}
			rs.close();
			stmt.close();
			mq.conn.close();
			return ret;
		}catch(Exception e){
			
		}
		return ret;
	}
}

