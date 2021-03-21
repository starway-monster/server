package monster.starway.server.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WayService {
    public String getFilteredWays(String from, String to, List<String> excludedZones) {
        String excluded = "";
        if (excludedZones != null && excludedZones.size() > 0) {
            excluded = ", excluded:";
            for (String excludedZone: excludedZones) {
                excluded += excludedZone + ",";
            }
            excluded = excluded.substring(0, excluded.length()-2);
        }
        return String.format("Hello from: " +  from + ", to:" + to + excluded);
    }
}
