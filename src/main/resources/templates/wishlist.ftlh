<#import "parts/common.ftlh" as c>
<@c.page>
    <h1>Wishlist</h1>
    <#if books?has_content>
        <div class="container align-items-center">
            <#list books as book>
                <div class="book-item" data-book-id="${book.id}">
                    <b1><a href="/books/book/${book.id}">${book.name}<span class="book-count"></span></a></b1>
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
                                <#include "parts/rating.ftlh">
                            </#if>
                            <p class="books-p">${book.price}&nbsp;UAH</p>
                        </div>
                        <div class="col-lg-3 col-sm-12 d-flex">
                            <div class="mt-4">
                                <form method="post" action="/wishlist/cart/add-book/${book.id}">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="bookId" value="${book.id}">
                                    <button class="btn btn-success custom-btn-width" type="submit">Add to Cart</button>
                                </form>
                                <form method="post" action="/wishlist/remove/${book.id}">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="bookId" value="${book.id}">
                                    <button type="submit" class="btn btn-outline-danger custom-btn-width">Remove
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
        </div>

    <#else>
        <p>Empty yet</p>
    </#if>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            let books = Array.from(document.querySelectorAll('.book-item'));
            let bookCounts = {};
            for (let book of books) {
                let bookId = book.getAttribute('data-book-id');
                if (bookId in bookCounts) {
                    bookCounts[bookId].count++;
                    bookCounts[bookId].elements.push(book);
                } else {
                    bookCounts[bookId] = {count: 1, elements: [book]};
                }
            }
            for (let bookId in bookCounts) {
                if (bookCounts[bookId].count > 1) {
                    let elements = bookCounts[bookId].elements;

                    for (let i = 1; i < elements.length; i++) {
                        elements[i].style.display = 'none';
                    }
                    elements[0].querySelector('.book-count').textContent = ' x ' + bookCounts[bookId].count;
                }
            }
        });
    </script>
</@c.page>