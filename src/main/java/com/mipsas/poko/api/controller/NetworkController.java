package com.mipsas.poko.api.controller;

import static com.mipsas.poko.api.Paths.NETWORK_INFO;
import com.mipsas.poko.api.model.request.NetworkInfoRequest;
import com.mipsas.poko.api.service.NetworkService;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(NETWORK_INFO)
@RequiredArgsConstructor
public class NetworkController {
    private final NetworkService networkService;

    @Operation(summary = "Set user network connection info")
    @PostMapping
    public void setNetworkInfo(@RequestBody @Valid NetworkInfoRequest request) {
        networkService.saveNetworkInfo(request);
    }
}