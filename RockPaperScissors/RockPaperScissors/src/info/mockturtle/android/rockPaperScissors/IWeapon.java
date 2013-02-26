package info.mockturtle.android.rockPaperScissors;

import java.util.List;

public interface IWeapon {
	String getName();

	List<IWeapon> beats();

	boolean doesBeat(IWeapon weapon);
}