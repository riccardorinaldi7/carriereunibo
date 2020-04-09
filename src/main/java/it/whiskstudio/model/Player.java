package it.whiskstudio.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {
    private String nome;
    private int denaro;
    private List<String> occasioni;
    private List<String> esperienze;

    public Player(String nome){
        this.nome = nome;
        this.denaro = 0;
        this.occasioni = new ArrayList<String>();
        this.esperienze = new ArrayList<String>();
    }

    public String getNome() {
        return nome;
    }

    public int getDenaro() {
        return denaro;
    }

    public List<String> getOccasioni() {
        return occasioni;
    }

    public List<String> getEsperienze() {
        return esperienze;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nome='" + nome + '\'' +
                '}';
    }

    public void setOccasione(String occasione) {
        occasioni.add(occasione);
    }

    public void updateDenaro(int amount) {
        this.denaro += amount;
    }

    public void setEsperienza(String esperienza) {
        esperienze.add(esperienza);
    }

    public String removeCard(String carta) {
        Iterator<String> occIter = this.occasioni.iterator();
        while(occIter.hasNext()){
            String s = occIter.next();
            if(s.equalsIgnoreCase(carta)){
                this.occasioni.remove(s);
                return "occasione";
            }
        }
        Iterator<String> espIter = this.esperienze.iterator();
        while(espIter.hasNext()){
            String s = espIter.next();
            if(s.equalsIgnoreCase(carta)){
                this.esperienze.remove(s);
                return "esperienza";
            }
        }
        return "";
    }
}
