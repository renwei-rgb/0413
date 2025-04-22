

import com.tss.atm.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class mp {


    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("John Doe");
        user.setAge(30);
    }

    @Test
    public void testSelect() {
    }
}
