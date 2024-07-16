package SW_ET.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sub_region")
public class SubRegion { // 은펴구 , 강남구 등 세부지역

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_region_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Long subRegionId;

    @Column(name = "sub_region_name", nullable = false, length = 255)
    private String subRegionName; // 강남구, 은평구 등

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id") // Reference to the parent region
    private Region region;
}