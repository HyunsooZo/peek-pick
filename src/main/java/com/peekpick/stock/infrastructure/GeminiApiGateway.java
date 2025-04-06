package com.peekpick.stock.infrastructure;

import jakarta.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component("gemini")
public class GeminiApiGateway implements StockAnalysisGateway {
    private final RestClient restClient;
    private final GeminiApiProperties properties;
    private static final Logger log = LoggerFactory.getLogger(GeminiApiGateway.class);

    public GeminiApiGateway(
            RestClient restClient,
            GeminiApiProperties properties
    ) {
        this.restClient = restClient;
        this.properties = properties;
    }

    @Override
    public StockAnalysisResponse analyze(StockAnalysisRequest request) {
        var responseEntity = restClient.post()
                .uri(properties.getUrl() + properties.getKey())
                .headers(httpHeaders -> httpHeaders.add(
                        HttpHeaders.CONTENT_TYPE,
                        APPLICATION_JSON_VALUE
                ))
                .body(request.toJson())
                .retrieve() // TODO : Handle errors
                .toEntity(GeminiResponse.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            return new GeminiResponse(List.of());
        }
        return responseEntity.getBody();
    }

    public record GeminiRequest(List<Content> contents) implements StockAnalysisRequest {
        @Override
        public String toJson() {
            var contentsArray = Json.createArrayBuilder();
            var partsArray = Json.createArrayBuilder();
            for (Content content : contents) {
                for (Part part : content.parts()) {
                    var partJson = Json.createObjectBuilder().add("text", part.text()).build();
                    partsArray.add(partJson);
                }
                var contentJson = Json.createObjectBuilder().add("parts", partsArray).build();
                contentsArray.add(contentJson);
            }
            return Json.createObjectBuilder().add("contents", contentsArray).build().toString();
        }

        public record Content(List<Part> parts) {}

        public record Part(String text) {}
    }

    public record GeminiResponse(List<Candidate> candidates) implements StockAnalysisResponse {
        @Override
        public Map<String, String> indexAnalysis() {
            var list = candidates.stream()
                    .map(Candidate::content)
                    .flatMap(content -> content.parts().stream())
                    .map(Part::text)
                    .toList();
            return list.stream()
                    .collect(Collectors.toMap(
                            part -> part.split("\n")[0],
                            part -> part
                    ));
        }

        @Override
        public String indexName() {
            return candidates.getFirst().content().parts().getFirst().text().split("\n")[0];
        }

        public record Candidate(Content content) {}

        public record Content(List<Part> parts) {}

        public record Part(String text) {}


    }
}
