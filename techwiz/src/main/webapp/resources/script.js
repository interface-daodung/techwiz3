

function toggleSearchForm(){
  if( $("#search-form").css('display') == "none" ){
    $("#search-form").css('display','inline');
  }else{
    $("#search-form").css('display','none');
  }
};


$(function(){
  $("head").append("<base href='" + window.location.host +"'/EXAM/ />")


  $.get("components/navbar.html", function(data,status){
    $(".navbar").html(data);
  });
  $.get("components/bottom.html", function(data,status){
    $(".bottom-part").html(data);
  });
  // add to head elements
  $("head").append('<meta name="viewport" content="width=device-width, initial-scale=1.0">');
  $("head").append('<link rel="stylesheet" href="resources/style.css">');
  $("head").append('<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">');
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
