package whut_404notfound.audio_editing_tool.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @program audio_editing_tool
 * @description 这个类和数据库中的登录信息表对应（对应表实体）
 * @author Xiaoyu Fan
 * @create 2021-03-07 16:05
 * @version 1.0
 */
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
}
