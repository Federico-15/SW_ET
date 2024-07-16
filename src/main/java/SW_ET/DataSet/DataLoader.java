package SW_ET.DataSet;

import SW_ET.service.RegionService;
import SW_ET.service.TravelDestinationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RegionService regionService;
    private final TravelDestinationService travelDestinationService;


    public DataLoader(RegionService regionService, TravelDestinationService travelDestinationService) {
        this.regionService = regionService;
        this.travelDestinationService = travelDestinationService;
    }

    @Override
    public void run(String... args) throws Exception {
        regionService.loadRegionsFromJson();
        travelDestinationService.loadTravelDestinationsFromJson();
    }
}