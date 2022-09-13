package com.demo.NS3.controller;

import com.demo.NS3.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiController {
    private final ApiService service;

    @PostMapping("/test")
    public ResponseEntity<?> test(@NotNull MultipartHttpServletRequest request) throws Exception {
        return service.saveData(request);
    }

    @GetMapping(value = "/img/{deviceId}/{cameraname}/{date}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable(value = "deviceId") String deviceId, @PathVariable(value = "cameraname") String cameraname,
                                  @PathVariable(value = "date")String date) throws IOException {
        return service.getImage(deviceId,cameraname,date);
    }
}
