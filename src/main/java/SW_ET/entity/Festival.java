/*
package SW_ET.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Festival {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //자동 증가 설정
    @Column(name = "festival_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Long festivalId;  // 축제 ID

    @Column(name = "festival_name", nullable = false, length = 255)
    private String name;  // 축제이름

    @Column(name = "festival_description", columnDefinition = "TEXT")
    private String description;  // 축제설명

    @Column(name = "festival_place", nullable = false, length = 255)
    private String place;  // 축제주소
}
*/
