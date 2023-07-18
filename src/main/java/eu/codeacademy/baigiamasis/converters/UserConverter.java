package eu.codeacademy.baigiamasis.converters;

import eu.codeacademy.baigiamasis.dto.UserDTO;
import eu.codeacademy.baigiamasis.entities.User;

import java.util.ArrayList;
import java.util.List;

public abstract class UserConverter {
    public static UserDTO convertUserToUserDTO(User user) {
        UserDTO userDTO = null;
        if (user != null) {
            userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setSurname(user.getSurname());
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhone(user.getPhoneNumber());
            userDTO.setOrderList(OrderConverter.convertOrdersToOrdersDTO(user.getOrderList()));
        }
        return userDTO;
    }

    public static User convertUserDTOToUser(UserDTO userDTO) {
        User user = null;
        if (userDTO != null) {
            user = new User();
            user.setId(userDTO.getId());
            user.setName(userDTO.getName());
            user.setSurname(userDTO.getSurname());
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setOrderList(OrderConverter.convertOrdersDTOToOrders(userDTO.getOrderList()));
        }
        return user;
    }

    public static List<UserDTO> convertUsersToUsersDTO(List<User> usersList) {
        List<UserDTO> userDTOList = null;
        if (usersList != null && !usersList.isEmpty()) {
            userDTOList = new ArrayList<>();
            for (User e : usersList) {
                userDTOList.add(convertUserToUserDTO(e));
            }
        }
        return userDTOList;

    }
}
