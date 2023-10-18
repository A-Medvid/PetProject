<#if book.averageRating() == 0.0 || book.averageRating()?is_nan>
    <p class="books-p">Not rated yet</p>
<#else>
    <div style="font-size: 18px;">
        <#assign rating = book.averageRating()?replace(",", ".")?number?int />
        <#list 1..5 as i>
            <#if i <= rating>
                <span class="fa fa-star" style="color: gold;"></span>
            <#else>
                <span class="fa fa-star-o"></span>
            </#if>
        </#list>
    </div>
</#if>