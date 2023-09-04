<#import "parts/common.ftlh" as c>

<@c.page>
        <h2>Join us!</h2>
        <br>
        <br>
        <div class="form-row">
            <div class="form-group col-md-6">
                <form method="post" action="/registration" class="form-inline">
                    <label for="username">Username:</label>
                    <input id="username" type="text" name="username" class="form-control"
                           placeholder="Username length must be between 4 and 16 characters"
                           required minlength="4" maxlength="16">
                    <span id="usernameError" class="error" style="color: red;"></span>
                    <br>
                    <#if errorUsernameExist??>
                        <div class="alert alert-danger" role="alert">
                            ${errorUsernameExist}
                        </div>
                    </#if>
                    <label for="email">Email:</label>
                    <input id="email" type="email" name="email" class="form-control"
                           placeholder="example@mail.com" required>
                    <span id="emailError" class="error" style="color: red;"></span>
                    <br>
                    <#if errorEmailValid??>
                        <div class="alert alert-danger" role="alert">
                            ${errorEmailValid}
                        </div>
                    </#if>
                    <#if errorEmailExist??>
                        <div class="alert alert-danger" role="alert">
                            ${errorEmailExist}
                        </div>
                    </#if>
                    <label for="password">Password:</label>
                    <input id="password" type="password" name="password" class="form-control"
                           placeholder="Password length must be between 3 and 16 characters"
                           required minlength="3" maxlength="16">
                    <span id="passwordError" class="error" style="color: red;"></span>
                    <br>
                    <#if errorPassword??>
                        <div class="alert alert-danger" role="alert">
                            ${errorPassword}
                        </div>
                    </#if>
                    <label for="confirmPassword">Confirm Password:</label>
                    <input id="confirmPassword" type="password" name="confirmPassword" class="form-control"
                           placeholder="Confirm password" required>
                    <span id="confirmPasswordError" class="error" style="color: red;"></span>
                    <br>
                    <#if errorPasswordConfirmation??>
                        <div class="alert alert-danger" role="alert">
                            ${errorPasswordConfirmation}
                        </div>
                    </#if>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <br>
                    <button type="submit" class="btn btn-success"
                            style="display: block; margin: auto;">Register
                    </button>
                </form>
            </div>
        </div>
        <br>
        <br>
        <p>Already have an account?</p>
        <button class="btn btn-outline-success custom-btn-width-2" onclick="location.href='/login'">Log in
        </button>
        <#--style="display: block; margin: auto;"-->


    <script>
        const usernameInput = document.getElementById("username");
        const emailInput = document.getElementById("email");
        const passwordInput = document.getElementById("password");
        const confirmPasswordInput = document.getElementById("confirmPassword");

        usernameInput.addEventListener("input", validateUsername);
        emailInput.addEventListener("input", validateEmail);
        passwordInput.addEventListener("input", validatePassword);
        confirmPasswordInput.addEventListener("input", validateConfirmPassword);

        function validateUsername() {
            const usernameError = document.getElementById("usernameError");
            if (usernameInput.value.length < 4 || usernameInput.value.length > 16) {
                usernameError.textContent = "Username must be between 4 and 16 characters";
                return false;
            }
            usernameError.textContent = "";
            return true;
        }

        function validateEmail() {
            const emailError = document.getElementById("emailError");
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailPattern.test(emailInput.value)) {
                emailError.textContent = "Please enter a valid email address";
                return false;
            }
            emailError.textContent = "";
            return true;
        }

        function validatePassword() {
            const passwordError = document.getElementById("passwordError");
            if (passwordInput.value.length < 3 || passwordInput.value.length > 16) {
                passwordError.textContent = "Password length must be between 3 and 16 characters";
                return false;
            }
            passwordError.textContent = "";
            return true;
        }

        function validateConfirmPassword() {
            const confirmPasswordError = document.getElementById("confirmPasswordError");
            if (confirmPasswordInput.value !== passwordInput.value) {
                confirmPasswordError.textContent = "Passwords do not match";
                return false;
            }
            confirmPasswordError.textContent = "";
            return true;
        }
    </script>

</@c.page>