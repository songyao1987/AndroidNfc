package com.pos.cpoc.utils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Class that provide a wide set of methods
 *
 * @author xmen.lin@gmail.com
 *
 */
public final class MessageDigestUtil {
    public static int TDES_ECB = 1;
    public static int TDES_CBC = 2;
    private static final String MD5 = "MD5";
    private final static String DES = "DES";

//	private static final char[] HEXADECIMAL = { '0', '1', '2', '3', '4', '5',
//			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * Convert a plain text string to MD5 string
     *
     * @param data
     *            the specified string
     * @return String
     * @throws NoSuchAlgorithmException
     */
    public static String encodeMD5(String data) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(MD5);
        return bytesToHexString(md5.digest(data.getBytes()));
    }

    public static String encodeSHA1(String data) throws NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] en = sha1.digest(hexStringToBytes(data));
        return bytesToHexString(en);
    }

    /**
     * Convert a plain text string to DES string
     *
     * @param data
     *            the specified string
     * @param key
     *            the specified DES key，the key length must be multiple of 8
     * @return String
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public final static String encodeDES(String data, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
        //return bytesToHexString(encodeDES(data.getBytes(), key.getBytes()));
        byte[] en = encodeDES(hexStringToBytes(data),
                hexStringToBytes(key));
        return bytesToHexString(en).substring(0,16);
    }

    /**
     * Convert a plain text bytes to DES bytes
     *
     * @param data
     *            the specified bytes
     * @param key
     *            the specified DES key，the key length must be multiple of 8
     * @return String
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] encodeDES(byte[] data, byte[] key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }

    /**
     * Convert a DES string to plain text string
     *
     * @param data
     *            the specified bytes
     * @param key
     *            the specified DES key，the key length must be multiple of 8
     * @return String
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public final static String decodeDES(String data, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
        return new String(decodeDES(hexStringToBytes(data), key.getBytes()));
    }

    /**
     * Convert a DES bytes to plain text bytes
     *
     * @param data
     *            the specified bytes
     * @param key
     *            the specified DES key，the key length must be multiple of 8
     * @return byte[]
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws Exception
     */
    public static byte[] decodeDES(byte[] data, byte[] key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }

    public static String encodeTripleDES(String data, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeySpecException {
        return encodeTripleDES(TDES_ECB, data, key);
    }

    public static byte[] encodeTripleDES(byte[] data, byte[] key)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeySpecException {
        return encodeTripleDES(TDES_ECB, data, key);
    }

    public static String encodeTripleDES(int type, String data, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeySpecException {
        return bytesToHexString(encodeTripleDES(type, hexStringToBytes(data),
                hexStringToBytes(key)));
    }

    public static byte[] encodeTripleDES(int type, byte[] data, byte[] key)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeySpecException {
        byte[] keys;
        if (key.length == 16) {
            keys = new byte[24];
            System.arraycopy(key, 0, keys, 0, 16);
            System.arraycopy(key, 0, keys, 16, 8);
        } else {
            keys = key;
        }

//		SecretKey secretKey = new SecretKeySpec(keys, "DESede");
        DESedeKeySpec spec = new DESedeKeySpec(keys);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        Key secretKey = factory.generateSecret(spec);
        Cipher cipher = null;
        if (type == TDES_ECB) {
            cipher = Cipher.getInstance("DESede/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } else if (type == TDES_CBC) {
            cipher = Cipher.getInstance("DESede/CBC/NoPadding");
            IvParameterSpec iv = new IvParameterSpec(new byte[] { (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00 });
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        }
        return cipher.doFinal(data);
    }

    public static String decodeTripleDES(String data, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeySpecException {
        return decodeTripleDES(TDES_ECB, data, key);
    }

    public static byte[] decodeTripleDES(byte[] data, byte[] key)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeySpecException {
        return decodeTripleDES(TDES_ECB, data, key);
    }

    public static String decodeTripleDES(int type, String data, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeySpecException {
        return bytesToHexString(decodeTripleDES(type, hexStringToBytes(data),
                hexStringToBytes(key)));
    }

    public static byte[] decodeTripleDES(int type, byte[] data, byte[] key)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeySpecException {
        byte[] keys;
        if (key.length == 16) {
            keys = new byte[24];
            System.arraycopy(key, 0, keys, 0, 16);
            System.arraycopy(key, 0, keys, 16, 8);
        } else {
            keys = key;
        }

//		SecretKey secretKey = new SecretKeySpec(keys, "DESede");
        DESedeKeySpec spec = new DESedeKeySpec(keys);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        Key secretKey = factory.generateSecret(spec);
        Cipher cipher = null;
        if (type == TDES_ECB) {
            cipher = Cipher.getInstance("DESede/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } else if (type == TDES_CBC) {
            cipher = Cipher.getInstance("DESede/CBC/NoPadding");
            IvParameterSpec iv = new IvParameterSpec(new byte[] { (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00 });
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        }
        return cipher.doFinal(data);
    }

    /**
     * Generate the 32 digits and random BDK hex string
     *
     * @return String
     */
    public static String encodeBDK() {
        StringBuffer sbf = new StringBuffer();
        sbf.append(RandomUtil.generateString(32, RandomUtil.CHAR_HEX));
        return sbf.toString();
    }

    /**
     * Generate the 32 digits and random BDK hex string
     *
     * @param vid
     *            the specified vendor id, 4 digits, between 0001-FFFF
     * @param pid
     *            the specified product id, 4 digits, between 0001-FFFF
     * @param did
     *            the specified device id, more than 4 digits, less than or
     *            equal to 14 digits<br/>
     *            if 5 digits, between 00001-FFFFF<br/>
     *            if 9 digits, between 000000001-FFFFFFFFF<br/>
     * @return String
     */
    public static String encodeBDK(String vid, String pid, String did) {
        checkArguments(vid, pid, did);
        StringBuffer sbf = new StringBuffer();
        sbf.append(RandomUtil.generateString(32, RandomUtil.CHAR_HEX));
        return sbf.toString();
    }

    /**
     * Generate the 20 digits and random KSN hex string. KSN format:<br/>
     * 11223344556677000000<br/>
     * (1)0~13: generate random hex string, length is 14 digits, e.g.:
     * 11223344556677<br/>
     * (2)14: fixed value, it is "0";<br/>
     * (3)15~19: transaction number, length is 5 digits, value is "00000"<br/>
     *
     * @return String
     */
    public static String encodeKSN() {
        // 生成6位16进制的初始密码
        StringBuffer sbf = new StringBuffer();
        sbf.append(RandomUtil.generateString(14, RandomUtil.CHAR_HEX));
        sbf.append("0");
        // 五位交易标识，默认是为00000，当刷卡交易时从00001开始计算
        sbf.append("00000");
        return sbf.toString();
    }

    /**
     * Generate the 20 digits and random KSN hex string. KSN format:<br/>
     * (1)0~n: generate random hex string.the length is (14 - did length)<br/>
     * (2)n+1~13: device id<br/>
     * (3)14: fixed value, it is "0";<br/>
     * (4)15~19: transaction number, length is 5 digits, value is "00000"<br/>
     *
     * @param vid
     *            the specified vendor id, 4 digits, between 0001-FFFF
     * @param pid
     *            the specified product id, 4 digits, between 0001-FFFF
     * @param did
     *            the specified device id, more than 4 digits, less than or
     *            equal to 14 digits<br/>
     *            if 5 digits, between 00001-FFFFF<br/>
     *            if 9 digits, between 000000001-FFFFFFFFF<br/>
     * @return String
     */
    public static String encodeKSN(String vid, String pid, String did) {
        checkArguments(vid, pid, did);
        // 生成6位16进制的初始密码
        StringBuffer sbf = new StringBuffer();
        sbf.append(RandomUtil.generateString(14 - did.length(),
                RandomUtil.CHAR_HEX));
        sbf.append(did);
        sbf.append("0");
        // 五位交易标识，默认是为00000，当刷卡交易时从00001开始计算
        sbf.append("00000");
        return sbf.toString();
    }

    private static void checkArguments(String vid, String pid, String did) {
        if (vid == null || vid.length() != 4)
            throw new IllegalArgumentException(
                    "The vendor id lenght must be 4 digits");
        if (pid == null || pid.length() != 4)
            throw new IllegalArgumentException(
                    "The product id lenght must be 4 digits");
        if (did == null || did.length() < 5 || did.length() > 14)
            throw new IllegalArgumentException(
                    "The device id lenght must be more than 4 digits and less than or equal to 14 digits");
    }

    private static String encodePAN(String pan) {
        // PAN: 主帐号 Primary Account Number
        // 2Byte(\0x00\0x00) + 5Byte(取主帐号的右12位，不包括最右边1位校验位，不足12位左边补0)
        int len = pan.length();
        StringBuffer sbf = new StringBuffer();
        sbf.append("0000");
        if (len < 12 + 1) {
            for (int i = 0; i < 12 + 1 - len; i++)
                sbf.append("0");
            sbf.append(pan.substring(0, len - 1));
        } else {
            sbf.append(pan.substring(len - 1 - 12, len - 1));
        }
        return sbf.toString();
    }

    private static String encodePinBlock(String pin) {
        // 1Byte(Pin的长度) + 7Byte(6～12位Pin，不足14位右边补F)
        int len = pin.length();
        if (len < 4 || len > 12)
            throw new IllegalArgumentException("Pin length must be 4~12 digits");
        StringBuffer sbf = new StringBuffer();
        String tmp = Integer.toHexString(len);
        if (tmp.length() == 1)
            sbf.append("0").append(tmp);
        else
            sbf.append(tmp);
        sbf.append(pin);
        len = sbf.length();
        for (int i = 0; i < 16 - len; i++)
            sbf.append("F");
        return sbf.toString();
    }

    private static String decodePinBlock(String pinBlock) {
        // 1Byte(Pin的长度) + 7Byte(6～12位Pin，不足14位右边补F)
        int len = pinBlock.length();
        if (len != 16)
            throw new IllegalArgumentException("Pin block length must be 16 digits");
        len = Integer.parseInt(pinBlock.substring(0, 2), 16);
        return pinBlock.substring(2, 2 + len);
    }

    private static String encodePinBlock(String pin, String pan) {
        String pinBlock = encodePinBlock(pin);
        System.out.println("pin="+pin+",pinBlock="+pinBlock);
        String anb = encodePAN(pan);
        System.out.println("pan="+pan+",anb="+anb);
        byte[] pins = hexStringToBytes(pinBlock);
        byte[] pans = hexStringToBytes(anb);
        for (int i = 0; i < 8; i++)
            pins[i] ^= pans[i];
        return bytesToHexString(pins);
    }

    private static String decodePinBlock(String encrypted, String pan) {
        String anb = encodePAN(pan);
        byte[] pins = hexStringToBytes(encrypted);
        byte[] pans = hexStringToBytes(anb);
        for (int i = 0; i < 8; i++)
            pins[i] ^= pans[i];
        return bytesToHexString(pins);
    }

    public static String encodePin(String pin, String pan, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeySpecException {
        String clearPinBlock = encodePinBlock(pin, pan);
        System.out.println("PinBlock="+clearPinBlock);
        if(key.length() == 16*2)
        {
            System.out.println("3DES");
            return encodeTripleDES(TDES_CBC, clearPinBlock, key);
        }
        else
        {
            System.out.println("DES");
            return encodeDES(clearPinBlock, key);
        }
    }

    public static String decodePin(String encrypted, String pan, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeySpecException {
        String pinBlock = decodeTripleDES(TDES_CBC, encrypted, key);
        pinBlock = decodePinBlock(pinBlock, pan);
        return decodePinBlock(pinBlock);
    }

//	private static String byteToHex(byte[] input) {
//		char[] buffer = new char[input.length];
//		for (int i = 0; i < input.length / 2; i++) {
//			int low = (int) (input[i] & 0x0f);
//			int high = (int) ((input[i] & 0xf0) >> 4);
//			buffer[i * 2] = HEXADECIMAL[high];
//			buffer[i * 2 + 1] = HEXADECIMAL[low];
//		}
//		return new String(buffer);
//	}

//	private static byte[] hexToByte(byte[] b) {
//		byte[] b2 = new byte[b.length / 2];
//		for (int n = 0; n < b.length; n += 2) {
//			String item = new String(b, n, 2);
//			b2[n / 2] = (byte) Integer.parseInt(item, 16);
//		}
//		return b2;
//	}

//	private static byte[] hexStrToByte(String data) {
//		int length = data.length();
//		byte[] tmp = new byte[length / 2];
//		for (int i = 0, idx = 0; i < length; i = i + 2, idx++) {
//			String str = data.substring(i, i + 2);
//			tmp[idx] = (byte) (Integer.parseInt(str, 16));
//		}
//		return tmp;
//	}

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toLowerCase();
    }

    public static byte[] hexStringToBytes(String hexString) {
        hexString = hexString.toLowerCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789abcdef".indexOf(c);
    }

    public static void main(String[] args) {
        try {
            System.out.println(encodePinBlock("123456"));
            System.out.println(encodePinBlock("1234"));
            System.out.println(encodePinBlock("123456789012"));
            System.out.println(encodePAN("1234"));
            System.out.println(encodePAN("12345678901"));
            System.out.println(encodePAN("123456789012"));
            System.out.println(encodePAN("1234567890123"));
            System.out.println(encodePAN("123456789012345678"));
            System.out.println(encodePinBlock("123456", "123456789012345678"));
            System.out.println(encodePin("123456", "123456789012345678", "91C0B125438600B05FD5B41A5BA3D17F"));
            System.out.println(decodePin("0aa8d7750765919e", "123456789012345678", "91C0B125438600B05FD5B41A5BA3D17F"));
            System.out.println(encodePin("3901", "0000209855649354f", "34D818B6EE13A2F548665E68948DA088"));
            System.out.println(decodePin("388A9705393E8F7D", "0000209855649354f", "EFF5C758A589AD08E292BD316BC48FE7"));
//			System.out.println(encodeDES("abcddd", "aaaaaaaabbbbbbbbccccccccdddddddd"));
//			System.out.println(decodeDES("86d990c9184772fb", "aaaaaaaabbbbbbbbccccccccdddddddd"));
//			System.out.println(encodeTripleDES(MessageDigestUtils.TDES_ECB, "f45c3ee2666d50a8", "aaaaaaaabbbbbbbbccccccccdddddddd"));
//			System.out.println(decodeTripleDES(MessageDigestUtils.TDES_ECB, "e94b4e62ebcc1919", "aaaaaaaabbbbbbbbccccccccdddddddd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
