		
			dropdownBrands = $("#brand");
	        dropdownCategories = $("#category");
	        
			$(document).ready(function(){
				
				$("#shortDescription").richText();
				$("#fullDescription").richText();
				
				dropdownBrands.change(function(){
					dropdownCategories.empty();
					getCategories();
				});
				getCategoriesforNewForm();
			});
 
 
       function getCategoriesforNewForm(){
		   catIdField = $("#categoryId");
		   
		   editMode = false;
		   if(catIdField.length){
			   editMode = true;
		   }
		   
		   if(! editMode)getCategories();
	   }
				function getCategories() {
						brandId = dropdownBrands.val();
						url = brandModuleURL + "/" + brandId + "/categories";

						$.get(url, function(responseJson) {
							$.each(responseJson, function(index, category) {
							$("<option>").val(category.id).text(category.name).appendTo(dropdownCategories);
							});
						});
					}

 
			  function chechUnique(form){			
			 productId = $("#id").val();
			productName = $("#name").val();
			csrfValue=$("input[name='_csrf']").val();
			
			params = {id:productId,name: productName, _csrf: csrfValue};
			
			$.post(checkUniqueUrl,params,function(response){
				if(response =="OK"){
					form.submit();
				}else if(response =="Duplicated"){
					showModalDialog("Warning","There is another product having the name" + productName);
				}else{
					showModalDialog("Error", "There is another product having the same name");
				}
			}).fail(function(){
				showModalDialog("Error","Could not connect to the server");
			});			
			return false;
		}
		
		
		
		