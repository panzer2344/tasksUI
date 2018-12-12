


function solve(){

    var data = {
        squareCoeff: $( '#squareCoeff' ).val(),
        linearCoeff: $( '#linearCoeff' ).val(),
        freeCoeff: $( '#freeCoeff' ).val()
    };

    $.ajax({
        method: "POST",
        url : "/solveSquare",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        data : JSON.stringify(data),
        success: function(data) {
            console.log(data);
            $( '#x1Answer' ).text(data['x1']);
            $( '#x2Answer' ).text(data['x2']);
        }
    });

}

$(document).ready( function () {
    $('#submitSolver').click( function () {
        solve();
    } );
});
