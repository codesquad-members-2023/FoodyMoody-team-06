package com.foodymoody.be.member.service;

import com.foodymoody.be.common.exception.DuplicateMemberEmailException;
import com.foodymoody.be.common.exception.DuplicateNicknameException;
import com.foodymoody.be.common.exception.MemberNotFoundException;
import com.foodymoody.be.member.controller.dto.MemberSignupRequest;
import com.foodymoody.be.member.controller.dto.MemberSignupResponse;
import com.foodymoody.be.member.domain.Member;
import com.foodymoody.be.member.domain.MemberId;
import com.foodymoody.be.member.repository.MemberFeedData;
import com.foodymoody.be.member.domain.TasteMood;
import com.foodymoody.be.member.domain.TasteMoodId;
import com.foodymoody.be.member.repository.MemberProfileFeedPreviewResponse;
import com.foodymoody.be.member.repository.MemberProfileResponse;
import com.foodymoody.be.member.repository.MemberRepository;
import com.foodymoody.be.member.util.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final TasteMoodService tasteMoodService;
    @Transactional
    public MemberSignupResponse create(MemberSignupRequest request) {
        TasteMoodId tasteMoodId = new TasteMoodId(request.getTasteMoodId());
        TasteMood tasteMood = tasteMoodService.findById(tasteMoodId);
        validateNicknameDuplication(request.getNickname());
        validateEmailDuplication(request.getEmail());
        MemberId savedMemberId = memberRepository.save(MemberMapper.toEntity(request, tasteMood)).getId();
        return MemberMapper.toSignupResponse(savedMemberId);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }

    public MemberFeedData fetchFeedDataById(String id) {
        return memberRepository.fetchFeedDataById(id).orElseThrow(MemberNotFoundException::new);
    }

    public void validateIdExists(String id) {
        MemberId key = new MemberId(id);
        if (!memberRepository.existsById(key)) {
            throw new MemberNotFoundException();
        }
    }

    public Member findById(String id) {
        MemberId key = new MemberId(id);
        return memberRepository.findById(key).orElseThrow(MemberNotFoundException::new);
    }

    private void validateEmailDuplication(String email) {
        if(memberRepository.existsByEmail(email)){
            throw new DuplicateMemberEmailException();
        }
    }

    private void validateNicknameDuplication(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException();
        }
    }

    public Slice<MemberProfileFeedPreviewResponse> fetchProfileFeedPreviews(String id, Pageable pageable) {
        return memberRepository.fetchFeedPreviews(id, pageable);
    }

    public MemberProfileResponse fetchProfile(String id) {
        return memberRepository.fetchProfileById(id)
                .orElseThrow(MemberNotFoundException::new);
    }

}