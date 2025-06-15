package singleton;

public class SettingsV3 {

    private static volatile SettingsV3 instance;

    private SettingsV3() {}

    public static SettingsV3 getInstance() {
        if (instance == null) {
            synchronized (SettingsV3.class) {
                if (instance == null) {
                    instance = new SettingsV3();
                }
            }
        }

        return instance;
    }

}
