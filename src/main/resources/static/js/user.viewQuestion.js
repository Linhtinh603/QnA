var quillContent = new Quill('#editor', {
	modules: {
		toolbar: false
	},
	placeholder: 'Nhập nội dung câu hỏi ở đây...',
	theme: 'bubble'
});
quillContent.setContents(originContent)
quillContent.disable()

$(document).ready(function () {
	$("#btnDelete").click(function () {
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			type: 'DELETE',
			url: "/questions/delete?" + $.param({ "id": questionId }),
			beforeSend: function () {
				self.disabled = true;
			},
			statusCode: {
				302: function () {
					toastr.success('Hết phiên đăng nhập');
					setTimeout(function () {
						window.location.reload()
					}, 1000)
				},
				400: function () {
					toastr.error('Hãy thử lại sau', 'Có lỗi xảy ra!');
				},
				200: function () {
					toastr.success('Xóa câu hỏi thành công')
					setTimeout(function () {
						window.location = '/my_profile'
					}, 1000)
				},
			},
			complete: function () {
				self.disabled = false;
			}
		});
	});

	$("#btnFollow").click(function () {
		if (following) {
			$("#unfollow-question-modal").modal()
			$("#btnUnfollow").click(function () {
				$.ajax({
					contentType: 'application/json; charset=utf-8',
					type: 'POST',
					url: "/questions/unfollow?" + $.param({ "id": questionId }),
					beforeSend: function () {
						self.disabled = true;
					},
					statusCode: {
						302: function () {
							toastr.success('Hết phiên đăng nhập');
							setTimeout(function () {
								window.location.reload()
							}, 1000)
						},
						400: function () {
							toastr.error('Hãy thử lại sau', 'Có lỗi xảy ra!');
						},
						200: function () {
							toastr.success('Hãy kiểm tra lại ở trong hồ sơ cá nhân', 'Bỏ theo dõi câu hỏi thành công')
							setTimeout(function () {
								window.location.reload()
							}, 1000)
						},
					},
					complete: function () {
						self.disabled = false;
					}
				});
			})
		} else {
			$.ajax({
				contentType: 'application/json; charset=utf-8',
				type: 'POST',
				url: "/questions/follow?" + $.param({ "id": questionId }),
				beforeSend: function () {
					self.disabled = true;
				},
				statusCode: {
					302: function () {
						toastr.success('Hết phiên đăng nhập');
						setTimeout(function () {
							window.location.reload()
						}, 1000)
					},
					400: function () {
						toastr.error('Hãy thử lại sau', 'Có lỗi xảy ra!');
					},
					200: function () {
						toastr.success('Hãy kiểm tra lại ở trong hồ sơ cá nhân', 'Theo dõi câu hỏi thành công')
						setTimeout(function () {
							window.location.reload()
						}, 1000)
					},
				},
				complete: function () {
					self.disabled = false;
				}
			});
		}
	});

	$(".answer-tick").click(function () {
		$("#tick-answer-modal").modal()
		var answerId = $(this).attr("id")
		$("#btnTick").attr("data-answer", answerId);
	})

	$("#btnTick").click(function () {
		var answerId = $(this).attr("data-answer")
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			type: 'PUT',
			url: `/questions/${questionId}/right-answer?` + $.param({ "rightAnswerId": answerId }),
			beforeSend: function () {
				self.disabled = true;
			},
			statusCode: {
				302: function () {
					toastr.success('Hết phiên đăng nhập');
					setTimeout(function () {
						window.location.reload()
					}, 1000)
				},
				400: function () {
					toastr.error('Hãy thử lại sau', 'Có lỗi xảy ra!');
				},
				200: function () {
					toastr.success('Câu trả lời bạn vừa chọn đã được đưa lên đầu tiên', 'Chọn câu trả lời đúng thành công')
					setTimeout(function () {
						window.location.reload()
					}, 1000)
				},
			},
			complete: function () {
				self.disabled = false;
				$("#tick-answer-modal").modal('hide')
			}
		});
	})

	$("#btnCancleRightAnswer").click(function () {
		$("#untick-answer-modal").modal()
	})

	$("#btnCancleRightQ").click(function () {
		var answerId = $(this).attr("data-answer")
		$.ajax({
			contentType: 'application/json; charset=utf-8',
			type: 'PUT',
			url: `/questions/${questionId}/cancle-right-answer`,
			beforeSend: function () {
				self.disabled = true;
			},
			statusCode: {
				302: function () {
					toastr.success('Hết phiên đăng nhập');
					setTimeout(function () {
						window.location.reload()
					}, 1000)
				},
				400: function () {
					toastr.error('Hãy thử lại sau', 'Có lỗi xảy ra!');
				},
				200: function () {
					toastr.success('Bạn đã vừa hủy câu trả lời đúng cho câu hỏi này', 'Hủy câu trả lời đúng thành công')
					setTimeout(function () {
						window.location.reload()
					}, 1000)

				},
			},
			complete: function () {
				self.disabled = false;
				$("#untick-answer-modal").modal('hide')
			}
		});
	})

});