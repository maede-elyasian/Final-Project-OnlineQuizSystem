$(document).ready(function () {
    get_descriptive();
});

function get_descriptive() {
    let offset = parseInt($('#offset').val());
    let quizId = $("#quizId").val();
    console.log(offset + " offset");
    console.log(quizId + " quizId");

    $.ajax({
        url: '/question/descriptive/' + quizId,
        method: 'GET',
        data: {
            offset: offset
        },
        contentType: 'application/json; charset=utf-8',
        async: false,
        cache: false,
        success: function (response) {
            $('#descriptive h3').empty();
            $.each(response.questionList, (i, question) => {
                $('#btn_previous').show();
                let ques = question.content;
                console.log(ques);

                $('#offset').attr('value', offset += 1);
                console.log("after off " + offset);
                console.log("total " + response.totalElements);
                if (offset != response.totalElements-1) {
                    let answer_div = "<textarea class=\"form-control\" cols=\"10\" rows=\"5\"></textarea>";
                    $('#descriptive h3').append(ques);
                    $('#descriptive #answer').append(answer_div);
                } else {
                    $('#btn_next').hide();
                    $('#btn_submit').show();
                }
            });
        }
    });
}

// function show_next() {
//     let questionDiv = $("#descriptive");
//     let offset = parseInt($('#offset').val());
//     let quizId = $("#quizId").val();
//     console.log(offset + " offset");
//     console.log(quizId + " quizId");
//
//     $.ajax({
//         url: '/question/start/' + quizId,
//         method: 'GET',
//         data: {
//             offset: offset
//         },
//         contentType: 'application/json; charset=utf-8',
//         async: false,
//         cache: false,
//         success: function (response) {
//             $('#descriptive h3').empty();
//             if (offset != 5) {
//                 $.each(response.descriptive, (i, question) => {
//                     $('#btn_previous').show();
//                     let ques = question.content;
//
//                     $('#offset').attr('value', offset += 1);
//                     console.log("after off " + offset)
//                     $('#descriptive h3').append(ques);
//
//                 });
//             } else {
//                 $('#btn_next').hide();
//                 $('#btn_submit').show();
//             }
//         },
//         error: function () {
//             console.log();
//             alert("error")
//         }
//     });
// }
//
// function show_prev() {
//     let questionDiv = $("#descriptive");
//     let offset = parseInt($('#offset').val());
//     let quizId = $("#quizId").val();
//
//     $.ajax({
//         url: '/question/start/' + quizId,
//         method: 'GET',
//         data: {
//             offset: offset
//         },
//         contentType: 'application/json; charset=utf-8',
//         async: false,
//         cache: false,
//         success: function (response) {
//             $('#descriptive h3').empty();
//             if (offset != 0 && offset < 5) {
//
//                 $.each(response.descriptive, (i, question) => {
//                     let ques = question.content;
//                     $('#offset').attr('value', offset -= 1
//                     );
//                     console.log("in prevvvvvvvvvvvvvvv");
//                     console.log("in prev offff: " + offset);
//                     $('#descriptive h3').append(ques);
//                 });
//
//             } else {
//                 $('#btn_previous').hide();
//                 console.log("in prevvvvvvvvvvvvvvv elssssssssss");
//
//             }
//         },
//         error: function () {
//             console.log();
//             alert("error")
//         }
//     });
//
// }