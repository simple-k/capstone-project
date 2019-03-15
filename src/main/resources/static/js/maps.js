"use strict";

var mapOptions = {
    // Set the zoom level
    zoom: 5,

    // This sets the center of the map at our location
    center: {
        lat:  29.426791,
        lng: -98.489602
    }
};

var map;
map = new google.maps.Map(document.getElementById('map-canvas'), {
    zoom: 4,
    center: {lat: -28, lng: 137}
});

// NOTE: This uses cross-domain XHR, and may not work on older browsers.
map.data.loadGeoJson(
    'https://opendata.arcgis.com/datasets/e115fed14c0f4ca5b942dc3323626b1c_0.geojson');

var geocoder = new google.maps.Geocoder();
var laPanaderia = "301 E Houston St, San Antonio, TX 78205";
function locate(address){
    geocoder.geocode({ "address": address }, function(results, status) {

        // Check for a successful result
        if (status == google.maps.GeocoderStatus.OK) {
            // Recenter the map over the address
            map.setCenter(results[0].geometry.location);
            map.setZoom(20);
            var marker = new google.maps.Marker({
                position: results[0],
                map: map
            });
        } else {

            // Show an error message with the status if our request fails
            alert("Geocoding was not successful - STATUS: " + status);
        }
    });
}
locate(laPanaderia);
