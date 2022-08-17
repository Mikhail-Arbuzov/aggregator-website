$(document).ready(function() {
    const table1 = document.querySelector(".statistic");
    const table2 = document.querySelector(".tablerating");
    // const link = document.querySelector(".link2");
    // const valuesId = document.querySelectorAll(".transferId");
    const inputResult = document.querySelector(".result");
    // const spanTransferSiteNames = document.querySelectorAll(".transferSiteName");
    const siteName = document.querySelector(".domenName");
    $('.link').on('click', function() {
        table1.classList.toggle('effect');
        const spans = $(this).children();
        inputResult.value = spans[0].innerText;
        siteName.textContent = spans[1].innerText;
    });


   // link.addEventListener('click',function(){
   //      table2.classList.toggle('effect2');
   //  });
    $('.link2').on('click',function () {
        table2.classList.toggle('effect2');
    })

});