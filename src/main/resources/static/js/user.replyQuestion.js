
function connect() {
      
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
 
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/answer', onMessageReceived);
    console.log("onConnected")
}

function onError(error) {
	 console.log("error")
}
function sendMessage(event){
	 var answer = {
		 content: "Nội dung câu hỏi"
	 }
	 stompClient.send("/app/reply", {}, JSON.stringify(answer));
	 console.log("sendmessage")
}
function onMessageReceived(payload){
	 var message = JSON.parse(payload.body);
	 console.log("on message receive:  ",message);
}
$(document).ready(function() {
	connect();
});

$(document).ready(function() {
	$("#btnReply").click(function() {
		var self = this;
		var path = window.location.pathname.split("/")
		var idQuestion = path[2]
		var content = $("[name=content]").val();
		if (!content||$.trim(content)=='') {
			toastr.error('Nội dung câu hỏi bị trống (hãy nhập nội dung câu hỏi)', 'Có lỗi xảy ra!');
			return;
		}
		if (content.length<10) {
			toastr.error('Nội dung trả lời quá ngắn(phải từ 10 ký tự)', 'Có lỗi xảy ra!');
			return;
		}
		$.ajax({
			contentType:'application/json; charset=utf-8',
			type: 'POST',
			url:"/questions/"+idQuestion,
			dataType: 'json',
			data:JSON.stringify({
				content }),
			beforeSend:function(){
				self.disabled = true;
			},
			statusCode: {
				  302: function() { 
					  toastr.success('Hết phiên đăng nhập');
					  window.location.reload()
				  },
				  201: function() { 
					  toastr.success('Đăng câu hỏi thành công')
					  sendMessage();
					  window.location.reload()
				  },
				  400: function() { toastr.error('Hãy thử lại sau', 'Có lỗi xảy ra!'); }
			},
			complete: function() {
				self.disabled = false;
			}
		});
	})
});