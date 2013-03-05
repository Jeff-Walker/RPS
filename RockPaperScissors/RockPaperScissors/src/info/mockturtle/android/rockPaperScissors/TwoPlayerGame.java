package info.mockturtle.android.rockPaperScissors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoPlayerGame implements IGame {

	private IGameConfig gameConfig;
	private List<IPlayer> players = new ArrayList<IPlayer>();
	private Map<IPlayer, IWeapon> playsMap = new HashMap<IPlayer, IWeapon>();
	private Map<IPlayer, IWeapon> unmodifiablePlaysMap = Collections.unmodifiableMap(playsMap);
	private boolean hasPlayed;
	private IPlayer winner;
	
	public TwoPlayerGame(IGameConfig gameConfig) {
		this.gameConfig = gameConfig;
	}

	@Override
	public void setGameConfig(IGameConfig gameConfig) {
		this.gameConfig = gameConfig;
	}

	@Override
	public IGameConfig getGameConfig() {
		return gameConfig;
	}

	@Override
	public List<IPlayer> getPlayers() {
		return players;
	}

	@Override
	public void add(IPlayer player) {
		players.add(player);
	}
	
	public Map<IPlayer, IWeapon> getPlays_general() {
		if ( players.size() != 2 ) {
			throw new IllegalStateException("need 2 players");
		}
		playsMap.clear();
		winner = null;
		
		Map<IPlayer, IWeapon> playsMap = buildPlayMap();
		IWeapon winningWeapon = null;
		for ( IWeapon w : playsMap.values() ) {
			if ( winningWeapon == null || w.doesBeat(winningWeapon)) {
				winningWeapon = w;
			}
		}
		
		IPlayer winningPlayer = null;
		int winnerCount = 0;
		for ( Map.Entry<IPlayer, IWeapon> e : playsMap.entrySet() ) {
			if ( e.getValue().equals(winningWeapon)) {
				winningPlayer = e.getKey();
				winnerCount++;
			}
		}
		
		if ( winnerCount == 1 ) {
			winner = winningPlayer;
		}
		hasPlayed = true;
		return playsMap;
	}
	
	@Override	
	public Map<IPlayer, IWeapon> getPlays() {
		return getPlays_general();
	}

	private Map<IPlayer, IWeapon> buildPlayMap() {
		Map<IPlayer, IWeapon> map = new HashMap<IPlayer, IWeapon>();
		for ( IPlayer p : players ) {
			IWeapon play = p.getPlay();
			if ( play == null ) {
				throw new IllegalArgumentException("player " + p.getId() + " has null weapon.");
			}
			map.put(p, play);
		}
		return map;
	}

	Map<IPlayer, IWeapon> getPlays_simplistic() {
		if ( players.size() != 2 ) {
			throw new IllegalStateException("need 2 players");
		}
		playsMap.clear();
		winner = null;
		IPlayer one = players.get(0);
		IPlayer two = players.get(1);
		IWeapon weaponOne = one.getPlay();
		IWeapon weaponTwo = two.getPlay();
		playsMap.put(one, weaponOne );
		playsMap.put(two, weaponTwo);
		if ( weaponOne.doesBeat(weaponTwo) ) {
			winner = one;
		} else if ( weaponTwo.doesBeat(weaponOne)) {
			winner = two;
		}
		hasPlayed = true;
		return unmodifiablePlaysMap;
	}

	@Override
	public boolean hasPlayed() {
		return hasPlayed;
	}

	@Override
	public IPlayer getWinner() {
		return winner;
	}

	@Override
	public boolean hasWinner() {
		return winner != null;
	}

	@Override
	public void resetAll() {
		reset();
		players.clear();
	}
	@Override
	public void reset() {
		winner = null;
		hasPlayed = false;
	}
	
}
