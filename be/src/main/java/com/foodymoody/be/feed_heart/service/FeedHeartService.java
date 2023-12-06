package com.foodymoody.be.feed_heart.service;

import com.foodymoody.be.common.util.IdGenerator;
import com.foodymoody.be.feed.domain.Feed;
import com.foodymoody.be.feed.service.FeedService;
import com.foodymoody.be.feed_heart.domain.FeedHeart;
import com.foodymoody.be.feed_heart.dto.request.FeedHeartServiceRequest;
import com.foodymoody.be.feed_heart.dto.response.FeedHeartResponse;
import com.foodymoody.be.feed_heart.repository.FeedHeartRepository;
import com.foodymoody.be.feed_heart.util.FeedHeartMapper;
import com.foodymoody.be.feed_heart_count.domain.FeedHeartCount;
import com.foodymoody.be.feed_heart_count.service.FeedHeartCountService;
import com.foodymoody.be.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FeedHeartService {

    private final FeedHeartRepository feedHeartRepository;
    private final FeedHeartCountService feedHeartCountService;
    private final MemberService memberService;
    private final FeedService feedService;

    @Transactional
    public FeedHeartResponse like(FeedHeartServiceRequest request) {
        String memberId = request.getMemberId();
        String feedId = request.getFeedId();

        memberService.validateIdExists(memberId);

        if (existsHeart(memberId, feedId)) {
            throw new IllegalArgumentException("이미 좋아요 누른 피드입니다.");
        }

        FeedHeart feedHeart = FeedHeartMapper.makeFeedHeartWithFeedIdAndMemberId(feedId, memberId);
        FeedHeart savedFeedHeart = feedHeartRepository.save(feedHeart);

        feedHeartCountService.incrementFeedHeartCount(feedId);

        FeedHeartCount feedHeartCount = feedHeartCountService.findFeedHeartCountByFeedId(feedId);
        Feed feed = updateFeed(feedId, feedHeartCount.getCount(), true);

        return FeedHeartMapper.toHeartResponse(savedFeedHeart.getId(), savedFeedHeart.getFeedId(),
                savedFeedHeart.getMemberId(), feed.isLiked(), feedHeartCount.getCount());
    }

    @Transactional
    public void unLike(FeedHeartServiceRequest request) {
        String feedId = request.getFeedId();
        String memberId = request.getMemberId();

        memberService.validateIdExists(memberId);

        if (!existsHeart(memberId, feedId)) {
            throw new IllegalArgumentException("좋아요 기록이 없어 취소할 수 없습니다.");
        }

        feedHeartRepository.deleteByFeedIdAndMemberId(feedId, memberId);

        feedHeartCountService.decrementFeedHeartCount(feedId);

        FeedHeartCount feedHeartCount = feedHeartCountService.findFeedHeartCountByFeedId(feedId);
        updateFeed(feedId, feedHeartCount.getCount(), false);
    }

    private Feed updateFeed(String feedId, int heartCount, boolean isLiked) {
        Feed feed = feedService.findFeed(feedId);

        feed.updateIsLikedBy(isLiked);
        feed.updateLikeCountBy(heartCount);

        return feed;
    }

    private boolean existsHeart(String memberId, String feedId) {
        return feedHeartRepository.existsHeartByMemberIdAndFeedId(memberId, feedId);
    }

}