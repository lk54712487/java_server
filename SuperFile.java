package likai.Main.update;
import java.util.ArrayList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.security.MessageDigest;

public class SuperFile{	
	private String dir = "";
	private int socket_size = 1024 * 8;	
	byte [] buf = null;	

	private static ArrayList<String>  files = new ArrayList<String> ();

	private int skip = 0;
	private BufferedInputStream bufffile = null;
	private FileInputStream streamFile = null;
	private int Flen = 0;	//文件长度
	private int Part = 0;	//分成几段
	private boolean F_flag = false;	//文件长度

	public SuperFile(){
//		this.init();
	}

	public SuperFile set(String key , String val){
		switch(key){
			case "dir":
				this.dir = val;
				this.rep_dir();
			break;
			case "path":

			break;
			default:

			break;
		}
		return this;
	}

	//创建更新日志
	public void update(){

		File file = new File(this.dir + "update");
		if(!file.exists()){
			file.mkdirs(); 
		}
		String file_log = this.dir + "update/update.log";
		File wfile = new File(file_log);

		try{
			if(!wfile.exists()){
				wfile.createNewFile();
			}
			BufferedOutputStream wf = new BufferedOutputStream(new FileOutputStream(wfile));
			File ff = null;
			for(String dd : this.files){
				ff = new File(this.dir + dd);
				if(ff.isDirectory()){
					dd += "\n";
					wf.write(dd.getBytes());
				}else{
					long f_len = ff.length();
					String md5 = this.getMD5(ff);
					String ins = dd + "|" + f_len + "|" + md5 + "\n";
					wf.write(ins.getBytes());
				}
			}
			wf.flush();
			wf.close();
		}catch(Exception e){
			System.out.println("create " +file_log+ " fail");
		}
	}
	
	public int getPart(){
		return this.Part;
	}
	public int file_len(){
		return this.Flen;
	}
	//打开文件
	public boolean OpenFile(String path){
		try{
			path = this.rep_path(path);
			path = path.replaceAll(this.dir , "");
			path = this.dir + path;
			
			System.out.println(this.dir);
			File file = new File(path);
			this.Flen = (int)file.length();
			double a = this.Flen;
			double b = this.socket_size;
			double p = 0;
			p = Math.ceil(a/b);
			this.Part = (int)p;
			this.streamFile = new FileInputStream(file);
			this.bufffile = new BufferedInputStream(this.streamFile);
			this.F_flag = true;
			return true;
		}catch(Exception e){
			return false;
		}
	}
	public void close(){
		try{
			this.bufffile.close();
			this.streamFile.close();
		}catch(Exception e){
			System.out.println("file close fail");
		}
	}
	//自动循环读取文件buff
	public byte[] read(){
		this.buf = null;
		if(!this.F_flag){
			return this.buf;
		}
		try{
			int size = this.socket_size;
			if( this.Flen - this.skip <= this.socket_size ){
				size = this.Flen - this.skip  ;
				this.F_flag = false;
				this.skip = 0;
			}
			this.buf = new byte[size];

//			this.bufffile.skip(this.skip);
			int abc = this.bufffile.read(this.buf , 0 , size);
			
			this.skip = this.skip + size;
			if(!this.F_flag){
				this.bufffile.close();
				this.streamFile.close();
			}
			return this.buf;
		}catch(Exception e){
			return this.buf;
		}
	}

	public String get(String key){
		switch(key){
			case "dir":
			return this.dir;
			default: 
			return "";
		}
	}

	public void init(){
		byte [] buf = new byte[this.socket_size];
		if(!this.dir.isEmpty()){
			this.rep_dir();	
			this.check_file(this.dir);
		}
	}

	private void rep_dir(){
		this.dir  = this.dir  + "\\";
		this.dir  = this.dir.replaceAll("\\\\\\\\","\\\\"); //双斜杠变成单斜杠
		this.dir  = this.dir.replaceAll("\\\\","/");
		this.dir  = this.dir.replaceAll("//","/");
	}

	private String rep_path(String path){
		path  = path.replaceAll("\\\\\\\\","\\\\");	//双斜杠变成单斜杠
		path  = path.replaceAll("\\\\","/");
		return path;
	}
	
	//递归遍历文件目录列表
	private void check_file(String path){
		File file = new File(path);
        if (file.exists()) {
            File [] ff = file.listFiles();
			String file_name = "";
			String file_dir = "";
            if (ff.length == 0) {
            	return;
            } else {
                for (File f : ff) {
                	file_dir = f.getAbsolutePath();
					file_name = this.rep_path(file_dir);
				
					file_name = file_name.replaceAll(this.dir,"");
					if(!file_name.equals("update") && !f.isDirectory()){
						this.files.add(file_name);
					}
                    if (f.isDirectory()) {
                    	if(!file_name.equals("update")){
                    		this.check_file(file_dir);
						}
                    }
                }
            }
        } else {
            System.out.println("file does not exist");
        }
	}

	public static String getMD5(File file) {
		BufferedInputStream bufferedInputStream = null;
		try {
			char hexDigits[]= {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};  
			MessageDigest MD5 = MessageDigest.getInstance("MD5");
			bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[1024 * 1024 * 10];	//1M
			int length;
			while ((length = bufferedInputStream.read(buffer)) != -1) {
//				System.out.println("-");
				MD5.update(buffer, 0, length);
			}
			byte[] md = MD5.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} 
	}
}