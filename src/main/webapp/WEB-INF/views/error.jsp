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
<h1>${messaggioerrore}</h1>
<p>Codice partita inserito: ${idpartita}</p>

<button onclick="window.location.href=getHomeUrl()">Torna alla home</button>
</body>
</html>