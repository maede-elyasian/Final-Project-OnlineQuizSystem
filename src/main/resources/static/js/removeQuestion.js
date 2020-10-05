$(document).ready(function () {
    let questionId = 0;
    let quizId = $('#quizId').val();

    $(document).on("click", "#questionTable button.btn_delete", function () {
        questionId = $(this).parent().find('#delete').attr('value');
        console.log(questionId + ' Question Id');

        $("div.modal-body")
            .text("Are you sure you want to remove this question from quiz?");
        $("#model-delete-btn").css({"display": "inline"});
    });

    $(document).on("click", "#model-delete-btn", function () {
        $.ajax({
            url: '/question/' + questionId + '/remove/' + quizId,
            type: 'DELETE',
            success: function () {
                $("div.modal-body")
                    .text("Question Successfully Removed!");

                $("#model-delete-btn").css({"display": "none"});
                $("button.btn.btn-secondary").text("Close");
                window.location.reload();
            },
            error: function (error) {
                console.log(error);
                alert("Error -> " + error);
            }
        });
    });
});