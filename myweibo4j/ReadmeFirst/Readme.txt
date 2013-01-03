环境：windows7/java sdk1.6/MySQL 5.2

Weibo数据表设计.docx：数据表设计
Config说明：说明一些配置，我默认已经设置好，可以直接使用
weibo.sql：在MySql中创建Table可以直接导入使用

需要创建两个一样结构的Table，一个储存抓下来的微博信息，
因为可能微博是转发自微博，存在一层嵌套，所以另外一个
repostfrom的Table储存转发的微博内容

所有设置可以在.properties文件中修改，不用修改程序，程序没有经过非常多测试，
可能运行很久后会出现内存爆了的情况，尚未清楚。logs中的日志文件可以作为一定
追踪参考。

Main程序为exec中weibo4j.clawer.MainExecute.java
StorePublicTimelineToMySql.java继承了Runnable，可以实现多线程

官方说明是普通授权每个IP请求限制是10000次/小时
不过以我在家里的网速根本没法达到10000次/小时
我这边运行一次要6s左右，获得200条公共微博，并且请求快有大量的重复。
你可以对间隔时间(reflesh_time)进行调整，来调整下适合你网速的，
有问题随时联系我，我QQ经常隐身