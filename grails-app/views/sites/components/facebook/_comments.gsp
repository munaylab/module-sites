<template id="comentarios-template">
  <div class="comentarios">
    <h3 id="comentarios"><g:message code="label.comentarios" default="Comentarios"/>:</h3>
    <div class="fb-comments" :data-href="link" data-width="100%"></div>
  </div>
</template>

<script>
Vue.component('facebookComments', {
  template: '#comentarios-template',
  props: ['link'],
});
</script>
