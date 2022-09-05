$(document).ready(function(){
    // инициализация всплывающих подсказок
    $('[data-toggle="tooltip"]').tooltip();
    // отмена для ссылок с атрибутом data-trigger="click" стандартного поведения
    $('a[data-trigger="click"]').click(function(e){
        e.preventDefault();
    });

    //для оформления стиля елементов ссылок источников при на видении мыши
    function replacingColor1(colorForH3){
        const divs = document.querySelectorAll('.el__body1');
        for(let div of divs){
            const subtitle = div.firstElementChild;
            subtitle.style.color=colorForH3;
        }
    }

    $('.linkOnAntivSource__el').on('mouseover',function(){
        replacingColor1('#5a6163');
    });

    $('.linkOnAntivSource__el').on('mouseout',function(){
        replacingColor1('#e0e0e0');
    });

});