package SW_ET.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserServiceException(UserServiceException ex) {
        return ex.getMessage();
    }
    //사용자 ID가 이미 존재할 때
    //사용자 닉네임이 이미 존재할 때
    //제공된 비밀번호와 확인 비밀번호가 일치하지 않을 때
    //로그인 시도 시 자격 증명이 유효하지 않을 때 예외처리

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
