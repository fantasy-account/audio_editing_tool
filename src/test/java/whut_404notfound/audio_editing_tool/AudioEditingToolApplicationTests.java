package whut_404notfound.audio_editing_tool;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.domain.VideoPart;
import whut_404notfound.audio_editing_tool.repository.UserRepository;

import java.sql.Time;


@SpringBootTest
class AudioEditingToolApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        System.out.println(userRepository.findUserByUsernameAndPassword("13300000000", "123456"));
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
