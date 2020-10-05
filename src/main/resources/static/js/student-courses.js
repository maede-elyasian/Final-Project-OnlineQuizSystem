$(document).ready(function () {
    let pageNumber = 0;
    let studentId = $('#studentId').val();
    console.log(studentId + " Student Id");

    function fetchCourses(page) {
        pageNumber = (typeof page !== 'undefined') ? page : 0;

        $.ajax({
            type: "GET",
            url: "/student/courses/" + studentId,
            data: {
                page: pageNumber
            },
            success: function (response) {
                $('#course_table tbody').empty();
                $.each(response.courseList, (i, course) => {
                    let exams = '<a id="questions" href="' + "/student/" + studentId + "/exams/" + course.id + '">' + 'exams' + '</a>';


                    let tr_id = 'tr_' + course.id;
                    let courseRow = '<tr id=\"' + tr_id + "\"" + '>' +
                        '<td>' + course.courseTitle + '</td>' +
                        '<td>' + course.startDate + '</td>' +
                        '<td>' + course.endDate + '</td>' +
                        '<td>' + course.classification.title + '</td>' +
                        '<td>' + exams + '</td>' +
                        '</tr>';

                    $('#course_table tbody').append(courseRow);

                });

                if ($('ul.pagination li').length - 2 != response.totalPages) {
                    $('ul.pagination').empty();
                    buildPagination(response.totalPages);
                }
            },
            error: function (e) {
                alert("error: ", e);
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
        fetchCourses(0);
    })();


    $(document).on("click", "ul.pagination li a", function () {

        let val = $(this).text();

        if (val.toUpperCase() === "NEXT") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            let totalPages = $("ul.pagination li").length - 2;
            if (activeValue < totalPages) {
                let currentActive = $("li.active");
                fetchCourses(activeValue);
                $("li.active").removeClass("active");
                currentActive.next().addClass("active");
            }
        } else if (val.toUpperCase() === "PREVIOUS") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            if (activeValue > 1) {
                fetchCourses(activeValue - 2);
                let currentActive = $("li.active");
                currentActive.removeClass("active");
                currentActive.prev().addClass("active");
            }
        } else {
            fetchCourses(parseInt(val) - 1);
            $("li.active").removeClass("active");
            $(this).parent().addClass("active");
        }
    });
});