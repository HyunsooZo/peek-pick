package com.peekpick.stock.presentation;

import com.peekpick.stock.application.StockQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/stocks")
public class StockController {
    private final StockQueryService stockQueryService;

    public StockController(StockQueryService stockQueryService) {
        this.stockQueryService = stockQueryService;
    }

    @GetMapping
    public ResponseEntity<StockPayload.StockResponse> fetchAll() {
        final var stocks = stockQueryService.fetchAll();
        return ResponseEntity.ok(StockMapper.mapToResponse(stocks));
    }
}
