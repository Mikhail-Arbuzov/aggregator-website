$(document).ready(function() {
    const table1 = document.querySelector(".statistic");
    const table2 = document.querySelector(".tablerating");
    const link = document.querySelector(".link2");
    $('.link').on('click', function() {
        table1.classList.toggle('effect');
    });

    link.addEventListener('click',function(){
        table2.classList.toggle('effect2');
    });
});