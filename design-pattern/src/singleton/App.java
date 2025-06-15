package singleton;

public class App {

    public static void main(String[] args) {

        SettingsV1 settingsV1 = SettingsV1.getInstance();
        SettingsV1 settingsV11 = SettingsV1.getInstance();

        System.out.println("1) synchronize 키워드 : " + (settingsV11 == settingsV1));

        SettingsV2 settingsV2 = SettingsV2.getInstance();
        SettingsV2 settingsV2_1 = SettingsV2.getInstance();

        System.out.println("2) 이른 초기화 (eager initialization) : " + (settingsV2 == settingsV2_1));

        SettingsV3 settingsV3 = SettingsV3.getInstance();
        SettingsV3 settingsV3_1 = SettingsV3.getInstance();

        System.out.println("3) double checked locking : " + (settingsV3 == settingsV3_1));

        SettingsV4 settingsV4 = SettingsV4.getInstance();
        SettingsV4 settingsV4_1 = SettingsV4.getInstance();

        System.out.println("4) static inner 클래스 : " + (settingsV4 == settingsV4_1));
    }

}
