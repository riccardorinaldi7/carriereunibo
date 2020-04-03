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
    <h1>Le Carriere Unibo</h1>
    <hr>
    <div class="wrap">
        <button onclick="creaPartita()">Crea Partita</button>
        <br><br>
        <input type="text" id="idpartita" name="idpartita" placeholder="ID Partita"><br>
        <button onclick="trovaPartita()">Unisciti alla partita</button>
    </div>
    <script>
        function creaPartita() {
            if(document.cookie.length != 0){
                if(!confirm("C'è già una partita creata, vuoi sovrascrivere i dati?\n" + printGameCookie()))
                    return;
            }
            window.location = window.location.href + "creapartita";
        }
        function trovaPartita() {
            var typedid = document.getElementById("idpartita").value;
            if(typedid == ""){
                alert("Campo 'ID Partita' è richiesto");
                return;
            }
            if(document.cookie.length != 0){
                if(getCookie("idpartita") === typedid){
                    window.location = window.location.href + "trovapartita?idpartita=" + typedid;
                    return;
                }
                else{
                    if(confirm("Ci sono dei dati salvati, vuoi sovrascriverli?\n" + printGameCookie()))
                        window.location = window.location.href + "trovapartita?idpartita=" + typedid;
                    else return;
                }
            }
            else window.location = window.location.href + "trovapartita?idpartita=" + typedid;
        }
    </script>
</body>
</html>