

function toggleSearchForm(){
  if( $("#search-form").css('display') == "none" ){
    $("#search-form").css('display','inline');
  }else{
    $("#search-form").css('display','none');
  }
};


$(function(){
  $("head").append("<base href='" + window.location.host +"'/EXAM/ />")

// render navbar and bottom part
  $.get("components/navbar.html", function(data,status){
    $(".navbar").html(data);
  });
  $.get("components/bottom.html", function(data,status){
    $(".bottom-part").html(data);
  });
})

$(function(){
  // check contact name
  $("#contact-name").keyup(function(){
    if( $("#contact-name").val()){
      $("#required-name").hide()
    }else{
      $("#required-name").show()
    }
  })
  // check contact mail
  $("#contact-mail").keyup(function(){
    if( $("#contact-mail").val()){
      $("#required-mail").hide()
    }else{
      $("#required-mail").show()
    }
  })
})
