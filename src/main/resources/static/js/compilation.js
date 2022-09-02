(function(){
    const linkUp = document.querySelector(".sizeUp");

    linkUp.addEventListener('click',function(){
        window.scrollTo(pageXOffset, 0);
    });
    window.addEventListener('scroll', function() {
        linkUp.hidden = (pageYOffset < document.documentElement.clientHeight);
    });

    const element1 = document.querySelector(".el1");
    element1.firstElementChild.setAttribute("checked","checked");

    const element2 = document.querySelector(".el2");
    element2.firstElementChild.setAttribute("checked","checked");

    const element3 = document.querySelector(".el3");
    element3.firstElementChild.setAttribute("checked","checked");

    const element4 = document.querySelector(".el4");
    element4.firstElementChild.setAttribute("checked","checked");

    const element5 = document.querySelector(".el5");
    element5.firstElementChild.setAttribute("checked","checked");

    const element6 = document.querySelector(".el6");
    element6.firstElementChild.setAttribute("checked","checked");

    const element7 = document.querySelector(".el7");
    element7.firstElementChild.setAttribute("checked","checked");

    const element8 = document.querySelector(".el8");
    element8.firstElementChild.setAttribute("checked","checked");

    const element9 = document.querySelector(".el9");
    element9.firstElementChild.setAttribute("checked","checked");

    const element10 = document.querySelector(".el10");
    element10.firstElementChild.setAttribute("checked","checked");

    const element11 = document.querySelector(".el11");
    element11.firstElementChild.setAttribute("checked","checked");

    const element12 = document.querySelector(".el12");
    element12.firstElementChild.setAttribute("checked","checked");

    const element13 = document.querySelector(".el13");
    element13.firstElementChild.setAttribute("checked","checked");

    const element14 = document.querySelector(".el14");
    element14.firstElementChild.setAttribute("checked","checked");
})();