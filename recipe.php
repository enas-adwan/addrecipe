 <?php 
 $title=$_POST['title'];
$con=mysqli_connect("localhost","root","","project");

$i= "SELECT * FROM recipe  where title like '$title'";
//$row = mysqli_fetch_array(mysqli_query($con,$i));
$r = $con->query($i);
$result = array();
  
while($row = mysqli_fetch_array($r)){
$totalrating=$row['totalrating'];
$totalratingpeople=$row['totalratingpeople'];
if($totalratingpeople=='0' and $totalrating=='0'){
$rating='0';
}else{

$rating=$totalrating/$totalratingpeople;}
$im = file_get_contents('androidimages/'.$row['image']);
 $imdata = base64_encode($im);  
 $type =$row['type'] ;  
 if($type=="face"){
 $face_id=$row['face_id'];
 $q= "SELECT * FROM project  where face_id like '$face_id'";
 $rq = $con->query($q);
 while($roww = mysqli_fetch_array($rq)){
$nameuer=$roww['name'];}
 
 }  else{
  $face_id=$row['user_key'];
 $q= "SELECT * FROM project  where user_key like '$user_key'";
 $rq = $con->query($q);
 while($roww = mysqli_fetch_array($rq)){
$nameuer=$roww['name'];}
 
 }
    array_push($result,array(
        'id'=>$row['id'],
        'title'=>$row['title'],
        'desc'=>$row['descc'],
         'prep'=>$row['prep'],
          'total'=>$row['total'],
           'cook'=>$row['cook'],
           'calory'=>$row['calory'],
           'image'=>$imdata,
           'list'=>$row['list'],
            'photo'=>$row['image'],
           'rating'=>$rating,
           'username'=>$nameuer,
        
       
    ));
}

echo json_encode(array('result'=>$result));

mysqli_close($con);

?>