# 1.Redis总结

## 1.1基本数据类型以及低层实现

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2018.00.56.png" width="300" "/> </div><br>
 <div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2018.01.06.png" width="300" "/> </div><br>

  **ziplist**：

  组成结构如下图所示：
  <div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2018.47.26.png" width="300" "/> </div><br>

​    *zlbytes*：整个压缩列表占用字节长度；*zltail*：距离尾节点的偏移量；*zllen*：记录列表节点的数量；

​    *entry*：具体节点；*zlend*：列表结尾。

 **intset**：

 当使用set数据类型时**并且元素个数小于设置的set-max-intset-entries(默认512个)采用intset实现**。typedef struct inset{//编码方式 uint32_t encoding; //集合包含的元素个数 uint_32 length; //保存元素的数组 int8_t contents[]} intset;

  **skiplist**：

  <div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2019.06.59.png" width="300" "/> </div><br>
**跳表需要额外申请内存，以时间换空间。空间复杂度为O(N)级别。**

Typedef struct **zskiplist**{//表头节点和表尾节点 structz skiplistNode *header,*tail; //表中节点的数量 unsigned long length; //表中层数 int level;} zskiplist;

typedef struct **zskiplistNode**{

//层 struct zskiplistLevel{ 

//前进指针 struct zskiplistNode *forward;

 // 跨度 unsigned int span

}level[];

struct zskiplistNode {*backward;//后退指针

double score; //分值

 robj *obj; //成员对象

}zskiplistNode;

**Redis为什么一定要用跳表来实现有序集合而不用红黑树呢？**

​       跳表可以支持快速的插入、删除、查找操作写起来简单，甚至也可以代替红黑树。Redis中有序集合支持核心操作包括插入、删除、查找、按照区间查找、迭代输出有序序列。其中插入、删除、查找、迭代输出有序序列红黑树也能完成，**但是按照区间查找操作，红黑树的效率就没有那么高了。对于按照区间查找数据这个操作，跳表可以做到 O(logn) 的时间复杂度定位区间的起点，然后在原始链表中顺序往后遍历就可以了**。还有其他原因就是跳表实现起来简单，RBTree实现麻烦。

## 1.2 字典

### 1.2.1 字典底层实现
<div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2019.24.15.png" width="300" "/> </div><br>

typedef struct **dictht**{

dictEntry **table; // 哈希表数组
unsogned long size; // 哈希表大小

unsigned long sizemask; // 哈希表大小掩码，用于计算索引值

unsigned long used;

}dictht;

typedef struct **dictEntry**{

void *key; 

// 值

union{

​    void *val;

​    uint64_tu64;

​    Int64_ts64;

}v;

struct dictEntry *next;

}dictEntry;

<div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2019.28.21.png" width="300" "/> </div><br>

### 1.2.2 rehash

   当超过hash表负载因子的时候进行扩容，扩容会执行rehash操作

**1）为字典的ht[1]哈希表分配空间，那么ht[1]大小为大于等于ht[0].used*2的Math.pow(2,n);**

**2）将ht[0]上所有的键值对rehash到ht[1]上：重新计算hash值和索引值。**

**3）当节点全部迁移到ht[1]中之后，ht[0]与ht[1]角色进行交换。**

<div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2019.32.08.png" width="300" "/> </div><br>

 <div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2019.32.34.png" width="300" "/> </div><br>

### 1.2.3 渐进式rehash

当节点数目成百万和千万如果执行一次性hash操作，将这些键值对全部rehash到ht[1]的话，庞大的计算量可能会导致服务器在一段时间内停止服务(阻塞)，为了避免一次性的hash操作，可以分多次，渐进式的将ht[0]里的键值对慢慢的rehash到ht[1]中。分而治之的思想。

**1）为ht[1]分配空间，在字典中维持一个索引计数器变量rehashid，并将它值设置为0；表示rehash工作正式开始；**

**2）在rehash期间，每次对字典执行curd时，还会把ht[0]在索引rehashid上的键值对rehash到ht[1]上，执行完此操作之后rehashid增一；**

**3）当ht[0]键值对全部rehash到ht[1]上之后，将rehashid设为-1，表示rehash操作已经完成。**

​       **当执行渐进式rehash时，字典会同时使用ht[0]和ht[1]两个hash表，所以同时会在两个rud操作，但是执行c操作会在ht[1]中执行，保证ht[0]元素个数只减不增。**

## 1.3 Redis过期清理策略

### 1.3.1 定时删除

定时删除：**该删除策略对cpu是不友好的**，当为key设置过期时间之后，会有一个定时器，当key过期时就会被删除。因为每次创建一个带有过期时间的key，就会创建一个定时器，所以这种方式并不前世现实。

### 1.3.2 惰性清理

惰性删除：**该删除策略对内存是不友好的**，当get一个key时，首先检查这个key是否过期，如果过期则删除，

<div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2020.07.31.png" width="300" "/> </div><br>

### 1.3.3定期删除

上述两种删除策略一个是对cpu不友好一个是对内存不友好，所以采用定期清理策略来完善二者。

redis默认是每隔 100ms 就**随机抽取**一些设置了过期时间的key，检查其是否过期，如果过期就删除。注意这里是随机抽取的。为什么要随机呢？你想一想假如 redis 存了几十万个 key ，每隔100ms就遍历所有的设置过期时间的 key 的话，就会给 CPU 带来很大的负载！

**综上所述redis是采用定期删除+惰性删除策略来处理过期key的**

## 1.4Redis持久化操作

### 1.4.1RDB持久化

RDB持久化是把当前进程数据生成快照保存到硬盘的过程。RDB持久化分为手动触发和被动触发。

手动触发分为save和bgsave。save会阻塞当前Redis服务器，直到完成为止；bgsave会执行fork创建子进程来持久化，完成后自动结束。阻塞只发生在fork阶段，一般时间很短。

自动触发是save m n 表示m秒内数据集中n次修改，自动触发bgsave。
<div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2020.39.56.png" width="300" "/> </div><br>

### 1.4.2AOF持久化

Redis的AOF持久化是通过追加写命令来实现持久化，默认设置appendonly no 表示默认不开启AOF功能，AOF工作流程操作：命令的写入(append)；文件同步(sync)；文件重写(rewrite)；重启加载(load)。
<div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-25%2016.33.46.png" width="300" "/> </div><br>

**1）命令写入**

将set或get命令直接写入AOF缓冲区

**2）文件同步**
<div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-25%2016.35.41.png" width="300" "/> </div><br>

**3）重写机制**

随着命令不断写入AOF，文件越来越大，为了解决这个问题，Redis引入AOF重写机制压缩文件体积，重写机制包括(1)进程内超时数据不在写入(2)无效的命令不在处理(3)多条命令可以合并为1个

**实现原理(copy on write)：**

**1.调用fork()，创建子进程；**

**2.子进程把AOF写到一个临时文件中，不依赖原来的AOF文件；**

**3.主进程持续将新的变动写到内存和原来的AOF里；**

**4.主进程获取子进程重写AOF完成的信号，往新AOF同步增量变动；**

**5.使用新的AOF文件替换掉旧的AOF文件。**

**手动触发**：直接执行bgrewriteaof

**自动触发**：根据auto-aof-rewrite-min-size（AOF重写文件最小体积）和auto-aof-rewrite-percentage（当前AOF文件空间和上次重写AOF文件空间比值）

<div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-25%2016.48.51.png" width="300" "/> </div><br>

**4）重启加载**
<div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2020.40.32.png" width="300" "/> </div><br>

## 1.5 事件

Redis是一个事件驱动的程序，服务器需要处理文件事件和时间事件。

### 1.5.1 文件事件

Redis是基于Reactor模式来实现文件事件，文件事件使用I/O多路复用程序来同时监听多个套接字，每个套接字对应连接应答(accept)、写入(write)、读取(read)、关闭(close)等操作，文件事件分别由**套接字，I/O多路复用程序，文件事件分派器，以及事件处理器四部分组成**，其中I/O多路复用底层提供多个IO多路复用库函数可选，

<div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E6%96%87%E4%BB%B6%E4%BA%8B%E4%BB%B6%E6%A8%A1%E5%9E%8B.png" width="300" "/> </div><br>

 <div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-12-04%2018.45.08.png" width="300" "/> </div><br>

### 1.5.2 时间事件

时间事件分为定时事件和周期性事件，正常模式下Redis服务器只使用serverCron一个时间事件，Redis服务器以周期性事件的方式运行serverCron函数。serverCron主要工作包括：

* 更新服务器的各类统计信息，比如时间、内存占用、数据库占用情况
* 清理数据库中的过期时间对
* 关闭和清理连接失效的客户端
* 尝试进行AOF或者RDB持久化操作
* 如果服务器是主服务器，那么对从服务器进行定期同步
* 如果处于集群模式，对集群进行定期同步和连接测试

## 1.6缓存的设计

## 1.7 分布式锁的几种实现

分布式锁一般有三种实现方式：**1. 数据库排他锁；2. 基于Redis的分布式锁；3. 基于ZooKeeper的分布式锁。**

1）数据库排他锁实现分布式锁：https://blog.csdn.net/u010963948/article/details/79006572

2）Redis实现分布式锁：http://www.importnew.com/27477.html

3）基于ZooKeeper的分布式锁：https://www.hollischuang.com/archives/1716

https://github.com/CyC2018/CS-Notes/blob/master/notes/%E5%88%86%E5%B8%83%E5%BC%8F.md

项目中使用Redis分布式锁案例：

<div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81.png" width="300" "/> </div><br>

**对于加锁来说：如果先调用setnx，如果获取锁成功在执行expire，但是这两条命令不具有原子性，如果setnx执行之后程序突然崩溃，那么就会造成死锁。**

**对于解锁来说：因为lua脚本能够保证原子性，如果不使用lua，使用先比较requestId，如果想等在删除key，那么这也是不保证原子原子性的，因为判断requestId想等之后有可能到达了key的过期时间，那么就会造成把别的进行获取到的分布式锁释放掉**

## 1.8 集群扩容

### 1.8.1 集群扩容

分为三步：**准备新节点、加入集群、迁移槽和数据**

* （1）**准备新节点**

提前准备好实例，并运行在集群模式下。

* （2）**加入集群**

方式一：手动执行cluster meet {ip}:{port}

方式二：执行redis-trib.rd {ip}:{host}

正式环境建议使用redis-trib.rb add node加入新节点，该命令内部会执行新节点的状态检查，如果新节点已经加入其他集群或者包含数据，则放弃集群加入。

* （3）**迁移槽和数据**

1）对目标节点执行cluster setslot{slot}importing{sourceNodeId}，让目标节点准备导入槽的数据。

2）对源节点执行cluster setslot{slot}migrating{sourceNodeId}，让源节点准备迁出槽的数据。

3）源节点循环执行cluster getkeysinslot {slot} {count}，获取count个属于{slot}的键。

4）在源节点执行count个键的迁移，migrate{targetIp}{targetPort}""0{timeout} keys{keys...}

5）重复3）和4）直到槽下所有的键值数据迁移搭配目标节点

6）向集群内所有主节点发送cluster setslot{slot} node {targetNodeId}，通知槽分配给目标节点。

## 1.9 集群故障检测和故障转移

### 1.9.1 故障检测

集群中每个节点都会定期地向集群中的其他节点发送PING消息，以此来检测对方是否在线，如果接收PING消息的节点没有在规定时间内回复PONG消息，那么发送PING消息的节点就会将接收PING消息的节点标记为**疑似下线（probable fail, PFAIL)**。

在一个集群中，**超过半数以上**负责处理槽的主节点都将这个主节点x报告为疑似下线，那么这个主节点x将被标记为以下线（FAIL），并向**集群广播一条关于主节点x的FAIL消息**。

### 1.9.2 故障转移

节点下线后，从节点将开始对下线主节点进行故障转移，有以下几个步骤：

- 1）**选主(Raft算法)**：从从节点中选出新的主节点
- 2）**SLAVEOF no one：**被选中的从节点会执行**SLAVEOF no one命令**，成为新的主节点
- 3）**指派槽：**新的主节点会撤销所有对已下线主节点的槽指派，并将这些槽全部指派给自己
- 4）**广播新主节点：**新的主节点像集群广播PONG消息，让其它节点了解这个节点已经成为新的主节点，接管原本已下线节点负责处理的槽
- 5）**处理请求：**新的主节点开始接受和自己负责处理的槽有关的命令请求，故障转移完成。

## 1.10 Redis主从复制

### 1.10.1 2.8版本之前主从复制

执行SYNC步骤：

* **1）从服务器发送SYNC命令**

* **2）主服务器执行bgsave，生成RDB文件，在生成RDB文件过程中继续有写命令，生成RDB文件之后发送给从服务器。**

* **3）发送缓冲区中所有的写命令**

如果在同步时候出现中断，从服务器会发送SYNC，主服务器会把当前**所有的键值对**bgsave成一个RDB文件，一并发送到从服务器，这样做是低效的。生成RDB文件过程是耗费CPU、内存和磁盘I/O资源

### 1.10.2 新版本复制

和旧版本的区别主要是实现了部分重同步，包括三个部分：

* **主服务器和从服务器的复制偏移量**
* 主服务器的复制积压缓冲区(**如果有一个从服务器断开，再次重连的时候需要从该区域读数据**)
* **服务器运行ID**

<div align="center"> <img 
  src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E7%B3%BB%E7%89%88%E6%9C%AC%E5%A4%8D%E5%88%B6%E6%AD%A5%E9%AA%A4.png" width="300" "/> </div><br>
## 1.11 Redis Sentinel

### 1.11.1故障转移处理：

* 1）**主观下线**：sentinel每隔1秒向master发送ping，如果在一段时间内没有收到回复或者收到无效回复，则认为master主观下线。
* 2）**客观下线**：如果一个sentinel认为master主观下线，会询问其他sentinel是否下线，如果认为master主观下线的master达到一定数量，那么认为其客观下线
* 3）**选择leader sentinel(Raft算法)**：进行故障处理，由它负责故障转移
* 4）**leader会在slaves中选择一个作为新的master**。leader向master发送**slaveof on one**，向其他slaves发送**slaveof ip port**（新的master的ip和port），slaves就会复制新的master中的数据。

### 1.11.2 leader会选择哪一个slave作为新的master？

**1）排除断线的slaves**

**2）选择优先级最高的**

**3）选择复制偏移量最大的**

**4）选择run_id最小的**

## 1.12 Gossip协议

在杂乱无章中寻求一致：
**(1) 每个节点都随机的与对方通信，最终所有节点状态达成一致；**

**(2) 种子节点定期随机的向其他节点列表以及需要传播的消息；**

**(3) 不保证信息一定会传递给所有节点，但是最终会趋于一致**