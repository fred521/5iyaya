var map;
var geocoder;
var ml;
var lat = 30.282343384169735;
var lng = 120.11927604675293;
var zoom = 13;
// Create a base icon for all of our markers that specifies the
// shadow, icon dimensions, etc.
var baseIcon = new GIcon();
baseIcon.shadow = "http://www.google.com/mapfiles/shadow50.png";
baseIcon.iconSize = new GSize(20, 34);
baseIcon.shadowSize = new GSize(37, 34);
baseIcon.iconAnchor = new GPoint(9, 34);
baseIcon.infoWindowAnchor = new GPoint(9, 2);
baseIcon.infoShadowAnchor = new GPoint(18, 25);

function load() {
  if (GBrowserIsCompatible()) {
    map = new GMap2(document.getElementById("map"));
    map.setCenter(getCenter(), zoom);
    map.addControl(new GSmallMapControl());
    // create a local search control and add it to your map
	map.addControl(new google.maps.LocalSearch());
    
    map.addOverlay(createMarker(map.getCenter(),1));
    
    ml = GEvent.addListener(map, "mousemove", function(point) {
    	document.getElementById("lat").value=point.lat();
    	document.getElementById("lng").value=point.lng();
  	});
  	
  	geocoder = new GClientGeocoder();
  }
}

function getCenter() {
	var location = window.opener.document.getElementById("location").value;
	if(location && location !="") {
		var latlng = location.split("*");
		// alert("*"+latlng[0]+"*  *"+latlng[1]+"*");
		return new GLatLng(latlng[0],latlng[1]);
	} else {
		return new GLatLng(lat, lng);
	}
}

function unload() {
	GUnload();
	var lt = document.getElementById("lat").value;
	var lg = document.getElementById("lng").value;
	var result = lt+"*"+lg;
	window.opener.document.getElementById("location").value= result;
    window.close();
	
}

// fireLocating is called when click 'select location' button.
// It adds a marker to the map after click on map
function fireLocating() {
	GEvent.addListener(map, "click", function(overlay, point) {
    	if(point) {
    		map.addOverlay(createMarker(point,0));
    		GEvent.removeListener(ml);
    	}
  	});
}


// addAddressToMap() is called when the geocoder returns an
// answer.  It adds a marker to the map with an open info window
// showing the nicely formatted version of the address and the country code.
function addAddressToMap(response) {
  map.clearOverlays();
  if (!response || response.Status.code != 200) {
    alert("Sorry, we were unable to geocode that address");
  } else {
    place = response.Placemark[0];
    point = new GLatLng(place.Point.coordinates[1],
                        place.Point.coordinates[0]);
    marker = new GMarker(point);
    map.addOverlay(marker);
    marker.openInfoWindowHtml(place.address + '<br>' +
      '<b>Country code:</b> ' + place.AddressDetails.Country.CountryNameCode);
  }
}

// showLocation() is called when you click on the Search button
// in the form.  It geocodes the address entered into the form
// and adds a marker to the map at that location.
function showLocation() {
  var address = document.forms[0].q.value;
  geocoder.getLocations(address, addAddressToMap);
}

// Creates a marker whose info window displays the letter corresponding
// to the given index.
function createMarker(point, index) {
    // Create a lettered icon for this point using our icon class
    var letter = String.fromCharCode("A".charCodeAt(0) + index);
    var letteredIcon = new GIcon(baseIcon);
    letteredIcon.image = "http://www.google.com/mapfiles/marker" + letter + ".png";

    // Set up our GMarkerOptions object
    markerOptions = { icon:letteredIcon };
    var marker = new GMarker(point, markerOptions);

    GEvent.addListener(marker, "click", function() {
      marker.openInfoWindowHtml('<a href="http://www.5iyya.com/video/publicPlay.action?playVideoId=27&share=true">view</a>');
    });
    return marker;
}
