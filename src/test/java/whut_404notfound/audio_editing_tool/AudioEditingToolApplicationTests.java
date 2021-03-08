package whut_404notfound.audio_editing_tool;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whut_404notfound.audio_editing_tool.repository.UserRepository;

@SpringBootTest
class AudioEditingToolApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        System.out.println(userRepository.findUserByNameAndPwd("13300000000","123456"));
    }

}
