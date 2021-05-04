     /*$("#zv-cqa-select-sort").change(function(){
	var page = $("#currentPageNo").text();
	var url = "/goods/qaSort";
	data = {
		"page": page
	};
	debugger;*/
    $(function(){
	debugger;
	$(".prviousPage").css("pointer-events","none").css("color","grey"); 
	});   	
	$("#zv-cqa-select-sort").change(function(){				
	    paging(2);
	});
	$(".nextPage").click(function(){
		paging(0);
	$(".previousPage").css("pointer-events","auto").css("color","#009e96");
	});
	$(".previousPage").click(function(){
		paging(1);
	});

	
	
	
				
	function paging(num){
		var page = $("#currentPageNo").text();		
		var PageNo = 0;
		console.log("current page: ",page);
		var url = "/goods/qaSort";
		if(num == 0){			
			PageNo = parseInt(page) + 1;
			}else if (num == 1){
			PageNo = parseInt(page) - 1;
			}else{
				PageNo = 1
			}
		    data = {
				"page":PageNo
				}
		
	   $.ajax({
            type: 'POST',//方法类型            
            url : "/goods/qaSort",
            contentType: 'application/json',
            data:JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {                    
                   var el;
                   if(result.data.list.length> 0){
	               $("#ZVCQuestionsArea").find(".delete").remove();
                   }
                   var ar = result.data.list;
                   for(let i =0; i < ar.length; i++){
	               el = $(".hiddenQaDiv").clone().removeClass("hiddenQaDiv");
	               el.find(".zv-cqa-q-text").html(result.data.list[i].question);
	               el.find(".zv-cqa-q-info").html(result.data.list[i].submitDate);
	               el.find(".zv-cqa-q-text").html(result.data.list[i].answer);
	               el.find(".zv-cqa-q-info").html(result.data.list[i].answerDate);
	               $("#dateilFooter"),before(el);
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
        }
	 
	$("#ZVPostQuestionButton").click(function(){
		debugger;
		var question = $("#ZVQuestionTextarea").val();
		var path = window.location.pathname;
		var ar = path.split("/");
		var len = ar.length;
		var goodsId = ar[len-1];
		debugger;
		data = {
			"question":question,
			"goodsId" :goodsId
	    };
		
	  $.ajax({
            type: 'POST',            
            url : "/goods/insertGoodsQa",
            contentType: 'application/json',
            data:JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {  
	                swal("感谢您的留言",{  
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
   });
/*展开更多评论*/
   $("#showMoreReviewsBtn").click(function(){
	debugger;
	var goodsId = getGoodsId();
	var data = {
		"goodsId": goodsId
	           };
	 $.ajax({
            type: 'POST',            
            url : "/goods/showMoreReviews",
            contentType: 'application/json',
            data:JSON.stringify(goodsId),
            success: function (result) {
                if (result.resultCode == 200) {
	                debugger;
	                var list = result.data;
	                if(list === undefined){
		             swal(error,{
			             icon:"error",
			
		                 });
	                 }
	               if(list !=undefined && list.length !=0){
		               for( i =0; i< list.length; i++){
			               var el =$(".g-reviewList_item").clone().removeClass("hiddenList");
			               $(".g-reviewList_item").before(el);
		               }
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
	
     })
     function getGoodsId(){
	 var path = window.location.pathname;
	 var ar = path.split("/");
	 var len = ar.length;
	 var goodsId  = ar[len-1];
	 return  goodsId;
	 
     }
/*参考人数*/
     function helpNumClickFunc(){
	 var reviewId = $( this ).parent().find(".hidSpForRevId").text();
	 var data = {
		 "reviewId": reviewId
	}
	$.ajax({
            type: 'POST',//方法类型            
            url : "/goods/helpNum",
            contentType: 'application/json',
            data:JSON.stringify(goodsId),
            success: function (result) {
                if (result.resultCode == 200) {
	                debugger;
	               console.log(data);
	               _this.find(".helpNumSpan").text("参考人数("+ result.data + "人)");
	                
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
    
        
    