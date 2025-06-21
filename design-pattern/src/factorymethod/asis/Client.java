package factorymethod.asis;

public class Client {

    public static void main(String[] args) {

        Ship whiteship = ShipFactory.orderShip("Whiteship", "minnseong@mail.com");
        System.out.println(whiteship);

        Ship blackship = ShipFactory.orderShip("Blackship", "minnseong@mail.com");
        System.out.println(blackship);
    }

}
