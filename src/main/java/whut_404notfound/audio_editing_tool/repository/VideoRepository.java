package whut_404notfound.audio_editing_tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import whut_404notfound.audio_editing_tool.domain.Video;

import javax.transaction.Transactional;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findVideoByVideoIdOrderByAddTime(int videoId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update video set video_duration=?2 ,video_url=?3 ,image_url=?4 where video_id=?1", nativeQuery = true)
    int updateVideo(Integer video_id, Integer video_duration, String video_url, String image_url);
}
