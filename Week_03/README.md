# 学习笔记 
没用过netty 感觉学习很吃力，跟不上进度
## 1. 高性能 
	高并发用户 Concurrent Users  技术指标
	高吞吐量 Throughout  技术指标
	低延迟 latency 技术指标
	
	latency  服务器系统的响应时间  对于系统
	Responce Time 往返客户端的时间 对于客户
	
	高性能的副作用
	1. 系统复杂度
	2. 建设维护成本
	3. 可靠性低
	
## 2. Netty
	Client
	Group
	NioEventLoopGroup
	EventLoop
	Channel
	Pipeline
	Handler
	Filter
	Executor
	
	Event & Handler
		入站事件：
		- 通道激活和停用 
		- 读操作事件 
		- 异常事件 
		- 用户事件 
		出站事件：
		- 打开连接 
		- 写入数据 
		- 刷新数据 
		- 关闭连接 
		
## 3.Netty
	netty对三种模式的支持
	1）Reator单线程模式：EventLoopGroup eventGroup = new NioEventLoopGroup(1);
						 ServerBootstrap serverBootstrap = new ServerBootstrap();
						 serverBootstrap.goup(eventGroup);
	2)非主从Reactor多线程模式
						 EventLoopGroup eventGroup = new NioEventLoopGroup(2*CPU_CORES);
						 ServerBootstrap serverBootstrap = new ServerBootstrap();
						 serverBootstrap.goup(eventGroup)
	3)主从Reactor多线程模式
						 EventLoopGroup bossGroup = new NioEventLoopGroup(3);
						 EventLoopGroup workerGroup = new NioEventLoopGroup(8);
						 ServerBootstrap serverBootstrap = new ServerBootstrap();
						 serverBootstrap.group(bossGroup, workerGroup);
						 
	核心对象
		bootstrap
		Eventloop eventLoopGroup
		Channel
		Handler encode, decode
		
## 4.多线程
	Java线程创建过程
		Thread t1 = new Thread();
		t1.start();
	java层面  JVM层面  OS层面
	
	