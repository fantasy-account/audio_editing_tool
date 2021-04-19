package whut_404notfound.audio_editing_tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 主程序类
 *
 * @author whut_404notfound
 */
@SpringBootApplication
public class AudioEditingToolApplication {
    public static void main(String[] args) {
        SpringApplication.run(AudioEditingToolApplication.class, args);
    }
}
