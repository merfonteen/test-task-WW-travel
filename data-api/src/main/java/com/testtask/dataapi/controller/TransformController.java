package com.testtask.dataapi.controller;

import com.testtask.dataapi.dto.TransformRequest;
import com.testtask.dataapi.dto.TransformResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/transform")
@RestController
public class TransformController {

    @PostMapping
    public ResponseEntity<TransformResponse> transform(@RequestBody @Valid TransformRequest transformRequest) {
        String result = new StringBuilder(transformRequest.text()).reverse().toString();
        return ResponseEntity.ok(new TransformResponse(result));
    }
}
