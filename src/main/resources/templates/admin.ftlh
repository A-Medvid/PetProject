<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Add a new book</h2>
    <form class="rf" action="/admin/add-book" method="post" id="addBookForm">
        <input class="form-control me-2 flex-grow-1" placeholder="Name" type="text" name="name" id="name" required>
        <select class="form-control me-2 flex-grow-1" id="genre" name="genre" required>
            <option class="grey" disabled selected>Select Genre</option>
            <option value="ROMANCE">Romance</option>
            <option value="DETECTIVE">Detective</option>
            <option value="FANTASY">Fantasy</option>
            <option value="SCIENCE_FICTION">Science Fiction</option>
            <option value="SELF_IMPROVEMENT">Self Improvement</option>
            <option value="CLASSICS">Classics</option>
            <option value="SCIENCE_AND_EDUCATION">Science and Education</option>
        </select>
        <div id="genreError" class="alert alert-danger" role="alert" style="display: none;">
        </div>
        <input class="form-control me-2 flex-grow-1" placeholder="Price in UAH" type="number" id="price" name="price"
               step="0.01" min="0" required>
        <input class="form-control me-2 flex-grow-1" placeholder="Quantity" type="number" id="quantity" name="quantity"
               step="1" min="1" required>
        <input class="form-control me-2 flex-grow-1" placeholder="Image's link" type="text"
               name="coverImage" id="coverImage" required>
        <div class="form-control me-2 flex-grow-1" id="authors">
            <input class="form-control me-2 flex-grow-1" placeholder="Author first name" type="text"
                   id="authors[0].firstName" name="authors[0].firstName" required>
            <input class="form-control me-2 flex-grow-1" placeholder="Author last name" type="text"
                   id="authors[0].lastName" name="authors[0].lastName" required>
        </div>
        <div class="mt-1">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-outline-success custom-btn-width" type="button" onclick="addAuthor()">Add Author
            </button>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-success custom-btn-width" type="submit">Add Book</button>
        </div>
    </form>
    <script>
        var authorIndex = 0;

        function addAuthor() {
            authorIndex++;
            var div = document.createElement('div');
            div.id = 'author' + authorIndex;

            var firstNameInput = '<input class="form-control me-2 flex-grow-1" placeholder="Author first name" type="text" id="authors[' + authorIndex + '].firstName" name="authors[' + authorIndex + '].firstName" required>';
            var lastNameInput = '<input class="form-control me-2 flex-grow-1" placeholder="Author last name" type="text" id="authors[' + authorIndex + '].lastName" name="authors[' + authorIndex + '].lastName" required>';
            var removeButton = '<button class="btn btn-outline-danger" type="button" onclick="removeAuthor(' + authorIndex + ')">Remove Author</button>';

            div.innerHTML = firstNameInput + lastNameInput + removeButton;

            document.getElementById('authors').appendChild(div);
        }

        function removeAuthor(index) {
            var div = document.getElementById('author' + index);
            div.remove();
        }
    </script>
    <script>
        document.getElementById('addBookForm').addEventListener('submit', function (event) {
            const genreSelect = document.getElementById('genre');
            const genreError = document.getElementById('genreError');
            if (genreSelect.value === 'Select Genre') {
                event.preventDefault();
                genreError.style.display = 'block';
                genreError.textContent = 'Please select a genre.';
            } else {
                genreError.style.display = 'none';
            }
        });
    </script>
</@c.page>