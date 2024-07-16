package SW_ET.service;/*
package SW_ET.service;

import SW_ET.entity.LikeDislike;
import SW_ET.entity.User;
import SW_ET.repository.LikeDislikeRepository;
import SW_ET.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeDislikeService {
    // 필요한 리포지토리 주입
    @Autowired
    private LikeDislikeRepository likeDislikeRepository;

    @Autowired
    private UserRepository userRepository;


    public void addLikeDislike(Long userId, String itemType, Long itemId, Boolean liked) {
        LikeDislike existingEntry = likeDislikeRepository.findByUser_UserKeyIdAndItemTypeAndItemId(userId, itemType, itemId).orElse(null);
        if (existingEntry != null) {
            existingEntry.setLiked(liked);
        } else {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            LikeDislike newEntry = new LikeDislike();
            newEntry.setUser(user);
            newEntry.setItemType(itemType);
            newEntry.setItemId(itemId);
            newEntry.setLiked(liked);
            likeDislikeRepository.save(newEntry);
        }
    }
}

*/
