var toolbarOptions = [ [ 'bold', 'italic', 'underline', 'strike' ], // toggled
																	// buttons
// ['blockquote', 'code-block'],

[ {
	'header' : 1
}, {
	'header' : 2
} ], // custom button values
[ {
	'list' : 'ordered'
}, {
	'list' : 'bullet'
} ], [ {
	'script' : 'sub'
}, {
	'script' : 'super'
} ], // superscript/subscript
[ {
	'indent' : '-1'
}, {
	'indent' : '+1'
} ], // outdent/indent
// [{ 'direction': 'rtl' }], // text direction

[ {
	'size' : [ 'small', false, 'large', 'huge' ]
} ], // custom dropdown
[ {
	'header' : [ 1, 2, 3, 4, 5, 6, false ]
} ],

[ {
	'color' : []
}, {
	'background' : []
} ], // dropdown with defaults from theme
[ {
	'font' : []
} ], [ {
	'align' : []
} ],

[ 'clean' ] // remove formatting button
];

var quillContent = new Quill('#editor', {
	modules : {
		toolbar : toolbarOptions
	},
	placeholder : 'Nhập nội dung câu hỏi ở đây...',
	theme : 'snow'
});
quillContent.setContents(originContent)

$(document).ready(function() {
	$("#btnReset").click(function(){
		window.location.reload()
	})
	$("#btnEdit").click(function() {
		var self = this;
		var title = $("[name=title]").val();
		var category = $("[name='category.id']").val();
		var author = $("[name='author.id']").val();
		var id = $("[name='id']").val();
		var content = quillContent.getContents();
		var length = quillContent.getLength();
		if (!title||$.trim(title)=='') {
			toastr.error('Tiêu đề bị trống(hãy điền tiêu đề)', 'Có lỗi xảy ra!');
			return;
		}
		if (title.length<10) {
			toastr.error('Tiêu đề quá ngắn(phải từ 10 ký tự)', 'Có lỗi xảy ra!');
			return;
		}
		
		if (title.length>100) {
			toastr.error('Tiêu đề quá dài(phải nhỏ hơn 100 ký tự)', 'Có lỗi xảy ra!');
			return;
		}
		if (!category) {
			toastr.error('Thể loại bị trống(hãy chọn thể loại)', 'Có lỗi xảy ra!');
			return;
		}
		if (length < 30+1) {
			toastr.error('Nội dung quá ngắn(phải từ 30 ký tự)', 'Có lỗi xảy ra!');
			return;
		}
		if (length > 2000+1) {
			toastr.error('Nội dung quá dài(phải nhỏ hơn 2000 ký tự)', 'Có lỗi xảy ra!');
			return;
		}
		$.ajax({
			contentType:'application/json; charset=utf-8',
			type: 'POST',
			url:"/questions/edit",
			dataType: 'json',
			data:JSON.stringify({
				id: id,
				author: {
					id: author
				},
				title: title,
				category: {id:category},
				content: JSON.stringify(content) }),
			beforeSend:function(){
				$(self).prop("disabled", true);
			},
			statusCode: {
				  302: function() { 
					  toastr.success('Hết phiên đăng nhập');
					  window.location.reload()
				  },
				  201: function() { 
					  toastr.success('Chỉnh sửa câu hỏi thành công')
					  setTimeout(function () {
						  window.location = `/questions/${id}`
					  }, 1000)   
				  },
				  400: function() { toastr.error('Hãy thử lại sau', 'Có lỗi xảy ra!'); }
			},
			complete: function() {
				$(self).prop("disabled", true);
			}
		});
	});
});