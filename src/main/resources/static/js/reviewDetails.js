function loadReviewDetails(reviewId) {
    const token = localStorage.getItem('jwtToken');
    fetch(`/api/reviews/detail/${reviewId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);  // 여기에서 데이터를 페이지에 표시하는 로직을 추가
        })
        .catch(error => console.error('Error:', error));
}

// 페이지 로드 시 자동으로 리뷰 정보를 로드하도록 설정
document.addEventListener('DOMContentLoaded', function () {
    const reviewId = '여기에 리뷰 ID 입력';  // 실제 사용 시에는 리뷰 ID를 동적으로 설정
    loadReviewDetails(reviewId);
});
