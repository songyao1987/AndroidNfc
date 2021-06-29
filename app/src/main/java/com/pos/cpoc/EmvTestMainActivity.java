package com.pos.cpoc;

import java.io.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.lang.String;
import java.lang.InterruptedException;
import android.content.pm.PackageManager;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.tech.IsoDep;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcV;
import android.nfc.tech.NfcF;
import android.os.Parcelable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.view.Gravity;
import android.view.Window;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.pos.cpoc.config.Terminal;
import com.pos.cpoc.config.TerminalConfig;
import com.pos.cpoc.emv.EmvData;
import com.pos.cpoc.emv.EmvResult;
import com.pos.cpoc.emv.EmvTermParam;
import com.pos.cpoc.emv.EmvTransParam;
import com.pos.cpoc.emv.OnEmvListener;
import com.pos.cpoc.utils.AmountUtil;
import com.pos.cpoc.utils.DateUtil;
import com.pos.cpoc.utils.ISOUtil;
import com.pos.cpoc.utils.MessageDigestUtil;
import com.pos.cpoc.utils.StringUtil;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import static com.pos.cpoc.utils.ByteUtil.bytes2HexString;
import static com.pos.cpoc.utils.ByteUtil.convertBytesToHex;
import static com.pos.cpoc.utils.ByteUtil.hexToStringGBK;


public class EmvTestMainActivity extends AppCompatActivity
{	
	private NfcAdapter mNfcAdapter = null;
	private PendingIntent mPendingIntent = null;
	private IntentFilter[] mIntentFilter = null;
	private String[][] mTechList = null;
	public static IsoDep isoDep = null;
	EmvHandler  emvHandler = EmvHandler.getInstance();
	EmvTransParam emvTransParam = new EmvTransParam();
	EmvTrans emvTrans = new EmvTrans();
	private Param param;
	private TextView tv_message ;
	MessageHandler _mHandler;
	private boolean runing = false;
	boolean threadFlag;
	int iOnlineRet;
	byte[] isEcTrans = new byte[1];
	byte[] transResult = new byte[1];
	byte[] bCVMType = new byte[1];  
	byte[] balance = new byte[6];
	byte[] pinblock = new byte[12+1];
	String encryptedPin ;
    CountDownLatch mLatch;
    long iStartTime;
    long iEndTime;
	int iRet = 0;		
	int function = 0;
	String amount ;
	String cashbackAmount ;
	String strAmount ;
	byte transType = 0x00;
	private static int[] mIccTags = {0x5F2A,0x82,0x84,0x95,0x9A,0x9C,0x9F02,0x9F03,
			0x9F09,0x9F10,0x9F1A,0x9F1E,0x9F26,0x9F27,0x9F33,0x9F35,0x9F36,0x9F37,0x9F41,0x5F34,0x9F53};
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
			"android.permission.READ_EXTERNAL_STORAGE",
			"android.permission.WRITE_EXTERNAL_STORAGE" };
	String successMsg = new String();
	String[] response = new String[3];
	String title = new String();

	OnEmvListener onEmvListener = new OnEmvListener() {

		@Override
		public int onSelApp(String[] appLabelList) {
			// TODO Auto-generated method stub
			Log.d("Debug","onSelApp");
			int iRet = appSelDialog(appLabelList);
			Log.d("Debug", "iRet="+iRet);
			return iRet;
		}

		@Override
		public int onRequestInputAmount() {
			// TODO Auto-generated method stub
			Log.d("Debug","onRequestInputAmount");
			return 0;
		}

		@Override
		public int onConfirmCardNo(String cardNo) {
			// TODO Auto-generated method stub
			Log.d("Debug","onConfirmCardNo");
			int iRet=0;
			if(EmvTermParam.emvTest == EmvData.KERNAL_CONFIG_UNIONPAY) {
				Log.d("Debug","transcation time = " + (System.currentTimeMillis()-iStartTime));
				iRet = confirmDialog(title,"PAN:\n"+cardNo+"\n"+"Please Confirm:");
				Log.d("Debug", "iRet="+iRet);
			}
			return iRet;
		}

		@Override
		public int onInputPIN(byte pinType) {
			// TODO Auto-generated method stub
			Log.d("Debug","onInputPIN");
			return 0;
		}

		@Override
		public int onCertVerify(int certType,String certNo) {
			// TODO Auto-generated method stub
			Log.d("Debug","onCertVerify");
			return 0;
		}

		@Override
		public int onlineProc() {
			// TODO Auto-generated method stub
			Log.d("Debug","onOnlineProc");
			String[] track2 = new String[1];
			String[] pan = new String[1];
			Log.d("Debug","iOnlineRet=" + emvHandler.getTrack2AndPAN(track2, pan)
					+ "Track2=" + track2[0] + "PAN=" + pan[0]);
			iOnlineRet = sendOnline();
			Log.d("Debug", "sendOnline() iOnlineRet="+iOnlineRet);
			return iOnlineRet;
		}

		@Override
		public byte[] onExchangeApdu(byte[] send) {
			// TODO Auto-generated method stub
			Log.d("Debug","Callback nfc");
			try{
				byte[] recv = isoDep.transceive(send);
				return recv;
			} catch (IOException e) {
				Log.d("Debug","EmvCbExchangeApdu IOException");
				return null;
			}
		}

		@Override
		public int onConfirmEcSwitch() {
			// TODO Auto-generated method stub
			Log.d("Debug","onConfirmEcSwitch");
			return 0;
		}
	};
    Handler handler = new Handler(){
    	public void handleMessage(Message msg) {
    		Log.d("Debug","STATIC:"+msg.what);

    		switch (msg.what) {
    		case EmvTrans.STATIC_TRANS_INIT			:
    			iRet = 0;
    			function = msg.arg1;
    			Log.d("Debug","============"+"function:"+function+"============");

    			sendMessages("");
    			termParamAndKernelInit(param);
    			strAmount = "";
    			if(function == EmvTrans.STATIC_TRANS) {
    				title = getResources().getString(R.string.title_trans);
    			} else if(function == EmvTrans.STATIC_BALANCE_QUERY) {
    				title = getResources().getString(R.string.title_balance_query);
				} else if(function == EmvTrans.STATIC_READ_CARD_NO) {
    				title = getResources().getString(R.string.title_read_cardno);
    			} else if(function == EmvTrans.STATIC_READ_TRANS_LOG) {
    				title = getResources().getString(R.string.title_read_trans_log);
    			} else {
    				title = "";
    			}
    			handler.sendEmptyMessage(EmvTrans.STATIC_EMV_CORE_INIT);
    			break;
    		case EmvTrans.STATIC_EMV_CORE_INIT		:
        		if(function == EmvTrans.STATIC_TRANS) {
        			handler.sendEmptyMessage(EmvTrans.STATIC_INPUT_AMOUNT);
        		} else if(function == EmvTrans.STATIC_BALANCE_QUERY
						|| function == EmvTrans.STATIC_READ_CARD_NO
						|| function == EmvTrans.STATIC_READ_TRANS_LOG) {
        			handler.sendEmptyMessage(EmvTrans.STATIC_SWIPE_CARD);
				}
    			break;
    		case EmvTrans.STATIC_INPUT_AMOUNT		:
				inputAmountDialog();
    			break;
    		case EmvTrans.STATIC_INPUT_AMOUNT_END	:
    			if(msg.arg1 == 0) {
    				//ok
    				Log.d("Debug", "amount="+amount);
    				if((function == EmvTrans.STATIC_TRANS)
							&& (param.getKernelConfig() == EmvData.KERNAL_CONFIG_QPBOC
							|| param.getKernelConfig() == EmvData.KERNAL_CONFIG_QUICS
							|| param.getKernelConfig() == EmvData.KERNAL_CONFIG_PAYWARE)) {
    					iRet = emvHandler.qTransProProc(ISOUtil.hex2byte(amount));
    					if(iRet != 0) {
    						handler.sendEmptyMessage(EmvTrans.STATIC_TRANS_END);
    						break;
    					}
    					qTransParamInit(amount,param);
    					threadFlag = false;
    				}
    				handler.sendEmptyMessage(EmvTrans.STATIC_SWIPE_CARD);
    			} else {
    				iRet = Error.USER_CANCEL_INPUT;
     				handler.sendEmptyMessage(EmvTrans.STATIC_TRANS_END);
    			}
    			break;
    		case EmvTrans.STATIC_SWIPE_CARD	:
    			if(function == EmvTrans.STATIC_TRANS) {
    				strAmount = AmountUtil.changeFen2Yuan(amount)+ getResources().getString(R.string.tv_amount_unit);
    			}
    			swipeCardDialog(title);
    			break;
    		case EmvTrans.STATIC_SWIPE_MULTI_CARD	:
    			swipeCardDialog("Multiple card detected");
    			break;
    		case EmvTrans.STATIC_SWIPE_CARD_CHECK_PHONE:
    			swipeCardDialog("Please check your phone！check it again");
//    			EmvTrans.EmvQTransParamInit(amount,param);
//    			handler.sendEmptyMessage(EmvTrans.STATIC_TRANS);
    			break;
    		case EmvTrans.STATIC_SWIPE_CARD_END:
    			if(msg.arg1 == 0) {
    				handler.sendEmptyMessage(EmvTrans.STATIC_SWIPE_CARD);
    			} else if(msg.arg1 == 1) {
    				iRet = Error.USER_CANCEL_INPUT;
    				function = 0;
    				handler.sendEmptyMessage(EmvTrans.STATIC_TRANS_END);
    			} else if(msg.arg1 == 2) {
    				//detected card.
    				if(alertDialogSwipeCard != null) {
    					alertDialogSwipeCard.dismiss();
    				}
    				handler.sendEmptyMessage(function);
    			}
    			if(msg.arg1 == 3) {
    				if(alertDialogSwipeCard != null) {
    					alertDialogSwipeCard.dismiss();
    				}
    				handler.sendEmptyMessage(EmvTrans.STATIC_SWIPE_MULTI_CARD);
    			}

    			break;
    		case EmvTrans.STATIC_DETECTED_CARD		:
    			handler.sendEmptyMessage(EmvTrans.STATIC_TRANS);
    			break;
    		case EmvTrans.STATIC_READ_CARD_NO		:
				new Thread(new Runnable() {

					@Override
					public void run() {
		    			String[] ReadTrack2 = new String[1];
		    			String[] ReadPan = new String[1];
						iRet = emvHandler.readPANProc(EmvData.KERNAL_CONTACTLESS_ENTRY_POINT,onEmvListener,
								ISOUtil.hex2byte(DateUtil.DateToStr(new Date(), "yyMMdd")),ReadTrack2,ReadPan);
		    			if(iRet == 0) {
			    			successMsg = " Successfull\n"+" PAN："+ReadPan[0];
			    		}
			    		handler.sendEmptyMessage(EmvTrans.STATIC_TRANS_END);
					}
				}).start();
    			break;
    		case EmvTrans.STATIC_INPUT_PIN			:
    			inputPINDialog();
    			break;
    		case EmvTrans.STATIC_INPUT_PIN_END		:
    			if(msg.arg1 == 0) {
    				handler.sendEmptyMessage(EmvTrans.STATIC_ONLINE);
    			}else {
    				iRet = Error.USER_CANCEL_INPUT;
    				handler.sendEmptyMessage(EmvTrans.STATIC_TRANS_END);
    			}
    			break;
    		case EmvTrans.STATIC_ONLINE				:
				new Thread(new Runnable() {
					@Override
					public void run() {
						try{
							do {
								//socket online connection
								//send auth request
								//receive and parse response
								if ("3030".equals(response[0])) {
					    			successMsg = "Transaction Approved\n";
									if(balance[0] == 0x00) {
										successMsg = " " + successMsg+"Availiable Balance："+AmountUtil.changeFen2Yuan(ISOUtil.hexString(balance));
									}
					    			handler.sendEmptyMessage(EmvTrans.STATIC_TRANS_END);
								}
								else {
									iRet = ISOUtil.parseInt(new String(ISOUtil.hex2byte(response[0])));
									handler.sendEmptyMessage(EmvTrans.STATIC_TRANS_END);
								}
							} while (false);

						}
						catch(Exception e){
							iRet = Error.EXCEPTION_ERR;
							handler.sendEmptyMessage(EmvTrans.STATIC_TRANS_END);
						}
					}
				}).start();
    			break;
    		case EmvTrans.STATIC_TRANS				:
				new Thread(new Runnable() {
					@Override
					public void run() {
						emvTransParam.setReaderTTQ("26000080");
						emvTransParam.setTransKernalType(EmvData.KERNAL_CONTACTLESS_ENTRY_POINT) ;
						if(param.getKernelConfig() == EmvData.KERNAL_CONFIG_UNIONPAY
								|| param.getKernelConfig() == EmvData.KERNAL_CONFIG_QPBOC) {
							//qPBOC
							emvTransParam.setReaderTTQ("26000080");
						} else if(param.getKernelConfig() == EmvData.KERNAL_CONFIG_QUICS) {
							//qUICS
							emvTransParam.setReaderTTQ("26004080");
						} else if(param.getKernelConfig() == EmvData.KERNAL_CONFIG_PAYWARE) {
							//PayWare
							emvTransParam.setReaderTTQ("26004080");
						}
						if(param.getIsForcedOnline()){
							emvTransParam.setIsForceOnline((byte)0x01);
						}
						else {
							emvTransParam.setIsForceOnline((byte)0x00);
						}
						Log.d("Debug","emvTransParam.getIsForceOnline="+emvTransParam.getIsForceOnline());
						Log.d("Debug","emvTrans");
						emvTransParam.setTerminalSupportIndicator((byte)0x00);
						emvTransParam.setTransDate(DateUtil.DateToStr(new Date(), "yyMMdd"));
						emvTransParam.setTransTime(DateUtil.DateToStr(new Date(), "HHmmss"));
						emvTransParam.setAmountAuth(amount);
						emvTransParam.setAmountOther(cashbackAmount);
						emvTransParam.setTransNo(ISOUtil.zeropad(String.valueOf(param.getTransNo()),8));
						param.setTransNo(param.getTransNo()+1);
						Log.d("Debug","param.getTransactionType()="+param.getTransactionType());
						emvTransParam.setTransType(param.getTransactionType());
						iStartTime = System.currentTimeMillis();
						iOnlineRet = EmvResult.EMV_NO_ONLINE;
						if(param.getKernelConfig() == EmvData.KERNAL_CONFIG_PAYPASS){
							balance[0] = 0x01;
							byte result[] = new byte[36];
							iRet =  emvHandler.paypassTrans(emvTransParam, onEmvListener, result);
							if((result[0] & 0x10) != 0){
								transResult[0] = (byte)EmvData.APPROVE_M;
							}else if((result[0] & 0x20) != 0){
								transResult[0] = (byte)EmvData.DECLINE_M;
							}else if ((result[0] & 0x30) != 0) {
								transResult[0] = (byte)EmvData.ONLINE_M;
							}
						}else{
							iRet =  emvHandler.emvTrans(emvTransParam, onEmvListener, isEcTrans, balance, transResult);
						}
						Log.d("Debug","transcation time = " + (System.currentTimeMillis()-iStartTime));
						Log.d("Debug", "iRet="+iRet);
						Log.d("Debug", "transResult="+transResult[0]);
						Log.d("Debug", "isEcTrans="+isEcTrans[0]);
						Log.d("Debug", "balance="+emvHandler.bytesToHexString(balance));
						Log.d("Debug", "iOnlineRet="+iOnlineRet);

						int iEndRet = 0;
						if(transResult[0] == (byte)EmvData.APPROVE_M){
			                if(iOnlineRet  == EmvResult.EMV_ONLINE_NORESP_MAC_OR_RECV_ERR){
			                	Log.d("Debug"," Online transaction declined\n");

			                	if(EmvTermParam.emvTest == EmvData.KERNAL_CONFIG_EMV
										|| EmvTermParam.emvTest == EmvData.KERNAL_CONFIG_PBOC){
			                		//emv test
									iEndRet = sendReversal();
									Log.d("Debug","sendReversal() iEndRet="+iEndRet);
			                	}

			                } else if(iOnlineRet == EmvResult.EMV_NO_ONLINE){
								Log.d("Debug"," Offline Approved\n");
							} else{
								Log.d("Debug"," Online Approved\n");
							}
							Log.d("Debug","Save EMV Transaction\n");
							if(EmvTermParam.emvTest == EmvData.KERNAL_CONFIG_EMV
									|| EmvTermParam.emvTest == EmvData.KERNAL_CONFIG_PBOC){
								iEndRet = sendConfirm();
								Log.d("Debug","sendConfirm() iEndRet="+iEndRet);
							}
							iEndRet = sendTransResult(0x01);
							Log.d("Debug","sendTransResult() iEndRet="+iEndRet);

						} else if(transResult[0] == (byte)EmvData.DECLINE_M) {
			                if(iOnlineRet  == EmvResult.EMV_NO_ONLINE){
			                	if(iRet != EmvResult.EMV_OK) {
			                		Log.d("Debug","Transaction terminated\n");
			                		handler.sendEmptyMessage(EmvTrans.STATIC_TRANS_END);
			                		return;
			                	} else {
				                	Log.d("Debug","Offline declined\n");
								}
			                } else if(iOnlineRet  == EmvResult.EMV_ONLINE_FAILED){
			                	Log.d("Debug","Online failed\n");
			                } else if(iOnlineRet  == EmvResult.EMV_ONLINE_NORESP_MAC_OR_RECV_ERR){
			                	Log.d("Debug","Online failed for timeout or Mac verification error\n");

			                	if(EmvTermParam.emvTest == EmvData.KERNAL_CONFIG_EMV || EmvTermParam.emvTest == EmvData.KERNAL_CONFIG_PBOC){
									iEndRet = sendReversal();
									Log.d("Debug","sendReversal() iEndRet="+iEndRet);
			                	}

			                } else if(iOnlineRet  == EmvResult.EMV_ONLINE_RESP_AAC){
			                	Log.d("Debug","Auth code error(Field#39)\n");

			                	//iRet = Error.TXN_REFUSED_BY_POSCENTER;

			                } else if(iOnlineRet  == EmvResult.EMV_OK){
			                	Log.d("Debug","Online succeed, transaction declined,need to reversal now\n");
			                    //iRet = Error.CONTACT_ISSUING_BANK_BANK;

								if(EmvTermParam.emvTest == EmvData.KERNAL_CONFIG_EMV || EmvTermParam.emvTest == EmvData.KERNAL_CONFIG_PBOC){
									iEndRet = sendReversal();
									Log.d("Debug","sendReversal() iEndRet="+iEndRet);
								}
			                }
			                if(iRet == EmvResult.EMV_OK){
			                	iRet = EmvResult.EMV_DECLINE;
			                	iEndRet = sendTransResult(0x02);
			                } else if(iRet == EmvResult.EMV_NOT_ACCEPT){
			                	iEndRet = sendTransResult(0x04);
			                } else{
			                	Log.d("Debug","Transaction terminated\n");
								iEndRet = sendTransResult(0x03);
							}
							Log.d("Debug","sendReversal() iEndRet="+iEndRet);

						}

						handler.sendEmptyMessage(EmvTrans.STATIC_TRANS_END);
					}
				}).start();
    			break;
    		case EmvTrans.STATIC_TRANS_END			:
    			Log.d("Debug","Trans iRet = "+iRet);
    			if(iRet == 0) {
    				if(function == EmvTrans.STATIC_TRANS) {
//    					iRet = skHandler.saveTransRecs(emvTrans.packBatch());
    					successMsg = " " + getResources().getString(R.string.trans_approved) + "\n";
    				}
    				sendMessages(successMsg);
					infoField55();
    			} else if(iRet == Error.USER_CANCEL_INPUT || iRet == Error.INPUT_TIME_OUT) {
    				//Cancel and timeout
					successMsg = " " + getResources().getString(R.string.trans_cancelled) + "\n";
					sendMessages(successMsg);
    			} else if(iRet == EmvResult.EMV_USE_CONTACT || iRet == EmvResult.EMV_TERMINATE) {
    				successMsg = " " + getResources().getString(R.string.trans_termination) + "\n";
    				sendMessages(successMsg);
    			} else if(iRet == EmvResult.EMV_DECLINE) {
    				successMsg = " " + getResources().getString(R.string.trans_declined) + "\n";
					if(balance[0] == 0x00) {
						successMsg = " " + successMsg+"Availiable Balance："+AmountUtil.changeFen2Yuan(ISOUtil.hexString(balance));
					}
					sendMessages(successMsg);
					infoField55();
                } else if(iRet > 0 || iRet == EmvResult.EMV_EXCP_FILE
						|| iRet == EmvResult.EMV_DATA_AUTH_FAIL || iRet == EmvResult.EMV_CARD_EXPIRED) {
                	successMsg = getResources().getString(R.string.trans_declined) + "\n";
					successMsg = successMsg +Error.getErrorMsg(iRet)+"\n";
					if(balance[0] == 0x00) {
						successMsg = " " + successMsg+"Availiable Balance："+AmountUtil.changeFen2Yuan(ISOUtil.hexString(balance));
					}
					sendMessages(successMsg);
					infoField55();
                } else {
    				successMsg = " " + getResources().getString(R.string.trans_termination) + "\n";
    				successMsg = successMsg +Error.getErrorMsg(iRet)+"\n";
    				sendMessages(successMsg);
					infoField55();
    			}
    			break;
			default:
				break;
			}
    	}
    };

    public void sendMessages(String str) {
		_mHandler.sendMessage(str);
	}

	public static void verifyStoragePermissions(Activity activity) {
		try {
			int permission = ActivityCompat.checkSelfPermission(activity,
					"android.permission.WRITE_EXTERNAL_STORAGE");
			if (permission != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emv_l2_test_main);
		verifyStoragePermissions(this);
        tv_message = (TextView) findViewById(R.id.TextViewStatus);
        _mHandler = new MessageHandler(tv_message);
        mLatch = new CountDownLatch(1);
        new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				param = new Param(EmvTestMainActivity.this);
				Log.d("Debug", EmvTermParam.emvParamFilePath);
				SdkFile sdkFile = new SdkFile();
				if(sdkFile.dirIsEmpty(EmvTermParam.emvParamFilePath)) {
					Log.d("Debug", EmvTermParam.emvParamFilePath+" is empty dir!");
					sdkFile.copyAssets(EmvTestMainActivity.this, "emv", EmvTermParam.emvParamFilePath);
				}
				emvHandler.kernelInit(new EmvTermParam());
				mLatch.countDown();
			}
		}).start();

        try {
            mLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        nfcCheck();
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter intentFilter1 = new IntentFilter(mNfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter intentFilter2 = new IntentFilter(mNfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter intentFilter3 = new IntentFilter(mNfcAdapter.ACTION_TAG_DISCOVERED);
        mIntentFilter = new IntentFilter[]{intentFilter1, intentFilter2, intentFilter3};
        mTechList = new String[][]{
                {IsoDep.class.getName()}, {NfcA.class.getName()}, {NfcB.class.getName()},
                {NfcV.class.getName()}, {NfcF.class.getName()}, {Ndef.class.getName()}};

    }
       
    private void nfcCheck() {
    	Log.d("Debug", "nfcCheck OK");
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not supported", Toast.LENGTH_SHORT).show();
        } else {
            if (!mNfcAdapter.isEnabled()) {
            	Toast.makeText(this, "NFC is not open", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("Debug", "Tag:" + intent.getParcelableExtra(mNfcAdapter.EXTRA_TAG));
        Parcelable p = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if(p == null) {
        	//Toast.makeText(this,"NFC Tag not connect！", Toast.LENGTH_SHORT).show();
        	return;
        }
        try {
        	isoDep = IsoDep.get((Tag)p);
            isoDep.connect();
			//detected card already.
			Message message = handler.obtainMessage(EmvTrans.STATIC_SWIPE_CARD_END,2,0);
			handler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null)
        	mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilter, mTechList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null)
        	mNfcAdapter.disableForegroundDispatch(this);
    }


	public void onClickTrans(View view) {
		threadFlag = true;
		Log.d("Debug", "onClick_Trans");
		Message message = handler.obtainMessage(EmvTrans.STATIC_TRANS_INIT,EmvTrans.STATIC_TRANS,0);
		handler.sendMessage(message);
	}

	public void onClickReadCardNo(View view) {
		threadFlag = true;
		Message message = handler.obtainMessage(EmvTrans.STATIC_TRANS_INIT,EmvTrans.STATIC_READ_CARD_NO,0);
		handler.sendMessage(message);
	}

	public void onClickExit(View view) {
		threadFlag = true;
		super.finish();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_emv_l2_test_main, menu);
		return true;
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Log.d("Debug", "onClick_ShowDebugLog");
        switch (item.getItemId()) {
        case R.id.itemShowDebugLog:

		    try {
				Process process = Runtime.getRuntime().exec("logcat -d -s Debug:D");
				BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));

				StringBuilder log=new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					//if(line.indexOf("D/Debug") >= 0)
					//{
						//Log.d("Debug", line);
						log.append(line.substring(line.lastIndexOf("):")+2)+"\n");
					//}
				}

				infoDialog("Logs", log.toString());

		    } catch (IOException e) {
		    //
		    }
		    break;
        case R.id.itemEMVKernelVersion:
    		String kernel = "EMV";//EMV
    		String configuration = "E0F8C8";
    		String key = "3333333333333333";

    		try{
    			String checksumKernel = MessageDigestUtil.encodeDES(convertBytesToHex(kernel.getBytes()),key);
    			String checksumConfiguration = MessageDigestUtil.encodeDES(configuration,key);
            	infoDialog(getResources().getString(R.string.title_EMVKernelVersion),"Kernel Version:"+emvHandler.getKernelVersion()+"\nKernel Checksum:"+checksumKernel +"\nConfiguration Checksum:"+checksumConfiguration);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}


        }
        return false;
	}

	/**
	 * online request and got response from acquirer or bank
	 * @return
	 */
	public int sendOnline() {
		Log.d("Debug","sendOnline");
		// socket connection
		//pack request
		//send request
		//receive response
		//parse response
		//auth code Tag80,Tag91,Tag71 or Tag72
		return 0;
	}

	//sending confirmation
	public int sendConfirm() {
		Log.d("Debug","sendConfirm");
		return 0;
	}

	//sending reversal
	public int sendReversal() {
		Log.d("Debug","sendReversal");

		return 0;
	}

	//sending trans result
	public int sendTransResult(int transResult) {
		Log.d("Debug","sendTransResult");
		return 0;
	}

	private AlertDialog alertDialogInputAmount;
    public void inputAmountDialog()
    {
    	final View viewDialog = (View)getLayoutInflater().inflate(
				R.layout.dialog_input_amount, null);

    	final EditText editTextCashbackAmount = (EditText)viewDialog.findViewById(R.id.EditTextCashbackAmount);

    	final LinearLayout linearLayoutEditTextCashbackAmount = (LinearLayout)viewDialog.findViewById(R.id.LinearLayoutEditTextCashbackAmount);
    	final LinearLayout linearLayoutTextViewCashbackAmount = (LinearLayout)viewDialog.findViewById(R.id.LinearLayoutTextViewCashbackAmount);
    	if(param.getTransactionType() != 0x09){
    		Log.d("Debug", "not Cashbak");
    		linearLayoutEditTextCashbackAmount.setVisibility(View.GONE);
    		linearLayoutTextViewCashbackAmount.setVisibility(View.GONE);
    	}

    	final EditText editTextAmount = (EditText)viewDialog.findViewById(R.id.EditTextAmount);
    	editTextAmount.setSelection(editTextAmount.getText().length());
    	editTextAmount.getSelectionStart();


    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(R.string.title_trans).setView(viewDialog).setPositiveButton(R.string.ok,
			new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog,
						int whichButton)
				{
					//ok
					amount = ISOUtil.zeropad(editTextAmount.getText().toString(),12);
					cashbackAmount = ISOUtil.zeropad(editTextCashbackAmount.getText().toString(),12);
					Message message = handler.obtainMessage(EmvTrans.STATIC_INPUT_AMOUNT_END, 0, 0);
					handler.sendMessage(message);
				}
			}).setNegativeButton(R.string.cancel,
			new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog,
						int whichButton)
				{
					//cancel
					Message message = handler.obtainMessage(EmvTrans.STATIC_INPUT_AMOUNT_END, 1, 0);
					handler.sendMessage(message);
					function = 99;
				}
			});

		alertDialogInputAmount = dialog.create();
		alertDialogInputAmount.show();
		alertDialogInputAmount.setCanceledOnTouchOutside(false);// show之前设置无效


    }

    private AlertDialog alertDialogInputPIN;
    public void inputPINDialog()
    {
    	final View viewDialog = (View)getLayoutInflater().inflate(
				R.layout.dialog_input_pin, null);

    	final EditText editTextPIN = (EditText)viewDialog.findViewById(R.id.EditTextPIN);
    	AlertDialog.Builder dialog = new AlertDialog.Builder(EmvTestMainActivity.this);
		dialog.setTitle(R.string.tv_enter_pin).setView(viewDialog).setPositiveButton(R.string.ok,
			new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog,
						int whichButton)
				{
					// 确定
    				String pin  = String.valueOf(editTextPIN.getText().toString());
    				String[] track2 = new String[1];
    				String[] pan = new String[1];
    				String key = "3333333333333333";
    				int iRet = emvHandler.getTrack2AndPAN(track2, pan);
    				if(iRet == 0 && pan[0] != null && pan[0].length() > 0)
    				{
    					try{
    						encryptedPin = MessageDigestUtil.encodePin(pin,pan[0],key);
						} catch (Exception e) {
						}
    				}
					Log.d("Debug","encryptedPin="+encryptedPin);
					Message message = handler.obtainMessage(EmvTrans.STATIC_INPUT_PIN_END,0,0,encryptedPin);
					handler.sendMessage(message);
				}
			}).setNegativeButton(R.string.cancel,
			new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog,
						int whichButton)
				{
					// 取消
					Message message = handler.obtainMessage(EmvTrans.STATIC_INPUT_PIN_END, 1, 0,"");
					handler.sendMessage(message);
				}
			});

		alertDialogInputPIN = dialog.create();
		alertDialogInputPIN.show();
		alertDialogInputPIN.setCanceledOnTouchOutside(false);// show之前设置无效

    }


    private int inputPINResult = 0x00;
    public int inputPINDialog(byte pinType)
    {
    	final byte InputPinType = pinType;
    	String pinTitle;
    	if(pinType == EmvData.ONLINE_ENCIPHERED_PIN)
    	{
    		pinTitle = getResources().getString(R.string.tv_enter_pin);
    	}
    	else
    	{
    		pinTitle = getResources().getString(R.string.tv_enter_offline_pin);
    	}
    	final String pinTypeTitle = pinTitle;

		mLatch = new CountDownLatch(1);
    	final View viewDialog = (View)getLayoutInflater().inflate(
				R.layout.dialog_input_pin, null);
       	final EditText editTextPIN = (EditText)viewDialog.findViewById(R.id.EditTextPIN);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                 	AlertDialog.Builder dialog = new AlertDialog.Builder(EmvTestMainActivity.this);
            		dialog.setTitle(pinTypeTitle).setView(viewDialog).setPositiveButton(R.string.ok,
            			new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
            						int whichButton) {

            					String pin  = String.valueOf(editTextPIN.getText().toString());
            					if(InputPinType == EmvData.ONLINE_ENCIPHERED_PIN){

	                				String[] track2 = new String[1];
	                				String[] pan = new String[1];
	                				String key = "3333333333333333";
	                				int iRet = emvHandler.getTrack2AndPAN(track2, pan);
	                				if(iRet == 0 && pan[0] != null && pan[0].length() > 0) {
	                					try{
	                						encryptedPin = MessageDigestUtil.encodePin(pin,pan[0],key);
	                						byte[] pin1 = emvHandler.hexStringToBytes(encryptedPin);
	                						System.arraycopy(pin1, 0, pinblock, 0, pin.length());
	                						pinblock[pin.length()] = 0x00;

	            						} catch (Exception e) {
	            						}
	                				}
            					} else{
            						byte pin1[] = pin.getBytes();
            						System.arraycopy(pin1, 0, pinblock, 0, pin.length());
            						pinblock[pin.length()] = 0x00;

            						encryptedPin = pin;
            					}

                				Log.d("Debug","encryptedPin="+encryptedPin);

                				if(pin.length() == 0) {
                					inputPINResult = EmvResult.EMV_NO_PASSWORD;
                				} else {
                					//pin length =0
                					inputPINResult = EmvResult.EMV_OK;;
                				}

            					mLatch.countDown();
            				}
            			}).setNegativeButton(R.string.cancel,
            			new DialogInterface.OnClickListener() {
            				public void onClick(DialogInterface dialog,
            						int whichButton) {
            					inputPINResult = EmvResult.EMV_USER_CANCEL;
            					mLatch.countDown();
            				}
            			});

            		Looper.prepare();
            		alertDialogInputPIN = dialog.create();
            		alertDialogInputPIN.show();
            		alertDialogInputPIN.setCanceledOnTouchOutside(false);
            		Looper.loop();
                  } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            mLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		return inputPINResult;

    }


    public int getPin(byte pinType)
    {
		String pin  = "1111";
		if(pinType == EmvData.ONLINE_ENCIPHERED_PIN){

			String[] track2 = new String[1];
			String[] pan = new String[1];
			String key = "3333333333333333";
			int iRet = emvHandler.getTrack2AndPAN(track2, pan);
			if(iRet == 0 && pan[0] != null && pan[0].length() > 0)
			{
				try{
					encryptedPin = MessageDigestUtil.encodePin(pin,pan[0],key);
					byte[] pin1 = emvHandler.hexStringToBytes(encryptedPin);
					System.arraycopy(pin1, 0, pinblock, 0, pin.length());
					pinblock[pin.length()] = 0x00;

				} catch (Exception e) {
				}
			}
		}
		else{
			byte pin1[] = pin.getBytes();
			System.arraycopy(pin1, 0, pinblock, 0, pin.length());
			pinblock[pin.length()] = 0x00;

			encryptedPin = pin;
		}

		Log.d("Debug","encryptedPin="+encryptedPin);

		if(pin.length() == 0)
		{
			inputPINResult = EmvResult.EMV_NO_PASSWORD;
		}
		else
		{//pin length =0
			inputPINResult = EmvResult.EMV_OK;;
		}
		return inputPINResult;
    }


    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }

    private AlertDialog dialogMessage;
    private int myShowMS;
	public void messageDialog(String title,String message,int showMs)
	{
		myShowMS = showMs;

		if(dialogMessage != null)
		{
			dialogMessage.dismiss();
		}

		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(title);

		dialog.setMessage(message);

		dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            	dialogMessage.dismiss();
            }
        });

//	   	dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//            }
//        });

		dialogMessage = dialog.create();
		dialogMessage.show();


        WindowManager.LayoutParams lp = dialogMessage.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        Window win = dialogMessage.getWindow();
        win.setAttributes(lp);

        // only 1 second
        new Thread(new Runnable() {

            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime < myShowMS) {
                    try {
                         Thread.sleep(100);
                    } catch (InterruptedException e) {
                    	dialogMessage.dismiss();
                    }
                }

                dialogMessage.dismiss();
            }
        }).start();

	}

	private AlertDialog alertDialogInfo;
	public void infoDialog(String title,String info)
	{
    	final View viewDialog = (View)getLayoutInflater().inflate(
				R.layout.dialog_info, null);

    	final TextView tvInfo = (TextView)viewDialog.findViewById(R.id.textviewInfo);
    	tvInfo.setText(info);

	   	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(title);
		dialog.setView(viewDialog);

	   	dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

	   	alertDialogInfo = dialog.create();
	   	alertDialogInfo.show();
	   	alertDialogInfo.setCanceledOnTouchOutside(false);// show之前设置无效

	}

	// 显示需要打印的数据
	private void showPrint() {
		String tmp;
		StringBuffer sb = new StringBuffer();

		//qPBOC---------------------------
		sb.append("\n");
		tmp = emvTrans.getTLVData(0x9F4E);
		sb.append("Merchant Name: " + hexToStringGBK(tmp) + "\n");

		tmp = emvTrans.getTLVData(0x50);
		sb.append("Application Label: " + hexToStringGBK(tmp) + "\n");
		//------------------------------------

		tmp = emvTrans.getTLVData(0x9F36);
		sb.append("ATC: " + tmp + "\n");

		//tmp = EmvTrans.EmvGetTLVData(0x5A);
		String track2[] = new String[1];
		String pan[] = new String[1];
		emvHandler.getTrack2AndPAN(track2, pan);
		sb.append("PAN: " + pan[0] + "\n");

		tmp = emvTrans.getTLVData(0x4F);
		sb.append("AID: " + tmp + "\n");

		tmp = emvTrans.getTLVData(0x9A);
		sb.append("Transaction Date: " + tmp + "\n");

		tmp = emvTrans.getTLVData(0x9F21);
		sb.append("Transaction Time: " + tmp + "\n");

		tmp = emvTrans.getTLVData(0x9F02);
		sb.append("Amount,Authorised: " + StringUtil.TwoWei(tmp) + "\n");

		tmp = emvTrans.getTLVData(0x9C);
		sb.append("Transaction Type: " + tmp + "\n");

		if(balance[0] == 0x00) {
			sb.append("Balance: " + StringUtil.TwoWei(ISOUtil.hexString(balance)) + "\n");
		}

		if(bCVMType[0] == EmvData.RD_CVM_SIG) {
			sb.append("CardHolder Signature: " + "\n");
			sb.append("---------------------\n");
		}

		infoDialog("Print", sb.toString());

	}

	private AlertDialog alertDialogSwipeCard;
    public void swipeCardDialog(String title)
    {
    	final View viewDialog = (View)getLayoutInflater().inflate(
				R.layout.dialog_swipe_card, null);

		TextView tvCard = (TextView)viewDialog.findViewById(R.id.TextViewCard);

		if(param.getKernelConfig() == EmvData.KERNAL_CONFIG_UNIONPAY )
		{
			tvCard.setText(strAmount +"\n" + getResources().getString(R.string.tv_insert_or_swipe_card));
		}
		else if(param.getKernelConfig() == EmvData.KERNAL_CONFIG_QPBOC || param.getKernelConfig() == EmvData.KERNAL_CONFIG_QUICS || param.getKernelConfig() == EmvData.KERNAL_CONFIG_PAYWARE || param.getKernelConfig() == EmvData.KERNAL_CONFIG_PAYPASS)
		{
			tvCard.setText(strAmount + "\n" + getResources().getString(R.string.tv_swipe_rf_card));
		}
		else if(param.getKernelConfig() == EmvData.KERNAL_CONFIG_EMV || param.getKernelConfig() == EmvData.KERNAL_CONFIG_PBOC)
		{
			tvCard.setText(strAmount + "\n" + getResources().getString(R.string.tv_insert_card));
		}
	   	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(title);
	   	dialog.setView(viewDialog);
	   	dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface arg0, int arg1) {
					function = 99;
					Message message = handler.obtainMessage(EmvTrans.STATIC_SWIPE_CARD_END,1,0);
					handler.sendMessage(message);
					alertDialogSwipeCard.dismiss();
	            }
	        });

	   	alertDialogSwipeCard = dialog.create();
	   	alertDialogSwipeCard.show();
	   	alertDialogSwipeCard.setCanceledOnTouchOutside(false);
    }




	private AlertDialog alertDialogAppSel;
    private int selAppLabelListIndex = 0x00;

	public int appSelDialog(String[] AppLabelList) {
		final String[] items = AppLabelList;
		mLatch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                	Looper.prepare();
                	AlertDialog.Builder dialog = new AlertDialog.Builder(EmvTestMainActivity.this);
            		dialog.setTitle("Select AID:");
            		dialog.setItems(items, new DialogInterface.OnClickListener() {
            			@Override
            			public void onClick(DialogInterface dialog, int which) {
            				Log.d("Debug","Selected:" + which) ;
            				selAppLabelListIndex = which;
            				mLatch.countDown();
            			}
            		});

            	   	dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
        	            @Override
        	            public void onClick(DialogInterface arg0, int arg1) {
        	            	Log.d("Debug","cancel") ;
                        	selAppLabelListIndex = 	EmvResult.EMV_USER_CANCEL;
                        	mLatch.countDown();
        	            }
        	        });
            		alertDialogAppSel = dialog.create();
            		alertDialogAppSel.show();
            		alertDialogAppSel.setCanceledOnTouchOutside(false);
            		Looper.loop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            mLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		return selAppLabelListIndex;
	}


    private int confirmDialogRet = EmvResult.EMV_OK;
	public int confirmDialog(String title,String info) {
		final String title1 = title;
		final String info1 = info;
		mLatch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                	Looper.prepare();

                	View viewDialog = (View)getLayoutInflater().inflate(
            				R.layout.dialog_info, null);

                	TextView tvInfo = (TextView)viewDialog.findViewById(R.id.textviewInfo);
                	tvInfo.setText(info1);

            	   	AlertDialog.Builder dialog = new AlertDialog.Builder(EmvTestMainActivity.this);
            		dialog.setTitle(title1);
            		dialog.setView(viewDialog);

            	   	dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        	confirmDialogRet = EmvResult.EMV_OK;
                        	mLatch.countDown();
                        }
                    });

            	   	dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
        	            @Override
        	            public void onClick(DialogInterface arg0, int arg1) {
                        	Log.d("Debug","cancel") ;
                        	confirmDialogRet = EmvResult.EMV_USER_CANCEL;
                        	mLatch.countDown();
        	            }
        	        });


            	   	alertDialogInfo = dialog.create();
            	   	alertDialogInfo.show();
            	   	alertDialogInfo.setCanceledOnTouchOutside(false);
            		//Log.d("Debug","transcation time = " + (System.currentTimeMillis()-iStartTime));

            		Looper.loop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            mLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		return confirmDialogRet;
	}

	public void termParamAndKernelInit(Param param)
	{
		Log.d("Debug","EmvTermParamAndKernelInit()");

		// 从XML加载终端参数
		TerminalConfig termConfig = new TerminalConfig();
		Terminal term = termConfig.getConfig();

		EmvTermParam emvTermParam = new EmvTermParam();


//		EmvTermParam.ifd = "12345678";                  				//9F1E 设备序列号,ASC以0x00结束 max:8byte
		try {
			emvTermParam.terminalCountry = term.getTermCountryCode();		//9F1A 国家代码
		} catch (Exception e) {
			// TODO: handle exception
		}

//		EmvTermParam.termType = 0x22;				    				//9F35 终端类型

		if(param.getKernelConfig() == EmvData.KERNAL_CONFIG_PBOC
				|| param.getKernelConfig() == EmvData.KERNAL_CONFIG_QPBOC
				|| param.getKernelConfig() == EmvData.KERNAL_CONFIG_QUICS) {
			//PBOC,qPBOC,qUICS
			emvTermParam.termCapa = "E0E9C8";               				//9F33 终端性能  PBOC
		} else if(param.getKernelConfig() == EmvData.KERNAL_CONFIG_UNIONPAY) {
			//unionPay
			emvTermParam.termCapa = "E0E1C8";               				//9F33 终端性能  直联
		} else if(param.getKernelConfig() == EmvData.KERNAL_CONFIG_EMV
				|| param.getKernelConfig() == EmvData.KERNAL_CONFIG_PAYWARE
				|| param.getKernelConfig() == EmvData.KERNAL_CONFIG_PAYPASS) {
			//EMV,PayWare,PayPass
			emvTermParam.termCapa = "E0F8C8";               				//9F33 终端性能  EMV
		}


		emvTermParam.addTermCapa = "6000F0A001" ;          				//9F40 终端附加性能 支持签名 "6000F02001"->9F40 终端附加性能 不支持签名
		emvTermParam.merchantNameLocation = "SZCPC";					//9F4E 商户名，ASC码以0x00结束 max:40byte
		emvTermParam.merchantCode = "3031";           					//9F15 商户分类号BCD
		emvTermParam.merchantID = "123456789012345";          			//9F16 商户号,应用以0x00结束 max:15byte
		emvTermParam.acquirerID = "1234567890";        					//9F01 收单行BCD
		emvTermParam.termID = "12345678";               				//9F1C 终端标识(终端号),ASC以0x00结束 max:8byte
		emvTermParam.tranRefCurrExp = 0x02;		    				//9F3D 交易参考货币指数
		emvTermParam.tranRefCurr = "0156";								//9F3C 参考货币代码
		emvTermParam.tranCurrExp = 0x02;			    				//5F36 交易货币指数
		emvTermParam.tranCurrCode = "0156";								//5F2A 交易货币代码

		emvTermParam.hostType = (byte)param.getHostType();			//	 =0标准,=1BCTC测试后台
		emvTermParam.termSMSupportIndicator = 0x00;					//   终端是否支持SM算法 =0不支持 =1支持
		emvTermParam.termFLmtFlg = 0x01;    							//	   非接读卡器是否支持终端最低限额检查9F1B (qPBOC一般为1) =0不检查，=1检查
		emvTermParam.rfTxnLmtFlg = 0x01;								//	   非接读卡器是否支持交易限额检查DF20(qPBOC一般为1) =0不检查，=1检查
		emvTermParam.rfFLmtFlg = 0x01;								//	   非接读卡器是否支持脱机最低限额检查DF19(qPBOC一般为1) =0不检查，=1检查
		emvTermParam.rfCVMLmtFlg = 0x01;   							//	   非接读卡器是否支持CVM执行限额检查DF21(qPBOC一般为1) =0不检查，=1检查

		emvTermParam.rfStatusCheckFlg = 0x00;    						// 	   非接读卡器是否支持状态检查(qPBOC一般为0) =1若授权金额为1,则9F66要求联机密文
		emvTermParam.rfZeroAmtNoAllowed = 0x01;       				//	   非接交易0金额检查(qPBOC一般为0) =0不检查，=1检查若授权金额为0,则9F66要求联机密文

		Log.d("Debug","param.getIsPrintDebugInfo()="+param.getIsPrintDebugInfo());
		emvTermParam.printfDebugInfo = (param.getIsPrintDebugInfo() == true)? (byte)1:(byte)0;           //     打印串口调试信息 =0不打印 =1打印

//		EmvTermParam.useFangba = 0x00;                 				//     启用闪卡(防拔)功能 =0不启用 =1启用
		emvTermParam.emvTest = (byte)param.getKernelConfig();                   				//   =0unionPay, =1EMV, =2PBOC, =3qPBOC, =4qUICS,=5PayWare,=6PayPass
		emvTermParam.useCallBackApdu =(byte)param.getApduMode();
		Log.d("Debug","emvTermParam.useCallBackApdu:" + emvTermParam.useCallBackApdu);
		emvTermParam.emvParamFilePath = Environment.getExternalStorageDirectory().getPath()+"/emv/";
		emvHandler.kernelInit(emvTermParam);

		Log.d("sy","AppNum = " + emvHandler.getAppNum());
		Log.d("sy","CapkNum = " + emvHandler.getCapkNum());
		Log.d("sy","RecvoNum = " + emvHandler.getRecvoNum());

		Log.d("sy","AppNum = " + emvHandler.getApp(0).toString());
//		emvHandler.kernelInit(new EmvTermParam());
	}


	public void qTransParamInit(String amountAuth,Param param)
	{
		emvTransParam.setTransKernalType(EmvData.KERNAL_CONTACTLESS_ENTRY_POINT) ; 	// 	   本交易走的内核类型(=0接触式EMV/PBOC, =1非接EMV/PBOC,=2qPBOC)
		emvTransParam.setTerminalSupportIndicator((byte)0x00);					//9F7A 电子现金终端支持指示器,用于EMV/PBOC中 =0借贷记 =1电子现金 ,qPBOC不用设置
		if(param.getKernelConfig() == EmvData.KERNAL_CONFIG_UNIONPAY
				|| param.getKernelConfig() == EmvData.KERNAL_CONFIG_QPBOC) {
			//qPBOC
			emvTransParam.setReaderTTQ("26000080");
		} else if(param.getKernelConfig() == EmvData.KERNAL_CONFIG_QUICS) {
			//qUICS
			emvTransParam.setReaderTTQ("26004080");
		} else if(param.getKernelConfig() == EmvData.KERNAL_CONFIG_PAYWARE) {
			//PayWare
			emvTransParam.setReaderTTQ("26004080");
		}
		emvTransParam.setTransDate(DateUtil.DateToStr(new Date(), "yyMMdd"));
		emvTransParam.setTransTime(DateUtil.DateToStr(new Date(), "HHmmss"));
		emvTransParam.setAmountAuth(amountAuth);
		emvTransParam.setAmountOther("000000000000");
		emvTransParam.setTransNo(ISOUtil.zeropad(String.valueOf(param.getTransNo()),8));
		//Log.d("Debug","emvTransParam.TransNo="+emvTransParam.TransNo);
		param.setTransNo(param.getTransNo()+1);
		emvTransParam.setTransType((byte)0x00);
		emvHandler.transParamInit(emvTransParam);
	}

	private void infoField55() {
		byte[] tlvList = emvHandler.packageTlvList(mIccTags);
		Log.d("TLV","getTLV:" + bytes2HexString(tlvList));
		infoDialog("TLV Result(Field#55)", bytes2HexString(tlvList));
	}
}
