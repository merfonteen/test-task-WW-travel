package com.testtask.authapi.service;

import com.testtask.authapi.client.DataApiClient;
import com.testtask.authapi.dto.ProcessRequest;
import com.testtask.authapi.dto.ProcessResponse;
import com.testtask.authapi.entity.ProcessingLogEntity;
import com.testtask.authapi.repository.ProcessingLogRepository;
import com.testtask.authapi.security.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class ProcessService {
    private final DataApiClient dataApiClient;
    private final ProcessingLogRepository processingLogRepository;

    public ProcessResponse process(PrincipalUser principal, ProcessRequest request) {
        ProcessResponse result = dataApiClient.callDataApiProcess(request);

        ProcessingLogEntity processingLog = ProcessingLogEntity.builder()
                .userId(principal.userId())
                .inputText(request.text())
                .outputText(result.result())
                .createdAt(Instant.now())
                .build();

        processingLogRepository.save(processingLog);
        return result;
    }
}
