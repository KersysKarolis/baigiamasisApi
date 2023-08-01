package eu.codeacademy.baigiamasis.converters;

import eu.codeacademy.baigiamasis.dto.OrderDTO;
import eu.codeacademy.baigiamasis.entities.Employee;
import eu.codeacademy.baigiamasis.entities.Order;
import eu.codeacademy.baigiamasis.entities.Client;

import java.util.ArrayList;
import java.util.List;

public abstract class OrderConverter {
    public static Order convertOrderDTOToOrder (OrderDTO orderDTO){
        Order order = null;
        if(orderDTO != null){
            order = new Order();
            order.setId(orderDTO.getId());
            order.setOrderNumber(orderDTO.getOrderNumber());
            if(orderDTO.getEmployeeDtoId() != null){
                Employee employee = new Employee();
                employee.setId(orderDTO.getEmployeeDtoId());
                order.setEmployee(employee);
            }
            if(orderDTO.getClientDtoId() != null){
                Client client = new Client();
                client.setId(orderDTO.getClientDtoId());
                order.setClient(client);
            }
        }
        return order;
    }
    public static OrderDTO convertOrderToOrderDTO (Order order){
        OrderDTO orderDTO = null;
        if(order != null){
            orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setOrderNumber(order.getOrderNumber());
            orderDTO.setClientDtoId(order.getClient().getId());
            orderDTO.setEmployeeDtoId(order.getEmployee().getId());
        }
        return orderDTO;
    }
    public static List<OrderDTO> convertOrdersToOrdersDTO(Iterable<Order> orderList) {
        List<OrderDTO> ordersDTO = null;
        if(orderList != null){
            ordersDTO = new ArrayList<>();
            for(Order e: orderList){
                ordersDTO.add(convertOrderToOrderDTO(e));
            }
        }
        return ordersDTO;
    }

    public static List<Order> convertOrdersDTOToOrders(List<OrderDTO> ordersDTOList) {
        List<Order> ordersList = null;
        if(ordersDTOList != null && !ordersDTOList.isEmpty()){
            ordersList = new ArrayList<>();
            for (OrderDTO e: ordersDTOList){
                ordersList.add(convertOrderDTOToOrder(e));
            }
        }
        return ordersList;
    }
}
