package com.foodymoody.be.common.exception;

public enum ErrorMessage {
    // global
    INVALID_INPUT_VALUE("입력값이 올바르지 않습니다.", "g001"),
    INVALID_ID("유효하지 않은 아이디입니다.", "g002"),
    CREATE_TIME_IS_NULL("생성시간이 null이면 안된다", "g003"),
    // comment
    CONTENT_NOT_EXISTS("댓글이 존재하지 않습니다.", "c001"),
    CONTENT_IS_EMPTY("댓글 내용이 공백입니다.", "c002"),
    CONTENT_IS_SPACE("댓글 내용이 공백입니다.", "c003"),
    FEED_ID_NOT_EXISTS("피드가 존재하지 않습니다.", "c004"),
    CONTENT_IS_OVER_200("댓글은 200자 이하여야 합니다.", "c005"),
    REGISTER_COMMENT_REQUEST_NOT_NULL("등록 요청이 없으면 안된다", "c006"),
    COMMENT_NOT_EXISTS("댓글이 존재하지 않는다", "c007"),
    COMMENT_DELETED("삭제된 댓글입니다.", "c008"),
    // member
    MEMBER_NOT_FOUND("존재하지 않는 회원입니다", "m001"),
    DUPLICATE_MEMBER_EMAIL("이미 가입된 이메일입니다", "m002"),
    DUPLICATE_MEMBER_NICKNAME("이미 존재하는 닉네임입니다", "m003"),
    INVALID_CONFIRM_PASSWORD("입력하신 패스워드와 일치하지 않습니다", "m004"),
    MEMBER_INCORRECT_PASSWORD("사용자 정보와 패스워드가 일치하지 않습니다", "m005"),
    // auth
    INVALID_ACCESS_TOKEN("유효하지 않은 액세스 토큰입니다", "a001"),
    IS_NOT_HTTP_REQUEST("http 요청이 아닙니다", "a002");

    private final String message;
    private final String code;

    ErrorMessage(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
