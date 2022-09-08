$(document).ready(function(){
    // инициализация всплывающих подсказок
    $('[data-toggle="popover"]').popover({
        trigger:'hover'
    });

//кнопка в верх
    const linkUpTwo = document.querySelector(".sizeUp2");
    linkUpTwo.addEventListener('click',function(){
        window.scrollTo(pageXOffset, 0);
    });

    window.addEventListener('scroll', function() {
        linkUpTwo.hidden = (pageYOffset < document.documentElement.clientHeight);
    });

});