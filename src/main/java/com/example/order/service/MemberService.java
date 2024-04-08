package com.example.order.service;

import com.example.order.dao.MemberDao;
import com.example.order.pojo.MemberDto;
import com.example.order.pojo.MemberStatisticsDto;
import com.example.order.dao.OrderDao;
import com.example.order.pojo.MemberDo;
import com.example.order.pojo.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberDao memberDao;
    private final OrderDao orderDao;

    public ResponseDto<MemberDo> addMember(MemberDto memberDto) {
        if (memberDao.existsByEmail(memberDto.getEmail()) > 0) {
            log.info("Email {} already exists", memberDto.getEmail());

            ResponseDto<MemberDo> responseDto = new ResponseDto<>();
            responseDto.setStatus(0);
            responseDto.setMessage("Email already exists");
            return responseDto;
        }

        MemberDo memberDo = mapToMemberDo(memberDto);
        MemberDo savedMember = memberDao.save(memberDo);
        log.info("Member added successfully with ID: {}", savedMember.getId());

        ResponseDto<MemberDo> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        responseDto.setMessage("Member added successfully");
        responseDto.setData(savedMember);
        return responseDto;
    }

    private MemberDo mapToMemberDo(MemberDto memberDto) {
        MemberDo memberDo = new MemberDo();
        memberDo.setName(memberDto.getName());
        memberDo.setEmail(memberDto.getEmail());
        memberDo.setPassword(memberDto.getPassword());
        return memberDo;
    }

    public ResponseDto<String> deleteMember(Long id) {
        if (memberDao.existsById(id)) {
            memberDao.deleteById(id);
            log.info("Member deleted successfully with ID: {}", id);

            ResponseDto<String> responseDto = new ResponseDto<>();
            responseDto.setStatus(1);
            responseDto.setMessage("Member deleted successfully");
            return responseDto;
        } else {
            log.info("Member not found with ID: {}", id);

            ResponseDto<String> responseDto = new ResponseDto<>();
            responseDto.setStatus(0);
            responseDto.setMessage("Member not found");
            return responseDto;
        }
    }

    public ResponseDto<MemberDo> updateMember(Long id, MemberDto memberDto) {
        if (memberDao.existsById(id)) {
            MemberDo memberDo = mapToMemberDo(memberDto);
            memberDo.setId(id);
            MemberDo updatedMember = memberDao.save(memberDo);
            log.info("Member updated successfully with ID: {}", id);

            ResponseDto<MemberDo> responseDto = new ResponseDto<>();
            responseDto.setStatus(1);
            responseDto.setMessage("Member updated successfully");
            responseDto.setData(updatedMember);
            return responseDto;
        } else {
            log.info("Member not found with ID: {}", id);

            ResponseDto<MemberDo> responseDto = new ResponseDto<>();
            responseDto.setStatus(0);
            responseDto.setMessage("Member not found");
            return responseDto;
        }
    }

    public ResponseDto<MemberDo> getMemberById(Long id) {
        MemberDo memberDo = memberDao.findById(id).orElse(null);
        if (memberDo != null) {
            log.info("Member found with ID: {}", id);

            ResponseDto<MemberDo> responseDto = new ResponseDto<>();
            responseDto.setStatus(1);
            responseDto.setMessage("Member found");
            responseDto.setData(memberDo);
            return responseDto;
        } else {
            log.info("Member not found with ID: {}", id);

            ResponseDto<MemberDo> responseDto = new ResponseDto<>();
            responseDto.setStatus(0);
            responseDto.setMessage("Member not found");
            return responseDto;
        }
    }

    public ResponseDto<List<MemberDo>> getAllMembers() {
        List<MemberDo> members = memberDao.findAll();
        ResponseDto<List<MemberDo>> responseDto = new ResponseDto<>();
        if (!members.isEmpty()) {
            log.info("Members found");

            responseDto.setStatus(1);
            responseDto.setMessage("Members found");
            responseDto.setData(members);
        } else {
            responseDto.setStatus(0);
            responseDto.setMessage("No members found");
        }
        return responseDto;
    }

    public ResponseDto<List<MemberStatisticsDto>> getMembersWithOrdersGreaterThanN(int n) {
        List<MemberStatisticsDto> memberStatisticsList = new ArrayList<>();
        List<MemberDo> members = memberDao.findAll();
        for (MemberDo member : members) {
            int orderCount = orderDao.countByMemberId(member.getId());
            if (orderCount > n) {
                MemberStatisticsDto memberStatisticsDto = new MemberStatisticsDto(member.getId(), member.getName(), member.getEmail(), orderCount);
                memberStatisticsList.add(memberStatisticsDto);
            }
        }
        log.info("Members with orders greater than {} fetched successfully", n);

        ResponseDto<List<MemberStatisticsDto>> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        responseDto.setMessage("Members with orders greater than " + n + " fetched successfully");
        responseDto.setData(memberStatisticsList);
        return responseDto;
    }
}