const submitLogin = document.getElementById("submitLogin");

const loginError = document.getElementById("loginError");
const passwordError = document.getElementById("passwordError");

loginError.hidden = true;
passwordError.hidden = true;

submitLogin.addEventListener("click", function (e) {
        let loginField = document.getElementById("login");
        let passwordField = document.getElementById("password");

        loginError.hidden = true;
        passwordError.hidden = true;

        let valid = true;

        if (!validateLogin(loginField.value)) {
            loginError.hidden = false;
            valid = false;
        }
        if (!validatePassword(passwordField.value)) {
            passwordError.hidden = false;
            valid = false;
        }

        if (!valid) {
            e.preventDefault();
        }
        return valid;
    }
)

function validateLogin(login) {
    const loginPattern = "^[\\w_-]{3,20}$";
    return login.length <= 20 && login.length >= 3 && login.match(loginPattern) !== null;
}

function validatePassword(password) {
    return password.length <= 64 && password.length >= 6;
}