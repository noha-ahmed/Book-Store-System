package Models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Publisher {
    private String name;
    private String address;
    private String telephoneNumbers;

}
