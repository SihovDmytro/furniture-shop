let fetchedOrders = [];

function getProductInfos(id) {
    console.log("id: " + id)
    console.log("fetchedOrders: " + fetchedOrders)
    let productInfoRow = document.getElementById("productInfoHeaderRow" + id);
    let productInfoTable = document.getElementById("productInfoTable" + id);
    productInfoRow.hidden = !productInfoRow.hidden;

    if (!fetchedOrders.includes(id)) {
        $.ajax({
            type: "GET",
            url: "orders/" + id,
            dataType: "json",
            contentType: "application/json",
            error: function (error) {
                console.log("error: " + error)
            },
            success: function (response) {
                console.log("response: " + response)
                for (let i = 1; i <= response.length; i++) {
                    let row = productInfoTable.insertRow(i);
                    let j = 0;
                    let total = response[i - 1].productInfo.price * response[i - 1].quantity;
                    row.insertCell(j++).innerHTML = getLocalizedString(total);
                    row.insertCell(j++).innerHTML = getLocalizedString(response[i - 1].productInfo.price);
                    row.insertCell(j++).innerHTML = response[i - 1].productInfo.name;
                    row.insertCell(j++).innerHTML = response[i - 1].productInfo.category.name;
                    row.insertCell(j++).innerHTML = response[i - 1].productInfo.producer.name;
                    row.insertCell(j++).innerHTML = response[i - 1].productInfo.description;
                    row.insertCell(j++).innerHTML = response[i - 1].quantity;
                }
                fetchedOrders.push(id);
            }
        })
    }
}
