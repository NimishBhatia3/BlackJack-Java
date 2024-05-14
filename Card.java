import java.util.ArrayList;
import java.util.List;

public class Card {
    private String value; // rank
    private String suit; // suit of card

    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        if (value.equals("A")) {
            return 11;
        }
        if (value.equals("J") || value.equals("Q") || value.equals("K")) {
            return 10;
        }
        return Integer.parseInt(value);
    }

    public String toString() {
        return value + "-" + suit;
    }

    public String getImagePath() {
        return "./cards/" + toString() + ".png";
    }
}
