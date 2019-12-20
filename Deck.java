import java.util.ArrayList;
import java.util.Random;

/**
 * Deck.java
 * 
 * @brief A class that represents a deck of playing cards
 * 
 * @details This class is responsible for shuffling and dealing a deck of cards.
 *          The deck should contain 52 cards A,2 - 10, J,Q K or four suits, but
 *          no jokers.
 */
class Deck {
  private ArrayList<Card> cards;
  private int numberOfSuits;
  private int numberOfCardsPerSuit;

  /**
   * Constructor that instantiates the default settings for the deck.
   */
  public Deck() {
    numberOfSuits = Card.Suit.values().length;
    numberOfCardsPerSuit = Card.Value.values().length - 1; // Joker card removed.
    cards = new ArrayList<Card>();

    for (int i = 0; i < numberOfSuits; i++) {
      for (int k = 1; k < numberOfCardsPerSuit + 1; k++) {
        Card newCard = new Card();
        newCard.set_value(k, i);
        cards.add(newCard);
      }
    }
  }

  /**
   * Prints N sets of M cards: The internal set of cards in the deck does is not
   * altered. The deck doesn't 'lose' the cards it deals. Also, two consecutive
   * 'deals' should produce the same result. The deck must be shuffled in order to
   * produce a different 'deal'.
   * 
   * @param sets  Number of sets to provide.
   * @param cards Number of cards per set.
   */
  public void deal_hand(int sets, int cards) {
    for (int i = 0; i < sets; i++) {
      for (int k = 0; k < cards; k++) {
        int index = i * cards + k;
        this.cards.get(index).print();
      }
      System.out.println();
    }
  }

  /**
   * Prints the entire deck to standard out. Prints 4 rows of 13 cards each. No
   * order is assumed here.
   */
  public void print_deck() {
    int counter = 0;
    for (Card card : cards) {
      card.print();
      counter++;
      if (counter % 13 == 0) {
        System.out.println();
      }
    }
  }

  /**
   * All 52 cards are randomly permuted based upon provided seed. The original
   * list is overwritten in this instance.
   * 
   * @param seed Seed provided to rng.
   */
  public void shuffle(int seed) {
    Random rng = new Random(seed);
    ArrayList<Card> newCardList = new ArrayList<Card>();

    while (!cards.isEmpty()) {
      int index = rng.nextInt(cards.size());
      newCardList.add(cards.get(index));
      cards.remove(index);
    }
    cards = newCardList;
  }

  public void sort() {
  }

}
