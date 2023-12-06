package com.foodymoody.be.reply_heart.presentation;

import com.foodymoody.be.common.annotation.MemberId;
import com.foodymoody.be.reply_heart.infra.usecase.ReplyHeartWriteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReplyHeartWriteController {

    private final ReplyHeartWriteUseCase useCase;

    @PostMapping("/api/comments/{commentId}/replies/{replyId}/hearts")
    public ResponseEntity<Void> create(@PathVariable String commentId, @PathVariable String replyId,
            @MemberId String memberId) {
        useCase.registerReplyHeart(commentId, replyId, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/api/comments/{commentId}/replies/{replyId}/hearts")
    public ResponseEntity<Void> delete(@PathVariable String commentId, @PathVariable String replyId,
            @MemberId String memberId) {
        useCase.deleteReplyHeart(commentId, replyId, memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
