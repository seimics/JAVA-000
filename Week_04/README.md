# 学习笔记 
Java并发编程
1. Java并发包  Java.util.concurrency
锁机制类 Locks : Lock, Condition, ReadWriteLock
原子操作类 Atomic: AtomicInteger LongAdder AtomicLong
线程池相关类 Executer : Future, Callable, Executor
信号量工具类 Tools : CounDownLatch, CyclicBarrier, Semephore
并发集合类 Collections : CopyOnWriteArrayList, ConcurrentMap

2. 为什么需要显式的Lock
synchronized方式的问题：
    1）、同步块的阻塞无法中断（不能Interruptibly）
    2）、同步块的阻塞无法控制超时（无法自动解锁）
    3）、同步块无法异步处理锁（即不能立即知道是否可以拿到锁）
    4）、同步块无法根据条件灵活地加锁解锁（即之恶能跟同步块范围一致）
Lock :
锁工具包 java.util.concurrent.locks
Lock接口设计：
支持中断的API
void lockInterruptibly() throws InterruptedException;
支持超时的API
boolean tryLock(lock time, TimeUnit unit) throws InterruptedException;
支持非阻塞获取锁的API
boolean tryLock();

void lock(); 获取锁; 类比synchronized (lock) void lockInterruptibly() throws InterruptedException; 获取锁; 允许打断; 
boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
尝试获取锁;  成功则返回true; 超时则退出
boolean tryLock(); 尝试【无等待】获取锁;  成功则返回true void unlock(); 解锁；要求当前线程已获得锁; 类比同步块结束
ConditionnewCondition(); 新增一个绑定到当前Lock的条件； 示例: （类比:  Objectmonitor） final Lock lock = new ReentrantLock(); final Condition notFull  = lock.newCondition(); final Condition notEmpty = lock.newCondition();

3. AQS
AbstractQueuedSynchronizer，即队列同步器。它是构建锁或者其他同步组件的基础（ 如Semaphore、CountDownLatch、ReentrantLock、ReentrantReadWriteLock）， 是JUC并发包中的核心基础组件。
Semaphore
CountdownLatch
CyclicBarrier
Future/FutureTask/CompletableFuture

4. 常用线程安全类型
1、 ArrayList 安全问题： 1、写冲突： -两个写，相互操作冲突 2、读写冲突： -读，特别是iterator的时候，数据个数变了，拿到了非预期数据或者报错 -产生ConcurrentModificationException 
2、 LinkedList 1、写冲突： -两个写，相互操作冲突 2、读写冲突： -读，特别是iterator的时候，数据个数变了  ，拿到了非预期数据或者报错 -产生ConcurrentModificationException 
3、 CopyOnWriteArrayList 
4、 HashMap 安全问题： 1、写冲突， 2、读写问题，可能会死循环 3、keys()无序问题 
5、 ConcurrentHashMap-Java7分段   ConcurrentHashMap-Java8 CAS　大数组 链表 红黑树  保持稀疏 

5. 并发编程相关 
ThreadLocal 
并行Stream => parallel 
伪并发问题 
分布式下的锁和计数器 

