package com.example.order.controller;

import com.example.order.pojo.MemberDto;
import com.example.order.pojo.MemberStatisticsDto;
import com.example.order.pojo.MemberDo;
import com.example.order.service.MemberService;
import com.example.order.pojo.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseDto<List<MemberDo>> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get member by ID")
    public ResponseDto<MemberDo> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @PostMapping
    @Operation(summary = "Add a new member")
    public ResponseDto<MemberDo> addMember(@RequestBody MemberDto memberDto) {
        return memberService.addMember(memberDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update member by ID")
    public ResponseDto<MemberDo> updateMember(@PathVariable Long id, @RequestBody MemberDto memberDto) {
        return memberService.updateMember(id, memberDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete member by ID")
    public ResponseDto<String> deleteMember(@PathVariable Long id) {
        return memberService.deleteMember(id);
    }

    @GetMapping("/statistics")
    @Operation(summary = "Get members with orders greater than N")
    public ResponseDto<List<MemberStatisticsDto>> getMembersWithOrdersGreaterThanN(@RequestParam int n) {
        return memberService.getMembersWithOrdersGreaterThanN(n);
    }
}

