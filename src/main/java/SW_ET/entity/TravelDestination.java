package SW_ET.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "travel_destinations")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TravelDestination { // 여행지 데이터 엔티티

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("title")
    @Column(nullable = false)
    private String title;

    @JsonProperty("상세지역")
    @Column(nullable = false)
    private String subRegionName;

    @JsonProperty("지역")
    @Column(nullable = false)
    private String regionName; // json데이터의 region값에 저장되어있는 것을 regionName 컬럼에 저장. region과 중복선언 피함.

    @JsonProperty("one_line_desc")
    @Column(nullable = true)
    private String oneLineDesc;

    @JsonProperty("image_urls")
    @ElementCollection
    @CollectionTable(name = "image_urls", joinColumns = @JoinColumn(name = "destination_id"))
    @Column(name = "url")
    private List<String> imageUrls;

    @JsonProperty("detailed_info")
    @Column(columnDefinition = "TEXT")
    private String detailedInfo;

    @JsonProperty("contact_info")
    @ElementCollection
    @CollectionTable(name = "contact_info", joinColumns = @JoinColumn(name = "destination_id"))
    @MapKeyColumn(name = "contact_key")
    @Column(name = "contact_value", columnDefinition = "TEXT")
    private Map<String, String> contactInfo;

    @OneToMany(mappedBy = "travelDestination")
    private List<Favorite> favorites;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_region_id")
    private SubRegion subRegion;
}
