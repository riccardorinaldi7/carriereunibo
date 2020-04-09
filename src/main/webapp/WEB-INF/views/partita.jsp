<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <%
    // Set refresh, autoload time as 10 seconds
    //response.setIntHeader("Refresh", 10);
    %>
    <h1>Benvenuto in partita</h1>
    <!--<div id="version">Versione corrente: ${version}</div>-->
    <div id="demo">Codice partita: </div>

    <c:forEach var="player" items="${players}">
        <%--<c:forEach var="tache" items="${phase.taches}">
            <tr>
                <td>${tache.name}</td>
            </tr>
        </c:forEach>--%>
        <div id="${player.nome}" class="playerdiv">
            <h3>${player.nome}</h3>
            <div id="${player.nome}money"><p>Denaro: <span id="denaro">${player.denaro}</span> €</p></div>

            <c:forEach var="occasione" items="${player.occasioni}">
                <div class="card occasioni">
                    <p>${occasione}</p>
                </div>
            </c:forEach>
            <c:forEach var="esperienza" items="${player.esperienze}">
                <div class="card esperienze">
                    <p>${esperienza}</p>
                </div>
            </c:forEach>
        </div>
    </c:forEach>

    <div id="cardMenu" style="display: none;"></div>

    <script>
        var iddiv = document.getElementById("demo");
        iddiv.innerText += getCookie("idpartita");

        console.log(getCookie("nomegiocatore"))
        var me = getCookie("nomegiocatore");
        var mydiv = document.getElementById(me);
        mydiv.classList.add("itsme");

        var mycards = mydiv.getElementsByClassName("card");
        for (let card of mycards) {
            card.onclick = cardMenu;
        }

        var divmoney = document.getElementById(me+"money");
        var formmoney = document.createElement("form");
        formmoney.action = '/updatemoney';
        var input = document.createElement("input");
        input.type = 'number';
        input.name = 'amount';
        var submit = document.createElement("input");
        submit.type = 'submit';
        submit.name = 'updatemoney';
        submit.value = 'Aggiorna';
        formmoney.appendChild(input);
        formmoney.appendChild(submit);
        divmoney.appendChild(formmoney);

        var formocc = document.createElement("form");
        var buttonocc = document.createElement("input");
        formocc.action = "/cartaoccasione";
        buttonocc.type = "submit";
        buttonocc.value = "Pesca Occasione";
        formocc.appendChild(buttonocc);
        mydiv.appendChild(formocc);

        var formesp = document.createElement("form");
        var buttonesp = document.createElement("input");
        formesp.action = "/cartaesperienza";
        buttonesp.type = "submit";
        buttonesp.value = "Pesca Esperienza";
        formesp.appendChild(buttonesp);
        mydiv.appendChild(formesp);

        function checkUpdate() {
            var xhttp = new XMLHttpRequest();
            var oldversion = ${version};
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    let currentversion = this.responseText;
                    console.log(currentversion + " vs " + oldversion);
                    if(currentversion > oldversion) window.location.href = getHomeUrl() + 'partita';
                    else console.log("non ci sono aggiornamenti");
                }
            };
            xhttp.open("GET", "/checkupdate", true);
            xhttp.send();
        }

        setInterval(checkUpdate, 10000);

        function cardMenu(event){
            var menuDiv = document.getElementById("cardMenu");
            menuDiv.style.display = "block";
            menuDiv.onclick = function(e){
                if(e.target == menuDiv) menuDiv.style.display = "none";
            }
            //svuoto menuDiv
            var child = menuDiv.lastElementChild;
            while (child) {
                menuDiv.removeChild(child);
                child = menuDiv.lastElementChild;
            }
            var useCardForm = document.createElement("form");
            useCardForm.action = "/usecard";
            useCardForm.method = 'post';
            var sendCardForm = document.createElement("form");
            sendCardForm.action = "/sendcard";
            sendCardForm.method = 'post';
            sendCardForm.id = "sendCardForm"
            var useButton = document.createElement("input");
            useButton.type = "submit";
            useButton.name = 'usacarta'
            useButton.value = "Usa";
            var sendButton = document.createElement("input");
            sendButton.type = "submit";
            sendButton.name = 'cedicarta'
            sendButton.value = "Cedi a";
            var sendList = document.createElement("select");
            sendList.name = "recipient";
            sendList.form = sendCardForm;
            sendList.required = true;
            var otherPlayers = getPlayers();
            for(var i=0; i<otherPlayers.length; i++){
               var option = document.createElement("option");
               option.value = otherPlayers[i];
               option.innerText = otherPlayers[i];
               sendList.appendChild(option);
            }
            var cardInput = document.createElement("input");
            cardInput.type = 'text';
            cardInput.readOnly = true;
            cardInput.name = 'carta';
            cardInput.value = event.target.innerText;
            cardInput.classList.add('card');
            console.log(event.target.innerText);
            useCardForm.appendChild(cardInput);
            useCardForm.appendChild(useButton);
            var cardInputForSend = cardInput.cloneNode();
            cardInputForSend.style.display = 'none';
            sendCardForm.appendChild(cardInputForSend);
            sendCardForm.appendChild(sendList);
            sendCardForm.appendChild(sendButton);
            menuDiv.appendChild(useCardForm);
            menuDiv.appendChild(sendCardForm);
        }

        function getPlayers(){
            var players = [];
            var divs = document.getElementsByClassName("playerdiv");
            for(var i=0; i<divs.length; i++){
                if(divs[i].id != me) players.push(divs[i].id)
            }
            return players;
        }

        /*var button = document.createElement("button");
        button.onclick = getOccasione;
        var text = document.createTextNode("Pesca Occasione");
        button.appendChild(text);
        mydiv.appendChild(button);

        function getOccasione() {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    var occasioni = document.getElementById("occasioni");
                    var carta = document.createTextNode(${cartaoccasione});
                    occasioni.appendChild(carta);
                }
            };
            xhttp.open("GET", "/cartaoccasione", true);
            xhttp.send();
        }*/
    </script>
</body>
</html>