<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="q" style="display: flex; flex-direction: column; align-items: center;">
        <h1>${book.name}</h1>
        <br>
        <div class="image-container">
            <#if book.getCoverImage()??>
                <img src="${book.getCoverImage()}" alt="book cover image" style="height: 300px">
            <#else>
                <img src="${book.getPlaceHolder()}" alt="book cover placeholder" style="height: 300px">
            </#if>
        </div>
        <br>
        <#list book.authors as author>
            <p>${author.name()}</p>
        </#list>
        <p><a href="/books/genre/${book.genre}">${book.genre.getDisplayName()}</a></p>
        <p>${book.price}&nbsp;UAH</p>
        <#if book.averageRating() == 0.0 || book.averageRating()?is_nan>
            <p>Not rated yet</p>
        <#else>
            <div style="font-size: 18px;">
                <#assign rating = (book.averageRating()?number)?int />
                <#list 1..5 as i>
                    <#if i <= rating>
                        <span class="fa fa-star" style="color: gold;"></span>
                    <#else>
                        <span class="fa fa-star-o"></span>
                    </#if>
                </#list>
            </div>
        </#if>
        <div class="d-flex justify-content-center" style="width: 100%; display: flex; justify-content: center;">
            <form method="post" action="/rate-book/${book.id}"
                  style="display: flex; flex-direction: row; align-items: center;">
                <input class="form-control me-2" placeholder="Rate" type="number" id="mark" name="mark" min="1" max="5"
                       step="1" required style="margin-right: 10px; width: 100px;">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-outline-warning">Rate</button>
            </form>
        </div>
        <br>
        <div class="d-flex justify-content-center">
            <form method="post" action="/cart/add-book/${book.id}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="bookId" value="${book.id}">
                <button class="btn btn-success" type="submit">Add to Cart</button>
            </form>
        </div>
        <br>
        <div class="d-flex justify-content-center" style="width: 100%; display: flex; justify-content: center;">
            <form method="post" action="/new-comment/${book.id}"
                  style="display: flex; flex-direction: row; align-items: center; justify-content: center; width: 70%;">
                <input type="text" class="form-control me-2 flex-grow-1" placeholder="Write your comment" name="comment"
                       id="comment" style="height: 68px; margin-right: 10px;">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-outline-success">Post comment</button>
            </form>
        </div>
        <br>
        <#if book.getReviews()?has_content>
            <h2>Comments:</h2>
            <#list book.getReviews() as comment>
                <p>${comment.person.username}:</p>
                <p class="comment">${comment.text}</p>
                <#if isAdmin??>
                    <form class="d-flex mb-2" role="form" action="/delete-comment" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="commentId" value="${comment.id}">
                        <input type="hidden" name="bookId" value="${book.id}">
                        <button class="btn btn-outline-danger" type="submit">DeleteComment</button>
                    </form>
                </#if>
            </#list>
        </#if>
    </div>
    <h2>More books in genre:&nbsp;${book.genre.getDisplayName()}</h2>
    <div style="display: flex; justify-content: center; flex-wrap: nowrap; overflow-x: auto;">
        <#list books as book>
            <div style="margin: 10px; width: 250px; display: flex; flex-direction: column; align-items: center;">
                <b2 style="text-align: center;"><a href="/books/book/${book.id}">${book.name}</a></b2>
                <div class="book-item" data-book-id="${book.id}" style="display: flex; flex-wrap: nowrap;">
                    <div class="image-container">
                        <#if book.getCoverImage()??>
                            <img src="${book.getCoverImage()}" alt="book cover image" style="height: 300px">
                        <#else>
                            <img src="${book.getPlaceHolder()}" alt="book cover placeholder" style="height: 300px">
                        </#if>
                    </div>
                    <div class="custom-col-lg-4 col-sm-12"
                         style="padding-left: 10px; flex-direction: column; justify-content: space-between;">
                        <p class="books-p"> By&nbsp;
                            <#if book.authors?size &gt; 1>
                                ${book.authors?first.name()}
                                <#list book.authors[1..] as author>
                                    <br>${author.name()}
                                </#list>
                            <#else>
                                ${book.authors?first.name()}
                            </#if>
                        </p>
                        <#if book.genre?has_content>
                            <p class="books-p"><a
                                        href="/books/genre/${book.genre}">${book.genre.getDisplayName()}</a></p>
                        </#if>
                        <#if book.averageRating() == 0.0 || book.averageRating()?is_nan>
                            <p class="books-p">Not rated yet</p>
                        <#else>
                            <div style="font-size: 18px;">
                                <#assign rating = (book.averageRating()?number)?int />
                                <#list 1..5 as i>
                                    <#if i <= rating>
                                        <span class="fa fa-star" style="color: gold;"></span>
                                    <#else>
                                        <span class="fa fa-star-o"></span>
                                    </#if>
                                </#list>
                            </div>
                        </#if>

                        <p class="books-p">${book.price}&nbsp;UAH</p>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</@c.page>