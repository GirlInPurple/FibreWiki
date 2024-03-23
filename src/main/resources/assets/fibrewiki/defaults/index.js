$(document).ready(function() {
    $.getJSON("data.json", function(j){
        console.log(j);
        $("title").text(j.wiki_name);
    });

    console.log("Readied!")
});