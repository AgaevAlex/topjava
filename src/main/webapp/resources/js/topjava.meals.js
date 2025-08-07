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
            updateTableByData(data);
        },
        error: function (jqXHR) {
            failNoty(jqXHR);
        }
    });
}

function updateTable() {
    filter();
}

function filterReset() {
    document.getElementById("filter").reset();
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
                    "desc"
                ]
            ]
        })
    );
});