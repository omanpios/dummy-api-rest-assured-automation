package pojo;

import lombok.Data;

@Data
public class User {
    private String id;
    private String title;
    private String firstName;
    private String lastName;
    private String picture;
    private String gender;
    private String email;
    private String dateOfBirth;
    private String phone;
    private String registerDate;
    private String updatedDate;
    private Location location;
}
