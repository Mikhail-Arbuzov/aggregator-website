$(document).ready(function(){
    // инициализация всплывающих подсказок
    $('[data-toggle="tooltip"]').tooltip();
    // отмена для ссылок с атрибутом data-trigger="click" стандартного поведения
    $('a[data-trigger="click"]').click(function(e){
        e.preventDefault();
    });

});