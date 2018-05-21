<template id="articulo-template">
  <section :id="id" class="container" :class="sectionClass">


    <div v-if="imagen && landing" class="fondo"></div>

    <div class="row">

      <div class="columna-1" :class="imagen ? 'col-md-6' : 'col-md-12'">
        <h1 v-if="landing" class="title-animation" v-html="titulo"></h1>
        <h2 v-else class="title-animation">
          <span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>
          {{ titulo }}
        </h2>
        <div class="content-animation" v-html="markdown"></div>
      </div>

      <div v-if="imagen" class="columna-2 col-md-6 text-center">
        <img class="img-responsive image-animation" :src="imagen" />

        <div class="botones" v-if="linkPrincipal || linkOpcional || linkSecundario">
          <a v-if="linkOpcional" :href="linkOpcional" v-html="tituloOpcional" class="btn btn-default"></a>
          <a v-if="linkPrincipal" :href="linkPrincipal" v-html="tituloPrincipal" class="btn btn-default btn-primary"></a>
          <a v-if="linkSecundario" :href="linkSecundario" v-html="tituloSecundario" class="btn btn-default"></a>
        </div>
      </div>

    </div>

  </section>
</template>

<script>
Vue.component('articulo', {
  template: '#articulo-template',
  props: ['id', 'titulo', 'contenido', 'imagen', 'invertir', 'landing',
      'linkOpcional', 'tituloOpcional', 'linkPrincipal', 'tituloPrincipal',
      'linkSecundario', 'tituloSecundario'],
  computed: {
    sectionClass: function() {
      if (this.landing && this.imagen) {
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
