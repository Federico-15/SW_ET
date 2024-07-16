package SW_ET.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor  // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 매개변수를 받는 생성자 자동 생성
public class RegionGroupDto {
    private Long regionGroupId;
    private String regionGroupName;
    private List<RegionDto> regions;
}