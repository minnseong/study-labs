package singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class AppSerializable {

    public static void main(String[] args)
            throws IOException, ClassNotFoundException {

        SettingsSerializable settingsV1 = SettingsSerializable.getInstance();
        SettingsSerializable settingsV1_1;

        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("settings.obj"))) {
            out.writeObject(settingsV1);
        }

        try (ObjectInput in = new ObjectInputStream(new FileInputStream("settings.obj"))) {
            settingsV1_1 = (SettingsSerializable) in.readObject();
        }

        System.out.println("1) 직렬화 & 역직렬화 사용 : " + (settingsV1 == settingsV1_1));


        SettingsSerializableReadResolve settingsV2 = SettingsSerializableReadResolve.getInstance();
        SettingsSerializableReadResolve settingsV2_1;

        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("settings2.obj"))) {
            out.writeObject(settingsV2);
        }

        try (ObjectInput in = new ObjectInputStream(new FileInputStream("settings2.obj"))) {
            settingsV2_1 = (SettingsSerializableReadResolve) in.readObject();
        }

        System.out.println("2) 직렬화 & 역직렬화 방지 : " + (settingsV2 == settingsV2_1));
    }

}
