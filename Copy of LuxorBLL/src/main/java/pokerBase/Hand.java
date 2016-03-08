package pokerBase;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Locale;

import exceptions.DeckException;
import exceptions.HandException;
import pokerEnums.*;

import static java.lang.System.out;
import static java.lang.System.err;

/**
 * An implementation of a hand
 * 
 * @author Charles Cheung, Adam Caulfield, Khalid Al-Sarhan, Morgan Sanchez
 *
 */
public class Hand {

	/**
	 * the cards in the hand
	 */
	private ArrayList<Card> CardsInHand;
	/**
	 * the best cards in the hand
	 */
	private ArrayList<Card> BestCardsInHand;
	/**
	 * the hand score
	 */
	private HandScore HandScore;
	/**
	 * a boolean used to check to see whether a hand has been evaluated
	 */
	private boolean bScored = false;

	/**
	 * a constructor for hand
	 */
	public Hand() {
		CardsInHand = new ArrayList<Card>();
		BestCardsInHand = new ArrayList<Card>();
	}

	/**
	 * a getter for the cards in a hand
	 * 
	 * @return the cards in the hand
	 */
	public ArrayList<Card> getCardsInHand() {
		return CardsInHand;
	}

	private void setCardsInHand(ArrayList<Card> cardsInHand) {
		CardsInHand = cardsInHand;
	}

	/**
	 * a getter for the best cards in a hand
	 * 
	 * @return the best cards in the hand
	 */
	public ArrayList<Card> getBestCardsInHand() {
		return BestCardsInHand;
	}

	public void setBestCardsInHand(ArrayList<Card> bestCardsInHand) {
		BestCardsInHand = bestCardsInHand;
	}

	/**
	 * a getter for the hand score
	 * 
	 * @return the hand score
	 */
	public HandScore getHandScore() {
		return HandScore;
	}

	public void setHandScore(HandScore handScore) {
		HandScore = handScore;
	}

	/**
	 * a boolean determining whether the score has been determined
	 * 
	 * @return a boolean
	 */
	public boolean isbScored() {
		return bScored;
	}

	public void setbScored(boolean bScored) {
		this.bScored = bScored;
	}

	/**
	 * adds a card to the hand
	 * 
	 * @param c
	 *            a card
	 * @return the cards in the hand
	 */
	public Hand AddCardToHand(Card c) {
		CardsInHand.add(c);
		return this;
	}

	/**
	 * draws from the deck
	 * 
	 * @param d
	 *            deck
	 * @return the cards in the hand
	 * @throws DeckException
	 */
	public Hand Draw(Deck d) throws DeckException {
		CardsInHand.add(d.Draw());
		return this;
	}

	/**
	 * EvaluateHand is a static method that will score a given Hand of cards
	 * 
	 * @param h
	 * @return
	 * @throws HandException
	 */
	public static Hand EvaluateHand(Hand h) throws HandException {

		Collections.sort(h.getCardsInHand());

		// Collections.sort(h.getCardsInHand(), Card.CardRank);

		if (h.getCardsInHand().size() != 5) {
			throw new HandException(h);
		}

		HandScore hs = new HandScore();
		try {
			Class<?> c = Class.forName("pokerBase.Hand");

			for (eHandStrength hstr : eHandStrength.values()) {
				Class[] cArg = new Class[2];
				cArg[0] = pokerBase.Hand.class;
				cArg[1] = pokerBase.HandScore.class;

				Method meth = c.getMethod(hstr.getEvalMethod(), cArg);
				Object o = meth.invoke(null, new Object[] { h, hs });

				// If o = true, that means the hand evaluated- skip the rest of
				// the evaluations
				if ((Boolean) o) {
					break;
				}
			}

			h.bScored = true;
			h.HandScore = hs;

		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return h;
	}

	/**
	 * checks to see if a hand is five of a kind
	 * 
	 * @param h
	 *            a hand
	 * @param hs
	 *            a hand score
	 * @return a boolean checking if the hand has been evaluated
	 */
	public static boolean isHandFiveOfAKind(Hand h, HandScore hs) {
		boolean bHandCheck = false;
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.FiveOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);
		}
		return bHandCheck;
	}

	/**
	 * checks to see if a hand is a royal flush
	 * 
	 * @param h
	 *            a hand
	 * @param hs
	 *            a hand score
	 * @return a boolean checking if the hand has been evaluated
	 */
	public static boolean isHandRoyalFlush(Hand h, HandScore hs) {
		boolean bHandCheck = false;
		if (isHandStraight(h, hs) && isHandFlush(h, hs)
				&& h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.RoyalFlush.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);
		}
		hs.setHandStrength(eHandStrength.RoyalFlush.getHandStrength());
		return bHandCheck;
	}

	/**
	 * checks to see if a hand is a straight flush
	 * 
	 * @param h
	 *            a hand
	 * @param hs
	 *            a hand score
	 * @return a boolean checking if the hand has been evaluated
	 */
	public static boolean isHandStraightFlush(Hand h, HandScore hs) {
		boolean bHandCheck = false;

		if (isHandStraight(h, hs) && isHandFlush(h, hs)
				&& h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() != eRank.ACE) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.StraightFlush.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);
		}

		hs.setHandStrength(eHandStrength.StraightFlush.getHandStrength());
		return bHandCheck;
	}

	/**
	 * checks to see if a hand is four of a kind
	 * 
	 * @param h
	 *            a hand
	 * @param hs
	 *            a hand score
	 * @return a boolean checking if the hand has been evaluated
	 */
	public static boolean isHandFourOfAKind(Hand h, HandScore hs) {

		boolean bHandCheck = false;

		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.FourOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setKickers(kickers);

		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.FourOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			hs.setKickers(kickers);
		}

		return bHandCheck;
	}

	/**
	 * checks to see if a hand is a full house
	 * 
	 * @param h
	 *            a hand
	 * @param hs
	 *            a hand score
	 * @return a boolean checking if the hand has been evaluated
	 */
	public static boolean isHandFullHouse(Hand h, HandScore hs) {

		boolean bHandCheck = false;
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()
				&& h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.FullHouse.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNum());
		} else if (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()
				&& h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.SecondCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.FullHouse.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());
		}
		return bHandCheck;
	}

	/**
	 * checks to see if a hand is a flush
	 * 
	 * @param h
	 *            a hand
	 * @param hs
	 *            a hand score
	 * @return a boolean checking if the hand has been evaluated
	 */
	public static boolean isHandFlush(Hand h, HandScore hs) {

		boolean bHandCheck = false;

		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteSuit() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteSuit()
				&& h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteSuit() == h.getCardsInHand()
						.get(eCardNo.ThirdCard.getCardNo()).geteSuit()
				&& h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteSuit() == h.getCardsInHand()
						.get(eCardNo.FourthCard.getCardNo()).geteSuit()
				&& h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteSuit() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteSuit()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.Flush.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);
		}
		hs.setHandStrength(eHandStrength.Flush.getHandStrength());
		return bHandCheck;
	}

	/**
	 * checks to see if a hand is a straight
	 * 
	 * @param h
	 *            a hand
	 * @param hs
	 *            a hand score
	 * @return a boolean checking if the hand has been evaluated
	 */
	public static boolean isHandStraight(Hand h, HandScore hs) {

		boolean bHandCheck = false;

		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum()
				- h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNum() == 1
				&& h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNum()
						- h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNum() == 1
				&& h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNum()
						- h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNum() == 1
				&& h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNum()
						- h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank().getiRankNum() == 1) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.Straight.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);

		}
		hs.setHandStrength(eHandStrength.Straight.getHandStrength());
		return bHandCheck;
	}

	/**
	 * checks to see if a hand is three of a kind
	 * 
	 * @param h
	 *            a hand
	 * @param hs
	 *            a hand score
	 * @return a boolean checking if the hand has been evaluated
	 */
	public static boolean isHandThreeOfAKind(Hand h, HandScore hs) {

		boolean bHandCheck = false;

		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.ThreeOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setKickers(kickers);

		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.ThreeOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setKickers(kickers);
		} else if (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.ThreeOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			hs.setKickers(kickers);
		}

		return bHandCheck;
	}

	/**
	 * checks to see if a hand has two pairs
	 * 
	 * @param h
	 *            a hand
	 * @param hs
	 *            a hand score
	 * @return a boolean checking if the hand has been evaluated
	 */
	public static boolean isHandTwoPair(Hand h, HandScore hs) {
		boolean bHandCheck = false;

		if (!isHandFourOfAKind(h, hs)) {
			if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
					.get(eCardNo.SecondCard.getCardNo()).geteRank())
					&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
							.get(eCardNo.FourthCard.getCardNo()).geteRank())) {
				bHandCheck = true;
				hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
				if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum() > h.getCardsInHand()
						.get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNum()) {
					hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());
					hs.setLoHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNum());

				} else {
					hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNum());
					hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());

				}
				ArrayList<Card> kickers = new ArrayList<Card>();
				kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
				hs.setKickers(kickers);
			} else if ((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
					.get(eCardNo.ThirdCard.getCardNo()).geteRank())
					&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
							.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
				bHandCheck = true;
				hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
				if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNum() > h.getCardsInHand()
						.get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNum()) {
					hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNum());
					hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNum());

				} else {
					hs.setHiHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNum());
					hs.setLoHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNum());

				}
				ArrayList<Card> kickers = new ArrayList<Card>();
				kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
				hs.setKickers(kickers);
			} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
					.get(eCardNo.SecondCard.getCardNo()).geteRank())
					&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
							.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
				bHandCheck = true;
				hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
				if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum() > h.getCardsInHand()
						.get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNum()) {
					hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());
					hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNum());

				} else {
					hs.setHiHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNum());
					hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());

				}
				ArrayList<Card> kickers = new ArrayList<Card>();
				kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
				hs.setKickers(kickers);
			}
		}
		return bHandCheck;
	}

	/**
	 * checks to see if a hand has one pair
	 * 
	 * @param h
	 *            a hand
	 * @param hs
	 *            a hand score
	 * @return a boolean checking if the hand has been evaluated
	 */
	public static boolean isHandPair(Hand h, HandScore hs) {

		boolean bHandCheck = false;

		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setKickers(kickers);
		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setKickers(kickers);
		} else if (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setKickers(kickers);
		} else if (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			hs.setKickers(kickers);
		}
		return bHandCheck;
	}

	/**
	 * checks to see if a hand is a high card
	 * 
	 * @param h
	 *            a hand
	 * @param hs
	 *            a hand score
	 * @return a boolean checking if the hand has been evaluated
	 */
	public static boolean isHandHighCard(Hand h, HandScore hs) {
		boolean bHandCheck = false;
		if (!isHandStraight(h, hs) && !isHandThreeOfAKind(h, hs) && !isHandTwoPair(h, hs) && !isHandPair(h, hs)
				&& !isHandFlush(h, hs) && !isHandFlush(h, hs) && !isHandFullHouse(h, hs) && !isHandFourOfAKind(h, hs)
				&& !isHandStraightFlush(h, hs) && !isHandRoyalFlush(h, hs)) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.HighCard.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNum());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setKickers(kickers);
		}
		return bHandCheck;
	}

}