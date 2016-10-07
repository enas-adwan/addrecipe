<?php 
require "conn.php";
$name = $_POST["name"];
$user_pass = $_POST["password"];
$mysql_qry = "select * from project where name like '$name' and password like '$user_pass';";
$result = mysqli_query($conn ,$mysql_qry);
if(mysqli_num_rows($result) > 0) {
echo "ok";
}
else {
echo "login not success";
}

?>