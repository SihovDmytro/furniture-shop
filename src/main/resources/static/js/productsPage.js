jQuery(document).ready(function ($) {
    $("#filter-form").submit(function () {
        $(this).find(":input").filter(function () {
            return !this.value;
        }).attr("disabled", "disabled");
        return true;
    });

    $("#filter-form").find(":input").prop("disabled", false);

    const pageSize = document.querySelector('#pageSize');

    pageSize.addEventListener('change', (event) => {
        let params = new URLSearchParams(document.location.search);
        params.set("pageSize", pageSize.value);
        document.location.search = params;
    });

    const sortField = document.querySelector('#sortField');

    sortField.addEventListener('change', (event) => {
        let params = new URLSearchParams(document.location.search);
        params.set("sortField", sortField.value);
        document.location.search = params;
    });
    const sortOrder = document.querySelector('#sortOrder');

    sortField.addEventListener('change', (event) => {
        let params = new URLSearchParams(document.location.search);
        params.set("sortOrder", sortOrder.value);
        document.location.search = params;
    });
})

function addToCart(id) {
    $.ajax({
        type: "POST",
        url: "cart",
        data: "productID=" + id,
        dataType: "json",
        success: function (response) {
            document.getElementById("cart-size").innerHTML = response.count;
        }
    })
}
