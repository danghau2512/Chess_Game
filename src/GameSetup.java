public class GameSetup {
    private final PlayerType whiteType;
    private final PlayerType blackType;
    private final int searchDepth;

    public GameSetup(PlayerType whiteType, PlayerType blackType, int searchDepth) {
        this.whiteType = whiteType;
        this.blackType = blackType;
        this.searchDepth = searchDepth;
    }

    public PlayerType getWhiteType() {
        return whiteType;
    }

    public PlayerType getBlackType() {
        return blackType;
    }

    public int getSearchDepth() {
        return searchDepth;
    }
}
