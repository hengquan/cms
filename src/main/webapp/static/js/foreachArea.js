			//北京市二级联动
			var provinceEle = document.getElementById("province");
			var cityEle = document.getElementById("city");
			var areaEle = document.getElementById("area");
			function insertData(children) {
				if(Object.prototype.toString.call(children) == "[object Array]") { //是数组 则不是根节点
					for(var child in children) {
						//console.log(children[child]);//获取的所有对象
						var optionNode = document.createElement("option"); //获取各个option
						var textNode = document.createTextNode(children[child].name); //获取各个名称
						optionNode.appendChild(textNode); //将名称填到option里边
						for(var attribute in children[child].attributes) {
							optionNode.setAttribute(attribute, children[child].attributes[attribute]);
						}
						if(children[child].id.length == 6) {
							cityEle.appendChild(optionNode);
							optionNode.style.display = "block";
						} else {
							areaEle.appendChild(optionNode);
							optionNode.style.display = "none";
						}
						if(children[child].isParent == "true") {
							insertData(children[child].children);
						}
					}
				} else {
					var optionNode = document.createElement("option");
					var textNode = document.createTextNode(children.name);
					optionNode.appendChild(textNode);
					for(var attribute in children.attributes) {
						optionNode.setAttribute(attribute, children.attributes[attribute]);
					}
					/*provinceEle.appendChild(optionNode);*/
					insertData(children.children);
				}

			}
			cityEle.onchange = function() {
				var parentNode = this;
				var index = parentNode.selectedIndex;
				var parentId = parentNode[index].id;
				var areaChildren = areaEle.children;
				for(var i = 0, len = areaChildren.length; i < len; i++) {
					if(areaChildren[i].getAttribute("parentId") == parentId) {
						areaChildren[i].style.display = "block";
					} else {
						areaChildren[i].style.display = "none";
					}
				}
			};
			areaEle.onchange = function() {
				if(cityEle.value == "-1") {
					alert("请输入区县");

				}
				var parentNode = this;
				var index = parentNode.selectedIndex;
				var selectedId = parentNode[index].id;
				console.log(selectedId);
			};
			insertData(localArea.data);
			//省市区三级联动
			var provinceEle1 = document.getElementById("province1");
			var cityEle1 = document.getElementById("city1");
			var areaEle1 = document.getElementById("area1");
			function insertData1(children) {
				if(Object.prototype.toString.call(children) == "[object Array]") { //是数组 则不是根节点
					for(var child in children) {
						var optionNode1 = document.createElement("option");
						var textNode1 = document.createTextNode(children[child].name);
						optionNode1.appendChild(textNode1);
						optionNode1.style.display = "none";
						for(var attribute in children[child].attributes) {
							optionNode1.setAttribute(attribute, children[child].attributes[attribute]);
						}
						if(children[child]["attributes"].parentId == "001" && children[child].id.substr(-2) === "00") { //一级
							provinceEle1.appendChild(optionNode1);
							optionNode1.style.display = "block";
						} else if(children[child]["attributes"].parentId != "001" && children[child].id.substr(-2) === "00") {
							cityEle1.appendChild(optionNode1);
							optionNode1.style.display = "block";
						} else if(children[child]["attributes"].parentId != "001" && children[child].id.substr(-2) != "00") {
							areaEle1.appendChild(optionNode1);
							optionNode1.style.display = "block";
						} else {
							console.log("missed");
							console.log(children[child]);
						}
						if(children[child].isParent == "true") {
							insertData1(children[child].children);
						}
					}
				} else {
					insertData1(children.children);
				}
			}
			provinceEle1.onchange = function() {
				var parentNode = this;
				var index = parentNode.selectedIndex;
				var parentId = parentNode[index].id;
				var cityChildren = cityEle1.children;
				var areaChildren = areaEle1.children;

				for(var i = 0, len = cityChildren.length; i < len; i++) {
					if(cityChildren[i].getAttribute("parentId") == parentId) {
						cityChildren[i].style.display = "block";
					} else {
						cityChildren[i].style.display = "none";
					}
				}
				for(var i = 0, len = areaChildren.length; i < len; i++) {
					if(areaChildren[i].getAttribute("parentId") == parentId) {
				
						areaChildren[i].style.display = "block";
					} else {
						areaChildren[i].style.display = "none";
					}
				}
			};
			cityEle1.onchange = function() {
				if(provinceEle1.value == "-1") {
					alert("请输入省份");

				}
				var parentNode = this;
				var index = parentNode.selectedIndex;
				var parentId = parentNode[index].id;
				var areaChildren = areaEle1.children;
				for(var i = 0, len = areaChildren.length; i < len; i++) {
					if(areaChildren[i].getAttribute("parentId") == parentId) {
						areaChildren[i].style.display = "block";
					} else {
						areaChildren[i].style.display = "none";
					}
				}
			};
			areaEle1.onchange = function() {
				if(provinceEle1.value == "-1") {
					alert("请输入省份");
				} else if(cityEle1.value == "-1") {
					alert("请输入区县");
				}
				var parentNode = this;
				var index = parentNode.selectedIndex;
				var selectedId = parentNode[index].id;
				console.log(selectedId);
			};
			insertData1(allArea.data);