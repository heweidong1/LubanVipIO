jedis实现思路
根据redis的aof 思路实现 利用socket set get 数据
在aof中保存数据的形式为：

  *3  代表一下是3个命令【3组】
  --1组
  $3  代表3个长度
  SET 代表要进行set操作
  --

  --2组
  $6
  taibai 代表要保存这个key
  --
  --3组
  $6
  123456 代表要保存这个value
  --
  例如  一个set 命令 set("1","2")  转换为aof就是：
   *3
   $3
   SET
   $1
   1
   $1
   2
 实现思路 就是讲以上拼接为一个字符串  通过socket发送给redis  就实现了自定义jedis