package monster.starway.server.controllers;

import monster.starway.server.data.dto.PathDTO;
import monster.starway.server.data.dto.SearchDTO;
import monster.starway.server.exceptions.ValidationException;
import monster.starway.server.services.WayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/way")
public class WayController {
    @Autowired
    private WayService wayService;

    @CrossOrigin
    @GetMapping(path="/search")
    public SearchDTO getSearchResult(
            @RequestParam(name ="from") String from,
            @RequestParam(name = "to") String to,
            @RequestParam(name = "exclude", required = false) List<String> excludedZones
    ) {
        return wayService.getFilteredWays(from, to, excludedZones);
    }

    @CrossOrigin
    @GetMapping(path="/un-escrow")
    public PathDTO getUnescrowPath(
            @RequestParam(name ="zonecurrent", required = false) String zoneCurrent,
            @RequestParam(name = "zonesource", required = false) String zoneSource,
            @RequestParam(name = "trace") String trace
    ) {
        if (zoneCurrent == null && zoneSource == null ||
                (zoneCurrent != null && zoneCurrent.equalsIgnoreCase(zoneSource)) ||
                (zoneSource != null && zoneSource.equalsIgnoreCase(zoneCurrent)))
            throw new ValidationException("Wrong zone arguments");
        if (!trace.contains("transfer/"))
            throw new ValidationException("Wrong trace argument. Only ibc-transfer module supported");

        return wayService.getUnescrowPath(zoneCurrent, zoneSource, trace);
    }
}
