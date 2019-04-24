package com.hj.utils;

import java.net.InetAddress;
import java.security.SecureRandom;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UUIDGenerator {
	 private static final Log log = LogFactory.getLog(UUIDGenerator.class);
	 private static SecureRandom seederStatic;
	 private static byte[] addr;
	 private static String midValueStatic = null;
	 private String midValue;
	 private SecureRandom seeder;
	 private static long prevMillis = 0L;
	 private static byte[] addrBytes = null;

	  public UUIDGenerator(){
		  this.midValue = null;
		  this.seeder = null;
		  StringBuffer buffer = new StringBuffer(16);
		  buffer.append(midValueStatic);
		  buffer.append(toHex(System.identityHashCode(this), 8));
		  this.midValue = buffer.toString();
		  this.seeder = new SecureRandom();
		  this.seeder.nextInt();
	  }

	  public static String generate(Object obj) {
	    StringBuffer uid = new StringBuffer(32);
	    long currentTimeMillis = System.currentTimeMillis();
	    uid.append(toHex((int)(currentTimeMillis & 0xFFFFFFFF), 8));
	    uid.append(midValueStatic);
	    uid.append(toHex(System.identityHashCode(obj), 8));
	    uid.append(toHex(getRandom(), 8));
	    return uid.toString();
	  }

	  public String generate() {
	    StringBuffer uid = new StringBuffer(32);
	    long currentTimeMillis = System.currentTimeMillis();
	    uid.append(toHex((int)(currentTimeMillis & 0xFFFFFFFF), 8));
	    uid.append(this.midValue);
	    uid.append(toHex(this.seeder.nextInt(), 8));
	    return uid.toString();
	  }

	  private static String toHex(int value, int length) {
	    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	    StringBuffer buffer = new StringBuffer(length);
	    int shift = length - 1 << 2;
	    int i = -1;
	    while (true) { i++; if (i >= length) break;
	      buffer.append(hexDigits[(value >> shift & 0xF)]);
	      value <<= 4;
	    }

	    return buffer.toString();
	  }

	  private static int toInt(byte[] bytes) {
	    int value = 0;
	    int i = -1;
	    while (true) { i++; if (i >= bytes.length) break;
	      value <<= 8;
	      value |= bytes[i];
	    }

	    return value;
	  }

	  private static synchronized int getRandom() {
		  return seederStatic.nextInt();
	  }

	  private static synchronized long getSystemTimeMillis() {
	    long millis = System.currentTimeMillis();
	    if (millis > prevMillis)
	      prevMillis = millis;
	    else {
	      prevMillis += 1L;
	    }
	    return prevMillis;
	  }

	  public static Long getUniqueLong() {
	    long l = getSystemTimeMillis();
	    l *= 1000L;
	    long b1 = addrBytes[3] & 0xFF;
	    l += b1;
	    return Long.valueOf(l);
	  }

	  static
	  {
	    seederStatic = null;
	    addr = null;
	    try {
	       //因anzeen服务器上不行，暂时注释
	      //addr = InetAddress.getLocalHost().getAddress();
	      //addrBytes = InetAddress.getLocalHost().getAddress();
	    	
	      addr = new byte[]{-64,-88,1,105};
	      addrBytes = new byte[]{-64,-88,1,105};
	      StringBuffer buffer = new StringBuffer(8);
	      buffer.append(toHex(toInt(addr), 8));
	      midValueStatic = buffer.toString();
	      seederStatic = new SecureRandom();
	      seederStatic.nextInt();
	    } catch (Exception ex) {
	      log.error("", ex);
	    }
	  }
}
