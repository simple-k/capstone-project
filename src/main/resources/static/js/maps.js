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
// Color each letter gray. Change the color when the isColorful property
// is set to true.

// STYLING OPTIONS FOR GEOJSON
map.data.setStyle({strokeWeight: 1});
// map.data.setStyle(function(feature) {
//     var color = 'gray';
//     if (feature.getProperty('isColorful')) {
//         color = feature.getProperty('color');
//     }
//     return /** @type {!google.maps.Data.StyleOptions} */({
//         fillColor: color,
//         strokeColor: color,
//         strokeWeight: 2
//     });
// });
//
// // When the user clicks, set 'isColorful', changing the color of the letters.
// map.data.addListener('click', function(event) {
//     event.feature.setProperty('isColorful', true);
// });
//
// // When the user hovers, tempt them to click by outlining the letters.
// // Call revertStyle() to remove all overrides. This will use the style rules
// // defined in the function passed to setStyle()
// map.data.addListener('mouseover', function(event) {
//     map.data.revertStyle();
//     map.data.overrideStyle(event.feature, {strokeWeight: 8});
// });
//
// map.data.addListener('mouseout', function(event) {
//     map.data.revertStyle();
// });



var geocoder = new google.maps.Geocoder();


function locate(address) {
    geocoder.geocode({"address": address}, function (results, status) {

        // Check for a successful result
        if (status == google.maps.GeocoderStatus.OK) {
            // Recenter the map over the address
            map.setCenter(results[0].geometry.location);
            map.setZoom(15);
            var marker = new google.maps.Marker({
                position: results[0].geometry.location,
                map: map
            });
        } else {

            // Show an error Message with the status if our request fails
            alert("Geocoding was not successful - STATUS: " + status);
        }
    });
}

function placeMarker(address){
    geocoder.geocode({"address": address}, function (results, status) {

        // Check for a successful result
        if (status == google.maps.GeocoderStatus.OK) {
            var marker = new google.maps.Marker({
                position: results[0].geometry.location,
                map: map
            });
            console.log('done with' + address);
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
        });
        let districts = resultArray.properties.NAME;
        let coordinates = resultArray.geometry.coordinates;
        for(let i = 0; i < districts.length; i++){
            for(coordinate of coordinates){
                console.log(coordinate[0]);
            }
        }
    });
}
function testData(){
    $.getJSON('/Json/San_Antonio_Districts.geojson', function (data) {
        console.log(data);
        let result = data.features;
        console.log(result);
        for(let item of result){
            console.log(item.properties.NAME);
            console.log(item.geometry.coordinates[0][0]);
            if(item.properties.NAME == "Lackland ISD" || item.properties.NAME == "San Antonio ISD") {
                let secondResult = item.geometry.coordinates[0][0];
                for (let coordinate of secondResult) {
                    let lat = coordinate[0];
                    let lng = coordinate[1];
                    console.log(lat);
                    console.log(lng);
                }
            }
            // else{
            //     let i = 0;
            //     let lat = item.geometry.coordinates[0][i][0];
            //     let lng = item.geometry.coordinates[0][i++][1];
            //     i++;
            //     console.log(lat);
            //     console.log(lng);
            // }
        }
    })
}
