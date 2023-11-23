# Bookstore

This is my pet project for an online bookstore where users can browse, purchase, and manage books. For the backend, Spring Framework was used, and for the frontend, Bootstrap and FreeMarker were utilized.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Security](#security)
- [Database](#database)
- [Contributing](#contributing)
- [Contact me](#contact-me)

## Features

- **Book Catalog:** Browse available books with details such as title, author, genre, and price.
- **User Accounts:** Register an account, log in, and manage personal information.
- **Wishlist:** Maintain a wishlist of the desired books for the future reference.
- **Shopping Cart:** Add books to a cart for purchasing and manage cart contents.
- **Checkout Process:** *This feature is not available; the application is for study purposes only.*
- **Search Functionality:** Search for specific books by title, author, or genre.
- **Sorting Options:** Sort books by name, rating, and ascending/descending prices.
- **Book Rating and Reviews/Comments:** Authenticated users can rate books from 1 to 5 points and write reviews or comments for each book.
- **Admin Panel:** 
  - Manage book inventory: adding and deleting books, editing books logo, price, stock quantity, title and author’s name; 
  - Moderation of users reviews/comments.
- **Page 404:** Implemented a custom page to handle the error 404 (Not Found) for better user experience. 

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- Hibernate
- PostgreSQL
- Freemarker
- Bootstrap
- HTML, CSS, JavaScript

## Getting Started

To run this application locally, follow the next steps:

1. Clone the repository: `git clone https://github.com/A-Medvid/PetProject.git`
2. Navigate to the project directory: `cd PetProjecte`
3. Configure the database settings in `application.properties` file.
```
spring.jpa.generate-ddl=true
spring.datasource.url=jdbc:postgresql://localhost:5432/bookstore
spring.datasource.username=postgres
spring.datasource.password=pass
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```
4. Build the project: `mvn clean install`
5. Run the application: `java -jar target/PetProject.jar`
6. Access the application in your browser at `http://localhost:5001/home`

If it is needed to use another server port, make changes in this line in `application.properties` file.
```
server.port=${PORT:5001}
```

## Usage

- **Register/Login:** Create an account or log in with existing credentials.
- **Browse Books:** Explore the available collection of books with details.
- **Add to Cart:** Select books and add them to your cart for purchasing.
- **Manage Wishlist:** Add desired books to the wishlist for future reference.
- **Admin Functions:** Access the admin panel to manage inventory and users comments.

## Security

- **Authentication and Authorization:** The project implements authentication and authorization for users.
- **User Roles:** Users are categorized into three roles: Guest, Admin, and Registered User. Each role has specific permissions and access rights.
- **Default Users:** The database includes two pre-configured users:
  - Registered User: Username `user` with password `123`
  - Administrator: Username `admin` with password `admin`

## Database

- **Database:** PostgreSQL is used as the project's database.
- **Database Setup:** A file for creating the initial database:
 [Configuration file](https://drive.google.com/file/d/16zxRxX88oGtLM3rgJvXgQhuctVBRx8tn/view?usp=sharing)


## Contributing

Contributions are welcome! Feel free to submit issues or pull requests for any improvements or new features.

1. Fork the repository.
2. Create your feature branch: `git checkout -b feature/new-feature`
3. Commit your changes: `git commit -am 'Add a new feature'`
4. Push to the branch: `git push origin feature/new-feature`
5. Submit a pull request.


## Contact me

You can reach me on [LinkedIn](https://www.linkedin.com/in/arsen-medvid/).
