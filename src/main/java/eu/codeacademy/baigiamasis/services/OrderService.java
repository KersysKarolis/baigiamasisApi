package eu.codeacademy.baigiamasis.services;

import eu.codeacademy.baigiamasis.converters.OrderConverter;
import eu.codeacademy.baigiamasis.dto.OrderDTO;
import eu.codeacademy.baigiamasis.entities.Order;
import eu.codeacademy.baigiamasis.repositories.ClientRepository;
import eu.codeacademy.baigiamasis.repositories.EmployeeRepository;
import eu.codeacademy.baigiamasis.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    MessageSource messageSource;
    public List<OrderDTO> getAllOrdersByClientId(Pageable pageable, Long id){
        Page<Order> orderPage = null;
        if(id != null){
            orderPage = orderRepository.findAllByClient_Id(pageable, id);
        }else {
            orderPage = orderRepository.findAll(pageable);
        }
        return OrderConverter.convertOrdersToOrdersDTO(orderPage);
    }
    public OrderDTO getOrderById(Long id){
        return OrderConverter.convertOrderToOrderDTO(orderRepository.findById(id).get());
    }
    public void deleteOrderById(Long id){
        orderRepository.deleteById(id);
    }
    public OrderDTO addNewOrder(OrderDTO orderDTO){
        boolean isClientExist = clientRepository.existsById(orderDTO.getClientDtoId());
        boolean isEmployeeExist = employeeRepository.existsById(orderDTO.getEmployeeDtoId());
        if(!isClientExist){
            throw new NoSuchElementException(messageSource.getMessage("client.by.id.not.found", null, LocaleContextHolder.getLocale()));
        } else if(!isEmployeeExist){
            throw new NoSuchElementException(messageSource.getMessage("employee.by.id.not.found", null, LocaleContextHolder.getLocale()));
        } else {
            Order order = OrderConverter.convertOrderDTOToOrder(orderDTO);
            orderRepository.saveAndFlush(order);
            return OrderConverter.convertOrderToOrderDTO(order);
        }
    }
    public OrderDTO updateOrderById(OrderDTO orderDTO){
        Order order = OrderConverter.convertOrderDTOToOrder(orderDTO);
        if(order != null){
            if(clientRepository.existsById(orderDTO.getClientDtoId())){
                order.setClient(clientRepository.findById(orderDTO.getClientDtoId()).get());
            }
            if(employeeRepository.existsById(orderDTO.getEmployeeDtoId())) {
                order.setEmployee(employeeRepository.findById(orderDTO.getEmployeeDtoId()).get());
            }
            orderRepository.saveAndFlush(order);
            return OrderConverter.convertOrderToOrderDTO(order);
        } else{
            throw new NoSuchElementException(messageSource.getMessage("order.by.id.not.found", null, LocaleContextHolder.getLocale()));
        }
    }

}
