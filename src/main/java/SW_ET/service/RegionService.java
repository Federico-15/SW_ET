package SW_ET.service;

import SW_ET.dto.*;
import SW_ET.entity.*;
import SW_ET.repository.RegionGroupRepository;
import SW_ET.repository.RegionRepository;
import SW_ET.repository.SubRegionRepository;
import SW_ET.repository.TravelDestinationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegionService {

    private final ObjectMapper objectMapper;
    private final RegionRepository regionRepository;
    private final SubRegionRepository subRegionRepository;
    private final RegionGroupRepository regionGroupRepository;
    private final TravelDestinationRepository travelDestinationRepository;

    private static final Logger logger = LoggerFactory.getLogger(RegionService.class);


    @Autowired
    public RegionService(ObjectMapper objectMapper, RegionRepository regionRepository, SubRegionRepository subRegionRepository, RegionGroupRepository regionGroupRepository, TravelDestinationRepository travelDestinationRepository) {
        this.objectMapper = objectMapper;
        this.regionRepository = regionRepository;
        this.subRegionRepository = subRegionRepository;
        this.regionGroupRepository = regionGroupRepository;
        this.travelDestinationRepository = travelDestinationRepository;
    }

    // regions.json을 db에 넣는 메소드
    @Transactional
    public void loadRegionsFromJson() throws IOException {
        InputStream is = new FileInputStream("C:\\Users\\sh980\\workspace\\regions.json"); // 경로는 적절히 수정
        ObjectMapper objectMapper = new ObjectMapper();
        List<RegionGroupData> regionGroups = objectMapper.readValue(is, new TypeReference<List<RegionGroupData>>() {});

        for (RegionGroupData groupData : regionGroups) {
            // RegionGroup 처리
            RegionGroup regionGroup = regionGroupRepository.findByRegionGroupName(groupData.getName())
                    .orElseGet(() -> {
                        RegionGroup newRegionGroup = new RegionGroup();
                        newRegionGroup.setRegionGroupName(groupData.getName());
                        return regionGroupRepository.save(newRegionGroup);
                    });

            for (RegionData data : groupData.getRegions()) {
                // Region 처리
                List<Region> existingRegions = regionRepository.findByRegionName(data.getRegionName());
                Region region;
                if (existingRegions.isEmpty()) {
                    region = new Region();
                    region.setRegionName(data.getRegionName());
                    region.setRegionGroup(regionGroup);
                    region = regionRepository.save(region);
                } else {
                    region = existingRegions.get(0);
                }

                // SubRegion 처리
                for (String subRegionName : data.getSubRegions()) {
                    List<SubRegion> existingSubRegions = subRegionRepository.findBySubRegionName(subRegionName);
                    if (existingSubRegions.isEmpty()) {
                        SubRegion newSubRegion = new SubRegion();
                        newSubRegion.setSubRegionName(subRegionName);
                        newSubRegion.setRegion(region);
                        subRegionRepository.save(newSubRegion);
                    }
                }
            }
        }
    }
    // 지역 그룹에 속하는 메인 지역만 반환 (ex 수도권 -> 경기도  .. 헤더에서 쓰일 메소드)
    public List<RegionDto> getRegionsByGroup(Long groupId) {
        logger.info("Fetching regions for group ID: {}", groupId);
        List<Region> regions = regionRepository.findByRegionGroupId(groupId);
        if (regions.isEmpty()) {
            logger.warn("No regions found for group ID: {}", groupId);
        }
        return regions.stream().map(region -> new RegionDto(region.getRegionId(), region.getRegionName(), null))
                .collect(Collectors.toList());
    }


    // 메인 지역 ID에 따라 하위 지역 조회 ( 서울시 -> 강남구 등등 .. 리뷰 작성시 쓰일 메소드)
    public List<SubRegionDto> getSubRegionsByRegion(Long RegionId) {
        List<SubRegion> subRegions = subRegionRepository.findByRegionId(RegionId);
        return subRegions.stream()
                .map(subRegion -> new SubRegionDto(subRegion.getSubRegionId(), subRegion.getSubRegionName()))
                .collect(Collectors.toList());
    }


    // 모든 지역 그룹 반환
    public List<RegionGroupDto> getAllRegionGroups() {
        List<RegionGroup> groups = regionGroupRepository.findAll();
        return groups.stream().map(group -> {
            RegionGroupDto dto = new RegionGroupDto();
            dto.setRegionGroupId(group.getRegionGroupId());
            dto.setRegionGroupName(group.getRegionGroupName());
            dto.setRegions(group.getRegions().stream().map(region -> {
                RegionDto regionDto = new RegionDto();
                regionDto.setRegionId(region.getRegionId());
                regionDto.setRegionName(region.getRegionName());
                // 메인 지역에 속하는 하위 지역만 표시
                regionDto.setSubRegions(region.getSubRegions().stream()
                        .map(subRegion -> new SubRegionDto(subRegion.getSubRegionId(), subRegion.getSubRegionName()))
                        .collect(Collectors.toList()));
                return regionDto;
            }).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
    }

    public Optional<RegionSubRegionNamesDto> getRegionAndSubRegionNames(Long regionId, Long subRegionId) {
        List<Object[]> result = regionRepository.findRegionAndSubRegionNames(regionId, subRegionId);
        return result.stream().map(objects -> new RegionSubRegionNamesDto((String) objects[0], (String) objects[1])).findFirst();
    }

    public List<TravelDestinationDto> getDestinationsByRegion(Long regionId) {
        Pageable pageable = Pageable.unpaged(); // 페이징 없이 모든 데이터를 한 번에 가져옴
        Page<TravelDestination> destinations = travelDestinationRepository.findByRegionId(regionId, pageable);
        return destinations.getContent().stream()
                .map(destination -> new TravelDestinationDto(destination))
                .collect(Collectors.toList());
    }
}