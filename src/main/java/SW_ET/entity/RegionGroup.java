package SW_ET.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "RegionGroup")
public class RegionGroup { // 수도권, 강원권 등
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regionGroupId;

    @Column(nullable = false)
    private String regionGroupName; // 예: "수도권", "영남권"

    @OneToMany(mappedBy = "regionGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Region> regions; // 지역 그룹에 속한 지역 리스트
}