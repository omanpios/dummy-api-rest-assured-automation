package pojo;

import lombok.Data;
import java.util.List;

@Data
public class UserListResponse {
    private List<UserPreview> data;
    private int total;
    private int page;
    private int limit;
}
