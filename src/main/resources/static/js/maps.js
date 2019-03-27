function filterDistricts(){
    let district = document.getElementById("districtFilter").value;
    console.log(district);
}
$('#find').click(function (){
    findDistrict();
});

let theAddress;

let map;
const geocoder = new google.maps.Geocoder();
map = new google.maps.Map(document.getElementById('map-canvas'), {
    zoom: 13,
    center: {lat: 29.4241, lng: -98.4936}
});

// ----------- geoJSON ------------
// NOTE: This uses cross-domain XHR, and may not work on older browsers.
map.data.loadGeoJson('../Json/San_Antonio_Districts.geojson', {
    idPropertyName: 'NAME2',
    });
map.data.setStyle({
    fillOpacity: .10,
    strokeWeight: 1
});
map.data.addListener('mouseover', function(event) {
    map.data.revertStyle();
    map.data.overrideStyle(event.feature, {
        zIndex:1,
        strokeColor: 'yellow',
        strokeWeight: 2});
});
map.data.addListener('mouseout', function(event) {
    map.data.revertStyle();
});

const geocoder = new google.maps.Geocoder();

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
            alert("Geocoding was not successful - STATUS: " + status);
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

var placeSearch, autocomplete;

// Create the autocomplete object, restricting the search predictions to
// geographical location types.
autocomplete = new google.maps.places.Autocomplete(
    document.getElementById('autocomplete'), {types: ['geocode']});

// When the user selects an address from the drop-down, populate the
// address fields in the form.
autocomplete.addListener('place_changed', fillInAddress);

function fillInAddress() {
    // Get the place details from the autocomplete object.
    let place = autocomplete.getPlace();

    for (let component in componentForm) {
        document.getElementById(component).value = '';
        document.getElementById(component).disabled = false;
    }

    // Get each component of the address from the place details,
    // and then fill-in the corresponding field on the form.
    for (let i = 0; i < place.address_components.length; i++) {
        let addressType = place.address_components[i].types[0];
        if (componentForm[addressType]) {
            let val = place.address_components[i][componentForm[addressType]];
            document.getElementById(addressType).value = val;
        }
    }
}

// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
function geolocate() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            let geolocation = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            let circle = new google.maps.Circle(
                {center: geolocation, radius: position.coords.accuracy});
        });
    }
}

function findDistrict(){
    let district;
    let district2;
    let userDistrict;
    $.getJSON('/Json/San_Antonio_Districts.geojson', function (data) {
        let result = data.features;
        for (let item of result) {
            let coordinates = [];
            if (item.properties.NAME == "San Antonio ISD") {
                userDistrict=item.properties.NAME;
                let secondResult = item.geometry.coordinates[0][0];
                let coordinates2 = [];
                for (let coordinate of secondResult) {
                    let lat = coordinate[1];
                    let lng = coordinate[0];
                    let districtCoordinate = {lat: lat, lng: lng};
                    coordinates.push(districtCoordinate);
                }
                let thirdResult = item.geometry.coordinates[1][0];
                for (let coordinate of thirdResult) {
                    let lat = coordinate[1];
                    let lng = coordinate[0];
                    let districtCoordinate = {lat: lat, lng: lng};
                    coordinates2.push(districtCoordinate);
                }
                district = new google.maps.Polygon({
                    paths: coordinates,
                    strokeColor: '#FF0000',
                    strokeOpacity: 0.8,
                    strokeWeight: 2,
                    fillColor: '#FFff00',
                    fillOpacity: 0.05
                });
                district2 = new google.maps.Polygon({
                    paths: coordinates2,
                    strokeColor: '#FF0000',
                    strokeOpacity: 0.8,
                    strokeWeight: 2,
                    fillColor: '#FFff00',
                    fillOpacity: 0.05
                });
            district.setMap(map);
            district2.setMap(map);
            }
        }
    });
    theAddress = $('#address').val();
    theAddress += ', ' + $('#zipCode').val();
    console.log(theAddress);
    if (theAddress == null){
        console.log('This only shows if you\'re not logged in');
    }
    else {
        geocoder.geocode({"address": theAddress}, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                let userAddress = results[0].geometry.location;
                var marker = new google.maps.Marker({
                    position: results[0].geometry.location,
                    map: map
                });
                let addressCoord = {lat: userAddress.lat, lng: userAddress.lng};
                console.log(addressCoord);
                if(google.maps.geometry.poly.containsLocation(addressCoord, district)|| google.maps.geometry.poly.containsLocation(addressCoord, district2));{
                    alert(`Your district is: ${userDistrict}`);
                    $('#userDistrict').val(userDistrict);
                }
            } else {
                alert("Geocoding was not successful - STATUS: " + status);
            }
        });
    }
}
