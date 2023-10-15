<#import "parts/common.ftlh" as c>
<@c.page>
    <h1>BOOKSTORE</h1>
    <h6>Pet Project</h6>
    <br>
    <section class="user-info" style="text-align: center">
        <#if user??>
            <h2>Welcome, ${user.username}, Hope you’ll enjoy this book!</h2>
            <section class="random-book">
                <div id="random-book">
                    <div class="image-container">
                        <a href="/books/book/${book.id}">
                            <#if book.getCoverImage()??>
                                <img src="${book.getCoverImage()}" alt="book cover image" style="height: 300px">
                            <#else>
                                <img src="${book.getPlaceHolder()}" alt="book cover placeholder" style="height: 300px">
                            </#if>
                        </a>
                    </div>
                    <h3><a href="/books/book/${book.id}">${book.name}</a></h3>
                    <#if book.averageRating() == 0.0 || book.averageRating()?is_nan>
                        <div style="font-size: 18px;    justify-items: center;
    align-items: center; margin-left: auto; margin-right: auto">
                            <p>Not rated yet</p>
                        </div>
                    <#else>
                        <div style="font-size: 18px;    justify-items: center;
    align-items: center; margin-left: auto; margin-right: auto; text-align: center">
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
                    <p>
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
                        <p><a href="/books/genre/${book.genre}">${book.genre.getDisplayName()}</a></p>
                    </#if>
                    <div class="button-container">
                        <button onclick="location.reload()" id="update-book" class="btn btn-outline-success"
                                type="submit">Try
                            another
                        </button>
                    </div>
                </div>
            </section>
        <#else>
            <h2>Welcome, guest!</h2>
            <p>Enter the name of the book, or its author.</p>
            <form class="d-flex" role="search" action="/books" method="get">
                <input class="form-control me-2" type="search" placeholder="Search" name="search" aria-label="Search">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </#if>
    </section>

<#--
   Used for creating a person with the ADMIN role. Also check /controllers/HomeController
  -->
<#--<form method="post" action="/home" class="form-inline">
    <label for="username">Username:</label>
    <input id="username" type="text" name="username" class="form-control"
           placeholder="Username length must be between 4 and 16 characters"
           required minlength="4" maxlength="16">
    <label for="email">Email:</label>
    <input id="email" type="email" name="email" class="form-control"
           placeholder="example@mail.com" required>
    <label for="password">Password:</label>
    <input id="password" type="password" name="password" class="form-control"
           placeholder="Password length must be between 3 and 16 characters"
           required minlength="3" maxlength="16">
    <label for="confirmPassword">Confirm Password:</label>
    <input id="confirmPassword" type="password" name="confirmPassword" class="form-control"
           placeholder="Confirm password" required>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <br>
    <button type="submit" class="btn btn-success"
            style="display: block; margin: auto;">RegisterAdmin
    </button>
</form>-->

</@c.page>