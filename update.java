
import java.util.Properties; 
import java.io.File;
import java.io.FileInputStream;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  
import likai.Main.update.SuperFile;  

import java.security.MessageDigest;

public class update{
	public static void main(String[] args){
		SuperFile up_file = new SuperFile();
		String path = "e:\\a1\\";
		up_file.set("dir" , path);
		up_file.init();
		up_file.update();
	}
}
