$(document).ready(function(){
	$('.my-btn-register').popover({
		html: true,
		content: function() {						
			return $('#div_register').html();
		}
	});
	
	$('body').on('click', '#btnReloadCaptcha', function (e) {
		$('#imgCaptcha').attr('src', 'http://82.209.91.137:8082/get_captcha?' + new Date());
		e.preventDefault();
	});
	
	/* register button handler */
	$('body').on('click', '#btnRegister', function (e) {
		e.preventDefault();
		var request = $.ajax({
			url: 'http://82.209.91.137:8082/register',
			method: 'POST',
			crossDomain: true,
		    //dataType: 'jsonp',
			xhrFields: {
				withCredentials: true
		    },
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
			url: 'http://82.209.91.137:8082/login',
			method: 'POST',
			crossDomain: true,
		    //dataType: 'jsonp',
			xhrFields: {
				withCredentials: true
		    },
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
	
	/* load shelves list */
	var request = $.ajax({
		url: 'http://82.209.91.137:8082/getshelves',
		method: 'POST',
		crossDomain: true,
	    //dataType: 'jsonp',
		xhrFields: {
			withCredentials: true
	    },
		data: {}	// --- data
	});	// --- ajax
	
	request.done(function(_data) {
		$('#list-shelves').remove();
		$('body').append('<div id="list-shelves" class="hide"></div>');
		
		var iLast = 0;
		for (var i = 0; i < _data.length; i++) {
			if (i%10 === 0) {
				iLast = i;
				$('#list-shelves').append('<div id="div' + iLast + '" style="float:left;"><div>');
				$('#div' + iLast).append('<ul class="nav nav-pills nav-stacked"></ul>');
			}
			$('#div' + iLast + ' ul').append('<li title="' + _data[i].description + '"><a href="#">' + _data[i].name + '</a></li>');
		}	// --- for			
		$('#list-shelves').append('<div style="clear:both;"></div>');
	});
	request.fail(function(_jqXHR, _textStatus) {
		alert('Request failed: ' + _textStatus);
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