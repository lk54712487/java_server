package likai.Main.des;
import java.util.Properties; 
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.security.MessageDigest;

public class DES{
	
	private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',  
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',  
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };  
  
    private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55,  
            56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,  
            21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46,  
            47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };  
	
	private String str1 = null;
	private String str2 = null;
//	private String KEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String KEY = "likai1991";
	public DES(){
		
	}
	public DES(String str){
		this.str1 = str;
	}
	public DES(String str,String KEY){
		this.str1 = str;
		this.KEY = KEY;
	}
	public void set_key(String KEY){
		this.KEY = KEY;
	}
	public String get_key(){
		return this.KEY;
	}
	public void set(String str){
		this.str1 = str;
	}
	public String get(){
		return this.str1;
	}
	
	public String encrypt(String str,String KEY){
		this.str1 = str;
		this.KEY = KEY;
		return this.encrypt();
	}
	
	public String encrypt(){
		if(this.str1 == null){
			return null;
		}
		String[] txt = this.str1.split("");
		String[] chars = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_.").split("");
		String charses = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_.";
		String ikey = "-x6g6ZWm2G9g_vr0Bo.pOq3kRIxsZ6rm";
		int nh1 = (int)(Math.random()*64);
		int nh2 = (int)(Math.random()*64);
		int nh3 = (int)(Math.random()*64);
	//	nh1 = nh2 = nh3 = 63;
		String ch1 = chars[nh1];
		String ch2 = chars[nh2];
		String ch3 = chars[nh3];
		int nhnum = nh1 + nh2 +nh3 ; 
		int knum = 0;
		int i = 0;
		int key_len = this.KEY.length();
		char [] ch = this.KEY.toCharArray();
		for(i = 0 ; i < key_len ; i++ ){
			knum += (int)(ch[i]);
		}
		 
		String mdKey = this.MD5(this.MD5((this.MD5(this.KEY + ch1).toLowerCase() + ch2  + ikey)).toLowerCase()+ ch3 ).toLowerCase() ;
		
		mdKey = mdKey.substring(nhnum%8,((knum%8)+16 + nhnum%8 ));
//		System.out.println(mdKey);
	
		base64_code c = new base64_code();
		String str = c.encode(this.str1);
	//	String str = this.encode(this.str1.getBytes());
		
		str = str.replace("+","-");
		str = str.replace("/","_");
		str = str.replace("=",".");
		String tmp = "";
		int j = 0;
		int k = 0;
		int tlen = str.length();
		int klen = mdKey.length();
	//	String [] mdkey = mdKey.split("");
		txt = str.split("");
		
		for(i = 0 ; i < tlen ; ++i){
			k = k == klen ? 0 : k;
			j = ( nhnum + charses.indexOf(txt[i]) + (int)mdKey.charAt(k++)) % 64 ;
			tmp += chars[j];
		}
		
		int tmplen = tmp.length();
		int num1 = nh2 % (++tmplen);
		int num2 = nh1 % (++tmplen);
		int num3 = knum % (++tmplen);
		tmp = tmp.substring(0,num1) + ch3 + tmp.substring(num1,tmp.length());
		tmp = tmp.substring(0,num2) + ch2 + tmp.substring(num2,tmp.length());
		tmp = tmp.substring(0,num3) + ch1 + tmp.substring(num3,tmp.length());
//		System.out.println(tmp);
		return tmp;
	}
	
	public String decrypt(String str,String KEY){
		this.str2 = str;
		this.KEY = KEY;
		return this.decrypt();
	}
	
	public String decrypt(String str){
		this.str2 = str;
		return this.decrypt();
	}
	
	public String decrypt(){
		if(this.str2 == null){
			return null;
		}
		String[] txt = this.str2.split("");
		String txt2 = this.str2;
		String[] chars = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_.").split("");
		String charses = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_.";
		String ikey = "-x6g6ZWm2G9g_vr0Bo.pOq3kRIxsZ6rm";
		
		int knum = 0;
		int i = 0;
		int tlen = this.str2.length();
		int key_len = KEY.length();
		char [] ch = KEY.toCharArray();
		for(i = 0 ; i < key_len ; i++ ){
			knum += (int)(ch[i]);
		}
		
		String ch1 = txt[knum%tlen];
		int nh1 = charses.indexOf(ch1);
		int num1 =  knum % tlen--;
		txt2 = txt2.substring(0,num1) + txt2.substring(num1+1,txt2.length());
		txt = txt2.split("");
		String ch2 = txt[nh1%tlen];
		int nh2 = charses.indexOf(ch2);
		int num2 =  nh1 % tlen--;
		txt2 = txt2.substring(0,num2) + txt2.substring(num2+1,txt2.length());
		txt = txt2.split("");
		String ch3 = txt[nh2%tlen];
		int nh3 = charses.indexOf(ch3);
		int num3 =  nh2 % tlen--;
		txt2 = txt2.substring(0,num3) + txt2.substring(num3+1,txt2.length());
		
		int nhnum = nh1 + nh2 + nh3;
		
		String mdKey = this.MD5(this.MD5((this.MD5(this.KEY + ch1).toLowerCase() + ch2  + ikey)).toLowerCase() + ch3 ).toLowerCase() ;
		
		mdKey = mdKey.substring(nhnum%8,((knum%8)+16 + nhnum%8 ));
		
		String tmp = "";
		int j = 0;
		int k = 0;
		tlen = txt2.length();
		int klen = mdKey.length();
		txt = null;
		
		txt = txt2.split("");
		for(i = 0 ; i < tlen ; ++i){
			k = k == klen ? 0 : k;
			j = charses.indexOf(txt[i]) - nhnum - (int)mdKey.charAt(k++);
			while ( j < 0){
				j += 64;
			}
			tmp += chars[j];
		}
		
		
		tmp = tmp.replace("-","+");
		tmp = tmp.replace("_","/");
		tmp = tmp.replace(".","=");
		//	base64_code c = new base64_code();
	//	String str2 = c.encode("abc");  //fiFAIyQlXiYqKCktPV8rYWJjZGVmZ2hpams=%$#@#$%^&*()
	//	String str2 = c.encode("aa");
	//	String str2 = c.encode("aa");
	//	System.out.println(str2);
	//	String str = c.decode(str2);
		
	//	String str = c.decode(str2);
	//	str = c.BinstrToStr("100111000001010 111111101010001");
	//	System.out.println(str);
		base64_code c = new base64_code();
	//	System.out.println(tmp);
		tmp = c.decode(tmp);
	//	tmp = new String(this.decode(tmp));
		tmp.trim();
		return tmp;
	}
	
	public final static String MD5(String s) {
        char hexDigits[]= {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
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
            return null;
        }
    }
  
    public static String encode(byte[] data)  
    {  
        StringBuffer sb = new StringBuffer();  
        int len = data.length;  
        int i = 0;  
        int b1, b2, b3;  
  
        while (i < len)  
        {  
            b1 = data[i++] & 0xff;  
            if (i == len)  
            {  
                sb.append(base64EncodeChars[b1 >>> 2]);  
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);  
                sb.append("==");  
                break;  
            }  
            b2 = data[i++] & 0xff;  
            if (i == len)  
            {  
                sb.append(base64EncodeChars[b1 >>> 2]);  
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);  
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);  
                sb.append("=");  
                break;  
            }  
            b3 = data[i++] & 0xff;  
            sb.append(base64EncodeChars[b1 >>> 2]);  
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);  
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);  
            sb.append(base64EncodeChars[b3 & 0x3f]);  
        }  
        return sb.toString();  
    }  
  
    public static byte[] decode(String str)  
    {  
        byte[] data = str.getBytes();  
        int len = data.length;  
        ByteArrayOutputStream buf = new ByteArrayOutputStream(len);  
        int i = 0;  
        int b1, b2, b3, b4;  
  
        while (i < len)  
        {  
  
            /* b1 */  
            do  
            {  
                b1 = base64DecodeChars[data[i++]];  
            }  
            while (i < len && b1 == -1);  
            if (b1 == -1)  
            {  
                break;  
            }  
  
            /* b2 */  
            do  
            {  
                b2 = base64DecodeChars[data[i++]];  
            }  
            while (i < len && b2 == -1);  
            if (b2 == -1)  
            {  
                break;  
            }  
            buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));  
  
            /* b3 */  
            do  
            {  
                b3 = data[i++];  
                if (b3 == 61)  
                {  
                    return buf.toByteArray();  
                }  
                b3 = base64DecodeChars[b3];  
            }  
            while (i < len && b3 == -1);  
            if (b3 == -1)  
            {  
                break;  
            }  
            buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));  
  
            /* b4 */  
            do  
            {  
                b4 = data[i++];  
                if (b4 == 61)  
                {  
                    return buf.toByteArray();  
                }  
                b4 = base64DecodeChars[b4];  
            }  
            while (i < len && b4 == -1);  
            if (b4 == -1)  
            {  
                break;  
            }  
            buf.write((int) (((b3 & 0x03) << 6) | b4));  
        }  

        return buf.toByteArray();  
    }  
}

class base64_code{

	public String encode(String str){
		String res = "";
		int len = str.length();	
		if(len == 0){
			return res;
		}
		try{
			str = new String(str.getBytes("utf-8"),"utf-8");
		}catch(Exception e){
			
		}
		char[] strChar = null;
		char[] basecode = null;
		
		strChar = str.toCharArray(); 
		String temporary = "";
//		String str2 = "";
		
		for(int i = 0 ; i < strChar.length ; i++){
			String s = Integer.toBinaryString(strChar[i]);
			int l = s.length();
			switch(l){
				case 1 :
					s = "0000000" + s;
				break;
				case 2 :
					s = "000000" + s;
				break;
				case 3 :
					s = "00000" + s;
				break;
				case 4 :
					s = "0000" + s;
				break;
				case 5 :
					s = "000" + s;
				break;
				case 6 :
					s = "00" + s;
				break;
				case 7 :
					s = "0" + s;
				break;
				default:
					if(l > 7 && l <= 16){
						s = this.utf8(s);
					}
				break;
			}
			temporary += s;
			if(temporary.length() >= 24){
//				str2 = temporary.substring(0,24);
				res += this.bit_to_str(temporary.substring(0,6));
				res += this.bit_to_str(temporary.substring(6,12));
				res += this.bit_to_str(temporary.substring(12,18));
				res += this.bit_to_str(temporary.substring(18,24));
				if(temporary.length() > 24){
					temporary = temporary.substring(24,temporary.length());
				}else{
					temporary = "";
				}
			}
		}

		//处理剩下的字符串
		if(temporary.length() > 0){

			int bu0 = 24 - temporary.length();
			if(bu0 == 8){
				temporary += "00";
				res += this.bit_to_str(temporary.substring(0,6));
				res += this.bit_to_str(temporary.substring(6,12));
				res += this.bit_to_str(temporary.substring(12,18));
				res += "=";
			}else if(bu0 == 16){
				temporary += "0000";
				res += this.bit_to_str(temporary.substring(0,6));
				res += this.bit_to_str(temporary.substring(6,12));
				res += "==";
			}
		}
		return res;
	}

	public String decode(String str){
		String res = "";
		int len = str.length();
		if(len == 0){
			return "";
		}
		try{
			str = new String(str.getBytes("utf-8"),"utf-8");
		}catch(Exception e){
			
		}
		String temporary = "";
		String temporary_bin = "";
		String temporary_bin_8 = "";
		temporary = str;
		char[] strChar = null;
		String s1 = "";
		String s2 = "";
		String s3 = "";
		int chuli_weishu = 8;
		int ch_weishu = 8;
		while(true){
			
			if(str.equals("")){
				break;
			}
			if(str.length() > 1){
				temporary = str.substring(0,1);
				str = str.substring(1 , str.length());
			}else{
				temporary = str;
				str = "";
			}
			temporary_bin += this.str_to_bin(temporary);
			if(temporary_bin.length() >= 8){
				temporary_bin_8 = temporary_bin.substring(0,8);
				s1 = temporary_bin_8.substring(0,1);
				if(s1.equals("0")){
					res += this.BinstrToStr(temporary_bin_8);
					temporary_bin_8 = "";
					chuli_weishu = 8;
					temporary_bin = temporary_bin.substring(chuli_weishu,temporary_bin.length());
				}
				if(s1.equals("1")){
					ch_weishu = 16;
					s3 = temporary_bin_8.substring(2,3);
					if(s3.equals("1")){
						ch_weishu = 24;
					}
					if(temporary_bin.length() >= ch_weishu){
						temporary_bin_8 = temporary_bin.substring( 0 , ch_weishu);
						System.out.println(temporary_bin_8);
						if(ch_weishu == 16){
							temporary_bin_8 = temporary_bin_8.substring(2,8) + temporary_bin_8.substring(10,16);
						}
						if(ch_weishu == 24){
							temporary_bin_8 = temporary_bin_8.substring(3,8) + temporary_bin_8.substring(10,16) + temporary_bin_8.substring(18,24) ;
						}
						res += this.BinstrToStr(temporary_bin_8);
						temporary_bin_8 = "";
						temporary_bin = temporary_bin.substring(ch_weishu,temporary_bin.length());
					}
				}
			}
		}

		return res;
	}

	public boolean get_one_len(String str , int num){
		boolean res = false;
		str = str.substring(num-1,num);
		if(str.equals("1")){
			return true;
		}
		return res;
	}

	public String str_to_bin(String s){
		String r = "";
		String rep = "";
		char[] strChar = null;
		char[] basechar = {
						'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						,'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
						,'0','1','2','3','4','5','6','7','8','9','+','/'
						};
		int len = s.length();
		if(len == 0){
			return "";
		}
		strChar = s.toCharArray(); 
//		System.out.println(strChar);
		for(int j = 0;j < strChar.length;j++){
			if(strChar[j] == '='){
				continue;
			}
			String a = s.substring(j*1,j*1+1);
			byte [] bz = a.getBytes();
			int bb = (int) bz[0];
			
			int z = bb;
			if(bb == 47){		
				z = 63;
			}
			if(bb == 43){		
				z = 62;
			}
			if(bb > 96 && bb < 123){	
				z = bb - 71;	
			}
			if(bb > 64 && bb <91){	
				z = bb - 65;
			//	System.out.println(a +"-" +z);		
			}
			if(bb > 47 && bb < 58){	
				z = bb + 4;
			}
			
			rep = Integer.toBinaryString(z);
			int l = rep.length();
			switch(l){
				case 1:
					rep = "00000" + rep;
				break;	
				case 2:
					rep = "0000" + rep;
				break;
				case 3:
					rep = "000" + rep;
				break;
				case 4:
					rep = "00" + rep;
				break;
				case 5:
					rep = "0" + rep;
				break;
			}
			r += rep;
		}
//		System.out.println(r);
		return r;
	}

	private String BinstrToStr(String binStr){
		
        String[] tempStr= binStr.split(" ");
        char[] tempChar=new char[tempStr.length];
        for(int i=0;i<tempStr.length;i++) {
           tempChar[i] = BinstrToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }

    private int[] BinstrToIntArray(String binStr) {       
        char[] temp=binStr.toCharArray();
        int[] result=new int[temp.length];   
        for(int i=0;i<temp.length;i++) {
            result[i]=temp[i]-48;
        }
        return result;
    }
	
	private char BinstrToChar(String binStr){
        int[] temp=BinstrToIntArray(binStr);
        int sum=0;
        for(int i=0; i<temp.length;i++){
            sum +=temp[temp.length-1-i]<<i;
        }   
        return (char)sum;
    }

	public String bit_to_str(String str){
		String res = "";
		char[] basecode = null;
		char[] basechar = {
						'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						,'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
						,'0','1','2','3','4','5','6','7','8','9','+','/'
						};
		int label = 0;
		if(str.length() == 6){
			basecode = str.toCharArray();
			if(basecode[0] == '1'){
				label += 32;
			}
			if(basecode[1] == '1'){
				label += 16;
			}
			if(basecode[2] == '1'){
				label += 8;
			}
			if(basecode[3] == '1'){
				label += 4;
			}
			if(basecode[4] == '1'){
				label += 2;
			}
			if(basecode[5] == '1'){
				label += 1;
			}
			res += basechar[label];
		}
		return res;
	}

	public String utf8(String str){
		int len = str.length();
		String r = "";
		switch(len){
			case 8:
				r = "110000" + str.substring(0,2) + "10" + str.substring(2,8);
			break;
			case 9:
				r = "11000" + str.substring(0,3) + "10" + str.substring(3,9);
			break;
			case 10:
				r = "1100" + str.substring(0,4) + "10" + str.substring(4,10);
			break;
			case 11:
				r = "110" + str.substring(0,5) + "10" + str.substring(5,11);
			break;
			case 12:
				r = "11100000"  + "10" + str.substring(0,6) + "10" + str.substring(6,12);
			break;
			case 13:
				r = "1110000" + str.substring(0,1) + "10" + str.substring(1,7) + "10" + str.substring(7,13);
			break;
			case 14:
				r = "111000" + str.substring(0,2) + "10" + str.substring(2,8) + "10" + str.substring(8,14);
			break;
			case 15:	
				r = "11100" + str.substring(0,3) + "10" + str.substring(3,9) + "10" + str.substring(9,15);
			break;
			case 16:	
				r = "1110" + str.substring(0,4) + "10" + str.substring(4,10) + "10" + str.substring(10,16);
			break;
			case 21:	
				r = "11110" + str.substring(0,3) + "10" + str.substring(3,9) + "10" + str.substring(9,15) + "10" + str.substring(15,21);
			break;
		}
		return r;
	}
}