// 별점 선택
let starRating = 1;
$(document).ready(function() {
    $('.star_rating > .star').click(function() {
        $(this).parent().children('span').removeClass('on');
        $(this).addClass('on').prevAll('span').addClass('on');
        starRating = $(this).attr('value');
        console.log('별점:', starRating);
        $('#starRating').val(starRating); // hidden 필드에 별점 값 설정
    });
});