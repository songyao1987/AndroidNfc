package com.pos.cpoc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import android.content.Context;
import android.os.Environment;

/**
 * @author ChengLuo1
 *
 */
public class SdkFile {
    /** 
     * 获取内置SD卡路径 
     * @return 
     */	
	public String sdkGetInnerSDCardPath() {   
	    return Environment.getExternalStorageDirectory().getPath();       
	} 
    
    /**
     * 复制asset文件到指定目录
      * @param oldPath  asset下的路径
      * @param newPath  SD卡下保存路径
      */
    public void copyAssets(Context context, String oldPath, String newPath) {
		try {
			String fileNames[] = context.getAssets().list(oldPath);// 获取assets目录下的所有文件及目录名
			if (fileNames.length > 0) {// 如果是目录
				File file = new File(newPath);
				file.mkdirs();// 如果文件夹不存在，则递归
				for (String fileName : fileNames) {
				    copyAssets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
				    }
			} else {// 如果是文件
				InputStream is = context.getAssets().open(oldPath);
				FileOutputStream fos = new FileOutputStream(new File(newPath));
				byte[] buffer = new byte[1024];
			    int byteCount = 0;
			    while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
				                            // buffer字节
			    	fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
				}
				
				fos.flush();// 刷新缓冲区
				is.close();
				fos.close();
			}
		} catch (Exception e) {
		    e.printStackTrace();
		}
     }
    
    /***
     * 
     * 
     * 
     */
    public boolean dirIsEmpty(String dirs){
    	try {
        	File file = new File(dirs);
        	if (file.exists() && file.isDirectory()) {
        	    if(file.list().length > 0) {
        	        //Not empty, do something here.
        	    	return false;
        	    }

        	}

        	return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}
    
    /**
     * 创建多级目录
     * @return
     */
    public int mkdirs(String dirs)
    {
		File myFile = new File(dirs);   
		
		try {
			if(!myFile.exists()){
				myFile.mkdirs();//测试下这个能否创建多级目录结构
			}
		    return 0;
		}   
		catch (Exception e) {   		   
		    return -1;   
		}
        
    }
    
	public int sdkFileIsExists(String fileName){
		File myFile = new File(fileName);   
		
		try {
			if(myFile.exists()){
				return 1;
			}
		    return 0;
		}   
		catch (Exception e) {   		   
		    return -1;   
		}
	}	
	
	public int sdkFileDel(String fileName){
		File myFile = new File(fileName);   
		try {
			if(myFile.exists()){
				myFile.delete();
			}
		    return 0;
		}   
		catch (Exception e) {     
		    return -1;   
		}
	}

    public int sdkFileGetSize(String fileName){
        RandomAccessFile randomFile = null;
        try {
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            int FileSize = (int) randomFile.length(); // 追加到文件结尾              
            return FileSize;
        } catch (IOException e) {
            return -1;
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }	
	
    public int sdkFileRead(String fileName,byte[] OutData,int Start,int Len){
        RandomAccessFile randomFile = null;
        try {
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            randomFile.seek(Start);  //指向源文件的开始位置  
            return randomFile.read(OutData, 0, Len);  //把文件内容读入缓冲区  
        } catch (IOException e) {
            return -1;
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }
    
    
    public int sdkFileWrite(String fileName,byte[] InData,int Start,int Len){
        RandomAccessFile randomFile = null;
        try {
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "rw");
            randomFile.seek(Start);  //指向源文件的开始位置  
            randomFile.write(InData, 0, Len);  //把文件内容读入缓冲区  
            return 0;
        } catch (IOException e) {
            return -1;
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }    
    
    public int sdkFileAppend(String fileName,byte[] InData,int Len){
        RandomAccessFile randomFile = null;
        try {
            // 打开一个随机访问文件流
            randomFile = new RandomAccessFile(fileName, "rw");
            int End = (int) randomFile.length(); // 追加到文件结尾  
            randomFile.seek(End);  //指向源文件的开始位置  
            randomFile.write(InData, 0, Len);  //把文件内容读入缓冲区  
            return 0;
        } catch (IOException e) {
            return -1;
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }    
    
    
    /**
     * 读取源文件内容
     * @param filename String 文件路径
     * @throws IOException
     * @return byte[] 文件内容
     */
  public static byte[] readFile(String filename) throws IOException {

      File file =new File(filename);
      if(filename==null || filename.equals(""))
      {
        throw new NullPointerException("无效的文件路径");
      }
      long len = file.length();
      byte[] bytes = new byte[(int)len];

      BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
      int r = bufferedInputStream.read( bytes );
      if (r != len)
        throw new IOException("读取文件不正确");
      bufferedInputStream.close();

      return bytes;

  }

  /**
     * 将数据写入文件
     * @param data byte[]
     * @throws IOException
     */
  public static void writeFile(byte[] data,String filename) throws IOException {
      File file =new File(filename);
      file.getParentFile().mkdirs();
      BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(file));
      bufferedOutputStream.write(data);
      bufferedOutputStream.close();

  }

  /**
     * 从jar文件里读取class
     * @param filename String
     * @throws IOException
     * @return byte[]
     */
  public byte[] readFileJar(String filename) throws IOException {
      BufferedInputStream bufferedInputStream=new BufferedInputStream(getClass().getResource(filename).openStream());
      int len=bufferedInputStream.available();
      byte[] bytes=new byte[len];
      int r=bufferedInputStream.read(bytes);
      if(len!=r)
      {
        bytes=null;
        throw new IOException("读取文件不正确");
      }
      bufferedInputStream.close();
      return bytes;
  }

  /**
     * 读取网络流，为了防止中文的问题，在读取过程中没有进行编码转换，而且采取了动态的byte[]的方式获得所有的byte返回
     * @param bufferedInputStream BufferedInputStream
     * @throws IOException
     * @return byte[]
     */
  public byte[] readUrlStream(BufferedInputStream bufferedInputStream) throws IOException {
      byte[] bytes = new byte[100];
      byte[] bytecount=null;
      int n=0;
      int ilength=0;
      while((n=bufferedInputStream.read(bytes))>=0)
      {
        if(bytecount!=null)
          ilength=bytecount.length;
        byte[] tempbyte=new byte[ilength+n];
        if(bytecount!=null)
        {
          System.arraycopy(bytecount,0,tempbyte,0,ilength);
        }

        System.arraycopy(bytes,0,tempbyte,ilength,n);
        bytecount=tempbyte;

        if(n<bytes.length)
          break;
      }
      return bytecount;
  }
    
}

