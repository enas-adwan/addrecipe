<?php 
require "conn.php";
$id = $_POST["id"];
$name = $_POST["name"];
$gender = $_POST["gender"];
$pictur = $_POST["pictur"];

$result1 =$conn->query("SELECT id FROM facebook where id='$id'");
$num_rows = $result1->num_rows;
echo $num_rows;
if($num_rows==0){

$mysql_qry="insert into facebook (id,name,gender,picture) values ('$id','$name','$gender','$picture')";
$result =$conn->query($mysql_qry);

if($result === TRUE) {
echo "success";
}
else {
echo "Error: " . $mysql_qry . "<br>" . $conn->error;
}

}
else {echo"user exist";}
?>