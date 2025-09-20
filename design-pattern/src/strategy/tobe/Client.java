package strategy.tobe;

public class Client {

    public static void main(String[] args) {

        ContextV1 contextV1 = new ContextV1(new FastStrategy());
        contextV1.blueLight();
        contextV1.redLight();

        contextV1 = new ContextV1(new NormalStrategy());
        contextV1.blueLight();
        contextV1.redLight();

        ContextV2 contextV2 = new ContextV2();
        contextV2.blueLight(new NormalStrategy());
        contextV2.blueLight(new SlowStrategy());
    }
}
