
        var map, userMarker;

        function initMap1() {
            map = new mappls.Map('map', {
                center: [17.659920, 75.906387],  // Initial center coordinates (Solapur)
                zoom: 10,  // Zoom level
                zoomControl: true,
                location: true
            });
        }

        function plotUserLocation(position) {
            var userLat = position.coords.latitude;
            var userLng = position.coords.longitude;

            // Create a marker for the user's current location
            userMarker = new mappls.Marker({
                map: map,
                position: {
                    "lat": 18.520430,
                    "lng": 73.856743
                },
                fitbounds: true,
                icon_url: 'https://apis.mapmyindia.com/map_v3/1.png'
            });

            // Center the map on the user's location
            map.setCenter([userLat, userLng]);
            map.setZoom(15);  // Zoom in to the user's location
        }

        function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function(position) {
                    plotUserLocation(position);

                    // Set hidden input values
                    document.getElementById("latitudeInput").value = position.coords.latitude;
                    document.getElementById("longitudeInput").value = position.coords.longitude;
                }, function(error) {
                    console.error("Error getting user's location: ", error);
                    alert("Unable to retrieve your location. Please check your location settings and try again.");
                });
            } else {
                console.error("Geolocation is not supported by this browser.");
                alert("Geolocation is not supported by your browser.");
            }
        }

        // Initialize the map
        initMap1();
    