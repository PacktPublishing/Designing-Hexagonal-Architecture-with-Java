var wsocket;

function connect() {
    wsocket = new WebSocket("ws://localhost:8887");
    wsocket.onopen = onopen;
    wsocket.onmessage = onmessage;
    wsocket.onclose = onclose;
}

    function onopen() {
    console.log("Connected!");
}

    function onmessage(event) {
    console.log("Data received: " + event.data);
    var tag = document.createElement("div");
    tag.id = "message";
    var text = document.createTextNode(">>"+event.data);
    tag.appendChild(text);
    var element = document.getElementById("events");
    element.appendChild(tag);
}

    function onclose(e) {
    console.log("Connection closed.");
}
window.addEventListener("load", connect, false);