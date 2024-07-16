document.getElementById('myPageLink').addEventListener('click', function(e) {
    e.preventDefault();
    loadMyPageData();  // 페이지 데이터 로드 함수 호출
});

function loadMyPageData() {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
        alert('Please log in to view this page.');
        return;
    }

    fetch('/api/my-page', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (response.ok) {
            response.json().then(data => {
                displayMyPage(data);
            });
        } else {
            alert('Unable to load the page. Please log in again.');
        }
    }).catch(error => {
        console.error('Failed to fetch:', error);
    });
}

function displayMyPage(data) {
    const contentDiv = document.getElementById('myPageContent');
    contentDiv.innerHTML = `
        <h1>My Favorites</h1>
        <ul>
            ${data.favoriteDestinations.map(d => `<li>${d.name}</li>`).join('')}
        </ul>
        <h2>My Reviews</h2>
        <ul>
            ${data.userReviews.map(r => `<li>${r.title} - ${r.content}</li>`).join('')}
        </ul>
    `;
}