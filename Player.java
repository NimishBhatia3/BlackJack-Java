public class Player { // You might want to add methods for additional player actions, like splitting or doubling down.
    private Hand pHand;
    public Player() {
        pHand = new Hand();
    }

    public Hand getHand() { return pHand; }
}
