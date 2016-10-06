 <?php 
 $title=$_POST['title'];
  $rating=$_POST['rating'];
$con=mysqli_connect("localhost","root","","project");

$i= "SELECT * FROM recipe  where title like '$title'";
//$row = mysqli_fetch_array(mysqli_query($con,$i));
$r = $con->query($i);
$result = array();
  
while($row = mysqli_fetch_array($r)){

$totalrating=$row['totalrating'];
$totalratingpeople=$row['totalratingpeople'];

}


$totalrating=$totalrating+ $rating;
$totalratingpeople=$totalratingpeople+1;
$ii= "UPDATE recipe  SET totalrating='$totalrating', totalratingpeople='$totalratingpeople'
WHERE title='$title'";
//$row = mysqli_fetch_array(mysqli_query($con,$i));
$r = $con->query($ii);
mysqli_close($con);

?>