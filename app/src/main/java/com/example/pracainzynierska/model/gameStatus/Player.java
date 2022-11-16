package com.example.pracainzynierska.model.gameStatus;

import com.example.pracainzynierska.model.ArmyToken;

import java.util.List;

public class Player {

    private int id;

    private List<ArmyToken> chosenArmy;

    private List<ArmyToken> lobby;

    private List<ArmyToken> draft;

    private List<ArmyToken> discardToken;

    public int getId() {
        return id;
    }

    public List<ArmyToken> getChosenArmy() {
        return chosenArmy;
    }

    public List<ArmyToken> getLobby() {
        return lobby;
    }

    public List<ArmyToken> getDraft() {
        return draft;
    }

    public List<ArmyToken> getDiscardToken() {
        return discardToken;
    }

    public void setChosenArmy(List<ArmyToken> chosenArmy) {
        this.chosenArmy = chosenArmy;
    }

    public void setLobby(List<ArmyToken> lobby) {
        this.lobby = lobby;
    }

    public void setDraft(List<ArmyToken> draft) {
        this.draft = draft;
    }

    public void setDiscardToken(List<ArmyToken> discardToken) {
        this.discardToken = discardToken;
    }

    // mozemy dobrac do 3 tokenów chyba ze mamy w lobby juz jakies np. jak mamy jednego tokena to mozemy dobrac 2
    public int getLimitTokenToDraft(){
        return 3- lobby.size();
    }

}
