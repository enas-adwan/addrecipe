<?php
if($_POST['secret'] != '3CH6knCsenas2va8GrHk4mf3JqmUctCM') {


    exit("Access denied");
}
$name = $_POST['name'];
$gender = $_POST['gender'];
$email=$_POST['email'];

$password = $_POST['password'];


if($name == '' ||  $password == '' ||$email==''){
echo 'please fill all values';
}else{


$con=mysqli_connect("localhost","root","","project");

$result = mysqli_query($con,"SELECT * FROM project WHERE email='$email'");

    /* determine number of rows result set */
   if( $result->num_rows>0)
   {
 

echo ' email already exist';
}
else{
$sql = "INSERT INTO project (name,password,gender,email) VALUES('$name','$password','$gender','$email')";
if(mysqli_query($con,$sql)){
echo 'successfully registered';
}else{
echo 'oops! Please try again!';
}
}
mysqli_close($con);
}
?>
