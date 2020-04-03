package it.whiskstudio.model;

import it.whiskstudio.utilities.Utilities;

import java.util.*;

public class Game {
    //private int id;
    private int version;
    private List<Player> players;
    private List<String> occasioni;
    private List<String> esperienze;
    private ArrayList<String> colors;
    private ListIterator<String> colorsIterator;

    // constructors

    public Game() {
        this.occasioni = Utilities.inizializzaMazzo("CarteOccasioni.txt");
        this.esperienze = Utilities.inizializzaMazzo("CarteEsperienza.txt");
        this.colors = Utilities.inizializzaColori();
        this.colorsIterator = colors.listIterator();
        this.players = new ArrayList<>();
        this.version = 0;

        if(occasioni.isEmpty() || esperienze.isEmpty()){
            System.out.println("Nessuna carta caricata");
        }
    }

    public int incrementaVersione (){ version++; return version;}

    // getters

    public int getVersion(){return this.version;}

    public List<Player> getPlayers() {
        return players;
    }

    public boolean addPlayer(String nomeGiocatore) {
        if(doublePlayer(nomeGiocatore)) return false;
        return this.players.add(new Player(nomeGiocatore));
    }

    private boolean doublePlayer(String nomeGiocatore) {
        Iterator<Player> i = this.players.iterator();
        while(i.hasNext()){
            Player p = (Player) i.next();
            if (p.getNome().equalsIgnoreCase(nomeGiocatore)) return true;
        }
        return false;
    }

    public String nextColor() {
        String color = null;
        if(colorsIterator.hasNext()){
            color =  colorsIterator.next();
            colorsIterator.remove();
        }
        if(colorsIterator.hasPrevious()){
            color =  colorsIterator.previous();
            colorsIterator.remove();
        }
        return color;
    }

    public boolean hasplayer(String nomegiocatore) {
        Iterator<Player> i = players.iterator();
        while (i.hasNext()){ if(i.next().getNome().equals(nomegiocatore)) return true; }
        return false;
    }

    public void removePlayer(String nomegiocatore) {
        if(players.removeIf(player -> { return player.getNome().equalsIgnoreCase(nomegiocatore);}))
            colorsIterator.add(nomegiocatore);
    }

    public void pescaOccasione(String nomegiocatore) {
        Iterator<Player> i = players.iterator();
        while(i.hasNext()){
            Player p = i.next();
            if(p.getNome().equalsIgnoreCase(nomegiocatore)){
                try {
                    String occasione = occasioni.remove(0);
                    if(occasione != null) p.setOccasione(occasione);
                    return;
                } catch (Exception e){
                    System.out.println("Carte finite - non è un problema");
                    return;
                }
            }
        }
        return;
    }

    public void pescaEsperienza(String nomegiocatore) {
        Iterator<Player> i = players.iterator();
        while(i.hasNext()){
            Player p = i.next();
            if(p.getNome().equalsIgnoreCase(nomegiocatore)){
                try {
                    String esperienza = esperienze.remove(0);
                    if(esperienza != null) p.setOccasione(esperienza);
                    return;
                } catch (Exception e){
                    System.out.println("Carte finite - non è un problema");
                    return;
                }
            }
        }
        return;
    }

    public Player getPlayer(String nomegiocatore) {
        Iterator<Player> i = players.iterator();
        while(i.hasNext()){
            Player p = i.next();
            if(p.getNome().equalsIgnoreCase(nomegiocatore)){
                return p;
            }
        }
        return null;
    }
}