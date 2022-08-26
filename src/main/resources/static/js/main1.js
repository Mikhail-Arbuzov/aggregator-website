$(document).ready(function(){

    const media1 = document.getElementById("media1");

    const media2 = document.getElementById("media2");

    const media3 = document.getElementById("media3");

    const media4 = document.getElementById("media4");

    const media5 = document.getElementById("media5");

    const media6 = document.getElementById("media6");

    const blocks = [media1,media2,media3,media4,media5,media6];


    function replacingImageAndText(path1,text1,linkPath1, path2,text2,linkPath2,path3,text3,linkPath3,path4,text4,linkPath4){
        var owlItems = document.querySelectorAll('.owl-item');
        for(let owlitem of owlItems){
            const index1 = $(owlitem).closest('.owl-stage').children().index(owlitem);
            const childs1 = owlitem.children;
            for(let child of childs1){
                const link = child.firstElementChild;
                const title = child.lastElementChild;

                switch(index1){
                    case 3:{
                        link.href = linkPath4;
                        const image3 = link.firstElementChild;
                        image3.src = path4;
                        const referenc3 = title.firstElementChild;
                        referenc3.href = linkPath4;
                        referenc3.textContent=text4;
                    }
                        break;
                    case 4:{
                        link.href = linkPath1;
                        const image4 = link.firstElementChild;
                        image4.src = path1;
                        const referenc4 = title.firstElementChild;
                        referenc4.href = linkPath1;
                        referenc4.textContent=text1;
                    }
                        break;
                    case 5:{
                        link.href = linkPath2;
                        const image5 = link.firstElementChild;
                        image5.src = path2;
                        const referenc5 = title.firstElementChild;
                        referenc5.href = linkPath2;
                        referenc5.textContent=text2;
                    }
                        break;
                    case 6:{
                        link.href = linkPath3;
                        const image6 = link.firstElementChild;
                        image6.src = path3;
                        const referenc6 = title.firstElementChild;
                        referenc6.href = linkPath3;
                        referenc6.textContent=text3;
                    }
                        break;
                }
            }
            console.log(index1);
            console.log(owlitem);
        }
    }

    function replacingImageAndTextForClone(path1,text1,linkPath1,path2,text2,linkPath2,path3,text3,linkPath3,path4,text4,linkPath4){
        var owlItems = document.querySelectorAll('.cloned');
        for(let owlitemClone of owlItems){
            const index = $(owlitemClone).closest('.owl-stage').children().index(owlitemClone);
            const childs = owlitemClone.children;
            for(let childClone of childs){
                const a = childClone.firstElementChild;
                const h3 = childClone.lastElementChild;
                switch(index){
                    case 0:{
                        a.href = linkPath1
                        const image = a.firstElementChild;
                        image.src = path1;
                        const referenc = h3.firstElementChild;
                        referenc.href = linkPath1;
                        referenc.textContent=text1;
                    }
                        break;
                    case 1:{
                        a.href = linkPath2;
                        const image1 = a.firstElementChild;
                        image1.src = path2;
                        const referenc1 = h3.firstElementChild;
                        referenc1.href = linkPath2;
                        referenc1.textContent=text2;
                    }
                        break;
                    case 2:{
                        a.href = linkPath3;
                        const image2 = a.firstElementChild;
                        image2.src = path3;
                        const referenc2 = h3.firstElementChild;
                        referenc2.href = linkPath3;
                        referenc2.textContent=text3;
                    }
                        break;
                    case 7:{
                        a.href = linkPath4;
                        const image7 = a.firstElementChild;
                        image7.src = path4;
                        const referenc7 = h3.firstElementChild;
                        referenc7.href = linkPath4;
                        referenc7.textContent=text4;
                    }
                        break;
                    case 8:{
                        a.href = linkPath1;
                        const image8 = a.firstElementChild;
                        image8.src = path1;
                        const referenc8 = h3.firstElementChild;
                        referenc8.href = linkPath1;
                        referenc8.textContent=text1;
                    }
                        break;
                    case 9:{
                        a.href = linkPath2;
                        const image9 = a.firstElementChild;
                        image9.src = path2;
                        const referenc9 = h3.firstElementChild;
                        referenc9.href = linkPath2;
                        referenc9.textContent=text2;
                    }
                        break;
                }
            }
            // console.log(index);
            // console.log(owlitemClone);
        }
    }

    function replacingImageAndTextByIdTwo(path,text,linkPath){
        var owls = document.querySelectorAll('.owl-item');
        for(let owl of owls){
            if(owl.classList.contains('cloned')!=true){
                const i = $(owl).closest('.owl-stage').children().index(owl);
                const divs = owl.children;
                for(let div of divs){
                    const linkA = div.firstElementChild;
                    const titleH3 = div.lastElementChild;
                    if(i == 2){
                        linkA.href = linkPath;
                        const img = linkA.firstElementChild;
                        img.src =path;
                        const referenc2 = titleH3.firstElementChild;
                        referenc2.href = linkPath;
                        referenc2.textContent=text;
                    }
                }
            }
        }
    }


    function removeByIdElement(trigger1) {
        var $item = $(trigger1).closest('.owl-item');
        var index = $item.closest('.owl-stage').children().index($item);
        if(trigger1.hasAttribute('id')){
            $('.owl-2').trigger('remove.owl.carousel', index).trigger('refresh.owl.carousel');
        }
    }

    function removeAllElements(item) {
        var $item2 = $(item).closest('.owl-item');
        var index2 = $item2.closest('.owl-stage').children().index($item2);
        $('.owl-2').trigger('remove.owl.carousel', index2).trigger('refresh.owl.carousel');
    }


    const elem1 = '<div class="media-29101"id="media1"><a href="/allForPC/ssd" target="_blank"><img src="img/sistemblock/ssd.jpg"alt="Image"class="img-fluid"></a><h3><a href="/allForPC/ssd"class="link-style1" target="_blank">Твердотельный накопитель</a></h3></div>';
    const elem2 = '<div class="media-29101"id="media2"><a href="/allForPC/hdd" target="_blank"><img src="img/sistemblock/hdd.jpg"alt="Image"class="img-fluid"></a><h3><a href="/allForPC/hdd"class="link-style1" target="_blank">Жесткий диск</a></h3></div>';
    const elem3 = '<div class="media-29101"id="media3"><a href="/allForPC/videoCard" target="_blank"><img src="img/sistemblock/videokard.jpg"alt="Image"class="img-fluid"></a><h3><a href="/allForPC/videoCard"class="link-style1" target="_blank">Видеокарта</a></h3></div>';
    const elem4 = '<div class="media-29101"id="media4"><a href="/allForPC/powerSupply" target="_blank"><img src="img/sistemblock/blockpit.jpeg"alt="Image"class="img-fluid"></a><h3><a href="/allForPC/powerSupply"class="link-style1" target="_blank">Блок питания</a></h3></div>';
    const elem5 = '<div class="media-29101"id="media5"><a href="/allForPC/cooler-corpus" target="_blank"><img src="img/sistemblock/kullerkorp.jpg"alt="Image"class="img-fluid"></a><h3><a href="/allForPC/cooler-corpus"class="link-style1" target="_blank">Кулер для корпуса</a></h3></div>';
    const elem6 = '<div class="media-29101"id="media6"><a href="/allForPC/corpus" target="_blank"><img src="img/sistemblock/korpus.jpg"alt="Image"class="img-fluid"></a><h3><a href="/allForPC/corpus"class="link-style1" target="_blank">Корпус</a></h3></div>';
    const elem7='<div class="media-29101" ><a href="/allForPC/ram" target="_blank"><img src="img/sistemblock/operativ.jpg"alt="Image"class="img-fluid"></a><h3><a href="/allForPC/ram"class="link-style1" target="_blank">Оперативная память</a></h3></div>';
    const elem8 ='<div class="media-29101" ><a href="/allForPC/cooler-cpu" target="_blank"><img src="img/sistemblock/kullerpr.jpg" alt="Image"class="img-fluid"></a><h3><a href="/allForPC/cooler-cpu" class="link-style1" target="_blank">Кулер для процессора</a></h3></div>';
    const elem9 ='<div class="media-29101" ><a href="/allForPC/motherboard" target="_blank"><img src="img/sistemblock/matpl.jpg"alt="Image"class="img-fluid"></a><h3><a href="/allForPC/motherboard"class="link-style1" target="_blank">Материнская плата</a></h3></div>';
    const elem10 ='<div class="media-29101" ><a href="/allForPC/processor" target="_blank"><img src="img/sistemblock/proces.jpg" alt="Image"class="img-fluid"></a><h3><a href="/allForPC/processor" class="link-style1" target="_blank">Процессор</a></h3></div>';


    const arrElems =[elem1,elem2,elem3,elem4,elem5,elem6,elem7,elem8,elem9,elem10];
    const madia29101 = document.querySelectorAll('.media-29101');

    $('#btn1').on('mousedown',function(){
        $('#btn1').addClass('specialeffect');
    });

    $('#btn1').on('mouseup',function(){
        $('#btn1').removeClass('specialeffect');
    });


    $('#btn1').on('click',function(){

        var count3 = $(".owl-item").children().length;

        if(count3 !=20 | count3 < 10){
            for(let media of madia29101){
                removeAllElements(media);
            }
            for(let elem of arrElems){
                $('.owl-2').trigger('add.owl.carousel', [$(elem), 0]).trigger('refresh.owl.carousel');
            }
        }

    });

    $('#btn2').on('mousedown',function(){
        $('#btn2').addClass('specialeffect');
    });

    $('#btn2').on('mouseup',function(){
        $('#btn2').removeClass('specialeffect');
    });


    $('#btn2').on('click',function(){

        var count = $(".owl-item").children().length;

        if(count == 20 | count > 10){
            for(let block of blocks){
                removeByIdElement(block);
            }
        }

        if(count >= 10){
            replacingImageAndTextForClone('img/periferia/klaviatura.png','Клавиатура','/allForPC/keyboard','img/periferia/mause.png','Компьютерная мышка','/allForPC/mouse','img/periferia/mfu.png','МФУ','/allForPC/mfd','img/periferia/monitor.png','Монитор','/allForPC/monitor');
        }

        if(count >=8){
            replacingImageAndTextForClone('img/periferia/klaviatura.png','Клавиатура','/allForPC/keyboard','img/periferia/mause.png','Компьютерная мышка','/allForPC/mouse','img/periferia/mfu.png','МФУ','/allForPC/mfd','img/periferia/monitor.png','Монитор','/allForPC/monitor');
            replacingImageAndTextByIdTwo('img/periferia/mfu.png','МФУ','/allForPC/mfd');
        }

        replacingImageAndText('img/periferia/klaviatura.png','Клавиатура','/allForPC/keyboard','img/periferia/mause.png','Компьютерная мышка','/allForPC/mouse','img/periferia/mfu.png','МФУ','/allForPC/mfd','img/periferia/monitor.png','Монитор','/allForPC/monitor');

    });

    $('#btn3').on('mousedown',function(){
        // effectWhenPressed('#btn3');
        $('#btn3').addClass('specialeffect');
    });

    $('#btn3').on('mouseup',function(){
        $('#btn3').removeClass('specialeffect');
    });


    $('#btn3').on('click',function(){
        var count2 = $(".owl-item").children().length;
        if(count2 == 20 | count2 > 10){
            for(let block of blocks){
                removeByIdElement(block);
            }
        }

        if(count2 >=10){
            replacingImageAndTextForClone('img/po/soft.jpg','Различный софт','/allForPC/soft','img/po/antivirus.jpg','Антивирусная программа','/allForPC/antivirus-soft','img/po/ofic.jpg','Офисные программы','/allForPC/officeProgram','img/po/operac.jpg','Операционная система','/allForPC/operative');
        }

        if(count2 >=8){
            replacingImageAndTextForClone('img/po/soft.jpg','Различный софт','/allForPC/soft','img/po/antivirus.jpg','Антивирусная программа','/allForPC/antivirus-soft','img/po/ofic.jpg','Офисные программы','/allForPC/officeProgram','img/po/operac.jpg','Операционная система','/allForPC/operative');
            replacingImageAndTextByIdTwo('img/po/ofic.jpg','Офисные программы','/allForPC/officeProgram');
        }
        replacingImageAndText('img/po/soft.jpg','Различный софт','/allForPC/soft','img/po/antivirus.jpg','Антивирусная программа','/allForPC/antivirus-soft','img/po/ofic.jpg','Офисные программы','/allForPC/officeProgram','img/po/operac.jpg','Операционная система','/allForPC/operative');
    });


//style link

    function replacingColor(colorForH3,colorForP){
        const divs = document.querySelectorAll('.element__body');
        for(let div of divs){
            const subtitle = div.firstElementChild;
            const paragraph = div.lastElementChild;

            subtitle.style.color=colorForH3;
            paragraph.style.color=colorForP;
        }
    }

    $('.preferens__element').on('mouseover',function(){
        replacingColor('#fff','#1c2478');
    });

    $('.preferens__element').on('mouseout',function(){
        replacingColor('rgba(146,206,249,1)','#d6dae1');
    });

});