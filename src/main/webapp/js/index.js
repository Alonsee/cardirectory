$(function(){
	loadCarList();
	addCar();
	deleteCar();
	searchCar();
});

var loadCarList = function(){
	$('tbody').remove();
    $.getJSON('http://localhost:8080/cars', function(data){
        $.each(data, function(key, value){
            $('.content').append('<tbody><tr>'
                                 + '<td>' + value.carId + '</td>'
                                 + '<td>' + value.carManufacturer + '</td>'
                                 + '<td>' + value.carModel + '</td>'
                                 + '<td>' + value.carColor + '</td>'
                                 + '<td>' + value.carReleaseYear + '</td>'
                                 + '</tr></tbody>');
        });
    });
};

var searchCar = function(){
	$('.searchForm').find('input[name = "searchBtn"]').bind('click', function(){
		$('tbody').remove();
	    $.getJSON('http://localhost:8080/cars?'
	    		  + 'manufacturer=' + $('.searchForm').find('input[name = "manufacturer"]').val()
	    		  + '&model=' + $('.searchForm').find('input[name = "model"]').val()
	    		  + '&color=' + $('.searchForm').find('input[name = "color"]').val()
	    		  + '&year=' + $('.searchForm').find('input[name = "year"]').val()
	    		  + '&orderby=' + $('.searchForm').find('select[name = "orderby"]').val(), 
	    		      function(data){
					        $.each(data, function(key, value){
					            $('.content').append('<tbody><tr>'
					                                 + '<td>' + value.carId + '</td>'
					                                 + '<td>' + value.carManufacturer + '</td>'
					                                 + '<td>' + value.carModel + '</td>'
					                                 + '<td>' + value.carColor + '</td>'
					                                 + '<td>' + value.carReleaseYear + '</td>'
					                                 + '</tr></tbody>');
					        });
	    });
	});
};

var addCar = function(){
	$('.addForm').find('input[name = "addBtn"]').bind('click', function(){
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/cars',
            headers: { 
                     'Accept': 'application/json',
                     'Content-Type': 'application/json' 
                     },
            data: JSON.stringify({'carManufacturer' : $('.addForm').find('input[name = "manufacturer"]').val(),
                   'carModel' : $('.addForm').find('input[name = "model"]').val(), 
                   'carColor' : $('.addForm').find('input[name = "color"]').val(), 
                   'carReleaseYear' : $('.addForm').find('input[name = "year"]').val()}),
            success: function(response){
                         $('.add').append('<p>' + response.message + '</p>');
                     },
            error: function(e){
                       $('.add').append('<p>' + e + '</p>');
                   }
        });
        setTimeout(loadCarList(), 1000);
    });
};

var deleteCar = function(){
    $('.deleteForm').find('input[name="deleteBtn"]').bind('click', function(){
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/cars/delete/' + $('.deleteForm').find('input[name="deletedId"]').val(),
            headers: { 
                     'Accept': 'application/json',
                     'Content-Type': 'application/json' 
                     },
            success: function(response){
            	         $('.del').append('<p>' + response.message + '</p>');
            	         setTimeout(loadCarList(), 1000);
                     },
            error: function(e){
            	       $('.del').append('<p>' + e + '</p>');
                   }
        })
    });
};

