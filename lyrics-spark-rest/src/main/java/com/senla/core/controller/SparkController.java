package com.senla.core.controller;

import com.senla.core.service.SparkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scala.Tuple2;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spark")
public class SparkController {
//    private final SparkService sparkService;
//
//    public SparkController(SparkService sparkService) {
//        this.sparkService = sparkService;
//    }
//
//    @GetMapping("/get-count-records")
//    public ResponseEntity<Long> getCountRecords() {
//        return ResponseEntity.ok(sparkService.getCountRecords());
//    }
//
//    @GetMapping("/get-count-month")
//    public List<Tuple2<String, Long>> getCountByMonth() {
//        return sparkService.getCountByMonth();
//    }
//
//    @GetMapping("/get-records-by-month")
//    public List<String> getRecordsByMonth(@RequestParam("month") String month) {
//        return sparkService.getRecordsByMonth(month);
//    }
//
//    @GetMapping("/get-average-value-in-countries")
//    public List<Tuple2<String, Float>> getAverageValueInCountries() {
//        return sparkService.getAverageValueInCountries();
//    }
//
//    @GetMapping("/get-count-records-by-direction-eng")
//    public Long getCountRecordsByDirectionEng(@RequestParam("direction") String direction) {
//        return sparkService.getCountRecordsByDirectionEng(direction);
//    }
//
//    @GetMapping("/get-stats-direction-eng")
//    public Map<String, Long> getStatsDirectionEng() {
//        return sparkService.getCountRecordsGroupByDirectionEng();
//    }
//
//    @GetMapping("/get-average-value-by-district")
//    public List<Tuple2<String, Float>> getAverageValueByDistrict() {
//        return sparkService.getAverageValueByDistrict();
//    }
//
//    @GetMapping("/get-anomalies-records")
//    public List<String> getAnomaliesRecords() {
//        return sparkService.getAnomaliesRecords();
//    }
//
//    @GetMapping("/get-pics-month-by-value")
//    public Tuple2<String, Float> getPicsMonthByValue() {
//        return sparkService.getPicsMonthByValue();
//    }
//
//    @GetMapping("/get-trends-by-value")
//    public List<Tuple2<String, Float>> getTrendsByValue() {
//        return sparkService.getTrendsByValue();
//    }
}
