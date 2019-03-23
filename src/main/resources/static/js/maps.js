var mapOptions = {
    // Set the zoom level
    zoom: 5,

    // This sets the center of the map at our location
    center: {
        lat: 29.426791,
        lng: -98.489602
    }
};
var map;
map = new google.maps.Map(document.getElementById('map-canvas'), {
    zoom: 9,
    center: {lat: 29.4241, lng: -98.4936}
});

// NOTE: This uses cross-domain XHR, and may not work on older browsers.
map.data.loadGeoJson(
    '../Json/San_Antonio_Districts.geojson');

var geocoder = new google.maps.Geocoder();

function locate(address, id) {
    console.log('thisworks!');

    // CSRF token
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    // get selected school from database and return JSON
    jQuery.ajax({
        url: '/api/school/selected',
        type: 'GET',
        dataType: 'json',
        data: {"schoolId": id},
        cache:false,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
            success:function(array){
                console.log(array);
                $("#school").remove();
                $('#selected_school').append("<div id='school'>" +
                    "<h3>"+array[0].schoolName+"</h3>" +
                    "<p>"+array[0].streetAddress+"</p>" +
                    "<p>San Antonio, TX, <span>"+array[0].zipCode+"</span></p>" +
                    "<p>Grades: PK-<span>"+array[0].high0rade+"</span></p>" +
                    "<p>Total students enrolled: <span>"+array[0].students+"</span></p>" +
                    "<p>Total teachers: <span>"+array[0].teachers+"</span></p>" +
                    "<p>Students per teacher: <span>"+array[0].studentTeacherRatio+"</span></p>" +
                    "<p>District: <a href='"+array[1][3]+"' target='_blank'><span>"+array[1][0]+"</span></a></p></div>");
        },
        error:function(jqXhr, textStatus, errorThrown){
            console.log(jqXhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });

    geocoder.geocode({"address": address}, function (results, status) {

        // Check for a successful result
        if (status == google.maps.GeocoderStatus.OK) {
            // Recenter the map over the address
            map.setCenter(results[0].geometry.location);
            map.setZoom(15);
            var marker = new google.maps.Marker({
                position: results[0],
                map: map
            });
        } else {

            // Show an error Message with the status if our request fails
            alert("Geocoding was not successful - STATUS: " + status);
        }
    });
}
var placeSearch, autocomplete;

// Create the autocomplete object, restricting the search predictions to
// geographical location types.
autocomplete = new google.maps.places.Autocomplete(
    document.getElementById('autocomplete'), {types: ['geocode']});

// Avoid paying for data that you don't need by restricting the set of
// place fields that are returned to just the address components.

// When the user selects an address from the drop-down, populate the
// address fields in the form.
autocomplete.addListener('place_changed', fillInAddress);

function fillInAddress() {
    // Get the place details from the autocomplete object.
    var place = autocomplete.getPlace();

    for (var component in componentForm) {
        document.getElementById(component).value = '';
        document.getElementById(component).disabled = false;
    }

    // Get each component of the address from the place details,
    // and then fill-in the corresponding field on the form.
    for (var i = 0; i < place.address_components.length; i++) {
        var addressType = place.address_components[i].types[0];
        if (componentForm[addressType]) {
            var val = place.address_components[i][componentForm[addressType]];
            document.getElementById(addressType).value = val;
        }
    }
}

// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
function geolocate() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var geolocation = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            var circle = new google.maps.Circle(
                {center: geolocation, radius: position.coords.accuracy});
        });
    }
}

function findDistrict(address){
    geocoder.geocode({"address": address}, function (results, status) {
        let resultArray;
        $.getJSON('/Json/San_Antonio_Districts.geojson', function (data) {
            resultArray = data.features;
        })

    });
}
function testData(){
    $.getJSON('/Json/San_Antonio_Districts.geojson', function (data) {
        console.log(data);
        let result = data.features;
        console.log(result);
        for(let item of result){
            console.log(item.geometry.coordinates[0][0]);
        }
    })
}