package it.whiskstudio.controller;

import it.whiskstudio.model.AppSession;
import it.whiskstudio.model.Game;
import it.whiskstudio.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ConcurrentModificationException;

@Controller
public class HomeController {

    @Autowired
    private AppSession appSession;

    @GetMapping("/")
    public String homePage(Model model) {
        return "index";
    }

    @GetMapping("/creapartita")
    public String creaPartita(Model model, HttpServletResponse response, @CookieValue(defaultValue = "") String nomegiocatore, @CookieValue(defaultValue = "") String idpartita) {

        if (!nomegiocatore.equals("") && !idpartita.equals("")) {
            Game g = appSession.getGame(idpartita);
            if (g != null) g.removePlayer(nomegiocatore);
        }

        //creo una nuova partita, ci aggiungo il giocatore "red" e la aggiungo alla gamelist
        int id = appSession.getRandom().nextInt(100000);
        String nomeGiocatore = "red";
        Game game = new Game();
        game.addPlayer(nomeGiocatore);
        appSession.addGame(id, game);

        //setto i cookie per idpartita e nomegiocatore
        Cookie idcookie = new Cookie("idpartita", String.valueOf(id));
        Cookie namecookie = new Cookie("nomegiocatore", nomeGiocatore);
        response.addCookie(idcookie);
        response.addCookie(namecookie);

        //scrivo i campi per la pagina successiva
        model.addAttribute("idpartita", id);
        model.addAttribute("nomegiocatore", nomeGiocatore);
        return "creapartita";
    }

    @GetMapping("/trovapartita")
    public String trovaPartita(Model model, HttpServletResponse response, @RequestParam String idpartita, @CookieValue(defaultValue = "") String nomegiocatore, @CookieValue(name = "idpartita", defaultValue = "") String idpartitacookie) {
        Game game = appSession.getGame(idpartita);
        model.addAttribute("idpartita", idpartita);

        if (game != null) {
            System.out.println(idpartita + " - " + idpartitacookie + " - " + nomegiocatore);
            if (game.hasplayer(nomegiocatore) && idpartita.equalsIgnoreCase(idpartitacookie)) {
                model.addAttribute("giocatori", game.getPlayers().toString());
                return "partitatrovata";
            } else {
                String nome = game.nextColor();

                if (nome != null && game.addPlayer(nome)) {
                    game.incrementaVersione();
                    //setto i cookie per idpartita e nomegiocatore
                    Cookie idcookie = new Cookie("idpartita", String.valueOf(idpartita));
                    Cookie namecookie = new Cookie("nomegiocatore", nome);
                    response.addCookie(idcookie);
                    response.addCookie(namecookie);

                    model.addAttribute("giocatori", game.getPlayers().toString());
                    return "partitatrovata";
                } else {
                    model.addAttribute("messaggioerrore", "Raggiunto massimo numero giocatori");
                    return "error";
                }
            }
        } else {
            model.addAttribute("messaggioerrore", "Partita non trovata");
            return "error";
        }

    }

    @GetMapping("/partita")
    public String partita(Model model, @CookieValue(defaultValue = "") String idpartita) {
        if (idpartita == "") {
            model.addAttribute("messaggioerrore", "Non ho un id partita");
            model.addAttribute("idpartita", idpartita);
            return "error";
        }
        Game g = appSession.getGame(idpartita);
        if (g == null) {
            model.addAttribute("messaggioerrore", "Non trovo questa partita nell'applicazione");
            model.addAttribute("idpartita", idpartita);
            return "error";
        }
        model.addAttribute("players", g.getPlayers());
        model.addAttribute("version", g.getVersion());
        return "partita";
    }

    @GetMapping("/cartaoccasione")
    public String pescaOccasione(Model model, @CookieValue(defaultValue = "") String nomegiocatore, @CookieValue(defaultValue = "") String idpartita) {
        if(!nomegiocatore.equals("") && !idpartita.equals("")){
            Game game = appSession.getGame(idpartita);
            game.pescaOccasione(nomegiocatore);
            model.addAttribute("players", game.getPlayers());
            model.addAttribute("version", game.incrementaVersione());
        }
        return "partita";
    }

    @GetMapping("/cartaesperienza")
    public String pescaEsperienza(Model model, @CookieValue(defaultValue = "") String nomegiocatore, @CookieValue(defaultValue = "") String idpartita) {
        if(!nomegiocatore.equals("") && !idpartita.equals("")){
            Game game = appSession.getGame(idpartita);
            game.pescaEsperienza(nomegiocatore);
            model.addAttribute("players", game.getPlayers());
            model.addAttribute("version", game.incrementaVersione());
        }
        return "partita";
    }

    @GetMapping("/updatemoney")
    public String aggiornaDenaro(Model model, @RequestParam int amount, @CookieValue(defaultValue = "") String nomegiocatore, @CookieValue(defaultValue = "") String idpartita) {
        if(!nomegiocatore.equals("") && !idpartita.equals("")){
            Game game = appSession.getGame(idpartita);
            game.getPlayer(nomegiocatore).updateDenaro(amount);
            model.addAttribute("players", game.getPlayers());
            model.addAttribute("version", game.incrementaVersione());
        }
        return "partita";
    }

    @GetMapping("/checkupdate")
    @ResponseBody
    public String checkGameVersion(Model model, @CookieValue(defaultValue = "") String nomegiocatore, @CookieValue(defaultValue = "") String idpartita) {
        if(!nomegiocatore.equals("") && !idpartita.equals("")){
            Game game = appSession.getGame(idpartita);
            model.addAttribute("version", game.getVersion());
            return String.valueOf(game.getVersion());
        }
        return "0";
    }

    @PostMapping("/usecard")
    public String usaCarta(Model model, @RequestParam String carta, @CookieValue(defaultValue = "") String nomegiocatore, @CookieValue(defaultValue = "") String idpartita) {
        Game game = appSession.getGame(idpartita);
        if(carta != ""){
            game.getPlayer(nomegiocatore).removeCard(carta);
            game.incrementaVersione();
        }
        model.addAttribute("players", game.getPlayers());
        model.addAttribute("version", game.getVersion());
        return "partita";
    }

    @PostMapping("/sendcard")
    public String inviaCarta(Model model, @RequestParam String carta, @RequestParam String recipient, @CookieValue(defaultValue = "") String nomegiocatore, @CookieValue(defaultValue = "") String idpartita) {
        Game game = appSession.getGame(idpartita);
        if(carta != "" && recipient != ""){
            String tipoCarta = game.getPlayer(nomegiocatore).removeCard(carta);
            Player destinatario = game.getPlayer(recipient);
            if(tipoCarta.equalsIgnoreCase("occasione")) destinatario.setOccasione(carta);
            else if(tipoCarta.equalsIgnoreCase("esperienza")) destinatario.setEsperienza(carta);
            game.incrementaVersione();
        }
        model.addAttribute("players", game.getPlayers());
        model.addAttribute("version", game.getVersion());
        return "partita";
    }


}
