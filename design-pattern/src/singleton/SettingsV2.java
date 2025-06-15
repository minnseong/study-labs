package singleton;

public class SettingsV2 {

    private static SettingsV2 instance = new SettingsV2();

    private SettingsV2() {}

    public static synchronized SettingsV2 getInstance() {
        return instance;
    }

}
