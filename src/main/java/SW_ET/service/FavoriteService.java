package SW_ET.service;

import SW_ET.entity.Favorite;
import SW_ET.entity.TravelDestination;
import SW_ET.entity.User;
import SW_ET.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    public List<Favorite> getFavoritesByUser(User user) {
        return favoriteRepository.findByUser(user);
    }

    public void addFavorite(User user, TravelDestination destination) {
        if (!favoriteRepository.existsByUserAndTravelDestination(user, destination)) {
            Favorite favorite = new Favorite();
            favorite.setUser(user);
            favorite.setTravelDestination(destination);
            favoriteRepository.save(favorite);
        }
    }

    public void removeFavorite(User user, TravelDestination destination) {
        favoriteRepository.deleteByUserAndTravelDestination(user, destination);
    }


}