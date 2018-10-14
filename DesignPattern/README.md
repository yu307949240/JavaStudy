* [一、创建型设计模式](#一创建型设计模式)
  * [1.简单工厂](#1简单工厂)
  * [2.工厂方法](#2工厂方法)
  * [3.抽象工厂](#3抽象工厂)
  * [4.建造者](#4建造者)
  * [5.单例模式](#5单例模式)
  * [6.原型模式](#6原型模式)
* [二、结构型设计模式](#二结构型设计模式)
  * [1.外观](#1外观)
  * [2.装饰者](#2装饰者)
  * [3.适配器](#3适配器)
  * [4.享元](#4享元)
  * [5.组合](#5组合)
  * [6.桥接](#6桥接)
  * [7.代理](#7代理)

# 一、创建型设计模式

## 1.简单工厂
* 简单工厂-适用场景  
1.工厂类负责创建的对象比较少；  
2.客户端（应用层）只知道传入工厂类的参数，对于如何创建对象（逻辑）不关心。  
* 简单工厂-优点  
1.只需要传入一个正确的参数，就可以获取所需要的对象，而无需知道其创建细节。  
* 简单工厂-缺点  
1.工厂类的职责相对过重，增加新的产品，需要修改工厂类的判断逻辑，违背开闭原则。  
应用到到的类：  
Calendar
Logger  
### Implementation

```java
public class VideoFactory {
    //用反射来实现简单工厂
    public Video getVideo(Class c){
        Video video = null;
        try {
            video = (Video) Class.forName(c.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return video;
    }
}
public abstract class Video {
    public abstract void produce();
}

class JavaVideo extends Video{
    @Override
    public void produce() {
        System.out.println("学习Java课程");
    }
}
```
## 2.工厂方法
* 工厂方法-定义与类型   
定义：定义一个创建对象的接口，但让实现这个接口的类来决定实力化哪个类，工厂方法让类的实例化推迟到子类中进行  
类型：创建型  
* 工厂方法-适用场景  
1.创建对象需要大量重复的代码  
2.客户端（应用层）不依赖于产品类实例如何被创建，实现等细节。  
3.一个类通过其子类来指定创建哪个对象。  
* 工厂方法-优点：  
1.用户只需要关心所需产品的对应工厂，无需关心创建细节。  
2.加入新产品符合开笔原则，提高扩展性。  
* 工厂方法-缺点  
1.类的个数容易过多，增加复杂度  
2.增加类系统的抽象性和理解程度  

### Implementation

```java
public abstract class VideoFactory {
    public abstract Video getVideo();
    public static void main(String[] args) {
        VideoFactory videoFactory = new JavaVideoFactory();
        Video video = videoFactory.getVideo();
        video.produce();
    }
}

class JavaVideoFactory extends VideoFactory {
    @Override
    public Video getVideo() {
        return new JavaVideo();
    }
}

abstract class Video {
    public abstract void produce();
}

class JavaVideo extends Video{
    @Override
    public void produce() {
        System.out.println("学习Java课程");
    }
}
```
### JDK

- [java.util.Calendar](http://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html#getInstance--)
- [java.util.ResourceBundle](http://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html#getBundle-java.lang.String-)
- [java.text.NumberFormat](http://docs.oracle.com/javase/8/docs/api/java/text/NumberFormat.html#getInstance--)
- [java.nio.charset.Charset](http://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html#forName-java.lang.String-)
- [java.net.URLStreamHandlerFactory](http://docs.oracle.com/javase/8/docs/api/java/net/URLStreamHandlerFactory.html#createURLStreamHandler-java.lang.String-)
- [java.util.EnumSet](https://docs.oracle.com/javase/8/docs/api/java/util/EnumSet.html#of-E-)
- [javax.xml.bind.JAXBContext](https://docs.oracle.com/javase/8/docs/api/javax/xml/bind/JAXBContext.html#createMarshaller--)


## 3.抽象工厂
* 抽象工厂-定义与类型  
定义：1.抽象工厂模式提供一个创建一系列相关或相互依赖对象的接口。  
     2.无需指定他们的具体类型  
类型：创建型  
* 抽象工厂-适用场景  
1.客户端（应用层）不依赖于产品类实例如何被创建，实现等细节  
2.强调一系列相关产品对象（属于同一产品族）一起使用创建对象需要大量的重复代码。  
3.提供一个产品类的库，所有的产品以同样的接口出现，从而使客户端不依赖于具体实现  
* 抽象工厂-优点  
1.具体产品在应用层代码隔离，无须关心创建细节  
2.将一个系列的产品族统一到一起创建  
* 抽象工厂-缺点  
1.规定类所有可能被创建的产品集合，产品族中扩展新的产品困难，需要修改抽象工厂的接口  
2.增加了系统的抽象性和理解难度  

### Class Diagram

抽象工厂模式创建的是对象家族，也就是很多对象而不是一个对象，并且这些对象是相关的，也就是说必须一起创建出来。而工厂方法模式只是用于创建一个对象，这和抽象工厂模式有很大不同。

抽象工厂模式用到了工厂方法模式来创建单一对象，AbstractFactory 中的 createProductA() 和 createProductB() 方法都是让子类来实现，这两个方法单独来看就是在创建一个对象，这符合工厂方法模式的定义。

至于创建对象的家族这一概念是在 Client 体现，Client 要通过 AbstractFactory 同时调用两个方法来创建出两个对象，在这里这两个对象就有很大的相关性，Client 需要同时创建出这两个对象。

从高层次来看，抽象工厂使用了组合，即 Cilent 组合了 AbstractFactory，而工厂方法模式使用了继承

### Implementation

```java
public class AbstractProductA {
}
```

```java
public class AbstractProductB {
}
```

```java
public class ProductA1 extends AbstractProductA {
}
```

```java
public class ProductA2 extends AbstractProductA {
}
```

```java
public class ProductB1 extends AbstractProductB {
}
```

```java
public class ProductB2 extends AbstractProductB {
}
```

```java
public abstract class AbstractFactory {
    abstract AbstractProductA createProductA();
    abstract AbstractProductB createProductB();
}
```

```java
public class ConcreteFactory1 extends AbstractFactory {
    AbstractProductA createProductA() {
        return new ProductA1();
    }

    AbstractProductB createProductB() {
        return new ProductB1();
    }
}
```

```java
public class ConcreteFactory2 extends AbstractFactory {
    AbstractProductA createProductA() {
        return new ProductA2();
    }

    AbstractProductB createProductB() {
        return new ProductB2();
    }
}
```

```java
public class Client {
    public static void main(String[] args) {
        AbstractFactory abstractFactory = new ConcreteFactory1();
        AbstractProductA productA = abstractFactory.createProductA();
        AbstractProductB productB = abstractFactory.createProductB();
        // do something with productA and productB
    }
}
```

### Examples
java.sql.Connection  
SqlSessionFactory  

### JDK

- [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
- [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
- [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)


## 4.建造者
* 建造者-定义与类型  
定义：1.将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示  
     2.用户只需指定需要建造的类型就可以得到它们，建造过程和细节不需要知道  
类型：创建型  
* 建造者-适用场景  
1.如果一个对象有非常复杂的内部结构(很多属性)  
2.想把复杂对象的创建和使用分离  
* 建造者-优点  
1.封装性好，创建和使用分离  
2.扩展性好，建造类之间独立，一定程度上解耦  
* 建造者-缺点  
1.产生多余的Builder对象  
2.产品内部发生变化，建造者都要修改，成本较大  
建造者模式更注重于方法的调用顺序，而工厂模式注重于创建产品。  
StringBuilder和StringBuffer是标准的建造者模式实现的！！
ImmutableSet
SqlSessionFactoryBuilder

### Implementation

以下是一个简易的 StringBuilder 实现，参考了 JDK 1.8 源码。

```java
public class AbstractStringBuilder {
    protected char[] value;

    protected int count;

    public AbstractStringBuilder(int capacity) {
        count = 0;
        value = new char[capacity];
    }

    public AbstractStringBuilder append(char c) {
        ensureCapacityInternal(count + 1);
        value[count++] = c;
        return this;
    }

    private void ensureCapacityInternal(int minimumCapacity) {
        // overflow-conscious code
        if (minimumCapacity - value.length > 0)
            expandCapacity(minimumCapacity);
    }

    void expandCapacity(int minimumCapacity) {
        int newCapacity = value.length * 2 + 2;
        if (newCapacity - minimumCapacity < 0)
            newCapacity = minimumCapacity;
            if (newCapacity < 0) {
            if (minimumCapacity < 0) // overflow
                throw new OutOfMemoryError();
            newCapacity = Integer.MAX_VALUE;
        }
        value = Arrays.copyOf(value, newCapacity);
    }
}
```

```java
public class StringBuilder extends AbstractStringBuilder {
    public StringBuilder() {
        super(16);
    }

    @Override
    public String toString() {
        // Create a copy, don't share the array
        return new String(value, 0, count);
    }
}
```

```java
public class Client {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        final int count = 26;
        for (int i = 0; i < count; i++) {
            sb.append((char) ('a' + i));
        }
        System.out.println(sb.toString());
    }
}
```

```html
abcdefghijklmnopqrstuvwxyz
```

### JDK

- [java.lang.StringBuilder](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html)
- [java.nio.ByteBuffer](http://docs.oracle.com/javase/8/docs/api/java/nio/ByteBuffer.html#put-byte-)
- [java.lang.StringBuffer](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuffer.html#append-boolean-)
- [java.lang.Appendable](http://docs.oracle.com/javase/8/docs/api/java/lang/Appendable.html)
- [Apache Camel builders](https://github.com/apache/camel/tree/0e195428ee04531be27a0b659005e3aa8d159d23/camel-core/src/main/java/org/apache/camel/builder)

## 5.单例模式
* 单例模式-定义与类型  
定义：保证一个类仅有一个实力，并提供一个全局访问点  
类型：创建型  
* 单例模式-适用场景  
想确保任何情况下都绝对只有一个实例  
线程池
数据库连接池
* 单例模式-优点  
在内存里只有一个实例，减少了内存开销  
可以避免对资源的多重占用  
设置全局访问点，严格控制访问  
* 单例模式-缺点  
没有接口，扩展困难 
* 单例模式-重点  
1.私有构造器  
2.线程安全  
3.延迟加载(懒汉式和恶汉式最大区别)  
4.序列化和反序列化安全！！  
5.反射！！  
spring中用到的单例的类：AbstractFactoryBean  
mybatis: ErrorContext，保证了每个线程各自的数据，不同的线程，每个线程自己的上下文自己保存好，在每个线程是唯一的。  
* 单例模式-实用技能  
1.反编译  
2.内存原理  
3.多线程Debug  
单例模式-相关设计模式  
单例模式和工厂模式  
单例模式和享元模式

### Implementation

#### Ⅰ 懒汉式-线程不安全   

以下实现方式，如果是多线程执行的时候，多个线程同时进入`if(lazySingleton == null)`，执行多次`lazySingleton = new LazySingleton();`  
将导致实例化多次 lazySingleton。  

```java
/**
 * 单例模式-懒汉式，注重延迟加载，只有使用该类的时候才进行初始化。
 */
public class LazySingleton {
    private static LazySingleton lazySingleton = null;
    private LazySingleton(){
        if(lazySingleton != null){
            throw new RuntimeException("单例构造器禁止反射调用");
        }
    }
    public synchronized static LazySingleton getInstance(){
        if(lazySingleton == null){
            lazySingleton = new LazySingleton();
         }
        return lazySingleton;
    }
}
```
#### Ⅱ 饿汉式-线程安全

由于线程不安全的方式导致instance被实例化多次，采取直接实例化 uniqueInstance 的方式就不会产生线程不安全问题。  

但是直接实例化的方式(饿汉式)也丢失了延迟实例化带来的节约资源的好处。  
```java
/**
 * 单例模式-恶汉式
 */
public class HungrySingleton implements Serializable,Cloneable{
    private final static HungrySingleton hungrySingleton;//声明为final的静态变量，必须在类加载完成时完成赋值
    static{
        hungrySingleton = new HungrySingleton();
    }
    private HungrySingleton(){
        if(hungrySingleton != null){
            throw new RuntimeException("单例构造器禁止反射调用");
        }
    }
    public static HungrySingleton getInstance(){
        return hungrySingleton;
    }

    private Object readResolve(){
        return hungrySingleton;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
```

#### Ⅲ 懒汉式-线程安全

```java
/**
 * 单例模式-懒汉式，注重延迟加载，只有使用该类的时候才进行初始化。
 */
public class LazySingleton {
    private static LazySingleton lazySingleton = null;
    private LazySingleton(){
        if(lazySingleton != null){
            throw new RuntimeException("单例构造器禁止反射调用");
        }
    }
    public synchronized static LazySingleton getInstance(){
        if(lazySingleton == null){
            lazySingleton = new LazySingleton();
         }
        return lazySingleton;
    }
}
```

#### Ⅳ 双重校验锁-线程安全

双重校验先判断instance是否已经被实例化，如果没有被实例化，那么才对实例化语句进行加锁。

```java
/**                                                                            
 * 单例模式-懒加载，双重检查机制                                                             
 */                                                                            
public class LazyDoubleCheckSingleton {                                        
    private volatile static LazyDoubleCheckSingleton lazyDoubleCheckSingleton; 
                                                                               
    private LazyDoubleCheckSingleton(){}                                       
                                                                               
    public static LazyDoubleCheckSingleton getInstance() {                     
        if(lazyDoubleCheckSingleton == null){                                  
            synchronized (LazyDoubleCheckSingleton.class){                     
                if(lazyDoubleCheckSingleton == null){                          
                    lazyDoubleCheckSingleton = new LazyDoubleCheckSingleton(); 
                    // 1.分配内存                                                  
                    // 2.初始化对象                                                 
                    // 3.将lazyDoubleCheckSingleton指向内存空间                                             
                }                                                              
            }                                                                  
        }                                                                      
        return lazyDoubleCheckSingleton;                                       
    }                                                                  
```

考虑下面的实现，也就是只使用了一个 if 语句。在 lazyDoubleCheckSingleton == null 的情况下，如果两个线程都执行了 if 语句，那么两个线程都会进入 if 语句块内。虽然在 if 语句块内有加锁操作，但是两个线程都会执行 `lazyDoubleCheckSingleton = new LazyDoubleCheckSingleton();` 这条语句，只是先后的问题，那么就会进行两次实例化。因此必须使用双重校验锁，也就是需要使用两个 if 语句。

```java
synchronized (LazyDoubleCheckSingleton.class){                     
                if(lazyDoubleCheckSingleton == null){                          
                    lazyDoubleCheckSingleton = new LazyDoubleCheckSingleton(); 
                    // 1.分配内存                                                  
                    // 2.初始化对象                                                 
                    // 3.将lazyDoubleCheckSingleton指向内存空间                                             
                }  
```
lazyDoubleCheckSingleton 采用volatile关键字修饰也是很有必要的，`lazyDoubleCheckSingleton = new LazyDoubleCheckSingleton();`这段代码  
分三步执行。    
1.为lazyDoubleCheckSingleton分配内存空间  
2.初始化 lazyDoubleCheckSingleton  
3.将lazyDoubleCheckSingleton指向分配的内存地址  
但是由于 JVM 具有指令重排的特性，执行顺序有可能变成 1>3>2。指令重排在单线程环境下不会出先问题，但是在多线程环境下会导致一个线程获得还没有初始化的实例。例如，线程 T<sub>1</sub> 执行了 1 和 3，此时 T<sub>2</sub> 调用 getInstance() 后发现 lazyDoubleCheckSingleton 不为空，因此返回 lazyDoubleCheckSingleton，但此时 lazyDoubleCheckSingleton 还未被初始化。  

使用 volatile 可以禁止 JVM 的指令重排，保证在多线程环境下也能正常运行。

#### Ⅴ 静态内部类实现

```java
/**
 * 单例模式-懒汉式，静态内部类-基于类初始化的延迟加载解决方案及原理解析
 */
public class StaticInnerClassSingleton {
    /**
     * 静态内部类在于哪个线程拿到类的初始化锁，
     */
    private static class InnerClass{
        private static StaticInnerClassSingleton staticInnerClassSingleton = new StaticInnerClassSingleton();
    }
    public static StaticInnerClassSingleton getInstance(){
        return InnerClass.staticInnerClassSingleton;
    }

    private StaticInnerClassSingleton(){
        //单例模式-反射攻击解决方案
        if(InnerClass.staticInnerClassSingleton != null){
            throw  new RuntimeException("单例构造器禁止反射调用");
        }
    }
}
```

#### Ⅵ 枚举实现

```java
/**
 * 单例模式-Enum枚举单例
 */
public enum EnumInstance {
    INSTANCE{
        protected void printTest(){
            System.out.println("Geely Print Test");
        }
    };
    protected abstract void printTest();
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static EnumInstance getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        EnumInstance instance = EnumInstance.getInstance();
        instance.setData(new Object());
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("singleton_file"));
        oos.writeObject(instance);

        File file = new File("singleton_file");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        EnumInstance newInstance = (EnumInstance) ois.readObject();
        System.out.println(instance.getData());
        System.out.println(newInstance.getData());
        System.out.println(instance.getData() == newInstance.getData());
    }
}
```

该实现可以防止反射攻击。在其它实现中，通过 setAccessible() 方法可以将私有构造函数的访问级别设置为 public，然后调用构造函数从而实例化对象，如果要防止这种攻击，需要在构造函数中添加防止多次实例化的代码。该实现是由 JVM 保证只会实例化一次，因此不会出现上述的反射攻击。

### Examples

- Logger Classes
- Configuration Classes
- Accesing resources in shared mode
- Factories implemented as Singletons

### JDK

- [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)
- [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
- [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)

## 6.原型模式
* 原型模式-定义与类型  
定义：值原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。  
特点：不需要知道任何的创建细节，不调用构造函数。  
类型：创建型  
* 原型模式-适用场景  
1.类初始化消耗比较多的资源  
2.new产生的一个对象需要非常繁琐的过程（数据准备，访问权限）  
3.构造函数比较复杂  
4.循环体中生产大量对象时  
* 原型模式-优点  
1.性能比直接new一个对象的性能高  
2.简化创建过程  
* 原型模式-缺点  
1.必须配置克隆方法  
2.对克隆复杂对象或克隆出的对象进行复杂改造时，容易引入风险  
3.深拷贝和浅拷贝要运用得当  

# 二、结构型设计模式

## 1.外观

* 外观-定义与类型  
定义：又叫门面模式，提供了一个统一的接口，用来访问系统中的一群接口；外观模式定义的一个高层接口，让子系统更容易调用  
类型：结构型  
* 外观-适用场景  
子系统越来越复杂，增加外观模式提供简单调用接口；  
构建多层系统结构，利用外观对喜庆作为每层的入口，简化层间调用。  
* 外观-优点  
简化了调用过程，无需了解深入子系统，防止带来风险；  
减少系统依赖，松散耦合；  
更好的划分访问层次；  
符合迪米特法则，即最少知道原则。  
* 外观-缺点 
增加子系统，扩展子系统行为容易引入风险；  
不符合开闭原则。  
* 外观-相关设计模式  
外观模式和中介者模式：外观模式关注系统之间的交互；中介者模式关注系统系统内部各个子模块的交互。  
单例模式和单例模式  
外观模式和抽象工厂模式  

### Intent

提供了一个统一的接口，用来访问子系统中的一群接口，从而让子系统更容易使用。

### Implementation

```java

/**
 * 外观模式（门面模式）; https://www.cnblogs.com/lthIU/p/5860607.html
 */
public class Computer {
    private CPU cpu;
    private Memory memory;
    private Disk disk;

    public Computer(){
        cpu = new CPU();
        memory = new Memory();
        disk = new Disk();
    }

    public void start(){
        cpu.start();
        memory.start();
        disk.start();
    }

    public void shutDown(){
        cpu.shutDown();
        memory.shutDown();
        disk.shutDown();
    }

}

class CPU {
    public void start() {
        System.out.println("cpu is start...");
    }

    public void shutDown() {
        System.out.println("CPU is shutDown...");
    }
}

class Memory{
    public void start() {
        System.out.println("Memory is start...");
    }

    public void shutDown() {
        System.out.println("Memory is shutDown...");
    }
}

class Disk{
    public void start() {
        System.out.println("Disk is start...");
    }

    public void shutDown() {
        System.out.println("Disk is shutDown...");
    }
}
```

### Examples

org.springframework.jdbc.support.JdbcUtils   

### 设计原则

最少知道原则：只和你的密友谈话。也就是说客户对象所需要交互的对象应当尽可能少。

## 2.装饰者

* 装饰者-定义与类型  
定义：在不改变原有对象的基础之上，将功能附加到对象上  
     提供了比继承更有弹性的替代方案（扩展原有对象功能）  
类型：结构型  
* 装饰者-适用场景  
1.扩展一个类的功能或给一个类添加附加职责；  
2.动态的给一个对象添加功能，这些功能可以在动态的撤销。  
* 装饰者-优点  
继承的有力补充，比继承灵活，不改变原有对象情况下给一个对象扩展功能；  
通过使用不同的装饰类以及这些装饰类的排列组合，可以实现不同的效果；  
符合开闭原则。  
* 装饰者-缺点  
会出现更多的代码，更多的类，增加程序的复杂性；  
动态装饰时，多层装饰时会更复杂。  
装饰者-相关设计模式  
装饰者模式和代理模式  
装饰者模式和适配器模式  

```java
/**
 * 装饰器模式; https://www.cnblogs.com/zhangtianq/p/6091047.html
 * create by yyq on 2018/08/18
 */

/***
 * 抽象组件
 */
public interface ICar {
    void move();
}

// ConcreteComponent 具体构件角色（真实对象）
class Car implements ICar{

    @Override
    public void move() {
        System.out.println("陆地上跑");
    }
}

class SuperCar implements ICar{
    private ICar car;
    public SuperCar(ICar car){
        this.car = car;
    }

    @Override
    public void move() {
        car.move();
    }
}

// ConcreteDecorator 具体装饰角色
class FlyCar extends SuperCar{

    public FlyCar(ICar car) {
        super(car);
    }

    public void fly(){
        System.out.println("天上飞");
    }
    @Override
    public void move(){
        super.move();
        fly();
    }
}

// ConcreteDecorator 具体装饰角色
class WaterCar extends SuperCar{

    public WaterCar(ICar car) {
        super(car);
    }

    public void swim(){
        System.out.println("水里游");
    }
    @Override
    public void move(){
        super.move();
        swim();
    }
}

// ConcreteDecorator 具体装饰角色
class AICar extends SuperCar{

    public AICar(ICar car) {
        super(car);
    }

    public void autoMove(){
        System.out.println("自动跑");
    }
    @Override
    public void move(){
        super.move();
        autoMove();
    }
}
```

### 设计原则

类应该对扩展开放，对修改关闭：也就是添加新功能时不需要修改代码。饮料可以动态添加新的配料，而不需要去修改饮料的代码。

不可能把所有的类设计成都满足这一原则，应当把该原则应用于最有可能发生改变的地方。

### JDK

- java.io.BufferedInputStream(InputStream)
- java.io.DataInputStream(InputStream)
- java.io.BufferedOutputStream(OutputStream)
- java.util.zip.ZipOutputStream(OutputStream)
- java.util.Collections#checked[List|Map|Set|SortedSet|SortedMap]()

## 3.适配器

### Intent

把一个类接口转换成另一个用户需要的接口。

### Implementation

#### 类适配器



#### 对象适配器

```java
/**
 * 适配器模式-对象适配器
 * @author yyq
 * @since 18/09/16
 */
public class PowerAdapter implements DC5 {
    private AC220 ac220 = new AC220();
    @Override
    public int outputDC5V() {
        int adapterInput = ac220.outputAc220();

        //变压器
        int adapterOutput = adapterInput/44;
        System.out.println("使用PowerAdapter输入AC："+adapterInput+"V"+" 输出DC："+adapterOutput+"V");
        return adapterOutput;
    }
    public static void main(String[] args) {
        DC5 dc5 = new PowerAdapter();
        dc5.outputDC5V();
    }
}

interface DC5 {
    int outputDC5V();
}

class AC220 {
    public int outputAc220(){
        int output = 220;
        System.out.println("输出交流电"+output+"V");
        return output;
    }
}
```
### JDK

- [java.util.Arrays#asList()](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList%28T...%29)
- [java.util.Collections#list()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#list-java.util.Enumeration-)
- [java.util.Collections#enumeration()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-)
- [javax.xml.bind.annotation.adapters.XMLAdapter](http://docs.oracle.com/javase/8/docs/api/javax/xml/bind/annotation/adapters/XmlAdapter.html#marshal-BoundType-)



## 4.享元

* 享元-定义与类型  
  定义：提供了减少对象数量从而改善应用所需的对象结构的方式；  
     运用共享技术有效地支持大量细粒度的对象。  
  类型：结构型  
* 享元-使用场景  
  1.常常用于系统底层的开发，以便解决系统的性能问题；  
  2.系统有大量相似对象，需要缓冲池的场景。  
  如果对象的复用度很高可以考虑享元模式  
  String类型
  数据库连接池
* 享元-优点  
  1.减少对象的创建，降低内存中对象的数量，降低系统的内存，提高效率；  
  2.减少内存之外的其他资源占用。  
* 享元-缺点  
  关注内／外部状态，专注线程安全问题；  
  使系统，程序的逻辑复杂化。  
* 享元-扩展  
  1.内部状态  
  2.外部状态  
* 享元-相关设计模式  
  享元模式和代理模式  
  享元模式和单例模式（容器单例就是二者的结合）  

### Intent

将对象组合成树形结构来表示“整体/部分”层次关系，允许用户以相同的方式处理单独对象和组合对象。

### Class Diagram

- Flyweight：享元对象
- IntrinsicState：内部状态，享元对象共享内部状态
- ExtrinsicState：外部状态，每个享元对象的外部状态不同



### Implementation

```java
public interface Employee {
    void report();
}
```

```java
public class EmployeeFactory {
    private static final Map<String,Employee> EMPLOYEE_MAP = new HashMap<String,Employee>();

    public static Employee getManager(String department){
        Manager manager = (Manager) EMPLOYEE_MAP.get(department);
        if(manager==null){
            manager = new Manager(department);
            System.out.print("创建部门经理："+department);
            String reportContent = department+"部门汇报：此次报告的主要内容是......";
            manager.setReportContent(reportContent);
            System.out.println(" 创建报告："+department);
            EMPLOYEE_MAP.put(department,manager);
        }
        return manager;
    }
}
```

```java
public class Manager implements Employee {
    @Override
    public void report() {
        System.out.println(reportContent);
    }
    private String department;
    private String reportContent;

    public void setReportContent(String reportContent){
        this.reportContent = reportContent;
    }

    public Manager(String department){
        this.department = department;
    }
}
```

```
/**
 * 享元模式
 * @author yyq
 * @since 18/09/16
 */
public class Test {
    private static final String departments[] = {"RD","QA","PM","BD"};

    /*public static void main(String[] args) {
        for(int i=0;i<10;i++){
            String department = departments[(int) (Math.random() * departments.length)];

            Manager manager = (Manager) EmployeeFactory.getManager(department);

            manager.report();
        }
    }*/
    //测试Integer
    public static void main(String[] args) {
        Integer a = Integer.valueOf(100);
        Integer b = 100;

        Integer c = Integer.valueOf(1000);
        Integer d = 1000;

        System.out.println("a==b: "+(a==b));
        System.out.println("c==d: "+(c==d));
    }
}
```



### JDK

Java 利用缓存来加速大量小对象的访问时间。

- java.lang.Integer#valueOf(int)
- java.lang.Boolean#valueOf(boolean)
- java.lang.Byte#valueOf(byte)
- java.lang.Character#valueOf(char)



## 5.组合

* 组合-定义与类型  
  定义：将对象组合成树形结构以表示"部分-整体"的层次结构  
  组合模式使客户端对单个对象和组合对象保持一致的方式处理  
  类型：结构型  

* 组合-适用场景  
  希望客户端可以忽略组合对象与单个对象的差异时   
  处理一个树形结构时  

* 组合-优点  
  清楚的定义了分层次的复杂对象，表示对象的全部或部分层次  
  让客户端忽略了层次的差异，方便对整个层次结构进行控制  
  简化客户端代码  
  符合开闭原则  

* 组合-缺点  
  限制类型时比较复杂  
  使设计变的得更加抽象  

* 组合-相关设计模式  
  组合模式和访问者模式   

  ###Implementation 

  ```java
  public abstract class CatalogComponent {
      public void add(CatalogComponent catalogComponent){
          throw new UnsupportedOperationException("不支持添加操作");
      }
      public void remove(CatalogComponent catalogComponent){
          throw new UnsupportedOperationException("不支持删除操作");
      }
      public String getName(CatalogComponent catalogComponent){
          throw new UnsupportedOperationException("不支持获取名称操作");
      }
      public double getPrice(CatalogComponent catalogComponent){
          throw new UnsupportedOperationException("不支持获取价格加操作");
      }
      public void print(){
          throw new UnsupportedOperationException("不支持打印操作");
      }
  }
  ```

  ```java
  public class Course extends CatalogComponent {
      private String name;
      private double price;
  
      public Course(String name, double price) {
          this.name = name;
          this.price = price;
      }
  
      @Override
      public String getName(CatalogComponent catalogComponent) {
          return this.name;
      }
  
      @Override
      public double getPrice(CatalogComponent catalogComponent) {
          return this.price;
      }
  
      @Override
      public void print() {
          System.out.println("Course Name: "+name+"Price:"+price);
      }
  }
  ```

```java
public class CourseCatalog extends CatalogComponent {
    private List<CatalogComponent> items = new ArrayList<CatalogComponent>();

    private String name;
    private Integer level;

    public CourseCatalog(String name,Integer level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public String getName(CatalogComponent catalogComponent) {
        return this.name;
    }

    @Override
    public void add(CatalogComponent catalogComponent) {
        items.add(catalogComponent);
    }

    @Override
    public void remove(CatalogComponent catalogComponent) {
        items.remove(catalogComponent);
    }

    @Override
    public void print() {
        System.out.println(this.name);
        for(CatalogComponent catalogComponent : items){
            if(this.level!=null){
                for(int i=0;i<this.level;i++){
                    System.out.print("  ");
                }
            }
            catalogComponent.print();
        }
    }
}
```

```java
/**
 * 组合模式
 *
 * @author yyq
 * @since 18/09/20
 */
public class Test {
    public static void main(String[] args) {
        CatalogComponent linuxCourse = new Course("Linux课程", 11);

        CatalogComponent windowsCourse = new Course("Windows课程", 11);

        CatalogComponent javaCourseCatalog = new CourseCatalog("Java课程目录", 2);

        CatalogComponent mmallCourse1 = new Course("Java电商1期", 55);
        CatalogComponent mmallCourse2 = new Course("Java电商2期", 66);
        CatalogComponent designPattern = new Course("Java设计模式", 77);

        javaCourseCatalog.add(mmallCourse1);
        javaCourseCatalog.add(mmallCourse2);
        javaCourseCatalog.add(designPattern);

        CatalogComponent imoocMainCourselog = new CourseCatalog("慕课网课程主目录", 1);
        imoocMainCourselog.add(linuxCourse);
        imoocMainCourselog.add(windowsCourse);
        imoocMainCourselog.add(javaCourseCatalog);

        imoocMainCourselog.print();
    }
}
```



### Example

org.apache.ibtais.scripting.xmltags.SqlNode 



### JDK

- javax.swing.JComponent#add(Component)

- java.awt.Container#add(Component)

- java.util.Map#putAll(Map)

- java.util.List#addAll(Collection)

- java.util.Set#addAll(Collection)


## 6.桥接

### Intent

将抽象与实现分离开来，使它们可以独立变化。

### Implementation

RemoteControl 表示遥控器，指代 Abstraction。

TV 表示电视，指代 Implementor。

桥接模式将遥控器和电视分离开来，从而可以独立改变遥控器或者电视的实现。

```java
public abstract class TV {
    public abstract void on();

    public abstract void off();

    public abstract void tuneChannel();
}
```

```java
public class Sony extends TV {
    @Override
    public void on() {
        System.out.println("Sony.on()");
    }

    @Override
    public void off() {
        System.out.println("Sony.off()");
    }

    @Override
    public void tuneChannel() {
        System.out.println("Sony.tuneChannel()");
    }
}
```
```java
public class RCA extends TV {
    @Override
    public void on() {
        System.out.println("RCA.on()");
    }

    @Override
    public void off() {
        System.out.println("RCA.off()");
    }

    @Override
    public void tuneChannel() {
        System.out.println("RCA.tuneChannel()");
    }
}
```

```java
public abstract class RemoteControl {
    protected TV tv;

    public RemoteControl(TV tv) {
        this.tv = tv;
    }

    public abstract void on();

    public abstract void off();

    public abstract void tuneChannel();
}
```
```java
public class ConcreteRemoteControl1 extends RemoteControl {
    public ConcreteRemoteControl1(TV tv) {
        super(tv);
    }

    @Override
    public void on() {
        System.out.println("ConcreteRemoteControl1.on()");
        tv.on();
    }

    @Override
    public void off() {
        System.out.println("ConcreteRemoteControl1.off()");
        tv.off();
    }

    @Override
    public void tuneChannel() {
        System.out.println("ConcreteRemoteControl1.tuneChannel()");
        tv.tuneChannel();
    }
}
```
```java
public class ConcreteRemoteControl2 extends RemoteControl {
    public ConcreteRemoteControl2(TV tv) {
        super(tv);
    }

    @Override
    public void on() {
        System.out.println("ConcreteRemoteControl2.on()");
        tv.on();
    }

    @Override
    public void off() {
        System.out.println("ConcreteRemoteControl2.off()");
        tv.off();
    }

    @Override
    public void tuneChannel() {
        System.out.println("ConcreteRemoteControl2.tuneChannel()");
        tv.tuneChannel();
    }
}
```

```java
public class Client {
    public static void main(String[] args) {
        RemoteControl remoteControl1 = new ConcreteRemoteControl1(new RCA());
        remoteControl1.on();
        remoteControl1.off();
        remoteControl1.tuneChannel();
    }
}
```

### JDK

- AWT (It provides an abstraction layer which maps onto the native OS the windowing support.)
- JDBC



## 7.代理

### Intent

控制对其它对象的访问。

### Class Diagram

代理有以下四类：

- 远程代理（Remote Proxy）：控制对远程对象（不同地址空间）的访问，它负责将请求及其参数进行编码，并向不同地址空间中的对象发送已经编码的请求。
- 虚拟代理（Virtual Proxy）：根据需要创建开销很大的对象，它可以缓存实体的附加信息，以便延迟对它的访问，例如在网站加载一个很大图片时，不能马上完成，可以用虚拟代理缓存图片的大小信息，然后生成一张临时图片代替原始图片。
- 保护代理（Protection Proxy）：按权限控制对象的访问，它负责检查调用者是否具有实现一个请求所必须的访问权限。
- 智能代理（Smart Reference）：取代了简单的指针，它在访问对象时执行一些附加操作：记录对象的引用次数；当第一次引用一个对象时，将它装入内存；在访问一个实际对象前，检查是否已经锁定了它，以确保其它对象不能改变它。

### Implementation

#### 静态代理



#### 动态代理



### JDK

- java.lang.reflect.Proxy
- RMI