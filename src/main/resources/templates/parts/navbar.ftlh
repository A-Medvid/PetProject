<#include "security.ftlh">
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/home">Book Store</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link fs-5" aria-current="page" href="/home">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link fs-5" aria-current="page" href="/books">Books</a>
                </li>
                <#if isUser == true>
                <li class="nav-item">
                    <a class="nav-link fs-5" aria-current="page" href="/cart/">Cart <#--${person.reservedBooks}--></a>
                </li>
                </#if>
                <#if isUser == true>
                <li class="nav-item">
                    <a class="nav-link fs-5" aria-current="page" href="/wishlist/">Wishlist <#--${person.desiredBooks}--></a>
                </li>
                </#if>
                <#if isAdmin == true>
                <li class="nav-item">
                    <a class="nav-link fs-5" href="/admin/admin-panel" style="color: seagreen">Admin-Panel</a>
                </li>
                </#if>
            </ul>
        </div>
    </div>
    <#if isUser == true>
    <div class="container-fluid">
        <form class="d-flex" role="search" action="/books" method="get">
            <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn btn-outline-success" type="submit">Search</button>
        </form>
    </div>
    </#if>
    <#if isUser == true>
        <a class="nav-item nav-link mx-2" href="/user/profile">${name}</a>

        <a  type="button" class="btn btn-outline-success mx-2" href="/logout">Logout</a>
    <#else>
        <div>
            <a type="button" class="btn btn-outline-success mx-2" href="/login">LogIn</a>
        </div>
        <div>
            <a type="button" class="btn btn-success mx-2" href="/registration">SignUp</a>
        </div>
    </#if>
</nav>