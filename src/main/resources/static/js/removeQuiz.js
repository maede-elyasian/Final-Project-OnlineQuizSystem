$(document).ready(function () {
    let quizId = 0;
    let courseId = $('#courseId').val();

    $(document).on("click", "#quizTable button.btn_delete", function () {
        quizId = $(this).parent().find('#delete').attr('value');
        console.log(quizId + ' Quiz Id');

        $("div.modal-body")
            .text("Are you sure you want to remove this quiz?");
        $("#model-delete-btn").css({"display": "inline"});
    });

    $(document).on("click", "#model-delete-btn", function () {
        $.ajax({
            url: '/quiz/' + courseId + '/deleteQuiz/' + quizId,
            type: 'DELETE',
            success: function (response) {
                $("div.modal-body")
                    .text("Quiz Successfully Removed!");

                $("#model-delete-btn").css({"display": "none"});
                $("button.btn.btn-secondary").text("Close");
                let row_id = "tr_" + quizId;
                $("#" + row_id).remove();
                window.location.reload();
            },
            error: function (error) {
                console.log(error);
                alert("Error -> " + error);
            }
        });
    });
});