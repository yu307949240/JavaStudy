# 1.Redis总结

## 1.1基本数据类型以及低层实现

![image-20181124191550186](/Users/yuyouquan/Library/Application%20Support/typora-user-images/image-20181124191550186.png)

![image-20181124191622997](/Users/yuyouquan/Library/Application%20Support/typora-user-images/image-20181124191622997.png)

  **ziplist**：组成结构如下图所示：![屏幕快照 2018-11-24 18.47.26](/Users/yuyouquan/Desktop/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2018.47.26.png)

​    *zlbytes*：整个压缩列表占用字节长度；*zltail*：距离尾节点的偏移量；*zllen*：记录列表节点的数量；

​    *entry*：具体节点；*zlend*：列表结尾。

 **intset**：当使用set数据类型时**并且元素个数小于设置的set-max-intset-entries(默认512个)采用intset实现**。typedef struct inset{//编码方式 uint32_t encoding; //集合包含的元素个数 uint_32 length; //保存元素的数组 int8_t contents[]} intset;

  **skiplist**：![屏幕快照 2018-11-24 19.06.59](/Users/yuyouquan/Desktop/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2019.06.59.png)

Typedef struct **zskiplist**{//表头节点和表尾节点 structz skiplistNode *header,*tail; //表中节点的数量 unsigned long length; //表中层数 int level;} zskiplist;

typedef struct **zskiplistNode**{

//层 struct zskiplistLevel{ 

//前进指针 struct zskiplistNode *forward;

 // 跨度 unsigned int span

}level[];

struct zskiplistNode *backward;//后退指针

double score; //分值

 robj *obj; //成员对象

}zskiplistNode;

## 1.2 字典

### 1.2.1 字典底层实现

![屏幕快照 2018-11-24 19.24.15](/Users/yuyouquan/Desktop/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2019.24.15.png)

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

![屏幕快照 2018-11-24 19.28.21](/Users/yuyouquan/Desktop/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2019.28.21.png)

### 1.2.2 rehash

   当超过hash表负载因子的时候进行扩容，扩容会执行rehash操作

**1）为字典的ht[1]哈希表分配空间，那么ht[1]大小为大于等于ht[0].used*2的Math.pow(2,n);**

**2）将ht[0]上所有的键值对rehash到ht[1]上：重新计算hash值和索引值。**

**3）当节点全部迁移到ht[1]中之后，ht[0]与ht[1]角色进行交换。**



![屏幕快照 2018-11-24 19.32.08](/Users/yuyouquan/Desktop/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2019.32.08.png)

![屏幕快照 2018-11-24 19.32.34](/Users/yuyouquan/Desktop/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2019.32.34.png)

### 1.2.3 渐进式rehash

当节点数目成百万和千万如果执行一次性hash操作，将这些键值对全部rehash到ht[1]的话，庞大的计算量可能会导致服务器在一段时间内停止服务(阻塞)，为了避免一次性的hash操作，可以分多次，渐进式的将ht[0]里的键值对慢慢的rehash到ht[1]中。分而治之的思想。

**1）为ht[1]分配空间，在字典中维持一个索引计数器变量rehashid，并将它值设置为0；表示rehash工作正式开始；**

**2）在rehash期间，每次对字典执行curd时，还会把ht[0]在索引rehashid上的键值对rehash到ht[1]上，执行完此操作之后rehashid增一；**

**3）当ht[0]键值对全部rehash到ht[1]上之后，将rehashid设为-1，表示rehash操作已经完成。**

​       **当执行渐进式rehash时，字典会同时使用ht[0]和ht[1]两个hash表，所以同时会在两个rud操作，但是执行c操作会在ht[1]中执行，保证ht[0]元素个数只增不减。**

## 1.3 Redis过期清理策略

### 1.3.1 定时删除

定时删除：当为key设置过期时间之后，会有一个定时器，当key过期时就会被删除。因为每次创建一个带有过期时间的key，就会创建一个定时器，所以这种方式并不前世现实。这个是对内存友好的，但是是对cpu不友好的。

### 1.3.2 惰性清理

惰性删除：当get一个key时，首先检查这个key是否过期，如果过期则删除，显然整个是对cpu友好的，但是是对内存不友好的。



![屏幕快照 2018-11-24 20.07.31](/Users/yuyouquan/Desktop/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2020.07.31.png)

### 1.3.3定期删除

上述两种删除策略一个是对cpu不友好一个是对内存不友好，所有采用定期清理策略来完善二者。

​        redis默认是每隔 100ms 就**随机抽取**一些设置了过期时间的key，检查其是否过期，如果过期就删除。注意这里是随机抽取的。为什么要随机呢？你想一想假如 redis 存了几十万个 key ，每隔100ms就遍历所有的设置过期时间的 key 的话，就会给 CPU 带来很大的负载！

**综上所述redis是采用定期删除+惰性删除策略来处理过期key的**

## 1.4Redis持久化操作

### 1.4.1RDB持久化

RDB持久化是把当前进程数据生成快照保存到硬盘的过程。RDB持久化分为手动触发和被动触发。

手动触发分为save和bgsave。save会阻塞当前Redis服务器，直到完成为止；bgsave会执行fork创建子进程来持久化，完成后自动结束。阻塞只发生在fork阶段，一般时间很短。

自动触发是save m n 表示m秒内数据集中n次修改，自动触发bgsave。

![屏幕快照 2018-11-24 20.39.56](/Users/yuyouquan/Desktop/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2020.39.56.png)

### 1.4.2AOF持久化

Redis的AOF持久化是通过追加写命令来实现持久化，默认设置appendonly no 表示默认不开启AOF功能，AOF工作流程操作：命令的写入(append)；文件同步(sync)；文件重写(rewrite)；重启加载(load)。

![屏幕快照 2018-11-25 16.33.46](/Users/yuyouquan/Desktop/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-25%2016.33.46.png)

**1）命令写入**

将set或get命令直接写入AOF缓冲区

**2）文件同步**

![屏幕快照 2018-11-25 16.35.41](/Users/yuyouquan/Desktop/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-25%2016.35.41.png)

**3）重写机制**

随着命令不断写入AOF，文件越来越大，为了解决这个问题，Redis引入AOF重写机制压缩文件体积，重写机制包括(1)进程内超时数据不在写入(2)无效的命令不在处理(3)多条命令可以合并为1个

**手动触发**：直接执行bgrewriteaof

**自动触发**：根据auto-aof-rewrite-min-size（AOF重写文件最小体积）和auto-aof-rewrite-percentage（当前AOF文件空间和上次重写AOF文件空间比值）

![屏幕快照 2018-11-25 16.48.51](/Users/yuyouquan/Desktop/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-25%2016.48.51.png)

**4）重启加载**

![屏幕快照 2018-11-24 20.40.32](/Users/yuyouquan/Desktop/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-24%2020.40.32.png)

