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


//ajax與後台通訊.查找履歷
$("#keyword").focus(function(){
	var keyword = $("#keyword").text();
	$.ajax({
            type: 'POST',//方法类型            
            url : "/searchHistory/getSearchHistory",
            contentType: 'application/json',
            //data:JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {
	                var list = result.data;
	                for(var i=0; i< list.size(); i++){
		               $(".dumyLi").clone().removeClass("dumyLi");
	                }
	                 
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
});

//暧昧显示
$("#keyword").focusout(function(){
	if(MouseOnSearchResultUl)
	return;
	$("#searchResultUl").children().toArray().forEach(function(value,index,array){
		var incFlag = $(value).attr("class").includes("dumyLi");
		if(!incFlgo){
			$(value).remove();
		}
	})
	$("#searchResultUl").hide();
	//console.log("Handler for .keyup() called.");
});

function showResult(result){
	var list = result.data;
	var _href = "/searchHistory/getSearchHistory";
	for(var i = 0; i< list.size();i++){
		var link = $(".dumyLi").clone().removeClass("dumyLi").find("a");
		link.text(list[i].goodsName);
		link.attr("href",list[i].goodsId);
		
	}
	$("searchResultUl").show();
	addendToSearchBar($("#searchResultUl"));
}
function addendToSearchBar(el){
	var searchBar = $("keyword");
	//var searchBar = document.getElementById("keyword");
	var rect = searchBar[0].getBoundingClientRect();
	console.log(rect.top,rect.right,rect.bottom,rect.left);
	/*el[0].style.height = rect.top + sbHeight;
	el[0].style.height = rect.left;*/
	el.css({top: rect.top + sbHeight, left: rect.left,position:"absolute"});
}
function checkIfMousOver(){
	var rect = document.getElementById("searchResultUl").getBoundingClientRect();
}
$("#searchResultUl").mousemove(function(){
	MouseOnSearchResultUl = true;
	debugger;
});
$("#searchResultUl").mousemove(function(){
	MouseOnSearchResultUl = false;
	
});
