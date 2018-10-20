* [一.AQS数据结构以及相关概念](#一aqs数据结构以及相关概念)
  * [1.FIFO队列](#1fifo队列)
  * [2.Node和ConditionObject内部类](#2node和conditionobject内部类)
  * [3.独占式和共享式](#3独占式和共享式)
  * [4.公平锁和非公平锁](#4公平锁和非公平锁)
* [二.AQS类核心函数(模板方法模式)](#二aqs类核心函数(模板方法模式))
  * [1.acquire()：独占式的获取资源](#1acquire独占式的获取资源)
  * [2.release()：独占模式下释放资源](#2release独占模式下释放资源)
* [三.AQS应用案例](#三aqs应用案例)
  * [1.独占式](#1独占式)
    * [1.1 ReentrantLock](#11-reentrantlock)
    * [1.2 ReentrantReadWriteLock](#12-reentrantreadwritelock)
    * [1.3 ThreadPoolExecutor](#13-threadpoolexecutor)
  * [2.共享式](#2共享式)
    * [2.1 Semaphore](#21-semaphore)
    * [2.2 CountDownLatch](#22-countdownlatch)
# 一.AQS数据结构以及相关概念

## 1.FIFO队列

AQS是一个基于FIFO算法的队列。数据结构可以表示成如下：采用双向链表存储，包括head和tail结点。
<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/AQS内部类节点.png" width="400" "/> </div><br>
  
## 2.Node和ConditionObject内部类

### Node：
```java
static final class Node {

​        // 模式，分为共享与独占

​        // 共享模式

​        static final Node SHARED = new Node();

​        // 独占模式

​        static final Node EXCLUSIVE = null;        

​        // 结点状态

​        // CANCELLED，值为1，表示当前的线程被取消

​        // SIGNAL，值为-1，表示当前节点的后继节点包含的线程需要运行，也就是unpark

​        // CONDITION，值为-2，表示当前节点在等待condition，也就是在condition队列中

​        // PROPAGATE，值为-3，表示当前场景下后续的acquireShared能够得以执行

​        // 值为0，表示当前节点在sync队列中，等待着获取锁

​        static final int CANCELLED =  1;

​        static final int SIGNAL    = -1;

​        static final int CONDITION = -2;

​        static final int PROPAGATE = -3;        



​        // 结点状态

​        volatile int waitStatus;        

​        // 前驱结点

​        volatile Node prev;    

​        // 后继结点

​        volatile Node next;        

​        // 结点所对应的线程

​        volatile Thread thread;        

​        // 下一个等待者

​        Node nextWaiter;

​        

​        // 结点是否在共享模式下等待

​        final boolean isShared() {

​            return nextWaiter == SHARED;

​        }

​        

​        // 获取前驱结点，若前驱结点为空，抛出异常

​        final Node predecessor() throws NullPointerException {

​            // 保存前驱结点

​            Node p = prev; 

​            if (p == null) // 前驱结点为空，抛出异常

​                throw new NullPointerException();

​            else // 前驱结点不为空，返回

​                return p;

​        }

​        

​        // 无参构造函数

​        Node() {    // Used to establish initial head or SHARED marker

​        }

​        

​        // 构造函数

​         Node(Thread thread, Node mode) {    // Used by addWaiter

​            this.nextWaiter = mode;

​            this.thread = thread;

​        }

​        

​        // 构造函数

​        Node(Thread thread, int waitStatus) { // Used by Condition

​            this.waitStatus = waitStatus;

​            this.thread = thread;

​        }

​    }

```

### ConditionObject：
```java
// 内部类

​    public class ConditionObject implements Condition, java.io.Serializable {

​        // 版本号

​        private static final long serialVersionUID = 1173984872572414699L;

​               // condition队列的头结点

​        private transient Node firstWaiter;

​               // condition队列的尾结点

​        private transient Node lastWaiter;

​        // 构造函数

​        public ConditionObject() { }

​    }
```
## 3.独占式和共享式

独占式：同一时刻只有一个线程获取到锁。而其他线程放在AQS中等待

共享式：同一时刻可以有多个线程获取同步状态。

## 4.公平锁和非公平锁

公平锁每一次acquired的时候直接在AQS取对尾元素。

非公平锁每一次尝试获取资源，正在此时恰好有一个资源被释放，则会被当前线程获取到，而不是先去查AQS，这就造成了不公平的现象

# 二.AQS类核心函数(模板方法模式)

## 1.acquire()：独占式的获取资源
<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/AQS.acquire().png" width="400" "/> </div><br> 
(1)执行tryAcquire()，试图去在独占模式下获取对象状态，需要子类重写此方法。

(2)若tryAcquire失败，则把线程放入sync queue中。

## 2.release()：独占模式下释放资源

和上述acquire分析类似，其中tryRelease()也是需要子类重写的。

3.acquireShared()：共享模式下获取资源

4.releaseShared()：共享模式下释放资源

# 三.AQS应用案例

## 1.独占式

### 1.1 ReentrantLock

https://www.cnblogs.com/leesf456/p/5383609.html

有一个静态内部类Sync实现AQS模板方法。ReentrantLock默认构造方法是非公平锁，可以使用构造函数ReentrantLock(true)设置为公平锁

```java

abstract static class Sync extends AbstractQueuedSynchronizer {

​        // 序列号

​        private static final long serialVersionUID = -5179523762034025860L;

​                // 获取锁

​        abstract void lock();

​                // 非公平方式获取

​        final boolean nonfairTryAcquire(int acquires) {

​              }

​                // 试图在共享模式下获取对象状态，此方法应该查询是否允许它在共享模式下获取对象状态，如果允许，则获取它

​        protected final boolean tryRelease(int releases) {

​                 }     

​        // 判断资源是否被当前线程占有

​        protected final boolean isHeldExclusively() {

​            }

​        // 新生一个条件

​        final ConditionObject newCondition() {

​            return new ConditionObject();

​        }

​        // Methods relayed from outer class

​        // 返回资源的占用线程

​        final Thread getOwner() {        

​        }

​    }
```
### 非公平锁(default)：
```java
​    static final class NonfairSync extends Sync {

​        // 版本号

​        private static final long serialVersionUID = 7316153563782823691L;



​        // 获得锁

​        final void lock() {

​            if (compareAndSetState(0, 1)) // 比较并设置状态成功，状态0表示锁没有被占用(当前线程释放了资源，可以直接获取锁，不需要再查AQS，这就造成了不公平性)

​                // 把当前线程设置独占了锁

​                setExclusiveOwnerThread(Thread.currentThread());

​            else // 锁已经被占用，或者set失败

​                // 以独占模式获取对象，忽略中断

​                acquire(1); 

​        }



​        protected final boolean tryAcquire(int acquires) {

​            return nonfairTryAcquire(acquires);

​        }

​    }
```
### 公平锁：
```java
​    static final class FairSync extends Sync {

​        // 版本序列化

​        private static final long serialVersionUID = -3000897897090466540L;



​        final void lock() {

​            // 以独占模式获取对象，忽略中断

​            acquire(1);

​        } 
​        // 尝试公平获取锁

​        protected final boolean tryAcquire(int acquires) {

​            // 获取当前线程

​            final Thread current = Thread.currentThread();

​            // 获取状态

​            int c = getState();

​            if (c == 0) { // 状态为0

​                if (!hasQueuedPredecessors() &&

​                    compareAndSetState(0, acquires)) { // 不存在已经等待更久的线程并且比较并且设置状态成功

​                    // 设置当前线程独占

​                    setExclusiveOwnerThread(current);

​                    return true;

​                }

​            }

​            else if (current == getExclusiveOwnerThread()) { // 状态不为0，即资源已经被线程占据

​                // 下一个状态

​                int nextc = c + acquires;

​                if (nextc < 0) // 超过了int的表示范围

​                    throw new Error("Maximum lock count exceeded");

​                // 设置状态

​                setState(nextc);

​                return true;

​            }

​            return false;

​        }

​    }
```
## 1.2 ReentrantReadWriteLock

内部类：
<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/ReentrantReadWriteLock.png" width="400" "/> </div><br> 

<https://www.cnblogs.com/leesf456/p/5419132.html>

## 1.3 ThreadPoolExecutor

内部类：

<https://www.cnblogs.com/leesf456/p/5585627.html>

# 2.共享式

## 2.1 Semaphore
https://www.cnblogs.com/leesf456/p/5414778.html
<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/semaphore.png" width="400" "/> </div><br> 
<https://www.cnblogs.com/leesf456/p/5414778.html>

## 2.2 CountDownLatch

<https://www.cnblogs.com/leesf456/p/5406191.html>
