package info.mockturtle.android.rockPaperScissors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class Testt {
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();

	// final IGameConfig gameConfig = context.mock(IGameConfig.class);
	@Mock IGameConfig gameConfig;
	// @Mock List<IWeapon> listOfWeapons;
	@Mock IWeapon weapon1;
	@Mock IWeapon weapon2;
	@Mock IWeapon weapon3;
	List<IWeapon> weapons;

	@Before
	public void setUp() throws Exception {
		context.checking(new Expectations() {
			{
				weapons = Arrays.asList(weapon1, weapon2, weapon3);

				allowing(gameConfig).getWeapons();
				will(returnValue(weapons));

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
	public void GameConfigReturnsListOfWeapons() {
		List<IWeapon> w = gameConfig.getWeapons();
		assertThat(w, is(List.class));
		assertThat(w.size(), is(greaterThan(0))); // Matchers.hasItem(is(IWeapon.class)));
		assertThat(w.get(0), is(IWeapon.class));
	}

}
