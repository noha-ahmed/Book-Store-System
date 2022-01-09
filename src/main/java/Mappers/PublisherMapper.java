package Mappers;

import Models.Publisher;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherMapper {

    public static Publisher toPublisher(ResultSet resultSet) throws SQLException {
        return Publisher.builder().name(resultSet.getNString("Name"))
                .address(resultSet.getNString("Address"))
                .telephoneNumbers(resultSet.getNString("Phone_Number"))
                .build();
    }
}
