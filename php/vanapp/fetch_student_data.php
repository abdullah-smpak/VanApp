<?php
require "conn.php";


if($conn)
{
$route_id = 0;
$driname ='';
$mob_no = $_POST['mob_no'];
$query1 = "SELECT * FROM parent_reg WHERE `mother_no` = '".$mob_no."'";
$sql1 = mysqli_query($conn, $query1);

if(mysqli_num_rows($sql1) > 0)
{
	while($row2 = mysqli_fetch_assoc($sql1))
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