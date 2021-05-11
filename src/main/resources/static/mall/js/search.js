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


//ajax与后台通信，查找查询履歴
$( "#keyword" ).focus(function() {
	/*var keyword = $("#keyword").val();
	if(keyword !=""){
		$("keyword").trigger("keyup");
	}*/
	$.ajax({
            type: 'POST',//方法类型
            url: '/searchHistory/getSearchHistory',
            contentType: 'application/json',
            //data: JSON.stringify(keyword),
            success: function (result) {
			//サーバーが成功の場合ここ呼ばれる
                if (result.resultCode == 200) {
					
					showResult(result);
					
                } else {
                    swal(result.message, {
                        icon: "error",
                    });
                };
            },
			//エラーの場合、以下呼ばれる
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });
});
$( "#keyword").focusout(function() {
	
	//return means jump out of this function. end.
	if("MouseOnSearchResultUl"){
		return;
	}
	
	$("#searchResultUl").children().toArray().forEach(function(value, index, array){
		var incFlag = $(value).attr('class').includes("dumyLi");
		if(!incFlag){
			$(value).remove();
		}
		
	})
	$("#searchResultUl").hide();
	
});
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
function appendToSearchBar(el){
	
	var searchBar = $("#keyword"); //jquery object
	var rect = searchBar[0].getBoundingClientRect(); //convert jquery object to dom by searchBar[0]
	console.log(rect.top, rect.right, rect.bottom, rect.left);
	var sbHeight = searchBar.height();
	
	el.css({top: rect.top + sbHeight, left: rect.left, position:'absolute'});
}
$( "#searchResultUl" ).mousemove(function() {
	MouseOnSearchResultUl = true;
	
});
$( "#searchResultUl" ).mouseleave(function(){
	
	MouseOnSearchResultUl = false;
});
$("#keyword").keyup(function(){
	debugger;
	var keyword = $("#keyword").val();
	$.ajax({
		/*type: 'POST',//方法类型
            url: '/goods/search',
            contentType: 'application/json',
            //data: JSON.stringify(keyword),
			data: keyword,
            success: function (result) {
			//サーバーが成功の場合ここ呼ばれる
                if (result.resultCode == 200) {
					debugger;
					console.log(result);

                } else {
                    swal(result.message, {
                        icon: "error",
                    });
                };
            },
			//エラーの場合、以下呼ばれる
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });*/
		
		type:"get",                // method = "POST"
        url:"/goods/search?goodsName="+keyword,        // POST送信先のURL
        //data: keyword,  // JSONデータ本体
        //contentType: 'application/json', // リクエストの Content-Type
        dataType: "json",           // レスポンスをJSONとしてパースする
        success: function(json_data) {   // 200 OK時
			debugger;
           	console.log(json_data);

        },
        error: function() {         // HTTPエラー時
			debugger;
            alert("Server Error. Pleasy try again later.");
        }
    });

}); 
	
	
	
