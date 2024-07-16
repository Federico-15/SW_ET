package SW_ET.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RegionData {
    @JsonProperty("region")
    private String regionName;

    @JsonProperty("subregions")
    private List<String> subRegions;

    // getters and setters
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public List<String> getSubRegions() {
        return subRegions;
    }

    public void setSubRegions(List<String> subRegions) {
        this.subRegions = subRegions;
    }
}