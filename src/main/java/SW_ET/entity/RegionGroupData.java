package SW_ET.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RegionGroupData {
    @JsonProperty("regionGroup")
    private String name;

    @JsonProperty("regions")
    private List<RegionData> regions;

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RegionData> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionData> regions) {
        this.regions = regions;
    }
}