package monster.starway.server.controllers;

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
    public String getSearchResult(
            @RequestParam(name ="from") String from,
            @RequestParam(name = "to") String to,
            @RequestParam(name = "exclude", required = false) List<String> excludedZones
    ) {
        return wayService.getFilteredWays(from, to, excludedZones);
    }
}
