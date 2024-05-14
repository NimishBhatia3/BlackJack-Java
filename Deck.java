import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Deck {
    List<Card> deck = new ArrayList<Card>();
    String[] value = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    String[] suit = {"C", "D", "H", "S"};

    public Deck() { //creates the deck of 52 cards
        for (int i = 0; i < suit.length; i++) {
            for (int j = 0; j < value.length; j++) {
                Card card = new Card(value[j], suit[i]);
                deck.add(card);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(deck); // shuffles deck of cards
    }

    public Card dealCard() {
        return deck.remove(0); // removes card from top of deck simulates dealing cards
    }
}
