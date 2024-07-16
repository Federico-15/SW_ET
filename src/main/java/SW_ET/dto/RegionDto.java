package SW_ET.dto;

import SW_ET.entity.Region;
import SW_ET.entity.User;
import SW_ET.entity.types.UserRole;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor  // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 매개변수를 받는 생성자 자동 생성
public class RegionDto {
    private Long regionId;
    private String regionName;
    private List<SubRegionDto> subRegions;
}
