package likai.Main.sql;

import java.sql.*;

public class mysql{
	public String driver = "com.mysql.jdbc.Driver";
	public String port = "3306";
	public String host = "localhost";	//localhost  |   127.0.0.1
	public String DB = "game_1";
	public String user = "root";
	public String charset = "UTF8";
	public String affix = "g_";
	public String password = "123456";
	public Connection conn = null;
	Statement stmt = null;	
	Statement stmt2 = null;
	
	public static void main(String[] args){
		mysql mq = new mysql();
		mq.connect();
	}
	
	public Connection connect(){
		Connection ret_conn = null;
		try{
        //    Class.forName("com.mysql.jdbc.Driver");
        //    Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+this.host+":"+this.port+"/"+this.DB + "?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding="+this.charset+"&serverTimezone=GMT";
			
            conn = DriverManager.getConnection(
				url
				,this.user
				,this.password				
				);
			ret_conn = conn;
			/*
			String update_sql = "update ttt set name='1' where id = 1";
			String update_sql2 = "update ttt set name='1' where id = 2";
				//	System.out.print(update_sql);
			stmt.executeUpdate(update_sql);
			stmt.executeUpdate(update_sql2);
			
			boolean k = true;
			int i = 1;
			int j = 2;
			while(k){
				String sql1 = "SELECT * FROM ttt where id=1";
				rs = stmt.executeQuery(sql1);
				
				while(rs.next()){
					int z = Integer.parseInt(rs.getString("name"));
					z++;
					update_sql = "update ttt set name='" + z + "' where id = 1";
					stmt2.executeUpdate(update_sql);
					System.out.print(z);
				}
				
				
				i++;
				j++;
				update_sql = "update ttt set name='" + i + "' where id = 1";
				System.out.print(update_sql);
				stmt.executeUpdate(update_sql);
				update_sql2 = "update ttt set name='" + j + "' where id = 2";
				System.out.print(update_sql);
				stmt.executeUpdate(update_sql2);
				
			}
			
            rs.close();
			
            stmt.close();
			*/
        //    conn.close();
			
			return ret_conn;
        }catch(SQLException se){
            se.printStackTrace();
			return ret_conn;
        }catch(Exception e){
            e.printStackTrace();
			return ret_conn;
        }
	//	finally{
    //        try{
    //            if(stmt!=null) stmt.close();
    //        }catch(SQLException se2){
    //        }
    //        try{
    //            if(conn!=null) conn.close();
    //        }catch(SQLException se){
    //            se.printStackTrace();
    //        }
	//		return false;
    //    }
	//	return false;
	}
	
	public void close()
    {
        try {
            if(this.conn != null)
            {
                this.conn.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
	
	public ResultSet select(String sql){
		ResultSet rs =null;
		try{
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
			sql = "SELECT * FROM g_user where 1";
			rs = stmt.executeQuery(sql);
			int len = rs.getRow();
			System.out.print("len: " + len + ",");
			while(rs.next()){
				int id  = rs.getInt("id");
				String user_name = rs.getString("user");
				String nickname = rs.getString("password");
				System.out.print("ID: " + id);
				System.out.print(", name: " + user_name);
				System.out.print(", password: " + nickname);
				System.out.print("\n");
			}
		}catch(SQLException se){
            se.printStackTrace();
			return rs;
        }catch(Exception e){
            e.printStackTrace();
			return rs;
        }
		return rs;
	}
	
	public boolean update(String sql){
		boolean ret = false;
		return ret;
	}
	
	public mysql set(String key , String val){
		switch(key){
			case "host":
				this.host = val;
			break;
			case "user":
				this.user = val;
			break;
			case "password":
				this.password = val;
			break;
			case "charset":
				this.charset = val;
			break;
			case "affix":
				this.affix = val;
			break;
			case "AFFIX":
				this.affix = val;
			break;
			case "DB":
				this.DB = val;
			break;
			case "db":
				this.DB = val;
			break;
		}
		return this;
	}
}