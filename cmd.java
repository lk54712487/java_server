import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.InputStreamReader; 
import java.io.BufferedReader; 

public class cmd {
	
	public static void main(String[] args) {
		Runtime rt = Runtime.getRuntime();
		try {
			while(true){
				BufferedReader wt = new BufferedReader(new InputStreamReader(System.in)); 
				String str = wt.readLine();
				if (str.equals("del") || str.equals("clear")) {
					File file1 = new File("D:\\java\\java\\likai");
					File file2 = new File("F:\\java\\MyClient.class");
					if(file1.exists() == true){
						rt.exec("cmd /c start rd /s /q D:\\java\\java\\likai");
					}
					if(file2.exists() == true){
		//				rt.exec("cmd /c start del /s /q F:\\java\\MyClient.class");
					}		 
				} 
				if (str.equals("bianyi") ||str.equals("make") ) {
					String java_file = "";
					java_file += " mysql.java";
					java_file += " DES.java";
					
					java_file += " socket_data_processing.java";
					java_file += " lk_map.java"; 
					java_file += " container.java"; 
					java_file += " lk_thread.java";
					java_file += " lk_hashthread.java";
					
					java_file += " SuperFile.java";
					java_file += " game_list.java";
					java_file += " lk_user_register.java";
					java_file += " lk_update.java";
					java_file += " lk_login.java";
					java_file += " lk_chat.java";
					java_file += " lk_auth.java";
					java_file += " lk_string.java";
					java_file += " index.java";
					String cmd_str = "cmd /c start javac -d ." + java_file;
					System.out.println(cmd_str);
					rt.exec(cmd_str);
				}
				if (str.equals("server") ) {	//启动服务
					rt.exec("cmd /k start java likai.Main.index.lk_start");
				}
				if (str.equals("client")) {	//启动客户端
					rt.exec("cmd /k start java MyClient");
				}
				if (str.equals("start") ) {
					rt.exec("cmd /k start java likai.Main.index.lk_start");
					Thread.currentThread().sleep(1000);
					rt.exec("cmd /k start java MyClient");
				}

				if(str.equals("remake")){
					File file1 = new File("F:\\java\\likai");
					if(file1.exists() == true){
						rt.exec("cmd /c rd /s /q F:\\java\\likai");
					}
					Thread.sleep(3000);
					String java_file = "";
					java_file += " mysql.java";
					java_file += " DES.java";
					java_file += " socket_data_processing.java";
					java_file += " lk_map.java"; 
					java_file += " container.java"; 
					java_file += " lk_thread.java"; 
					
					java_file += " SuperFile.java";
					java_file += " game_list.java";
					java_file += " lk_update.java";
					java_file += " lk_login.java";
					java_file += " lk_auth.java";
					java_file += " lk_string.java";
					java_file += " index.java";
					String cmd_str = "cmd /c start javac -d ." + java_file;
					System.out.println(cmd_str);
					rt.exec(cmd_str);
					Thread.sleep(3000);
					rt.exec("cmd /k start java likai.Main.index.lk_start");
				}
				if(str.equals("update")){
					rt.exec("cmd /c javac -d . SuperFile.java");
					Thread.sleep(1000);
					rt.exec("cmd /c java update");
				}
				
				if(str.equals("test")){
					File file1 = new File("F:\\java\\test.class");
					if(file1.exists() == true){
						rt.exec("cmd /c rd /s /q F:\\java\\test.class");
					}
					Thread.sleep(3000);
					rt.exec("cmd /c javac -d . test.java");
					Thread.sleep(3000);
					rt.exec("cmd /k start java test");
				}
			}
			
		}  catch (Exception e){
			System.out.println("cuowu"+e);
        }
	}
}