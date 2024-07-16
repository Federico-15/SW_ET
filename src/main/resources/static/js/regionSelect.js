/*
document.addEventListener('DOMContentLoaded', function () {
    // 페이지 로드 시 지역 그룹 데이터를 가져옴
    fetchWithAuth('/api/regions/groups') // '/api/regions/groups' 엔드포인트에서 지역 그룹 데이터를 가져옴
        .then(response => response.json()) // 응답을 JSON 형식으로 변환
        .then(data => {
            console.log('Regions:', data); // 데이터 확인을 위해 콘솔에 출력
            populateRegions(data); // 지역 데이터를 사용하여 드롭다운 메뉴를 채움
        })
        .catch(error => console.error('Error fetching regions:', error)); // 에러 발생 시 콘솔에 출력

    // 지역 선택 시 하위 지역 데이터를 가져옴
    document.getElementById('regionSelect').addEventListener('change', function () {
        const regionId = this.value; // 선택된 지역의 ID를 가져옴
        if (regionId) {
            fetchWithAuth(`/api/regions/${regionId}/sub-regions`) // 하위 지역 데이터를 가져오는 함수 호출
                .then(response => response.json()) // 응답을 JSON 형식으로 변환
                .then(data => {
                    console.log('Subregions:', data); // 하위 지역 데이터를 콘솔에 출력
                    populateSubRegions(data); // 하위 지역 데이터를 사용하여 드롭다운 메뉴를 채움
                })
                .catch(error => console.error('Error fetching subregions:', error)); // 에러 발생 시 콘솔에 출력
        } else {
            const subregionSelect = document.getElementById('subregionSelect');
            subregionSelect.innerHTML = '<option value="">시.도청 소재지를 선택해주세요</option>'; // 하위 지역 선택 초기화
        }
    });
});

function populateSubRegions(subRegions) {
    const subregionSelect = document.getElementById('subregionSelect');
    subregionSelect.innerHTML = '<option value="">시.도청 소재지를 선택해주세요</option>'; // 기본 옵션 추가
    subRegions.forEach(subregion =>
    {
        const option = document.createElement('option');
        option.value = subregion.subRegionId;  // 옵션의 값으로 subRegionId 설정
        option.innerText = subregion.subRegionName;  // 옵션의 텍스트로 subRegionName 설정
        console.log('Adding subregion option:', option); // 추가할 하위 지역 옵션을 콘솔에 출력
        subregionSelect.appendChild(option); // 하위 지역 옵션을 드롭다운 메뉴에 추가
    });
}

function fetchWithAuth(url) {
    return fetch(url, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${getJwtToken()}` // 공백 추가
        }
    }).then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Server responded with status: ' + response.status);
        }
    });
}
*/


// 여기부터 내 코드

/*
document.addEventListener('DOMContentLoaded', function () {
    fetchWithAuth('/api/regions/groups')
        .then(response => {
            if (response.ok) {
                return response.json();  // 이 부분이 누락되었습니다. 추가해야 합니다.
            } else {
                throw new Error('Failed to load region groups: ' + response.statusText);
            }
        })
        .then(data => {
            console.log('Regions:', data);
            populateRegions(data);
        })
        .catch(error => {
            console.error('Error fetching regions:', error);
            alert('지역 정보를 불러오는 데 실패했습니다. 페이지를 새로 고침 해주세요.');
        });
});

function fetchWithAuth(url) {
    const token = getToken();
    return fetch(url, {
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    });
}

document.getElementById('regionSelect').addEventListener('change', function () {
    const regionId = this.value;
    if (regionId) {
        fetchWithAuth(`/api/regions/${regionId}/sub-regions`)
            .then(response => response.json())
            .then(data => {
                console.log('Subregions:', data);
                populateSubRegions(data);
            })
            .catch(error => console.error('Error fetching subregions:', error));
    } else {
        const subregionSelect = document.getElementById('subregionSelect');
        subregionSelect.innerHTML = '<option value="">시.도청 소재지를 선택해주세요</option>';
    }
});

/!*function populateRegions(regionGroups) {
    console.log("Received region groups:", regionGroups);
    const regionSelect = document.getElementById('regionSelect');
    regionSelect.innerHTML = '<option value="">행정구역을 선택해주세요</option>';
    regionGroups.forEach(group => {
        const option = document.createElement('option');
        option.value = group.regionId;
        option.innerText = group.regionName;
        regionSelect.appendChild(option);
    });
}

function populateSubRegions(subRegions) {
    const subregionSelect = document.getElementById('subregionSelect');
    subregionSelect.innerHTML = '<option value="">시.도청 소재지를 선택해주세요</option>';
    subRegions.forEach(subregion => {
        const option = document.createElement('option');
        option.value = subregion.subRegionId;
        option.innerText = subregion.subRegionName;
        subregionSelect.appendChild(option);
    });
}*!/

function populateRegions(regionGroups) {
    console.log("Received region groups:", regionGroups);
    const regionSelect = document.getElementById('regionSelect');
    regionSelect.innerHTML = '<option value="">행정구역을 선택해주세요</option>';
    regionGroups.forEach(group => {
        group.regions.forEach(region => { // 중첩된 regions 배열에 접근
            const option = document.createElement('option');
            option.value = region.regionId; // regionId 사용
            option.innerText = region.regionName; // regionName 사용
            regionSelect.appendChild(option);
        });
    });
}

function populateSubRegions(subRegions) {
    const subregionSelect = document.getElementById('subregionSelect');
    subregionSelect.innerHTML = '<option value="">시.도청 소재지를 선택해주세요</option>';
    subRegions.forEach(subregion => {
        const option = document.createElement('option');
        option.value = subregion.subRegionId; // subRegionId 사용
        option.innerText = subregion.subRegionName; // subRegionName 사용
        subregionSelect.appendChild(option);
    });
}

function getToken() {
    return localStorage.getItem('jwtToken');
}
*/

document.addEventListener('DOMContentLoaded', function () {
    fetchWithAuth('/api/regions/region-groups')
        .then(response => {
            if (response.ok) {
                return response.json(); // 서버로부터 받은 응답을 JSON으로 파싱
            } else {
                throw new Error('Failed to load region groups: ' + response.statusText);
            }
        })
        .then(data => {
            console.log('Regions:', data);
            populateRegions(data);
        })
        .catch(error => {
            console.error('Error fetching regions:', error);
            alert('지역 정보를 불러오는 데 실패했습니다. 페이지를 새로 고침 해주세요.');
        });
});

function populateRegions(regionGroups) {
    const regionSelect = document.getElementById('regionSelect');
    regionSelect.innerHTML = '<option value="">행정구역을 선택해주세요</option>';
    regionGroups.forEach(group => {
        group.regions.forEach(region => {
            const option = document.createElement('option');
            option.value = region.regionId;
            option.innerText = region.regionName;
            regionSelect.appendChild(option);
        });
    });
}

document.getElementById('regionSelect').addEventListener('change', function () {
    const regionId = this.value;  // 선택된 지역의 ID를 가져옴
    if (regionId) {
        fetchWithAuth(`/api/regions/sub-regions/${regionId}`)
            .then(response => {
                if (response.ok) {
                    return response.json();  // 응답을 JSON 형식으로 변환
                } else {
                    throw new Error('Failed to load subregions: ' + response.statusText);
                }
            })
            .then(data => {
                console.log('Subregions:', data);  // 하위 지역 데이터를 콘솔에 출력
                populateSubRegions(data);  // 하위 지역 데이터를 사용하여 드롭다운 메뉴를 채움
            })
            .catch(error => {
                console.error('Error fetching subregions:', error);
                alert('하위 지역 정보를 불러오는 데 실패했습니다.');
            });
    } else {
        const subregionSelect = document.getElementById('subregionSelect');
        subregionSelect.innerHTML = '<option value="">시.도청 소재지를 선택해주세요</option>';  // 하위 지역 선택 초기화
    }
});

function populateSubRegions(subRegions) {
    const subregionSelect = document.getElementById('subregionSelect');
    subregionSelect.innerHTML = '<option value="">시.도청 소재지를 선택해주세요</option>';
    subRegions.forEach(subregion => {
        const option = document.createElement('option');
        option.value = subregion.subRegionId;  // 옵션의 값으로 subRegionId 설정
        option.innerText = subregion.subRegionName;  // 옵션의 텍스트로 subRegionName 설정
        subregionSelect.appendChild(option);  // 하위 지역 옵션을 드롭다운 메뉴에 추가
    });
}

function fetchWithAuth(url) {
    const token = getToken();
    return fetch(url, {
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    });
}

function getToken() {
    return localStorage.getItem('jwtToken');
}