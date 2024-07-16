/* 닉네임 중복 검사 */
function checkUserNicknameExists() {
    var userNickName = document.getElementById("userNickName").value;

    if (userNickName.trim() === "") {
        alert("닉네임을 입력해주세요.");
        return;
    }

    fetch(`/users/check-nickname?userNickName=${encodeURIComponent(userNickName)}`)
        .then(response => response.json())
        .then(data => {
            if (!data) {
                alert("사용할 수 있는 닉네임입니다.");
            } else {
                alert("이미 사용 중인 닉네임입니다. 다른 닉네임을 입력해주세요.");
            }
        })
        .catch(() => alert("서버 오류가 발생했습니다."));
}