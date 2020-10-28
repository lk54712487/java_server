
import java.util.Properties; 
import java.io.File;
import java.io.FileInputStream;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  

import java.security.MessageDigest;

public class test2{
	
	public static void main(String[] args){
		//30 line
		int i = 0;
		String s = "";
		while(true){
			s = "";
			for(int k = 0 ; k < 29 ; k++){	//shuxian
				for(int j = 0 ; j < 30 ; j++){	//hengxian
					if(j==14 && k==10
					|| j==15 && k ==9
					|| j==13 && k ==9
					|| j==17 && k ==8
					|| j==11 && k ==8
					|| j==19 && k ==7
					|| j==9 && k ==7
					|| j==21 && k ==8
					|| j==7 && k ==8
					|| j==23 && k ==9
					|| j==5 && k ==9
					|| j==25 && k ==10
					|| j==3 && k ==10
					|| j==23 && k ==11
					|| j==5 && k ==11
					|| j==21 && k ==12
					|| j==7 && k ==12
					|| j==19 && k ==13
					|| j==9 && k ==13
					|| j==17 && k ==14
					|| j==11 && k ==14
					|| j==15 && k ==15
					|| j==13 && k ==15
					|| j==14 && k ==16
					){
						s +=" ";
					}else{
						s +="*";
					}
				}
				s +="\n";
			}
			System.out.println(s ) ;
			i++;
		}
	}
}
