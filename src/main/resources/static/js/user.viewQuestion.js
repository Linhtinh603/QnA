var quillContent = new Quill('#editor', {
	modules : {
		toolbar : false
	},
	placeholder : 'Nhập nội dung câu hỏi ở đây...',
	theme : 'bubble'
});
quillContent.setContents(originContent)
quillContent.disable()

$(document).ready(function() {
	$("#btnDelete").click(function() {
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
	
	$("#btnFollow").click(function() {
		$.ajax({
			contentType:'application/json; charset=utf-8',
			type: 'POST',
			url:"/questions/follow?"+ $.param({"id":questionId}),
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
					  toastr.success('Hãy kiểm tra lại ở trong hồ sơ cá nhân','Theo dõi câu hỏi thành công')
				  },
			},
			complete: function() {
				self.disabled = false;
			}
		});
	});
	
});