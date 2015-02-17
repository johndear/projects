(function($){
   
   function save(){
	  	
	}

   function doAjax(url,type,params,callback){
       $.ajax({
           	 type: type,
           	 url: url,
	   		 data: params,
	   		 success: callback
	   	});
   }
   
    /** examples: 
	   	doAjax('','GET',{},function(msg){
	       	alert('result: '+msg);
	 	});
 	*/
 });
