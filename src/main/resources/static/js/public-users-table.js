let appendHtml = '<td>' +
		'<input type="text" class="form-control" id="publicUser0" name="publicUser">' +
	'</td>' +
	'<td>' +
		'<i class="fas fa-trash-alt mr-1 public-user-remove"></i>' +
	'</td>';
$("#public-user-add").click(function() {
	$("#public-users-table").append(
		$("<tr></tr>")
			.append($(appendHtml))
		);
});

$('body').on('click', '.public-user-remove' , function() {
	$(this).closest("tr").remove();
});

$('#publicPreference').on('change', function() {
	var selectValue = $(this).val();
	if (selectValue == 'PUBLIC') {
		$('#public-users-area').hide();
	} else {
		$('#public-users-area').show();
	}
});
$(function(){
	$('#publicPreference').change();
});