<?php

require "conn.php";

//$cls = "Class 2";
$cls = $_POST['cls'];
//$date = "2019-01-10";
$date = $_POST['date'];

$result1 = mysqli_query($conn, "Select class_id from class where class_name='$cls'");

 while($row1 = mysqli_fetch_assoc($result1))
{
	$class_id = $row1['class_id'];
}



$result = mysqli_query($conn, "Select * from std_att where class='$class_id' AND date='$date'");

if(mysqli_num_rows($result) > 0 )
{
	echo "Attendance Marked Already";
}

else
{
	echo "Not Marked";
}
?>