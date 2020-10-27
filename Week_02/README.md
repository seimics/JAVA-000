# 学习笔记 
一般来说，JDK8及以下版本通过以下参数来开启GC日志：
	‐XX:+PrintGCDetails ‐XX:+PrintGCDateStamps ‐Xloggc:gc.log
如果是在JDK9及以上的版本，则格式略有不同：
	‐Xlog:gc*=info:file=gc.log:time:filecount=0

第 3 课作业实践 

1、使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例。
2、使用压测工具（wrk或sb），演练gateway-server-0.0.1-SNAPSHOT.jar 示例。 
3、(选做)如果自己本地有可以运行的项目，可以按照2的方式进行演练。 
根据上述自己对于1和2的演示，写一段对于不同 GC 的总结，提交到 Github 

第 4 课作业实践
1、（可选）运行课上的例子，以及 Netty 的例子，分析相关现象。
2、写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801，代码提交到Github。


 ---
1、使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例。 
SerialGC
	512m
	java -XX:+UseSerialGC -Xms512m -Xmx512m -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
	执行结束!共生成对象次数:9953
	18次 Young GC，后几次没有减小，几十毫秒
	1次 Full GC，50毫秒
	
	4g
	java -XX:+UseSerialGC -Xms4g -Xmx4g -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
	执行结束!共生成对象次数:12812
	3次 Young GC，100毫秒左右

ParallelGC
	512m
	java -XX:+UseParallelGC -Xms512m -Xmx512m -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
	执行结束!共生成对象次数:9437
	33次 Allocation Failure, Young GC， 10-20毫秒
	10次 Ergonomics, Full GC， 40-50毫秒
	
	4g
	java -XX:+UseParallelGC -Xms4g -Xmx4g -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
	执行结束!共生成对象次数:16130
	3次 Young GC，40毫秒

CMS GC
	512m
	java -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
	执行结束!共生成对象次数:11682
	21次 Young GC，40毫秒， 最后几个GC没有减小
	11次 Full GC
	
	4g
	java -XX:+UseConcMarkSweepGC -Xms4g -Xmx4g -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
	执行结束!共生成对象次数:14263
	6次 Young GC,70毫秒

G1 GC
	512m
	java -XX:+UseG1GC -Xms512m -Xmx512m -Xloggc:gc.demo.log -XX:+PrintGC -XX:+PrintGCDateStamps GCLogAnalysis
	执行结束!共生成对象次数:10748
	(GC pause (G1 Evacuation Pause) (young) 38次 几毫秒
	GC pause ((G1 Humongous Allocation) (young) 31次 几毫秒
	GC pause (G1 Evacuation Pause) (mixed) 30次 几毫秒

	4g
	java -XX:+UseG1GC -Xms4g -Xmx4g -Xloggc:gc.demo.log -XX:+PrintGC -XX:+PrintGCDateStamps GCLogAnalysis
	执行结束!共生成对象次数:17166
	GC pause (G1 Evacuation Pause) (young) 2毫秒  14次
	
2、使用压测工具（wrk或sb），演练gateway-server-0.0.1-SNAPSHOT.jar 示例。
>sb -u http://localhost:8088/api/hello -c 40 -N 30

SerialGC
	RPS: 4144.3 (requests/second)
	Full GC (Metadata GC Threshold) 2次  metadata数据区默认初始大小20501K 
	GC (Allocation Failure) 29次

ParallelGC
	RPS: 4144.3 (requests/second)
	Full GC (Metadata GC Threshold) 2次
	GC (Metadata GC Threshold) 2次
	GC (Allocation Failure) 27次

CMS GC
	RPS: 4122.6 (requests/second)
	GC (Allocation Failure) 29次
	GC (GCLocker Initiated GC) 1次
	CMS 1次
G1 GC
	RPS: 4221.6 (requests/second)
	GC pause (G1 Evacuation Pause) 14次
	GC pause (Metadata GC Threshold) 2次
	full gc 2次
### GC 总结 
SerialGC
	适用于单线程、小内存、低负载的环境，简单高效，但GC时STW时间长。
ParallelGC
	并行的Serial，性能比SerialGC好，但还是存在较长的STW时间。吞吐量大。
CMS
	暂停时间短，响应速度快，缺点无法处理浮动垃圾，会存在空间碎片，CPU占用大
G1
	固定大小的region，响应速度快，可以较好使用多核心，大内存环境。




