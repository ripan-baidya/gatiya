package com.gatiya.common.test;

import com.gatiya.common.dto.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
@Tag(name = "Test Controller", description = "Test Related API's")
public class TestController {

    /**
     * Sample rest api to test the api response.
     */
    @GetMapping
    @Operation(summary = "Get Test Response", description = "Get Test Response")
    @ApiResponse(responseCode = "200", description = "Test Response")
    public ResponseEntity<ResponseWrapper<String>> getTestResponse() {
        return new ResponseEntity<>(
                ResponseWrapper.success("Test Response"),
                HttpStatus.OK
        );
    }
}
