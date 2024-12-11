# Delivery service: API Documentation

The Delivery service provides APIs for getting the deliveries informations. The delivery creation is performed in back-end, and it can't be done via API. So, currently, the delivery service doesn't have create, update and delete APIs.

The service listens ```text order-placed``` event in kafka and consume whenever the data is pushed into the topic. It will assign the placed order to the available agent and push that order status to the kafka, and the pushing events are ```text order-out-for-delivery``` and ```text order-delivered```

It will contact agent-service for getting the available agents informations.

### Responses

The following responses are returned from the server for the API process.

| Status code | Message  |
| :-------- | :------- |
| `200` | `Ok` |
| `-` | `-` |
| `500` | `Internal Server Error` |

### APIs

#### Get all deliveries

```text
Endpoint: GET /deliveries
```

##### Response
```json
[{
    "id": 1,
    "orderId": 28,
    "customerId": 1,
    "deliveryAgentId": 1,
    "message": "Your order: #28 has been placed",
    "status": "PLACED"
}]
```

#### Get a delivery details

```text
Endpoint: GET /deliveries/{deliveryId}
```

##### Response
```json
{
    "id": 1,
    "orderId": 28,
    "customerId": 1,
    "deliveryAgentId": 1,
    "message": "Your order: #28 has been placed",
    "status": "PLACED"
}
```