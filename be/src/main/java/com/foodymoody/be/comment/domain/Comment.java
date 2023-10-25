package com.foodymoody.be.comment.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {

    @Id
    private String id;
    private String content;
    private String feedId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Comment(String id, String content, String feedId) {
        this.id = id;
        this.content = content;
        this.feedId = feedId;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getFeedId() {
        return feedId;
    }

    public void edit(String content) {
        CommentValidator.validateContent(content);
        this.content = content;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
}
