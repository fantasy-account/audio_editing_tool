package whut_404notfound.audio_editing_tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import whut_404notfound.audio_editing_tool.domain.Modify;

import java.util.List;

public interface ModifyRepository extends JpaRepository<Modify,Integer> {
    List<Modify> findModifyByVideoId(int VideoId);
}
