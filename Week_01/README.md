学习笔记
# JVM核心技术--基础知识 
# JVM基础知识 
# Java字节码技术 
	字节码由单字节Byte的指令构成，理论上最多支持256个操作码（opcode），实际只用了200个左右，剩下的保留给调试操作。
	根据指令的性质，分为4类：
		1.栈操作指令
		2.程序流程操作指令
		3.对象操作指令，包括方法调用指令
		4.算术运算以及类型转换指令
	
		javac -d . *.java
		javac -g 加本地变量表
		javap -c -verbose
		
		方法调用的指令
		invokestatic
		invodespecial
		invokevirtual
		invokeinterface
		invokedynamic
		
# JVM类加载器 
	类的生命周期
		1.加载：找Class文件
		2.验证：验证格式、依赖
		3.准备：静态字段、方法表
		4.解析：符号解析为引用
		5.初始化：构造器、静态变量赋值、静态代码块
		6.使用
		7.卸载
	类的加载时机
		1.当虚拟机启动，初始化用户指定的主类，就是启动执行的main方法所在的类
		2.当遇到用以新建目标实例的new指令时，初始化new指令的目标类（就是new一个类的时候要初始化）。
		3.当遇到调用静态方法的指令时，初始化该静态方法所在的类。
		4.当遇到访问静态字段的指令时，初始化该静态字段所在的类。
		5.子类的初始化会触发父类的初始化。
		6.如果一个接口定义了default方法，那么直接实现或者间接实现该接口的类的初始化，会触发该接口的初始化。
		7.使用反射API对某个类进行反射调用时，初始化这个类。反射调用要么是已经有实例了，要么是静态方法，都需要初始化。
		8.当初次调用MethodHandle实例时，初始化该MethodHandle指向的方法所在的类。
	不会初始化，可能会加载
		1.通过子类引用父类的静态字段，只会出发父类的初始化，而不会出发子类的初始化。
		2.定义对象数组，不会触发该类的初始化。
		3.常量在编译期间会存入调用类的常量池中，本质上并没有直接引用定义常量类的量，不会触发定义常量所在的类。
		4.通过类名获取Class对象，不会触发类的初始化，Hello.class不会让Hello类初始化。
		5.通过Class.forName加载指定类时，如果指定参数initialize为false时，也不会触发类初始化，其实这个参数时告诉虚拟机，是否要对类进行初始化。
		6.通过ClassLoader默认的loadClass方法，也不会触发初始化动作。
	三类加载器
		启动类加载器 BootstrapClassLoader 加载系统类
		扩展类加载器 ExtClassLoader 加载类路径的类
		应用类加载器 AppClassLoader 
		特点：
			双亲委托
			负责依赖
			缓存加载
	添加引用类的几种方式
		1.放到JDK的lib/ext下，或者-Djava.ext.dirs
		2.java -cp/classpath 或者 class文件放到当前路径
		3.自定义ClassLoader加载
		4.拿到当前执行类的ClassLoader，反射调用addUrl方法添加Jar或路径（JDK9前），Class.forName(),第三个参数是classLoader（JDK9后）
	
# JVM内存模型 
	所有原生类型的局部变量都存储在线程栈中。
	线程只能将一个原生局部变量值的副本传给另一个线程。
	堆内存包含了Java代码中的所创建的所有对象，其中也涵盖了包装类型。
	不管是创建一个对象并将其赋值给局部变量，还是赋值给另一个对象的成员变量，创建的对象都会被保存在堆内存中。
	对象引用在线程栈中的局部变量槽位中保存着对象的引用地址，而实际的对象内容保存在堆中。
	对象的成员变量与对象本体一起存储在堆上，不管成员变量的类型是原生数值还是对象引用。
	类的静态变量和类定义一样保存在堆中。
	总结：
		方法中使用的原生数据类型和对象引用地址在栈上存储；对象、对象成员与类定义、静态变量在堆上。
		堆内存又称为”共享堆“，堆中的所有对象，可以被所有线程访问，只要它们能拿到对象的引用地址。
		如果一个线程可以访问某个对象时，也就可以访问该对象的成员变量。
		如果两个线程同时调用某个对象的同一方法，则它们都可以访问到这个对象的成员变量，但每个线程的局部变量副本是独立的。
	内存整体结构
	每启动一个进程，JVM就会在栈空间分配对应的线程栈，也叫Java方法栈。
	JNI方法分配单独的本地方法栈。
	线程执行过程中，一般会有多个方法组成调用栈（Stack Trace），比如A调用B，B调用C。。。每执行到一个方法，就会创建对应的 栈帧（Frame）。
	栈帧是一个逻辑上概念。具体的大小在一个方法编写完后基本就能确定。
	堆内存 分为年轻代和老年代1:2
	年轻代分为Eden新生代和和s0、s1存活区（from区、to区）8：1：1
	非堆 一般不归GC管
		Metaspace元数据区（永久代）
		CCS（Compressed Class Space） 存放class信息
		Code Cache,存放JIT编译后的本地机器码。
	
# JVM启动参数 
	-开头的是标准参数
	-D设置系统属性
	-X 非标准参数 java -X 显示所有支持的非标准参数
	-XX 非稳定参数  -XX:+-Flags    -XX:key=value
	
	1. 系统属性参数  -Dfile.encoding=UTF-8
	2. 运行模式参数  -server   -Xmixed
	3. 堆内存设置参数  -Xmx  -Xms  -Xmn -XX:MaxMetaspaceSize=size  -XX:MaxDirectMemorySize=size  -Xss
	4. GC设置参数  -XX:+UseG1GC   -xx:+UseConcMarkSweepGC   -XX:+UseSerialGC  -XX:+UseParallelGC   -XX:+UnlockExperimentalVMOptions -XX:+UseZGC    -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC
	5. 分析诊断参数  -XX:+HeapDumpOnOutOfMemoryError -Xmx256m ConsumeHeap
	6. JavaAgent参数 -agentlib:libname[=options]
	
# JVM命令行工具
jps/jinfo 查看 java 进程      jps -mlv
jstat 查看 JVM 内部 gc 相关信息 jstat -gcutil pid 1000 1000    jstat -gc pid 1000 1000
jmap 查看 heap 或类占用空间统计 jmap -heap pid    jmap -histo pid   jmap -dump:format=b,file=3826.hprof
3826
jstack 查看线程信息
jcmd 执行 JVM 相关分析命令（整合命令）
jrunscript/jjs 执行 js 命令

#JVM 图形化工具 
 jconsole
 jvisualvm 
 VisualGC
 jmc
 
 #GC
 标记清除算法（Mark and Sweep） 并行 GC 和 CMS 的基本原理
	清除      产生碎片
	复制      占用空间多
	整理	  需要停止工作等待整理
	
 串行 GC（Serial GC）/ParNewGC    
 并行 GC（Parallel GC）
 CMS GC（Mostly Concurrent Mark and Sweep Garbage Collector）
 G1 GC
 
 常用的组合为：
（1）Serial+Serial Old 实现单线程的低延迟垃圾回收机制；
（2）ParNew+CMS，实现多线程的低延迟垃圾回收机制；
（3）Parallel Scavenge和Parallel Scavenge Old，实现多线程的高吞吐量垃圾回收机制

脱离场景谈性能都是耍流氓