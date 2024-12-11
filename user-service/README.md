# User service: API Documentation

The user service provides APIs for performing the user's CRUD operation.

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

#### Create a user

```text
Endpoint: POST /users
```

##### Parameters:
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `name` | `string` | **Required.** User's name |
| `email` | `string` | **Required.** User's email |
| `password` | `string` | **Required.** User's password |
| `phoneNo` | `string` | **Required.** User's phone no |
| `address` | `string` | **Required.** User's address |

##### Request Body
```json
{
    "name": "user-1",
    "email": "user-1@email.com",
    "phoneNo": "1234567890",
    "password": "sample-password",
    "address": "localhost"
}
```

##### Response
```json
{
    "id": 1,
    "name": "user-1",
    "email": "user-1@email.com",
    "phoneNo": "1234567890",
    "address": "localhost"
}
```

#### Update a user

```text
Endpoint: PATCH /users/{userId}
```

##### Parameters:
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `name` | `string` | User's name |
| `email` | `string` | User's email |
| `password` | `string` | User's password |
| `phoneNo` | `string` | User's phone no |
| `address` | `string` | User's address |

##### Request Body
```json
{
    "email": "updated-email@mail.com"
}
```

##### Response
```json
{
    "id": 1,
    "name": "user-1",
    "email": "updated-email@mail.com",
    "phoneNo": "1234567890",
    "address": "localhost"
}
```

#### Get a user

```text
Endpoint: GET /users/{userId}
```

##### Response
```json
{
    "id": 1,
    "name": "user-1",
    "email": "updated-email@mail.com",
    "phoneNo": "1234567890",
    "address": "localhost"
}
```

#### Get all users

```text
Endpoint: GET /users
```

##### Parameters:
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `from` | `integer` | Default value is 0 |
| `limit` | `integer` | Default value is 10 |

##### Response
```json
[{
    "id": 1,
    "name": "user-1",
    "email": "updated-email@mail.com",
    "phoneNo": "1234567890",
    "address": "localhost"
}]
```

#### Delete a user

```text
Endpoint: DELETE /users/{userId}
```

##### Response
```text 
204 - No Content
```