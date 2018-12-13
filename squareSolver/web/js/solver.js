

function solve(){

    var data = {
        squareCoeff: $( '#squareCoeff' ).val(),
        linearCoeff: $( '#linearCoeff' ).val(),
        freeCoeff: $( '#freeCoeff' ).val(),
        error : ""
    };

    $.ajax({
        method: "POST",
        url : "/solveSquare",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        data : JSON.stringify(data),
        success: function(data) {
            console.log(data);

            if(data['error'] != 0){
                $('#error').text(data['error']);
            }else {
                $('#x1Answer').text(data['x1']);
                $('#x2Answer').text(data['x2']);
            }
        }
    });

}

$(document).ready( function () {
    $('#submitSolver').click( function () {
        solve();
    } );

    $(window).resize(sizeChanger);

    $('#inputs input').on('input', function () {
        if($(this).val() > 999999){
            $(this).val(999999);
        } else if($(this).val() < -999999){
            $(this).val(-999999);
        }
    })
});

function sizeChanger() {

    /*if($('body').height() < 691){
        $('#answers').css("margin-top", "25px");
    } else {
        $('#answers').css("margin-top", "0px");
    }*/


    var fontSize = Math.round((0.013324) * $('body').width() + (0.009575) * $('body').height());

    $('#solver').css("font-size", fontSize);

    $('#submitSolver').css("width", fontSize * 3.5);
    $('#submitSolver').css("height", fontSize * 1.5);

    $('#submitSolver').css("font-size", fontSize);
    $('#submitSolver').css("text-align", "center");

    if($('body').width() * $('body').height() < 350000) {
        $('#solver').css("font-size", fontSize + 4);

        $('#submitSolver').css("width", (fontSize + 4) * 3.5);
        $('#submitSolver').css("height", (fontSize + 4) * 1.5);

        $('#submitSolver').css("font-size", fontSize + 4);

        $('#answers').css("margin-top", "40px");
    } else {
        $('#answers').css("margin-top", "0px");
    }

    /*if($('body').width() < 512){

        $('#header').css("font-size", "8");

    } else if($('body').width() < 768){

        $('#header').css("font-size", "9");

    } else  if($('body').width() < 898){

        $('#header').css("font-size", "10");

    } else if($('body').width() < 978){

        $('#header').css("font-size", "11");

    } else if($('body').width() < 1140){

        $('#header').css("font-size", "12");

    } else if($('body').width() < 1300){

        $('#header').css("font-size", "14");

    } else if($('body').width() < 1468){

        $('#header').css("font-size", "16");

    } else if($('body').width() < 1600){

        $('#header').css("font-size", "18");

    }*/

}
