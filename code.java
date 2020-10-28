package likai.Main.lib;

class base64_code{
	/*
	public static void main(String[] args){
		base64_code c = new base64_code();
	//	String str2 = c.encode("ABC");  //fiFAIyQlXiYqKCktPV8rYWJjZGVmZ2hpams=%$#@#$%^&*()
		String str2 = c.encode("vv里fh");
	//	String str2 = c.encode("东南vvjh");
		System.out.println(str2);
		String str = c.decode(str2);
	//	String str = c.decode(str2);
	//	str = c.BinstrToStr("100111000001010 111111101010001");
		System.out.println(str);
	}
	*/
	/*
	0000 0000-0000 007F | 0xxxxxxx
	0000 0080-0000 07FF | 110xxxxx 10xxxxxx
	0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx
	0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
	下面，还是以汉字“严”为例，演示如何实现UTF-8编码。
	已知“严”的unicode是4E25（100111000100101），根据上表，可以发现4E25处在第三行的范围内（0000 0800-0000 FFFF），
	因此“严”的UTF-8编码需要三个字节，即格式是“1110xxxx 10xxxxxx 10xxxxxx”。
	然后，从“严”的最后一个二进制位开始，依次从后向前填入格式中的x，多出的位补0。这样就得到了，
	“严”的UTF-8编码是“11100100 10111000 10100101”，转换成十六进制就是E4B8A5。
	*/
	public String encode(String str){
		int len = str.length();	
		if(len == 0){
			return "";
		}
		try{
			str = new String(str.getBytes("utf-8"),"utf-8");
		}catch(Exception e){
			
		}
		int y = len/3;
		int label = 0;
		String s = "";
		String rep = "";
		String ss = "";
		String r = "";
		String other = "";
		String return_str = "";
		String d_str = "";	//多余的位
		char[] strChar = null;
		char[] basecode = null;
		char[] basechar = {
						'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						,'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
						,'0','1','2','3','4','5','6','7','8','9','+','/'
						};
		int z = 0;
		int start = 0;
		int end = 0;
		for(int i = 0 ; i < y ; i ++){
			start = i*3;
			end = i*3+3;
			s = str.substring(start,end);	//取三个字符
			strChar = s.toCharArray(); 
			for(int j = 0 ; j < strChar.length ; j++){
				rep = Integer.toBinaryString(strChar[j]);
				int l = rep.length();
				
				switch(l){
					case 1:
						rep = "0000000" + rep;
					break;
					case 2:
						rep = "000000" + rep;
					break;
					case 3:
						rep = "00000" + rep;
					break;
					case 4:
						rep = "0000" + rep;
					break;
					case 5:
						rep = "000" + rep;
					break;
					case 6:
						rep = "00" + rep;
					break;
					case 7:
						rep = "0" + rep;
					break;
					case 11:
						rep = this.utf8(rep);
					break;
					case 15:
						rep = this.utf8(rep);
					break;
					case 16:
						rep = this.utf8(rep);
					break;
					case 21:
						rep = this.utf8(rep);
					break;
				}
				r += rep;
			}
		//	System.out.println(r.length());
			if(d_str != null){	//把多余的位加进到前面
				r = d_str + r;
			}
			
			int a = r.length()/6;
			z = i * 3 + 3;	//z shi zui hou yi ge wei zhi 
		//	System.out.println(r);
			for(int j = 0 ; j < a ; j++){
				ss = r.substring( j * 6 , j * 6 + 6 );
				basecode = ss.toCharArray();
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
				return_str += basechar[label];
				label = 0;
			}
			if(r.length()%6 > 0){	//有小数点
				d_str = r.substring( a*6 , r.length());
				
				if(y-1 == i && len % 3 == 0){ //如果没有余数
					int l = 6 - d_str.length();
					for(int k = 0 ; k < l ; k++){
						d_str = d_str + "0";
					}
					
					basecode = d_str.toCharArray();
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
					return_str += basechar[label];
				}
			}else{
				d_str = "";
			}
			r = "";
		}
		if(len > z){
		//	System.out.println("=================");
			r = "";
			s = str.substring(z,len);
			strChar = s.toCharArray(); 
			for(int j = 0;j < strChar.length;j++){
				rep = Integer.toBinaryString(strChar[j]);
				
				int l = rep.length();
				switch(l){
					case 1:
						rep = "0000000" + rep;
					break;
					case 2:
						rep = "000000" + rep;
					break;
					case 3:
						rep = "00000" + rep;
					break;
					case 4:
						rep = "0000" + rep;
					break;
					case 5:
						rep = "000" + rep;
					break;
					case 6:
						rep = "00" + rep;
					break;
					case 7:
						rep = "0" + rep;
					break;
					case 11:
						rep = this.utf8(rep);
					break;
					case 15:
						rep = this.utf8(rep);
					break;
					case 16:
						rep = this.utf8(rep);
					break;
					case 21:
						rep = this.utf8(rep);
					break;
				}
				r += rep;
			}
			
			if(d_str != null){	//把多余的位加进到前面
				r = d_str + r;
			}
			
			int rlen = r.length()/6;
			int jlen = rlen;
			rlen = rlen * 6 + 6 - r.length();
			
			if(rlen > 0 && rlen != 6){
				for(int j = 0 ; j < rlen ; j++){
					r += "0";	//bu wei
				}
				jlen += 1;
			}
			
			for(int j = 0 ; j < jlen ; j++){
				ss = r.substring( j*6 , j*6 + 6 );
				basecode = ss.toCharArray();
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
				other += basechar[label];
				label = 0;
			}
			return_str += other;
		}
		
		if(return_str.length()%4 > 0){	//有余位
			int deng = (return_str.length()/4)*4 + 4  - return_str.length();
			if(deng == 1){
				return_str = return_str + "=" ;
			}
			if(deng == 2){
				return_str = return_str + "==" ;
			}
			if(deng == 3){
				return_str = return_str + "A==" ;
			}
		}
		
		return return_str;	//basecode64
	}
	public String utf8(String str){
		int len = str.length();
		String r = "";
	
		switch(len){
			case 11:	//2字节
				r = "110" + str.substring(0,5) + "10" + str.substring(5,11);
			break;
			case 15:	//3字节
				r = "11100" + str.substring(0,3) + "10" + str.substring(3,9) + "10" + str.substring(9,15);
			break;
			case 16:	//3字节
				r = "1110" + str.substring(0,4) + "10" + str.substring(4,10) + "10" + str.substring(10,16);
		//		System.out.println(str);
		//		System.out.println(r);
			break;
			case 21:	//4字节
				r = "11110" + str.substring(0,3) + "10" + str.substring(3,9) + "10" + str.substring(9,15) + "10" + str.substring(15,21);
			break;
		}
		
		return r;
	}
	
	//--------------------
	public String decode(String str){
		String return_str = "";
		int len = str.length();
		if(len == 0){
			return "";
		}
		try{
			str = new String(str.getBytes("utf-8"),"utf-8");
		}catch(Exception e){
			
		}
		
		String r = "";
		String s = "";
		String d_str = "";	//多余的位
		int s_num = 0;	//舍弃的位置
		int start = 0;	//
		int end = 0;	//
		boolean flag = true;
		boolean w_flag = true;
		int i = 0;
		while(w_flag){
			start = i*4;
			end = i*4+4;
			if(start > len){
				break;
			}
			/*
			if(s_num > 0){
				start = s_num;
				end = start + 4;
				s_num = 0;
			}
			*/
			if(end > len){
				end = len;
			}
			
			if(d_str.length() < 24){
				if(flag){
					s = str.substring(start,end);
		//			System.out.println("1|" + s);
					r = this.str_to_bin(s);
				}
				i++;
				
			}else{
				end = start;
			}
			
			if(d_str != null){
				r = d_str + r;
				d_str = "";		//滞空
			}
		//	r = "123456781234567812345678";
			int r_len = r.length()/8;
			
			String repl = "";
			String st1 = "";
	//		System.out.println("------------------");
			int k = 3;
			if(r.length() < 24){
				k = r.length()/8;
			}
			for(int j = 0 ; j < k && r_len > 0 ; j++){
		//		System.out.println(r.length());
				st1 = r.substring(j*8,j*8+2);
		//		System.out.println("r|"+r);
				if(st1.equals("11")){
					String ss1 = "";
					String ss2 = "";
					int cha = len - end;
					if(flag){
						if(cha > 4){
		//					System.out.println("2|" + str.substring(end,end+4));
							ss2 = this.str_to_bin(str.substring(end,end+4));
							i++;
						}
						if(cha <= 4 && cha > 0){
		//					System.out.println("3|" + str.substring(end,len));
							ss2 = this.str_to_bin(str.substring(end,len));
							flag = false;
						}
					}
					
					ss1 += r + ss2;
					ss1 = ss1.substring( j*8 , ss1.length());
		//			System.out.println("a|"+ss1);
					//a倒萨上c东南vvjh三dsas菱电机网d大撒旦撒njksan网d
					String s1 = ss1.substring(4,8);	//不用处理开头的 0
		//			System.out.println( return_str + "|" + ss1 + "|" + ss1.length());
					String s2 = ss1.substring(10,16);
					String s3 = ss1.substring(18,24);
					d_str = ss1.substring(24,ss1.length());
					
			//		ss1 = "12345678123456781234567810345678";
					if(ss1.length() >= 32){	//如果是4字节
						if(ss1.substring(24,26).equals("10")){
							s3 = s3 + ss1.substring(26 , 32);
							d_str = ss1.substring(32 , ss1.length());
						}
					}
					
					String s4 = s1+s2+s3;
					return_str += this.BinstrToStr(s4);
		//			System.out.println("z|" + return_str + "|" +s4);
					break;
				}else{
					return_str += this.BinstrToStr(r.substring(j*8,j*8+8));
					if(r.length() > 24 && j == 2){	//最后一次循环并且有多余的位
						d_str = r.substring(j*8+8 , r.length());
					}
		//			System.out.println("s|" + return_str +"|" +r.substring(j*8,j*8+8));
				}
			}
			r = "";
		}
		return return_str;	//basecode64
	}
	
	//字符串转二进制字符串
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
		for(int j = 0;j < strChar.length;j++){
			if(strChar[j] == '='){
				continue;
			}
			String a = s.substring(j*1,j*1+1);
			byte [] bz = a.getBytes();
			int bb = (int) bz[0];
			
			int z = bb;
			if(bb == 47){		// /号
				z = 63;
			}
			if(bb == 43){		// +号
				z = 62;
			}
			if(bb > 96 && bb < 123){	//小写
				z = bb - 71;	
			}
			if(bb > 64 && bb <91){	//大写
				z = bb - 65;	
			}
			if(bb > 47 && bb < 58){	//数字
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
		return r;	//二进制字符串
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
	
	private String BinstrToStr(String binStr){
		
        String[] tempStr= binStr.split(" ");
        char[] tempChar=new char[tempStr.length];
        for(int i=0;i<tempStr.length;i++) {
           tempChar[i] = BinstrToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }
}