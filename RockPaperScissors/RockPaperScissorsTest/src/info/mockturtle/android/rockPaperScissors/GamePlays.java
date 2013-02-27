package info.mockturtle.android.rockPaperScissors;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class GamePlays {
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();

	IGame sut;

	@Mock IPlayer player1;
	@Mock IPlayer player2;

//	@Mock IWeapon firstWeapon;
//	@Mock IWeapon weapon2;
//	@Mock IWeapon weapon3;
	
	@Mock IWeapon firstWeapon;
	@Mock IWeapon beatsFirstWeapon;
	@Mock IWeapon isBeatByFirstWeapon;

	@Mock IGameConfig gameConfig;
	List<IWeapon> weaponList;

	@Before
	public void setUp() throws Exception {
		sut = new Game(gameConfig);
		sut.add(player1);
		sut.add(player2);

		context.checking(new Expectations() {
			{
				
				allowing(firstWeapon).doesBeat(beatsFirstWeapon);
				will(returnValue(false));
				
//				weaponList = Arrays.asList(firstWeapon, weapon2, weapon3);
//
//				allowing(gameConfig).getWeapons();
//				will(returnValue(weaponList));

//				// allowing(firstWeapon).doesBeat(firstWeapon);
//				// will(returnValue(false));
//				allowing(firstWeapon).doesBeat(weapon2);
//				will(returnValue(true));
//
//				// allowing(weapon2).doesBeat(weapon2);
//				// will(returnValue(false));
//				allowing(weapon2).doesBeat(weapon3);
//				will(returnValue(true));
//
//				// allowing(weapon3).doesBeat(weapon3);
//				// will(returnValue(false));
//				allowing(weapon3).doesBeat(firstWeapon);
//				will(returnValue(true));
			}
		});
	}

	@Test
	public void whenGetPlays() throws Exception {
		context.checking(new Expectations() {
			{
				allowing(player1).getPlay();
				will(returnValue(firstWeapon));

				allowing(player2).getPlay();
				will(returnValue(beatsFirstWeapon));

			}
		});

		Map<IPlayer, IWeapon> plays = sut.getPlays();

		assertThat("should return something", plays, Matchers.notNullValue());
		assertThat("should have size same as players", plays.size(),
				Matchers.equalTo(2));

		assertThat("should contain player1", plays, Matchers.hasKey(player1));
		assertThat("should contain player2", plays, Matchers.hasKey(player2));
		assertThat("should map player1 to firstWeapon", plays,
				Matchers.hasEntry(player1, firstWeapon));
		assertThat("should map player2 to weapon2", plays,
				Matchers.hasEntry(player2, beatsFirstWeapon));

		assertThat("after getplays, we have played", sut.hasPlayed(),
				Matchers.is(true));
	}

	@Test
	public void whenPlayersTie() throws Exception {
		context.checking(new Expectations() {
			{
				allowing(player1).getPlay();
				will(returnValue(firstWeapon));

				allowing(player2).getPlay();
				will(returnValue(firstWeapon));
			}
		});

		sut.getPlays();
		assertThat("should not have a winner", sut.hasWinner(),
				Matchers.is(false));
	}

	@Test
	public void whenPlayer1BeatsPlayer2() throws Exception {
		context.checking(new Expectations() {
			{
				allowing(player1).getPlay();
				will(returnValue(firstWeapon));
				
				allowing(player2).getPlay();
				will(returnValue(isBeatByFirstWeapon));
			}
		});
		
		sut.getPlays();
		IPlayer winner = sut.getWinner();
		
		assertThat("player 1 should be the winner", winner, Matchers.sameInstance(player1));
	}
	@Test
	public void whenPlayer2BeatsPlayer1() throws Exception {
		context.checking(new Expectations() {
			{
				allowing(player1).getPlay();
				will(returnValue(firstWeapon));
				
				allowing(player2).getPlay();
				will(returnValue(beatsFirstWeapon));
			}
		});
		
		sut.getPlays();
		IPlayer winner = sut.getWinner();
		
		assertThat("player 2 should be the winner", winner, Matchers.sameInstance(player2));
	}
}
