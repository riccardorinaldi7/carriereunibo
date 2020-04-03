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
    <h1>Partita Trovata</h1>
    <p>Codice partita: ${idpartita}</p>
    <p>Giocatori attuali: ${giocatori}</p>
    <form action="/partita"><input type="submit" value="Continua"></form>
</body>
</html>