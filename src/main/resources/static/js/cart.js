function updateCart(id, e) {
    var quantity = e.value;
    if (quantity == null) {
        return;
    }
    var data = {}
    data["productID"] = id;
    data["quantity"] = e.value
    $.ajax({
        type: "PUT",
        url: "cart",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json",
        error: function (error) {
            e.value = e.defaultValue;
        },
        success: function (response) {
            let tableCart = document.getElementById("cart");
            let currRow = e.parentElement.parentElement.rowIndex;

            tableCart.rows[currRow].cells[4].innerHTML = getLocalizedString(response.total);
            tableCart.rows[tableCart.rows.length - 1].cells[1].innerHTML = getLocalizedString(response.cartPrice);
        }
    })
}

function deleteFromCart(id, row) {
    $.ajax({
        type: "DELETE",
        url: "cart",
        data: JSON.stringify(id),
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            let tableCart = document.getElementById("cart");
            let currRow = row.parentElement.rowIndex;
            tableCart.deleteRow(currRow);
            tableCart.rows[tableCart.rows.length - 1].cells[1].innerHTML = getLocalizedString(response.cartPrice);
            if (tableCart.rows.length <= 2) {
                hideCart();
            }
        }
    })
}

function hideCart() {
    document.getElementById("cart-container").hidden = true;
    document.getElementById("empty-cart").hidden = false;
}
