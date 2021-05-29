	var MouseOnSearchResultUl  //全局变量
$("#downloadsale").on('click',function(){
	        debugger;
	      var ids = [];
			var format=$("#inputGroupSelect04").val();
			var index = ids.indexOf("Campaign ID");
			  if (index > -1) {
			  ids.splice(index, 1);
			}
			var data = {
				"ids": ids,
				"format": format,
			}
			$('input:checkbox:checked').parent().next().map(function (){
			    ids.push($(this).text())
			    return ids;
			})
			if (!ids){
			    swal("请选择一条记录" ,{
				icon:"warning",
				});
			    return
		    }
	  	    $.ajax({
            type: 'POST',//方法类型
            url: '/admin/goodsSale/download',
            contentType: 'application/json',
            data: JSON.stringify(data),
            //data:1,
            
            success: function (result) {
	        //サーバーが成功した場合
                if (result.resultCode == 200) {
	              debugger;
	             // var url = window.location.assign(result.data);
	              Download(result.data);
                } else {
                    	swal(result.message, {
                        icon: "error",
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
function Download(url) {
   document.getElementById('my_iframe').src = url;
};
/*txt*/
/*$("#downloadsaleTxt").on('click',function(){
	        debugger;
	        var ids = [];
	        $('input:checkbox:checked').parent().next().map(function (){
			    ids.push($(this).text())
			    return ids;
			})
			var index = ids.indexOf("Campaign ID");
			  if (index > -1) {
			  ids.splice(index,1);
			}
			if (!ids){
			    swal("请选择一条记录" ,{
				icon:"warning",
				});
			    return
		    }
	  	    $.ajax({
            type: 'POST',//方法类型
            url: '/admin/goodsSale/downloadtxt',
            contentType: 'application/json',
            data: JSON.stringify(ids),
            //data:1,
            
            success: function (result) {
	        //サーバーが成功した場合
                if (result.resultCode == 200) {
	              debugger;
	             // var url = window.location.assign(result.data);
	              Download(result.data);
                } else {
                    	swal(result.message, {
                        icon: "error",
                    });
                }
                
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
             }
         })
  });*/
new AjaxUpload('#col-119', {
        action: '/admin/sale/search',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif|csv)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif、csv格式的文件！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
              swal("uploadがOKです！" ,{
								icon:"success",
							});
            } else {
                alert("error");
            }
        }

 });
    //keyword 2021/05/21 ajax与后台通信，查找查询履历
$( "#keywordSale" ).focus(function(){
	var keyword = $( "#keywordSale" ).val();
	if(keyword != ""){
		$( "#keywordSale" ).trigger("keyup");
	}
});		
//鼠标移开时候删除elements的内容delete elements when focus out
$("#keywordSale").focusout(function(){
	if(MouseOnSearchResultUl)
	return;
    clearResultList()
	//hide #searchResultUl
	$("#searchResultUl").hide();
});
  // 2021/05/21 ajax あいまい検索
$("#keywordSale").keyup(function(){
	debugger;
	var keyword = $("#keywordSale").val();
	    $.ajax({
            type: 'get',//方法类型  //method = "POST"
            url: "/sale/search?name="+keyword,  //Post送信先のurl
            dataType:"json",
            success: function (json_data) {
			debugger;
			clearResultList();
			showResultForLikeSearch(json_data);
			debugger;
	   	    var list = json_data.data.list[0];
		    var str = list.name;
		},
		error: function() {
			debugger;
			alert("Service Error. Pleasy try again later.");
		}
	});
		
});
function clearResultList(){
	$("#salesearchResultUl").children().toArray().forEach(function(value,index,array){
		var incFlag = $(value).attr('class').includes("saledumyLi");
		if(!incFlag){
			$(value).remove();
		}
	})
}
function showResultForLikeSearch(result){
	var list = result.data.list;
	for(var i = 0; i< list.length; i++){
		var el = $(".saledumyLi").clone().removeClass("saledumyLi");
		var link = el.find("a");
		link.text(list[i].name);
		$(".saledumyLi").before(el);
	}
	$("#salesearchResultUl").show();
	appendToSearchBar($("#salesearchResultUl"));
}
function appendToSearchBar(el){
	debugger;
	var searchBar = $("#keywordSale");//jquery object
	var rect = searchBar[0].getBoundingClientRect();//转换成dom加[0]  convert jquery object to dom by searchBar[0]
	console.log(rect.top,rect.right,rect.bottom,rect.left);
	var sbHeight = searchBar.height();
	el.css({top: rect.top + sbHeight,left: rect.left,position:'absolute'});//相对定位relative  绝对定位absolute
	}
$("#salesearchResultUl").mousemove(function(){
	MouseOnSearchResultUl = true;
});
$("#salesearchResultUl").mouseleave(function(){
	MouseOnSearchResultUl = false;
})



// 2021/05/24 toggle
$('#select-all').click(function(event) {   
    if(this.checked) {
        $(':checkbox').each(function() {
            this.checked = true;                        
        });
    } else {
        $(':checkbox').each(function() {
            this.checked = false;                       
        });

  
    }
});
//2021/05/24 modal
$(function(){
	$("#modal-open").click(function(){
		$(".modal").fadeIn();
	});
	$("#cancel").click(function(){
		$(".modal").fadeOut();
	});
});
$("#saveSaleButton").click(function(){	
	var name = $("#campaignSaleName").val();
	var startDate = $("#startDateSale").val();
	var endDate = $("#endDateSale").val();
	var campaign = $("#campaign").val();
	var content1 = $("#content1").val();
	var content2 = $("#content2").val();
	var content3 = $("#content3").val();
	var content4 = $("#content4").val();
	var content5 = $("#content5").val();
    data = {
	  "name":name,
	"startDate":startDate,
	"endDate":endDate,
	"campaign":campaign,
	"content1":content1,
	"content2":content2,
	"content3":content3,
	"content4":content4,
	"content5":content5,
    };	  
    $.ajax({
        type: 'POST',//方法类型
        url: '/goods/insertGoodsSale',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
//サーバーが成功した場合
            if (result.resultCode == 200) {
			debugger;					
					swal("ご登録ありがとうございました！" ,{
						icon:"success",
					});
            } else {
                	swal(result.message, {
                    icon: "error",
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

  
  
  //排序
  $(function(){
  // カラムのクリックイベント
  $("th").click(function(){
    // ★span要素の独自属性（sort）の値を取得
    var sortClass = $(this).find("span").attr("sort");
    var sortFlag = "asc";
    // 初期化
    $("table thead tr span").text("");
    $("table thead tr span").attr("sort", "");

    // 空欄チェック
    if(isBlank(sortClass) || sortClass == "asc") {
      $(this).find("span").text("▼");
      // ★独自属性（sort）の値を変更する
      $(this).find("span").attr("sort", "desc");
      sortFlag = "desc";
    } else if(sortClass == "desc") {
      $(this).find("span").text("▲");
      $(this).find("span").attr("sort", "asc"); 
      sortFlag = "asc";
    }

    var element = $(this).attr("id");
    sort(element, sortFlag);
  });
  /******** 共通関数 ********/
  function sort(element, sortFlag) {
    // ソート
    // ★sort()で前後の要素を比較して並び変える。※対象が文字か数値で処理を変更
    var arr = $("table tbody tr").sort(function(a, b) {
      if ($.isNumeric($(a).find("td").eq(element).text())) {
        // ソート対象が数値の場合
        var a_num = Number($(a).find("td").eq(element).text());
        var b_num = Number($(b).find("td").eq(element).text());
        if(isBlank(sortFlag) || sortFlag == "desc") {
          // 降順
          return b_num - a_num;
        } else {
          // 昇順
          return a_num - b_num;
        }
      } else {
        // ソート対象が数値以外の場合
        var sortNum = 1;
        if($(a).find("td").eq(element).text() 
             > $(b).find("td").eq(element).text()) {
          sortNum = 1;
        } else {
          sortNum = -1;
        }
        if(isBlank(sortFlag) || sortFlag == "desc") {
          // 降順
          sortNum *= (-1) ;
        }
        return sortNum;
      }
    });
    // 表を置き換える  ★html()要素を置き換える
    $("table tbody").html(arr);
  }
  //バリデーションチェック
  function isBlank(data){
    if (data.length ==0 || data == ''){
      return true;
    } else {
      return false;
    }
  }
});
 





//絞り込み検索 改修 2021/05/25
(function(document) {
  'use strict';
  var LightTableFilter = (function(Arr) {
    var _input;
    function _onInputEvent(e) {
      _input = e.target;
      var tables = document.getElementsByClassName(_input.getAttribute('data-table'));
      Arr.forEach.call(tables, function(table) {
        Arr.forEach.call(table.tBodies, function(tbody) {
          Arr.forEach.call(tbody.rows, _filter);
        });
      });
    }
    function _filter(row) {
      var text = row.textContent.toLowerCase(), val = _input.value.toLowerCase();
      row.style.display = text.indexOf(val) === -1 ? 'none' : 'table-row';
    }
    return {
      init: function() {
        var inputs = document.getElementsByClassName('light-table-filter');
        Arr.forEach.call(inputs, function(input) {
          input.oninput = _onInputEvent;
        });
      }
    };
  })(Array.prototype);
  document.addEventListener('readystatechange', function() {
    if (document.readyState === 'complete') {
      LightTableFilter.init();
    }
  });

})(document);
