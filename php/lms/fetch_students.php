<?php
require "conn.php";


$class_name = $_POST['class_name'];
//$class_name = "Class 1";

 $result1 = mysqli_query($conn, "Select class_id from class where class_name='$class_name'");
 while($row1 = mysqli_fetch_assoc($result1))
{
	$class_id = $row1['class_id'];
}


$result2 = mysqli_query($conn, "Select std_id, name from std_att where class_id='$class_id'");

while($row = mysqli_fetch_assoc($result2))
{
	$temp[] = $row;
}

echo json_encode($temp);
mysqli_close($conn);

?>