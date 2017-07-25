
		function doAdd() {
			$("#editValue").val('');
			$('#myForm').submit();
		}
		
		function doEdit(id) {
			$("#editValue").val(id);
			$('#myForm').submit();
		}
		
		function submitData(){
			$('#ImgForm').submit();
		}

		function checkbox() {
			var str = document.getElementsByName("box");
			var objarray = str.length;
			var chestr = "";
			var jy = false;
			for (i = 0; i < objarray; i++) {
				if (str[i].checked == true) {
					jy = true;
					chestr += str[i].value + ",";
				}
			}
			if (!jy) {
				windowShow("请选择要删除的数据！")
				$('#quxiao').click();
				return false;
			} else {
				$("#boxeditId").val(chestr);
				return true;
			}
		}

		function doDelete() {
			var flag=checkbox();
			if(flag){
			  var $modal = $('#myModal2');
			  $modal.modal();
			}
		}

		function doDel(id) {
			var $modal = $('#myModal1');
			$("#byid").val(id);
			$modal.modal();
		}

		function Del() {
			$("#deleForm").submit();
		}

		function Delete() {
				$("#deleForm").submit();
		}
		