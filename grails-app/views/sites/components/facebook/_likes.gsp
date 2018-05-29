<template id="likes-template">
  <div>
    <div class="articulo-likes">
      <div
          class="fb-like"
          :data-href="link"
          data-layout="standard"
          data-action="like"
          data-size="large"
          data-show-faces="true"
          data-share="true">
      </div>
    </div>
  </div>
</template>

<script>
Vue.component('facebookLikes', {
  template: '#likes-template',
  props: ['link'],
});
</script>
