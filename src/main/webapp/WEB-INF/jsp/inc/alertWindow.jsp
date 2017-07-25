<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
	<div class="am-modal-dialog">
		<!-- <div class="am-modal-hd">Amaze UI</div> -->
		<div class="am-modal-bd" id="alertContent">Hello world！</div>
		<div class="am-modal-footer">
			<span class="am-modal-btn">OK</span>
		</div>
	</div>
</div>

<script type="text/javascript">
	function showAlert(alertTxt){
		$("#alertContent").text(alertTxt);
		
		var $modal = $('#my-alert');
		$modal.modal();
	}
</script>