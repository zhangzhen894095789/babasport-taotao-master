<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/back_page/head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>babasport-edit</title>
<script type="text/javascript">
function uploadPic(){
	//jquery.form.js
	var options = {
			url : "/upload/uploadPic.do",
			type : "post",
			dataType : "json",
			success : function(data){
				//执行回调的程序 
				//img src = data.path 
				$("#imgSize1ImgSrc").attr("src",data.url);
				$("#path").val(data.path);
			}
	}
	$("#jvForm").ajaxSubmit(options);
}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 商品管理 - 修改</div>
	<form class="ropt">
		<input type="submit" onclick="this.form.action='v_list.shtml';" value="返回列表" class="return-button"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box" style="float:right">
	<form id="jvForm" action="/product/edit.do" method="post">
		<input type="hidden" value="${product.id}" name="id"/>
		<table cellspacing="1" cellpadding="2" width="100%" border="0" class="pn-ftable">
			<tbody>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						<span class="pn-frequired">*</span>
						商品名称:</td><td width="80%" class="pn-fcontent">
						<input type="text" class="required" name="name" value="${product.name}" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						<span class="pn-frequired">*</span>
						上传商品图片(90x150尺寸):</td>
						<td width="80%" class="pn-fcontent">
						注:该尺寸图片必须为90x150。
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h"></td>
						<td width="80%" class="pn-fcontent">
						<img width="100" height="100" id="imgSize1ImgSrc" src="${product.img.allUrl }"/>
						<input  type="hidden" id="path" name="img.url" value="${product.img.url }"/>
						<input type="file" onchange="uploadPic()" name="pic"/>
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						商品描述:</td><td width="80%" class="pn-fcontent">
						<input type="text" class="required" name="description" value="${product.description}" maxlength="80"  size="60"/>
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						是否上架:</td><td width="80%" class="pn-fcontent">
						<input type="radio" name="isShow" value="1" <c:if test="${product.isShow==true}">checked="checked"</c:if> />上架
						<input type="radio" name="isShow" value="0" <c:if test="${product.isShow==false}">checked="checked"</c:if>/>下架
					</td>
				</tr>
			</tbody>
			<tbody>
				<tr>
					<td class="pn-fbutton" colspan="2">
						<input type="submit" class="submit" value="提交"/> &nbsp; <input type="reset" class="reset" value="重置"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
</body>
</html>