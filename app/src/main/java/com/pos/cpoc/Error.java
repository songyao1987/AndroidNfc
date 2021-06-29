package com.pos.cpoc;

import com.pos.cpoc.emv.EmvResult;

public class Error {
	public static final int BANK_RESP_END_ERROR             = 0x00FF; //最大错误码定义,此值不使用
	public static final int LOGIC_END_ERROR                 = 0x3FFF; //最大逻辑错误码定义,此值不使用
	public static final int PRINT_END_ERROR                 = 0x4FFF; //最大打印错误码定义,此值不使用
	public static final int EMV_END_ERR                     = -100; //EMV交易最小错误码定义,此值不使用
	public static final int TXN_REFUSED_BY_POSCENTER		= 0x1000; //39域返回非成功,错误显示为应答码表
	public static final int NOT_BREAKPOINT                  = 0x5FFF; //不需要处理断点批上送(此值不需要显示错误)
	public static final int POS_HAVE_LOCK                   = 0x6000; //POS已经锁定(此值不需要显示错误)

	public static final int NOT_ONLINE						= -9999;//未联机
//基本逻辑错误
	public static final int EXCEPTION_ERR		 		    = 0x1C00; //程序异常
	
	public static final int MD_STATUS_NOT_SETTEMENT_ERR	    = 0x1C01; //请先做批结算
	public static final int USER_CANCEL_INPUT               = 0x1C02; //用户取消
	public static final int INPUT_ERR                       = 0x1C03; //输入错误
	public static final int INPUT_TIME_OUT                  = 0x1C04; //输入超时
	public static final int UPDATE_WK_ERROR                 = 0x1C06; //更新工作密钥失败
	public static final int UPDATE_BATCH_NO_ERROR           = 0x1C07; //更新批次号失败
	public static final int SALE_VOID_ADD_TIP               = 0x1C08; //原交易已经被撤销,不能追加小费!
	public static final int OLD_TRADE_HAVE_ADD_TIP          = 0x1C09; //原交易已经追加过小费,不能再追加小费!
	public static final int ADD_TIP_ERROR                   = 0x1C0A; //内卡不能追加小费
	public static final int OLD_TRADE_HAVE_ADJUST           = 0x1C0C; //原交易已经做过调整,不能重复调整!
	public static final int CONTACT_ISSUING_BANK_BANK       = 0x1C0E; //请联系发卡行
	public static final int FD_ERR                          = 0x1C0F; //域错误
	public static final int FD_NOT_EXIST                    = 0x1C10; //域不存在
	public static final int UPDATE_RT_ERR                   = 0x1C11; //更新时钟失败
	public static final int NOT_SUPPORT_ADD_TIP             = 0x1C12; //终端不支持追加小费交易
	public static final int NO_PARAM_DOWN                   = 0x1C13; //后台无参数下载
	public static final int SERVICE_RESP_ERR                = 0x1C14; //后台响应出错
	public static final int DOWN_PARAM_FAIL                 = 0x1C15; //下载参数失败
	public static final int DOWN_PARA_FD62_LEN_ERR		    = 0x1C16; //参数下载时应答报文62域长度不合要求
	public static final int ORIGNAL_TXN_NOT_RIGHT		    = 0x1C17; //原交易类型不符合要求
	public static final int ORIGNAL_TXN_NOT_EXIST		    = 0x1C18; //原交易不存在
	public static final int NOT_TRADE_REC                   = 0x1C19; //无交易记录
	public static final int OLD_TRADE_HAVE_VOID             = 0x1C1A; //原交易已经被撤销
	public static final int OLD_TRADE_CANNOT_VOID           = 0x1C1B; //原交易已经追加过小费,不能被撤销
	public static final int TRADE_ID_NOT_RIGHT              = 0x1C1C; //交易ID错误
	public static final int NOT_TRADE_REC_NEED_UPLOAD       = 0x1C1D; //无需要上送的交易
	public static final int NO_TXN_CAN_BE_PRINT             = 0x1C1E; //无可以打印的交易记录
	public static final int NOT_HAVE_CERT_FILE              = 0x1C1F; //终端没有证书
	public static final int OPEN_CERT_FILE_ERR              = 0x1C20; //打开证书文件失败
	public static final int POS_HAVE_NOT_LOGIN              = 0x1C21; //POS未签到,请先签到
	public static final int NOT_HAVE_ENOUGH_FLASH           = 0x1C22; //存储空间不足
	public static final int VOUCHER_FULL                    = 0x1C23; //流水满,请先结算
	public static final int TRADE_TOTAL_NUM_FULL            = 0x1C24; //交易笔数满,请先结算
	public static final int NOT_SUPPORT_CURRENT_TRADE       = 0x1C25; //终端不支持该交易
	public static final int CAN_NOT_REPRINT_REFUSED_REC     = 0x1C28; //交易拒绝的IC卡脱机消费不能重打印
	public static final int NOT_ENOUGH_RECHARGE_AMOUNT      = 0x1C29; //可充金额超出,交易拒绝!
	public static final int NOT_APP_LIST_REC                = 0x1C2A; //无IC卡参数
	public static final int NOT_CAPK_REC                    = 0x1C2B; //无IC卡公钥
	public static final int GET_SYSTEM_FREE_SPACE_ERR       = 0x1C2C; //获取系统剩余存储空间失败
	public static final int NOT_FIND_CAPK                   = 0x1C2D; //没有找到公钥
	public static final int NOT_FIND_AID                    = 0x1C2E; //没有找到IC卡参数
	public static final int INVALID_CARD_NO                 = 0x1C2F; //无效卡号
	public static final int TDK_ENCRYPT_FAILED              = 0x1C30; //磁道加密失败
	public static final int OFFLINE_NUM_FULL                = 0x1C31; //脱机交易笔数超过阀值
	public static final int DELETE_UPFAILED_REC_ERR         = 0x1C33; //删除上送失败交易明细失败
	public static final int OFFLINE_AMOUNT_FULL             = 0x1C34; //脱机交易金额超过阀值
	public static final int NOT_FAILED_OFFLINE_TRADE        = 0x1C35; //无脱机失败明细

//报文响应错误
	public static final int NO_RESP_CODE_ERR			    = 0x2C01; //39域未返回应答码
	public static final int VERIFY_PROCESS_CODE_ERR			= 0x2C02; //交易处理码(3域)不一致
	public static final int VERIFY_RES_FIELD11_ERR			= 0x2C03; //交易流水号(11域)不一致
	public static final int VERIFY_RES_FIELD41_ERR			= 0x2C04; //终端号(41域)不一致
	public static final int VERIFY_RES_FIELD42_ERR			= 0x2C05; //商户号(42域)不一致
	public static final int VERIFY_RES_FIELD04_ERR			= 0x2C06; //交易金额(4域)不一致
	public static final int VERIFY_RES_MAC_ERR				= 0x2C07; //校验MAC错误
	public static final int VERIFY_PINCODE_ERR				= 0x2C08; //校验PINKEY错误
	public static final int VERIFY_MACCODE_ERR				= 0x2C09; //校验MACKEY错误
	public static final int VERIFY_TDKCODE_ERR				= 0x2C0A; //校验TDKKEY错误

//操作员错误
	public static final int OP_NOT_EXIST					= 0x2D01; //操作员不存在
	public static final int OP_PWD_ERR						= 0x2D02; //操作员密码错
	public static final int TWICE_INPUT_PWD_NOT_SAME		= 0x2D03; //两次输入的密码不一致
	public static final int OP_HAVE_EXIST_ERR				= 0x2D04; //操作员已经存在
	public static final int CAN_NOT_DEL_MANAGE_OP			= 0x2D05; //不能删除主管操作员
	public static final int CAN_NOT_DEL_SYSTEM_OP			= 0x2D06; //不能删除系统管理员
	public static final int CAN_NOT_DEL_CURRENT_OP          = 0x2D07; //不能删除当前操作员
	public static final int TWICE_INPUT_NOT_SAME_PD			= 0x2D08; //两次输入的密码不匹配
	public static final int NOT_NORMAL_OPERATOR             = 0x2D09; //请以一般操作员身份登录
	public static final int INVALID_OP_NO                   = 0x2D0A; //无效操作员号

//密码键盘错误
	//public static final int PED_ERR_INPUT_PIN_TIMEOUT		= EMV_TIME_OUT; //交易取消(输入密码超时)
	//public static final int PED_ERR_CANCEL_INPUT            = EMV_USER_CANCEL; //交易取消
	public static final int PED_ERR_LOCK_SELF				= 0x2E02; //密码键盘自锁
	public static final int PED_ERR_TW_INPUT_IN_30SEC		= 0x2E03; //两次输入密码时间必需大于30秒
	public static final int PED_ERR_DUPLI_KEY               = 0x2E04; //密钥重复
	public static final int PED_ERR_KEY_INDEX_INVALD        = 0x2E05; //密钥索引非法
	public static final int PED_ERR_NOT_SET_KEY             = 0x2E06; //没有设置主密钥
	public static final int PED_ERR_UNDEF                   = 0x2E07; //密钥未知错误

//通讯错误
	public static final int NOT_CURRENT_COMM_MODE		    = 0x2F01; //不支持此通讯方式
	public static final int INIT_COMM_ERR                   = 0x2F02; //通讯初始化失败
	public static final int CONNECT_HOST_ERR			    = 0x2F03; //连接主机失败
	public static final int SEND_ERR                        = 0x2F04; //发送数据错误
	public static final int RECV_TIMEOUT				    = 0x2F05; //接收收据超时
	public static final int RECV_ERR					    = 0x2F06; //接收数据错误
	public static final int UNPACK8583_LEN_ERR              = 0x2F07; //解析报文长度出错

//读写数据错误
	public static final int READ_REVERSAL_ERR               = 0x3B00; //读取冲正数据失败
	public static final int SAVE_REVERSAL_ERR               = 0x3B01; //保存冲正数据失败
	public static final int SAVE_TRADE_ERR                  = 0x3B02; //保存交易失败
	public static final int READ_TRADE_ERR                  = 0x3B03; //读取交易失败
	public static final int SAVE_EMV_FILE_ERR               = 0x3B04; //保存EMV文件失败
	public static final int READ_EMV_FILE_ERR               = 0x3B05; //读取EMV文件失败
	public static final int VERIFY_CRC_ERR                  = 0x3B07; //CRC校验错误
	public static final int SAVE_PARAM_ERR                  = 0x3B08; //保存参数失败
	public static final int READ_PARAM_ERR                  = 0x3B09; //读取参数失败
	public static final int SAVE_SCRIPTRESULT_ERR           = 0x3B0A; //保存脚本结果失败
	public static final int READ_SCRIPTRESULT_ERR           = 0x3B0B; //读取脚本结果失败
	public static final int UPDATE_TRADE_ERR                = 0x3B0C; //更新交易数据失败

//打印错误
	public static final int PRN_STATUS_PAPEROUT             = 0x4A01; //打印机缺纸
	public static final int PRN_STATUS_TOOHEAT              = 0x4A02; //打印机温度过高
	public static final int PRN_STATUS_FAULT                = 0x4A03; //打印机设备故障
	public static final int PRINT_FAULT                     = 0x4A04; //打印失败
	


	static public String getErrorMsg(int errorNo)
	{
		String strErrorNo = new String();
		if(errorNo > 0)
		{
			if(errorNo > 0xFF)
			{
				strErrorNo = String.format("%04X ", errorNo);
			}
			else
			{
				strErrorNo = String.format("%d ", errorNo);
			}
		}
		else
		{
			strErrorNo = String.format("%d ", errorNo);
		}
		
		switch(errorNo)
		{
			case EXCEPTION_ERR		 		     : return strErrorNo+"异常错误";

			//基本逻辑错误
			case MD_STATUS_NOT_SETTEMENT_ERR	 : return strErrorNo+"请先做批结算";
			case USER_CANCEL_INPUT               : return strErrorNo+"用户取消";
			case INPUT_ERR                       : return strErrorNo+"输入错误";
			case INPUT_TIME_OUT                  : return strErrorNo+"输入超时";
			case UPDATE_WK_ERROR                 : return strErrorNo+"更新工作密钥失败";
			case UPDATE_BATCH_NO_ERROR           : return strErrorNo+"更新批次号失败";
			case SALE_VOID_ADD_TIP               : return strErrorNo+"原交易已经被撤销,不能追加小费!";
			case OLD_TRADE_HAVE_ADD_TIP          : return strErrorNo+"原交易已经追加过小费,不能再追加小费!";
			case ADD_TIP_ERROR                   : return strErrorNo+"内卡不能追加小费";
			case OLD_TRADE_HAVE_ADJUST           : return strErrorNo+"原交易已经做过调整,不能重复调整!";
			case CONTACT_ISSUING_BANK_BANK       : return strErrorNo+"请联系发卡行";
			case FD_ERR                          : return strErrorNo+"域错误";
			case FD_NOT_EXIST                    : return strErrorNo+"域不存在";
			case UPDATE_RT_ERR                   : return strErrorNo+"更新时钟失败";
			case NOT_SUPPORT_ADD_TIP             : return strErrorNo+"终端不支持追加小费交易";
			case NO_PARAM_DOWN                   : return strErrorNo+"后台无参数下载";
			case SERVICE_RESP_ERR                : return strErrorNo+"后台响应出错";
			case DOWN_PARAM_FAIL                 : return strErrorNo+"下载参数失败";
			case DOWN_PARA_FD62_LEN_ERR		     : return strErrorNo+"参数下载时应答报文62域长度不合要求";
			case ORIGNAL_TXN_NOT_RIGHT		     : return strErrorNo+"原交易类型不符合要求";
			case ORIGNAL_TXN_NOT_EXIST		     : return strErrorNo+"原交易不存在";
			case NOT_TRADE_REC                   : return strErrorNo+"无交易记录";
			case OLD_TRADE_HAVE_VOID             : return strErrorNo+"原交易已经被撤销";
			case OLD_TRADE_CANNOT_VOID           : return strErrorNo+"原交易已经追加过小费,不能被撤销";
			case TRADE_ID_NOT_RIGHT              : return strErrorNo+"交易ID错误";
			case NOT_TRADE_REC_NEED_UPLOAD       : return strErrorNo+"无需要上送的交易";
			case NO_TXN_CAN_BE_PRINT             : return strErrorNo+"无可以打印的交易记录";
			case NOT_HAVE_CERT_FILE              : return strErrorNo+"终端没有证书";
			case OPEN_CERT_FILE_ERR              : return strErrorNo+"打开证书文件失败";
			case POS_HAVE_NOT_LOGIN              : return strErrorNo+"POS未签到,请先签到";
			case NOT_HAVE_ENOUGH_FLASH           : return strErrorNo+"存储空间不足";
			case VOUCHER_FULL                    : return strErrorNo+"流水满,请先结算";
			case TRADE_TOTAL_NUM_FULL            : return strErrorNo+"交易笔数满,请先结算";
			case NOT_SUPPORT_CURRENT_TRADE       : return strErrorNo+"终端不支持该交易";
			case CAN_NOT_REPRINT_REFUSED_REC     : return strErrorNo+"交易拒绝的IC卡脱机消费不能重打印";
			case NOT_ENOUGH_RECHARGE_AMOUNT      : return strErrorNo+"可充金额超出,交易拒绝!";
			case NOT_APP_LIST_REC                : return strErrorNo+"无IC卡参数";
			case NOT_CAPK_REC                    : return strErrorNo+"无IC卡公钥";
			case GET_SYSTEM_FREE_SPACE_ERR       : return strErrorNo+"获取系统剩余存储空间失败";
			case NOT_FIND_CAPK                   : return strErrorNo+"没有找到公钥";
			case NOT_FIND_AID                    : return strErrorNo+"没有找到IC卡参数";
			case INVALID_CARD_NO                 : return strErrorNo+"无效卡号";
			case TDK_ENCRYPT_FAILED              : return strErrorNo+"磁道加密失败";
			case OFFLINE_NUM_FULL                : return strErrorNo+"脱机交易笔数超过阀值";
			case DELETE_UPFAILED_REC_ERR         : return strErrorNo+"删除上送失败交易明细失败";
			case OFFLINE_AMOUNT_FULL             : return strErrorNo+"脱机交易金额超过阀值";
			case NOT_FAILED_OFFLINE_TRADE        : return strErrorNo+"无脱机失败明细";
			
			//通讯错误
			case NOT_CURRENT_COMM_MODE		     : return strErrorNo+"不支持此通讯方式";
			case INIT_COMM_ERR                   : return strErrorNo+"通讯初始化失败";
			case CONNECT_HOST_ERR			     : return strErrorNo+"连接主机失败";
			case SEND_ERR                        : return strErrorNo+"发送数据错误";
			case RECV_TIMEOUT				     : return strErrorNo+"接收收据超时";
			case RECV_ERR					     : return strErrorNo+"接收数据错误";
			case UNPACK8583_LEN_ERR              : return strErrorNo+"解析报文长度出错";

		//读写数据错误
			case READ_REVERSAL_ERR               : return strErrorNo+"读取冲正数据失败";
			case SAVE_REVERSAL_ERR               : return strErrorNo+"保存冲正数据失败";
			case SAVE_TRADE_ERR                  : return strErrorNo+"保存交易失败";
			case READ_TRADE_ERR                  : return strErrorNo+"读取交易失败";
			case SAVE_EMV_FILE_ERR               : return strErrorNo+"保存EMV文件失败";
			case READ_EMV_FILE_ERR               : return strErrorNo+"读取EMV文件失败";
			case VERIFY_CRC_ERR                  : return strErrorNo+"CRC校验错误";
			case SAVE_PARAM_ERR                  : return strErrorNo+"保存参数失败";
			case READ_PARAM_ERR                  : return strErrorNo+"读取参数失败";
			case SAVE_SCRIPTRESULT_ERR           : return strErrorNo+"保存脚本结果失败";
			case READ_SCRIPTRESULT_ERR           : return strErrorNo+"读取脚本结果失败";
			case UPDATE_TRADE_ERR                : return strErrorNo+"更新交易数据失败";	
			
			//IC错误
			case EmvResult.ICC_CMD_ERR                 	: return strErrorNo+"IC卡通讯错误";
			case EmvResult.EMV_PARAM_ERR               	: return strErrorNo+"EMV参数错误";
			case EmvResult.ICC_BLOCK                   	: return strErrorNo+"IC卡锁卡";
			case EmvResult.ICC_RSP_ERR                 	: return strErrorNo+"IC卡响应错误";
			case EmvResult.EMV_APP_BLOCK               	: return strErrorNo+"应用已锁";
			case EmvResult.EMV_NO_APP                  	: return strErrorNo+"卡片没有终端支持的应用";
			case EmvResult.EMV_USER_CANCEL             	: return strErrorNo+"交易取消";
			case EmvResult.EMV_TIME_OUT                	: return strErrorNo+"用户操作超时";
			case EmvResult.EMV_DATA_ERR                	: return strErrorNo+"卡片数据错误";
			case EmvResult.EMV_NOT_ACCEPT              	: return strErrorNo+"交易不接受";
			case EmvResult.EMV_KEY_EXP                 	: return strErrorNo+"认证中心公钥过有效期";
			case EmvResult.EMV_DATETIME_ERR            	: return strErrorNo+"日期时间错误";
			case EmvResult.EMV_FILE_ERR                	: return strErrorNo+"读写EMV文件错误";
			case EmvResult.EMV_SUM_ERR                 	: return strErrorNo+"认证中心公钥校验失败";
			case EmvResult.EMV_NOT_FOUND               	: return strErrorNo+"读EMV文件没有找到相应的记录";
			case EmvResult.EMV_DATA_AUTH_FAIL          	: return strErrorNo+"脱机数据认证失败";
			case EmvResult.EMV_NOT_MATCH               	: return strErrorNo+"两数据不匹配(如2磁的卡号与Tag:5A卡号等)";
			case EmvResult.EMV_NO_TRANS_LOG            	: return strErrorNo+"无交易日志";
			case EmvResult.EMV_ONLINE_FAILED           	: return strErrorNo+"联机不成功,连接主机失败";
			case EmvResult.EMV_NOT_ORG_ICC             	: return strErrorNo+"不是产生闪卡的原始卡";
			case EmvResult.ICC_RSP_6985                	: return strErrorNo+"GAC中卡片回送6985";
			case EmvResult.EMV_EXCP_FILE               	: return strErrorNo+"卡片出现在终端异常文件中";
			case EmvResult.EMV_USE_CONTACT  		    : return strErrorNo+"请使用其他界面进行交易";
			case EmvResult.EMV_CARD_EXPIRED            	: return strErrorNo+"卡片过有效期";
			case EmvResult.EMV_TERMINATE    		    : return strErrorNo+"交易终止";
			case EmvResult.EMV_BALANCE_ERR    		    : return strErrorNo+"获取余额失败";
			case EmvResult.EMV_NOT_PAY    		        : return strErrorNo+"发生闪卡扣款未成功";
			case EmvResult.EMV_ALREADY_PAY    		    : return strErrorNo+"发生闪卡扣款成功 "; 
			case EmvResult.EMV_BALANCE_NOT_ENOUGH    	: return strErrorNo+"余额不足";
			case EmvResult.EMV_DECLINE                 	: return strErrorNo+"交易拒绝(交易结果为拒绝)";
			case EmvResult.EMV_NO_PASSWORD             	: return strErrorNo+"没有密码或未输入密码"; 
			case EmvResult.EMV_FANGBA                  	: return strErrorNo+"发生闪卡";
			case EmvResult.EMV_NO_PINPAD_OR_ERR        	: return strErrorNo+"密码键盘不存在或工作不正常"; 
			case EmvResult.EMV_NOT_QPBOC               	: return strErrorNo+"不支持非接电子现金"; 
			case EmvResult.EMV_NOT_SUPPORT             	: return strErrorNo+"不支持该交易";			
			case EmvResult.ICC_RSP_6986             	: return strErrorNo+"请查验手机";
			case EmvResult.EMV_ONLINE_NORESP_MAC_OR_RECV_ERR  : return strErrorNo+"联机不成功,联机无响应或返回MAC错误或接收数据错误";
			case EmvResult.EMV_NO_ONLINE               	: return strErrorNo+"未联机";
			case EmvResult.EMV_PARSING_ERROR           	: return strErrorNo+"解析卡片数据错误";
			default          					: return strErrorNo+"错误码未知";
		}
	}
}
