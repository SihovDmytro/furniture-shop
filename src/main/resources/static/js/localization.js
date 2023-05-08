function changeLanguage() {
    const selectLang = document.querySelector('#select-locale');
    let params = new URLSearchParams(document.location.search);
    params.set("lang", selectLang.value);
    console.log(params)
    document.location.search = params;
}

function getLocalizedString(string){
    let locale = document.getElementById("select-locale").value;
    console.log("locale: "+locale)
    return string.toLocaleString(locale, {
        minimumFractionDigits:2,
        maximumFractionDigits:2,
        minimumIntegerDigits:1,
        maximumIntegerDigits:9
    });
}