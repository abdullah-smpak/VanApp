<?php
require "conn.php";

$van_no = "2192";
//$van_no = $_POST['van_no'];
 $sql = "Select id,kid_nam,pro_img,lat,lon from parent_reg where van_no = ".$van_no;
$result = mysqli_query($conn, $sql);

while($row = mysqli_fetch_assoc($result))
{
	$temp[] = $row;
}

echo json_encode($temp);
mysqli_close($conn);

?>