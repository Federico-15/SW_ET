/*
package SW_ET.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class DestinationRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 지동 증가
    @Column(name = "dr_id", nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long id; // 중간 테이블의 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false, columnDefinition = "INT UNSIGNED") // 여행지 ID를 FK로
    private Destination destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false, columnDefinition = "INT UNSIGNED") // 지역 ID를 FK로
    private Region region;

}
*/
