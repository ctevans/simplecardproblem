import java.util.ArrayList;
import java.util.Arrays;
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

  /**
   * Sorts the cards in the deck. This method in particular is more about
   * up/downcasting and fitting the cards into arrays that can be easily processed
   * by the mergesort algorithm used.
   */
  public void sort() {
    Card[] cardArray = new Card[cards.size()];
    cardArray = cards.toArray(cardArray);
    mergeSort(cardArray, 0, cards.size() - 1);
    cards = new ArrayList<Card>(Arrays.asList(cardArray));
  }

  /**
   * Returns if the first card (lhs/left hand side) passed into the method has a
   * higher sorting order than the second card (rhs/right hand side) passed into
   * the method. Based upon the guidelines provided.
   * 
   * The suits are valued from highest to lowest: Spades, Hearts, Clubs, Diamonds
   * 
   * The cards are valued from highest to lowest:
   * King,Queen,Jack,10,9,8,7,6,5,4,3,2,Ace
   * 
   * @param lhsCard first card passed into the method.
   * @param rhsCard second card passed into the method.
   * @return Returns
   */
  public boolean determineHigherSortOrder(Card lhsCard, Card rhsCard) {
    // leftArray[leftIndex].is_less_than(rightArray[rightIndex])

    // spades > hearts > clubs > diamonds
    // k > 10 > 2 > a

    // suit > num

    // Firstly suit values.
    if (lhsCard.get_value().ordinal() < rhsCard.get_value().ordinal()) {
      return true;
    }
    if (lhsCard.get_value().ordinal() > rhsCard.get_value().ordinal()) {
      return false;
    }

    // Now face values if the suit values are equal.
    if (lhsCard.is_less_than(rhsCard)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Mergesort algorithm was selected due to it having a time complexity of
   * O(nLogn), and a worst runtime of O(nlogn).
   * 
   * Citation/Source of the original mergesort algorithm used here:
   * https://stackabuse.com/sorting-algorithms-in-java/
   */
  public void mergeSort(Card[] array, int left, int right) {
    if (right <= left)
      return;
    int mid = (left + right) / 2;
    mergeSort(array, left, mid);
    mergeSort(array, mid + 1, right);
    merge(array, left, mid, right);
  }

  void merge(Card[] array, int left, int mid, int right) {
    // calculating lengths
    int lengthLeft = mid - left + 1;
    int lengthRight = right - mid;

    // creating temporary subarrays
    Card leftArray[] = new Card[lengthLeft];
    Card rightArray[] = new Card[lengthRight];

    // copying our sorted subarrays into temporaries
    for (int i = 0; i < lengthLeft; i++)
      leftArray[i] = array[left + i];
    for (int i = 0; i < lengthRight; i++)
      rightArray[i] = array[mid + i + 1];

    // iterators containing current index of temp subarrays
    int leftIndex = 0;
    int rightIndex = 0;

    // copying from leftArray and rightArray back into array
    for (int i = left; i < right + 1; i++) {
      // if there are still uncopied elements in R and L, copy minimum of the two
      if (leftIndex < lengthLeft && rightIndex < lengthRight) {
        // if (leftArray[leftIndex].is_less_than(rightArray[rightIndex])) {
        if (determineHigherSortOrder(leftArray[leftIndex], (rightArray[rightIndex]))) {
          array[i] = leftArray[leftIndex];
          leftIndex++;
        } else {
          array[i] = rightArray[rightIndex];
          rightIndex++;
        }
      }
      // if all the elements have been copied from rightArray, copy the rest of
      // leftArray
      else if (leftIndex < lengthLeft) {
        array[i] = leftArray[leftIndex];
        leftIndex++;
      }
      // if all the elements have been copied from leftArray, copy the rest of
      // rightArray
      else if (rightIndex < lengthRight) {
        array[i] = rightArray[rightIndex];
        rightIndex++;
      }
    }
  }
}
