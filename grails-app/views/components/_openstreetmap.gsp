<g:if test="${!loadOpenStreetMapInPage}">
  <g:set var="loadOpenStreetMapInPage" value="true"/>
  <asset:javascript src="leaflet.js"/>
  <asset:stylesheet src="css/leaflet.css"/>
</g:if>

<div id="mapid" style="height: 20em"></div>

<script>
$(document).ready(function() {
  var map = L.map('mapid').setView([51.505, -0.09], 13);
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
  }).addTo(map);

  L.marker([51.5, -0.09]).addTo(map)
      .bindPopup('A pretty CSS3 popup.<br> Easily customizable.')
      .openPopup();
});
</script>
