package com.example.order.service;

import com.example.order.pojo.Member;
import com.example.order.pojo.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MemberService {

    private final ConcurrentHashMap<Long, Member> members = new ConcurrentHashMap<>();
    private final AtomicLong memberIdSequence = new AtomicLong(1);

    public ConcurrentHashMap<Long, Member> getMembers() {
        return this.members;
    }

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    public ResponseDto<Member> addMember(Member member) {
        logger.info("Adding member: {}", member.getName());

        if (member.getName() == null || member.getEmail() == null) {
            logger.warn("Invalid input for adding member.");
            ResponseDto<Member> errorResponse = new ResponseDto<>();
            errorResponse.setStatus(0);
            String errorMessage = messageSource.getMessage("member.create.invalidInput", null, LocaleContextHolder.getLocale());
            errorResponse.setMessage(errorMessage);
            return errorResponse;
        }

        Long memberId = memberIdSequence.getAndIncrement();
        member.setId(memberId);
        members.put(memberId, member);

        logger.info("Member added successfully: {}", member.getName());

        ResponseDto<Member> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        String successMessage = messageSource.getMessage("success", null, LocaleContextHolder.getLocale());
        responseDto.setMessage(successMessage);
        responseDto.setData(member);
        return responseDto;
    }

    public ResponseDto<Member> updateMember(Long id, Member updatedMember) {
        logger.info("Updating member with ID: {}", id);

        if (!members.containsKey(id)) {
            logger.warn("Member with ID {} not found for updating.", id);
            ResponseDto<Member> errorResponse = new ResponseDto<>();
            errorResponse.setStatus(0);
            String errorMessage = messageSource.getMessage("member.update.not.found", null, LocaleContextHolder.getLocale());
            errorResponse.setMessage(errorMessage);
            return errorResponse;
        }

        updatedMember.setId(id);
        members.put(id, updatedMember);

        logger.info("Member with ID {} updated successfully.", id);

        ResponseDto<Member> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        String successMessage = messageSource.getMessage("success", null, LocaleContextHolder.getLocale());
        responseDto.setMessage(successMessage);
        responseDto.setData(updatedMember);
        return responseDto;
    }

    public ResponseDto<Void> deleteMember(Long id) {
        logger.info("Deleting member with ID: {}", id);

        if (!members.containsKey(id)) {
            logger.warn("Member with ID {} not found for deletion.", id);
            ResponseDto<Void> errorResponse = new ResponseDto<>();
            errorResponse.setStatus(0);
            String errorMessage = messageSource.getMessage("member.delete.not.found", null, LocaleContextHolder.getLocale());
            errorResponse.setMessage(errorMessage);
            return errorResponse;
        }

        members.remove(id);

        logger.info("Member with ID {} deleted successfully.", id);

        ResponseDto<Void> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        String successMessage = messageSource.getMessage("success", null, LocaleContextHolder.getLocale());
        responseDto.setMessage(successMessage);
        return responseDto;
    }

    public ResponseDto<Member> getMemberById(Long id) {
        logger.info("Fetching member with ID: {}", id);

        Member member = members.get(id);
        if (member == null) {
            logger.warn("Member with ID {} not found.", id);
            ResponseDto<Member> errorResponse = new ResponseDto<>();
            errorResponse.setStatus(0);
            String errorMessage = messageSource.getMessage("member.fetch.not.found", null, LocaleContextHolder.getLocale());
            errorResponse.setMessage(errorMessage);
            return errorResponse;
        }

        logger.info("Member with ID {} fetched successfully.", id);

        ResponseDto<Member> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        String successMessage = messageSource.getMessage("success", null, LocaleContextHolder.getLocale());
        responseDto.setMessage(successMessage);
        responseDto.setData(member);
        return responseDto;
    }

    public ResponseDto<List<Member>> getAllMembers() {
        logger.info("Fetching all members.");

        List<Member> memberList = new ArrayList<>(members.values());

        logger.info("All members fetched successfully.");

        ResponseDto<List<Member>> responseDto = new ResponseDto<>();
        responseDto.setStatus(1);
        String successMessage = messageSource.getMessage("success", null, LocaleContextHolder.getLocale());
        responseDto.setMessage(successMessage);
        responseDto.setData(memberList);
        return responseDto;
    }
}