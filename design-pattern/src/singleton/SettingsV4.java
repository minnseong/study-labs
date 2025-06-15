package singleton;

public class SettingsV4 {

    private SettingsV4() {}

    private static class SettingsHolder {
        private static final SettingsV4 INSTANCE = new SettingsV4();
    }

    public static SettingsV4 getInstance() {
        return SettingsHolder.INSTANCE;
    }

}
