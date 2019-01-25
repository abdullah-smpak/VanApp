<?php
require "conn.php";


if($conn)
{
$school_id = 0;
$driname ='';
$schools = $_POST['schools'];
//$schools = "Happy Palace School";


$query1 = "SELECT `school_id` FROM `schools` WHERE `school_name` = '".$schools."'";
$sql1 = mysqli_query($conn, $query1);
while($row1 = mysqli_fetch_assoc($sql1))
{
	$school_id = $row1['school_id'];
}

$query2 = "SELECT branch FROM school_branches WHERE school_id = '".$school_id."'";
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

