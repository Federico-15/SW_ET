package SW_ET.controller;

import SW_ET.dto.*;
import SW_ET.repository.TravelDestinationRepository;
import SW_ET.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }


    // 모든 지역 그룹 정보를 가져오는 API
    @GetMapping("/region-groups")
    public ResponseEntity<List<RegionGroupDto>> getAllRegionGroups() {
        List<RegionGroupDto> regionGroups = regionService.getAllRegionGroups();
        return ResponseEntity.ok(regionGroups);
    }

    @GetMapping("/regions/{groupId}")
    public ResponseEntity<List<RegionDto>> getRegionsByGroup(@PathVariable Long groupId) {
        List<RegionDto> regions = regionService.getRegionsByGroup(groupId);
        return ResponseEntity.ok(regions);
    }

    @GetMapping("/sub-regions/{regionId}")
    public ResponseEntity<List<SubRegionDto>> getSubRegionsByRegion(@PathVariable Long regionId) {
        List<SubRegionDto> subRegions = regionService.getSubRegionsByRegion(regionId);
        return ResponseEntity.ok(subRegions);
    }

    @GetMapping("/header")
    public String showHeader(Model model) {
        List<RegionGroupDto> regionGroups = regionService.getAllRegionGroups();
        model.addAttribute("regionGroups", regionGroups);
        return "fragement/header";  // 'header'는 해당 템플릿의 이름입니다.
    }

    @GetMapping("/{regionId}/destinations")
    public ResponseEntity<List<TravelDestinationDto>> getDestinationsByRegion(@PathVariable Long regionId) {
        List<TravelDestinationDto> destinations = regionService.getDestinationsByRegion(regionId);
        return ResponseEntity.ok(destinations);
    }
}