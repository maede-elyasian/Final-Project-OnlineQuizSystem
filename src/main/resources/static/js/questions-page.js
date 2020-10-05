$(document).ready(function () {
    function fetchQuestions() {
        let quizId = $('#quizId').val();
        console.log(quizId + " QUIZ IDDDDDDDDDDDD");

        $.ajax({
            type: "GET",
            url: "/question/getQuestions/" + quizId,
            success: function (response) {
                $('#questionTable tbody').empty();
                for (var item in response) {
                    // log key , value
                    console.log(item, response[item]);
                }
                    // let editButton = '<a href="#" class="btn btn-link"> Edit</a>';
                    // let saveButton = '<a href="#" class="btn btn-link" style="display: none"> Save</a> |';
                    // let cancel = '<a href="#" class="btn btn-link" style="display: none"> Cancel</a> |';
                    // let deleteButton = '<button id="delete"' +
                    //     ' type="button" value="' + question.id + '" class="btn actionBtn btn_delete" data-toggle="modal" data-target="#delete-modal"' +
                    //     '>delete</button>';
                    //
                    // let tr_id = 'tr_' + key.id;
                    // let quizRow = '<tr id=\"' + tr_id + "\"" + '>' +
                    //     '<td>' + key.title + '</td>' +
                    //     '<td>' + key.content + '</td>' +
                    //     '<td>' + value.point + '</td>' +
                    //     '<td>' + editButton + saveButton + cancel + '</td>' +
                    //
                    //     '</tr>';

                    $('#questionTable tbody').append(quizRow);


            }
        });
    }
});


