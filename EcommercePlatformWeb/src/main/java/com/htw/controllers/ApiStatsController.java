package com.htw.controllers;

import com.htw.services.StatsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Trung Hau
 */
@Controller
@RequestMapping("/api")
public class ApiStatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/secure/stats-store")
    public ResponseEntity<List<Object[]>> getStatsStore() {
        return new ResponseEntity<>(this.statsService.statsRevenueStoreByUsername(), HttpStatus.OK);
    }
    
    
}
