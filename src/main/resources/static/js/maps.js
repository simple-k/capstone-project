function filterDistricts(){
    let district = document.getElementById("districtFilter").value;
    console.log(district);
}
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

function findDistrict(address, district, district2){
    geocoder.geocode({"address": address}, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            let userAddress = results[0].geometry.location;
            var marker = new google.maps.Marker({
                position: results[0].geometry.location,
                map: map
            });
            let addressCoord = {lat: userAddress.lat, lng: userAddress.lng};
            console.log(address, google.maps.geometry.poly.containsLocation(addressCoord, district));
            console.log(address + '#2', google.maps.geometry.poly.containsLocation(addressCoord, district2));
        }
        else{
            alert("Geocoding was not successful - STATUS: " + status);
        }
    });
}
function testData(){
    $.getJSON('/Json/San_Antonio_Districts.geojson', function (data) {
        console.log(data);
        let result = data.features;
        console.log(result);
        for(let item of result){
            let coordinates = [];
            console.log(item.properties.NAME);
            for(let coordinate of item.geometry.coordinates[0]){
                console.log('test' , coordinate);
                if(item.properties.NAME == "Lackland ISD" || item.properties.NAME == "San Antonio ISD" ) {
                    let secondResult = item.geometry.coordinates[0][0];
                    let coordinates2 = [];
                    for (let coordinate of secondResult) {
                        let lat = coordinate[1];
                        let lng = coordinate[0];
                        let districtCoordinate = {lat : lat, lng: lng};
                        coordinates.push(districtCoordinate);
                        // console.log(lat);
                        // console.log(lng);
                    }
                    let thirdResult = item.geometry.coordinates[1][0];
                    for (let coordinate of thirdResult) {
                        let lat = coordinate[1];
                        let lng = coordinate[0];
                        let districtCoordinate = {lat : lat, lng: lng};
                        console.log(districtCoordinate);
                        coordinates2.push(districtCoordinate);
                        // console.log(lat);
                        // console.log(lng);
                    }
                    console.log(coordinates);
                    let district = new google.maps.Polygon({
                        paths: coordinates,
                        strokeColor: '#FF0000',
                        strokeOpacity: 0.8,
                        strokeWeight: 2,
                        fillColor: '#FF0000',
                        fillOpacity: 0.35
                    });
                    let district2 = new google.maps.Polygon({
                        paths: coordinates2,
                        strokeColor: '#FF0000',
                        strokeOpacity: 0.8,
                        strokeWeight: 2,
                        fillColor: '#FF0000',
                        fillOpacity: 0.35
                    });
                    district.setMap(map);
                    district2.setMap(map);
                    // findDistrict('115 Medina Base Rd', district, district2);
                    // findDistrict('1800 Dimsted Pl', district, district2);
                    // findDistrict('600 Navarro St #350', district, district2);
                    // findDistrict('1 Towers Park Ln', district, district2);
                }
                // let lat = coordinate[1];
                // let lng = coordinate[0];
                // let districtCoordinate = {lat : lat, lng: lng};
                // coordinates.push(districtCoordinate);
                // console.log(coordinates);
                // let district = new google.maps.Polygon({
                //     paths: coordinates,
                //     strokeColor: '#FF0000',
                //     strokeOpacity: 0.8,
                //     strokeWeight: 2,
                //     fillColor: '#FF0000',
                //     fillOpacity: 0.35
                // });
                // district.setMap(map);
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
