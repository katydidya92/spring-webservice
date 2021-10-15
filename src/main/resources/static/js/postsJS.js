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
        postId: $('#cmtPostId').val(),
        cmtContent: $('#cmtContent').val(),
        cmtReplyId: $('#cmtReplyId').val()};

    $.ajax({
        type: 'POST',
        url: '/api/comments/' + id,
        data : JSON.stringify(param),
        dataType : "json",
        contentType : "application/json;charset=UTF-8",
        success: function() {
            printCommentList();
        }
    });
}