package info.mockturtle.android.rockPaperScissors;

import java.util.List;
import java.util.Map;

public interface IGame {
	void setGameConfig(IGameConfig gameConfig);

	IGameConfig getGameConfig();

	List<IPlayer> getPlayers();

	void add(IPlayer player);

	Map<IPlayer, IWeapon> getPlays();

	boolean hasPlayed();

	IPlayer getWinner();

	boolean hasWinner();

	public abstract void reset();

	public abstract void resetAll();
}