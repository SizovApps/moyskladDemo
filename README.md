# RomashkoTestProject

# RomashkoTestProject

Описание проекта и документация для запуска

## Деплой через докер

1. Запуск через докер доступен начиная с версии t3. Выполните команду

    ```bash
    docker-compose up -d --build

## Описание решения:

# Общее описание
Выполнены все бонусные задания. Написаны тесты. Примеры запросов будут.

# Технологии:
Java 17, Spring Boot, Spring MVC, Lombok, Jackson, JPA + Hibernate + PostgreSQL, Docker.

# Примеры запросов (для версии t5):

Создание товара:
<br>
POST: http://127.0.0.1:8080/api/v1/products
<br>
Body:
`{
"name": "B",
"description": "Desc",
"price": 10.0,
"available": true,
"amountOfProduct": 5
}`

<br>
Получение всех товаров:
<br>
GET: http://127.0.0.1:8080/api/v1/products
<br>

Получение по id:
<br>
GET: http://127.0.0.1:8080/api/v1/products/1


Примеры фильтров:
<br>
GET: http://127.0.0.1:8080/api/v1/products/?available=true&priceGreater=7&sortPrice=DESC

<br>
Обновление товара:
<br>
PUT: http://127.0.0.1:8080/api/v1/products/update_product
<br>
Body: 

`{
"id": 952,
"name": "Finn3",
"description": "updated!!! again1",
"price": 111,
"isAvailable": false,
"amountOfProduct": 10
}`

<br>
Получение всех продаж:
<br>
GET: http://127.0.0.1:8080/api/v1/product_sell
<br>

<br>
Создание продаже:
<br>
PUT: http://127.0.0.1:8080/api/v1/product_sell
<br>
Body: 

`{
"name": "first product sell",
"product": {
"id": 1002
},
"productAmount": 2
}`

Аналогично для Поставки товаров.



# t1

Использую Spring MVC. Http заросы обрабатываются в /controllers. 
<br>
Модели описаны в models/. Использую Lombok.
<br>
Шаблон ответа сервера на все запросы определен в ProductResponse.javа.
<br>
Сохранение данных происходит в InMemoryProductDAO наследованном от ProductRepository.
Данные хранятся только в рантайме приложения.
<br>
Для сериализации приложения использую Jackson. Кастомный сериализатор
описан в serializers/ProductDeserializer.java. Проверяю на валидность входящие данные.

# t2
Использую jpa+hibernate. Описание базы данных находится в resources/application.yaml.
Интерфейс ProductRepository наследуется от JpaRepository. Добавлен сервис ProductsServiceImpl.java, которые работает с репозиторием, который сохраняет данные в PostgreSQL.

# t3
Добавил запуск через докер. Развертывается два сервис - сам сервер и PostgreSQL.

# t4 
Добавил фильтрацию с сортировку + ограничение на количетсво полученных записей.
Метод интерфейса JpaRepository.findAll() принимает Specification<Product> в которой определены фильтры созданные в utils/ProductSpecification.java и сортировку.
Сортировка передается вместе с org.springframework.data.domain.Pageable.
Параметры филтьтров принимаются через параметры запроса.

# t5

Добавлены новые сущности. Для каждой сущности создан новый контроллер, сервис и репозиторий.
<br>
В “Продажа товара” при создании считается полная стоимость заказа через FK на Product.
<br>
В Product добавлено поле "количество товара". При создании “Продажа товара” поле уменьшается. Если количество товара меньше чем требуется, то пользователь будет уведомлен об этом.
Поле available зависит от количество товара и изменяется если товара не остается.
<br>
При обновлении “Продажа товара” количество товара тоже изменяется в зависимости от изменения продажи.