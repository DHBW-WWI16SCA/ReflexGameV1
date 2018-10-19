package ersteapp.dietzm.de.reflexgame.ersteapp.dietzm.de.reflexgame.db.model;

public class HighscoreEntry {

    private String player;
    private int level;
    private int score;

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
