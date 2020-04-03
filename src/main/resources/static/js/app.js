function validate() {
    var name = document.getElementById("name").value;
    if (name == '') {
        alert('Please enter a valid name.');
        return false;
    } else {
        return true;
    }
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function printGameCookie(){
    return "id=" + getCookie("idpartita") + " - colore=" + getCookie("nomegiocatore");
}

function getHomeUrl(){ return 'https://carriereunibo.herokuapp.com/' /*'http://localhost:8080/'*/;}
