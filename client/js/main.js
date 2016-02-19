$(document).ready(function(){
	$('.my-btn-register').popover({
		html: true,
		content: function() {						
			return $('#div_register').html();
		}
	});
	
	$('body').on('click', '#btnReloadCaptcha', function (e) {
		$('#imgCaptcha').attr('src', 'http://localhost:8080/get_captcha?' + new Date());
		e.preventDefault();
	});
	
	/* register button handler */
	$('body').on('click', '#btnRegister', function (e) {
		e.preventDefault();
		var request = $.ajax({
			url: 'http://localhost:8080/register',
			method: 'GET',
			crossDomain: true,
		    dataType: 'jsonp',
			data: {
				login: $('#editLogin').val(),
				password: $('#editPassword').val(),
				captcha: $('#editCaptcha').val()
			}	// --- data
		});	// --- ajax
		
		request.done(function(_msg) {
			var code = _msg.code,
				msg = _msg.msg;
			
			/* if no errors */
			if (code === 0) {
				$('#lblRegisterError').text('');
				$('#lblRegisterError').attr('display', 'none');
				
				alert('На указанную Вами почту отправлено письмо с инструкцией по дальнейшей активации вашего аккаунта!');
			}
			/* if an errors*/
			else {
				$('#lblRegisterError').text(msg);
				$('#lblRegisterError').attr('display', 'block');
			}			
		});
		request.fail(function(_jqXHR, _textStatus) {
			alert('Request failed: ' + _textStatus);
		});
	});
	
	/* login button handler */
	$('body').on('click', '#btnLogin', function (e) {
		e.preventDefault();
		var request = $.ajax({
			url: 'http://localhost:8080/login',
			method: 'GET',
			crossDomain: true,
		    dataType: 'jsonp',
			data: {
				login: $('#editLogin').val(),
				password: $('#editPassword').val(),
				captcha: $('#editCaptcha').val()
			}	// --- data
		});	// --- ajax
		
		request.done(function(_msg) {
			var code = _msg.code,
				msg = _msg.msg;
			
			/* if no errors */
			if (code === 0) {
				$('#lblRegisterError').text('');
				$('#lblRegisterError').attr('display', 'none');
				
				alert('Регистрация пройдена!');
			}
			/* if an errors*/
			else {
				$('#lblRegisterError').text(msg);
				$('#lblRegisterError').attr('display', 'block');
			}			
		});
		request.fail(function(_jqXHR, _textStatus) {
			alert('Request failed: ' + _textStatus);
		});
	});
	
	/*------------------------------------------------------------------*/
	$('.my-list-shelves').popover({
		html: true,
		content: function() {
			return $('#list-shelves').html();
		}
	});
	
	$('.my-category-about .btn').popover({
		html: true,
		content: function() {
			return $('#div_about').html();
		}
	});
	
	$('.my-category-read .btn').click(function() {
		window.location = 'read.html';
	});

	/* закрываем по клику вне всплывающего окна...
	$('body').on('click', function (e) {
	    $('[data-placement="bottom"]').each(function () {
	        /*if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
	        	//$(this).popover('hide');
	        	 $(this).siblings('.popover').remove();
	        }
	    });
	});
	*/				
});