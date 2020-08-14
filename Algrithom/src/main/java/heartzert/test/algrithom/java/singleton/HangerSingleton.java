package heartzert.test.algrithom.java.singleton;

/**
 * Created by heartzert on 2020/5/19.
 * Email: heartzert@163.com
 */

/*
优点:
由于使用了static关键字，在引用这个变量时，
关于这个变量的所有写入操作都会完成，在JVM层面保证了线程安全

缺点:
不能实现懒加载，造成空间浪费，
如果一个类比较大，我们在初始化的时就加载了这个类，
但是我们长时间没有使用这个类，这就导致了内存空间的浪费。
 */
public class HangerSingleton {

    private static final HangerSingleton instance = new HangerSingleton();

    //如果使用反射调用类的构造函数，则可以破坏单例
    //在此处加一个判断可以一定程度上防止破坏。
    private HangerSingleton() {
        if (instance != null) {
            throw new RuntimeException("实例已存在");
        }
    }

    static HangerSingleton getInstance() {
        return instance;
    }

}

class LazySingleton {

    private static LazySingleton instance = null;

    private LazySingleton() {
        if (instance != null) {
            throw new RuntimeException("实例已存在");
        }
    }

    /*
    直接在方法上加锁，锁粒度大，在获取对象时效率低下，基本不支持并发。
    如果不加锁，则完全不支持多线程。
     */
    static synchronized LazySingleton getInstance() {
        if (null == instance) {
            instance = new LazySingleton();
        }
        return instance;
    }

}

class DoubleCheckSingleton {

    //这里的volatile关键字是为了避免由于JVM的指令重排
    //在new出新对象时，已经赋值给了instance，但是还未
    //执行初始化操作，而此时另一个线程执行了判断
    //instance == null,并获取instance，就会出错。
    //而volatile会禁止instance赋值与取值操作指令重排。
    //
    //但在高版本JDK中，new对象操作已经被设计为原子操作，
    //不会发生指令重排，所以volatile可加可不加。
    private static volatile DoubleCheckSingleton instance = null;

    private DoubleCheckSingleton() {
        if (instance != null) {
            throw new RuntimeException("实例已存在");
        }
    }

    public static DoubleCheckSingleton getInstance() {
        if (instance == null) {
            /*
            当instance为空时需要赋值，此时才需要加锁，可以减小锁粒度
             */
            synchronized (DoubleCheckSingleton.class) {
                /*
                有可能两个线程都判断了空，然后获取锁，当然此时只有一个线程A
                会进来。
                但是当A线程执行完毕，B线程进入，如果不再次判断是否为空，
                则B线程会重新new一个值，单例出错。
                所以此处仍需要一次判空。
                 */
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}

/*
静态内部类单例模式是一种优秀的单例模式，是开源项目中比较常用的一种单例模式。
在没有加任何锁的情况下，保证了多线程下的安全，并且没有任何性能影响和空间的浪费。
 */
class StaticInnerClassSingleton {

    private StaticInnerClassSingleton() {
        if (Holder.instance != null) {
            throw new RuntimeException("实例已存在");
        }
    }

    private static class Holder {

        private static StaticInnerClassSingleton instance = new StaticInnerClassSingleton();
    }

    //当初始化StaticInnerClassSingleton类时，并不会直接加载静态内部类，
    //而是会在首次使用到静态内部类时才会加载，也就是调用getInstance方法时
    //而且此时属于类加载，线程安全和唯一性又JVM保证，不需要我们做工作。
    static StaticInnerClassSingleton getInstance() {
        return Holder.instance;
    }
}

/*
实际上枚举会被翻译成如下代码，可以看出跟饿汉式单例模式代码一致，所以枚举可以实现单例
public class Color {

    public static final Color GREEN = new Color(),
            RED = new Color(),
            BLUE = new Color(),
            GRAY = new Color();

    private Color() {
    }
}
 */
enum EnumSingleton {
    //调用EnumSingleton.INSTANCE即可，采用了枚举的特性保证单例。
    //
    INSTANCE
}