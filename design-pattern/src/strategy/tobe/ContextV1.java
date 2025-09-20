package strategy.tobe;

public class ContextV1 {

    private Strategy strategy;

    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void blueLight() {
        strategy.blueLight();
    }

    public void redLight() {
        strategy.redLight();
    }
}
