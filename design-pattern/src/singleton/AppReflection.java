package singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class AppReflection {

    public static void main(String[] args)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor<SettingsV1> constructor1 = SettingsV1.class.getDeclaredConstructor();
        constructor1.setAccessible(true);
        SettingsV1 settingsV1 = SettingsV1.getInstance();
        SettingsV1 settingsV1_1 = constructor1.newInstance();

        System.out.println("1) synchronize 키워드 : " + (settingsV1 == settingsV1_1));

        Constructor<SettingsV2> constructor2 = SettingsV2.class.getDeclaredConstructor();
        constructor2.setAccessible(true);
        SettingsV2 settingsV2 = SettingsV2.getInstance();
        SettingsV2 settingsV2_1 = constructor2.newInstance();
        System.out.println("2) 이른 초기화 (eager initialization) : " + (settingsV2 == settingsV2_1));

        Constructor<SettingsV3> constructor3 = SettingsV3.class.getDeclaredConstructor();
        constructor3.setAccessible(true);
        SettingsV3 settingsV3 = SettingsV3.getInstance();
        SettingsV3 settingsV3_1 = constructor3.newInstance();

        System.out.println("3) double checked locking : " + (settingsV3 == settingsV3_1));

        Constructor<SettingsV4> constructor4 = SettingsV4.class.getDeclaredConstructor();
        constructor4.setAccessible(true);
        SettingsV4 settingsV4 = SettingsV4.getInstance();
        SettingsV4 settingsV4_1 = constructor4.newInstance();

        System.out.println("4) static inner 클래스 : " + (settingsV4 == settingsV4_1));


    }

}
