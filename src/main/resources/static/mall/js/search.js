$(function () {
    $('#keyword').keypress(function (e) {
        var key = e.which; //e.which是按键的值
        if (key == 13) {
            var q = $(this).val();
            if (q && q != '') {
                window.location.href = '/search?keyword=' + q;
            }
        }
    });
});

function search() {
    var q = $('#keyword').val();
    if (q && q != '') {
        window.location.href = '/search?keyword=' + q;
    }
}


//ajax与后台通信，查找查询履历
$( "#keyword" ).focus(function(){//focus：使元素集中于焦点。设置焦点
	var keyword = $( "#keyword" ).val();//val是将由数字符号（包括正负号、小数点）组成的字符型数据转换成相应的数值型数据的函数。
	if(keyword != ""){
		$( "#keyword" ).trigger("keyup");//trigger：触发器
	}
	//console.log("focused");
		    $.ajax({
            type: 'POST',//方法类型
            url: '/searchHistory/getSearchHistory',
            contentType: 'application/json',//决定浏览器将用什么形式读取这个文件。application/json：数据以json形式进行编码。
            //data: JSON.stringify(keyword),
            success: function (result) {//success方法是一个回调函数，获取从后台传来的数据，其result参数是一个function类型
	//サーバーが成功した場合
                if (result.resultCode == 200) {//Code == 200是HTTP状态码，表示网络请求成功的意思，返回这个状态表示已经获取到数据了
				debugger;					
					showResult(result);
                } else {
                    	swal(result.message, {
                        icon: "error",//icon是一个图标
                    });
                }
                
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
             }
         })
});		
//鼠标移开时候删除elements的内容
$("#keyword").focusout(function(){//focusout：当元素失去焦点时被触发
	if(MouseOnSearchResultUl)
	return;
    clearResultList()//清空列表
	//hide #searchResultUl
	$("#searchResultUl").hide();
})
//ajax 暧昧検索
$("#keyword").keyup(function(){
	debugger;
	var keyword = $("#keyword").val();
	   
	    $.ajax({
            type: 'get',//方法类型  //method = "POST"
            url: "/goods/search?goodsName="+keyword,  //Post送信先のurl
            dataType:"json",
            success: function (json_data) {
			debugger;
			clearResultList();
			showResultForLikeSearch(json_data);
			 //1.提取第一行数据 1-1直接去页面取数据。
            //2.提取keyword 结果入力ip
            //apple iPhone 11 => 截取结果keyword：iPhone
            //insert ajax
            debugger;
            //这个方法就是说从指定位置往后找返回字符在该字符串中第一次出现处的索引，比如“woaizhongguo”indexOf（'o',2）那返回值就是6而不是1
              var list =json_data.data.list[0];
              var str = list.goodsName;
              var keywordIndex = str.indexOf(keyword);
              var startIndex;
              var endIndex;
              var temp = [];
              var spaceIndex = str.indexOf(' ');
              while(spaceIndex>-1){
                temp.push(spaceIndex);
                spaceIndex = str.indexOf(' ',spaceIndex+1);
              }
              for(var i = 0;i<temp.length;i++){
                 while(temp[i]<keywordIndex<temp[i+1]){
                  startIndex=temp[i];
                  endIndex=temp[i+1];
                  i++
                 }
               }
              var key = list.goodsName.substring(startIndex,endIndex);
          },
		error: function() {
			debugger;
			alert("Service Error. Pleasy try again later.")
		}
	  });
		
});
function clearResultList(){
	$("#searchResultUl").children().toArray().forEach(function(value,index,array){
		var incFlag = $(value).attr('class').includes("dumyLi");
		if(!incFlag){
			$(value).remove();
		}
	})
}
function showResult(result){
	var list = result.data;
	var _href = "/goods/detail/";
	for(var i = 0; i< list.length; i++){
		var el = $(".dumyLi").clone().removeClass("dumyLi");
		var link = el.find("a");
		link.text(list[i].goodsName);
		link.attr("href", _href + list[i].goodsId);
		$(".dumyLi").before(el);
	}
	$("#searchResultUl").show();
	appendToSearchBar($("#searchResultUl"));
}
function showResultForLikeSearch(result){
	var list = result.data.list;
	var _href = "/goods/detail/";
	for(var i = 0; i< list.length; i++){
		var el = $(".dumyLi").clone().removeClass("dumyLi");
		var link = el.find("a");
		link.text(list[i].goodsName);
		link.attr("href", _href + list[i].goodsId);
		$(".dumyLi").before(el);
	}
	$("#searchResultUl").show();
	appendToSearchBar($("#searchResultUl"));
}
function appendToSearchBar(el){
	debugger;
	var searchBar = $("#keyword");//jquery object
	var rect = searchBar[0].getBoundingClientRect();//转换成dom加[0]  convert jquery object to dom by searchBar[0]
	console.log(rect.top,rect.right,rect.bottom,rect.left);
	var sbHeight = searchBar.height();
	el.css({top: rect.top + sbHeight,left: rect.left,position:'absolute'});//相对定位relative  绝对定位absolute
	}
$("#searchResultUl").mousemove(function(){
/*	var msg = "Handler for .mousemove() called at ";
	msg += event.pageX + "," + event.pageY;
	$("#log"),append("<div>"+ msg + "</div>");*/
	MouseOnSearchResultUl = true;
});
$("#searchResultUl").mouseleave(function(){
	MouseOnSearchResultUl = false;
})
//
/*insert*/       
   /* $("#keywordButton").click(function(){
		debugger;
		var keyword = $("#keyword").val();
		var id = getId();
		debugger;
		data = {
			"keyword":keyword,
			
	    };
		
	  $.ajax({
            type: 'POST',            
            url : "/goods/search?goodsName="+keyword",
            contentType: 'application/json',
            data:JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {  
	                swal("null",{  
		                incon:"success"
		             });
                } else {                  	
                    swal(result.message, {
                        icon: "error",
                    });
                };
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });
});*/
function keywordInsert(key){
	var data = {
		"keyword":keyword,
	 };
	 $.ajax({
            type: 'POST',            
            url : "/goods/insertKeyword",
            contentType: 'application/json',
            data:JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {  
	                swal("chengong",{  
		                incon:"success"
		             });
                } else {                  	
                    swal(result.message, {
                        icon: "error",
                    });
                };
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });
}
	
