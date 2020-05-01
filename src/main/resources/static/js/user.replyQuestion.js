var content = ""
function connect() {
      
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
 
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    stompClient.subscribe(`/topic/answer/${questionId}`, onMessageReceived);
}

function onError(error) {
	 console.log("error when connect ws")
	 //reload
}
function sendMessage(event){
	 var answer = {
		content
	 }
	 stompClient.send(`/app/reply/${questionId}`, {}, JSON.stringify(answer));
	 toastr.success("Bạn đã vừa gửi trả lời cho câu hỏi này.")
}
function onMessageReceived(payload){
	 var answer = JSON.parse(payload.body);
	 console.log("on message receive:  ",answer);
	 var answer_list = document.getElementById('answer-list');
	 
	 if(owner){
		 var element = `<table class="mt-3 border-bottom border-secondary">
				<tbody>	
					<tr>
						<td class="align-middle text-secondary" data-toggle="tooltip"
							data-placement="top"
							title="Nhấn vào để dánh dấu câu trả lời giải quyết được vấn đề của bạn">
							<i class="fas fa-check fa-3x"></i>
						</td>
						<td>${answer.content}.</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<div>
								<a th:href="@{/profile/{id}(id=${answer.author.userName})}"
									class="font-italic m-1 text-info"> ${answer.author.fullName} </a> 
								<span class="font-italic text-info"> đã hỏi lúc ${answer.createTime}</span>
							</div>
						</td>
					</tr>
				</tbody>
			</table>`
	 }else{
		 var element = `<table class="mt-3 border-bottom border-secondary">
				<tbody>	
					<tr>
						<td>${answer.content}.</td>
					</tr>
					<tr>
						<td>
							<div>
								<a th:href="@{/profile/{id}(id=${answer.author.userName})}"
									class="font-italic m-1 text-info"> ${answer.author.fullName} </a> 
								<span class="font-italic text-info"> đã hỏi lúc ${answer.createTime}</span>
							</div>
						</td>
					</tr>
				</tbody>
			</table>`
	 }
	
	answer_list.insertAdjacentHTML("beforeend",element)
	$("#content").val("")
}

$(document).ready(function() {
	connect();
	$("#btnReply").click(function() {
		content = $("#content").val()
		var self = this;		
		if (!content||content=='') {
			toastr.error('Nội dung câu hỏi bị trống (hãy nhập nội dung câu hỏi)', 'Dữ liệu nhập không hợp lệ');
			console.log("cntent: ", content)
			return;
		}
		if (content.length<10) {
			toastr.error('Nội dung trả lời quá ngắn(phải từ 10 ký tự)', 'Dữ liệu nhập không hợp lệ');
			return;
		}
		sendMessage();
	})
});