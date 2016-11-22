<?php
header('Content-Type: bitmap; charset=utf-8');
if($_POST['secret'] != '3CH6knCsenas2va8GrHk4mf3JqmUctCM') {


  exit("Access denied");
}
$title = $_POST['title'];

$calory = $_POST['calory'];
$desc = $_POST['desc'];
$list = $_POST['list'];
$total = $_POST['total'];
$prep = $_POST['prep'];
$cook = $_POST['cook'];
$video = $_POST['video'];
$image = $_POST['image'];
$vitc = $_POST['vitc'];
$pro = $_POST['pro'];
$iron = $_POST['iron'];
$calc = $_POST['calc'];
$vitb6 = $_POST['vitb6'];
$vitb12 = $_POST['vitb12'];
$vite = $_POST['vite'];

$type = $_POST['type'];

$nameimage = $_POST['nameimage'];
$decodedImage = base64_decode($image);
//upload the image
    file_put_contents("androidimages/".$nameimage.".jpg", $decodedImage);
    $imagename="".$nameimage.".jpg";

if($title == '' ||  $calory == ''|| $desc == ''||$list=='' ||$prep==''||$cook==''){
echo 'please fill all values';
}else{

 

$con=mysqli_connect("localhost","root","","project");

$sql = "SELECT * FROM recipe WHERE title='$title' ";

$check = mysqli_fetch_array(mysqli_query($con,$sql));

if(isset($check)){
echo 'title already exist';
}else{
if($type=="app" ){
$user_key =  $_POST['user_key'];
$sql = "INSERT INTO recipe (title,calory,descc,list,prep,cook,total,image,video,user_key,type,vitc,pro,iron,calc,vitb6,vitb12,vite) VALUES('$title','$calory','$desc','$list','$prep','$cook','$total','$imagename','$video','$user_key','$type','$vitc','$pro','$iron','$calc','$vitb6','$vitb12','$vite')";
}else{
$face_id =$_POST['face_id'];
$sql = "INSERT INTO recipe (title,calory,descc,list,prep,cook,total,image,video,face_id,type,vitc,pro,iron,calc,vitb6,vitb12,vite) VALUES('$title','$calory','$desc','$list','$prep','$cook','$total','$imagename','$video','$face_id','$type','$vitc','$pro','$iron','$calc','$vitb6','$vitb12','$vite')";
}
if(!$sql){
echo "err";
}
if(mysqli_query($con,$sql)){
echo 'successfully added';
}else{
echo 'oops! Please try again!';
}
}
mysqli_close($con);
}
?>
