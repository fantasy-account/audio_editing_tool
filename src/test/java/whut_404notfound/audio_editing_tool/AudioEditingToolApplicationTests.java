package whut_404notfound.audio_editing_tool;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.domain.User;
import whut_404notfound.audio_editing_tool.domain.VideoPart;
import whut_404notfound.audio_editing_tool.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Time;
import java.util.UUID;


@SpringBootTest
class AudioEditingToolApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() {
//        System.out.println(dataSource.getClass());
//        System.out.println(userRepository.findUserByUsernameAndPassword("13720113769", "li001015fei"));
//        User user=new User();
//        user.setUsername("2");
//        user.setPassword("2");
//        System.out.println(userRepository.saveAndFlush(user));
        //System.out.println(UUID.randomUUID().toString().replace("-","").length());

    }

    @Test
    void xuliehuatest() throws Exception {

        Time[] startTime = new Time[3];
        Time[] endTime = new Time[3];
        String[] str = new String[3];

        VideoPart test = new VideoPart(3, startTime, endTime, str);

        for(int i=0;i<3;i++) {
            Time now = new Time(10, 10, i);
            test.setStartTime(now);
            Time now1 = new Time(10, 10, (i+10));
            test.setEndTime(now1);
            test.setContent("测试文字，老师说的第"+(i+1)+"段话");
            test.addNum();
        }
        byte[] s = Modify.toByteArray(test);
        Modify.toObject(s);
    }
}
