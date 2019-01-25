<?php
require "conn.php";


if($conn)
{
$route_id = 0;
$driname ='';
$route = $_POST['route'];
//$route = "North Karachi";

$query1 = "SELECT `route_id` FROM `routes` WHERE `routes_name` = '".$route."'";
$sql1 = mysqli_query($conn, $query1);
while($row1 = mysqli_fetch_assoc($sql1))
{
	$route_id = $row1['route_id'];
}

$query2 = "SELECT dri_nam , van_no FROM `driver_reg` WHERE `dri_route` = '".$route_id."'";
$sql2  = mysqli_query($conn, $query2);
if(mysqli_num_rows($sql2) > 0)
{
	while($row2 = mysqli_fetch_assoc($sql2))
	{
		$temp[] = $row2;
	}
} else {
	$temp[] = array("dri_nam" => "No Values");
}
echo json_encode($temp);
mysqli_close($conn);


}
?>