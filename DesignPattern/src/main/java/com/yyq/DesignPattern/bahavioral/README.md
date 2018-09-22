### [模板方法模式](/DesignPattern/src/main/java/com/yyq/DesignPattern/behavioral/templatemethod/)  
* 模板方法-定义与类型  
定义：定义了一个算法的骨架，并允许子类为一个或多个步骤提供实现。  
     模板方法使子类可以在不改变算法的结构情况下，重新定义算法的某些步骤。  
类型：行为型  
* 模板方法-适用场景  
一次性实现一个算法的不变的部分，并将可变的行为留给子类来实现  
各子类中公共的行为被提取出来，放到公共的子类当中，从而避免代码重复。  
* 模板方法-优点  
提高复用性  
提高扩展性  
符合开闭原则  
提供了一个很好的代码复用平台  
模板方法-缺点  
类数目增加  
增加了系统实现的复杂度  
继承关系自身缺点，如果父类添加新的抽象方法，把所有子类都要改一遍  
* 模板方法-扩展  
钩子方法  
* 模板方法-相关设计模式  
模板方法模式和工厂方法模式(工厂方法是模板方法的特殊实现方式) 
模板方法和策略模式  
java.util.AbstractList.addAll()  
java.util.AbstractList.get()    
java.util.Abstractset  
java.util.AbstractMap  
javax.servlet.http.HttpServlet  
### [迭代器模式](/DesignPattern/src/main/java/com/yyq/DesignPattern/behavioral/iterator)
* 迭代器-定义与类型  
定义：提供一个方法，顺序访问集合中的各个元素，而不暴露该对象的内部表示  
类型：行为型  
* 迭代器-适用场景  
访问一个集合对象的内容而无需暴露它的内部表示  
为遍历不同集合结构提供一个统一的访问接口
* 迭代器-优点  
分离了集合对象的遍历行为  
* 迭代器-缺点  
类的个数成对增加  
* 迭代器-相关设计模式   
迭代器模式和访问者模式   
java.util.Iterator  
java.util.ArrayList.Itr  