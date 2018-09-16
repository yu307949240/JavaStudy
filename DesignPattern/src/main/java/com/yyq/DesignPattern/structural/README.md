### [外观](/DesignPattern/src/main/java/com/yyq/DesignPattern/structural/facade/)
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
org.springframework.jdbc.support.JdbcUtils
### [装饰者](/DesignPattern/src/main/java/com/yyq/DesignPattern/structural/decorator/)
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
BufferedReader InputStream org.springframework.cache.transaction.TransactionAwareCacheDecorator
### [适配器](/DesignPattern/src/main/java/com/yyq/DesignPattern/structural/adapter/)
* 适配器-定义与类型  
定义：将一个类的接口转换成客户期望的另一个接口(例如笔记本的电源适配器，手机的电源适配器)；  
     使原本不兼容的类可以一起工作。  
类型：结构型  
* 适配器-适用场景  
已经存在的类，它的方法和需求不匹配时(方法结果相同或相似)； 
不是软件设计阶段考虑的设计模式，是随着软件维护，由于不同的产品，不同厂家造成功能类似而接口不相同情况下的解决方案。  
* 适配器-优点  
能提高类的透明性和复用，现有的类复用但不需要改变；  
目标类和适配器类解耦，提高程序的扩展性；  
符合开闭原则。  
* 适配器-缺点  
适配器编写过程中需要全面的考虑，可能会增加系统的复杂性；  
增加代码可读的难度。  
* 适配器-扩展  
1.对象适配器：符合组合复用原则，使用委托机制。  
2.类适配器：通过类继承  
* 适配器-相关设计模式  
适配器模式和外观模式  
javax.xml.bind.annotation.adapters.XmlAdapter  
org.springframework.aop.framework.adapter  
org.springframework.web.servlet.HandlerAdapter  
org.springframework.web.servlet.DispatcherServlet  
### [享元](/DesignPattern/src/main/java/com/yyq/DesignPattern/structural/flyweight/)  
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
java.lang.Integer
java.lang.Long
### [组合](/DesignPattern/src/main/java/com/yyq/DesignPattern/structural/composite/)
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
java.util.HashMap.pullAll()  
java.awt.Container.add()  
java.util.ArrayList.addAll()  
org.apache.ibtais.scripting.xmltags.SqlNode  
### [桥接](/DesignPattern/src/main/java/com/yyq/DesignPattern/structural/bridge/)
* 桥接-定义与类型  
定义：将抽象部分与它的具体实现部分分离，使他们都可以独立的变化；  
     通过组合的方式建立两个类之间联系，而不是继承。  
类型：结构型  
* 桥接-适用场景
抽象与具体实现之间增加更多的的灵活性；  
一个类存在两个(或多个)独立变化的唯独，且这两个(或多个)维度都需要独立进行扩展。  
* 桥接-优点  
分离抽象部分及其具体实现部分  
提高了系统的扩展性
符合开闭原则
* 桥接-缺点  
增加系统的理解和设计难度  
需要正确识别出系统中两个独立变化的维度  
* 桥接-相关设计模式  
桥接模式和组合模式  
桥接模式和适配器模式  
java.sql.Driver
java.sql.DriverManager