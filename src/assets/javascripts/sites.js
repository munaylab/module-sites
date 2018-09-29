$(document).ready(function() {
  window.sr = ScrollReveal();
  sr.reveal(document.querySelectorAll('section .title-animation'),
      { distance: '50px', origin: 'left', delay: 0 });
  sr.reveal(document.querySelectorAll('section .content-animation'),
      { distance: '50px', origin: 'bottom', delay: 100 });
  sr.reveal(document.querySelectorAll('section .image-animation'),
      { distance: '50px', origin: 'right', delay: 200 });

  $(window).scroll(function () {
    if ($(this).scrollTop() > 50) {
      $('#to-top').fadeIn();
    } else {
      $('#to-top').fadeOut();
    }
  });
  $('#to-top').click(function () {
      $('body, html').animate({ scrollTop: 0 }, 800);
      return false;
  });


});

var vm = new Vue({ el: '#wrapper' });
