// 헤더 UI 변경으로 활용하기(로그인 전: 글쓰기 버튼 없음)
// 페이지 로드 시 JWT를 읽어옵니다.
document.addEventListener('DOMContentLoaded', function () {
     var jwtToken = localStorage.getItem('jwtToken'); // 로컬 스토리지에서 JWT 토큰 가져오기
    if (jwtToken) {
        console.log('저장된 JWT:', jwtToken);
        console.log('You are logged in!');
        document.getElementById('loggedIn').style.display = 'block'; // 로그인 상태 표시
        document.getElementById('loggedOut').style.display = 'none'; // 비로그인 상태 숨기기
        // JWT를 활용하여 필요한 작업을 수행합니다.
        // 예를 들어, 사용자 정보를 가져오거나 보호된 API 호출을 할 수 있습니다.

    } else {
        console.log('JWT가 없습니다.');
        console.log('You are not logged in!');
        document.getElementById('loggedOut').style.display = 'block';
    }
    // 로그아웃 버튼 클릭 시 처리
    document.getElementById('logoutButton').addEventListener('click', function (event) {
        event.preventDefault(); // 폼 제출 방지 - 이 코드가 없으면 users/logout으로 이동
        localStorage.removeItem('jwtToken');  // 로컬 스토리지에서 JWT 토큰 삭제
        console.log('Logged out and JWT token removed.');
        alert("로그아웃 되었습니다. \nYou have been logged out.")
        document.getElementById('loggedIn').style.display = 'none';  // 로그인 상태 숨기기
        document.getElementById('loggedOut').style.display = 'block';  // 비로그인 상태 표시
    });
});

function movePage(moveUrl) {
    const token = getJwtToken();

    fetch('/verify-token', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}` // 인증 헤더에 토큰을 추가합니다.
        }
    })
        .then(response => {
            if (response.ok) {
                return response.json(); // JSON으로 파싱
            } else if (response.status === 403) {
                throw new Error('접근 권한이 없습니다. 로그인 상태를 확인해주세요.');
            } else {
                throw new Error('리뷰 등록 실패');
            }
        })
        .then(data => {
            console.log(data.message); // 서버로부터 받은 메시지 출력
            window.location.href = moveUrl; // 페이지 이동
        })
        .catch((error) => {
            console.error('실패:', error);
            alert(error.message);
        });
}

function getJwtToken() {
    return localStorage.getItem('jwtToken'); // 토큰을 로컬 스토리지에서 가져옵니다.
}


