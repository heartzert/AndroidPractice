package heartzert.test.all.java.singleton;

/**
 * Created by heartzert on 2020/5/19.
 * Email: heartzert@163.com
 */
public class LazySingleton {

    private LazySingleton instance = null;

    LazySingleton getInstance() {
        if (null == instance) {
            synchronized (instance) {
                instance = new LazySingleton();
            }
        }
        return instance;
    }

}
