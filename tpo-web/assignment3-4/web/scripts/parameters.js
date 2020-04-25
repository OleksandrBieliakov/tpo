function callback(response) {
    $("sum").text(response.sum);
}

function ajax_get() {
    $.get(
        "/addition",
        {
            p1: $("#number1").val(),
            p2: $("#number2").val(),
        },
        callback,
        "json");
}

function ajax_post() {
    $.post(
        "/addition",
        {
            p1: $("#number1").val(),
            p2: $("#number2").val(),
        },
        callback,
        "json");
}

$("#number1").focusout(ajax_post);
$("#number2").focusout(ajax_get);