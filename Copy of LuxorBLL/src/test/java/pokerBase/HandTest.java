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

		boolean bActualHandIsThreeOfAKind = Hand.isHandThreeOfAKind(h, hs);
		boolean bExpectedIsHandThreeOfAKind = true;

		// Did this evaluate to Four of a Kind?
		assertEquals(bActualHandIsThreeOfAKind, bExpectedIsHandThreeOfAKind);
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

		boolean bActualHandIsThreeOfAKind = Hand.isHandThreeOfAKind(h, hs);
		boolean bExpectedIsHandThreeOfAKind = true;

		// Did this evaluate to Four of a Kind?
		assertEquals(bActualHandIsThreeOfAKind, bExpectedIsHandThreeOfAKind);
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

		boolean bActualHandIsThreeOfAKind = Hand.isHandThreeOfAKind(h, hs);
		boolean bExpectedIsHandThreeOfAKind = true;

		// Did this evaluate to Four of a Kind?
		assertEquals(bActualHandIsThreeOfAKind, bExpectedIsHandThreeOfAKind);
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

		boolean bActualHandPair = Hand.isHandPair(h, hs);
		boolean bExpectedHandPair = true;

		// Did this evaluate to a Pair?
		assertEquals(bActualHandPair, bExpectedHandPair);
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

		boolean bActualHandPair = Hand.isHandPair(h, hs);
		boolean bExpectedHandPair = true;

		// Did this evaluate to a Pair?
		assertEquals(bActualHandPair, bExpectedHandPair);
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

		boolean bActualHandPair = Hand.isHandPair(h, hs);
		boolean bExpectedHandPair = true;

		// Did this evaluate to Four of a Kind?
		assertEquals(bActualHandPair, bExpectedHandPair);
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

		boolean bActualHandPair = Hand.isHandPair(h, hs);
		boolean bExpectedHandPair = true;

		// Did this evaluate to a Pair?
		assertEquals(bActualHandPair, bExpectedHandPair);
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

		boolean bHandActualTwoPair = Hand.isHandTwoPair(h, hs);
		boolean bExpectedHandTwoPair = true;

		// Did this evaluate to a Pair?
		assertEquals(bHandActualTwoPair, bExpectedHandTwoPair);
		// Was the three of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// Pairs has three kickers. Was the third one a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Pairs has three kickers. Was the third one a Jack?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.JACK);
	}
	
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

		boolean bHandActualTwoPair = Hand.isHandTwoPair(h, hs);
		boolean bExpectedHandTwoPair = true;

		// Did this evaluate to a Pair?
		assertEquals(bHandActualTwoPair, bExpectedHandTwoPair);
		// Was the three of a kind an Ace?
		assertEquals(hs.getHiHand(), eRank.KING.getiRankNum());
		// Pairs has three kickers. Was the third one a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		// Pairs has three kickers. Was the third one a Jack?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.JACK);
	}
	
	@Test
	public void TestRoyalFlush() {
		HandScore hs = new HandScore();
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.SPADES, eRank.ACE, 0));
		Pair.add(new Card(eSuit.SPADES, eRank.KING, 0));
		Pair.add(new Card(eSuit.SPADES, eRank.QUEEN, 0));
		Pair.add(new Card(eSuit.SPADES, eRank.JACK, 0));
		Pair.add(new Card(eSuit.SPADES, eRank.TEN, 0));

		Hand h = new Hand();
		h = SetHand(Pair, h);

		boolean bActualHandPair = Hand.isHandRoyalFlush(h, hs);
		boolean bExpectedHandPair = true;

		// Did this evaluate to Royal Flush?
		assertEquals(bActualHandPair, bExpectedHandPair);
		// Was the highest card an Ace?
		assertEquals(hs.getHiHand(), eRank.ACE.getiRankNum());
		// No test for kickers because there are no kickers
	}

	@Test
	public void TestStraightFlush() {
		HandScore hs = new HandScore();
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.SPADES, eRank.TEN, 0));
		Pair.add(new Card(eSuit.SPADES, eRank.NINE, 0));
		Pair.add(new Card(eSuit.SPADES, eRank.EIGHT, 0));
		Pair.add(new Card(eSuit.SPADES, eRank.SEVEN, 0));
		Pair.add(new Card(eSuit.SPADES, eRank.SIX, 0));

		Hand h = new Hand();
		h = SetHand(Pair, h);

		boolean bActualHandPair = Hand.isHandStraightFlush(h, hs);
		boolean bExpectedHandPair = true;

		// Did this evaluate to Straight Flush?
		assertEquals(bActualHandPair, bExpectedHandPair);
		// Was the highest card a Ten?
		assertEquals(hs.getHiHand(), eRank.TEN.getiRankNum());
		// No test for kickers because there are no kickers
	}

	@Test
	public void TestFlush() {
		HandScore hs = new HandScore();
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.SPADES, eRank.QUEEN, 0));
		Pair.add(new Card(eSuit.SPADES, eRank.EIGHT, 0));
		Pair.add(new Card(eSuit.SPADES, eRank.SIX, 0));
		Pair.add(new Card(eSuit.SPADES, eRank.FIVE, 0));
		Pair.add(new Card(eSuit.SPADES, eRank.TWO, 0));

		Hand h = new Hand();
		h = SetHand(Pair, h);

		boolean bActualHandPair = Hand.isHandFlush(h, hs);
		boolean bExpectedHandPair = true;

		// Did this evaluate to Flush?
		assertEquals(bActualHandPair, bExpectedHandPair);
		// Was the highest card an Ace?
		assertEquals(hs.getHiHand(), eRank.QUEEN.getiRankNum());
		// No test for kickers because there are no kickers
	}

	@Test
	public void TestStraight() {
		HandScore hs = new HandScore();
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.SPADES, eRank.SEVEN, 0));
		Pair.add(new Card(eSuit.HEARTS, eRank.SIX, 0));
		Pair.add(new Card(eSuit.DIAMONDS, eRank.FIVE, 0));
		Pair.add(new Card(eSuit.CLUBS, eRank.FOUR, 0));
		Pair.add(new Card(eSuit.SPADES, eRank.THREE, 0));

		Hand h = new Hand();
		h = SetHand(Pair, h);

		boolean bActualHandPair = Hand.isHandStraight(h, hs);
		boolean bExpectedHandPair = true;

		// Did this evaluate to Straight?
		assertEquals(bActualHandPair, bExpectedHandPair);
		// Was the highest card an Ace?
		assertEquals(hs.getHiHand(), eRank.SEVEN.getiRankNum());
		// No test for kickers because there are no kickers
	}

}