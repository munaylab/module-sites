const gulp = require('gulp');
const less = require('gulp-less');
const rename = require('gulp-rename');
const uglify = require('gulp-uglify');
const cleanCSS = require('gulp-clean-css');

const dest = {
  jsFolder: 'grails-app/assets/javascripts/',
  cssFolder: 'grails-app/assets/stylesheets/css/',
  fontsFolder: 'grails-app/assets/stylesheets/fonts/'
};

const srcFolder = {
  jsFolder: 'src/assets/javascripts/',
  styleFolder: 'src/assets/stylesheets/'
}

const srcFiles = {
  jsFiles: [ srcFolder.jsFolder + 'sites.js' ],
  cssFiles: [ srcFolder.styleFolder + 'sites.css' ],
  lessFiles: [ srcFolder.styleFolder + 'sites.less' ]
}

const resources = {
  javascripts: [
    'node_modules/vue/dist/vue.min.js',
    'node_modules/marked/lib/marked.js',
    'node_modules/lodash/lodash.min.js',
    'node_modules/jquery/dist/*.min.js',
    'node_modules/jquery.easing/*.min.js',
    'node_modules/leaflet/dist/leaflet.js',
    'node_modules/bootstrap/dist/js/*.min.js',
    'node_modules/scrollreveal/dist/*.min.js'
  ],
  stylesheets: [
    'node_modules/leaflet/dist/leaflet.css',
    'node_modules/bootstrap/dist/css/bootstrap.min.css'
  ],
  fonts: [
    'node_modules/bootstrap/dist/fonts/*'
  ]
};

gulp.task('install-js', function() {
  return gulp.src(resources.javascripts).pipe(gulp.dest(dest.jsFolder));
});
gulp.task('install-css', function() {
  return gulp.src(resources.stylesheets).pipe(gulp.dest(dest.cssFolder));
});
gulp.task('install-fonts', function() {
  return gulp.src(resources.fonts).pipe(gulp.dest(dest.fontsFolder));
});
gulp.task('install', ['install-js', 'install-css', 'install-fonts']);

gulp.task('less', function() {
  return gulp.src(srcFiles.lessFiles)
              .pipe(less())
              .pipe(gulp.dest(srcFolder.styleFolder));
});
gulp.task('minify-css', ['less'], function() {
  return gulp.src(srcFiles.cssFiles)
              .pipe(cleanCSS({ compatibility: 'ie8' }))
              .pipe(rename({ suffix: '.min' }))
              .pipe(gulp.dest(dest.cssFolder));
});

gulp.task('js', function() {
  return gulp.src(srcFiles.jsFiles)
              .pipe(gulp.dest(dest.jsFolder));
});
gulp.task('minify-js', ['js'], function() {
  return gulp.src(srcFiles.jsFiles)
              .pipe(uglify())
              .pipe(rename({ suffix: '.min' }))
              .pipe(gulp.dest(dest.jsFolder));
});

gulp.task('dev', ['default'], function() {
  gulp.watch(srcFolder.jsFolder + '*.js', ['js']);
  gulp.watch(srcFolder.styleFolder + '*.less', ['minify-css']);
});

gulp.task('minify', ['minify-js', 'minify-css']);

gulp.task('default', ['install', 'minify']);
