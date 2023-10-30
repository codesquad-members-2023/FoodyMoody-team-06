package com.foodymoody.be.comment.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.foodymoody.be.comment.repository.CommentRepository;
import com.foodymoody.be.comment.util.CommentFixture;
import com.foodymoody.be.common.exception.CommentNotExistsException;
import com.foodymoody.be.common.exception.ContentIsEmptyException;
import com.foodymoody.be.common.exception.ContentIsOver200Exception;
import com.foodymoody.be.common.exception.ContentIsSpaceException;
import com.foodymoody.be.common.exception.ContentNotExistsException;
import com.foodymoody.be.feed.service.FeedService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("댓글 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private FeedService feedService;
    @Mock
    private CommentRepository commentRepository;

    @DisplayName("댓글 등록 시 댓글 내용이 없으면 댓글 내용 없음 예외가 발생한다.")
    @Test
    void when_register_comment_if_content_not_exists_throw_exception() {
        // given
        var request = CommentFixture.registerCommentRequestWithoutContent();

        // when,then
        assertThatThrownBy(() -> commentService.registerComment(request))
                .isInstanceOf(ContentNotExistsException.class);
    }

    @DisplayName("댓글 등록 시 댓글 내용이 공백이면 댓글 내용 공백 예외가 발생한다.")
    @Test
    void when_register_comment_if_content_is_blank_then_throw_exception() {
        // given
        var request = CommentFixture.registerCommentRequestWithEmptyContent();

        // when,then
        assertThatThrownBy(() -> commentService.registerComment(request))
                .isInstanceOf(ContentIsEmptyException.class);
    }

    @DisplayName("댓글 등록 시 댓글 내용이 space 뿐이면 댓글 내용 공백 예외가 발생한다.")
    @Test
    void when_register_comment_if_content_is_space_then_throw_exception() {
        // given
        var request = CommentFixture.registerCommentRequestWithSpace();

        // when,then
        assertThatThrownBy(() -> commentService.registerComment(request))
                .isInstanceOf(ContentIsSpaceException.class);
    }

    @DisplayName("댓글 등록 시 댓글 내용이 200자를 초과하면 댓글 내용 200자 초과 예외가 발생한다.")
    @Test
    void when_register_comment_if_content_is_larger_than_200_then_throw_exception() {
        // given
        var request = CommentFixture.registerCommentRequestWithContentOver200();

        // when,then
        assertThatThrownBy(() -> commentService.registerComment(request))
                .isInstanceOf(ContentIsOver200Exception.class);
    }

    @DisplayName("댓글 조회 시 댓글이 없으면 에외를 던진다")
    @Test
    void when_fetch_comment_if_comment_not_exists_then_throw_exception() {
        // given
        var id = CommentFixture.NOT_EXISTS_ID;
        given(commentRepository.findById(id))
                .willReturn(Optional.empty());

        // when,then
        assertThatThrownBy(() -> commentService.fetchById(id))
                .isInstanceOf(CommentNotExistsException.class);
    }
}
