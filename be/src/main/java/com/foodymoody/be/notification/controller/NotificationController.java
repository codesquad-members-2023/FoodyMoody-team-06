package com.foodymoody.be.notification.controller;

import com.foodymoody.be.notification.controller.dto.NotificationResponse;
import com.foodymoody.be.notification.controller.dto.NotificationStatus;
import com.foodymoody.be.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/api/notifications/{memberId}")
    public ResponseEntity<Slice<NotificationResponse>> request(@PathVariable String memberId,
            @PageableDefault Pageable pageable) {
        Slice<NotificationResponse> notifications = notificationService.request(memberId, pageable);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/api/notifications/{memberId}/{notificationId}")
    public ResponseEntity<Void> changeStatus(@PathVariable String memberId, @PathVariable String notificationId,
            @RequestBody NotificationStatus status) {
        notificationService.changeStatus(memberId, notificationId, status.isRead());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/notifications/{memberId}/{notificationId}")
    public ResponseEntity<Void> delete(@PathVariable String memberId, @PathVariable String notificationId) {
        notificationService.delete(memberId, notificationId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/notifications/{memberId}")
    public ResponseEntity<Void> deleteAll(@PathVariable String memberId) {
        notificationService.deleteAll(memberId);
        return ResponseEntity.noContent().build();
    }
}
