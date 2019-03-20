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
//
//     var map = new google.maps.Map(document.getElementById('map-canvas'), {
//         center: {lat: 29.4241, lng: -98.4936},
//         zoom: 13,
//         mapTypeId: 'roadmap'
//     });
//
//     // Create the search box and link it to the UI element.
//     var input = document.getElementById('pac-input');
//     var searchBox = new google.maps.places.SearchBox(input);
//     map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
//
//     // Bias the SearchBox results towards current map's viewport.
//     map.addListener('bounds_changed', function() {
//         searchBox.setBounds(map.getBounds());
//     });
//
//     var markers = [];
//     // Listen for the event fired when the user selects a prediction and retrieve
//     // more details for that place.
//     searchBox.addListener('places_changed', function() {
//         var places = searchBox.getPlaces();
//
//         if (places.length == 0) {
//             return;
//         }
//
//         // Clear out the old markers.
//         markers.forEach(function(marker) {
//             marker.setMap(null);
//         });
//         markers = [];
//
//         // For each place, get the icon, name and location.
//         var bounds = new google.maps.LatLngBounds();
//         places.forEach(function(place) {
//             if (!place.geometry) {
//                 console.log("Returned place contains no geometry");
//                 return;
//             }
//             var icon = {
//                 url: place.icon,
//                 size: new google.maps.Size(71, 71),
//                 origin: new google.maps.Point(0, 0),
//                 anchor: new google.maps.Point(17, 34),
//                 scaledSize: new google.maps.Size(25, 25)
//             };
//
//             // Create a marker for each place.
//             markers.push(new google.maps.Marker({
//                 map: map,
//                 icon: icon,
//                 title: place.name,
//                 position: place.geometry.location
//             }));
//
//             if (place.geometry.viewport) {
//                 // Only geocodes have viewport.
//                 bounds.union(place.geometry.viewport);
//             } else {
//                 bounds.extend(place.geometry.location);
//             }
//         });
//         map.fitBounds(bounds);
//     });
