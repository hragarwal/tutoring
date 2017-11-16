$(document).ready(function($) {
   // $(".button-collapse").sideNav();
  //  $(".dropdown-content").css('top', 'initial');
	

	// $('#OpenImgUpload').click(function(){ $('#imgupload').trigger('click'); });  
	
	var wow = new WOW({
		mobile: false
	});
	wow.init();

	var waypoints = $('#funfactor').waypoint(function(direction) {
		if (direction = 'up') {
			$(".timer").countTo({
				speed: 5000
			})
		}
	},{
		offset: '60%'
	});
	
	$(document).on('focusin', '.form-control',  function(e){
		$(this).closest(".form-group").addClass("is-focused");
	}).on('focusout', '.form-control',  function(e){
		$(this).closest(".form-group").removeClass("is-focused");
	});	
});

