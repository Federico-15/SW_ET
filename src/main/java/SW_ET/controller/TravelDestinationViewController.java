package SW_ET.controller;

import SW_ET.dto.ReviewDto;
import SW_ET.dto.SubRegionDto;
import SW_ET.dto.TravelDestinationDto;
import SW_ET.entity.Region;
import SW_ET.entity.SubRegion;
import SW_ET.entity.User;
import SW_ET.repository.RegionRepository;
import SW_ET.repository.ReviewRepository;
import SW_ET.repository.SubRegionRepository;
import SW_ET.repository.TravelDestinationRepository;
import SW_ET.service.TravelDestinationReviewService;
import SW_ET.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mysql.cj.conf.PropertyKey.logger;


@Controller
@RequestMapping("/api")
@Slf4j
public class TravelDestinationViewController {

    @Autowired
    private TravelDestinationReviewService travelDestinationReviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TravelDestinationRepository travelDestinationRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SubRegionRepository subRegionRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(TravelDestinationViewController.class);

    @GetMapping("/sub-regions/{subRegionId}/details/{destinationId}")
    public String getDestinationDetails(@PathVariable Long subRegionId, @PathVariable Long destinationId, Model model) {
        log.debug("Fetching details for subRegionId: {} and destinationId: {}", subRegionId, destinationId);
        Optional<TravelDestinationDto> destination = travelDestinationReviewService.getDestinationBySubRegionAndId(subRegionId, destinationId);

        if (!destination.isPresent()) {
            return "errorPage"; // 에러 페이지 또는 적절한 메시지를 보여주는 뷰 이름
        }

        model.addAttribute("destination", destination.get());
        return "DestinationAndReview/destinationDetails"; // 세부 정보를 보여주는 Thymeleaf 템플릿 뷰 이름
    }

    @GetMapping("/sub-regions/{subRegionId}")
    public String getDestinationsBySubRegion(@PathVariable Long subRegionId, @RequestParam(defaultValue = "0") int page, Model model) {
        if (subRegionId == null) {
            model.addAttribute("error", "SubRegionId cannot be null");
            return "errorPage"; // 에러 페이지로 리다이렉트
        }

        Pageable pageable = PageRequest.of(page, 9); // 페이지당 9개의 항목 설정

        // 상세지역에 대한 리뷰 데이터 조회 및 변환
        List<ReviewDto> subRegionReviews = reviewRepository.findBySubRegionId(subRegionId).stream()
                .map(ReviewDto::new)  // ReviewDto 생성자를 사용하여 Review 객체를 ReviewDto 객체로 변환
                .collect(Collectors.toList());

        Page<TravelDestinationDto> destinationsPage = travelDestinationReviewService.getDestinationsBySubRegionId(subRegionId, pageable);

        SubRegion selectedSubRegion = subRegionRepository.findById(subRegionId).orElseThrow(() -> new ResourceNotFoundException("SubRegion not found with ID: " + subRegionId));
        Long regionId = selectedSubRegion.getRegion().getRegionId();

        // 같은 regionId 아래에 있는 subRegion 목록 조회
        List<SubRegionDto> subRegions = subRegionRepository.findByRegionId(regionId).stream()
                .map(subReg -> new SubRegionDto(subReg.getSubRegionId(), subReg.getSubRegionName()))
                .collect(Collectors.toList());

        model.addAttribute("subRegions", subRegions); // 서브 지역 목록 추가
        model.addAttribute("selectedRegionName", selectedSubRegion.getSubRegionName()); // selectedRegionName 추가
        model.addAttribute("destinations", destinationsPage.getContent());
        model.addAttribute("page", destinationsPage);  // Page 객체 추가
        model.addAttribute("subRegionReviews", subRegionReviews); // 모델에 상세지역 리뷰 추가
        model.addAttribute("subRegionId", subRegionId);  // subRegionId 추가

        return "DestinationAndReview/sub-region-list";
    }

    @GetMapping("/regions/{regionId}")
    public String showDestinationsByRegion(@PathVariable Long regionId, @RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 9);
        Region region = regionRepository.findById(regionId).orElseThrow(() -> new ResourceNotFoundException("Region not found with ID: " + regionId));

        List<SubRegionDto> subRegionDtos = subRegionRepository.findByRegionId(regionId).stream()
                .map(subRegion -> new SubRegionDto(subRegion.getSubRegionId(), subRegion.getSubRegionName()))
                .collect(Collectors.toList());

        // 리뷰 데이터 조회
        List<ReviewDto> regionReviews = reviewRepository.findByRegionId(regionId).stream()
                .map(review -> new ReviewDto(review))  // ReviewDto 생성자를 사용하여 Review 객체를 ReviewDto 객체로 변환
                .collect(Collectors.toList());

        model.addAttribute("selectedRegionName", region.getRegionName()); // selectedRegionName 추가
        model.addAttribute("subRegions", subRegionDtos);
        model.addAttribute("regionId", regionId);
        model.addAttribute("regionReviews", regionReviews);  // 리뷰 데이터를 모델에 추가

        Page<TravelDestinationDto> destinationsPage = travelDestinationReviewService.getDestinationsByRegionId(regionId, pageable);
        model.addAttribute("destinations", destinationsPage.getContent());
        model.addAttribute("page", destinationsPage);  // Page 객체 추가

        return "DestinationAndReview/region-list";
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TravelDestinationDto>> searchDestinations(
            @RequestParam String searchTerm,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<TravelDestinationDto> searchResults = travelDestinationReviewService.searchDestinations(searchTerm, pageable);
        return ResponseEntity.ok(searchResults);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    @PostMapping("/favorites/{destinationId}")
    public ResponseEntity<?> addFavorite(@PathVariable Long destinationId, Principal principal) {
        try {
            User user = userService.getUserByUserNickname(principal.getName());
            travelDestinationReviewService.addFavorite(user, destinationId);
            return ResponseEntity.ok("Favorite added successfully");
        } catch (Exception e) {
            log.error("Error adding favorite: {}", e.getMessage(), e);  // 오류 상세 포함 로깅
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding favorite");
        }
    }


    @DeleteMapping("/favorites/{destinationId}")
    public ResponseEntity<?> removeFavorite(@PathVariable Long destinationId, Principal principal) {
        try {
            User user = userService.getUserByUserNickname(principal.getName());
            log.debug("Removing favorite for user: {} and destinationId: {}", user.getUserKeyId(), destinationId);
            travelDestinationReviewService.removeFavorite(user, destinationId);
            log.debug("Favorite removed successfully");
            return ResponseEntity.ok("Favorite removed successfully");
        } catch (Exception e) {
            log.error("Error removing favorite: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing favorite");
        }
    }


    @GetMapping("/favorites/status/{destinationId}")
    public ResponseEntity<Boolean> checkFavoriteStatus(@PathVariable Long destinationId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.badRequest().body(false);
        }
        User user = userService.getUserByUserNickname(principal.getName());
        boolean isFavorite = travelDestinationReviewService.isFavorite(user.getUserKeyId(), destinationId);
        return ResponseEntity.ok(isFavorite);
    }

    @GetMapping("/main")
    public String showMainPage(Model model) {
        List<Region> regions = regionRepository.findAll();
        model.addAttribute("regions", regions);
        return "/users/home";  // 메인 페이지 템플릿 이름
    }


}
