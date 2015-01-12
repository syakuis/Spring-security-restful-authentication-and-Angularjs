<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
	<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Spring security</title>
	
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
	</head>
	
	<body>
	
	<script>
	
	function get_msg(message) {
		var move = '70px';
		jQuery('#message').text(message);
		jQuery('#message').animate({
			top : '+=' + move
		}, 'slow', function() {
			jQuery('#message').delay(1000).animate({ top : '-=' + move }, 'slow');
		});
	}
	
	<c:if test="${error == 'true'}">
	jQuery(function() {
		get_msg("로그인 실패하였습니다.");
	});
	
	</c:if>
	
	function singin() {
		$.ajax({
			url : './signin_ok',
			data: $('form input').serialize(),
			type: 'POST',
			dataType : 'json',
			beforeSend: function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
			}
		}).done(function(body) {
			
			var message = body.response.message;
			var error = body.response.error;
			if (error) get_msg(message);
			
			if (error == false) {
				var url = '${referer}';
				if (url == '') url = '<c:url value="/mypage" />';
				location.href = url;
			}
			
		});
		
	}
	</script>
	
	<div>
	<div id="message" style="width:300px;position:absolute; top:-60px;border: 1px;border-color: #000;"></div>
	</div>
	
	<div style="margin-top:100px;">
	<form id="form" action="./j_spring_security_check" method="post">
	아이디 : <input type="text" id="user_id" name="user_id">
	비밀번호 : <input type="password" id="password" name="password">
	<button type="button" onclick="singin();">Ajax Sign in</button>
	<button type="submit">Submit Sign in</button>
	</form>
	
	</div>
	
	</body>
	
</html>