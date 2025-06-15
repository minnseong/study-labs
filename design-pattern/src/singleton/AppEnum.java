package singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

public class AppEnum {

    public static void main(String[] args)
            throws IOException, ClassNotFoundException {

        EnumSettings settings = EnumSettings.INSTANCE;

        try {
            Constructor<EnumSettings> constructor = EnumSettings.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            EnumSettings settings1 = constructor.newInstance();
            System.out.println(settings == settings1);
        } catch (Exception e) {
            System.out.println("1) enum 은 리플랙션을 통해 생성자 호출을 할 수 없다. " + e);
        }

        EnumSettings settings2 = null;

        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("settings3.obj"))) {
            out.writeObject(settings);
        }

        try (ObjectInput in = new ObjectInputStream(new FileInputStream("settings3.obj"))) {
            settings2 = (EnumSettings) in.readObject();
        }

        System.out.println("2) 직렬화 & 역직렬화 사용 : " + (settings == settings2));

    }
}
