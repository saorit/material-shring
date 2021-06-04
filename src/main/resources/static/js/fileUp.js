document.getElementById('FormFile').addEventListener('change', handleFileSelect, false);
function handleFileSelect(e) {
	var area = document.getElementById('file');
	area.insertAdjacentHTML('afterend','<div id="preview"></div>');
	

	var files = e.target.files;

	for (var i = 0, f; f = files[i]; i++) {

		var reader = new FileReader();

		reader.onload = (function(theFile) {
			return function(e) {
				if (theFile.type.match('image.*')) {
					var preview = document.createElement('div');
					preview.classList.add('d-inline-block','mr-1','mt-1');
					preview.innerHTML = ['<img class="img-thumbnail" src="', e.target.result,'" title="', escape(theFile.name), '" style="height:100px;" /><div class="small text-muted text-center">', escape(theFile.name),'</div>'].join('');// 画像では画像のプレビューとファイル名の表示
				} else {
					var preview = document.createElement('div');
					preview.classList.add('d-inline-block','mr-1');
					preview.innerHTML = ['<span class="small">', escape(theFile.name),'</span>'].join('');//画像以外はファイル名のみの表示
				}
				document.getElementById('preview').appendChild(preview);
			};
		})(f);

		reader.readAsDataURL(f);
	}
}

// 取消ボタンでリセット
document.getElementById('btnResetFile').addEventListener('click', function(e) {
	document.getElementById('FormFile').value = '';
	document.getElementById('preview').remove();
}, false);