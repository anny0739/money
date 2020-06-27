package homework.controller;

import homework.dto.*;
import homework.service.SplitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/split-money", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SplitMoneyController {

    private final SplitService splitService;

    @PostMapping
    public TokenResponse splitMoney(@RequestHeader(value = "X-ROOM-ID") long roomId,
                                    @RequestHeader(value = "X-USER-ID") long userId,
                                    @Valid @RequestBody SplitMoneyRequest request) {
        return splitService.splitMoney(roomId, userId, request);
    }

    @PostMapping("/receive")
    public RecevieMoneyResponse receiveMoney(@RequestHeader(value = "X-ROOM-ID") long roomId,
                                             @RequestHeader(value = "X-USER-ID") long userId,
                                             @Valid @RequestBody ReceiveMoneyRequest request) {
        return splitService.receiveMoney(roomId, userId, request);
    }

    @GetMapping(consumes = "application/*")
    public SplitMoneyResponse getSplitMoney(@RequestHeader(value = "X-ROOM-ID") long roomId,
                                            @RequestHeader(value = "X-USER-ID") long userId,
                                            @RequestParam String token) {
        return splitService.getSplitMoney(roomId, userId, token);
    }
}
