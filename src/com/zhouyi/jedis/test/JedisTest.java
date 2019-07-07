package com.zhouyi.jedis.test;

import com.zhouyi.jedis.util.JedisPoolUtils;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisTest {

    @Test
    public void getTest(){

        // get connection
        Jedis jedis = new Jedis("localhost", 6379);

        // operation
        jedis.set("username", "laji");

        // close connection
        jedis.close();


    }

    @Test
    public void stringTest(){
        // get connection
        Jedis jedis = new Jedis();  // default url is localhost, protocal 6379

        // operation
        String username = jedis.get("username");

        System.out.println(username);


        // can add an expires header to the key using setex
        jedis.setex("activecode", 20, "hehe");

        // close connection
        jedis.close();
    }

    @Test
    public void hashTest(){

        Jedis jedis = new Jedis();

        jedis.hset("user", "name", "lisi");
        jedis.hset("user", "age", "32");
        jedis.hset("user", "gender", "male");

        String name = jedis.hget("user", "name");
        System.out.println(name);

        Map<String, String> user = jedis.hgetAll("user");
        Set<String> keySet = user.keySet();
        for(String key : keySet){
            String value = user.get(key);
            System.out.println(key + ": " + value);
        }

    }

    @Test
    public void listTest(){

        Jedis jedis = new Jedis();

        jedis.lpush("city", "shanghai", "yunnan", "beijing");
        jedis.rpush("city", "wuhan", "newyork", "nanjing");

        List<String> mylist = jedis.lrange("city", 0 , 5);
        System.out.println(mylist);

        System.out.println(jedis.lpop("city"));
        System.out.println(mylist);

    }

    @Test
    public void setTest(){

        Jedis jedis = new Jedis();

        jedis.sadd("language", "java", "c++","php");

        Set<String> mset = jedis.smembers("language");

        System.out.println(mset);

    }

    @Test
    public void sortedSetTest(){
        Jedis jedis = new Jedis();

        jedis.zadd("rank", 5 , "yase");
        jedis.zadd("rank", 7 , "houzi");
        jedis.zadd("rank", 11 , "houyi");

        System.out.println(jedis.zrange("rank", 0 , -1));

    }

    @Test
    public void jedisPoolTest(){

        // create pool config

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(50);
        config.setMaxIdle(10);


        // create JedisPool

        JedisPool jedisPool = new JedisPool(config, "localhost", 6379);

        // get connection

        Jedis jedis = jedisPool.getResource();

        // use

        jedis.set("hehe", "haha");

        // close, go back to the pool

        jedis.close();
    }

    @Test
    public void utilTest(){

        // use JedisPoolUtils

        Jedis jedis = JedisPoolUtils.getJedis();

        jedis.set("hello", "world");

        jedis.close();
    }
}
