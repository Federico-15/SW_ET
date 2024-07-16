package SW_ET.repository;

import SW_ET.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByUser_UserKeyId(Long userKeyId);
}