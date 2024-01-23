package com.minlink.minlink.api;

import com.minlink.minlink.helper.ResponseGenerationHelper;
import com.minlink.minlink.model.Url;
import com.minlink.minlink.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static com.minlink.minlink.constants.Constants.APPLICATION_JSON;
import static com.minlink.minlink.constants.Constants.APPLICATION_RAN_INTO_ERROR_WHILE_PROCESSING_REQUEST;
import static com.minlink.minlink.constants.Constants.ERROR_RAN_INTO_AN_ISSUE;
import static com.minlink.minlink.constants.Constants.LOCATION;
import static com.minlink.minlink.constants.Constants.REDIRECTS_TO_ORIGINAL_URL;
import static com.minlink.minlink.constants.Constants.REDIRECT_A_SHORT_URL;
import static com.minlink.minlink.constants.Constants.REDIRECT_ENDPOINT;
import static com.minlink.minlink.constants.Constants.STATUS_307;
import static com.minlink.minlink.constants.Constants.STATUS_404;
import static com.minlink.minlink.constants.Constants.STATUS_500;
import static com.minlink.minlink.constants.Constants.WHEN_SHORT_URL_IS_NOT_FOUND_OR_IS_DEACTIVATED;

@RestController
public class RedirectController {

    @Autowired
    UrlService urlService;

    private static final ExecutorService executorService = new ScheduledThreadPoolExecutor(10);

    @Operation(summary = REDIRECT_A_SHORT_URL)
    @ApiResponses({
            @ApiResponse(responseCode = STATUS_307, description = REDIRECTS_TO_ORIGINAL_URL, content = {@Content}),
            @ApiResponse(responseCode = STATUS_404, description = WHEN_SHORT_URL_IS_NOT_FOUND_OR_IS_DEACTIVATED, content = {@Content}),
            @ApiResponse(responseCode = STATUS_500, description = APPLICATION_RAN_INTO_ERROR_WHILE_PROCESSING_REQUEST,
                    content = {@Content(examples = @ExampleObject(value = ERROR_RAN_INTO_AN_ISSUE), mediaType = APPLICATION_JSON)})
    })
    @GetMapping(REDIRECT_ENDPOINT)
    public ResponseEntity<Map<String, Object>> redirectShortUrl(@PathVariable String shortUrl) {
        try {
            Url url = urlService.findByShortUrl(shortUrl);
            if (url == null || url.isDeactivated()) {
                return ResponseEntity.notFound().build();
            }
            executorService.submit(() -> handleRedirect(shortUrl));
            return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                    .header(LOCATION, url.getOriginalUrl()).build();
        } catch (Exception e) {
            return ResponseGenerationHelper.exceptionResponse();
        }
    }

    private void handleRedirect(String shortUrl) {
        urlService.increaseHitCount(shortUrl);
    }

}
