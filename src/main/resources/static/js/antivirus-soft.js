$(document).ready(function(){
    // инициализация всплывающих подсказок
    $('[data-toggle="tooltip"]').tooltip();
    // отмена для ссылок с атрибутом data-trigger="click" стандартного поведения
    $('a[data-trigger="click"]').click(function(e){
        e.preventDefault();
    });

    //для оформления стиля елементов ссылок источников при на видении мыши
    function replacingColor1(div,colorForH3){
        let elbody1 = div.lastElementChild;
        const subtitle1 = elbody1.firstElementChild;
        subtitle1.style.color=colorForH3;
    }

    $('.linkOnAntivSource__el').on('mouseover',function(){
        replacingColor1(this,'#5a6163');
    });

    $('.linkOnAntivSource__el').on('mouseout',function(){
        replacingColor1(this,'#e0e0e0');
    });

});