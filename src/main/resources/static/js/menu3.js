(function(){
    // меню бургер
    const burger3 = document.querySelector(".header3__burger");
    burger3.addEventListener("click", function(e){
        if(!e) e = window.event;
        burger3.classList.toggle('active3');
        const menu3 = document.querySelector(".header3__menu");
        menu3.classList.toggle('active3');
        const body3 = document.querySelector("body");
        body3.classList.toggle('lock3');
    });
})();