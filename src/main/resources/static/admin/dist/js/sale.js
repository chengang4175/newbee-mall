$(function(){
   //disable previous page 	  
  $(".previousPageSale").css("pointer-events", "none").css("color","grey");
  });   
 $("#zv-cqa-select-sort").change(function(){
    paging(2);
  });     
   //下一页
 $( ".nextPageSale" ).click(function(){
	  debugger;
	  paging(0);
	  $(".previousPageSale").css("pointer-events", "auto").css("color","#009e96");
 });
	   //上一页
 $( ".previousPageSale" ).click(function(){
	  paging(1);
 });	
	debugger; 
	//fenye  
function paging(num){
	//alert("Handlerfor .click() called." );   

    var page = $("#currentPageNoN").text();
    var pageNo = 0;
    console.log("current page: ",page);
	var url = "admin//goods/sale";
	if(num == 0){
		//下页
		 pageNo = parseInt(page) + 1;
	}else if (num == 1){
		//上页
		 pageNo = parseInt(page) - 1;
	}else{
		 pageNo = 1 
	}
	   var data = {
		"page":pageNo
	           };	    
        $.ajax({
            type: 'POST',//方法类型
            url: url,
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
	//サーバーが成功した場合
                if (result.resultCode == 200) {
	                    var el;
	                    if(result.data.list.length > 0){

							$(".campaignTable").find(".delete").remove();
					  	}
				        var ar = result.data.list;

					    debugger;
	                       for(let i = 0; i <ar.length;i++){
	                   el = $(".hiddenSaleDiv").clone().removeClass("hiddenSaleDiv");
	                   el.find(".zv-cqa-q-id").html(result.data.list[i].id);
	                   el.find(".zv-cqa-q-name").html(result.data.list[i].name);
	                   el.find(".zv-cqa-q-startDate").html(result.data.list[i].startDate);
	                   el.find(".zv-cqa-q-endDate").html(result.data.list[i].endDate);
	                   $("#dateilFooter"),before(el);
	               }
                } else {                  	
                    swal(result.message, {
                        icon: "error",
                    });
                }
                ;
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });
}
		

/*download*/
$("#downloadGoodsSale").on('click',function(){
   debugger;
   var ids = [];
   $('input:checkbox:checked').parent().next().map(function (){
	 ids.push($(this).text())
	 return ids;
})
   if(ids == null){
	swal("请选择一条记录",{
		icon:"warning",
	});
	return
}
	  $.ajax({
          type: 'POST',//方法类型
          url: '/admin/goodsSale/download',
          contentType: 'application/json',
          data: JSON.stringify(_data),
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

/*文件上传*/

    //图片上传插件初始化 用于商品预览图上传
     new AjaxUpload('#testUploadSale', {
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
                $("#goodsCoverImg").attr("src", r.data);
                $("#goodsCoverImg").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
    });
    
    
 //暧昧查询
  var MouseOnSearchResultUl 

//ajax暧昧検索
$("#searchForCampaign").keyup(function(){
	debugger;
	var keyword = $("#searchForCampaign").val();
	   
	    $.ajax({
            type: 'get',//方法类型  //method = "POST"
            url: "/sale/search?name="+keyword,  //Post送信先のurl
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
             var list = json_data.data.list[0];
             var str = list.name;
             var arr = str.split(" ");
             arr.filter(keyword => keyword.includes(keyword));  

             
             
           },
		error: function() {
			debugger;
			alert("Service Error. Pleasy try again later.")
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
	$("#datequxiao").click(function(){
		$(".modal").fadeOut();
	});
});
