package whut_404notfound.audio_editing_tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import whut_404notfound.audio_editing_tool.domain.Video;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findVideoByVideoIdOrderByAddTime(int videoId);
}
