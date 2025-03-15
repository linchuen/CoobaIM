package com.cooba.controller;

import com.cooba.component.OfficialChannelComponent;
import com.cooba.dto.request.ChannelCreateRequest;
import com.cooba.dto.request.ChannelDeleteRequest;
import com.cooba.dto.request.ChannelUpdateRequest;
import com.cooba.dto.response.ChannelCreateResponse;
import com.cooba.dto.response.ChannelDeleteResponse;
import com.cooba.dto.response.ChannelSearchResponse;
import com.cooba.dto.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/channel")
@RequiredArgsConstructor
@Tag(name = "/channel", description = "頻道管理")
public class OfficialChannelController {
    private final OfficialChannelComponent officialChannelComponent;

    @PostMapping("/create")
    @Operation(summary = "頻道建立")
    public ResultResponse<?> createChannel(@Valid @RequestBody ChannelCreateRequest request) {
        ChannelCreateResponse response = officialChannelComponent.create(request);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/update")
    @Operation(summary = "頻道建立")
    public ResultResponse<?> updateChannel(@Valid @RequestBody ChannelUpdateRequest request) {
        officialChannelComponent.update(request);
        return ResultResponse.builder().data(true).build();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "頻道刪除")
    public ResultResponse<?> deleteChannel(@Valid @RequestBody ChannelDeleteRequest request) {
        ChannelDeleteResponse response = officialChannelComponent.delete(request);
        return ResultResponse.builder().data(response).build();
    }

    @GetMapping("/search")
    @Operation(summary = "搜尋頻道")
    public ResultResponse<?> searchChannel() {
        ChannelSearchResponse response = officialChannelComponent.search();
        return ResultResponse.builder().data(response).build();
    }
}
