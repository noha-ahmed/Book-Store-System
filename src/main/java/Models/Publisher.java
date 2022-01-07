package Models;

import lombok.Data;

import java.util.List;

@Data
public class Publisher {
    private String name;
    private String address;
    private List<String> telephoneNumbers;

}
