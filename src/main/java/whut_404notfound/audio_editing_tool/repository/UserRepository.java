package whut_404notfound.audio_editing_tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import whut_404notfound.audio_editing_tool.domain.User;

import java.util.List;

/**
 * 数据访问层（最底层）。数据库查询接口类，从此处开始查询数据库，并返回给上层
 */
public interface UserRepository extends JpaRepository<User,Integer> {
   List<User> findUserByUsernameAndPassword(String name, String pwd);
}
