# Order service: API Documentation

The order service provides APIs for performing the order's CRUD operations.

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

### APIs

#### Create an order

This API contacts user and restaurant service for validate the given customer and restaurant id. After validate, this API will calculate the total price and push the order-placed event to the kafka. 

```text
Endpoint: POST /orders
```

##### Parameters:
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `customerId` | `integer` | **Required.** User's id value is passed here |
| `restaurantId` | `integer` | **Required.** Restaurant's id |
| `deliveryAddress` | `string` | **Required.** User's delivery address |
| `items` | `array` | **Required.** Ordering items id and its quantity |
| `status` | `string` | Order's status. Allowed values are: PLACED, OUT_FOR_DELIVERY, DELIVERED |

items sub-key details
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `itemId` | `integer` | **Required.** Ordering items id |
| `quantity` | `integer` | **Required.** Item quantity |

##### Request Body
```json
{
    "customerId": 10,
    "restaurantId": 4,
    "items": [
        {
            "itemId": 6,
            "quantity": 4
        },
        {
            "itemId": 8,
            "quantity": 1
        }
    ],
    "deliveryAddress": "7, Xyz, Abc."
}
```

##### Response
```json
{
    "orderId": 1,
    "customerId": 10,
    "restaurantId": 4,
    "deliveryAddress": "7, Xyz, Abc.",
    "status": "PLACED",
    "items": [
        {
            "id": 6,
            "name": "Item-110",
            "price": 7.0,
            "quantity": 4
        },
        {
            "id": 8,
            "name": "Item-110",
            "price": 7.0,
            "quantity": 1
        }
    ],
    "totalPrice": 35.0
}
```

#### Update an order

```text
Endpoint: PATCH /orders/{orderId}
```

##### Parameters:
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `customerId` | `integer` | **Required.** User's id value is passed here |
| `restaurantId` | `integer` | **Required.** Restaurant's id |
| `deliveryAddress` | `string` | **Required.** User's delivery address |
| `items` | `array` | **Required.** Ordering items id and its quantity |
| `status` | `string` | Order's status. Allowed values are: PLACED, OUT_FOR_DELIVERY, DELIVERED |

items sub-key details
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `itemId` | `integer` | **Required.** Ordering items id |
| `quantity` | `integer` | **Required.** Item quantity |

##### Request Body
```json
{
    "status": "OUT_FOR_DELIVERY"
}
```

##### Response
```json
{
    "orderId": 1,
    "customerId": 10,
    "restaurantId": 4,
    "deliveryAddress": "7, Xyz, Abc.",
    "status": "OUT_FOR_DELIVERY",
    "items": [
        {
            "id": 6,
            "name": "Item-110",
            "price": 7.0,
            "quantity": 4
        },
        {
            "id": 8,
            "name": "Item-110",
            "price": 7.0,
            "quantity": 1
        }
    ],
    "totalPrice": 35.0
}
```

#### Get an order

```text
Endpoint: GET /orders/{orderId}
```

##### Response
```json
{
    "orderId": 1,
    "customerId": 10,
    "restaurantId": 4,
    "deliveryAddress": "7, Xyz, Abc.",
    "status": "OUT_FOR_DELIVERY",
    "items": [
        {
            "id": 6,
            "name": "Item-110",
            "price": 7.0,
            "quantity": 4
        },
        {
            "id": 8,
            "name": "Item-110",
            "price": 7.0,
            "quantity": 1
        }
    ],
    "totalPrice": 35.0
}
```

#### Get all orders

```text
Endpoint: GET /orders
```

##### Parameters:
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `from` | `integer` | Default value is 0 |
| `limit` | `integer` | Default value is 10 |

##### Response
```json
[{
    "orderId": 1,
    "customerId": 10,
    "restaurantId": 4,
    "deliveryAddress": "7, Xyz, Abc.",
    "status": "OUT_FOR_DELIVERY",
    "items": [
        {
            "id": 6,
            "name": "Item-110",
            "price": 7.0,
            "quantity": 4
        },
        {
            "id": 8,
            "name": "Item-110",
            "price": 7.0,
            "quantity": 1
        }
    ],
    "totalPrice": 35.0
}]
```

#### Delete an order

```text
Endpoint: DELETE /orders/{orderId}
```

##### Response
```text 
204 - No Content
```

#### Get all User's orders

```text
Endpoint: GET /orders/customers/{customerId}
```

##### Response
```json
[{
    "orderId": 1,
    "customerId": 10,
    "restaurantId": 4,
    "deliveryAddress": "7, Xyz, Abc.",
    "status": "OUT_FOR_DELIVERY",
    "items": [
        {
            "id": 6,
            "name": "Item-110",
            "price": 7.0,
            "quantity": 4
        },
        {
            "id": 8,
            "name": "Item-110",
            "price": 7.0,
            "quantity": 1
        }
    ],
    "totalPrice": 35.0
}]
```