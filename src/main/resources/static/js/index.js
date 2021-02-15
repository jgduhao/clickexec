
$(function(){
	showAllScriptList();
	$("input[name=scriptType]").change(function(){
		controlContent(this.value);
	});
});

function controlContent(srciptType){
	if(srciptType == 1){
			$("#sContentDiv").show();
			$("#sPathDiv").hide();
			$("#scriptFilePath").val("");
		} else if(srciptType == 2){
			$("#sPathDiv").show();
			$("#sContentDiv").hide();
			$("#scriptContent").val("");
		}
}

function executeScript(scriptId){
	$(".executebtn").attr("disabled",true);
	$.ajax({
		type: "POST",
		dataType: "json",
		contentType: "application/json;charset=UTF-8",
		url: "bashScript/execute?scriptId="+scriptId,
		data: {},
		success: function(data){
			$(".executebtn").attr("disabled",false);
			if(data.status == 'success'){
				showSuccessToast(data.message);
			} else {
				showErrorToast(data.message);
			}
		},
		error: function(){
			$(".executebtn").attr("disabled",false);
			showErrorToast("执行失败");
		}
	});
}

function showAllScriptList(){
	$.ajax({
		type: "POST",
		dataType: "json",
		contentType: "application/json;charset=UTF-8",
		url: "bashScript/getAllList",
		data: {},
		success: function(data){
			if(data.status == 'success'){
				var scriptList = data.rstData;
				if(scriptList == null || scriptList.length <= 0){
					$('#tbBody').html("<tr><td colspan='2'>暂无数据</td></tr>");
				} else {
					var tbHtml = "";
					for(var i=0; i<scriptList.length; i++){
						var scriptObj = scriptList[i];
						tbHtml += getTbLineHtml(scriptObj);
					}
					$('#tbBody').html(tbHtml);
				}
			} else {
				showErrorToast(data.message);
			}
		},
		error: function(){
			showErrorToast("查询失败");
		}
	});
}

function submitScript(){
	//var submitType = $("#scriptFormType").val();
	var sendData = {
		scriptId : $("#scriptId").val(),
		scriptName : $("#scriptName").val(),
		scriptType : $("[name=scriptType]:checked").val(),
		scriptContent : $("#scriptContent").val(),
		scriptFilePath : $("#scriptFilePath").val()
	};
	$.ajax({
		type: "POST",
		dataType: "json",
		contentType: "application/json;charset=UTF-8",
		url: "bashScript/save",
		data: JSON.stringify(sendData),
		success: function(data){
			if(data.status == 'success'){
				closeForm();
				showAllScriptList();
			} else {
				showErrorToast(data.message);
			}
		},
		error: function(){
			showErrorToast("保存失败");
		}
	});
}

function openAddForm(){
	$("#scriptFormModalLabel").html("脚本新增");
	$("#scriptFormType").val("add");
	$("#scriptId").val("");
	$("#scriptName").val("");
	$("#scriptType1").prop("checked",true);
	controlContent(1);
	$("#scriptContent").val("");
	$("#scriptFilePath").val("");
	$('#scriptFormModal').show();
}

function openModiForm(scriptId){
	$.ajax({
		type: "POST",
		dataType: "json",
		contentType: "application/json;charset=UTF-8",
		url: "bashScript/getOne?scriptId="+scriptId,
		data: {},
		success: function(data){
			if(data.status == 'success'){
				var obj = data.rstData;
				$("#scriptFormModalLabel").html("脚本修改");
				$("#scriptFormType").val("modi");
				$("#scriptId").val(obj.scriptId);
				$("#scriptName").val(obj.scriptName);
				$("#scriptType"+obj.scriptType).prop("checked",true);
				controlContent(obj.scriptType);
				$("#scriptContent").val(obj.scriptContent);
				$("#scriptFilePath").val(obj.scriptFilePath);
				$('#scriptFormModal').show();
			} else {
				showErrorToast(data.message);
			}
		},
		error: function(){
			showErrorToast("查询失败");
		}
	});
}

function closeForm(){
	$('#scriptFormModal').hide();
}

function deleteScript(delFile){
	var scriptId = $("#delId").val();
	$.ajax({
		type: "POST",
		dataType: "json",
		contentType: "application/json;charset=UTF-8",
		url: "bashScript/deleteOne?scriptId="+scriptId+"&delFile="+delFile,
		data: {},
		success: function(data){
			if(data.status == 'success'){
				closeQuesModal();
				showSuccessToast("删除成功");
				showAllScriptList();
			} else {
				showErrorToast(data.message);
			}
		},
		error: function(){
			showErrorToast("执行失败");
		}
	});
}

function showQuesModal(scriptId){
	$("#delId").val(scriptId);
	$.ajax({
		type: "POST",
		dataType: "json",
		contentType: "application/json;charset=UTF-8",
		url: "bashScript/getOne?scriptId="+scriptId,
		data: {},
		success: function(data){
			if(data.status == 'success'){
				var obj = data.rstData;
				if(obj.scriptType == 1){
					deleteScript(false);
				} else if(obj.scriptType == 2) {
					$("#quesModal").show();
				}
			} else {
				showErrorToast(data.message);
			}
		},
		error: function(){
			showErrorToast("查询失败");
		}
	});
}

function closeQuesModal(){
	$("#quesModal").hide();
}

function gotoQuesModal(){
	closeDelCheckModal();
	showQuesModal($("#checkDelId").val());
}

function showDelCheckModal(scriptId){
	$("#checkDelId").val(scriptId);
	$("#delCheckModal").show();
}

function closeDelCheckModal(){
	$("#delCheckModal").hide();
}

function openExecModal(scriptId){
	$("#checkExecId").val(scriptId);
	$("#execCheckModal").show();
}

function closeExecModal(){
	$("#execCheckModal").hide();
}

function gotoExecScript(){
	closeExecModal();
	executeScript($("#checkExecId").val());
}

function getTbLineHtml(scriptObj){
	var tbHtml = "";
	tbHtml += '<tr>';
	tbHtml += '<td valign="middle" scope="row">'+scriptObj.scriptName+'</td>';
	tbHtml += '<td>';
	tbHtml += '<button type="button" class="btn btn-success executebtn" onclick="openExecModal('+scriptObj.scriptId+')">执行</button>&nbsp;';
	tbHtml += '<button type="button" class="btn btn-primary" onclick="openModiForm('+scriptObj.scriptId+')">修改</button>&nbsp;';
	tbHtml += '<button type="button" class="btn btn-danger" onclick="showDelCheckModal('+scriptObj.scriptId+')">删除</button>';
	tbHtml += '</td>';
	tbHtml += '</tr>';
	return tbHtml;
}

function showSuccessToast(text,parentDivId,toastId){
	var html = '';
	if(toastId == undefined){
		toastId = "toastSuccess";
	}
	if(parentDivId == undefined){
		parentDivId = "toastDiv";
	}
	html += '<div id="'+toastId+'" class="toast fade show align-items-center text-white bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">';
	html += '<div class="d-flex">';
	html += '<div class="toast-body">';
	html += text;
	html += '</div>';
	html += '<button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close" onclick="closeToast(\''+toastId+'\')"></button>';
	$("#"+parentDivId).html(html);
	setTimeout(function(){closeToast(toastId)},5000);
}

function showErrorToast(text,parentDivId,toastId){
	var html = '';
	if(toastId == undefined){
		toastId = "toastError";
	}
	if(parentDivId == undefined){
		parentDivId = "toastDiv";
	}
	html += '<div id="'+toastId+'" class="toast fade show align-items-center text-white bg-danger border-0" role="alert" aria-live="assertive" aria-atomic="true">';
	html += '<div class="d-flex">';
	html += '<div class="toast-body">';
	html += text;
	html += '</div>';
	html += '<button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close" onclick="closeToast(\''+toastId+'\')"></button>';
	$("#"+parentDivId).html(html);
	setTimeout(function(){closeToast(toastId)},5000);
}

function closeToast(toastId){
	$('#'+toastId).addClass('hide');
	$('#'+toastId).removeClass('show');
}