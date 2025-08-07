const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl,
};

function enable(checkbox, id) {
    var enabled = checkbox.is(":checked");
    $.ajax({
        url: ctx.ajaxUrl + id,
        type: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(enabled),
        success: function () {
            checkbox.closest('tr').toggleClass('disabled');
            successNoty(enabled ? 'Enabled' : 'Disabled');
        },
        error: function () {
            checkbox.prop('checked', !enabled);
            failNoty("Error")
        }
    });
}

function updateTable() {
    $.get(ctx.ajaxUrl, function (data) {
        updateTableByData(data);
    });
}

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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