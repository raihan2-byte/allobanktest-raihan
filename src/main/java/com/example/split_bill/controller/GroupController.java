package com.example.split_bill.controller;

import com.example.split_bill.dto.request.CreateGroupRequest;
import com.example.split_bill.dto.response.GroupResponse;
import com.example.split_bill.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponse createGroup(@RequestBody CreateGroupRequest request) {
        return groupService.createGroup(request);
    }

    @GetMapping("/{groupId}")
    public GroupResponse getGroup(@PathVariable Long groupId) {
        return groupService.getGroup(groupId);
    }

    @GetMapping
    public List<GroupResponse> getAllGroups() {
        return groupService.getAllGroups();
    }
}
