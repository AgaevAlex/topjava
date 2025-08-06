const userAjaxUrl = "admin/users/";
const userRestUrl = "rest/admin/users/"

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl,
    restUrl: userRestUrl
};

function enable(checkbox, id) {
    var enabled = checkbox.is(":checked");
    $.ajax({
        url: ctx.restUrl + id,
        type: 'POST',
        data: 'enabled=' + enabled,
        success: function () {
            checkbox.closest('tr').toggleClass('disabled');
            successNoty(enabled ? 'Enabled' : 'Disabled');
        }
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