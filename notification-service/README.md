# Notification service: Documentation

The notification service just listen the order events in kafka, and consume data from it, and send it to the user via Web socket. The listening events are ```text order-placed```, ```text order-out-for-delivery``` and ```text order-delivered```.

The ```text /ws-notifications``` is the notification service base url, and, the notification receiver or the web socket data consumer can connect to the service via this url. And they should subscribe to the ```text /queue/notifications ``` url for receiving the notification information.