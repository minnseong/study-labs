package singleton;

import java.io.Serializable;

public class SettingsSerializableReadResolve implements Serializable {

    private SettingsSerializableReadResolve() {}

    private static class SettingsHolder {
        private static final SettingsSerializableReadResolve INSTANCE = new SettingsSerializableReadResolve();
    }

    public static SettingsSerializableReadResolve getInstance() {
        return SettingsHolder.INSTANCE;
    }


    protected Object readResolve() {
        return SettingsHolder.INSTANCE;
    }
}
