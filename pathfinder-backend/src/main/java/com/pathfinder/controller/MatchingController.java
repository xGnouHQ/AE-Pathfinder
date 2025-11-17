package com.pathfinder.controller;

import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.model.Stelle;
import com.pathfinder.service.MatchingService;
import com.pathfinder.service.NachwuchskraftService;
import com.pathfinder.service.StelleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/matching")
public class MatchingController {

    private final MatchingService matchingService;
    private final NachwuchskraftService nwkService;
    private final StelleService stelleService;

    public MatchingController(MatchingService matchingService, NachwuchskraftService nwkService, StelleService stelleService) {
        this.matchingService = matchingService;
        this.nwkService = nwkService;
        this.stelleService = stelleService;
    }

    @GetMapping("/{nwkId}/{stelleId}")
    public ResponseEntity<Double> getMatchingScore(@PathVariable Long nwkId, @PathVariable Long stelleId) {
        Nachwuchskraft nwk = nwkService.getById(nwkId);
        Stelle stelle = stelleService.getById(stelleId);
        if (nwk == null || stelle == null) {
            return ResponseEntity.notFound().build();
        }
        double score = matchingService.calculateMatchingScore(nwk, stelle);
        return ResponseEntity.ok(score);
    }
}
