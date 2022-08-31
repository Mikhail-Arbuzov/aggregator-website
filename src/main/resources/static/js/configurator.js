$(function(){
    const sumSpan = document.querySelectorAll(".sum");
    const statsTables = document.querySelectorAll(".statsTable");
    let sums = [];
    let number = 0;
    let i = 0;

    for(const table of statsTables){
        const tbody = table.lastElementChild;
        const childsTbody = tbody.children;
        for(const tr of childsTbody){
            const tds = tr.children;
            number += Number(tds[1].textContent);
        }
        sums[i] = number;
        i++;
        number = 0;
    }


    for(let k = 0; k < sumSpan.length; k++ ){
        sumSpan[k].textContent = sums[k];
    }


    $('[data-toggle="popover"]').popover({
        trigger:'hover'
    });
});