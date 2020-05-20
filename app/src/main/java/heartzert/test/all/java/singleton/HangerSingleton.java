package heartzert.test.all.java.singleton;

/**
 * Created by heartzert on 2020/5/19.
 * Email: heartzert@163.com
 */
public class HangerSingleton {

    private static HangerSingleton instance = new HangerSingleton();

    HangerSingleton getInstance() {
        return instance;
    }

}
