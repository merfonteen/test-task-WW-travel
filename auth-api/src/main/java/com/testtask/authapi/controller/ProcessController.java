package com.testtask.authapi.controller;

import com.testtask.authapi.dto.ProcessRequest;
import com.testtask.authapi.dto.ProcessResponse;
import com.testtask.authapi.security.PrincipalUser;
import com.testtask.authapi.service.ProcessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/process")
@RestController
public class ProcessController {
    private final ProcessService processService;

    @PostMapping
    public ResponseEntity<ProcessResponse> process(@AuthenticationPrincipal PrincipalUser principal,
                                                   @RequestBody @Valid ProcessRequest request) {
        return ResponseEntity.ok(processService.process(principal, request));
    }
}
