package whut_404notfound.audio_editing_tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.domain.VideoPart;

import javax.transaction.Transactional;
import java.util.List;

public interface ModifyRepository extends JpaRepository<Modify,Integer> {
    List<Modify> findModifyByVideoId(int VideoId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update modify set modified_part_num=?2,modified_duration=?3,modified_part=?4 where video_id=?1", nativeQuery = true)
    int videoHasBeenModified(Integer video_id, Integer modified_part_num,Integer modified_duration, VideoPart videoPart);
}
