function callback(response) {
    console.log("response " + JSON.stringify(response))
    $("#sum").text(response.sum);
}

let num1 = $("#number1");
let num2 = $("#number2");

function ajax_get() {
    console.log("get " + num1.val() + " " +  num2.val())
    $.get(
        "/addition",
        {
            p1: num1.val(),
            p2: num2.val(),
        },
        callback,
        "json");
}

function ajax_post() {
    console.log("post " + num1.val() + " " +  num2.val())
    $.post(
        "/addition",
        {
            p1: num1.val(),
            p2: num2.val(),
        },
        callback,
        "json");
}

num1.focusout(ajax_post);
num2.focusout(ajax_get);