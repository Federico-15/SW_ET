/*
package SW_ET.service;

import SW_ET.dto.TravelDestinationDto;
import SW_ET.entity.Region;
import SW_ET.entity.SubRegion;
import SW_ET.entity.TravelDestination;
import SW_ET.entity.TravelDestinationData;
import SW_ET.repository.RegionRepository;
import SW_ET.repository.SubRegionRepository;
import SW_ET.repository.TravelDestinationRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class TravelDestinationService {

    @Autowired
    private TravelDestinationRepository travelDestinationRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SubRegionRepository subRegionRepository;

    private final ObjectMapper objectMapper;


    @Autowired
    public TravelDestinationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void loadTravelDestinationsFromJson() throws IOException {
        File file = new File("C:\\Users\\sh980\\workspace\\dataSudo.json");
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<TravelDestinationData> destinationDataList = objectMapper.readValue(file, new TypeReference<>() {});

        for (TravelDestinationData data : destinationDataList) {
            TravelDestination destination = convertToEntity(data);
            addOrUpdateTravelDestination(destination); // 이 메소드를 호출하여 DB에 저장 또는 업데이트
        }
    }

    @Transactional
    public void addOrUpdateTravelDestination(TravelDestination newDestination) {
        Optional<TravelDestination> existingDestination = travelDestinationRepository.findByTitleAndSubRegion(
                newDestination.getTitle(), newDestination.getSubRegion());

        if (existingDestination.isPresent()) {
            TravelDestination destination = existingDestination.get();
            // 기존 데이터와 동일한지 검사 후, 필요한 경우만 업데이트
            if (!isSameDestination(destination, newDestination)) {
                updateDestination(destination, newDestination);
                travelDestinationRepository.save(destination);
            }
        } else {
            travelDestinationRepository.save(newDestination);
        }
    }

    private boolean isSameDestination(TravelDestination existing, TravelDestination newData) {
        return existing.getSubRegionName().equals(newData.getSubRegionName())
                && existing.getRegionName().equals(newData.getRegionName())
                && existing.getOneLineDesc().equals(newData.getOneLineDesc())
                && existing.getImageUrls().equals(newData.getImageUrls())
                && existing.getDetailedInfo().equals(newData.getDetailedInfo())
                && existing.getContactInfo().equals(newData.getContactInfo());
    }

    private void updateDestination(TravelDestination existing, TravelDestination newData) {
        existing.setSubRegionName(newData.getSubRegionName());
        existing.setRegionName(newData.getRegionName());
        existing.setOneLineDesc(newData.getOneLineDesc());
        existing.setImageUrls(newData.getImageUrls());
        existing.setDetailedInfo(newData.getDetailedInfo());
        existing.setContactInfo(newData.getContactInfo());
        existing.setRegion(newData.getRegion());
    }

    private TravelDestination convertToEntity(TravelDestinationData data) { // 변환해주는 메소드
        if (data.getRegionName() == null) {
            throw new IllegalArgumentException("Region name cannot be null for destination: " + data.getTitle());
        }

        TravelDestination destination = new TravelDestination();
        destination.setTitle(data.getTitle());
        destination.setSubRegionName(data.getSubRegionName());
        destination.setRegionName(data.getRegionName());
        destination.setOneLineDesc(data.getOneLineDesc() != null ? data.getOneLineDesc() : "N/A");
        destination.setImageUrls(data.getImageUrls() != null ? data.getImageUrls() : new ArrayList<>());
        destination.setDetailedInfo(data.getDetailedInfo() != null ? data.getDetailedInfo() : "상세 정보가 없습니다.");
        destination.setContactInfo(data.getContactInfo() != null ? data.getContactInfo() : new HashMap<>());

        // Region 객체 찾기
        List<Region> regions = regionRepository.findByRegionName(data.getRegionName());
        if (regions.size() != 1) {
            throw new IllegalStateException("Expected one region, but found " + regions.size() + " for name: " + data.getRegionName());
        }
        Region region = regions.get(0);

        // SubRegion 객체 찾기
        List<SubRegion> subRegions = subRegionRepository.findBySubRegionName(data.getSubRegionName());
        SubRegion subRegion = subRegions.isEmpty() ? null : subRegions.get(0);

        // 찾은 Region과 SubRegion을 설정
        destination.setRegion(region);
        destination.setSubRegion(subRegion);

        return destination;
    }
}*/
package SW_ET.service;

import SW_ET.entity.Region;
import SW_ET.entity.SubRegion;
import SW_ET.entity.TravelDestination;
import SW_ET.entity.TravelDestinationData;
import SW_ET.repository.RegionRepository;
import SW_ET.repository.SubRegionRepository;
import SW_ET.repository.TravelDestinationRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class TravelDestinationService {

    @Autowired
    private TravelDestinationRepository travelDestinationRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SubRegionRepository subRegionRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public TravelDestinationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Async
    @Transactional
    public void loadTravelDestinationsFromJson() throws IOException {
        File file = new File("C:\\Users\\sh980\\workspace\\dataSudo0.json"); // 0 이 기본
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<TravelDestinationData> destinationDataList = objectMapper.readValue(file, new TypeReference<>() {});

        List<TravelDestination> destinations = new ArrayList<>();
        for (TravelDestinationData data : destinationDataList) {
            TravelDestination destination = convertToEntity(data);
            destinations.add(destination);
            if (destinations.size() >= 50) { // 50개 단위로 배치 저장
                saveDestinations(destinations);
                destinations.clear(); // 리스트 초기화
            }
        }
        if (!destinations.isEmpty()) {
            saveDestinations(destinations); // 남은 데이터 저장
        }
    }

    @Transactional
    public void saveDestinations(List<TravelDestination> destinations) {
        List<TravelDestination> toSave = new ArrayList<>();
        for (TravelDestination destination : destinations) {
            Optional<TravelDestination> existingDestination = travelDestinationRepository.findByTitleAndSubRegion(
                    destination.getTitle(), destination.getSubRegion());
            if (existingDestination.isPresent()) {
                TravelDestination existing = existingDestination.get();
                if (!isSameDestination(existing, destination)) {
                    updateDestination(existing, destination);
                    toSave.add(existing);
                }
            } else {
                toSave.add(destination);
            }
        }
        if (!toSave.isEmpty()) {
            travelDestinationRepository.saveAll(toSave);
        }
    }

    private boolean isSameDestination(TravelDestination existing, TravelDestination newData) {
        return existing.getSubRegionName().equals(newData.getSubRegionName())
                && existing.getRegionName().equals(newData.getRegionName())
                && existing.getOneLineDesc().equals(newData.getOneLineDesc())
                && existing.getImageUrls().equals(newData.getImageUrls())
                && existing.getDetailedInfo().equals(newData.getDetailedInfo())
                && existing.getContactInfo().equals(newData.getContactInfo());
    }

    private void updateDestination(TravelDestination existing, TravelDestination newData) {
        existing.setSubRegionName(newData.getSubRegionName());
        existing.setRegionName(newData.getRegionName());
        existing.setOneLineDesc(newData.getOneLineDesc());
        existing.setImageUrls(newData.getImageUrls());
        existing.setDetailedInfo(newData.getDetailedInfo());
        existing.setContactInfo(newData.getContactInfo());
        existing.setRegion(newData.getRegion());
    }

    private TravelDestination convertToEntity(TravelDestinationData data) {
        if (data.getRegionName() == null) {
            throw new IllegalArgumentException("Region name cannot be null for destination: " + data.getTitle());
        }

        TravelDestination destination = new TravelDestination();
        destination.setTitle(data.getTitle());
        destination.setSubRegionName(data.getSubRegionName());
        destination.setRegionName(data.getRegionName());
        destination.setOneLineDesc(data.getOneLineDesc() != null ? data.getOneLineDesc() : "N/A");
        destination.setImageUrls(data.getImageUrls() != null ? data.getImageUrls() : new ArrayList<>());
        destination.setDetailedInfo(data.getDetailedInfo() != null ? data.getDetailedInfo() : "상세 정보가 없습니다.");
        destination.setContactInfo(data.getContactInfo() != null ? data.getContactInfo() : new HashMap<>());

        // Region 객체 찾기
        List<Region> regions = regionRepository.findByRegionName(data.getRegionName());
        if (regions.size() != 1) {
            throw new IllegalStateException("Expected one region, but found " + regions.size() + " for name: " + data.getRegionName());
        }
        Region region = regions.get(0);

        // SubRegion 객체 찾기
        List<SubRegion> subRegions = subRegionRepository.findBySubRegionName(data.getSubRegionName());
        SubRegion subRegion = subRegions.isEmpty() ? null : subRegions.get(0);

        // 찾은 Region과 SubRegion을 설정
        destination.setRegion(region);
        destination.setSubRegion(subRegion);

        return destination;
    }
}
