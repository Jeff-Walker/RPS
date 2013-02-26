package info.mockturtle.android.rockPaperScissors;

import static org.hamcrest.MatcherAssert.*;

import java.util.Arrays;
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

	@Mock IWeapon weapon1;
	@Mock IWeapon weapon2;
	@Mock IWeapon weapon3;

	@Mock IGameConfig gameConfig;
	List<IWeapon> weaponList;

	@Before
	public void setUp() throws Exception {
		sut = new Game(gameConfig);
		sut.add(player1);
		sut.add(player2);

		context.checking(new Expectations() {
			{
				weaponList = Arrays.asList(weapon1, weapon2, weapon3);

				allowing(gameConfig).getWeapons();
				will(returnValue(weaponList));

				// allowing(weapon1).doesBeat(weapon1);
				// will(returnValue(false));
				allowing(weapon1).doesBeat(weapon2);
				will(returnValue(true));

				// allowing(weapon2).doesBeat(weapon2);
				// will(returnValue(false));
				allowing(weapon2).doesBeat(weapon3);
				will(returnValue(true));

				// allowing(weapon3).doesBeat(weapon3);
				// will(returnValue(false));
				allowing(weapon3).doesBeat(weapon1);
				will(returnValue(true));
			}
		});
	}

	@Test
	public void whenGetPlays() throws Exception {
		context.checking(new Expectations() {
			{
				allowing(player1).getPlay();
				will(returnValue(weapon1));

				allowing(player2).getPlay();
				will(returnValue(weapon2));

			}
		});

		Map<IPlayer, IWeapon> plays = sut.getPlays();

		assertThat("should return something", plays, Matchers.notNullValue());
		assertThat("should have size same as players", plays.size(),
				Matchers.equalTo(2));

		assertThat("should contain player1", plays, Matchers.hasKey(player1));
		assertThat("should contain player2", plays, Matchers.hasKey(player2));
		assertThat("should map player1 to weapon1", plays,
				Matchers.hasEntry(player1, weapon1));
		assertThat("should map player2 to weapon2", plays,
				Matchers.hasEntry(player2, weapon2));

		assertThat("after getplays, we have played", sut.hasPlayed(),
				Matchers.is(true));
	}

	@Test
	public void whenPlayersTie() throws Exception {
		context.checking(new Expectations() {
			{
				allowing(player1).getPlay();
				will(returnValue(weapon1));

				allowing(player2).getPlay();
				will(returnValue(weapon1));
			}
		});

		sut.getPlays();
		assertThat("should not have a winner", sut.hasWinner(),
				Matchers.is(false));
	}

	@Test
	public void whenPlayer1BeatsPlayer2() throws Exception {

	}
}
