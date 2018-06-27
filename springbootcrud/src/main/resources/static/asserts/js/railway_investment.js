var systemName = 'railway'
var userName = $("#hiddenDivUserName").html()
console.log('userName: ' + userName)

function postMessage(content){
	var obj = {
		systemName: systemName,
		userName: userName,
		content: content
	}
	console.log(obj)
	$.post({
		url: '/postMessage',
		data: {
			systemName: systemName,
			userName: userName,
			content: content,
			type: 1001
		},
		success: function(res){
			console.log('postMessage success...')
			console.log(res)
		}
	})
}

function transactA(){
	postMessage("办理A事项")
}

