package com.order.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.order.service.client.ClientUtil;
import com.order.service.constants.Constants;
import com.order.service.exception.ValidationException;
import com.order.service.kafka.KafkaMessageProducer;
import com.order.service.model.Order;
import com.order.service.model.OrderedItems;
import com.order.service.model.dto.MenuDTO;
import com.order.service.model.dto.OrderDTO;
import com.order.service.model.dto.OrderedItemsDTO;
import com.order.service.model.dto.RestaurantDTO;
import com.order.service.repository.OrderRepository;
import com.order.service.validator.ValidatorAPIImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class OrderService {

    private final OrderRepository repository;

    private final ValidatorAPIImpl validatorAPI;

    private final EntityManager manager;

    private final ClientUtil clientUtil;

    private final KafkaMessageProducer kafkaMessageProducer;

    public OrderService(OrderRepository repository, ValidatorAPIImpl validatorAPI, EntityManager manager,
            ClientUtil clientUtil, KafkaMessageProducer kafkaMessageProducer) {
        this.repository = repository;
        this.validatorAPI = validatorAPI;
        this.manager = manager;
        this.clientUtil = clientUtil;
        this.kafkaMessageProducer = kafkaMessageProducer;
    }

    public OrderDTO createOrder(Order order) {
        validatorAPI.validateOrderData(order);
        OrderDTO orderDTO = convertOrderAsOrderDTO(repository.save(order));
        kafkaMessageProducer.publishOrderCreatedEvent(orderDTO);
        return orderDTO;
    }

    private OrderDTO convertOrderAsOrderDTO(Order order) {
        RestaurantDTO restaurantDTO = clientUtil.findRestaurantByIdWithMenu(order.getRestaurantId());
        validatorAPI.validateRestaurantMenuData(order, restaurantDTO);
        return convertOrderAsOrderDTO(order, restaurantDTO);
    }

    private OrderDTO convertOrderAsOrderDTO(Order order, RestaurantDTO restaurantDTO) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setCustomerId(order.getCustomerId());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setRestaurantId(order.getRestaurantId());
        dto.setStatus(order.getStatus());
        dto.setItems(convertOrderItemsAOrderedItemsDTO(restaurantDTO, order.getItems()));
        dto.setTotalPrice(calculateTotalPrice(order, restaurantDTO));
        return dto;
    }

    private List<OrderedItemsDTO> convertOrderItemsAOrderedItemsDTO(RestaurantDTO restaurantDTO,
            List<OrderedItems> orderedItems) {
        List<OrderedItemsDTO> orderedItemsList = new ArrayList<>();
        Map<Integer, MenuDTO> menuItems = restaurantDTO.getMenuItems().stream()
                .collect(Collectors.toMap(MenuDTO::id, menu -> menu));
        for (OrderedItems orderedItem : orderedItems) {
            orderedItemsList.add(convertOrderItemsAOrderedItemsDTO(restaurantDTO, orderedItem, menuItems));
        }
        return orderedItemsList;
    }

    private OrderedItemsDTO convertOrderItemsAOrderedItemsDTO(RestaurantDTO restaurantDTO, OrderedItems orderedItems,
            Map<Integer, MenuDTO> menuItems) {
        OrderedItemsDTO dto = new OrderedItemsDTO();
        dto.setId(orderedItems.getItemId());
        dto.setName(menuItems.get(orderedItems.getItemId()).name());
        dto.setPrice(menuItems.get(orderedItems.getItemId()).price());
        dto.setQuantity(orderedItems.getQuantity());
        return dto;
    }

    public double calculateTotalPrice(Order order, RestaurantDTO restaurantDTO) {
        Map<Integer, Double> itemsPrice = restaurantDTO.getMenuItems().stream()
                .collect(Collectors.toMap(MenuDTO::id, MenuDTO::price));
        double totalPrice = 0d;
        for (OrderedItems items : order.getItems()) {
            int quantity = items.getQuantity();
            double price = itemsPrice.get(items.getItemId());
            totalPrice += quantity * price;
        }
        return totalPrice;
    }

    public OrderDTO getOrderById(int orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ValidationException("Invalid orderId: " + orderId));
        return convertOrderAsOrderDTO(order);
    }

    @SuppressWarnings("unchecked")
    public List<OrderDTO> getAllOrders(int from, int limit) {
        String queryString = "SELECT * FROM " + Constants.TABLE_NAME + " LIMIT ?1 OFFSET ?2";
        Query query = manager.createNativeQuery(queryString, Order.class);
        query.setParameter(1, limit);
        query.setParameter(2, from);
        List<Order> orders = (List<Order>) query.getResultList();
        return orders.stream().map(order -> convertOrderAsOrderDTO(order)).collect(Collectors.toList());
    }

    public List<OrderDTO> getAllTheCustomerOrders(int customerId) {
        return repository.findByCustomerId(customerId).stream().map(order -> convertOrderAsOrderDTO(order))
                .collect(Collectors.toList());
    }

    public void deleteOrder(int orderId) {
        repository.deleteById(orderId);
    }

    public OrderDTO updateOrder(int orderId, Order order) {
        Order existingOrder = repository.findById(orderId)
                .orElseThrow(() -> new ValidationException("Invalid orderId: " + orderId));
        existingOrder.setStatus(order.getStatus());
        return convertOrderAsOrderDTO(repository.save(existingOrder));
    }

}
