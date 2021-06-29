package com.pos.cpoc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;


import android.widget.CompoundButton.OnCheckedChangeListener;

import com.pos.cpoc.utils.MessageDigestUtil;

import static com.pos.cpoc.utils.ByteUtil.convertBytesToHex;

public class FunctionSetActivity extends Activity
{
	private Param param;
	EmvHandler  emvHandler = EmvHandler.getInstance();
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_function_set);
	    param = new Param(this);
	
    	Switch switchPrint = (Switch) findViewById(R.id.SwitchPrint);
    	switchPrint.setChecked(param.getIsPrint());
    	switchPrint.setOnCheckedChangeListener(new OnCheckedChangeListener() {   
            public void onCheckedChanged(CompoundButton buttonView,  
                    boolean isChecked) {  
            	// TODO Auto-generated method stub

            	param.setIsPrint(isChecked);
            }  
        }); 
    	
    	Switch switchDebug = (Switch) findViewById(R.id.SwitchDebug);
    	switchDebug.setChecked(param.getIsPrintDebugInfo());
    	switchDebug.setOnCheckedChangeListener(new OnCheckedChangeListener() {   
            public void onCheckedChanged(CompoundButton buttonView,  
                    boolean isChecked) {  
                // TODO Auto-generated method stub  
            	param.setIsPrintDebugInfo(isChecked);
            }  
        });      
    	
    	Switch switchForcedOnline = (Switch) findViewById(R.id.SwitchForcedOnline);
    	switchForcedOnline.setChecked(param.getIsForcedOnline());
    	switchForcedOnline.setOnCheckedChangeListener(new OnCheckedChangeListener() {   
            public void onCheckedChanged(CompoundButton buttonView,  
                    boolean isChecked) {  
                // TODO Auto-generated method stub  
            	param.setIsForcedOnline(isChecked);
            }  
        }); 
    	
    	
		final Spinner spinnerTransType = (Spinner)findViewById(R.id.spinnerTransType);
		String[] TransType = new String[]
		//{ "商品和服务", "现金", "返现", "查询", "转账","付款", "管理", "存款"};
		{getResources().getString(R.string.tv_goods),getResources().getString(R.string.tv_cash), getResources().getString(R.string.tv_cashback), getResources().getString(R.string.tv_inquiry),
				getResources().getString(R.string.tv_transfer),getResources().getString(R.string.tv_payment), getResources().getString(R.string.tv_administrative), getResources().getString(R.string.tv_cash_deposit)};
		
		
		
		ArrayAdapter<String> aaAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, TransType);
		// 将上如下代码可以使列表项带RadioButton控件
		aaAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTransType.setAdapter(aaAdapter1);

		for (int i = 0; i < TransType.length; i++) {
			if(emvHandler.hexStringToBytes(TransType[i].substring(0, 2))[0] == param.getTransactionType()){
				spinnerTransType.setSelection(i);
				break;
			}
		}

		spinnerTransType.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				
				param.setTransactionType(emvHandler.hexStringToBytes(spinnerTransType.getSelectedItem().toString().substring(0, 2))[0]);	
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});		
		
		
		Spinner spinnerKernelConfig = (Spinner) findViewById(R.id.spinnerKernelConfig);
		String[] kernelConfig = new String[]
		{"unionPay","EMV", "PBOC","qPBOC","qUICS","PayWare","PayPass"};
		ArrayAdapter<String> aaAdapter3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, kernelConfig);
		// 将上如下代码可以使列表项带RadioButton控件
		aaAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerKernelConfig.setAdapter(aaAdapter3);	
		spinnerKernelConfig.setSelection(param.getKernelConfig());
		spinnerKernelConfig.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				param.setKernelConfig(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});
		
		Spinner spinnerApduMode = (Spinner) findViewById(R.id.spinnerApduMode);
		String[] apduMode = new String[]
				{"Default","Callback","Callback nfc"};
		ArrayAdapter<String> aApduMode = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, apduMode);
		// 将上如下代码可以使列表项带RadioButton控件
		aApduMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerApduMode.setAdapter(aApduMode);	
		spinnerApduMode.setSelection(param.getApduMode());
		spinnerApduMode.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				param.setApduMode(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});	
		
		
		Spinner spinnerHost = (Spinner) findViewById(R.id.spinnerHost);
		String[] hostType = new String[]
				{"Standard","BCTC"};
		ArrayAdapter<String> aAdapterHost = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, hostType);
		// 将上如下代码可以使列表项带RadioButton控件
		aAdapterHost.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerHost.setAdapter(aAdapterHost);	
		spinnerHost.setSelection(param.getHostType());
		spinnerHost.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				param.setHostType(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});
		
		Spinner spinnerPinPadStyle = (Spinner) findViewById(R.id.spinnerPinPadStyle);
		String[] pinPadStyle = new String[]
				{"Style1","Style2"};
		ArrayAdapter<String> aAdapterPinPadStyle = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, pinPadStyle);
		// 将上如下代码可以使列表项带RadioButton控件
		aAdapterPinPadStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPinPadStyle.setAdapter(aAdapterPinPadStyle);	
		spinnerPinPadStyle.setSelection(param.getPinPadStyle());
		spinnerPinPadStyle.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				param.setPinPadStyle(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});	
		
	}
	
	public void checksumDialog(String title,String message)
	{
		AlertDialog alertDialogMessage;
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(title);
		dialog.setMessage(message);

	   	dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface arg0, int arg1) { 
            }  
        });		
		
		alertDialogMessage = dialog.create();  
		alertDialogMessage.show(); 
	} 	
	
	public void onClick_Checksum(View view)
	{		
		String kernel = "EMV";//EMV
		String configuration = "E0F8C8";
		String key = "3333333333333333";

		try{
			String checksumKernel = MessageDigestUtil.encodeDES(convertBytesToHex(kernel.getBytes()),key);
			String checksumConfiguration = MessageDigestUtil.encodeDES(configuration,key);
			checksumDialog("Checksum","Kernel:"+checksumKernel +"\nConfiguration:"+checksumConfiguration);

		} catch (Exception e) {
		}   					
		
		
	}	
	
	
}
