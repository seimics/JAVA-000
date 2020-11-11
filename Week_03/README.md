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
```
public static void main(String[] args) {
  Runnable task = new Runnable() {
  @Override
  public void run() {
	try{
	  Thread.sleep(5000);
	}catch(InterrruptedException e) {
	e.printStackTrace();
	}
  }
  };
  Thread t = new Thread(task);
  thread.setName("test-thread-1");
  thread.setDaemon(true);
  thread.start();
}
```
基础接口 Runnable
重要实现 Thread implements Runnable
Thread#start()    创建新线程
Thread#run()      本线程调用

Thread的状态改变操作
1. Thread.sleep(long millis)    一定是当前线程调用此方法，当前线程进入TIMED_WAITING状态，但不释放对象锁，millis后线程自动苏醒进入就绪状态。作用：给其他线程执行机会的最好方式。
2. Thread.yield()      当前线程放弃获取的CPU时间片，但不释放锁资源
3. t.join()/t.join(long millis)  当前线程里调用其他线程t的join方法，当前线程进入WAITING/TIME_WAITING状态，当前线程不会释放已经持有的对象锁。线程t执行完毕或者millis时间到，当前线程进入就绪状态。
4. obj.wait()  当前线程调用对象的wait()方法，当前线程释放对象锁，进入等待队列。依靠notify()/notifyAll()唤醒或wait(long timeout)timeout时间到自动唤醒。
5. obj.notify() 唤醒在此对象监视器上等待的单个线程，选择是任意性的。

Thread的中断与异常处理
1. 线程内部自己处理异常，不溢出到外层
2. 如果线程被Object.wait，Thread.join和Thread.sleep三种方法之一阻塞，此时调用该线程的interrupt()方法，那么该线程讲抛出一个InterruptedException中断异常
该线程必须实现预备好处理此异常，从而提早地终结被阻塞状态。如果线程没有被阻塞，这时调用interrupt()将不起作用，直到执行到wait(),sleep(),join()时，才会抛出InterruptedException
3. 如果是计算密集型的操作怎么办? 分段处理，每个片段检查一下状态，是否要终止。



