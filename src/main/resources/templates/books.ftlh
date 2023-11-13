<#import "parts/common.ftlh" as c>
<@c.page>
    <#include "parts/security.ftlh">
    <br>
    <div class="d-flex justify-content-between" style="max-width: 700px; margin-left: auto; margin-right: auto;">
        <#if (search??) && (search?has_content)>
            <form action="/books" method="get" class="me-2">
                <input type="hidden" id="search" name="search">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-outline-success" type="submit">Back To All</button>
            </form>
        </#if>
        <div class="btn-group me-3">
            <button class="btn btn-outline-success dropdown-toggle" type="button" data-bs-toggle="dropdown"
                    aria-expanded="false">
                Sort by:
                <#if sortOption??>
                    <#switch sortOption>
                        <#case "name">
                            Name
                            <#break>
                        <#case "priceAsc">
                            Price (Low to High)
                            <#break>
                        <#case "priceDesc">
                            Price (High to Low)
                            <#break>
                        <#case "rating">
                            Rating
                            <#break>
                    </#switch>
                </#if>
            </button>
            <form id="sortForm" action="/books/sort" method="get" role="search">
                <input type="hidden" id="sortOption" name="sortOption">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" id="sort-name" href="#" data-sort="name">Name</a></li>
                    <li><a class="dropdown-item" id="sort-priceAsk" href="#" data-sort="priceAsc">Price (Low to
                            High)</a></li>
                    <li><a class="dropdown-item" id="sort-priceDesk" href="#" data-sort="priceDesc">Price (High to
                            Low)</a></li>
                    <li><a class="dropdown-item" id="sort-rating" href="#" data-sort="rating">Average Rating</a></li>
                </ul>
            </form>
        </div>
        <div class="flex-grow-1">
            <form class="d-flex" role="search" action="/books" method="get">
                <input class="form-control me-2 flex-grow-1" type="search" name="search" placeholder="Search"
                       aria-label="Search">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </div>
    <br>
    <#if (search??) && (search?has_content)>
        <h2>Search results for: ${search}</h2>
    <#else>
        <h2 style="text-align: center">All Books</h2>
    </#if>
    <div style="text-align: center">
        <#list books.content as book>
            <h5 style="text-align: center"><a href="/books/book/${book.id}">${book.name}
                    <#if isAdmin == true> x ${book.quantity} </#if></a></h5>
            <div class="row mb-3 align-items-left">
                <div class="col-lg-2 col-sm-12 offset-lg-4">
                    <div class="image-container">
                        <a href="/books/book/${book.id}">
                            <#if book.getCoverImage()??>
                                <img src="${book.getCoverImage()}" alt="book cover image" style="height: 300px">
                            <#else>
                                <img src="${book.getPlaceHolder()}" alt="book cover placeholder" style="height: 300px">
                            </#if>
                        </a>
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
                <div class="col-lg-3 col-sm-12">
                    <#if isAdmin == true>
                        <form class="d-flex mb-2" role="form" action="/admin/price/${book.id}" method="post">
                            <input class="form-control me-2 flex-grow-1" type="number" id="newPrice"
                                   placeholder="New Price"
                                   aria-label="New Price" name="newPrice" step="0.01" min="0" required>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="bookId" value="${book.id}">
                            <button type="submit" class="btn btn-warning">Change</button>
                        </form>
                        <form class="d-flex mb-2" role="form" action="/admin/quantity/${book.id}" method="post">
                            <input class="form-control me-2 flex-grow-1" placeholder="New Quantity"
                                   aria-label="New Quantity" type="number" id="newQuantity" name="newQuantity" step="1"
                                   min="1" required>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="bookId" value="${book.id}">
                            <button type="submit" class="btn btn-warning">Change</button>
                        </form>
                        <form class="d-flex mb-2" role="form" action="/admin/image/${book.id}" method="post">
                            <input class="form-control me-2 flex-grow-1" placeholder="New Image Link"
                                   aria-label="New Image Link" type="text" id="newImage" name="newImage" required>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="bookId" value="${book.id}">
                            <button type="submit" class="btn btn-warning">Change</button>
                        </form>
                        <form action="/admin/book/${book.id}" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="bookId" value="${book.id}">
                            <button type="submit" class="btn btn-danger w-100">Delete</button>
                        </form>
                    </#if>
                </div>
                <div class="mx-auto mt-1">
                    <div class="d-flex justify-content-center">
                        <form method="post" action="/cart/adding-book/${book.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="bookId" value="${book.id}">
                            <button class="btn btn-success" type="submit">Add to Cart</button>
                        </form>
                        <span style="margin: 0 2px;"></span>
                        <form method="post" action="/wishlist/adding-book/${book.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="bookId" value="${book.id}">
                            <button class="btn btn-outline-warning" type="submit">Add To Wish List</button>
                        </form>
                    </div>
                </div>
            </div>
        </#list>
    </div>
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <#if currentPage?number gt 0>
                <li class="page-item">
                    <a class="page-link" href="?page=${currentPage - 1}&sortOption=${sortOption!}">Previous</a>
                </li>
            </#if>
            <#list 1..totalPages as i>
                <li class="page-item<#if i == currentPage + 1> active</#if>">
                    <a class="page-link" href="?page=${i-1}&sortOption=${sortOption!}">${i}</a>
                </li>
            </#list>
            <#if currentPage < totalPages - 1>
                <li class="page-item">
                    <a class="page-link" href="?page=${currentPage + 1}&sortOption=${sortOption!}">Next</a>
                </li>
            </#if>
        </ul>
    </nav>
    <script>
        document.querySelectorAll('#sort-name, #sort-priceAsk, #sort-priceDesk, #sort-rating').forEach(item => {
            item.addEventListener('click', function (e) {
                e.preventDefault();
                const sortOption = this.getAttribute('data-sort');
                document.getElementById('sortOption').value = sortOption;
                document.getElementById('sortForm').submit();
            });
        });
    </script>
</@c.page>
