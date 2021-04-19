package whut_404notfound.audio_editing_tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.domain.Statistics;
import whut_404notfound.audio_editing_tool.domain.VideoPart;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ModifyRepository extends JpaRepository<Modify, Integer> {
    List<Modify> findModifyByVideoId(int VideoId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update modify set modified_part_num=?2,modified_duration=?3,modified_part=?4 where video_id=?1", nativeQuery = true)
    int videoHasBeenModified(Integer video_id, Integer modified_part_num, Integer modified_duration, VideoPart videoPart);


    @Query(value = "select m.video_id,v.video_name,m.user_id,u.user_name,v.add_time,m.part_num,m.modified_part_num,m.modified_duration,m.inclusive_part,m.modified_part "
            +" from modify m , user u , video v"
            +" where m.video_id=v.video_id and m.user_id=u.id",nativeQuery = true)
    List<Map<String,Object>> findAllStatistics();

    @Query(value = "select m.video_id,v.video_name,m.user_id,u.user_name,v.add_time,m.part_num,m.modified_part_num,m.modified_duration,m.inclusive_part,m.modified_part "
            +" from modify m , user u , video v"
            +" where m.video_id=v.video_id and m.user_id=u.id and v.add_time >= ?1 and v.add_time<=?2",nativeQuery = true)
    List<Map<String,Object>> findStatisticsByTime(String date1,String date2);
}
