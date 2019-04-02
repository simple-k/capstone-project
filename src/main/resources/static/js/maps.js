// ---INITIALIZE GOOGLE MAPS---
let map = new google.maps.Map(
    document.getElementById('map-canvas'), {
        center: {lat: 29.4241, lng: -98.4936},
        zoom: 13,
        // defaults map to "roadmap" graphic display
        mapTypeId: google.maps.MapTypeId.ROADMAP
});
//---USER ADDRESS GEOCODER---
const geocoder = new google.maps.Geocoder();
let userAddress = $('#address').val() + ', ' + $('#zipCode').val();
placeMarker(userAddress);
let userLatLng = new google.maps.LatLng();
geocoder.geocode({'address': userAddress}, function (results, status) {
    // Check for a successful result
    if (status == google.maps.GeocoderStatus.OK) {
        let latitude = results[0].geometry.location.lat();
        let longitude = results[0].geometry.location.lng();
        let userLatLng = {lat: latitude, lng: longitude};
        console.log(userLatLng);
    } else {
        // Show an error Message with the status if our request fails
        alert("Geocoding was not successful - STATUS: " + status);
    }
});

// --- GEOJSON LAYER---
let districtData = new google.maps.Data();
districtData.loadGeoJson('../Json/San_Antonio_Districts.geojson', {idPropertyName: 'NAME2'});
let polygon;
let districtPoly = [];
let districtName = [
    'San Antonio',
    // 'Lackland',
    // 'Judson',
    // 'Alamo Heights',
    // 'East Central',
    // 'Fort Sam Houston',
    // 'Edgewood (Bexar)',
    // 'South San Antonio',
    // 'Floresville',
    // 'Harlandale',
    // 'Medina Valley',
    // 'Southwest',
    // 'Southside',
    // 'North East',
    // 'Northside (Bexar)',
    // 'Randolph Field'
];
// POLYGON CREATION
districtData.addListener('addfeature', function(evt) {
    // loop iterates through array of district names
    for (let i=0; i<districtName.length;i++){
        if (evt.feature.getId() == districtName[i]) {
            let district = districtData.getFeatureById(districtName[i]);
            let districtGeo = district.getGeometry();

            // checks for data.MultiPolygon type in districtData
            if (districtGeo.getType()=="MultiPolygon") {

                // converts data.MultiPolygon into a single array
                let toArray = districtGeo.getArray();

                // creates polygon
                toArray.forEach(function (item){
                    let coords= item.getAt(0).getArray();
                    districtPoly.push(new google.maps.Polygon({
                        paths: coords,
                        map: map,
                        clickable: false,
                        strokeWeight: 2,
                        strokeColor: 'red',
                        strokeOpacity: 1,
                        zIndex: 1,
                        fillOpacity: 0
                    }));
                    // console.log(google.maps.geometry.poly.containsLocation(userLatLng, districtData.getFeatureById(districtName[0].getGeometry().getAt(0).getArray())));
                    // if (google.maps.geometry.poly.containsLocation(userLatLng, coords)){
                    //     console.log(`user belongs in: ${districtName[i]}`)
                    // }
                });
                console.log(districtPoly[0].getPaths());
            }
            // after checking for data.MultiPolygon we create all of the regular polygons
            else {
                let coords = districtGeo.getAt(0).getArray();
                districtPoly.push(new google.maps.Polygon({
                    paths: coords,
                    map: map,
                    clickable: false,
                    strokeWeight:  2,
                    strokeColor: 'black',
                    strokeOpacity: 1,
                    zIndex: 1,
                    fillOpacity: 0
                }));
                // console.log(google.maps.geometry.poly.containsLocation(userLatLng, districtPoly[0].getPaths()));
                // if (google.maps.geometry.poly.containsLocation(userLatLng, coords)){
                //     console.log(`user belongs in: ${districtName[i]}`)
                // }

                // var infoWindow = new google.maps.InfoWindow();
                // google.maps.event.addListener(map, 'click', function(evt) {
                //     infoWindow.setPosition(evt.latLng);
                //     if (google.maps.geometry.poly.containsLocation(evt.latLng, districtPoly)) {
                //         infoWindow.setContent("INSIDE POLY<br>" + evt.latLng.toUrlValue(6));
                //     } else {
                //         infoWindow.setContent("OUTSIDE POLY<br>" + evt.latLng.toUrlValue(6));
                //     }
                //     infoWindow.open(map);
                // });

            }

        }
    }
});
map.data.addListener('mouseover', function(event) {
    map.data.revertStyle();
    map.data.overrideStyle(event.feature, {strokeWeight: 3, zIndex:4, strokeColor: 'yellow'});
});

map.data.addListener('mouseout', function(event) {
    map.data.revertStyle();
});
districtData.setStyle({
    strokeWeight: 1,
    clickable: true,
    visible: true,
    zIndex:0,
    fillOpacity: 0.1
});
districtData.setMap(map);
// GEOJSON: CONTAINS LOCATION


function filterDistricts(){
    let district = document.getElementById("districtFilter").value;
    console.log(district);
}
$('#find').click(function (){
    findDistrict();
});

let theAddress;
//variables for directions service function ------------------->Bryan Matta
var directionsService = new google.maps.DirectionsService;
var directionsDisplay = new google.maps.DirectionsRenderer;
//<--------------------------------------------------------------


directionsDisplay.setMap(map);
directionsDisplay.setPanel(document.getElementById('bottom-panel'));

function locate(address) {

    geocoder.geocode({"address": address}, function (results, status) {

        // Check for a successful result
        if (status == google.maps.GeocoderStatus.OK) {
            // Recenter the map over the address
            map.setCenter(results[0].geometry.location);
            map.setZoom(12);
            let marker = new google.maps.Marker({
                position: results[0].geometry.location,
                map: map
            });
        } else {

            // Show an error Message with the status if our request fails
            // alert("Geocoding was not successful - STATUS: " + status);
        }
    });
}

function placeMarker(address){
    geocoder.geocode({"address": address}, function (results, status) {
        var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';
        // Check for a successful result
        if (status == google.maps.GeocoderStatus.OK) {
            let marker = new google.maps.Marker({
                position: results[0].geometry.location,
                map: map,
                // icon: {
                //     url: "../img/google_maps/house_icon.png", // url
                //     scaledSize: new google.maps.Size(30, 30), // scaled size
                //     origin: new google.maps.Point(0,0), // origin
                    // anchor: new google.maps.Point(0, 0) // anchor
                // }
            });
            // console.log('done with ' + address);
        } else {

            // Show an error Message with the status if our request fails
            alert("Geocoding was not successful - STATUS: " + status);
        }
    });
}

// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
function geolocate() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            let geolocation = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            let circle = new google.maps.Circle(
                {center: geolocation, radius: position.coords.accuracy});
        });
    }
}




        // districtData.setStyle({
        //     clickable: true,
        //     visible: false,
        // });

        // var geocoder = new google.maps.Geocoder();
        // geocoder.geocode({
        //     'address': "334 Savannah Dr"
        // }, function(results, status) {
        //     if (status === google.maps.GeocoderStatus.OK) {
        //         map.fitBounds(results[0].geometry.viewport);
        //     } else {
        //         alert('Geocode was not successful for the following reason: ' + status);
        //     }
        // });
// google.maps.event.addDomListener(window, "load", initialize);

// Directions service--------------------------> Bryan Matta
function calculateAndDisplayRoute(schoolAddress) {
    let userAddress = $('#address').val() + ', ' + $('#zipCode').val();
    console.log(userAddress);
    console.log(schoolAddress);
    directionsService.route({
        origin: userAddress,
        destination: schoolAddress,
        travelMode: 'DRIVING'
    }, function (response, status) {
        if (status === 'OK') {
            directionsDisplay.setDirections(response);
        } else {
            window.alert('Directions request failed due to ' + status);
        }
    });
}