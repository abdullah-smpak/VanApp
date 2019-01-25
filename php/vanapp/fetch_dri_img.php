<?php
require "conn.php";

if($conn)
{
$dri_name = $_POST["d_names"];
$result = mysqli_query($conn, "Select dri_img from driver_reg where dri_nam = '".$dri_name."'");

while($row = mysqli_fetch_array($result))
{
	$temp = $row['dri_img'];
}

echo $temp;
mysqli_close($conn);
}
?>
