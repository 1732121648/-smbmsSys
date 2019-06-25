
var roleName = null;
var roleCode = null;
var saveBtn = null;
var backBtn = null;

$(function(){

	roleCode = $("#roleCode");
	roleName = $("#roleName");
	saveBtn = $("#save");
	backBtn = $("#back");

	roleCode.next().html("*");
	roleName.next().html("*");


	roleName.on("focus",function(){
		validateTip(roleName.next(),{"color":"#666666"},"* 请输入角色名称",false);
	}).on("blur",function(){
		if(roleName.val() != null && roleName.val() != ""){
			validateTip(roleName.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(roleName.next(),{"color":"red"},imgNo+" 角色名称不能为空，请重新输入",false);
		}
	});

	roleCode.on("focus",function(){
		validateTip(roleCode.next(),{"color":"#666666"},"* 请输入角色编码",false);
	}).on("blur",function(){
		if(roleCode.val() != null && roleCode.val() != ""){
			validateTip(roleCode.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(roleCode.next(),{"color":"red"},imgNo+"角色编码不能为空，请重新输入",false);
		}

	});
	
	saveBtn.on("click",function(){
		roleName.blur();
		roleName.blur();
		if(roleName.attr("validateStatus") == "true"
			&& roleName.attr("validateStatus") == "true"){
			if(confirm("是否确认要提交数据？")){
				$("#userForm").submit();
			}
		}
	});
	
	backBtn.on("click",function(){
		//alert("modify: "+referer);
		if(referer != undefined 
			&& null != referer 
			&& "" != referer
			&& "null" != referer
			&& referer.length > 4){
		 window.location.href = referer;
		}else{
			history.back(-1);
		}
	});
});