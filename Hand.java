import java.util.ArrayList;
import java.util.List;

public class Hand {
    List<Card> hand = new ArrayList<Card>();

    public void addCardToHand(Card card) { // Adds card to hand
        hand.add(card);
    }

    public int totalValueOfHand() {
        int total = 0;
        int numAces = 0; // Keeping track of total Aces

        for (int i = 0; i < hand.size(); i++) { // Adds card value to total
            int card = hand.get(i).getValue();
            if (card == 11) { // Checks if card is an Ace
                numAces++;
            }
            total += card;
        }

        for (int i = 0; i < numAces; i++) { // Adjusting values for Aces either 1 or 11
            if (total > 21) {
                total -= 10; // Subtracts 10 for each Ace is total is greater than 21
            }
        }

        return total;
    }

    public String toString() {
        return hand.toString() + " (Value: " + totalValueOfHand() + ")";
    }

    // Method to clear the hand
    public void clear() {
        hand.clear();
    }
}
