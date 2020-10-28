package likai.Main.update;

import java.util.ArrayList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.security.MessageDigest;
/*
class lk_start{
	public static void main(String[] args){
		lk_update ff = new lk_update();
		String ss = ff.is_update();
		
	}
}
*/

public class lk_update{
	
	public String body = "ab";
	public String footer = "1";
	
	private ArrayList<String>  file_list = new ArrayList<String> ();
	private String [] files;
	private String path;
	
	public lk_update(){
		this.path = "e:\\a1";
	}
	public lk_update(String path){
		this.path = path;
	}
	
	public void set_content(String content){
		String [] st = content.split("\\|");
		this.body = st[0];
		this.footer = st[1];
	}

	public String is_update_log(){
		String client_md5 = this.footer;
		SuperFile sf = new SuperFile();
		File ff = new File("e:/a1/update/update.log");
		String server_md5 = sf.getMD5(ff);
//		System.out.println(this.footer);
//		System.out.println(server_md5);
		String ret = "";
		if(client_md5.equals(server_md5)){
			ret = "false";
		}else{
			ret = "true";
		}
		return ret;
	}
	public SuperFile get_file(){
		SuperFile sf = new SuperFile();
		String file_name = this.body;
		sf.OpenFile(this.path +"\\" + file_name);
		return sf;
	}
	
	private void check_file(String path){
		File file = new File(path);
        if (file.exists()) {
            File [] files = file.listFiles();
            if (files.length == 0) {
     //           System.out.println( path + "file empty!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
						String file_name = file2.getAbsolutePath() + "\\";
						String ss = this.path.replaceAll("\\\\","\\\\\\\\") + "\\\\";
						String linux = this.path + "/";
				//		file_name = file_name.replace("/","\\");
						file_name = file_name.replaceAll(ss,"");
						this.file_list.add(file_name);
                        check_file(file2.getAbsolutePath());
                    } else {
//                      System.out.println(file2.getAbsolutePath());
						String file_name = file2.getAbsolutePath();
						String ss = this.path.replaceAll("\\\\","\\\\\\\\") + "\\\\";
						String linux = this.path + "/";
				//		file_name = file_name.replace("/","\\");
						file_name = file_name.replaceAll(ss,"");
						file_name = file_name.replaceAll(linux,"");
						
						this.file_list.add(file_name);
                    }
                }
            }
        } else {
            System.out.println("file does not exist");
        }
	}
}

