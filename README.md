# Online Food Delivery System

This is an idea and understanding of how an ordering system work in back-end. 

The high-level picture of the system:

![online-food-delivery-system](/online-food-delivery-system.png)

This system has 6 services those are user-service, restaurant-service, order-service, delivery-service, agent-service, and the notification-service. The services are used to perform the user's, restaurant's and order's CRUD operation within the system. Each request is reaching the respective service through API-gateway.

Services and its working processes are mentioned in the below documents.
1. [User service](/user-service/README.md)
2. [Restaurant service](/restaurant-service/README.md)
3. [Order service](/order-service/README.md)
4. [Delivery service](/delivery-service/README.md)
5. [Agent service](/agent-service/README.md)
6. [Notification service](/notification-service/README.md)


### Sevices communication:

The inter-service communication is happening during the order service create operation. The customer and restaurant ids are given as payload of the create order API. The order service communicate with user and restaurant service for validate the given data. If those are valid then it will persist the given input data such as customer id, restaurant id, ordered menu items and the delivery details in the database. After persist it will push the ordered event to the order-placed kafka topic.

The delivery service listen and consume the data from order-placed event. The delivery service will contact the agent service for fetching available agents information and assign the orders to them. Once the agent is choosen it will again contact agent service and change the agent status from available to in-delivery, and changed the order's status in order service too. After that it will send the ordered event and it status to the order-out-for-delivery topic in kafka. Finally, it will change the order's status as delivered and mark the agent status as available, and it will push the same to the order-delivered topic in kafka.

The notification service listen all the above mentioned events and consume the data from kafka, and send those information to the user via web socket.

The above mentioned communication process is the major part of the system. As of now, the APIs are doesn't have any authentication mechanism, all of them are public APIs. And, the notification informations are send to the listeners who are all subscribed to the /queue/notifications topic instead of sending the information to the specific user.