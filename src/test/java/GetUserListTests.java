import org.junit.jupiter.api.Test;

import api.GetUserList;

public class GetUserListTests {
    @Test
    void test() {
        GetUserList getUserList = new GetUserList();
        System.out.println(getUserList.response());
    }
}
