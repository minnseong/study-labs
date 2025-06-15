package singleton;

import java.io.Serializable;

public class SettingsSerializable implements Serializable {

    private SettingsSerializable() {}

    private static class SettingsHolder {
        private static final SettingsSerializable INSTANCE = new SettingsSerializable();
    }

    public static SettingsSerializable getInstance() {
        return SettingsHolder.INSTANCE;
    }

}
