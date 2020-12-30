package com.luban.lubanNio;

import java.util.Random;

public class LubanJedis {

    LubanSocket lubanSocket=new LubanSocket("127.0.0.1",6379);

    public String set(String key, String value){
        lubanSocket.send(commandUtil(Resp.command.SET,key.getBytes(),value.getBytes()));
        return lubanSocket.read();
    }


    public String get(String key){
        lubanSocket.send(commandUtil(Resp.command.GET,key.getBytes()));
        return lubanSocket.read();
    }


    public String incr(String key){
        lubanSocket.send(commandUtil(Resp.command.INCR,key.getBytes()));
        return lubanSocket.read();
    }



    public static String commandUtil(Resp.command command,byte[]... bytes){
        StringBuilder stringBuilder=new StringBuilder();
        //redis协议  resp  在redis  aof持久化文件中保存的也是命令
        //比如 set命令：
        /*
        *  *3  代表一下是3个命令【3组】
        *  --1组
        *  $3  代表3个长度
        *  SET 代表要进行set操作
        *  --
        *
        *  --2组
        *  $6
        *  taibai 代表要保存这个key
        *  --
        *  --3组
        *  $6
        *  123456 代表要保存这个value
        *  --
        *就是说只要是set操作 一定是3组  key value是2组 set命令也是一组
        * get操作是2组  get  key
        *所以1+bytes.length 要加1
        * */
        stringBuilder.append(Resp.star).append(1+bytes.length).append(Resp.line);
        stringBuilder.append(Resp.StringLength).append(command.toString().length()).append(Resp.line);
        stringBuilder.append(command.toString()).append(Resp.line);
        for (byte[] aByte : bytes) {
            stringBuilder.append(Resp.StringLength).append(aByte.length).append(Resp.line);
            stringBuilder.append(new String(aByte)).append(Resp.line);
        }
        return stringBuilder.toString();
    }


    public static void main(String[] args) {
        LubanJedis lubanJedis=new LubanJedis();
        //System.out.println(lubanJedis.set("taibai2", "123456"));
        System.out.println(lubanJedis.get("taibai2"));
        //System.out.println(lubanJedis.set("taibai1", "123456"));
//        System.out.println(lubanJedis.get("taibai"));
        //System.out.println(lubanJedis.incr("lock"));
    }



}
