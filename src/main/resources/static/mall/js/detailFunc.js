$("#zv-cqa-select-sort").change(function(){
	var page = $("#currentPageNo").text();
	var url = "/goods/qaSort";
	data = {
		"page": page
	};
	debugger;
	
	$.ajax({
            type: 'POST',//方法类型
            url: url,
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {                    
                    /*swal("保存成功", {
                        icon: "success",
                    });*/
                   var ar = result.data.list;
                   
                   for(let i =0; i < ar.length; i++){
	
	                   var qa = $(".hiddenQaDiv").clone().removeClass("hiddenQaDiv");
	                   qa.find(".zv-cqa-q-text").html(ar[i].question + "chen");
	                   qa.addendTO("#ZVCQuestionsArea");
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
    });



	
