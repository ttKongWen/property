
var tempID;
var tempPassword;
var flag = false;

function selectIdentity(o)
{
	if(o==0)
	{
		flag = true;
		document.getElementById("myForm").action="../student/login";
		document.getElementById("student").style.display="block";
		document.getElementById("admin").style.display="none";
	}
	else if(o==1)
	{
	    alert("static select admin");
		flag = true;
		document.getElementById("myForm").action="http://localhost:8080/admin/login";
        // document.getElementById("myForm").action="http://137.25.154.37:8080/admin/login";
		document.getElementById("student").style.display="none";
		document.getElementById("admin").style.display="block";
	}else{
		flag = false;
	}
}

function login()
{
	alert("static login");
  var url = document.getElementById("myForm").action == "../student/login" ? "../student/login" : "../admin/login";

  var name=document.getElementById("form-username").value;
  var password=document.getElementById("form-password").value;

  var reason;
  var success;

  var xmlhttp;
  if (window.XMLHttpRequest)
  {
    // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
    xmlhttp=new XMLHttpRequest();
  }
  else
  {
    // IE6, IE5 浏览器执行代码
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
  xmlhttp.onreadystatechange=function()
  {
    if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
      var jsonString = JSON.parse(xmlhttp.responseText);
      reason = jsonString.reason;
      success= jsonString.success;
      if(reason==null&&success==true)
 		 {
 		 	var urlArr = url.split('/');
 		 	var newUrl = urlArr[0]+"//"+urlArr[2]+"/"+urlArr[3]+"/dashboard";
  			window.location.href=newUrl+"?reason="+reason+"&success="+success;
 		 }
    }
  }
  xmlhttp.open("POST",url,true);
  xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
  xmlhttp.send("id="+name+"&password="+password);

 
}

function doit()
{
	// alert("static doit");
    var url = "../student/login";

    var xmlhttp;

    if(window.XMLHttpRequest)
    {
        xmlhttp = new XMLHttpRequest();
    }

    else  if(window.ActiveObject)
    {
        xmlhttp = new ActiveObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange = function()
    {
        // alert("onreadystatechange");
        if(xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            if(!flag){
                alert("登陆身份未选择喔...");
                window.location.href="../student/";
            }else if(xmlhttp.responseText.indexOf("invalid user")>=0){
                alert("学号或密码有误呀...");
                window.location.href="../student/";
            }
            else{
                window.location.href="../student/dashboard";
            }
        }
    }
    xmlhttp.open("POST",url,true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("id="+tempID+"&password="+tempPassword);
}


function doitt()
{
    // alert("static doitt");
    tempID=document.getElementById("form-username").value;
    tempPassword=document.getElementById("form-password").value;
    document.getElementById("myForm").submit();
    doit();
}
