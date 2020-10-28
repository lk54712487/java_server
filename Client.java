import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.PrintWriter;  
import java.net.InetAddress;  
import java.net.Socket; 

import java.security.MessageDigest;

//不处理回车符号 
class MyClient {  
    static Socket server;  
  
    public static void main(String[] args) throws Exception {  
        server = new Socket(InetAddress.getLocalHost(), 8500); 
		System.out.println("客户端地址是" + InetAddress.getLocalHost());
        BufferedReader in = new BufferedReader(new InputStreamReader(  
                server.getInputStream()));  
        PrintWriter out = new PrintWriter(server.getOutputStream());  
        BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));  
        int i = 0;
		
		String key = "13123";
		md5 mm = new md5(); 
		String sss = mm.hashmd5(key);
		System.out.println(sss);
		Handle hh = new Handle();
		String ss = hh.rep("dsadsads|adsaabbcc",sss,"\\|");
		ss = hh.rep("dsadsads|adsaabbcc","\\|",sss);
		System.out.println(ss);
		while (true) {  
			Thread.currentThread().sleep(1000);
			i++;
			String str = wt.readLine();
//            String str = "aaa|bbb|" + i ;
//			str = "{\"key\":\"dsajknnjnj\",\"type\":\"move\",\"0\":{\"profession \":\"a\",\"x\":\"100\",\"y\":\"100\",\"z\":\"100\"},\"player\":\"100\",\"player_list\":[{\"profession \":\"b\",\"x\":\"100\",\"y\":\"100\",\"z\":\"100\"},{\"profession \":\"c\",\"x\":\"200\",\"y\":\"200\",\"z\":\"200\"}]}";
            out.println(str);  
//            out.println(str);  
//            out.println(str);  
            out.flush();  
            if (str.equals("end") || str.equals("quit") || str.equals("exit")) {  
                break;  
            }  
			String jieshou = in.readLine();
			String []jieshou2 = jieshou.split("\\|");
            System.out.println(jieshou2[0]);  //接收的数据
            System.out.println(jieshou2[1]);  //接收的数据
            System.out.println(jieshou2[2]);  //接收的数据
        }  
        server.close();  
    }  
}  
class Handle{

	public String rep(String str1 , String key , String str2){
		String s;
		s = str1.replaceAll(str2,"("+key+")");
		return s;
	}
}

/*
	使用方法
	md5 mm = new md5();
	String str = mm.hashmd5(str);
*/
class md5{
	private String inStr = null;
	
	public void md5(){}
	
	public void set(String inStr){
		this.inStr = inStr;
	}
	
	public String hashmd5(String inStr){
		return this.Calculation(inStr);
	}
	
	private String Calculation(String inStr){
		try{
			MessageDigest md5 = MessageDigest.getInstance("MD5");   
			char[] charArray = inStr.toCharArray();
			byte[] byteArray = new byte[charArray.length];
	  
			for (int i = 0; i < charArray.length; i++)  
				byteArray[i] = (byte) charArray[i];  
		   
			byte[] md5Bytes = md5.digest(byteArray);  
			StringBuffer hexValue = new StringBuffer();  
			
			for (int i = 0; i < md5Bytes.length; i++){  
				
				int val = ((int) md5Bytes[i]) & 0xff;  
				if (val < 16)  
					hexValue.append("0");  
				hexValue.append(Integer.toHexString(val));  
			}  
			return hexValue.toString();  
		}catch (Exception e){  
         
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }
	}
	
	public String get_value(){
		return this.inStr;
	}
} 
