<#import "parts/common.ftlh" as c>

<@c.page>

    <h1>${user.username}</h1>
    <p>${user.email}</p>
    <div class="button-container-2">
        <a href="/cart/purchases" class="btn btn-outline-success custom-btn-width-p">List of Purchases</a>
        <button class="btn btn-outline-warning custom-btn-width-p" id="changePasswordButton">Change Password</button>
    </div>
    <#if errorOldPasswordProfile?? || errorNewPasswordProfile?? || errorPasswordConfirmationProfile?? >
        <div class="alert alert-danger" role="alert">
            Errors in form, try again.
        </div>
    </#if>
    <div id="passwordModal" class="modal">
        <div class="modal-content">
            <form class="rf" method="post" action="/user/profile">
                <h1>Change password:</h1>
                <br>
                <#--<label for="old-password">Old password</label>-->
                <input class="form-control" placeholder="Old password" type="password"
                       name="old-password" id="old-password">
                <span id="errorOldPasswordProfile" class="error" style="color: red;"></span>
                <#if errorOldPasswordProfile??>
                    <div class="alert alert-danger" role="alert">
                        ${errorOldPasswordProfile}
                    </div>
                </#if>
                <br>
                <#--<label for="new-password">New password</label>-->
                <input class="form-control" placeholder="New password" type="password"
                       name="new-password" id="new-password">
                <span id="errorNewPasswordProfile" class="error" style="color: red;"></span>
                <#if errorNewPasswordProfile??>
                    <div class="alert alert-danger" role="alert">
                        ${errorNewPasswordProfile}
                    </div>
                </#if>
                <br>
                <#--<label for="confirm-password">Confirm password</label>-->
                <input class="form-control" placeholder="Confirm password" type="password"
                       name="confirm-password" id="confirm-password">
                <span id="errorPasswordConfirmationProfile" class="error" style="color: red;"></span>
                <#if errorPasswordConfirmationProfile??>
                    <div class="alert alert-danger" role="alert">
                        ${errorPasswordConfirmationProfile}
                    </div>
                </#if>
                <br>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input class="btn btn-outline-success custom-btn-width-2" type="submit" value="Change">
            </form>
        </div>
    </div>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const newPassword = document.getElementById("new-password");
            const confirmPassword = document.getElementById("confirm-password");
            const errorPasswordConfirmationProfile = document.getElementById("errorPasswordConfirmationProfile");

            newPassword.addEventListener("input", function () {
                if (newPassword.value !== confirmPassword.value) {
                    errorPasswordConfirmationProfile.textContent = "Passwords do not match";
                } else {
                    errorPasswordConfirmationProfile.textContent = "";
                }
            });

            confirmPassword.addEventListener("input", function () {
                if (newPassword.value !== confirmPassword.value) {
                    errorPasswordConfirmationProfile.textContent = "Passwords do not match";
                } else {
                    errorPasswordConfirmationProfile.textContent = "";
                }
            });

            const changePasswordButton = document.getElementById("changePasswordButton");
            changePasswordButton.addEventListener("click", function () {
                const passwordModal = document.getElementById("passwordModal");
                passwordModal.style.display = "block";
            });
        });
    </script>

</@c.page>