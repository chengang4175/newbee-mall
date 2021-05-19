window.onload = function(){
  //要执行的js代码段  
}

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
	
	
//想要完成点击表头，完成排序功能
/*function SortTable(obj){
            var td0s=document.getElementsByName("td0");
            var td1s=document.getElementsByName("td1");
            var td2s=document.getElementsByName("td2");
            var td3s=document.getElementsByName("td3");
            var tdArray0=[];
            var tdArray1=[];
            var tdArray2=[];
            var tdArray3=[];
            for(var i=0;i<td0s.length;i++){
                tdArray0.push(td0s[i].innerHTML);
            }
            for(var i=0;i<td1s.length;i++){
                tdArray1.push(parseInt(td1s[i].innerHTML));
            }
            for(var i=0;i<td2s.length;i++){
                tdArray2.push(parseInt(td2s[i].innerHTML));
            }
            for(var i=0;i<td3s.length;i++){
                tdArray3.push(parseInt(td3s[i].innerHTML));
            }
            var tds=document.getElementsByName("td"+obj.id.substr(2,1));
            var columnArray=[];
            for(var i=0;i<tds.length;i++){
                columnArray.push(parseInt(tds[i].innerHTML));
            }
            var orginArray=[];
            for(var i=0;i<columnArray.length;i++){
                orginArray.push(columnArray[i]);
            }
            if(obj.className=="as"){
                columnArray.sort(sortNumberAS);               //排序后的新值
                obj.className="desc";
            }else{
                columnArray.sort(sortNumberDesc);               //排序后的新值
                obj.className="as";
            }
 
 
            for(var i=0;i<columnArray.length;i++){
                for(var j=0;j<orginArray.length;j++){
                    if(orginArray[j]==columnArray[i]){
                        document.getElementsByName("td0")[i].innerHTML=tdArray0[j];
                        document.getElementsByName("td1")[i].innerHTML=tdArray1[j];
                        document.getElementsByName("td2")[i].innerHTML=tdArray2[j];
                        document.getElementsByName("td3")[i].innerHTML=tdArray3[j];
                        orginArray[j]=null;
                        break;
                    }
                }
            }
        }
*/
//排序功能				
function paging(num){
		var page = $("#currentPageNo").text();		
		var PageNo = 0;
		console.log("current page: ",page);
		var url = "/goods/sale";
		if(num == 0){			
			PageNo = parseInt(page) + 1;
		}else if (num == 1){
			PageNo = parseInt(page) - 1;
		}else{
				PageNo = 1
			}
		    data = {
				"page":PageNo
			};
		
	console.log("data",data);
	   $.ajax({
            type: 'POST',//方法类型            
            url : "/goods/sale",
            contentType: 'application/json',
            data:JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {                    
                   var el;
                   if(result.data.list.length> 0){
	                   $("#ZVCQuestionsArea").find(".zv-cqa").remove();
                   }
                   
                   for(let i =0; i < result.length; i++){
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
		
var currentIndex = 1;
    $(function(){
	debugger;
	$(".prviousPage").css("pointer-events","none").css("color","grey"); 
	$("#closeBtn").hide();
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
	
	
	$("#closeBtn").click(function(){
	   $(".chen").hide();
	   $("#closeBtn").hide();
	   $("#showMoreReviewBtn").show();
		
	});
