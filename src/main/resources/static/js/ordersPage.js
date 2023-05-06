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
                    row.insertCell(j++).innerHTML = response[i - 1].price;
                    row.insertCell(j++).innerHTML = response[i - 1].name;
                    row.insertCell(j++).innerHTML = response[i - 1].category.name;
                    row.insertCell(j++).innerHTML = response[i - 1].producer.name;
                    row.insertCell(j++).innerHTML = response[i - 1].description;
                }
                fetchedOrders.push(id);
            }
        })
    }
}
