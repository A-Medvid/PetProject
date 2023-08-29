<#import "parts/common.ftlh" as c>

<@c.page>
    <h2>Welcome!</h2>
    <br>
    <form class="form-inline" method="post" action="/login">
        <input class="form-control" type="text" id="username" name="username" placeholder="Username"
               required>
        <input class="form-control" type="password" id="password" name="password"
               placeholder="Password" required>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-success custom-btn-width-3" type="submit" style="display: block; margin: auto">Login</button>
    </form>
    <br>
    <p>Don't have an account yet? Rather join!</p>
    <button class="btn btn-outline-success custom-btn-width-3"
            onclick="location.href='/registration'">Registration</button>
</@c.page>