package com.foodymoody.be.heart.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Heart {

    @Id
    private String id;
    private String feedId;
    private String memberId;
    private int count;

    public Heart() {
    }

    public Heart(String id, String feedId, String memberId) {
        this.id = id;
        this.feedId = feedId;
        this.memberId = memberId;
    }

    public String getId() {
        return id;
    }

    public String getFeedId() {
        return feedId;
    }

    public String getMemberId() {
        return memberId;
    }

    public int getCount() {
        return count;
    }

    public void updateCount() {
        this.count += 1;
    }

}