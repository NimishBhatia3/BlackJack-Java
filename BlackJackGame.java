import java.util.Scanner;
public class BlackJackGame {
    private Player player;
    private Player dealer;
    private Deck deck;
    private Scanner scanner;

    public BlackJackGame() {
        player = new Player();
        dealer = new Player();
        deck = new Deck();
        scanner = new Scanner(System.in);
    }

    public Player getDealer() {
        return dealer;
    }
    public Player getPlayer() {
        return player;
    }
    public Deck getDeck() {
        return deck;
    }

    public void play() {
        deck.shuffle();
        // Deals cards to player
        player.getHand().addCardToHand(deck.dealCard());
        player.getHand().addCardToHand(deck.dealCard());

        // Deals cards to Dealer
        dealer.getHand().addCardToHand(deck.dealCard());
        dealer.getHand().addCardToHand(deck.dealCard());
    }
}
