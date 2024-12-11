# Agent service: API Documentation

The agent service provides APIs for performing the agent's CRUD operation.

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

#### Create an agent

```text
Endpoint: POST /agents
```

##### Parameters:
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `name` | `string` | **Required.** Agent's name |
| `password` | `string` | **Required.** Agent's password |
| `status` | `string` | **Required.** Agent's status. Allowed values are: NOT_AVAILABLE, AVAILABLE, IN_DELIVERY  |
| `deliveredOrders` | `array` | Agent's delivered orders details |

Delivered hours sub-keys list
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `orderId` | `string` | Order id |

##### Request Body
```json
{
    "name": "Agent-1",
    "phoneNo": "0987654321",
    "status": "AVAILABLE",
    "deliveredOrders": [{"orderId": 62}]
}
```

##### Response
```json
{
    "agentId": 1,
    "name": "Agent-1",
    "phoneNo": "0987654321",
    "status": "AVAILABLE",
    "deliveredOrders": [{
        "id": 1,
        "orderId": 62
    }]
}
```

#### Update an agent

```text
Endpoint: PATCH /agents/{agentId}
```

##### Parameters:
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `name` | `string` | Agent's name |
| `password` | `string` | Agent's password |
| `status` | `string` | Agent's status. Allowed values are: NOT_AVAILABLE, AVAILABLE, IN_DELIVERY  |
| `deliveredOrders` | `array` | Agent's delivered orders details |

Delivered hours sub-keys list
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `orderId` | `string` | Order id |


##### Request Body
```json
{
    "status": "IN_DELIVERY",
    "deliveredOrders": [{"orderId": 62}]
}
```

##### Response
```json
{
    "agentId": 1,
    "name": "Agent-1",
    "phoneNo": "0987654321",
    "status": "IN_DELIVERY",
    "deliveredOrders": [{
        "id": 1,
        "orderId": 62
    }]
}
```

#### Get an agent

The order informations are associated to the agent once its assigned and delivered by them.

```text
Endpoint: GET /agents/{agentId}
```

##### Response
```json
{
    "agentId": 1,
    "name": "Agent-1",
    "phoneNo": "0987654321",
    "status": "IN_DELIVERY",
    "deliveredOrders": [{
        "id": 1,
        "orderId": 62
    }]
}
```

#### Get all agents

```text
Endpoint: GET /agents
```

##### Parameters:
| Parameter | Type     | Description |
| :-------- | :------- | :------- |
| `from` | `integer` | Default value is 0 |
| `limit` | `integer` | Default value is 10 |

##### Response
```json
[{
    "agentId": 1,
    "name": "Agent-1",
    "phoneNo": "0987654321",
    "status": "IN_DELIVERY",
    "deliveredOrders": [{
        "id": 1,
        "orderId": 62
    }]
}]
```

#### Delete an agent

```text
Endpoint: DELETE /agents/{agentId}
```

##### Response
```text 
204 - No Content
```