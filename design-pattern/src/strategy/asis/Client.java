package strategy.asis;

public class Client {

    public static void main(String[] args) {

        BlueLightRedRight blueLightRedRight = new BlueLightRedRight(2);

        blueLightRedRight.blueLight();
        blueLightRedRight.redLight();
    }
}
