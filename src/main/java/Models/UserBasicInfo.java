package Models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserBasicInfo {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String shippingAddress;
    private boolean privilege;
}
