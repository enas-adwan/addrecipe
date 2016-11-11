<?php

//if($_SERVER['REQUEST_METHOD']=='GET'){
require "conn.php";


$title  = $_POST['title'];
$calory=$_POST['calory'];
$total= $_POST['total'];

$list=$_POST['list'] ;
$qan=$_POST['qan'] ;
//$title  = '';
//$calory='';
//$total= '';

//$list='' ;
//$qan='more' ;
$list=trim($list, "]");
//$calory=trim($calory, '"');
//$calory=trim($calory, "'");
	$result1 = array();	
	$listarray = array();	
	$listarray = explode("-", $list);
$sql = "SELECT * FROM recipe ";
if($title!=''||$total!=''||$calory!=''||count($listarray)>2){
$sql .="WHERE ";
}


		if($title!=''){
		$sql .="title LIKE '%".$title."%'";}
		




	if($calory!=''){
		if($title!=''){
		$sql .="AND";}
		if($qan="less"){
		$sql .=" calory<".$calory."";
		}else if($qan="more"){
		$sql .=" calory>".$calory."";
		
		}else if($qan="equal"){
		$sql .=" calory=".$calory."";
		
		}
		
		
		
		
		
		}
		if($list!=''){
		if(count($listarray)>2){
			if($title!=''||$calory!=''){
			$sql .="AND"
		;}
		
		for($i=1;$i<count($listarray);$i++){
			if($i>1){
					$sql .=" AND";
			}
$lista=trim($listarray[$i]);
		$sql .=" list like '%".$lista."%'";
	
		
		}
		
		
		
		
		
		}
		
		
		
		
		
		
		
		
		
		
		
		
		}
			if($total!=''){
				if($title!=''||$calory!=''||count($listarray)>2){
		$sql .="AND";}
		$sql .=" total<'".$total."'";}
		





		$r = $conn->query($sql);
		
		while($res=mysqli_fetch_array($r)){
		$totalrating=$res['totalrating'];
$totalratingpeople=$res['totalratingpeople'];
if($totalratingpeople=='0' and $totalrating=='0'){
$rating='0';

}else{

$rating=$totalrating/$totalratingpeople;}
$im = file_get_contents('androidimages/'.$res['image']);
 $imdata = base64_encode($im);
		array_push($result1,array(
			"title"=>$res['title'],
			  "image"=>$imdata,
         "rating"=>$rating,
					"id"=>$res['id'],
			"calory"=>$res['calory'],
			"total"=>$res['total']
			)
		);
	}

			echo json_encode(array("result1"=>$result1));

		mysqli_close($conn);
		//echo $sql;

//}

?>
