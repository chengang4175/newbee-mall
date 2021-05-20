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

	        var _data = [1,2,3,4]
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
        action: '/admin/uploadtest/file',
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
 
 
