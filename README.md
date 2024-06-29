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

## Installation
To run the app - clone the repository. Also you need to have MySQL installed on your local computer, so I'd say it's much better to run it with Docker.  
Open the project in IDE and use `docker-compose up`. You need place ".env" file in the root folder of the project to be abble to run it. The ".env" might look like this:
```
MYSQL_ROOT_PASSWORD=root_password
MYSQL_DATABASE=database_name
DB_USERNAME=user_name
DB_PASSWORD=your_db_password
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/MYSQL_DATABASE
SPRING_DATASOURCE_USERNAME=user_name
SPRING_DATASOURCE_PASSWORD=user_password

JWT_SECRET=vhc498ht4mwt94t94t9gt3h9g3gh3795xgh93h5g938gh3ughreg 
JWT_EXPIRATION=300000000
ARRAY_OF_ENDPOINTS=/api/auth/**,/swagger-ui/**,/v3/api-docs/**

MYSQL_LOCAL_PORT=3306
MYSQL_DOCKER_PORT=3307
SPRING_LOCAL_PORT=8080 
SPRING_DOCKER_PORT=8080
DEBUG_PORT=5005
```

## Endpoints
You can try the app out in the Postman workspace. Just run the app and click the description of the endoint. Securitty in the app is implemented with JWT usage, but don't worry - the token will be saved as a variable in Postman environment, so you don't need to copy/paste it for each request! (If it doesn't work - choose the Bookstore Environment in the top right corner of the Postman UI)

### User 🦸‍♀️
- `POST /api/auth/login` - [login endpoint](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-21e38b64-020e-4428-bfde-a536e31e517d?action=share&creator=31607463&ctx=documentation)
- `POST /api/auth/registration` - [register a new user](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-de42cc79-9890-4501-8415-8cad8c37f6ce?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)

### Book 📙
- `GET /api/books` - [get all books form DB](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-01fdadc8-64ae-4a11-80fe-2bbcf7ecd00a?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)
- `GET /api/books/{id}` - [get book by id](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-456c195f-006d-401d-b2cc-aa85aaf94b05?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)
- `GET /api/books` - [get books by athor or title part](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-659bab5f-a6f4-4a8b-abb5-39c2252f2fb3?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10). Add `titles` and/or `authors` params on the Params
  tab to search (caseignore).
- `POST /api/books` - [add a new book to the DB](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-56b9fafa-7087-420a-9842-4c1ee2df6b25?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)
- `PUT /api/books/{id}` - [update a book in the DB](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-ab7445a6-bfd6-477b-85da-74100481fdf6?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)
- `DELETE /api/books/{id}` - [delete book by ID](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-17755c80-1b8a-4d4f-a997-2221a0e1168e?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)

### Category  🗃
- `GET: /api/categories` - [get all categories](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-ed711afe-0811-4df5-bbf4-eedab32a9b47?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)
- `GET: /api/categories/{id}` - [get category bi ID](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-f31671fc-360e-4dd2-8d5c-d51a3e9298d6?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)
- `POST: /api/categories` - [create a new category](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-ed711afe-0811-4df5-bbf4-eedab32a9b47?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)
- `PUT: /api/categories/{id}` - [update category by ID](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-c41ce83a-d85c-4a0f-a338-c5f11caf0cba?action=share&creator=31607463&ctx=documentation)
- `DELETE: /api/categories/{id}` - [delete category by ID](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-25732381-1534-42a7-927c-c881b16874d1?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)
- `GET: /api/categories/{id}/books` - [get all books by category ID](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-d41c8a1c-0be4-4640-91a9-60de250a06bd?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)

### Order 📝
- `GET: /api/orders` - [get all orders for current user](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-201dd8e3-020d-4969-81b6-085274aa0529?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)
- `POST: /api/orders` - [place an order](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-a32cab99-80a9-451f-9603-7253e47aa095?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10). All your shopping cart items goest to a new order
- `PATCH: /api/orders/{id}` [update order status](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-bff644ac-6fb1-4bca-a06b-d71445e29411?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)
- `GET: /api/orders/{orderId}/items` - [get order items for specific order](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-1fac8fb1-e417-4ce8-aa67-f9576c63f5c2?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)
- `GET: /api/orders/{orderId}/items/{itemId}` - [get specific item by ID from defined order by ID](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-27813a57-cd60-4666-ab28-b51d8a83b216?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)

### Cart 🛒
- `POST: /api/cart` - [add a book to the shopping cart](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-df63d6a3-695a-43fa-9dc3-1d9dbb8ed48a?action=share&source=copy-link&creator=31607463&ctx=documentation)
- `GET: /api/cart` - [get cart details](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-29669522-f91c-4fcf-9189-7e2bcb4d4fb4?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)
- `UPDATE: /api/cart/cart-items/{itemId}` - [update cart item](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-2f249ad3-f266-4442-9e4e-7f94af1329fc?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)
- `DELETE: /api/cart/cart-items/{itemId}` - [delete item from shoping cart](https://www.postman.com/api-test-3019/workspace/bookstroe-managemet-public/request/31607463-5ad40aea-2411-4a5d-b5e6-2ffc526dd455?action=share&creator=31607463&ctx=documentation&active-environment=31607463-eee2318c-b0af-486f-afa5-3acf8a734a10)

## History of project creation ⌛
The biggest challenge in creating this project was using Docker, as it was a new technology for me at the time. However, this hurdle has been successfully overcome!💪 The second "reason to suffer"😅 was Liquibase as it's fun to get a wide scope of errors caused by wrong scripts order


## Possible improvements 📈
In the near future, I plan to implement comprehensive code coverage with tests as a general improvement. Regarding logic and functionality enhancements, I aim to enable customers to pay for orders, most likely using Stripe, allow users to give feedback/ratings, and to implement order status notifications via a Telegram bot 📩. 

***
<div style="width:100%; text-align:center;">
    <img src="images/readme_footer_image.gif" alt="Footer Image">
</div>