$(document).ready(function() {
	$(".delete-button").click(function(){
		$("#deleteModal").modal()
		var questionId = $(this).attr("id")
		$("#btnDelete").attr("data-question",questionId);
	})
	
	$("#btnDelete").click(function() {
		var questionId = $(this).attr("data-question")
		$.ajax({
			contentType:'application/json; charset=utf-8',
			type: 'DELETE',
			url:"/questions/delete?"+ $.param({"id":questionId}),
			beforeSend:function(){
				self.disabled = true;
			},
			statusCode: {
				  302: function() { 
					  toastr.success('Hết phiên đăng nhập');
					  window.location.reload()
				  },
				  400: function() { 
					  toastr.error('Hãy thử lại sau', 'Có lỗi xảy ra!'); 
				  },
				  200: function() { 
					  toastr.success('Xóa câu hỏi thành công')
					   window.location = '/my_profile'
				  },
			},
			complete: function() {
				self.disabled = false;
			}
		});
	});
});