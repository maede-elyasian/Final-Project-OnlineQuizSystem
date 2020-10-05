$(document).ready(function(){
    $("#update_customer_form").submit(function(evt) {
        evt.preventDefault();
        try {
            let quizId = $("#quiz_id").val();
            console.log(quizId + ' in updating');

            let formData = {
                title : $("#title").val(),
                startDate :  $("#start_date").val(),
                endDate: $("#end_date").val(),
                time: $("#time").val(),
                description: $("#description").val()
            }

            $.ajax({
                url: '/quiz/update/' + quizId ,
                type: 'PUT',
                contentType : "application/json",
                data: JSON.stringify(formData),
                dataType : 'json',
                async: false,
                cache: false,
                success: function (response) {
                    let quiz = response;
                    let quizString = "{title:" + quiz.title +
                        " ,startDate:" + quiz.startDate +
                        ", endDate:" + quiz.endDate +
                        ", time:" + quiz.time  +
                        ", description:" + quiz.description  +
                        "}"
                    let updateMessage = 'Quiz Updated Successfully';
                    let successAlert = '<div class="alert alert-success alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                        '<strong>' + updateMessage + '</strong> Quiz\'s Info = ' + quizString+
                    '</div>'

                    $("#" + quizId + " td.td_first_name").text(quiz.title);
                    $("#" + quizId + " td.td_start_date").text(quiz.startDate);
                    $("#" + quizId + " td.td_end_date").text(quiz.endDate);
                    $("#" + quizId + " td.td_time").text(quiz.time);
                    $("#" + quizId + " td.td_description").text(quiz.description);

                    $("#response").empty();
                    $("#response").append(successAlert);
                    $("#response").css({"display": "block"});
                },

                error: function (response) {
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                        '<strong>' + "Unable to update quiz" + '</strong>' + ' ,Error: ' +
                        '</div>';

                    $("#response").empty();
                    $("#response").append(errorAlert);
                    $("#response").css({"display": "block"});
                }
            });
        } catch(error){
            console.log(error);
            alert(error);
        }
    });

    $(document).on("click", "#quizTable button.btn_edit", function(){
        let id_of_button = (event.srcElement.id);
        let quizId = $(this).parent().find('#edit').attr('value');
        console.log(quizId + " QUIZ ID IN UPDATE");
        $.ajax({
            url: '/quiz/search/' + quizId,
            type: 'GET',
            success: function(response) {
                let quiz = response;
                $("#quiz_id").val(quiz.id);
                $("#title").val(quiz.title);
                $("#start_date").val(quiz.startDate);
                $("#end_date").val(quiz.endDate);
                $("#time").val(quiz.time);
                $("#description").val(quiz.description);
                $(".close-icon").css({"display": "block"});
                $("#div_customer_updating").css({"display": "block"});
            },
            error: function(error){
                console.log(error);
                alert("Error -> " + error);
            }
        });
    });
});