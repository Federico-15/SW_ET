package SW_ET.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Region")
public class Region { // 서울시, 경기도 등

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Long regionId;

    @Column(name = "region_name", nullable = false, length = 255)
    private String regionName; // 서울시, 경기도 등

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_group_id")
    private RegionGroup regionGroup; // 상위 지역 그룹 참조

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubRegion> subRegions = new HashSet<>(); // 하위 지역 목록
}