# Restaurant service: API Documentation

The restaurant service provides APIs for performing the restaurant's CRUD operation.

### Responses

The following responses are returned from the server for the API process.

| Status code | Message  |
| :-------- | :------- |
| `201` | `Created` |
| `200` | `Ok` |
| `204` | `No Content` |
| `-` | `-` |
| `400` | `Bad Request` |
| `429` | `Unprocessable Entity` |
| `500` | `Internal Server Error` |

## Restaurant CRUD APIs

#### Create a restaurant

```text
Endpoint: POST /restaurants
```

##### Parameters:
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `name` | `string` | **Required.** Restaurant's name |
| `address` | `string` | **Required.** Restaurant's address |
| `email` | `string` | **Required.** Restaurant's email |
| `phoneNo` | `string` | **Required.** Restaurant's phone no |
| `operatingHours` | `object` | **Required.** Restaurant's Operating Hours Details |

```text Operating hours``` sub-keys details
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `openingTime` | `string` | **Required.** Restaurant's opening time, must be given in the time format |
| `closingTime` | `string` | **Required.** Restaurant's closing time, must be given in the time format |

##### Request Body
```json
{
    "name": "Restaurant-1",
    "address": "restaurant-address",
    "email": "restaurant@hotel.com",
    "phoneNo": "0987654321",
    "operatingHours": {
        "openingTime": "10:00PM",
        "closingTime": "10:30PM"
    }
}
```

##### Response
```json
{
    "id": "1",
    "name": "Restaurant-1",
    "address": "restaurant-address",
    "email": "restaurant@hotel.com",
    "phoneNo": "0987654321",
    "operatingHours": {
        "openingTime": "10:00PM",
        "closingTime": "10:30PM"
    }
}
```

#### Update a restaurant

```text
Endpoint: PATCH /restaurants/{restaurantId}
```

##### Parameters:
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `name` | `string` | Restaurant's name |
| `address` | `string` | Restaurant's address |
| `email` | `string` | Restaurant's email |
| `phoneNo` | `string` | Restaurant's phone no |
| `operatingHours` | `object` | Restaurant's Operating Hours Details |

Operating hours sublist keys details
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `openingTime` | `string` | **Required.** Restaurant's opening time, must be given in the time format |
| `closingTime` | `string` | **Required.** Restaurant's closing time, must be given in the time format |

##### Request Body
```json
{
    "email": "updated-email@mail.com"
}
```

##### Response
```json
{
    "id": "1",
    "name": "Restaurant-1",
    "address": "restaurant-address",
    "email": "updated-email@mail.com",
    "phoneNo": "0987654321",
    "operatingHours": {
        "openingTime": "10:00PM",
        "closingTime": "10:30PM"
    }
}
```

#### Get a restaurant

```text
Endpoint: GET /restaurants/{restaurantId}
```

##### Response
```json
{
    "id": "1",
    "name": "Restaurant-1",
    "address": "restaurant-address",
    "email": "updated-email@mail.com",
    "phoneNo": "0987654321",
    "operatingHours": {
        "openingTime": "10:00PM",
        "closingTime": "10:30PM"
    }
}
```

#### Get all restaurants

```text
Endpoint: GET /restaurants
```

##### Parameters:
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `from` | `integer` | Default value is 0 |
| `limit` | `integer` | Default value is 10 |

##### Response
```json
[{
    "id": "1",
    "name": "Restaurant-1",
    "address": "restaurant-address",
    "email": "updated-email@mail.com",
    "phoneNo": "0987654321",
    "operatingHours": {
        "openingTime": "10:00PM",
        "closingTime": "10:30PM"
    }
}]
```

#### Delete a restaurant

```text
Endpoint: DELETE /restaurants/{restaurantId}
```

##### Response
```text 
204 - No Content
```

#### Get a restaurant with its menu details

The menu details are associated to the restaurants with the help of Menu's entity CRUD APIs and those APIs are mentioned below.

```text
Endpoint: GET /restaurants/{restaurantId}/with-menu
```

##### Response
```json
[{
    "id": 1,
    "name": "Restaurant-1",
    "address": "restaurant-address",
    "email": "updated-email@mail.com",
    "phoneNo": "0987654321",
    "operatingHours": {
        "openingTime": "10:00PM",
        "closingTime": "10:30PM"
    },
    "menuItems": [{
        "id": 1,
        "name": "Item-101",
        "price": 7
    }]
}]
```

## Restaurant's Menu CRUD APIs

#### Add menus to the restaurant

Add list of menu items to the restaurant.

```text
Endpoint: POST /restaurants/{restaurantId}/menu
```

##### Parameters:
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `name` | `string` | **Required.** Menu item's name |
| `price` | `double` | **Required.** Item's price |


##### Request Body
```json
[{
    "name": "Item-110",
    "price": 7
}]
```

##### Response
```json
[{
    "id": 10,
    "name": "Item-110",
    "price": 7.0
}]
```

#### Update menu

```text
Endpoint: POST /restaurants/{restaurantId}/menu/{menuId}
```

##### Parameters:
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `name` | `string` | Menu item's name |
| `price` | `double` | Item's price |


##### Request Body
```json
{
    "name": "Item-110",
    "price": 7
}
```

##### Response
```json
{
    "id": 10,
    "name": "Item-110",
    "price": 7.0
}
```

#### Get a menu

```text
Endpoint: GET /restaurants/{restaurantId}/menu/{menuId}
```

##### Response
```json
{
    "id": 10,
    "name": "Item-110",
    "price": 7.0
}
```

#### Get all menu

```text
Endpoint: GET /restaurants/{restaurantId}/menu/
```

##### Response
```json
[{
    "id": 10,
    "name": "Item-110",
    "price": 7.0
}]
```

#### Delete a menu

```text
Endpoint: DELETE /restaurants/{restaurantId}/menu/{menuId}
```

##### Response
```text 
204 - No Content
```