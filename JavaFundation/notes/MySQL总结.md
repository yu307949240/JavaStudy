# MySQL总结

## 1.索引

常见的索引模型：**二分搜索树索引、b树索引、b+树索引、哈希表索引、有序数组、BitMap索引**

BST在特殊场景下会退化到O(n)，并且I/O很频繁

B-Tree：**1）根节点不少于两个子节点、2）根节点最多有m个子节点、3）叶子节点最多有不超过m个子节点，最少不小于ceil(m/2)个子节点。**

B+-Tree：**1）非叶子节点之存储节点，不存储数据、2）所有叶子节点用指针连接起来，方便做范围查询。**

B+更适合用来存储索引：磁盘读写代价更低、查询效率更稳定、有利于对数据库的扫描(范围查询效率高)

HASH索引：仅仅用于“=”、“IN”查询，**不能范围查询、无法排序、hash冲突造成效率低下，有可能造成数据都放到同一个bucket中**

### 1.1 主键索引和非主键索引(密集索引和稀疏索引)

InnoDB使用的索引是b+树索引

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-27%2020.23.01.png" width="400" "/> </div><br> 

索引类型分为主键索引和非主键索引。

**主键索引的叶子节点存的是整行数据。在 InnoDB 里，主键索引也被称为聚簇索引或密集索引；**

**非主键索引的叶子结点内容是主键结点的值，在InnoDB里，非主键索引也称之为二级索引或稀疏索引**

### 1.2 回表查询

（1）基于主键索引和普通索引的查询有什么区别？

* 如果语句是 select * from T where ID=500，即主键查询方式，则只需要搜索ID这颗b+树即可

* 如果语句是 select * from T where k= 5，即普通索引查询方式，则需要先搜索 k 索引树，得到

  ID 的值为 500，再到 ID 索引树搜索一次。这个过程称**回表查询**

（2）如果一个表没有主键，那么非主键索引查询的时候过程是什么？

没有主键的时候InnoDB会建一个Rowid做主键

### 1.3 覆盖索引

**select ID from T where k between 3 and 5；**

如果是select主键的情况那么就不需要在**回表查询**，减少了树的搜索次数，显著提升了性能；

如果有一个高频繁的请求可以考虑用覆盖索引的场景。

### 1.4 最左前缀匹配原则

### 1.5 索引下推

select * from tuser where name like '张 %' and age=10 and ismale=1;

(name,age)是联合索引

在MySQL5.6之前，只能从ID3开始一个个回表查询；

在MySQL 5.6之后，引入了**索引下推**优化可以在索引遍历的过程中，对包含的字段优先判断，直接过滤掉不满足条件的数据，减少回表次数。

* 无索引下推查询过程：

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-27%2020.49.57%202.png" width="400" "/> </div><br> 

* 索引下推执行过程：

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-27%2020.50.06.png" width="400" "/> </div><br> 

### 1.6聚簇索引和非聚簇索引

和1.1中讲的主键索引和非主键索引类似，

​        InnoDB使用的是**聚簇索引**，将主键组织到一棵B+树中，而行数据就储存在叶子节点上，若使用"where id = 14"这样的条件查找主键，则按照B+树的检索算法即可查找到对应的叶节点，之后获得行数据。若对Name列进行条件搜索，则需要两个步骤：第一步在辅助索引B+树中检索Name，到达其叶子节点获取对应的主键。第二步使用主键在主索引B+树种再执行一次B+树检索操作，最终到达叶子节点即可获取整行数据。**InnoDB表数据保存在.frm文件，索引保存在.ibd文件结尾的！**

**InnoDB中必须有一个密集索引(主键索引)，如果有主键则该主键为密集索引；如果没有主键，该表的第一个唯一非空索引作为密集索引；如果都不满足两个，那么会内部生成一个rowid作为主键。**

　　MyISM使用的是**非聚簇索引**，非聚簇索引的两棵B+树看上去没什么不同，节点的结构完全一致只是存储的内容不同而已，主键索引B+树的节点存储了主键，辅助键索引B+树存储了辅助键。表数据存储在独立的地方，这两颗B+树的叶子节点都使用一个地址指向真正的表数据，对于表数据来说，这两个键没有任何差别。由于索引树是独立的，通过辅助键检索无需访问主键的索引树。**MyISM表数据保存在.frm文件、表索引保存在.MYI文件、表索引数据保存在.MYD文件中！**

  <div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-27%2020.55.50.png" width="400" "/> </div><br> 

### 1.7 索引失效的场景

（1）如果条件中有or，即使其中有条件带索引也不会使用(这也是为什么尽量少用or的原因)。**要想使用or，又想让索引生效，只能将or条件中的每个列都加上索引；**

（2）对于组合索引，不是使用的第一部分，则不会使用索引（**最左前缀匹配**）；

（3）like查询**以%开头**；

（4）如果列类型是字符串，那一定要在条件中将数据使用引号引用起来,否则不使用索引；

（5）如果mysql估计使用全表扫描要比使用索引快,则不使用索引。

### 1.8 索引的优点
（1）避免全表扫描；  
（2）随机I/O变成顺序I/O，查询效率更高，磁盘读写代价更低；  
（3）帮助服务器避免排序和分组，以及避免创建临时表(不使用索引时，排序和分组需要创建临时表，当使用B+索引，由于本身有序，不需要创建临时表)。  
### 1.9 索引相关优化
（1）show index from table; -- 查询table所有索引
（2）有时候mysql会选错索引，执行analyze table t即可修复，select * from t where a between 10000 and 20000;(a列有索引)，explain显示![image](https://user-images.githubusercontent.com/30522257/119502241-509dd180-bd9c-11eb-9367-264775556547.png)(解释：因为这里mysql会认为查询a的时候会进行回表查询，预估会导致很慢，所以就直接查询全表)![image](https://user-images.githubusercontent.com/30522257/119502631-b5f1c280-bd9c-11eb-8dfc-8c6b2b0c5b7d.png)

## 2.MySQL中的锁(表锁和行锁)

概述：

* 表级锁，开销小，加锁快，锁定力度大，发生锁冲突的概率最高，并发度最低。
* 行级锁，开销大，加锁慢，会出现死锁，锁的粒度最小，发生锁冲突的概率最低，并发度也最高。
* 页级锁，开销和加锁时间，以及锁定粒度介于表锁和行锁之间，并发度一般。

InnoDB锁问题：

InnoDB与MyISAM最大的不同有两个，**一是支持事务，二是采用了行锁(共享锁和排他锁)**。

### 2.1 Mysql中的事务

事务满足ACID四大特性

### 2.2 InnoDB行锁和加锁方法

InnoDB实现了以下两种方式的行锁：

* 共享锁(s)：允许一个事务去读一行，阻止其他事务获得相同数据集的排他锁。
* 排他锁(x)：允许获取排他锁的事务更新数据，阻止其他事务取得相同的数据集共享读锁和排他写锁。

另外，为了解决**行锁和表锁同时存在有可能引发冲突**，实现多粒度锁机制，InnoDB还有两种内部使用的意向锁（Intention Locks），这两种意向锁都是**表锁**。

* 意向共享锁（IS）：事务打算给数据行共享锁，事务在给一个数据行加共享锁前必须先取得该表的IS锁。

* 意向排他锁（IX）：事务打算给数据行加排他锁，事务在给一个数据行加排他锁前必须先取得该表的IX锁。

#### **InnoDB行锁模式兼容性列表**

| 当前锁模式/是否兼容/请求锁模式 | X        | IX   | S        | IS   |
| :----------------------------: | -------- | ---- | -------- | ---- |
|               X                | **冲突** | 冲突 | **冲突** | 冲突 |
|               IX               | 冲突     | 兼容 | 冲突     | 兼容 |
|               S                | **冲突** | 冲突 | **兼容** | 兼容 |
|               IS               | 冲突     | 兼容 | 兼容     | 兼容 |

​       如果一个事务请求的锁模式与当前的锁兼容，InnoDB就请求的锁授予该事务；反之，如果两者两者不兼容，该事务就要等待锁释放。

​       意向锁是InnoDB自动加的，不需要用户干预，对于update，delete，insert语句，InnoDB会自动给涉及的数据集加排他锁；对于普通的select语句，InnoDB不会加任何锁，事务也可以显示加共享锁或排他锁。

* 加共享锁：SELECT * FROM table_name WHERE ... **LOCK IN SHARE MODE**
* 加排他锁：SELECT * FROM table_name WHERE ... **FOR UPDATE**

### 2.3 InnoDB行锁实现方式

​       InnoDB行锁是通过索引上的索引项来实现的，这就意味着：**只有通过索引来检索数据，InnoDB才会使用行级锁，否则是否表锁！**
​    行锁包括**共享锁(S)和排他锁(X)**

### 2.4 Next-Key Locks
   在介绍临建锁之前先介绍下记录锁(Record Locks)、间隙锁(Gap Locks)、临建锁(Next-Key Locks)。
   * **记录锁(Record Locks)**    
      封锁索引记录，例如：
   ```sql
   select * from t where id = 1 for update; ## id为索引
   ```
   会在id=1上加锁，以阻止其他事务插入、更新、删除id=1这行。
   * **间隙锁(Gap Locks)**  
     封锁索引记录中的间隔
   ```sql
   select * from t where id between 8 and 15 
   ```
   * **临键锁(Next-Key Locks)**  
      是**记录锁和间隙锁**的组合，用来解决“幻读”的问题，如下所述：  
​  当我们用范围条件而不是相等条件检索数据，并请求共享或排他锁时，InnoDB会给符合条件的已有数据的索引项加锁；对于键值在条件范围内但并不存在的记录，叫做“间隙(GAP)”，InnoDB也会对这个“间隙”加锁。  
      举例来说，假如emp表中只有101条记录，其empid的值分别是1,2,...,100,101，下面的SQL：

```sql
SELECT * FROM emp WHERE empid > 100 FOR UPDATE
```
  上述sql会在大于100当记录上加间隙锁，在封锁期间会阻止empid大于100的记录插入，如果能够插入成功，就会造成“**幻读**”。间隙锁的主要目的是为了“**防止其他事务在加锁间隔中插入数据**”。

对于**InnoDB**表，主要有以下几点

* （１）InnoDB的行销是基于索引实现的，如果不通过索引访问数据，InnoDB会使用表锁。
* （２）InnoDB间隙锁机制，以及InnoDB使用间隙锁的原因。
* （３）在不同的隔离级别下，InnoDB的锁机制和一致性读策略不同。
* （４）ＭySQL的恢复和复制对InnoDB锁机制和一致性读策略也有较大影响。
* （５）锁冲突甚至死锁很难完全避免。
  
## 3.MySQL中的事务

​       提到事务肯定会想到事务到ACID，其中多个事务同时执行，就可能出脏读、不可重复读、幻读的问题。为了解决这些问题，就有了**事务隔离级别**的概念。事务隔离级别包括：读未提交(read-uncommitted)、读提交(read-committed)、可重复读(repeatable-read)、可串行化(serializable)。

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-30%2010.47.49.png" width="400" "/> </div><br> 

- 读未提交是指，一个事务还没提交时，它做的变更就能被别的事务看到；**<font color="#FF0000">V1、V2、V3值都是2</font>**
- 读提交是指，一个事务提交之后，它做的变更就能被别的事务看到；**<font color="#FF0000">V1是1、V2、V3是2</font>**
- 可重复读是，一个事务执行过程中看到的数据，总是跟这个事务在启动时看到的数据是一致的。当然在可重复读隔离级别下，未提交变更对其他事务也是不可见的；**<font color="#FF0000">V1、V2是1，V3是2</font>**
- 串行化，顾名思义是对于同一行记录，“写”会加“写锁”，“读”会加“读锁”。当出现读写冲突的时候，后访问的事务必须等待前一个事务执行完成，才能继续执行。图中事务B执行update的时候会被锁住，直到事务A提交之后才可以继续执行，所以从事务A的角度来来说，**<font color="#FF0000">V1、V2是1，V3是2</font>**

### 3.1 事务的配置和启动

- 查看当前事务隔离级别：**<font color="#FF0000">select @@tx_isolation;</font>**
- 设置事务的隔离级别：**<font color="#FF0000">set tx_isolation='repeatable-read';</font>**
- 开启一个事务：**<font color="#FF0000">start transaction;／begin</font>**
- 提交或回滚一个事务：**commit / rollback**
- <font color="#FF0000">set autocommit=0;</font>**，该命令会把这个线程的**自动提交关掉。这样只要执行一个select语句，事务就启动，并不会自动提交，直到主动执行commit或rollback或断开连接。   
- 默认的<font color="#FF0000">set autocommit=1;</font>，可以使用**begin/start transaction**开启事务， 执行**<font color="#FF0000">commit work and chain;</font>**则提交事务并自动启动下一个事务。

### 3.2 事务隔离的实现(可重复读的场景下)

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-30%2011.24.29.png" width="400" "/> </div><br> 

**<font color="#FF0000">MVCC：</font>**

​         **当前值是4，但是查询这条记录的时候，不同时刻启动的事务会有不同的read-view，如图中看到，在视图A、B、C中记录的值分别是1、2、4，同一个记录在系统里会存在多个版本，这就是MVCC(多版本并发控制)。对于 read-view A，要得到 1，就必须将当前值依次执行图中所有的回滚操作得到。**

 **<font color="#FF0000">回滚日志总不能一直保留吧，什么时候删除呢？</font>**

在不需要的时候就会删除，也就是说，系统会判断，当没有事务需要用到回滚日志时，回滚日志会被删除。

 **<font color="#FF0000">使用长事务的弊病? 为什么使用长事务可能拖垮整个库?</font>**

长事务意味着系统里会保存很多老的视图，这些事务随时可能访问数据库中任何数据，所以在事务提交之前，数据库中可能用到的回滚记录都必须保留，长的事务有可能回滚段非常长，这就导致的大量的占用空间。除了存储空间，长事务还会占用锁资源，也可能拖垮整个库。

事务相关问题：

1. 事务的概念是什么?
2. mysql的事务隔离级别读未提交, 读已提交, 可重复读, 串行各是什么意思?
3. 读已提交, 可重复读是怎么通过视图构建实现的?
4. 可重复读的使用场景举例? 对账的时候应该很有用?
5. 事务隔离是怎么通过read-view(读视图)实现的?
6. 并发版本控制(MCVV)的概念是什么, 是怎么实现的?
7. 使用长事务的弊病? 为什么使用常事务可能拖垮整个库?
8. 事务的启动方式有哪几种? 
9. commit work and chain的语法是做什么用的? 
10. 怎么查询各个表中的长事务?
11. 如何避免长事务的出现?

### 3.3 MVCC底层实现

数据表中的一行记录，其实可能有多个**快照版本** (row), 每个版本都有自己的事务row trx_id；

InnoDB利用“MVCC”，实现了秒级别创建快照的能力。

1.事务A开始前，系统里提交事务ID最大是99；

2.事务A、B、C的版本号分别是100、101、102

3.三个事务开始前，(1,1)这一行的数据row trx_id = 99；这样A、B、C三个事务的up_limit_id都是99。

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-30%2014.17.55.png" width="400" "/> </div><br> 

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-30%2011.44.32.png" width="400" "/> </div><br> 

这里Q1查询到的值为3，Q2查询到的值为1；

Q2读的流程(**快照读的过程**)：
- **找到(1,3)的时候，判断出row trx_id=101大于up_limit_id，要不起**
- **接着找到上一个历史版本，一看row trx_id=102，还是要不起**
- **在往前找，终于找到了(1，1)，它的row trx_id=90；是可承认的数据，所以返回1**

**思考：   
   如果有一个事务id为98的事务，更新之后在提交，那么，事务A会读到98这个事务的提交更新呢？  
   答案：事务A(99)是不会看到98这个事务提交的更新的，所以事务启动的时候还要保存“正在执行的所有事务ID的列表”，如果有一个row tx_id在这个列表中，
   也要不可见**

Q1读的流程(**当前读的过程**)：

- **更新的时候都是先读后写，而这个读，只能读取当前值，称“当前读”**

- **因此，在更新的时候，当前读取到的数据是 (1,2)，更新后生成了新版本的数据(1,3)，这个新版本的row trx_id是101**
- **在执行Q1语句的时候，一看自己的版本号是101，最新数据的版本号也是101，所以查到的值为3**
### 3.4 可重复读和以提交读的区别 
<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-11-30%2014.08.21.png" width="400" "/> </div><br> 

  * 在可重复读的隔离级别下，只需要在事务开始时找到那个up_limit_id，之后事务里的其他查询都共用这个up_limit_id；
  * 在读提交隔离级别下，事务中每一个语句执行前都会重新计算一次up_limit_id的值。
### 3.5 快照读和当前读
  * **快照读**  
使用 MVCC 读取的是快照中的数据，这样可以减少加锁所带来的开销。
```sql
select * from table ...;
```
  * **当前读**  
读取的是最新的数据，需要加锁。以下第一个语句需要加 S 锁，其它都需要加 X 锁。

```sql
select * from table where ? lock in share mode;
select * from table where ? for update;
insert;
update;
delete;
```

四种隔离级别下执行CRUD时加锁情况：

(1) **读未提交**：select不加锁，可能出现读脏；

(2) **读提交(RC)**：普通select快照读，锁select /update /delete 会使用记录锁，可能出现不可重复读；

(3) **可重复读(RR)**：普通select快照读，锁select /update /delete 根据查询条件情况，会选择记录锁，或者间隙锁/临键锁，以防止读取到幻影记录；

(4) **串行化**：**只有串行化方式所有语句都加锁！** select隐式转化为select ... in share mode，会被update与delete互斥；

### 3.6 快照读在RR和RC下有何差异

t(id PK, name);

表中有三条记录：
1, shenjian
2, zhangsan
3, lisi

* case 1，两个并发事务A，B执行的时间序列如下（A先于B开始，B先于A结束）：

A1: start transaction;
​         B1: start transaction;
A2: select * from t;
​         B2: insert into t values (4, wangwu);
A3: select * from t;
​         B3: commit;
A4: select * from t;

**提问1：事务隔离级别是RR，A2、A3、A4分别读出的结果是什么？答案：(1、2、3)、(1、2、3)、(1、2、3)**

**提问2:   事务隔离级别是RC，A2、A3、A4分别读出的结果是什么？答案：(1、2、3)、(1、2、3)、(1、2、3、4)**

* case 2，仍然是并发的事务A与B（A先于B开始，B先于A结束）：

A1: start transaction;
​         B1: start transaction;
​         B2: insert into t values (4, wangwu);
​         B3: commit;
A2:  select * from t;

**提问1：RR情况下，A2的结果是什么？答案：(1、2、3)**

**提问2：RC情况下，A2的结果是什么？答案：(1、2、3、4)**

* case 3，

  事务A先执行，并且处于未提交状态：

  delete from t where id=40;

  事务A想要**删除一条不存在的记录**。 

  事务B后执行：

  insert into t values(40, ‘c’);

  事务B想要**插入一条主键不冲突的记录**。

**提问1：事务B是否阻塞？答案：是阻塞的，事务A执行delete会持有该行锁(X锁)**

**提问2：如果事务B阻塞，锁如何加在一条不存在的记录上？**

**提问3：事务的隔离级别，索引类型是否对问题1和2有影响？不会有影响**

* case 3，RR

  事务A**先**执行，还**未提交**：

  insert into t(name) values(xxx);

  事务B**后**执行：

  insert into t(name) values(ooo);

**提问1：事务B会不会被阻塞？答案：没有用到索引不会阻塞**

case 4，RR

事务A先执行，查询了一些记录，还未提交：

select * from t where id>10;

事务B后执行，在10与20两条记录中插入了一行：

insert into t values(11, xxx);

**提问1：事务B会不会阻塞？不会阻塞**

**提问2：如果事务A加上for update，事务B会不会阻塞？会阻塞，因为是加了临键锁(Next-Key Lock)，防止幻读。**

case 5，RR：**select不存在的一行加排他锁，那么也会把该行锁住！**

| 事务A  | 事务B |
| --------   | -----:   |
| begin;  | begin; |
| select * from tx where id = 4 for update; //(**id=4这行记录不存在，那么也会持有行锁！**) |       |
|  | update    tx  set  num =  4 where id = 4;//(**由于事务A持有行锁，所以一直处于阻塞状态，知道事务A commit** |

### 3.7 面试问题

1.为什么使用索引？

**避免全表扫描**，类似于字典。

2.什么样的信息能够成为索引？

主键等。 

3.如何定位并优化SQL？

**1）根据慢日志定位慢查询sql；执行show variables like '%quer%'，找到查询日志的log文件查找到慢sql**

**2）使用explain等工具分析sql；**

**2）修改SQL，尽量让SQL走索引。** 

4.最左前缀匹配原则？

 最左前缀匹配原则是非常重要的原则，mysql会之一匹配直*到遇到范围查询(>、<、between、like)就**停止匹配！**，比如**（1）a=3 and b = 4 and c > 5 and d = 6，如果建立(a,b,c,d)顺序的索引，只能用(a,b,c)索引，d是用不到的索引，如果建立(a,b,d,c)则都可以用到，a,b,d的顺序可以任意调整；**。

**（2）=和in可以乱序，比如a=1 and b = 2 and c = 3建立(a,b,c)可以任意顺序，MySQL查询优化器会帮助你优化成索引可以识别的样式。**

 5.索引是建立的越多越好呢？

数据量小不需要建立索引，建立会增加额外的开销；

数据变更需要维护索引；

更多索引意味着需要更多的空间。

6.MyISM和InnoDB锁的区别？

7.explain在之前版本只支持select，5.6之后才支持update/insert等

8.二阶段锁？

在同一个事务里有加锁的语句，commit之后会统一的解锁。

9.当在事务A中给一行加共享锁，事务B在update更新这个行时会**阻塞**直到A释放S锁。反之也是一样的！

10.当事务A给一行加共享锁，事务B给这行加共享锁是**不会阻塞**的！

| 当前锁模式/是否兼容/请求锁模式 | X    | S          |
| :----------------------------: | ---- | ---------- |
|               X                | 冲突 | 冲突       |
|               S                | 冲突 | **兼容！** |

11.MyISAM适合的场景？(1)频繁的执行全表count语句；(2)对数据进行增删改频率不高，查询频繁；(3)没有事务。

12.InnoDB适合的场景？(1)数据增删改都相当频繁；(2)可靠性要求较高，要求支持事务。

13.幻读样例？

| 事务A                                                        | 事务A                                 |
| ------------------------------------------------------------ | ------------------------------------- |
| begin；                                                      | begin;                                |
| select * from tx lock in share mode;                         |                                       |
|                                                              | insert into tx (id,num) values (4,4); |
| update tx set num = 2;(**会看到有4行与影响，之前明明是3行，这就造成的幻读！**) |                                       |
