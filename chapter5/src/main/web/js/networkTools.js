function addNetworkToRouter() {
    const routerId = document.getElementById("routerId").value;
    const address = document.getElementById("address").value;
    const name = document.getElementById("name").value;
    const cidr = document.getElementById("cidr").value;
    const xhttp = new XMLHttpRequest();
    xhttp.open("GET", "http://localhost:8080/network/add?routerId="+routerId+"&"+
        "address="+address+"&"+
        "name="+name+"&"+
        "cidr="+cidr, true);
    xhttp.onload = function () {
        if (xhttp.status === 200) {
            document.getElementById("message").innerHTML = "Network added with success!"
        } else{
            document.getElementById("message").innerHTML = "An error occurred while trying to add the network."
        }
    };
    xhttp.send();

}

function getRouter() {
    const routerId = document.getElementById("routerId").value;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        console.log(this.responseText);
        if (this.readyState == 4 && this.status == 200) {
            const json = JSON.parse(this.responseText)
            createTree(json)
        }
    };
    xhttp.open("GET", "http://localhost:8080/network/get?routerId="+routerId, true);
    xhttp.send();
}

function createTree(json) {
    const container = document.getElementById("container");
    const vt = new VTree(container);
    const reader = new VTree.reader.Object();

    var data = reader.read(json);
    vt.data(data).update();
}