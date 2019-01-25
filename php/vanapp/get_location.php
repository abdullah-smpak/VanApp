<?php
require "conn.php";


if($conn)
{
$van_no = $_POST["van_no"];
$date = $_POST["date"];



$result = mysqli_query($conn, "SELECT * FROM tracking WHERE van_no = '$van_no' and date = '$date'");
while($row = mysqli_fetch_assoc($result))
{
	$temp[] = $row;
}

echo json_encode($temp);
}
?>