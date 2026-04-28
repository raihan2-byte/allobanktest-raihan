package com.example.split_bill.service;

import com.example.split_bill.dto.request.CreateGroupRequest;
import com.example.split_bill.dto.response.GroupResponse;
import com.example.split_bill.entity.BillGroup;
import com.example.split_bill.entity.Participant;
import com.example.split_bill.exception.NotFoundException;
import com.example.split_bill.repository.GroupRepository;
import com.example.split_bill.repository.ParticipantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final ParticipantRepository participantRepository;

    @Transactional
    public GroupResponse createGroup(CreateGroupRequest request) {

        BillGroup group = new BillGroup();
        group.setName(request.getName());

        List<Participant> participants = request.getParticipants()
                .stream()
                .map(name -> {
                    Participant p = new Participant();
                    p.setName(name);
                    p.setGroup(group);
                    return p;
                })
                .toList();

        group.setParticipants(participants);

        groupRepository.save(group);

        return mapToResponse(group);
    }

    @Transactional
    public GroupResponse getGroup(Long groupId) {

        BillGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));

        return mapToResponse(group);
    }

    @Transactional
    public List<GroupResponse> getAllGroups() {

        List<BillGroup> groups = groupRepository.findAll();

        return groups.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public BillGroup getGroupEntity(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));
    }

    private GroupResponse mapToResponse(BillGroup group) {

        List<String> participants = group.getParticipants()
                .stream()
                .map(Participant::getName)
                .toList();

        GroupResponse response = new GroupResponse();
        response.setId(group.getId());
        response.setName(group.getName());
        response.setParticipants(participants);

        return response;
    }
}