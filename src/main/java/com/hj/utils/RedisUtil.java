package com.hj.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
* @author WangZhiYong
* @date 创建时间：2016年4月27日 上午9:31:39
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class RedisUtil {
private static JedisPool jedisPool = null;
    
    static {
        try {
        	if (jedisPool == null) {
                JedisPoolConfig config = new JedisPoolConfig();
                //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
                //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
                config.setMaxActive(500);
                //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
                config.setMaxIdle(5);
                //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
                config.setMaxWait(1000 * 100);
                //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
                config.setTestOnBorrow(true);
                jedisPool = new JedisPool(config, "127.0.0.1", 6379);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 
    
    /**
     * 获取数据
     * @param key
     * @return
     */
    public static String get(String key) {
 
        String value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
 
        return value;
    }
 
    public static void close(Jedis jedis) {
        try {
            jedisPool.returnResource(jedis);
 
        } catch (Exception e) {
            if (jedis.isConnected()) {
                jedis.quit();
                jedis.disconnect();
            }
        }
    }
 
    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
    public static byte[] get(byte[] key) {
 
        byte[] value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
 
        return value;
    }
 
    public static void set(byte[] key, byte[] value) {
 
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
    }
 
    public static void set(byte[] key, byte[] value, int time) {
 
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            jedis.expire(key, time);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            close(jedis);
        }
    }
}
