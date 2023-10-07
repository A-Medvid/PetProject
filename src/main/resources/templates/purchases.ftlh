<#import "parts/common.ftlh" as c>
<@c.page>
    <h1>Your purchases</h1>
    <br>
    <#if purchases?has_content>
        <div class="container d-flex flex-column justify-content-center align-items-center">
            <#list purchases as purchase>
                <div class="card w-50 mb-3">
                    <div class="card-body">
                        <h5 class="card-title">${purchase.getFormattedDate()}</h5>
                        <h6 class="card-subtitle mb-2 text-muted">Books:</h6>
                        <ul>
                            <#list purchase.books as book>
                                <li>${book.name} (${book.price}&nbsp;UAH)</li>
                            </#list>
                        </ul>
                        <#assign total=0>
                        <#list purchase.books as book>
                            <#assign total=total+book.price>
                        </#list>
                        <h6>
                            Total Price: ${total}&nbsp;UAH
                        </h6>
                    </div>
                </div>
            </#list>
        </div>
    <#else>
        <h2>Empty yet</h2>
    </#if>
</@c.page>