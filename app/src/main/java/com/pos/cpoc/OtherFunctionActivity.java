package com.pos.cpoc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.pos.cpoc.config.EMVTransConfig;
import com.pos.cpoc.utils.ISOUtil;


public class OtherFunctionActivity extends Activity implements OnClickListener
{
//	private SocketHandler skHandler = new SocketHandler();
	SdkFile sdkFile = new SdkFile();
	private int id;
	private Param param;
	ProgressDialog waitingDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other_function);
		param = new Param(this);
		
		Button buttonUpdateCapk = (Button)findViewById(R.id.buttonUpdateCapk);
		buttonUpdateCapk.setOnClickListener(this);
		
		Button buttonUpdateAID = (Button)findViewById(R.id.buttonUpdateAID);
		buttonUpdateAID.setOnClickListener(this);
		
		Button buttonUpdateTerminalParam = (Button)findViewById(R.id.buttonUpdateTerminalParam);
		buttonUpdateTerminalParam.setOnClickListener(this);
		
		Button buttonUpdateRevoc = (Button)findViewById(R.id.buttonUpdateRevoc);
		buttonUpdateRevoc.setOnClickListener(this);
		
		Button buttonBlacklist = (Button)findViewById(R.id.buttonUpdateBlacklist);
		buttonBlacklist.setOnClickListener(this);		
		
		Button buttonBatchUp = (Button)findViewById(R.id.buttonBatchUp);
		buttonBatchUp.setOnClickListener(this);		

//		skHandler.addSocketListener(new SocketListener() {
//
//			@Override
//			public void messagePorcess(String msg) {
//				//receive.setText(msg);
//			}
//		});
	}
		
	
	public void messageDialog(String title,String message)
	{
	   	AlertDialog.Builder dialog = new AlertDialog.Builder(this); 
		dialog.setTitle(title);	

		dialog.setMessage(message);
		
	   	dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override  
            public void onClick(DialogInterface arg0, int arg1) { 
        		int iRet = sdkFile.sdkFileDel(EMVTransConfig.file);
				if(iRet != 0)
				{
					iRet = Error.SAVE_EMV_FILE_ERR;
					Toast.makeText(OtherFunctionActivity.this,Error.getErrorMsg(iRet), Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(OtherFunctionActivity.this,"清除成功!", Toast.LENGTH_LONG).show();
				}        		
        		
            }  
        }); 
	   	
	   	dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override  
            public void onClick(DialogInterface arg0, int arg1) {           	
            }  
        });  	   	
				
		dialog.create().show();  

	}	
	
	public void onClick_ClearTransRecs(View view)
	{		
		messageDialog("清除交易流水","确定要清除交易流水吗?");
	}
	
	public void onClick_FunctionSetActivity(View view)
	{
		//threadFlag = true;
		Log.d("Debug", "onClick_FunctionSetActivity");
		Intent intent = new Intent(this, FunctionSetActivity.class);
		startActivity(intent);
	}	

	public void onClick_HostSet(View view)
	{		
		hostSetDialog();
	}		
	
	public void onClick(View v)
	{
//		id = v.getId();
//		//Toast.makeText(OtherFunctionActivity.this, ((Button)findViewById(id)).getText().toString() + "...", Toast.LENGTH_SHORT).show();
//		showWaitingDialog(((Button)findViewById(id)).getText().toString(), ((Button)findViewById(id)).getText().toString() + "...");
//		switch(id)
//		{
//			case R.id.buttonUpdateCapk:
//			case R.id.buttonUpdateAID:
//			case R.id.buttonUpdateTerminalParam:
//			case R.id.buttonUpdateRevoc:
//			case R.id.buttonUpdateBlacklist:
//			case R.id.buttonBatchUp:
//			{
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						int iRet = 0;
//						try{
//							do
//							{
//								skHandler.disConnect();
//								if(skHandler.connect(param.getHostIP(),param.getHostPort()) == false)
//								{
//									iRet = Error.CONNECT_HOST_ERR;
//									break;
//								}
//
//								if(id == R.id.buttonUpdateCapk)
//									iRet = skHandler.downloadCAPK();
//								else if(id == R.id.buttonUpdateAID)
//									iRet = skHandler.downloadAID();
//								else if(id == R.id.buttonUpdateTerminalParam)
//								{
//									iRet = skHandler.downTerminalConfig();
//									if(iRet != 0)
//									{
//										break;
//									}
//
//									// 从XML加载终端参数
//									TerminalConfig termConfig = new TerminalConfig();
//									Terminal term = termConfig.getConfig();
//									param.SetDataCapture(ISOUtil.parseInt(term.getDataCapture()));
//
//								}
//								else if(id == R.id.buttonUpdateRevoc)
//									iRet = skHandler.downloadRevoc();
//								else if(id == R.id.buttonUpdateBlacklist)
//									iRet = skHandler.downloadBlacklist();
//								else if(id == R.id.buttonBatchUp)
//								{
//									iRet = skHandler.sendBatchData();
//								}
//							}while(false);
//
//						}
//						catch(Exception e){
//							iRet = Error.EXCEPTION_ERR;
//						}
//						finally{
//							Log.d("Debug","disConnect");
//							try{
//								skHandler.disConnect();
//							}
//							catch(Exception e){
//							}
//
//							Looper.prepare();
//							if(waitingDialog != null)
//							{
//								waitingDialog.dismiss();
//							}
//							if(iRet != 0)
//							{
//								Toast.makeText(OtherFunctionActivity.this,Error.getErrorMsg(iRet), Toast.LENGTH_SHORT).show();
//							}
//							else
//							{
//								Toast.makeText(OtherFunctionActivity.this,"操作成功!", Toast.LENGTH_SHORT).show();
//							}
//							Looper.loop();
//
//						}
//
//					}
//				}).start();
//
//
//			}
//		}//switch()
		
		
	}
	
	private void showWaitingDialog(String title,String message) {
	    /* 等待Dialog具有屏蔽其他控件的交互能力
	     * @setCancelable 为使屏幕不可点击，设置为不可取消(false)
	     * 下载等事件完成后，主动调用函数关闭该Dialog
	     */
	    waitingDialog = new ProgressDialog(this);
	    waitingDialog.setTitle(title);
	    waitingDialog.setMessage(message);
	    waitingDialog.setIndeterminate(true);
	    waitingDialog.setCancelable(false);
	    waitingDialog.show();
	}	
	
	
    public void hostSetDialog()
    {    	
    	final View viewDialog = (View)getLayoutInflater().inflate(
				R.layout.dialog_host_set, null);
    	
    	final EditText editTextHostIP = (EditText)viewDialog.findViewById(R.id.EditTextHostIP);
    	final EditText editTextHostPort = (EditText)viewDialog.findViewById(R.id.EditTextHostPort);

    	//SharedPreferences sharedPreferences = getSharedPreferences("config.xml",
		//		Activity.MODE_PRIVATE);
		// 使用getXxx方法获得value，getXxx方法的第2个参数是value的默认值
		editTextHostIP.setText(param.getHostIP());
		editTextHostPort.setText(String.valueOf(param.getHostPort()));
		
		editTextHostIP.setSelection(editTextHostIP.getText().length());
		editTextHostIP.getSelectionStart();
		editTextHostPort.setSelection(editTextHostPort.getText().length());
		editTextHostPort.getSelectionStart();    	
		
	   	AlertDialog.Builder dialog = new AlertDialog.Builder(this);  
	   	dialog.setTitle(R.string.title_host_set);
	   	dialog.setView(viewDialog);
	   	
	   	dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {  
	            @Override  
	            public void onClick(DialogInterface arg0, int arg1) { 
	            	param.setHostIP(editTextHostIP.getText().toString());
	            	param.SetHostPort(ISOUtil.parseInt(editTextHostPort.getText().toString()));
	            	
	            
//					new Thread(new Runnable() {
//						@Override
//						public void run() {
//							int iRet = 0;
//							try{
//				            	param.setHostIP(editTextHostIP.getText().toString());
//				            	param.SetHostPort(ISOUtil.parseInt(editTextHostPort.getText().toString()));								
//								
//								do
//								{
//									Log.d("Debug","disConnect");
//									try{
//										skHandler.disConnect();
//									}
//									catch(Exception e){	
//									}
//									
//									if(skHandler.connect(param.getHostIP(),param.getHostPort()) == false)
//									{
//										iRet = error.CONNECT_HOST_ERR;
//										break;
//									}
//								}while(false);
//
//							}
//							catch(Exception e){
//								iRet = error.EXCEPTION_ERR;
//							}
//							finally{
//								
//								Looper.prepare();
//								if(waitingDialog != null)
//								{
//									waitingDialog.dismiss();
//								}
//								if(iRet != 0)
//								{
//									Toast.makeText(OtherFunctionActivity.this,error.getErrorMsg(iRet), Toast.LENGTH_LONG).show();
//								}
//								else
//								{
//									Toast.makeText(OtherFunctionActivity.this,"连接成功!", Toast.LENGTH_LONG).show();								
//								}
//								Looper.loop();							
//								
//							}
//						
//						}
//					}).start();
	            
	            
	            
	            }  
	        });  
	   	
	   	dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {  
	            @Override  
	            public void onClick(DialogInterface arg0, int arg1) {           	
	            }  
	        });  
	   	
       dialog.create().show(); 

		   
    } 	
				
}
