package App.Game.StatusBar;

import App.Game.GameModule;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lichk on 12/04/2017.
 */
public class StatusBarComponent extends BorderPane {

    SharedModule shared;
    GameModule game;

    @FXML
    private Text statusText;

    @FXML
    private Text player1Name;
    @FXML
    private Text player1Score;

    @FXML
    private Text player2Name;
    @FXML
    private Text player2Score;

    @FXML
    private Text player3Name;
    @FXML
    private Text player3Score;

    @FXML
    private Text player4Name;
    @FXML
    private Text player4Score;

    private Map<Integer, Text> playerNames;
    private Map<Integer, Text> playerScores;

    public StatusBarComponent(SharedModule shared, GameModule game) {
        this.shared = shared;
        this.game = game;
        this.shared.getJFX().loadFXML(this, this.getClass(),
                "StatusBarComponent.fxml");
        this.playerNames = new TreeMap<>();
        this.playerScores = new TreeMap<>();

        this.playerNames.put(1, this.player1Name);
        this.playerNames.put(2, this.player2Name);
        this.playerNames.put(3, this.player3Name);
        this.playerNames.put(4, this.player4Name);

        this.playerScores.put(1, this.player1Score);
        this.playerScores.put(2, this.player2Score);
        this.playerScores.put(3, this.player3Score);
        this.playerScores.put(4, this.player4Score);
    }

    public void setStatusText(String value) {
        if (this.statusText != null) {
            this.statusText.setText(value);
        }
    }

    public void setPlayerName(Integer player, String name) {
        Text playerName = this.playerNames.get(player);
        if (playerName != null) {
            playerName.setText(name);
        }
    }

    public void setPlayerScore(Integer player, String score) {
        Text playerScore = this.playerScores.get(player);
        if (playerScore != null) {
            playerScore.setText(score);
        }
    }
}
