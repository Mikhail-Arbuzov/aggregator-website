(function(){

    // меню бургер
    const burger = document.querySelector(".header__burger");
    burger.addEventListener("click", function(e){
        if(!e) e = window.event;
        burger.classList.toggle('active');
        const menu = document.querySelector(".header__menu");
        menu.classList.toggle('active');
        const body = document.querySelector("body");
        body.classList.toggle('lock');
    });

    // функции на разные экраны для выпадающего списка
    const dropdown = document.querySelector(".dropdown");
    const mediaQuery = window.matchMedia("(min-width: 790px)");

    const dt = document.querySelector(".dropdown-toggle");
    const dropmenu = document.querySelector(".dropdown-menu");

    let flag = document.documentElement.clientWidth <790;
    console.log(flag);
    if(flag){
        if(dropdown.addEventListener){
            dropdown.addEventListener("mouseover",removeHandlerMouseOver,true);
            dropdown.addEventListener("mouseout",removeHandlerMouseOut,true);
        }
    }
    else{
        if (dropdown.addEventListener){
            dropdown.addEventListener("mouseover",handlerMouseover,true);
            dropdown.addEventListener("mouseout",handlerMouseout,true);
        }
    }

    window.addEventListener("resize", function(){
        window.location.reload();
    });

    function handlerMouseover(){

        dt.style.color ="#fff";
        dropmenu.style.display ="block";
    }

    function handlerMouseout(){
        dt.style.color ="#000";
        dropmenu.style.display ="none";
    }

    function removeHandlerMouseOver(){
        dropdown.removeEventListener("mouseover",handlerMouseover,true);
    }
    function removeHandlerMouseOut(){
        dropdown.removeEventListener("mouseout",handlerMouseover,true);
    }

    // кнопка поиска

    const searchbtn = document.querySelector(".search");

    searchbtn.addEventListener("click",function(e){
        if(!e) e = window.event;
        searchbtn.classList.toggle('open');
        const searchMenu = document.querySelector(".wd");
        searchMenu.classList.toggle('open');
    })

})();