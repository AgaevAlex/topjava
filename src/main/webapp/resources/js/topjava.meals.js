const mealAjaxUrl = "ajax/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl
};

function filter() {
    const formData = $("#filter").serialize();

    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: formData,
        success: function (data) {
            ctx.datatableApi.clear().rows.add(data).draw();
        },
        error: function (jqXHR) {
            failNoty(jqXHR);
        }
    });
}

function filterReset() {
    $("#filter").find(":input").val("");
    updateTable();
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});