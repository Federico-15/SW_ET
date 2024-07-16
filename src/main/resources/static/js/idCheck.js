/* 아이디 중복 검사 */
function checkUserIdExists() {
    var userId = document.getElementById("userId").value;

    if (userId.trim() === "") {
        alert("아이디를 입력해주세요.");
        return;
    }

    fetch(`/users/check-userId?userId=${encodeURIComponent(userId)}`)
        .then(response => response.json())
        .then(data => {
            if (!data) {
                alert("사용할 수 있는 아이디입니다.");
            } else {
                alert("이미 사용 중인 아이디입니다. 다른 아이디를 입력해주세요");
            }
        })
        .catch(() => alert("서버 오류가 발생했습니다."));
}