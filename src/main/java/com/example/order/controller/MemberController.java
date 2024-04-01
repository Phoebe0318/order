package com.example.order.controller;

import com.example.order.pojo.Member;
import com.example.order.service.MemberService;
import com.example.order.pojo.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    @Operation(summary = "Get all members")
    public ResponseDto<List<Member>> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get member by ID")
    public ResponseDto<Member> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @PostMapping
    @Operation(summary = "Add a new member")
    public ResponseDto<Member> addMember(@RequestBody Member member) {
        return memberService.addMember(member);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update member by ID")
    public ResponseDto<Member> updateMember(@PathVariable Long id, @RequestBody Member updatedMember) {
        return memberService.updateMember(id, updatedMember);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete member by ID")
    public ResponseDto<Void> deleteMember(@PathVariable Long id) {
        return memberService.deleteMember(id);
    }
}

