<template id="articulo-template">
  <section :id="id" class="container" :class="sectionClass">

    <div v-if="background" ref="background" class="fondo"></div>

    <div class="columna-1" :class="imagen ? 'col-md-6' : 'col-md-12'">
      <h1 class="title-animation">
        <span v-if="bookmark" class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>
        {{ titulo }}
      </h1>
      <div class="content-animation" v-html="markdown"></div>
    </div>

    <div v-if="imagen" class="columna-2 col-md-6 text-center">
      <img class="img-rounded img-responsive image-animation" :src="imagen" />

      <div class="botones" v-if="linkPrincipal || linkOpcional || linkSecundario">
        <a v-if="linkOpcional" :href="linkOpcional" v-html="tituloOpcional" class="btn btn-default"></a>
        <a v-if="linkPrincipal" :href="linkPrincipal" v-html="tituloPrincipal" class="btn btn-default btn-primary"></a>
        <a v-if="linkSecundario" :href="linkSecundario" v-html="tituloSecundario" class="btn btn-default"></a>
      </div>
    </div>

    <div v-if="landing">
      <ul class="objetivos">
        <g:each in="${org.objetivos}" var="obj">
        <li>
          <img nombre="${obj.nombre}" src="/assets/${obj.nombreDeArchivo}" width="50" />
        </li>
        </g:each>
      </ul>
    </div>

  </section>
</template>

<script>
Vue.component('articulo', {
  template: '#articulo-template',
  props: [
          'id',               // ID
          'titulo',           // titulo
          'contenido',        // contenido markdown format
          'imagen',           // imagen url
          'background',       // imagen fondo
          'invertir',         // estilo invertido
          'bookmark',         // marca de seccion
          'landing',          // es la landing
          'linkOpcional',     // link de accion opcional
          'tituloOpcional',   // titulo de accion opcional
          'linkPrincipal',    // link de accion principal
          'tituloPrincipal',  // titulo de accion principal
          'linkSecundario',   // link de accion secundaria
          'tituloSecundario'  // titulo de accion secundaria
        ],
  mounted: function() {
    let background = this.$refs.background;
    if (background && this.background) {
      background.style.backgroundImage = "url('" + this.background + "')";
    }
  },
  computed: {
    sectionClass: function() {
      if (this.landing && this.background) {
        return 'imagen';
      } else if (this.invertir) {
        return this.invertir === 'true' ? 'inverted' : '';
      } else {
        return ''
      }
    },
    markdown: function () {
      return marked(this.contenido);
    }
  }
});
</script>

<style>
ul.objetivos {
  margin: 0;
  padding: 0;
  list-style-type: none;
  text-align: center;
}
ul.objetivos li {
  display: inline-block;
  font-size: 20px;
  padding: 20px;
}
</style>
