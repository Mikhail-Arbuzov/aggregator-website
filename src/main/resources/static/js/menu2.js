(function(){
		// меню бургер
	const burger2 = document.querySelector(".header2__burger");
	burger2.addEventListener("click", function(e){
		if(!e) e = window.event;
		burger2.classList.toggle('active2');
		const menu2 = document.querySelector(".header2__menu");
		menu2.classList.toggle('active2');
		const body2 = document.querySelector("body");
		body2.classList.toggle('lock2');
	});
})();