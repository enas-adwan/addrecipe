<?php 
require "conn.php";
$id = $_POST["id"];
$sql = "select name,gender,picture from facebook where id='$id'";
$res = mysqli_query($conn,$sql);
$result = array();
while($row = mysqli_fetch_array($res)){
array_push($result,
array('name'=>$row[0],
	'gender'=>$row[1],
	'picture'=>$row[2]
));
}
echo json_encode(array("result"=>$result));
mysqli_close($conn);
?>
