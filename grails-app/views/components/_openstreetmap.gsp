<g:if test="${!loadOpenStreetMapInPage}">
  <g:set var="loadOpenStreetMapInPage" value="true"/>
  <asset:javascript src="leaflet.js"/>
  <asset:stylesheet src="css/leaflet.css"/>
</g:if>

<script>
$(document).ready(function() {
  var map = L.map('${id ?: "mimap"}').setView([${longitud}, ${latitud}], 17);
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
  }).addTo(map);

  L.marker([${longitud}, ${latitud}]).addTo(map).bindPopup('${mensaje}').openPopup();
});
</script>

<div id="${id ?: 'mimap'}" style="height: 20em"></div>
