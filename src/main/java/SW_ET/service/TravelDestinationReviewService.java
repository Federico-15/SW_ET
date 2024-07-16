/*
package SW_ET.service;

import SW_ET.dto.ReviewDto;
import SW_ET.dto.TravelDestinationDto;
import SW_ET.entity.Favorite;
import SW_ET.entity.Review;
import SW_ET.entity.TravelDestination;
import SW_ET.entity.User;
import SW_ET.repository.FavoriteRepository;
import SW_ET.repository.ReviewRepository;
import SW_ET.repository.TravelDestinationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TravelDestinationReviewService {

    @Autowired
    private TravelDestinationRepository travelDestinationRepository;


    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private FavoriteRepository favoriteRepository;


    public Page<TravelDestinationDto> getDestinationsBySubRegionId(Long subRegionId, Pageable pageable) {
        if (subRegionId == null) {
            log.warn("subRegionId is null when querying destinations");
            return new PageImpl<>(Collections.emptyList());
        }
        Page<TravelDestination> destinations = travelDestinationRepository.findBySubRegion_SubRegionId(subRegionId, pageable);
        return destinations.map(this::convertToDto);
    }


    @Transactional(readOnly = true)
    public Optional<TravelDestination> getDestinationById(Long destinationId) {
        return travelDestinationRepository.findById(destinationId);
    }

    public Page<TravelDestinationDto> getDestinationsByRegionId(Long regionId, Pageable pageable) {
        Page<TravelDestination> destinationPage = travelDestinationRepository.findByRegionId(regionId, pageable);
        return destinationPage.map(this::convertToDto);
    }

    // Entity를 DTO로 변환하는 메소드
    private TravelDestinationDto convertToDto(TravelDestination destination) {
        // TravelDestinationDto 생성자나 빌더를 이용하여 인스턴스 생성
        return new TravelDestinationDto(destination);
    }

    public Optional<TravelDestinationDto> getDestinationBySubRegionAndId(Long subRegionId, Long destinationId) {
        Optional<TravelDestination> destination = travelDestinationRepository.findBySubRegionIdAndDestinationId(subRegionId, destinationId);
        return destination.map(TravelDestinationDto::new);  // 존재하는 경우 DTO로 변환
    }

    public List<ReviewDto> getReviewsByRegionId(Long regionId) {
        List<Review> reviews = reviewRepository.findByRegionId(regionId);
        return reviews.stream()
                .map(review -> new ReviewDto(review))
                .collect(Collectors.toList());
    }

    public Page<TravelDestinationDto> searchDestinations(String searchTerm, Pageable pageable) {
        return travelDestinationRepository.findByTitleContaining(searchTerm, pageable)
                .map(destination -> new TravelDestinationDto(destination));  // Entity를 DTO로 변환
    }

    public void addFavorite(User user, Long destinationId) {
        TravelDestination destination = travelDestinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        favoriteService.addFavorite(user, destination);
    }

    public void removeFavorite(User user, Long destinationId) {
        try {
            log.debug("Fetching favorite for user: {} and destinationId: {}", user.getUserKeyId(), destinationId);
            Favorite favorite = favoriteRepository.findByUser_UserKeyIdAndTravelDestination_Id(user.getUserKeyId(), destinationId)
                    .orElseThrow(() -> new RuntimeException("Favorite not found"));
            log.debug("Favorite found: {}", favorite.getFavoriteId());
            favoriteRepository.delete(favorite);
            log.debug("Favorite deleted successfully");
        } catch (Exception e) {
            log.error("Error in removeFavorite service: {}", e.getMessage(), e);
            throw e;
        }
    }
    public boolean isFavorite(Long userKeyId, Long destinationId) {
        return favoriteRepository.findByUser_UserKeyIdAndTravelDestination_Id(userKeyId, destinationId).isPresent();
    }


    public List<TravelDestinationDto> getFavoritesByUser(User user) {
        return favoriteRepository.findByUser(user).stream()
                .map(favorite -> new TravelDestinationDto(favorite.getTravelDestination()))
                .collect(Collectors.toList());
    }

    public List<ReviewDto> getReviewsByUser(User user) {
        return reviewRepository.findByUser(user)
                .stream()
                .map(review -> new ReviewDto(review))
                .collect(Collectors.toList());
    }

}*/

package SW_ET.service;

import SW_ET.dto.ReviewDto;
import SW_ET.dto.TravelDestinationDto;
import SW_ET.entity.Favorite;
import SW_ET.entity.Review;
import SW_ET.entity.TravelDestination;
import SW_ET.entity.User;
import SW_ET.repository.FavoriteRepository;
import SW_ET.repository.ReviewRepository;
import SW_ET.repository.TravelDestinationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TravelDestinationReviewService {

    @Autowired
    private TravelDestinationRepository travelDestinationRepository;


    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private FavoriteRepository favoriteRepository;


    public Page<TravelDestinationDto> getDestinationsBySubRegionId(Long subRegionId, Pageable pageable) {
        if (subRegionId == null) {
            log.warn("subRegionId is null when querying destinations");
            return new PageImpl<>(Collections.emptyList());
        }
        Page<TravelDestination> destinations = travelDestinationRepository.findBySubRegion_SubRegionId(subRegionId, pageable);
        return destinations.map(this::convertToDto);
    }


    @Transactional(readOnly = true)
    public Optional<TravelDestination> getDestinationById(Long destinationId) {
        return travelDestinationRepository.findById(destinationId);
    }

    public Page<TravelDestinationDto> getDestinationsByRegionId(Long regionId, Pageable pageable) {
        Page<TravelDestination> destinationPage = travelDestinationRepository.findByRegionId(regionId, pageable);
        return destinationPage.map(this::convertToDto);
    }

    // Entity를 DTO로 변환하는 메소드
    private TravelDestinationDto convertToDto(TravelDestination destination) {
        // TravelDestinationDto 생성자나 빌더를 이용하여 인스턴스 생성
        return new TravelDestinationDto(destination);
    }

    public Optional<TravelDestinationDto> getDestinationBySubRegionAndId(Long subRegionId, Long destinationId) {
        Optional<TravelDestination> destination = travelDestinationRepository.findBySubRegionIdAndDestinationId(subRegionId, destinationId);
        return destination.map(TravelDestinationDto::new);  // 존재하는 경우 DTO로 변환
    }

    public List<ReviewDto> getReviewsByRegionId(Long regionId) {
        List<Review> reviews = reviewRepository.findByRegionId(regionId);
        return reviews.stream()
                .map(review -> new ReviewDto(review))
                .collect(Collectors.toList());
    }

    public Page<TravelDestinationDto> searchDestinations(String searchTerm, Pageable pageable) {
        return travelDestinationRepository.findByTitleContaining(searchTerm, pageable)
                .map(destination -> new TravelDestinationDto(destination));  // Entity를 DTO로 변환
    }

    public void addFavorite(User user, Long destinationId) {
        TravelDestination destination = travelDestinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        favoriteService.addFavorite(user, destination);
    }

    public void removeFavorite(User user, Long destinationId) {
        try {
            log.debug("Fetching favorite for user: {} and destinationId: {}", user.getUserKeyId(), destinationId);
            Favorite favorite = favoriteRepository.findByUser_UserKeyIdAndTravelDestination_Id(user.getUserKeyId(), destinationId)
                    .orElseThrow(() -> new RuntimeException("Favorite not found"));

            favoriteRepository.delete(favorite);
            log.debug("Favorite deleted successfully");
        } catch (Exception e) {
            log.error("Error in removeFavorite service: {}", e.getMessage(), e);
            throw e;
        }
    }
    public boolean isFavorite(Long userKeyId, Long destinationId) {
        return favoriteRepository.findByUser_UserKeyIdAndTravelDestination_Id(userKeyId, destinationId).isPresent();
    }


    public List<TravelDestinationDto> getFavoritesByUser(User user) {
        return favoriteRepository.findByUser(user).stream()
                .map(favorite -> new TravelDestinationDto(favorite.getTravelDestination()))
                .collect(Collectors.toList());
    }

    public List<ReviewDto> getReviewsByUser(User user) {
        return reviewRepository.findByUser(user).stream()
                .map(ReviewDto::new)
                .collect(Collectors.toList());
    }
}