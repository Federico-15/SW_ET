// 파일 선택 창을 여는 함수
function openFilePicker() {
    document.getElementById('fileInput').click();
}

// 이미지 미리보기를 표시하는 함수
function previewImage(event) {
    var input = event.target; // 이벤트가 발생한 요소 (파일 입력 필드)를 참조
    var reader = new FileReader(); // FileReader 객체를 생성

    // 파일이 성공적으로 읽혔을 때 실행되는 함수
    reader.onload = function() {
        var dataURL = reader.result; // 읽은 파일의 데이터 URL을 가져옴
        var imagePreview = document.getElementById('imagePreview'); // id가 'imagePreview'인 요소를 참조
        imagePreview.src = dataURL; // 이미지의 src 속성을 데이터 URL로 설정
        imagePreview.style.display = 'block'; // 이미지를 화면에 표시

        var cancelImgButton = document.getElementById('cancel_img'); // id가 'cancel_img'인 버튼을 참조
        cancelImgButton.style.display = 'inline'; // 이미지 취소 버튼을 화면에 표시
    };

    // 파일이 선택되었는지 확인하고, 파일을 읽음
    if (input.files && input.files[0]) {
        reader.readAsDataURL(input.files[0]); // 선택된 파일을 데이터 URL로 읽음
    }
}

// 이미지 취소 버튼을 클릭했을 때 실행되는 함수
function cancelImage() {
    var fileInput = document.getElementById('fileInput'); // id가 'fileInput'인 파일 입력 필드를 참조
    var imagePreview = document.getElementById('imagePreview'); // id가 'imagePreview'인 이미지를 참조
    var cancelImgButton = document.getElementById('cancel_img'); // id가 'cancel_img'인 버튼을 참조

    fileInput.value = ""; // 파일 입력 필드의 값을 비움
    imagePreview.src = ""; // 이미지의 src 속성을 비움
    imagePreview.style.display = 'none'; // 이미지를 화면에서 숨김
    cancelImgButton.style.display = 'none'; // 이미지 취소 버튼을 화면에서 숨김
}
