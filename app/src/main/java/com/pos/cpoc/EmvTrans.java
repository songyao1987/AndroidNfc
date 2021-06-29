package com.pos.cpoc;
public class EmvTrans
{	
	EmvHandler emvHandler = EmvHandler.getInstance();
	//EmvHandler emvHandler = new EmvHandler();
	//交易状态
	public static final int STATIC_TRANS_INIT			= 0; //交易初始化
	public static final int STATIC_EMV_CORE_INIT		= 1; //内核初始化

	public static final int STATIC_ONLINE				= 8;	//联机	
	public static final int STATIC_TRANS_END			= 9; //交易结束
	
	public static final int STATIC_TRANS				= 10; //交易
	public static final int STATIC_BALANCE_QUERY		= 11; //余额查询
	public static final int STATIC_READ_CARD_NO			= 12; //读取银行卡号
	public static final int STATIC_READ_TRANS_LOG		= 13; //读交易日志
	public static final int STATIC_TRANS_AUTO_TEST		= 16; //交易自动测试	
	public static final int STATIC_PRINT				= 19; //打印
	
	
	public static final int STATIC_INPUT_AMOUNT			= 20; //显示输入金额
	public static final int STATIC_SWIPE_CARD			= 21; //显示请挥卡
	public static final int STATIC_INPUT_PIN			= 22;	//显示输入PIN	
	public static final int STATIC_INPUT_AMOUNT_END		= 23;	//输入金额后按了"确定或取消"
	public static final int STATIC_INPUT_PIN_END		= 24;	//输入PIN后按了"确定或取消"
	public static final int STATIC_SWIPE_CARD_END		= 25; //挥卡后按了"确定或取消"
	public static final int STATIC_DETECTED_CARD		= 26;	//检测到卡
	public static final int STATIC_SWIPE_MULTI_CARD		= 27; //显示多卡冲突，请挥卡	
	public static final int STATIC_SWIPE_CARD_CHECK_PHONE		= 28; //请查验手机,重新挥卡		
		
//	public static final String REVOCATIONLIST_NAME         ="RevocationListRec.data";    //证书回收列表文件
//	public static final String AID_NAME                    ="AidRec.data";               //IC卡参数文件
//	public static final String CAPK_NAME                   ="CapkRec.data";              //IC卡公钥文件
//	public static final String BLACKLIST_NAME              ="BlackListRec.data";         //黑名单文件
//	public static final String SCRIPTRESULT_NAME           ="ScriptResult.data";         //脚本文件	
	
	public String getTLVData(int tag){
		return emvHandler.bytesToHexString(emvHandler.getTlvData(tag));
	}	
	
	/**
	 * 授权请求报文
	 */
	public String packAuthorisationRequest() {
		int[] tags = new int[] { 0x82,0x9f36,0x9F26,0x9F27, 0x9F34, 0x9F1E,
				0x9F10, 0x9F33, 0x9F35, 0x95, 0x9F37, 0x9F01,0x9F02,0x9F03,
				0x5F25,0x5F24, 0x5A,0x5F34, 0x9F15, 0x9F16, 0x9F1A, 0x9F1C,0x57,0x5F2A, 0x9A, 0x9F21, 0x9C,0x99,0x9F39,0x9F24,
				0x5F20,0x9F5D,0x9F63};//qPBOC
		//return getIcField(tags);
		return emvHandler.bytesToHexString(emvHandler.packageTlvList(tags));
		
	}	
	
	/**
	 * 金融请求报文
	 */
	public String packRequest() {
		int[] tags = new int[] { 0x82,0x9f36, 0x9F07,0x9F26,0x9F27, 0x8E, 0x9F34, 0x9F1E, 0x9F0D,
				0x9F0E, 0x9F0F, 0x9F10, 0x9F33, 0x9F35, 0x95, 0x9F37, 0x9F01,0x9F02,0x9F03,
				0x5F25,0x5F24, 0x5A,0x5F34,0x5F28, 0x9F15, 0x9F16, 0x9F1A, 0x9F1C,0x57,0x5F2A, 0x9A, 0x9F21, 0x9C,0x99,0x9F39,0x9F24,
				};
		//return getIcField(tags);
		return emvHandler.bytesToHexString(emvHandler.packageTlvList(tags));
	}
	
	/**
	 * 金融确认报文
	 */
	public String packConfirmation() {
		int[] tags = new int[] { 0x9F36,0x9F26,0x9F33, 0x9F35,0x95, 0x9F37, 0x9F01,0x9F02,0x9F03,
				0x5F25,0x5F24, 0x5A,0x9C,0x8A, 0xDF31, 0x71, 0x72, 0x9F1C, 0x9F41, 0x9B, 0x9F39,0x9F24};
		//return getIcField(tags);
		return emvHandler.bytesToHexString(emvHandler.packageTlvList(tags));
	}

	/**
	 * 批上送报文
	 */
	public String packBatch() {
		int[] tags = new int[] { 0x82,0x9f36, 0x9F07,0x9F27, 0x8E, 0x9F34, 0x9F1E, 0x9F0D,
				0x9F0E, 0x9F0F, 0x9F10, 0xDF31,0x9F33, 0x9F35, 0x95, 0x9F26, 0x9F37, 0x9F01,0x9F02,0x9F03,
				0x5F25,0x5F24,0x5A,0x5F34,0x89,0x8A,0x5F28, 0x9F15, 0x9F16, 0x9F1A, 0x9F1C,0x81,0x5F2A, 0x9A, 0x9F21, 0x9C,0x9B,0x9F4C,0x9F74,0x9F39,0x9F24,
				0x57,0x5F20,0x9F5D,0x9F63};//QPBOC
		//return getIcField(tags);
		return emvHandler.bytesToHexString(emvHandler.packageTlvList(tags));
	}	
	
	/**
	 * 冲正报文
	 */
	public String packReversal() {
		int[] tags = new int[] { 0x82,0x9f36, 0x9F1E,0x9F10,0x9F33, 0x9F35, 0x95, 0x9F01, 0x5F24,
				0x5A, 0x5F34, 0x9F15, 0x9F16, 0x9F1A, 0x5F2A, 0x9A, 0x9F21,0x9C,0x57,
				0x8a,0xDF31, 0x71,0x72,0x9F1C, 0x9F41, 0x9B, 
				0x9F39,0x9F24};
		//return getIcField(tags);
		return emvHandler.bytesToHexString(emvHandler.packageTlvList(tags));
	}
	
	/**
	 * 交易结果报文
	 */
	public String packTransResult() {
		int[] tags = new int[] {0xDF31, 0x95, 0x9B};
		//return getIcField(tags);
		return emvHandler.bytesToHexString(emvHandler.packageTlvList(tags));
	}	
		
	/**
	 * 返回指定TAG的IC卡数据域
	 */
	public String getIcField(int[] tags) {
		StringBuffer sb = new StringBuffer();
		for (int i : tags) {
			byte[] res = emvHandler.packageTlvFormKernel(i);
			if (res != null) {
				sb.append(emvHandler.bytesToHexString(res));
			}
		}
		//System.out.println("请求数据域:"+sb.toString());
		return sb.toString();
	}

	public static String getHexString(int i) {
		String tmp = Integer.toHexString(i);
		if (tmp.length() == 1)
			return "0" + tmp;
		else
			return tmp;
	}	
} 