     /*$("#zv-cqa-select-sort").change(function(){
	var page = $("#currentPageNo").text();
	var url = "/goods/qaSort";
	data = {
		"page": page
	};
	debugger;*/
    (function(){
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
			}
		
	$.ajax({
            type: 'POST',//方法类型            
            url : "/goods/qaSort",
            contentType: 'application/json',
            
            success: function (result) {
	
                if (result.resultCode == 200) {                    
                    /*swal("保存成功", {
                        icon: "success",
                    });*/
                   var el;
                   if(result.data.list.length> 0){
	               $("#ZVCQuestionsArea").find(".delete").remove();
                   }
                   var ar = result.data.list;
                   
                   for(let i =0; i < ar.length; i++){
	               el = $(".hiddenQaDiv").clone().removeClass("hiddenQaDiv");
	               el.find(".zv-cqa-q-text").html(result.data.list[i].question);
	               $("#dateilFooter"),before(el);
	               }
	
	                   /*var qa = $(".hiddenQaDiv")[0].clone().removeClass("hiddenQaDiv");
	                   qa.find(".zv-cqa-q-text").html(ar[i].question + "chen");*/	               
	                   /*qa.addendTO("#ZVCQuestionsArea")*/

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
    