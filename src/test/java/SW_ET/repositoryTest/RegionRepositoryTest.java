/*
package SW_ET.repositoryTest;

import SW_ET.entity.Region;
import SW_ET.repository.RegionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RegionRepositoryTest {

    @Autowired
    private RegionRepository regionRepository;

    @Test
    public void testFindByRegionName() {
        String expectedRegionName = "전라남도";
        Region region = regionRepository.findByRegionName(expectedRegionName.toLowerCase());
        assertNotNull(region, "Region should not be null");
        assertEquals(expectedRegionName.toLowerCase(), region.getRegionName().toLowerCase(), "Region name should match");
    }
}*/
