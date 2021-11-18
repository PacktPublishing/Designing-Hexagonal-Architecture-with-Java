function myFunction() {
    var x = document.getElementById("myTopnav");
    if (x.className === "topnav neon-input") {
        x.className += " responsive topnav-with-background";
    } else {
        x.className = "topnav neon-input";
    }
}