package com.pathfinder.service;

import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.model.Stelle;
import com.pathfinder.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchingService {

    public double calculateMatchingScore(Nachwuchskraft nwk, Stelle stelle) {
        List<Tag> interessen = nwk.getInteressen();
        List<Tag> stellenTags = stelle.getTags();

        if (interessen.isEmpty() || stellenTags.isEmpty()) {
            return 0.0;
        }

        long matches = 0;
        for (Tag interesse : interessen) {
            if (stellenTags.contains(interesse)) {
                matches++;
            }
        }

        double score = (double) matches / stellenTags.size() * 100;
        return Math.round(score * 10.0) / 10.0;
    }
}
