<template id="articulo-card-template">
  <div class="card">

    <div v-if="imagen" class="card-image">
      <img class="img-responsive" :src="imagen">
    </div>

    <div class="card-content">
      <p class="date" v-html="fecha"></p>
      <h2 v-html="titulo"></h2>
      <hr>
      <p class="text" v-html="resumenWrap"></p>
    </div>

    <div class="card-action">
      <a :href="link">Continuar leyendo...</a>
    </div>

  </div>
</template>

<script>
Vue.component('articuloCard', {
  template: '#articulo-card-template',
  props: ['titulo', 'imagen', 'descripcion', 'fecha', 'link'],
  computed: {
    resumenWrap: function() {
      let resumen = this.descripcion;
      if (resumen.length < 300) {
        return resumen;
      } else {
        resumen = resumen.substring(0, 300)
        return resumen + "...";
      }
    }
  }
});
</script>
