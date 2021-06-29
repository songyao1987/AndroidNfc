package com.pos.cpoc;

import android.app.Activity;

import android.content.SharedPreferences;
import android.util.Log;
import android.content.Context;
//import android.content.ContextWrapper;

public class Param {
	private SharedPreferences sharedPreferences; 
	private SharedPreferences.Editor editor;
	//private MyApplication app;
		
	public Param(Context context){
		sharedPreferences = context.getSharedPreferences("param.xml",Activity.MODE_PRIVATE);	
		editor = sharedPreferences.edit();
		// TODO Auto-generated constructor stub
		//Log.d("Debug","Param()");
	}

	public Boolean getIsPrint() {
		return sharedPreferences.getBoolean("isPrint", false);
	}

	public void setIsPrint(Boolean isPrint) {
		Log.d("Debug","setIsPrint:"+isPrint.toString());
		editor.putBoolean("isPrint", isPrint);
		editor.commit();
	}
	
	public Boolean getIsPrintDebugInfo() {
		return sharedPreferences.getBoolean("isPrintDebugInfo", true);
	}

	public void setIsPrintDebugInfo(Boolean isPrintDebugInfo) {
		editor.putBoolean("isPrintDebugInfo", isPrintDebugInfo);
		editor.commit();		
	}
	
	public Boolean getIsForcedOnline() {
		return sharedPreferences.getBoolean("isForcedOnline", false);
	}

	public void setIsForcedOnline(Boolean isForcedOnline) {
		editor.putBoolean("isForcedOnline", isForcedOnline);
		editor.commit();		
	}
			
	public byte getTransactionType() {
		return  (byte)sharedPreferences.getInt("transactionType",0x00);
	}

	public void setTransactionType(byte transactionType) {
		editor.putInt("transactionType", transactionType);
		editor.commit();		
	}

	public int getKernelConfig() {
		return sharedPreferences.getInt("kernelConfig", 5);
	}

	public void setKernelConfig(int kernelConfig) {
		editor.putInt("kernelConfig", kernelConfig);
		editor.commit();		
	}	

	public int getHostType() {
		return sharedPreferences.getInt("hostType", 0);
	}

	public void setHostType(int hostType) {
		editor.putInt("hostType", hostType);
		editor.commit();		
	}	
	
	public int getApduMode() {
		return sharedPreferences.getInt("apduMode", 2);
	}

	public void setApduMode(int apduMode) {
		editor.putInt("apduMode", apduMode);
		editor.commit();		
	}	
	
	public String getHostIP() {
		return sharedPreferences.getString("hostIP", "192.168.1.105");
	}

	public void setHostIP(String hostIP) {
		editor.putString("hostIP", hostIP);
		editor.commit();		
	}
	
	public int getHostPort() {
		return sharedPreferences.getInt("hostPort", 6908);
	}

	public void SetHostPort(int hostPort) {
		editor.putInt("hostPort", hostPort);
		editor.commit();		
	}
	
	public int getDataCapture() {
		return sharedPreferences.getInt("dataCapture", 0);
	}

	public void SetDataCapture(int dataCapture) {
		editor.putInt("dataCapture", dataCapture);
		editor.commit();		
	}
	
	
	public int getTransNo() {
		return sharedPreferences.getInt("transNo", 1);
	}

	public void setTransNo(int transNo) {
		editor.putInt("transNo", transNo);
		editor.commit();		
	}	
	
	public int getPinPadStyle() {
		return sharedPreferences.getInt("style",0);
	}

	public void setPinPadStyle(int style) {
		editor.putInt("style", style);
		editor.commit();		
	}	
	
}
