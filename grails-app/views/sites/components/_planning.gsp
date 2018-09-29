<template id="planning-template">
  <section :id="id" class="container">
    <div class="content-animation" v-html="markdown"></div>
  </section>
</template>

<script>
Vue.component('planning', {
  template: '#planning-template',
  props: ['id', 'contenido'],
  computed: {
    markdown: function () {
      return marked(this.contenido);
    }
  }
});
</script>
