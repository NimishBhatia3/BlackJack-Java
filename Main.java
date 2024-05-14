import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;
import javafx.scene.text.Text;

public class Main extends Application {
    // Constants for card dimensions and positions
    private static final int CARD_WIDTH = 140;
    private static final int CARD_HEIGHT = 196;
    private static final int DEALER_Y_POSITION = 25;
    private static final int PLAYER_Y_POSITION = 345;

    private Player dealer;
    private Player player;
    private Deck deck;
    private Pane mainPane;
    private Text resultText;
    private Button hit;
    private Button stand;
    private Button nextGameButton;

    // Method to initialize the game state
    private void initializeGameState() {
        BlackJackGame blackJack = new BlackJackGame();
        blackJack.play();

        // Instantiate Player, Dealer, and Deck
        dealer = blackJack.getDealer();
        player = blackJack.getPlayer();
        deck = blackJack.getDeck();
    }

    // Method to draw a card
    private void drawCard(Card card, int index, int yPosition) {
        InputStream isTemp = getClass().getResourceAsStream(card.getImagePath());
        Image imageTemp = new Image(isTemp);
        ImageView imageViewTemp = new ImageView(imageTemp);
        imageViewTemp.setFitWidth(CARD_WIDTH);
        imageViewTemp.setFitHeight(CARD_HEIGHT);
        imageViewTemp.setX(25 + (CARD_WIDTH + 5) * index);
        imageViewTemp.setY(yPosition);
        mainPane.getChildren().add(imageViewTemp);
    }

    // Method to start a new game
    private void startGame() {
        initializeGameState(); // Call the method to initialize game state

        // Redraw cards for dealer and player
        redrawCards();

        // Add all buttons and images to pane
        mainPane.getChildren().addAll(hit, stand, resultText, nextGameButton);

        // Enable the hit button
        hit.setDisable(false);

        // Disable the stand button
        stand.setDisable(false);
    }

    // Method to redraw cards for dealer and player
    private void redrawCards() {
        // Clear existing cards from the pane
        mainPane.getChildren().clear();

        // Draw hidden card for dealer
        drawCard(dealer.getHand().hand.get(0), 0, DEALER_Y_POSITION);

        // Draw Dealer's hand minus the hidden card
        for (int i = 1; i < dealer.getHand().hand.size(); i++) {
            Card card = dealer.getHand().hand.get(i);
            drawCard(card, i, DEALER_Y_POSITION);
        }

        // Draw Player's hand
        for (int i = 0; i < player.getHand().hand.size(); i++) {
            Card card = player.getHand().hand.get(i);
            drawCard(card, i, PLAYER_Y_POSITION);
        }
    }

    // Method to determine the winner and update the result text
    private void determineWinner() {
        int playerTotal = player.getHand().totalValueOfHand();
        int dealerTotal = dealer.getHand().totalValueOfHand();

        if (playerTotal > 21) {
            resultText.setText("Player busts! Dealer wins.");
        } else if (dealerTotal > 21 || playerTotal > dealerTotal) {
            resultText.setText("Player wins!");
        } else if (playerTotal < dealerTotal) {
            resultText.setText("Dealer wins.");
        } else {
            resultText.setText("It's a tie.");
        }

        // After determining the winner, show the "Next Game" button
        nextGameButton.setVisible(true);
    }

    public void start(Stage primaryStage) {
        initializeGameState(); // Initialize game state at the start of the application

        mainPane = new Pane();
        mainPane.setStyle("-fx-background-color: rgb(10,80,70)");

        // Setting up hidden card
        InputStream is = getClass().getResourceAsStream("./cards/BACK.png");
        Image image = new Image(is);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(CARD_WIDTH);
        imageView.setFitHeight(CARD_HEIGHT);
        imageView.setX(25);
        imageView.setY(25);

        // Draw Dealer's hand minus the hidden card
        for (int i = 1; i < dealer.getHand().hand.size(); i++) {
            Card card = dealer.getHand().hand.get(i);
            drawCard(card, i, DEALER_Y_POSITION);
        }

        // Draw Player's hand
        for (int i = 0; i < player.getHand().hand.size(); i++) {
            Card card = player.getHand().hand.get(i);
            drawCard(card, i, PLAYER_Y_POSITION);
        }

        // Make Buttons for hit and stand
        hit = new Button("Hit");
        hit.setStyle("-fx-font: 22 arial; -fx-base: rgb(0,170,0); -fx-text-fill: rgb(255,255,255);");
        hit.relocate(200, 650);
        hit.setPrefSize(200, 100);

        stand = new Button("Stand");
        stand.setStyle("-fx-font: 22 arial; -fx-base: rgb(170,0,0); -fx-text-fill: rgb(255,255,255);");
        stand.relocate(400, 650);
        stand.setPrefSize(200, 100);

        // Text for result
        resultText = new Text();
        resultText.setStyle("-fx-font: 40 arial;");
        resultText.relocate(270, 300);

        // Button for next game
        nextGameButton = new Button("Next Game");
        nextGameButton.setStyle("-fx-font: 22 arial; -fx-base: rgb(0,0,255); -fx-text-fill: rgb(255,255,255);");
        nextGameButton.relocate(600, 650);
        nextGameButton.setPrefSize(200, 100);
        nextGameButton.setVisible(false); // Initially hide the button
        nextGameButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                startGame(); // Call startGame() to reset the game
                nextGameButton.setVisible(false); // Hide the button again
                resultText.setText(""); // Clear the result text
            }
        });

        // Button Logic for hit and stand
        hit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Card newCard = deck.dealCard();
                player.getHand().addCardToHand(newCard);
                drawCard(newCard, player.getHand().hand.size()-1, PLAYER_Y_POSITION);

                if (player.getHand().totalValueOfHand() >= 21) {
                    hit.setDisable(true);
                }
            }
        });

        stand.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                hit.setDisable(true); // Disables hit button after stand button is pressed as the player can no longer be dealt cards

                // Updates hidden card to be shown
                Card hiddenCard = dealer.getHand().hand.get(0);
                drawCard(hiddenCard, 0, DEALER_Y_POSITION);

                // Deals the dealer cards while value of hand is less than 17
                while (dealer.getHand().totalValueOfHand() < 17) {
                    Card newCard = deck.dealCard();
                    dealer.getHand().addCardToHand(newCard);

                    drawCard(newCard, dealer.getHand().hand.size()-1, DEALER_Y_POSITION);
                }
                stand.setDisable(true); // Disables stand button after being clicked once
                determineWinner(); // Check for winner after dealer stands
            }
        });

        // Add all buttons and images to pane
        mainPane.getChildren().addAll(hit, stand, imageView, resultText, nextGameButton);

        primaryStage.setTitle("BlackJack Game");
        primaryStage.setScene(new Scene(mainPane, 800, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
