		//北京市二级联动
			var provinceEle = document.getElementById("province");
			var cityEle = document.getElementById("city");
			console.log(cityEle);
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

			function insertData1 (children){
				//判断是否是根目录 根目录的data对应的值是对象不是数组  其他的都是children对应的数组
				if(Object.prototype.toString.call(children) == "[object Array]"){//是数组 则不是根节点
					//遍历元素渲染省份
					for(var child in children){
						var optionNode1 = document.createElement("option");
						var textNode1 = document.createTextNode(children[child].name);
						optionNode1.appendChild(textNode1);
						//为每个ioption添加自定义属性
						for(var attribute in children[child].attributes){
							optionNode1.setAttribute(attribute,children[child].attributes[attribute]);
						}
						//添加元素到文档
						provinceEle1.appendChild(optionNode1);
					}
				}else{//加载的是根目录 返回回调子级
					insertData1(children.children);
				}
			}
			provinceEle1.onchange = function(){
				var parentNode = this;
				//获取选中的option的id

				var index = parentNode.selectedIndex;
				var parentId = parentNode[index].id;

				var provinceData = allArea.data.children;

				//移除市区级 除了第一个option外的所有子的节点
				/*var childs = cityEle.childNodes;
				for(var i = 1; i < childs.length; i++) {
				  cityEle.removeChild(childs[i]);
				}*/

				//清除上次选择添加的option
				cityEle1.options.length=0;
				areaEle1.options.length=0;

				//添加该省份所有的市区
				for(var province in provinceData){
					if(provinceData[province].id == parentId){//找到选中的省
						var cities = provinceData[province].children;
						for(var city in cities){//遍历添加所有的市区
							var optionNode1 = document.createElement("option");
							var textNode1 = document.createTextNode(cities[city].name);
							optionNode1.appendChild(textNode1);
							for(var attribute in cities[city].attributes){
								optionNode1.setAttribute(attribute,cities[city].attributes[attribute]);
							}
							cityEle1.appendChild(optionNode1);
						}
					break;
					}
				}
			};

			cityEle1.onchange = function(){
				if(provinceEle1.value == "-1"){
					alert("请输入省份");
				}
				var parentNode = this;
				var index = parentNode.selectedIndex;
				var parentId = parentNode[index].id;

				var provinceId = parentNode[index].getAttribute("parentId");

				var provinceData = allArea.data.children;

				areaEle1.options.length=0;

				//添加该省份应有的子节点
				for(var province in provinceData){
					if(provinceData[province].id == provinceId){//找到选中的省
						var cities = provinceData[province].children;
						for(var city in cities){
							if(cities[city].id == parentId){//找到选中的市区
								var areas = cities[city].children;
								for(var area in areas){//遍历添加所有的县
									var optionNode1 = document.createElement("option");
									var textNode1 = document.createTextNode(areas[area].name);
									optionNode1.appendChild(textNode1);
									for(var attribute in areas[area].attributes){
										optionNode1.setAttribute(attribute,areas[area].attributes[attribute]);
									}
									areaEle1.appendChild(optionNode1);
								}
							break;
							}
						}
						break;
					}
				}
			};
			areaEle1.onchange = function(){
				if(provinceEle1.value == "-1"){
					alert("请输入省份");
				}else if(cityEle1.value == "-1"){
					alert("请输入区县");
				}
				var parentNode = this;
				var index = parentNode.selectedIndex;
				var selectedId = parentNode[index].id;
				console.log(selectedId);
			};
			insertData1(allArea.data);