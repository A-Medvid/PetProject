<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Books by Genre: ${selectedGenre}</h2>
    <br>
    <div class="container align-items-center">
        <#list booksPage.content as book>
            <h5 style="text-align: center"><a href="/books/book/${book.id}">${book.name}</a></h5>
            <div class="row mb-3 align-items-left">
                <div class="col-lg-2 col-sm-12 offset-lg-4">
                    <div class="image-container">
                        <#if book.getCoverImage()??>
                            <img src="${book.getCoverImage()}" alt="book cover image" style="height: 300px">
                        <#else>
                            <img src="${book.getPlaceHolder()}" alt="book cover placeholder" style="height: 300px">
                        </#if>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-12">
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
                        <p class="books-p">Genre:&nbsp; <a
                                    href="/books/genre/${book.genre}">${book.genre.getDisplayName()}</a></p>
                    </#if>
                    <#include "parts/rating.ftlh">
                    <p class="books-p">${book.price}&nbsp;UAH</p>
                </div>
                <div class="mx-auto mt-1">
                    <div class="d-flex justify-content-center">
                        <form method="post" action="/cart/adding-book-genre/${book.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="bookId" value="${book.id}">
                            <input type="hidden" name="selectedGenre" value="${book.genre}">
                            <button class="btn btn-success" type="submit">Add to Cart</button>
                        </form>
                        <span style="margin: 0 2px;"></span>
                        <form method="post" action="/wishlist/adding-book-genre/${book.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="bookId" value="${book.id}">
                            <input type="hidden" name="selectedGenre" value="${book.genre}">
                            <button class="btn btn-outline-warning" type="submit">Add To Wish List</button>
                        </form>
                    </div>
                </div>
            </div>
        </#list>
    </div>
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <#if booksPage.number gt 0>
                <li class="page-item">
                    <a class="page-link" href="?page=${booksPage.number - 1}">Previous</a>
                </li>
            </#if>
            <#list 1..booksPage.totalPages as i>
                <li class="page-item<#if i == booksPage.number + 1> active</#if>">
                    <a class="page-link" href="?page=${i-1}">${i}</a>
                </li>
            </#list>
            <#if booksPage.number lt booksPage.totalPages - 1>
                <li class="page-item">
                    <a class="page-link" href="?page=${booksPage.number + 1}">Next</a>
                </li>
            </#if>
        </ul>
    </nav>
</@c.page>