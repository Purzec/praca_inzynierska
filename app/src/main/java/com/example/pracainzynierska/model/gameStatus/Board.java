package com.example.pracainzynierska.model.gameStatus;

import com.example.pracainzynierska.model.Hex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private Player p1info;

    private Player p2info;

    private List<Hex> hexBoard;

    private Player player1;

    private String message;

    private Player player2;

    private boolean lastRound = false;

    private boolean updating;

    public Board() {
        updating = false;
    }

    public Player getP1info() {
        return p1info;
    }

    public void setP1info(Player p1info) {
        this.p1info = p1info;
    }

    public Player getP2info() {
        return p2info;
    }

    public void setP2info(Player p2info) {
        this.p2info = p2info;
    }

    public boolean isUpdating() {
        return updating;
    }

    public void setUpdating(boolean updating) {
        this.updating = updating;
    }

    public boolean isLastRound() {
        return lastRound;
    }

    public void setLastRound(boolean lastRound) {
        this.lastRound = lastRound;
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
        result.put("p1info", p1info);
        result.put("p2info", p2info);
        List<Map<String, Object>> hexBoardMap = new ArrayList<>();
        for (Hex hex : hexBoard) {
            hexBoardMap.add(hex.toMap());
        }
        result.put("hexBoard", hexBoardMap);
        result.put("player1", player1);
        result.put("message", message);
        result.put("player2", player2);
        result.put("lastRound", lastRound);
        result.put("updating", updating);
        return result;
    }
}
