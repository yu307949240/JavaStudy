* [一、创建型设计模式](#创建型设计模式)
  * [1.简单工厂](#1-简单工厂)
  * [2.工厂方法](#2-工厂方法)
  * [3.抽象工厂](#3-抽象工厂)
  * [4.建造者](#4-建造者)
  * [5.单例模式](#5-单例模式)
  * [6.原型模式](#6-原型模式)

# [一、创建型设计模式]

## [1.简单工厂]
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
## [2.工厂方法]
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


## [3.抽象工厂]
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


## [4.建造者]
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

## [5.单例模式]
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
                    // 3.将指针指向内存空间                                             
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
                    // 3.将指针指向内存空间                                             
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

## [6.原型模式]
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

