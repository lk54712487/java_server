package likai.Main.game;

import java.lang.Math;
import java.io.*;

import likai.Main.des.DES;

import java.util.Map;
import java.util.HashMap;

public class lk_juji{
	 private String key = "cf6458c69b7a8c7b324937efc8ee2dea";
	
	 public boolean first_auth(String str){
	 	boolean res = false;
	 	DES des = new DES();
		String str2 = des.decrypt(str,this.key);
		if(str2.equals(this.key)){
			res = true;
		}
	 	return res;
	 }
}
