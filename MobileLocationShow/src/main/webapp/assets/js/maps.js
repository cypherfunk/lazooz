var map;
var markers=[];

// map initialize 
function initialize(){
    var myLatlng = new google.maps.LatLng(23.758529,90.390126);
    var myOptions = {
        zoom: 10,
        center: myLatlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    setMarkers(map, usersLocation, "google-maps-reg.png");
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

// on click load logged user maps 
function loggedLocation(){
    $("#loggedin").addClass("btn-primary");
    $("#registerd").removeClass("btn-primary");
    $("#registerd").addClass("btn-default");
    
    clearMarkers();
    setMarkers(map, loggedInLocation, "google-maps-log.png");
}

// on click load all registerred user maps
function registeredLocation(){
    $("#registerd").addClass("btn-primary");
    $("#loggedin").removeClass("btn-primary");
    $("#loggedin").addClass("btn-default");
    clearMarkers();
    setMarkers(map, usersLocation, "google-maps-reg.png");
}


// set each markers
function setMarkers(map, userlocation, iconname){
    //var markers=[];
    var latlngbounds = new google.maps.LatLngBounds();

    for(var lop=0; lop<userlocation.length; lop++){
        var jsonparse		= userlocation[lop];
        var lat                 = parseFloat(jsonparse.latitude)
        var lng			= parseFloat(jsonparse.longitude);
        var name		= jsonparse.mobilenumber+" "+jsonparse.time;
        var contents 		= jsonparse.name;
        var icons		= 'assets/images/icon/'+iconname;
        var latlng = new google.maps.LatLng(lat, lng);
        console.log(latlng);
        latlngbounds.extend(latlng);
        var marker = new google.maps.Marker({
            position: latlng,
            map: map,
            icon: icons,
            title: name,
            zIndex:1,
            html:contents
        });
        markers.push(marker);
        marker.setMap(map);
    }
    add_info_window(map,markers,contents);
    map.fitBounds (latlngbounds);
    map.setZoom(5);
}

// add window pop up
function add_info_window(map,markers,window_content){
    for (var i = 0; i < markers.length; i++){
        infowindow = new google.maps.InfoWindow({
            content: window_content
        });
        var marker = markers[i];
        google.maps.event.addListener(marker, 'click', function () {
            infowindow.setContent(this.html);
            infowindow.open(map, this);
        });
    }
}

// zoom lavle 15 throu lat lng
function centerPoint(lat, lng){
    var latlng = new google.maps.LatLng(lat, lng);
    map.setCenter(latlng);
    map.setZoom(15);
}

