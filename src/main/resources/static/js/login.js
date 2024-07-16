/**
 * login.js
 * @author Hun
 */

"use strict";
const debug_mode = true;

// 로그인 파라미터 체크 함수
let check = () => {
    let userId = $( "#userId").val();
    let userPassword = $( "#userPassword" ).val();

    if("" == userId || "" == userPassword) {
        alert( "로그인 정보를 입력해주세요." );
        return false;
    }
}