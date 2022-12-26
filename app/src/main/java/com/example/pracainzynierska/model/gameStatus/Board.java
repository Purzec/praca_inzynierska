package com.example.pracainzynierska.model.gameStatus;

import com.example.pracainzynierska.model.Hex;
import com.example.pracainzynierska.model.view.HexBoard;

import java.util.List;

public class Board {

    private List<Player> players;

    private List<Hex> hexBoard;

    private Player player1;

    private String message;

    private Player player2;

    private String round;




    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
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
}
