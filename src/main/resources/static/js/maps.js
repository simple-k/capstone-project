// ---INITIALIZE GOOGLE MAPS---
let map = new google.maps.Map(
    document.getElementById('map-canvas'), {
        center: {lat: 29.4241, lng: -98.4936},
        zoom: 13,
        // defaults map to "roadmap" graphic display
        mapTypeId: google.maps.MapTypeId.ROADMAP
});
//---ADDRESS GEOCODER---
const geocoder = new google.maps.Geocoder();
let userAddress = $('#address').val() + ', ' + $('#zipCode').val();
placeMarker(userAddress);
let userCoord;
geocoder.geocode({"address": userAddress}, function (results, status) {
    // Check for a successful result
    if (status == google.maps.GeocoderStatus.OK) {
        userCoord = results[0].geometry.location;
        userCoord = {lat:userCoord.lat(), lng:userCoord.lng()};
        console.log(userCoord.lat);
    } else {
        // Show an error Message with the status if our request fails
        alert("Geocoding was not successful - STATUS: " + status);
    }
});



// ---AARON'S GEOJSON LAYER---
let districtData = new google.maps.Data();
districtData.loadGeoJson('../Json/San_Antonio_Districts.geojson', {
    idPropertyName: 'NAME2'
});
let districtPoly;
let districtName = [
    'San Antonio',
    'Judson',
    'Lackland',
    'Alamo Heights',
    'East Central',
    'Fort Sam Houston',
    'Edgewood (Bexar)',
    'South San Antonio',
    'Floresville',
    'Harlandale',
    'Medina Valley',
    'Southwest',
    'Southside',
    'North East',
    'Northside (Bexar)',
    'Randolph Field'
];
// GEOJSON: POLYGON CREATION
districtData.addListener('addfeature', function(evt) {
    // loop iterates through array of district names
    for (let i=0; i<districtName.length;i++){
        if (evt.feature.getId() == districtName[i]) {
            let district = districtData.getFeatureById(districtName[i]);
            let districtGeo = district.getGeometry();
            //districtGeo is the feature.geometry from districtData
            // checks for data.MultiPolygon type in districtData
            if (districtGeo.getType()=="MultiPolygon") {
                // converts data.Multipolygon into a single array
                let toArray = districtGeo.getArray();
                // creates polygon
                toArray.forEach(function (item){
                    let coords= item.getAt(0).getArray();
                    districtPoly = new google.maps.Polygon({
                        paths: coords,
                        map: map,
                        clickable: false
                    });
                    // if (google.maps.geometry.poly.containsLocation(, districtPoly)){
                    //     console.log(`user belongs in: ${districtName[i]}`)
                    // }

                })
            }
            // after checking for data.MultiPolygon we create all of the regular polygons
            else {
                districtPoly = new google.maps.Polygon({
                    paths: districtGeo.getAt(0).getArray(),
                    map: map,
                    clickable: false
                });
                // if (google.maps.geometry.poly.containsLocation({lat:userAddress.lat, lng:userAddress.lng}, districtPoly)){
                //     console.log(`user belongs in: ${districtName[i]}`)
                // }
            }
        }
    }
});
// GEOJSON: CONTAINS LOCATION
var infoWindow = new google.maps.InfoWindow();
google.maps.event.addListener(map, 'click', function(evt) {
    infoWindow.setPosition(evt.latLng);
    if (google.maps.geometry.poly.containsLocation(evt.latLng, districtPoly)) {
        infoWindow.setContent("INSIDE POLY<br>" + evt.latLng.toUrlValue(6));
    } else {
        infoWindow.setContent("OUTSIDE POLY<br>" + evt.latLng.toUrlValue(6));
    }
    infoWindow.open(map);
});
districtData.setStyle({
    clickable: false,
    visible: false,
});
districtData.setMap(map);


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

        // Check for a successful result
        if (status == google.maps.GeocoderStatus.OK) {
            let marker = new google.maps.Marker({
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