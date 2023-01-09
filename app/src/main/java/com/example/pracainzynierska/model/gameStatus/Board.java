package com.example.pracainzynierska.model.gameStatus;

import com.example.pracainzynierska.model.Hex;
import com.example.pracainzynierska.model.view.HexBoard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private Player players = new Player();

    private Player players2 = new Player();

    private List<Hex> hexBoard;

    private Player player1 = new Player();

    private String message;

    private Player player2 = new Player();

    private String round;

    private boolean updating;

    public Board() {
        updating = false;
    }


    public boolean isUpdating() {
        return updating;
    }

    public void setUpdating(boolean updating) {
        this.updating = updating;
    }

    public Player getPlayers() {
        return players;
    }

    public void setPlayers(Player players) {
        this.players = players;
    }

    public Player getPlayers2() {
        return players2;
    }

    public void setPlayers2(Player players2) {
        this.players2 = players2;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   public List<Hex> getHexBoard() {
        return hexBoard;
    }

    public void setHexBoard(List<Hex> hexBoard) {
        this.hexBoard = hexBoard;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("players", players);
        result.put("players2", players2);
        result.put("hexBoard", hexBoard);
        result.put("player1", player1);
        result.put("message", message);
        result.put("player2", player2);
        result.put("round", round);
        result.put("updating", updating);
        return result;
    }
}
