package SW_ET.config;

import SW_ET.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class RegionDataLoader implements CommandLineRunner {

    @Autowired
    private RegionService regionService;

    public RegionDataLoader(RegionService regionService) {
        this.regionService = regionService;
    }

    @Override
    public void run(String... args) throws Exception {
        regionService.loadRegionsFromJson();
    }
}