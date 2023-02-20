function changeLanguage() {
    const selectLang = document.querySelector('#select-locale');
    let params = new URLSearchParams(document.location.search);
    params.set("lang", selectLang.value);
    console.log(params)
    document.location.search = params;
}