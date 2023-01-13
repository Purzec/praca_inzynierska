package com.example.pracainzynierska.model.gameStatus;

import com.example.pracainzynierska.model.ArmyToken;
import com.example.pracainzynierska.model.DTO.ArmyTokenDto;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private int id;

    private String nick;

    private List<ArmyTokenDto> chosenArmy;

    private List<Integer> lobbyID;

    private List<ArmyToken> draft;

    private List<ArmyToken> discardToken;

    private String roleLobby;

    private int hpBoss;

    private int idChosenArmy;

    private int etap;

    private boolean flag;

    public int getIdChosenArmy() {
        return idChosenArmy;
    }

    public void setIdChosenArmy(int idChosenArmy) {
        this.idChosenArmy = idChosenArmy;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public List<ArmyTokenDto> getChosenArmy() {
        return chosenArmy;
    }


    public int getHpBoss() {
        return hpBoss;
    }

    public void setHpBoss(int hpBoss) {
        this.hpBoss = hpBoss;
    }

    public List<ArmyToken> getDraft() {
        return draft;
    }

    public List<ArmyToken> getDiscardToken() {
        return discardToken;
    }

    public void setChosenArmy(List<ArmyTokenDto> chosenArmy) {
        this.chosenArmy = chosenArmy;
    }


    public void setDraft(List<ArmyToken> draft) {
        this.draft = draft;
    }

    public void setDiscardToken(List<ArmyToken> discardToken) {
        this.discardToken = discardToken;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

   /* // mozemy dobrac do 3 tokenów chyba ze mamy w lobby juz jakies np. jak mamy jednego tokena to mozemy dobrac 2
    public int getLimitTokenToDraft(){
        return 3- lobby.size();
    }*/

    public Player() {
        this.lobbyID = new ArrayList<>();
    }

    public String getRoleLobby() {
        return roleLobby;
    }

    public int getEtap() {
        return etap;
    }

    public void setEtap(int etap) {
        this.etap = etap;
    }

    public void setRoleLobby(String roleLobby) {
        this.roleLobby = roleLobby;
    }

    public List<Integer> getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(List<Integer> lobbyID) {
        this.lobbyID = lobbyID;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", chosenArmy=" + chosenArmy +
                ", lobby=" + lobbyID +
                ", draft=" + draft +
                ", discardToken=" + discardToken +
                ", roleLobby='" + roleLobby + '\'' +
                ", etap=" + etap +
                '}';
    }
}
