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

function locate(address) {
    geocoder.geocode({"address": address}, function (results, status) {

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

var input = document.getElementById('pac-input');
var searchBox = new google.maps.places.SearchBox(input);
map.controls[google.maps.ControlPosition.TOP_CENTER].push(input);
// Bias the SearchBox results towards current map's viewport.
map.addListener('bounds_changed', function () {
    searchBox.setBounds(map.getBounds());
});

function buttonTest() {
    alert('This works!');
}
