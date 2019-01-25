<?php
require "conn.php";


$temp = 0;
$username = $_POST['username'];

$result = mysqli_query($conn, "Select emp_id from users where username='$username'");

while($row = mysqli_fetch_assoc($result))
{
	$temp = $row['emp_id'];
}


$query2 = "SELECT * FROM `employee` WHERE `emp_id` = '".$temp."'";
$sql2  = mysqli_query($conn, $query2);

	while($row2 = mysqli_fetch_assoc($sql2))
	{
		$temp2[] = $row2;
	}


echo json_encode($temp2);
mysqli_close($conn);

?>