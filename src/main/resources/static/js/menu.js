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

    // кнопка поиска

    const searchbtn = document.querySelector(".search");

    searchbtn.addEventListener("click",function(e){
        if(!e) e = window.event;
        searchbtn.classList.toggle('open');
        const searchMenu = document.querySelector(".wd");
        searchMenu.classList.toggle('open');
    });

    // функционал поиска
    const input = document.querySelector("#elastic");
    const btn = document.querySelector(".btnSearch");
    const divs = document.querySelectorAll(".hide");
    const notdata = document.querySelector(".notfound");
    const data = document.querySelector(".notf");
    btn.addEventListener("click",function(){
        let val = input.value.trim();
        if(val != ''){
            data.textContent = val;
            notdata.classList.add('visible');
            input.value='';
            for(let div of divs){
                const childDiv = div.firstElementChild;
                const items = childDiv.firstElementChild;
                const itemBody = items.lastElementChild;
                const itemLink = itemBody.firstElementChild;
                const a = itemLink.firstElementChild;
                if(a.innerText.search(val) == -1){
                    switch(a.textContent){
                        case 'Процессор':{
                            if(val =='процессор'| val == 'Intel'| val == 'AMD'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case 'Материнская плата':{
                            if(val =='материнская плата'| val == 'материнская'| val == 'материнка'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case 'Кулер для процессора':{
                            if(val =='кулер'| val == 'кулер для процессора'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case 'Блок питания':{
                            if(val =='блок питания'| val == 'блок'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case 'Видеокарта':{
                            if(val =='видеокарта'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case 'Жёсткий диск':{
                            if(val =='жёсткий диск'| val == 'жесткий диск'| val == 'винчестер' | val == 'hdd'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case 'Твердотельный накопитель':{
                            if(val =='твердотельный накопитель'| val == 'SSD'| val == 'ssd'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case 'Оперативная память':{
                            if(val =='oперативная память'| val == 'оперативка'| val == 'модули памяти'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case 'Кулер для корпуса':{
                            if(val =='кулер для корпуса'| val == 'кулер'| val == 'вентилятор'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case 'Корпус':{
                            if(val =='корпус'| val == 'системник'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case 'Монитор':{
                            if(val =='монитор'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case 'Клавиатура':{
                            if(val =='клавиатура'| val == 'клава' | val == 'keyboard'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case 'Компьютерная мышь':{
                            if(val =='компьютерная мышь'| val == 'мышка' | val == 'mouse'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case 'МФУ':{
                            if(val =='мфу'| val == 'сканер' | val == 'принтер'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case 'Антивирусная программа':{
                            if(val =='антивирусная программа'| val == 'антивирус'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case'Офисная программа':{
                            if(val =='офисная программа'| val == 'Microsoft Office' | val == 'office'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case'Операционная система':{
                            if(val =='операционная система'| val == 'операционка'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                        case'Программное обеспечение':{
                            if(val =='программное обеспечение'| val == 'софт' | val == 'Софт'){
                                div.classList.add('visi');
                                notdata.classList.remove('visible');
                                input.value='';
                            }
                            else{
                                div.classList.remove('visi');
                            }
                        }
                            break;
                    }
                }
                else{
                    div.classList.add('visi');
                    notdata.classList.remove('visible');
                    input.value='';
                }
            }
        }
        else{
            for(let div of divs){
                div.classList.remove('visi');
                notdata.classList.remove('visible');
            }
        }
    });

    // функции на разные экраны для выпадающего списка
    const dropdown = document.querySelector(".dropdown");
    const mediaQuery = window.matchMedia("(min-width: 790px)");

    const dt = document.querySelector(".dropdown-toggle");
    const dropmenu = document.querySelector(".dropdown-menu");

    let flag = document.documentElement.clientWidth < 790;
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



})();