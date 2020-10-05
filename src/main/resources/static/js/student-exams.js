$(document).ready(function () {
    let pageNumber = 0;
    let studentId = $("#studentId").val();

    function fetchQuizzes(page) {
        pageNumber = (typeof page !== 'undefined') ? page : 0;
        let courseId = $('#courseId').attr('value');

        $.ajax({
            type: "GET",
            url: "/quiz/getQuizzes?courseId=" + courseId,
            data: {
                page: pageNumber
            },
            success: function (response) {
                $('#quizTable tbody').empty();
                $.each(response.quizList, (i, quiz) => {

                    let startExam = '<a id="questions"' +
                        ' type="button" class="btn btn-info" href="' + "/student/" + studentId + "/start-exam/" + quiz.id + '">Start</a>';

                    console.log(quiz.id + " QUIZ ID");

                    let tr_id = 'tr_' + quiz.id;
                    let quizRow = '<tr id=\"' + tr_id + "\"" + '>' +
                        '<td>' + quiz.title + '</td>' +
                        '<td>' + quiz.startDate.replace('T', ' ') + '</td>' +
                        '<td>' + quiz.endDate.replace('T', ' ') + '</td>' +
                        '<td>' + quiz.description + '</td>' +
                        '<td>' + quiz.time + '</td>' +
                        '<td>' + quiz.teacher.personalInfo.firstName + " " + quiz.teacher.personalInfo.lastName + '</td>' +
                        '<td>' + startExam + '</td>' +
                        '</tr>';

                    $('#exam_table tbody').append(quizRow);

                });

                if ($('ul.pagination li').length - 2 != response.totalPages) {
                    $('ul.pagination').empty();
                    buildPagination(response.totalPages);
                }
            },
            error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    }

    function buildPagination(totalPages) {
        let pageIndex = '<li class="page-item"><a class="page-link">Previous</a></li>';
        $("ul.pagination").append(pageIndex);

        for (let i = 1; i <= totalPages; i++) {
            if (i == 1) {
                pageIndex = "<li class='page-item active'><a class='page-link'>"
                    + i + "</a></li>"
            } else {
                pageIndex = "<li class='page-item'><a class='page-link'>"
                    + i + "</a></li>"
            }
            $("ul.pagination").append(pageIndex);
        }

        pageIndex = '<li class="page-item"><a class="page-link">Next</a></li>';
        $("ul.pagination").append(pageIndex);
    }

    (function () {
        fetchQuizzes(0);
    })();


    $(document).on("click", "ul.pagination li a", function () {

        let val = $(this).text();

        if (val.toUpperCase() === "NEXT") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            let totalPages = $("ul.pagination li").length - 2;
            if (activeValue < totalPages) {
                let currentActive = $("li.active");
                fetchQuizzes(activeValue);
                $("li.active").removeClass("active");
                currentActive.next().addClass("active");
            }
        } else if (val.toUpperCase() === "PREVIOUS") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            if (activeValue > 1) {
                fetchQuizzes(activeValue - 2);
                let currentActive = $("li.active");
                currentActive.removeClass("active");
                currentActive.prev().addClass("active");
            }
        } else {
            fetchQuizzes(parseInt(val) - 1);
            // add focus to the li tag
            $("li.active").removeClass("active");
            $(this).parent().addClass("active");
        }
    });
});




