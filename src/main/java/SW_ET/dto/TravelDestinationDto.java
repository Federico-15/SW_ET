package SW_ET.dto;

import SW_ET.entity.TravelDestination;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelDestinationDto {
    private Long id;
    private String title;
    private Long subRegionId;
    private String subRegionName;
    private String regionName;
    private String oneLineDesc;
    private List<String> imageUrls;
    private String detailedInfo;
    private Map<String, String> contactInfo;
    private List<ReviewDto> reviews;  // 여행지에 해당하는 리뷰 리스트 추가
    private Long favoriteCount; // 찜 개수 필드 추가

    // 여행지 목록보기에서 쓰임. 여행지 + 리뷰 같이보이게

    public TravelDestinationDto(TravelDestination travelDestination, List<ReviewDto> reviews) {
        this.id = travelDestination.getId();
        this.title = travelDestination.getTitle();
        this.subRegionId = travelDestination.getSubRegion().getSubRegionId();
        this.subRegionName = travelDestination.getSubRegionName();
        this.regionName = travelDestination.getRegion().getRegionName();
        this.oneLineDesc = travelDestination.getOneLineDesc();
        this.imageUrls = travelDestination.getImageUrls();
        this.detailedInfo = travelDestination.getDetailedInfo();
        this.contactInfo = travelDestination.getContactInfo();
        this.reviews = reviews;
        this.favoriteCount = 0L; // 기본값 설정
    }

    // 리뷰 정보를 포함하지 않는 생성자
    public TravelDestinationDto(TravelDestination travelDestination) {
        this(travelDestination, new ArrayList<>()); // 리뷰 없이 기본 정보만 설정
    }

    public TravelDestinationDto(TravelDestination travelDestination, Long favoriteCount, List<ReviewDto> reviews) {
        this(travelDestination, reviews);
        this.favoriteCount = favoriteCount;
    }
}
