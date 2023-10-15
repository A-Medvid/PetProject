<#import "parts/common.ftlh" as c>
<@c.page>
    <#include "parts/security.ftlh">
    <div class="center">
        <h1>Thank you ${person.username} for the Purchase!</h1>
        <p>We appreciate that you choose our store! We are looking forward to seeing you again for more bestsellers and new releases!</p>
        <a href="/cart/purchases" class="btn btn-success custom-btn-width-3">View my Purchases</a>
        <a href="/home" class="btn btn-outline-success custom-btn-width-3">Back to Bookstore</a>
    </div>
</@c.page>