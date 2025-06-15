package singleton;

public class SettingsV1 {

    private static SettingsV1 instance;

    private SettingsV1() {}

    public static synchronized SettingsV1 getInstance() {

        if (instance == null) {
            instance = new SettingsV1();
        }

        return instance;
    }

}
