package com.hj.wxmp.mobile.common;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
* @author WangZhiYong
* @date 创建时间：2016年4月26日 下午3:14:52
* @version 1.0 
* @parameter  
* @since  
* @return  
*/

public class SerializeUtil <M extends Serializable>{
	
	protected static Logger logger = Logger.getLogger(SerializeUtil.class);
	
	public static byte[] objectSerialize(Object value) throws IOException {  
        if (value == null) {  
            throw new NullPointerException("Can't serialize null");  
        }  
        byte[] rv=null;  
        ByteArrayOutputStream bos = null;  
        ObjectOutputStream os = null;  
        try {  
            bos = new ByteArrayOutputStream();  
            os = new ObjectOutputStream(bos);  
            os.writeObject(value);  
            os.close();  
            bos.close();  
            rv = bos.toByteArray();  
        } catch (IOException e) {  
            throw new IllegalArgumentException("Non-serializable object", e);  
        } finally {  
           os.close();  
           bos.close(); 
        }  
        return rv;  
    }  

    public static Object objectDeserialize(byte[] in) throws IOException {  
        Object rv=null;  
        ByteArrayInputStream bis = null;  
        ObjectInputStream is = null;  
        try {  
            if(in != null) {  
                bis=new ByteArrayInputStream(in);  
                is=new ObjectInputStream(bis);  
                rv=is.readObject();  
                is.close();  
                bis.close();  
            }  
        } catch (IOException e) {  
        	e.printStackTrace();
        	return null;
        } catch (ClassNotFoundException e) {  
        	e.printStackTrace();
          return null;
        } finally {  
        	is.close();
        	bis.close();
        }  
        return rv;  
    }  
    
    
    
    public List<M> listDeserialize(byte[] in) throws IOException {  
        List<M> list = new ArrayList<>();  
        ByteArrayInputStream bis = null;  
        ObjectInputStream is = null;  
        try {  
            if (in != null) {  
                bis = new ByteArrayInputStream(in);  
                is = new ObjectInputStream(bis);  
                while (bis.available() > 0) {  
                                      // while(true) will throw EOFException  
                    @SuppressWarnings("unchecked")
					M m = (M)is.readObject();  
                    if (m == null) {  
                        break;  
                    }  
                      
                    list.add(m);  
                      
                }  
                is.close();  
                bis.close();  
            }  
        } catch (IOException e) {    
        	e.printStackTrace();
            return null;   
        } catch (ClassNotFoundException e) {    
        	e.printStackTrace();
            return null;
        }  finally {  
        	is.close();
        	bis.close();
        }  
          
        return  list;  
    }  
      
  
    @SuppressWarnings("unchecked")  
    public byte[] listSerialize(Object value) throws IOException {  
        if (value == null)  
            throw new NullPointerException("Can't serialize null");  
          
        List<M> values = (List<M>) value;  
          
        byte[] results = null;  
        ByteArrayOutputStream bos = null;  
        ObjectOutputStream os = null;  
          
        try {  
            bos = new ByteArrayOutputStream();  
            os = new ObjectOutputStream(bos);  
            for (M m : values) {  
                os.writeObject(m);  
            }
              
            // os.writeObject(null);  
            os.close();  
            bos.close();  
            results = bos.toByteArray();  
        } catch (IOException e) {  
            throw new IllegalArgumentException("Non-serializable object", e);  
        } finally {  
        	os.close();            
        	bos.close();
        }  
          
        return results;  
    } 
    
    
    
}
