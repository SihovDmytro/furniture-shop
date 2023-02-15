const submitRegistration = document.getElementById("submitRegistration");
console.log("submitRegistration: "+submitRegistration);

const loginError = document.getElementById("loginError");
const passwordError = document.getElementById("passwordError");
const emailError = document.getElementById("emailError");
const passwordRepeatError = document.getElementById("passwordRepeatError");
const nameError = document.getElementById("nameError")
const surnameError = document.getElementById("surnameError")

loginError.hidden = true;
passwordError.hidden = true;
emailError.hidden = true;
passwordRepeatError.hidden = true;
nameError.hidden = true;
surnameError.hidden = true;

submitRegistration.addEventListener("click", function (e) {
        let loginField = document.getElementById("login");
        let emailField = document.getElementById("email");
        let passwordField = document.getElementById("password");
        let passwordRepeatField = document.getElementById("repeat-password");
        let nameField = document.getElementById("name");
        let surnameField = document.getElementById("surname");
        loginError.hidden = true;
        passwordError.hidden = true;
        passwordRepeatError.hidden = true;
        nameError.hidden = true;
        surnameError.hidden = true;

        let valid = true;

        if (!validateLogin(loginField.value)) {
            loginError.hidden = false;
            valid = false;
        }
        if (!validatePassword(passwordField.value)) {
            passwordError.hidden = false;
            valid = false;
        }
        if (!validatePasswordRepeat(passwordField.value, passwordRepeatField.value)) {
            passwordRepeatError.hidden = false;
            valid = false;
        }
        if (!validateEmail(emailField.value)) {
            emailError.hidden = false;
            valid = false;
        }
        if (!validateName(nameField.value)) {
            nameError.hidden = false;
            valid = false;
        }
        if (!validateSurname(surnameField.value)) {
            surnameError.hidden = false;
            valid = false;
        }

        if (!valid) {
            e.preventDefault();
        }
        return valid;
    }
)

//====================================================================================================


// const loginError = $("#loginError");
// const passwordError = $("#passwordError");
// const emailError = $("#emailError");
// const passwordRepeatError = $("#passwordRepeatError");
// loginError.hide();
// passwordError.hide();
// emailError.hide();
// passwordRepeatError.hide();
//
// $("#submit").click(function (e) {
//     loginError.hide();
//     passwordError.hide();
//     emailError.hide();
//     passwordRepeatError.hide();
//     let valid = true;
//
//     if (!validateLogin($("#login").val())) {
//         loginError.show();
//         valid = false;
//     }
//     if (!validatePassword($("#password").val())) {
//         passwordError.show();
//         valid = false;
//     }
//     if (!validatePasswordRepeat($("#password").val(), $("#repeat-password").val())) {
//         passwordRepeatError.show();
//         valid = false;
//     }
//     if (!validateEmail($("#email").val())) {
//         emailError.show();
//         valid = false;
//     }
//
//     if (!valid) {
//         e.preventDefault();
//     }
//
//     console.log(valid);
//     return valid;
// })

//====================================================================================================

function validateLogin(login) {
    const loginPattern = "^[\\w_-]{3,20}$";
    return login.length <= 20 && login.length >= 3 && login.match(loginPattern) !== null;
}

function validatePassword(password) {
    return password.length <= 64 && password.length >= 6;
}

function validatePasswordRepeat(password, repeat) {
    return repeat.length <= 64 && repeat.length >= 6 && password === repeat;
}

function validateEmail(email) {
    const emailPattern = "[\\w_\\-.]+@([\\w-]+\\.)+[\\w-]{1,5}$";
    return email.length <= 30 && email.length >= 5 && email.match(emailPattern) !== null;
}

function validateName(name) {
    return name.length >= 1 && name.length <= 25;
}

function validateSurname(surname) {
    return surname.length >= 1 && surname.length <= 25;
}
