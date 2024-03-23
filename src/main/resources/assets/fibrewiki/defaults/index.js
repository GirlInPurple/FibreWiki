$(document).ready(function() {
    $.getJSON("data.json", function(j){
        $("title").text(j.wiki_name);
    });

    $('.logo').click(function() {
        window.location.href = 'index.html';
    });
});