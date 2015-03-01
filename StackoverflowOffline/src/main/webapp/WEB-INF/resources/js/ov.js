$(document).ready(function () {
    $("#suche-feld").on("keypress", function (e) {
        if (e.which == 13) {
            $.ajax({
                url: 'http://localhost:8080/StackoverflowOffline/search/' + $("#suche-feld").val(),
                type: 'GET',
                success: function (result) {
                    var questions_result = "";

                    questions_result += "<table>";
                    $.each(result, function (idx, obj) {
                        questions_result += "<tr><td><span>" + (idx + 1) + "</span><a href='/StackoverflowOffline/question/" + obj.id + "'>" + obj.title + "</a></td></tr>";
                    });
                    questions_result += "</table>";

                    $("#questions").html(questions_result);
                }
            });
        }
    });
});