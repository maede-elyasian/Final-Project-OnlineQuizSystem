$(document).ready(function () {
    let courseId = 0;

    $(document).on("click", "#courseTable button.btn_delete", function () {
        courseId = $(this).parent().find('#delete').attr('value');
        console.log(courseId + ' Course Id');

        $("div.modal-body")
            .text("Are you sure you want to remove course with id: " + courseId + "?");
        $("#model-delete-btn").css({"display": "inline"});
    });

    $(document).on("click", "#model-delete-btn", function () {
        $.ajax({
            url: '/course/deleteCourse/' + courseId,
            type: 'DELETE',
            success: function () {
                $("div.modal-body")
                    .text("Course Successfully Removed!");

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