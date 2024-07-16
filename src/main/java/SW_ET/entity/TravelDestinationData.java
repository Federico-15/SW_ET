package SW_ET.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class TravelDestinationData {

    private String title;

    @JsonProperty("상세지역")
    private String subRegionName; // JSON의 "상세지역" 필드에 해당
    
    @JsonProperty("지역")
    private String regionName;   // JSON의 "지역" 필드에 해당

    @JsonProperty("one_line_desc")
    private String oneLineDesc;

    @JsonProperty("image_urls")
    private List<String> imageUrls;

    @JsonProperty("detailed_info")
    private String detailedInfo;

    @JsonProperty("contact_info")
    private Map<String, String> contactInfo;

    // 기본 생성자
    public TravelDestinationData() {
    }

    // 모든 필드를 포함하는 생성자
    public TravelDestinationData(String title, String subRegionName, String regionName, String oneLineDesc, List<String> imageUrls, String detailedInfo, Map<String, String> contactInfo) {
        this.title = title;
        this.subRegionName = subRegionName;
        this.regionName = regionName;
        this.oneLineDesc = oneLineDesc;
        this.imageUrls = imageUrls;
        this.detailedInfo = detailedInfo;
        this.contactInfo = contactInfo;
    }

    // Getter와 Setter 메서드
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubRegionName() {
        return subRegionName;
    }

    public void setSubRegionName(String subRegionName) {
        this.subRegionName = subRegionName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getOneLineDesc() {
        return oneLineDesc;
    }

    public void setOneLineDesc(String oneLineDesc) {
        this.oneLineDesc = oneLineDesc;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getDetailedInfo() {
        return detailedInfo;
    }

    public void setDetailedInfo(String detailedInfo) {
        this.detailedInfo = detailedInfo;
    }

    public Map<String, String> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(Map<String, String> contactInfo) {
        this.contactInfo = contactInfo;
    }
}
