function likes(id) {
    $.ajax({
        url: '/api/likes/' + id,
        type : 'POST',
        success: function() {
            location.reload();
        }
    });
}

function unlikes(id) {
    $.ajax({
        url: '/api/likes/' + id,
        type : 'DELETE',
        success: function() {
            location.reload();
        }
    });
}

function deletePost(id) {
    $.ajax({
        url: '/api/boards/' + id,
        type: 'PUT',
        success: function(result) {
            console.log('result', result);
            alert('삭제됐습니다.');
            window.location.href = '/boards/list';
        }
    });
}

function writeCmt(id) {
    var params = {
        postId: $('#postId').val(),
        cmtContent: $('#cmtContent').val()};

    $.ajax({
        type: 'POST',
        url: '/api/comments',
        dataType : "json",
        contentType : "application/json;charset=UTF-8",
        data : JSON.stringify(params)
        }).done(function() {
            alert('댓글 등록 완료');
            location.reload();
        }).fail(function(error){
            alert('data: ' + JSON.stringify(params));
            location.reload();
        });
}

function deleteCmt(id) {
    var postId = $('#postId').val();

    $.ajax({
        url: '/api/comments/' + id,
        type: 'PUT',
        success: function() {
            alert('삭제됐습니다.');
            window.location.href = '/boards/'+ postId;
        }
    });
}