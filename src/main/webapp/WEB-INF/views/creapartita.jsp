<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <!-- Static content -->
    <link rel="stylesheet" href="/resources/css/style.css">
    <script type="text/javascript" src="/resources/js/app.js"></script>

    <title>Le Carriere Unibo</title>
</head>
<body>
    <h1>Partita creata</h1>
    <p>Codice partita: ${idpartita}</p>
    <p>Nome giocatore: ${nomegiocatore}</p>
    <!--
    <p id="viewcookie"></p>
    <button onclick="readCookie()">Leggi Cookie</button>
    -->
    <form action="/partita">
        <input type="text" name="player" placeholder="Nome giocatore">
        <input type="submit" value="Continua">
    </form>

    <script>
        function readCookie() {
            var paragraphElement = document.getElementById("viewcookie");
            paragraphElement.innerHTML = document.cookie;
        }
    </script>
</body>
</html>