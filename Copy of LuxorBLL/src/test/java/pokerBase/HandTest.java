package pokerBase;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.DeckException;
import exceptions.HandException;
import pokerEnums.eCardNo;
import pokerEnums.eHandStrength;
import pokerEnums.eRank;
import pokerEnums.eSuit;


/**
 * J-Unit tests for Hand class
 * 
 * @author Charles Cheung, Adam Caulfield, Khalid Al-Sarhan, Morgan Sanchez
 *
 */
public class HandTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private Hand SetHand(ArrayList<Card> setCards, Hand h) {
		Object t = null;

		try {
			// Load the Class into 'c'
			Class<?> c = Class.forName("pokerBase.Hand");
			// Create a new instance 't' from the no-arg Deck constructor
			t = c.newInstance();
			// Load 'msetCardsInHand' with the 'Draw' method (no args);
			Method msetCardsInHand = c.getDeclaredMethod("setCardsInHand", new Class[] { ArrayList.class });
			// Change the visibility of 'setCardsInHand' to true *Good Grief!*
			msetCardsInHand.setAccessible(true);
			// invoke 'msetCardsInHand'
			Object oDraw = msetCardsInHand.invoke(t, setCards);

		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		h = (Hand) t;
		return h;

	}

	/**
	 * This test checks to see if a HandException is throw if the hand only has
	 * two cards.
	 * 
	 * @throws Exception
	 */
	@Test(expected = HandException.class)
	public void TestEvalShortHand() throws Exception {

		Hand h = new Hand();

		ArrayList<Card> ShortHand = new ArrayList<Card>();
		ShortHand.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		ShortHand.add(new Card(eSuit.CLUBS, eRank.ACE, 0));

		h = SetHand(ShortHand, h);

		// This statement should throw a HandException
		h = Hand.EvaluateHand(h);
	}

	/**
	 * This test checks to see if a hand is Four Of A Kind
	 */
	@Test
	public void TestFiveOfAKind() {

		HandScore hs = new HandScore();
		ArrayList<Card> FiveOfAKind = new ArrayList<Card>();
		FiveOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		FiveOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		FiveOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		FiveOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		FiveOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));

		Hand h = new Hand();
		h = SetHand(FiveOfAKind, h);

		boolean bActualIsHandFiveOfAKind = Hand.isHandFiveOfAKind(h, hs);
		boolean bExpectedIsHandFiveOfAKind = true;

		// Did this evaluate to Five of a Kind?
		assertEquals(bActualIsHandFiveOfAKind, bExpectedIsHandFiveOfAKind);
		// Was the five of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// no tests for kickers because Five Of A Kind has no kickers.
	}

	/**
	 * This test checks to see if a hand is Four Of A Kind
	 */
	@Test
	public void TestFourOfAKind() {

		HandScore hs = new HandScore();
		ArrayList<Card> FourOfAKind = new ArrayList<Card>();
		FourOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		FourOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		FourOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		FourOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		FourOfAKind.add(new Card(eSuit.CLUBS, eRank.KING, 0));

		Hand h = new Hand();
		h = SetHand(FourOfAKind, h);

		boolean bActualIsHandFourOfAKind = Hand.isHandFourOfAKind(h, hs);
		boolean bExpectedIsHandFourOfAKind = true;

		// Did this evaluate to Four of a Kind?
		assertEquals(bActualIsHandFourOfAKind, bExpectedIsHandFourOfAKind);
		// Was the four of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// FOAK has one kicker. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// FOAK has one kicker. Was it a King?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.KING);
	}

	/**
	 * This is another test to see if a hand is Four Of A Kind
	 */
	@Test
	public void TestFourOfAKind2() {

		HandScore hs = new HandScore();
		ArrayList<Card> FourOfAKind = new ArrayList<Card>();
		FourOfAKind.add(new Card(eSuit.CLUBS, eRank.KING, 0));
		FourOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		FourOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		FourOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		FourOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));

		Hand h = new Hand();
		h = SetHand(FourOfAKind, h);

		boolean bActualIsHandFourOfAKind = Hand.isHandFourOfAKind(h, hs);
		boolean bExpectedIsHandFourOfAKind = true;

		// Did this evaluate to Four of a Kind?
		assertEquals(bActualIsHandFourOfAKind, bExpectedIsHandFourOfAKind);
		// Was the four of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// FOAK has one kicker. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// FOAK has one kicker. Was it a King?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.KING);
	}

	/**
	 * This is a test to see if a hand is a full house
	 */

	@Test
	public void TestFullHouse() {

		HandScore hs = new HandScore();
		ArrayList<Card> FullHouse = new ArrayList<Card>();
		FullHouse.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		FullHouse.add(new Card(eSuit.DIAMONDS, eRank.ACE, 0));
		FullHouse.add(new Card(eSuit.HEARTS, eRank.ACE, 0));
		FullHouse.add(new Card(eSuit.CLUBS, eRank.KING, 0));
		FullHouse.add(new Card(eSuit.DIAMONDS, eRank.KING, 0));

		Hand h = new Hand();
		h = SetHand(FullHouse, h);

		boolean bActualIsHandFullHouse = Hand.isHandFullHouse(h, hs);
		boolean bExpectedIsHandFullHouse = true;

		// Did this evaluate to Full House?
		assertEquals(bActualIsHandFullHouse, bExpectedIsHandFullHouse);
		// Was the high hand an ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// Was the low hand an ace?
		assertEquals(hs.getLoHand(), eRank.KING.getiRankNum());
		// no tests for kickers because Full House has no kickers.
	}

	/**
	 * This is a test to see if a hand is a full house
	 */

	@Test
	public void TestFullHouse2() {

		HandScore hs = new HandScore();
		ArrayList<Card> FullHouse = new ArrayList<Card>();
		FullHouse.add(new Card(eSuit.CLUBS, eRank.KING, 0));
		FullHouse.add(new Card(eSuit.DIAMONDS, eRank.KING, 0));
		FullHouse.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		FullHouse.add(new Card(eSuit.DIAMONDS, eRank.ACE, 0));
		FullHouse.add(new Card(eSuit.HEARTS, eRank.ACE, 0));

		Hand h = new Hand();
		h = SetHand(FullHouse, h);

		boolean bActualIsHandFullHouse = Hand.isHandFullHouse(h, hs);
		boolean bExpectedIsHandFullHouse = true;

		// Did this evaluate to Full House?
		assertEquals(bActualIsHandFullHouse, bExpectedIsHandFullHouse);
		// Was the high hand an ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// Was the low hand an ace?
		assertEquals(hs.getLoHand(), eRank.KING.getiRankNum());
		// no tests for kickers because Full House has no kickers.

	}

	/**
	 * This is a test to see if a hand is three of a kind
	 */
	@Test
	public void TestThreeOfAKind() {
		HandScore hs = new HandScore();
		ArrayList<Card> ThreeOfAKind = new ArrayList<Card>();
		ThreeOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS, eRank.KING, 0));
		ThreeOfAKind.add(new Card(eSuit.HEARTS, eRank.QUEEN, 0));

		Hand h = new Hand();
		h = SetHand(ThreeOfAKind, h);

		boolean bActualIsHandThreeOfAKind = Hand.isHandThreeOfAKind(h, hs);
		boolean bExpectedIsHandThreeOfAKind = true;

		// Did this evaluate to Four of a Kind?
		assertEquals(bActualIsHandThreeOfAKind, bExpectedIsHandThreeOfAKind);
		// Was the three of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// TOAK has two kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// TOAK has two kickers. Was it a King?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.KING);
		// TOAK has two kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.HEARTS);
		// TOAK has two kickers. Was it a King?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.QUEEN);

	}

	/**
	 * This is a test to see if a hand has a three of a kind
	 */
	@Test
	public void TestThreeOfAKind2() {
		HandScore hs = new HandScore();
		ArrayList<Card> ThreeOfAKind = new ArrayList<Card>();
		ThreeOfAKind.add(new Card(eSuit.CLUBS, eRank.KING, 0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		ThreeOfAKind.add(new Card(eSuit.HEARTS, eRank.QUEEN, 0));

		Hand h = new Hand();
		h = SetHand(ThreeOfAKind, h);

		boolean bActualIsHandThreeOfAKind = Hand.isHandThreeOfAKind(h, hs);
		boolean bExpectedIsHandThreeOfAKind = true;

		// Did this evaluate to Four of a Kind?
		assertEquals(bActualIsHandThreeOfAKind, bExpectedIsHandThreeOfAKind);
		// Was the three of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// TOAK has two kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// TOAK has two kickers. Was it a King?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.KING);
		// TOAK has two kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.HEARTS);
		// TOAK has two kickers. Was it a King?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.QUEEN);

	}

	/**
	 * This is a test to see if a hand has a three of a kind
	 */
	@Test
	public void TestThreeOfAKind3() {
		HandScore hs = new HandScore();
		ArrayList<Card> ThreeOfAKind = new ArrayList<Card>();
		ThreeOfAKind.add(new Card(eSuit.CLUBS, eRank.KING, 0));
		ThreeOfAKind.add(new Card(eSuit.HEARTS, eRank.QUEEN, 0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS, eRank.ACE, 0));

		Hand h = new Hand();
		h = SetHand(ThreeOfAKind, h);

		boolean bActualIsHandThreeOfAKind = Hand.isHandThreeOfAKind(h, hs);
		boolean bExpectedIsHandThreeOfAKind = true;

		// Did this evaluate to Four of a Kind?
		assertEquals(bActualIsHandThreeOfAKind, bExpectedIsHandThreeOfAKind);
		// Was the three of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// TOAK has two kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// TOAK has two kickers. Was it a King?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.KING);
		// TOAK has two kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.HEARTS);
		// TOAK has two kickers. Was it a King?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.QUEEN);

	}

	/**
	 * This is a test to see if a hand has a pair
	 */
	@Test
	public void TestPair() {
		HandScore hs = new HandScore();
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.HEARTS, eRank.QUEEN, 0));
		Pair.add(new Card(eSuit.HEARTS, eRank.QUEEN, 0));
		Pair.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		Pair.add(new Card(eSuit.CLUBS, eRank.KING, 0));
		Pair.add(new Card(eSuit.CLUBS, eRank.JACK, 0));

		Hand h = new Hand();
		h = SetHand(Pair, h);

		boolean bActualIsHandPair = Hand.isHandPair(h, hs);
		boolean bExpectedIsHandPair = true;


		// Did this evaluate to a Pair?
		assertEquals(bActualIsHandPair, bExpectedIsHandPair);
		// Was the three of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.QUEEN.getiRankNum());
		// Pairs has three kickers. Was the first one a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Pairs has three kickers. Was the first one an Ace?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.ACE);
		// Pairs has three kickers. Was the second one a Club?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Pairs has three kickers. Was the second one a King?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.KING);
		// Pairs has three kickers. Was the third one a Club?
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Pairs has three kickers. Was the third one a Jack?
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank(), eRank.JACK);

	}

	/**
	 * This is a test to see if a hand has a pair
	 */
	@Test
	public void TestPair2() {
		HandScore hs = new HandScore();
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		Pair.add(new Card(eSuit.HEARTS, eRank.QUEEN, 0));
		Pair.add(new Card(eSuit.HEARTS, eRank.QUEEN, 0));
		Pair.add(new Card(eSuit.CLUBS, eRank.KING, 0));
		Pair.add(new Card(eSuit.CLUBS, eRank.JACK, 0));

		Hand h = new Hand();
		h = SetHand(Pair, h);

		boolean bActualIsHandPair = Hand.isHandPair(h, hs);
		boolean bExpectedIsHandPair = true;

		// Did this evaluate to a Pair?
		assertEquals(bActualIsHandPair, bExpectedIsHandPair);
		// Was the three of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.QUEEN.getiRankNum());
		// Pairs has three kickers. Was the first one a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Pairs has three kickers. Was the first one an Ace?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.ACE);
		// Pairs has three kickers. Was the second one a Club?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Pairs has three kickers. Was the second one a King?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.KING);
		// Pairs has three kickers. Was the third one a Club?
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Pairs has three kickers. Was the third one a Jack?
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank(), eRank.JACK);
	}

	/**
	 * This is a test to see if a hand has a pair
	 */
	@Test
	public void TestPair3() {
		HandScore hs = new HandScore();
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.CLUBS, eRank.KING, 0));
		Pair.add(new Card(eSuit.HEARTS, eRank.QUEEN, 0));
		Pair.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		Pair.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		Pair.add(new Card(eSuit.CLUBS, eRank.JACK, 0));

		Hand h = new Hand();
		h = SetHand(Pair, h);

		boolean bActualIsHandPair = Hand.isHandPair(h, hs);
		boolean bExpectedIsHandPair = true;

		// Did this evaluate to Four of a Kind?
		assertEquals(bActualIsHandPair, bExpectedIsHandPair);
		// Was the three of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// TOAK has two kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// TOAK has two kickers. Was it a King?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.KING);
		// TOAK has two kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.HEARTS);
		// TOAK has two kickers. Was it a King?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.QUEEN);
		// TOAK has two kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// TOAK has two kickers. Was it a King?
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank(), eRank.JACK);

	}

	/**
	 * This is a test to see if a hand has a pair
	 */
	@Test
	public void TestPair4() {
		HandScore hs = new HandScore();
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		Pair.add(new Card(eSuit.CLUBS, eRank.KING, 0));
		Pair.add(new Card(eSuit.CLUBS, eRank.JACK, 0));
		Pair.add(new Card(eSuit.HEARTS, eRank.QUEEN, 0));
		Pair.add(new Card(eSuit.HEARTS, eRank.QUEEN, 0));
		Hand h = new Hand();
		h = SetHand(Pair, h);

		boolean bActualIsHandPair = Hand.isHandPair(h, hs);
		boolean bExpectedIsHandPair = true;

		// Did this evaluate to a Pair?
		assertEquals(bActualIsHandPair, bExpectedIsHandPair);
		// Was the three of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.QUEEN.getiRankNum());
		// Pairs has three kickers. Was the first one a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Pairs has three kickers. Was the first one an Ace?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.ACE);
		// Pairs has three kickers. Was the second one a Club?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Pairs has three kickers. Was the second one a King?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.KING);
		// Pairs has three kickers. Was the third one a Club?
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Pairs has three kickers. Was the third one a Jack?
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank(), eRank.JACK);

	}

	/**
	 * This is a test to see if a hand has two pairs
	 */
	@Test
	public void TestTwoPair1() {
		HandScore hs = new HandScore();
		ArrayList<Card> TwoPair = new ArrayList<Card>();
		TwoPair.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		TwoPair.add(new Card(eSuit.HEARTS, eRank.ACE, 0));
		TwoPair.add(new Card(eSuit.DIAMONDS, eRank.JACK, 0));
		TwoPair.add(new Card(eSuit.HEARTS, eRank.JACK, 0));
		TwoPair.add(new Card(eSuit.CLUBS, eRank.QUEEN, 0));
		Hand h = new Hand();
		h = SetHand(TwoPair, h);

		boolean bActualIsHandTwoPair = Hand.isHandTwoPair(h, hs);
		boolean bExpectedIsHandTwoPair = true;

		// Did this evaluate to Two Pairs?
		assertEquals(bActualIsHandTwoPair, bExpectedIsHandTwoPair);
		// Was the one of the two pairs an Ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// Two Pairs has one kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Two Pairs has one kickers. Was it a Jack?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.QUEEN);
	}

	/**
	 * This is a test to see if a hand has two pairs
	 */
	@Test
	public void TestTwoPair2() {
		HandScore hs = new HandScore();
		ArrayList<Card> TwoPair = new ArrayList<Card>();
		TwoPair.add(new Card(eSuit.CLUBS, eRank.JACK, 0));
		TwoPair.add(new Card(eSuit.HEARTS, eRank.JACK, 0));
		TwoPair.add(new Card(eSuit.DIAMONDS, eRank.ACE, 0));
		TwoPair.add(new Card(eSuit.HEARTS, eRank.ACE, 0));
		TwoPair.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		Hand h = new Hand();
		h = SetHand(TwoPair, h);

		boolean bActualIsHandTwoPair = Hand.isHandTwoPair(h, hs);
		boolean bExpectedIsHandTwoPair = true;

		// Did this evaluate to Two Pair?
		assertEquals(bActualIsHandTwoPair, bExpectedIsHandTwoPair);
		// Was the three of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// Two Pairs has one kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Two Pairs has one kickers. Was it an Ace?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.ACE);
	}

	/**
	 * This is a test to see if a hand has two pairs
	 */
	@Test
	public void TestTwoPair3() {
		HandScore hs = new HandScore();
		ArrayList<Card> TwoPair = new ArrayList<Card>();
		TwoPair.add(new Card(eSuit.CLUBS, eRank.TEN, 0));
		TwoPair.add(new Card(eSuit.HEARTS, eRank.ACE, 0));
		TwoPair.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		TwoPair.add(new Card(eSuit.CLUBS, eRank.QUEEN, 0));
		TwoPair.add(new Card(eSuit.HEARTS, eRank.QUEEN, 0));
		Hand h = new Hand();
		h = SetHand(TwoPair, h);

		boolean bActualIsHandTwoPair = Hand.isHandTwoPair(h, hs);
		boolean bExpectedIsHandTwoPair = true;

		// Did this evaluate to Two Pair?
		assertEquals(bActualIsHandTwoPair, bExpectedIsHandTwoPair);
		// Was the three of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// Two Pairs has one kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Two Pairs has one kickers. Was it a Ten?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.TEN);
	}

	/**
	 * This is a test to see if a hand has two pairs
	 */
	@Test
	public void TestTwoPair4() {
		HandScore hs = new HandScore();
		ArrayList<Card> TwoPair = new ArrayList<Card>();
		TwoPair.add(new Card(eSuit.CLUBS, eRank.QUEEN, 0));
		TwoPair.add(new Card(eSuit.HEARTS, eRank.JACK, 0));
		TwoPair.add(new Card(eSuit.CLUBS, eRank.JACK, 0));
		TwoPair.add(new Card(eSuit.CLUBS, eRank.KING, 0));
		TwoPair.add(new Card(eSuit.HEARTS, eRank.KING, 0));
		Hand h = new Hand();
		h = SetHand(TwoPair, h);

		boolean bActualIsHandTwoPair = Hand.isHandTwoPair(h, hs);
		boolean bExpectedIsHandTwoPair = true;

		// Did this evaluate to Two Pair?
		assertEquals(bActualIsHandTwoPair, bExpectedIsHandTwoPair);
		// Was the three of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.KING.getiRankNum());
		// Two Pairs has one kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Two Pairs has one kickers. Was it a Queen?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.QUEEN);
	}

	/**
	 * This is a test to see if a hand has two pairs
	 */
	@Test
	public void TestTwoPair5() {
		HandScore hs = new HandScore();
		ArrayList<Card> TwoPair = new ArrayList<Card>();
		TwoPair.add(new Card(eSuit.CLUBS, eRank.ACE, 0));
		TwoPair.add(new Card(eSuit.HEARTS, eRank.ACE, 0));
		TwoPair.add(new Card(eSuit.CLUBS, eRank.JACK, 0));
		TwoPair.add(new Card(eSuit.CLUBS, eRank.QUEEN, 0));
		TwoPair.add(new Card(eSuit.HEARTS, eRank.QUEEN, 0));
		Hand h = new Hand();
		h = SetHand(TwoPair, h);

		boolean bActualIsHandTwoPair = Hand.isHandTwoPair(h, hs);
		boolean bExpectedIsHandTwoPair = true;

		// Did this evaluate to Two Pair?
		assertEquals(bActualIsHandTwoPair, bExpectedIsHandTwoPair);
		// Was the three of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// Two Pairs has one kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Two Pairs has one kickers. Was it a Jack?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.JACK);
	}

	/**
	 * This is a test to see if a hand has two pairs
	 */
	@Test
	public void TestTwoPair6() {
		HandScore hs = new HandScore();
		ArrayList<Card> TwoPair = new ArrayList<Card>();
		TwoPair.add(new Card(eSuit.CLUBS, eRank.QUEEN, 0));
		TwoPair.add(new Card(eSuit.HEARTS, eRank.QUEEN, 0));
		TwoPair.add(new Card(eSuit.CLUBS, eRank.JACK, 0));
		TwoPair.add(new Card(eSuit.CLUBS, eRank.KING, 0));
		TwoPair.add(new Card(eSuit.HEARTS, eRank.KING, 0));
		Hand h = new Hand();
		h = SetHand(TwoPair, h);

		boolean bActualIsHandTwoPair = Hand.isHandTwoPair(h, hs);
		boolean bExpectedIsHandTwoPair = true;

		// Did this evaluate to Two Pair?
		assertEquals(bActualIsHandTwoPair, bExpectedIsHandTwoPair);
		// Was the three of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.KING.getiRankNum());
		// Two Pairs has one kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Two Pairs has one kickers. Was it a Jack?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.JACK);
	}

	/**
	 * This is a test to see if a hand is a royal flush
	 */
	@Test
	public void TestRoyalFlush() {
		HandScore hs = new HandScore();
		ArrayList<Card> RoyalFlush = new ArrayList<Card>();
		RoyalFlush.add(new Card(eSuit.SPADES, eRank.ACE, 0));
		RoyalFlush.add(new Card(eSuit.SPADES, eRank.KING, 0));
		RoyalFlush.add(new Card(eSuit.SPADES, eRank.QUEEN, 0));
		RoyalFlush.add(new Card(eSuit.SPADES, eRank.JACK, 0));
		RoyalFlush.add(new Card(eSuit.SPADES, eRank.TEN, 0));

		Hand h = new Hand();
		h = SetHand(RoyalFlush, h);

		boolean bActualRoyalFlush = Hand.isHandRoyalFlush(h, hs);
		boolean bExpectedRoyalFlush= true;

		// Did this evaluate to Royal Flush?
		assertEquals(bActualRoyalFlush, bExpectedRoyalFlush);
		// Was the highest card an Ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// No test for kickers because there are no kickers
	}

	/**
	 * This is a test to see if a hand is a straight flush
	 */
	@Test
	public void TestStraightFlush() {
		HandScore hs = new HandScore();
		ArrayList<Card> StraightFlush = new ArrayList<Card>();
		StraightFlush.add(new Card(eSuit.SPADES, eRank.TEN, 0));
		StraightFlush.add(new Card(eSuit.SPADES, eRank.NINE, 0));
		StraightFlush.add(new Card(eSuit.SPADES, eRank.EIGHT, 0));
		StraightFlush.add(new Card(eSuit.SPADES, eRank.SEVEN, 0));
		StraightFlush.add(new Card(eSuit.SPADES, eRank.SIX, 0));

		Hand h = new Hand();
		h = SetHand(StraightFlush, h);

		boolean bActualIsHandStraightFlush = Hand.isHandStraightFlush(h, hs);
		boolean bExpectedIsHandStraightFlush = true;

		// Did this evaluate to Straight Flush?
		assertEquals(bActualIsHandStraightFlush, bExpectedIsHandStraightFlush);
		// Was the highest card a Ten?
		assertEquals(hs.getHiHand(), eRank.TEN.getiRankNum());
		// No test for kickers because there are no kickers
	}

	/**
	 * This is a test to see if a hand is a straight flush
	 */
	@Test
	public void TestStraightFlush2() {
		HandScore hs = new HandScore();
		ArrayList<Card> StraightFlush = new ArrayList<Card>();
		StraightFlush.add(new Card(eSuit.CLUBS, eRank.SIX, 0));
		StraightFlush.add(new Card(eSuit.CLUBS, eRank.FIVE, 0));
		StraightFlush.add(new Card(eSuit.CLUBS, eRank.FOUR, 0));
		StraightFlush.add(new Card(eSuit.CLUBS, eRank.THREE, 0));
		StraightFlush.add(new Card(eSuit.CLUBS, eRank.TWO, 0));

		Hand h = new Hand();
		h = SetHand(StraightFlush, h);

		boolean bActualIsHandStraightFlush = Hand.isHandStraightFlush(h, hs);
		boolean bExpectedIsHandStraightFlush = true;

		// Did this evaluate to Straight Flush?
		assertEquals(bActualIsHandStraightFlush, bExpectedIsHandStraightFlush);
		// Was the highest card a Ten?
		assertEquals(hs.getHiHand(), eRank.SIX.getiRankNum());
		// No test for kickers because there are no kickers
	}

	/**
	 * This is a test to see if a hand is a flush
	 */
	@Test
	public void TestFlush() {
		HandScore hs = new HandScore();
		ArrayList<Card> Flush = new ArrayList<Card>();
		Flush.add(new Card(eSuit.SPADES, eRank.QUEEN, 0));
		Flush.add(new Card(eSuit.SPADES, eRank.EIGHT, 0));
		Flush.add(new Card(eSuit.SPADES, eRank.SIX, 0));
		Flush.add(new Card(eSuit.SPADES, eRank.FIVE, 0));
		Flush.add(new Card(eSuit.SPADES, eRank.TWO, 0));

		Hand h = new Hand();
		h = SetHand(Flush, h);

		boolean bActualIsHandFlush = Hand.isHandFlush(h, hs);
		boolean bExpectedIsHandFlush = true;

		// Did this evaluate to Flush?
		assertEquals(bActualIsHandFlush, bExpectedIsHandFlush);
		// Was the highest card an Ace?
		assertEquals(hs.getHiHand(), eRank.QUEEN.getiRankNum());
		// No test for kickers because there are no kickers
	}

	/**
	 * This is a test to see if a hand is a straight
	 */
	@Test
	public void TestStraight() {
		HandScore hs = new HandScore();
		ArrayList<Card> Straight = new ArrayList<Card>();
		Straight.add(new Card(eSuit.SPADES, eRank.SEVEN, 0));
		Straight.add(new Card(eSuit.HEARTS, eRank.SIX, 0));
		Straight.add(new Card(eSuit.DIAMONDS, eRank.FIVE, 0));
		Straight.add(new Card(eSuit.CLUBS, eRank.FOUR, 0));
		Straight.add(new Card(eSuit.SPADES, eRank.THREE, 0));

		Hand h = new Hand();
		h = SetHand(Straight, h);

		boolean bActualIsHandStraight = Hand.isHandStraight(h, hs);
		boolean bExpectedIsHandStraight = true;

		// Did this evaluate to Straight?
		assertEquals(bActualIsHandStraight, bExpectedIsHandStraight);
		// Was the highest card an Ace?
		assertEquals(hs.getHiHand(), eRank.SEVEN.getiRankNum());
		// No test for kickers because there are no kickers
	}

	/**
	 * This is a test to see if a hand is a high card
	 */
	@Test
	public void TestHighCard() {
		HandScore hs = new HandScore();
		ArrayList<Card> HighCard = new ArrayList<Card>();
		HighCard.add(new Card(eSuit.SPADES, eRank.SEVEN, 0));
		HighCard.add(new Card(eSuit.HEARTS, eRank.FIVE, 0));
		HighCard.add(new Card(eSuit.DIAMONDS, eRank.FOUR, 0));
		HighCard.add(new Card(eSuit.CLUBS, eRank.THREE, 0));
		HighCard.add(new Card(eSuit.SPADES, eRank.TWO, 0));

		Hand h = new Hand();
		h = SetHand(HighCard, h);

		boolean bActualIsHandHighCard = Hand.isHandHighCard(h, hs);
		boolean bExpectedIsHandHighCard = true;

		// Did this evaluate to High Hand?
		assertEquals(bActualIsHandHighCard, bExpectedIsHandHighCard);
		// Was the highest card an Ace?
		assertEquals(hs.getHiHand(), eRank.SEVEN.getiRankNum());
		// Two Pairs has one kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.HEARTS);
		// Two Pairs has one kickers. Was it a Jack?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.FIVE);
		// Two Pairs has one kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.DIAMONDS);
		// Two Pairs has one kickers. Was it a Jack?
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.FOUR);
		// Two Pairs has one kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Two Pairs has one kickers. Was it a Jack?
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank(), eRank.THREE);
		// Two Pairs has one kickers. Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FourthCard.getCardNo()).geteSuit(), eSuit.SPADES);
		// Two Pairs has one kickers. Was it a Jack?
		assertEquals(hs.getKickers().get(eCardNo.FourthCard.getCardNo()).geteRank(), eRank.TWO);
	}

}
