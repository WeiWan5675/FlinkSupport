package com.weiwan.support.common.utils;

import java.util.Random;

public class RandomSaltUtil {
	
	public static String generateRandomSaltUtil(int n){
		char[] str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random random = new Random();
		//System.out.println(random.nextInt(str.length));
		String salt="";
		for(int i=0;i<n;i++){
			salt+=str[random.nextInt(str.length)];
		}
			return salt;
	}
	public static void main(String[] args){
		String str=generateRandomSaltUtil(16);
		System.out.println(str);
	}
}
