package Mappers;

import Models.User;
import Models.UserBasicInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static User toUser(ResultSet resultSet) throws SQLException {
        return User.builder().username(resultSet.getNString("UserName"))
                .password(resultSet.getNString("Password"))
                .firstName(resultSet.getNString("First_Name"))
                .lastName(resultSet.getNString("Last_Name"))
                .email(resultSet.getNString("Email"))
                .phoneNumber(resultSet.getNString("Phone_Number"))
                .shippingAddress(resultSet.getNString("Shipping_Address"))
                .privilege(resultSet.getBoolean("privilege"))
                .build();
    }

    public static User toUser(UserBasicInfo info) throws SQLException {
        return User.builder().username(info.getUsername())
                .password(info.getPassword())
                .firstName(info.getFirstName())
                .lastName(info.getLastName())
                .email(info.getEmail())
                .phoneNumber(info.getPhoneNumber())
                .shippingAddress(info.getShippingAddress())
                .privilege(info.isPrivilege())
                .build();
    }
}
