![](images/readme_title_image.png)
# Bookstore

This application is designed for managing inventory in a bookstore 📚.  
It offers the ability to search for books by author and/or title, as well as the option to create orders🛒.

## Used Technologies
- Java ☕
- Spring Data JPA, Hibernate, MySQL 🗄️
- Spring MVC, Tomcat 🌐
- Spring Boot, Maven 🚀
- Spring Security (JWT) 🔒
- JUnit, Mockito 🧪
- Git 🔄
- IntelliJ IDEA 🖥️

# Installation
- Clone the repo, run `mvn clean package` and run it in IDE.
## Endpoints
- To send requests to this app's endpoints you should register a new user first.

For this purpose use this endpoint:
```code
POST /api/auth/registration
```
the request body should look like this:
```JSON
{
     "email":"your@email.com",
     "password":"password",
     "repeatPassword":"password",
     "firstName":"Johny",
     "lastName":"Blaze"
}
```
Use the response token on the login endpoint to proceed.
Or step over with a pre-registered user right to the next endpoint:

```code
POST /api/auth/login
```
You can use this one:
```JSON
{
    "email": "admin@example.com",
    "password": "12345678"
}
```
- You can retrieve the list of all stored books with a request to this endpoint:

```code
GET /api/books
```
Or find a book by ID with this endpoint:
```code
GET /api/books/{id}
```
Or even search 🕵️‍♂️ for books with this one("authors" and/or "titles" in params):
```code
GET /api/books/search
```
- Other book management endpoints:

`POST: /api/books` - endpoint for creating a new book (Admin authority)  
`PUT: /api/books/{id}` - endpoint for updating information about the book (Admin authority)  
`DELETE: /api/books/{id}` - endpoint for deleting books (Soft delete is used, Admin authority)

Other functionality:

**Categories**  
`GET: /api/categories` - endpoint to look at all categories (User authority)   
`GET: /api/categories/{id}` - endpoint for searching a specific category (User authority)  
`POST: /api/categories` - endpoint for creating a new category (Admin authority) (User authority)  
`PUT: /api/categories/{id}` - endpoint for updating information about specific categories (Admin authority)  
`DELETE: /api/categories/{id}` - endpoint for deleting categories (Soft delete is used, Admin authority)  
`GET: /api/categories/{id}/books` - endpoint to look at books with specific categories (User authority)

**Orders**  
`GET: /api/orders` - endpoint for getting orders history (User authority)  
`POST: /api/orders` - endpoint for placing orders (User authority)  
`PATCH: /api/orders/{id}` - endpoint for updating orders status (Admin authority)  
`GET: /api/orders/{orderId}/items` - endpoint for getting order items from specific order (User authority)  
`GET: /api/orders/{orderId}/items/{itemId}` - endpoint for getting a specific item from certain order (User authority)

**Cart**  
`GET: /api/cart` - endpoint for getting all items in your shopping cart (User authority)  
`POST: /api/cart` - endpoint for adding items to your shopping cart (User authority)  
`PUT: /api/cart/cart-items/{itemId}` - endpoint for updating items quantity (User authority)  
`DELETE: /api/cart/cart-items/{itemId}` - endpoint for deleting items from your shopping cart (User authority)
## Possible improvements
- To improve my project, I plan to add a customer ability to write reviews and implement some payment system.

## Contributing

Pull requests are welcome.

## License

[MIT](https://choosealicense.com/licenses/mit/)