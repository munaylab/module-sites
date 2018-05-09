$(document).ready(function() {
  window.sr = ScrollReveal();
  sr.reveal(document.querySelectorAll('section h2'),
      { distance: '50px', origin: 'left', delay: 0 });
  sr.reveal(document.querySelectorAll('section .html'),
      { distance: '50px', origin: 'bottom', delay: 100 });
  sr.reveal(document.querySelectorAll('section img'),
      { distance: '50px', origin: 'right', delay: 200 });

  $(window).scroll(function () {
    if ($(this).scrollTop() > 50) {
      $('#back-to-top').fadeIn();
    } else {
      $('#back-to-top').fadeOut();
    }
  });
  $('#back-to-top').click(function () {
      $('body, html').animate({ scrollTop: 0 }, 800);
      return false;
  });

});
