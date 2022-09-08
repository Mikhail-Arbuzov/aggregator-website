$(document).ready(function(){
    //кнопка в верх
    const linkUpThree = document.querySelector(".sizeUp3");
    linkUpThree.addEventListener('click',function(){
        window.scrollTo(pageXOffset, 0);
    });

    window.addEventListener('scroll', function() {
        linkUpThree.hidden = (pageYOffset < document.documentElement.clientHeight);
    });

    //для оформления стиля елементов ссылок источников при на видении мыши
    function replacingColor2(div,colorForH4){
        let videotxt = div.lastElementChild;
        const subtitle = videotxt.firstElementChild;
        subtitle.style.color=colorForH4;
    }


    $('.linkOnVideolink__video').on('mouseover',function(){
        replacingColor2(this,'#f7c10a');
    });

    $('.linkOnVideolink__video').on('mouseout',function(){
        replacingColor2(this,'#e0e0e0');

    });

});