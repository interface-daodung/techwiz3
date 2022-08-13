$(function(){
  // check email
  $("#email").keyup(function(){
    if( !emailCheck( $("#email").val()) ){
      $("#emailErr").text("Invalid Email");
    }else if( !$("#email").val() ){
      $("#emailErr").text("Email is required");
    }else{
      $("#emailErr").text("");
    }
  })

  // check password
  $("#password").keyup(function(){
    if( !passCheck( $("#password").val(), $("#repassword").val() )){
      $("#repassErr").text("Password does not matched");
    }else if( $("#password").val() == "" ){
      $("#repassErr").text("Password is required");
    }else{
      $("#repassErr").text("");
    }
  })
  $("#repassword").keyup(function(){
    if( !passCheck( $("#password").val(), $("#repassword").val() )){
      $("#repassErr").text("Password does not matched");
    }else{
      $("#repassErr").text("");
    }
  })

})

function emailCheck(email){
  var pattern = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
  return pattern.test(email);
}

function passCheck(pass,repass){
  res = (pass == repass) ? true : false ;
  return res;
}