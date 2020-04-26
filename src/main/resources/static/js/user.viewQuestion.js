var quillContent = new Quill('#editor', {
	modules : {
		toolbar : false
		border : false
	},
	placeholder : 'Nhập nội dung câu hỏi ở đây...',
	theme : 'bubble'
});
quillContent.setContents(originContent)
quillContent.disable()

$(document).ready(function() {
	
});