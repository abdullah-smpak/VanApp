<?php
require "conn.php";


if($conn)
{
$cw_class = $_POST["cw_class"];
$cw_subject = $_POST["cw_subject"];
$cw_image = $_POST["cw_image"];
$cw_date = $_POST["cw_date"];
$teacher_name = $_POST["teacher_name"];
$filename = $_POST["filename"];



	
$upload_path1 = "Class_Work/$filename.jpg";

		
	

	$qwe = "insert into classwork (cw_class,cw_subject,cw_image,cw_date,teacher_name)
	values ('$cw_class','$cw_subject','$upload_path1','$cw_date','$teacher_name')";

	if(mysqli_query($conn,$qwe))
	{
		file_put_contents($upload_path1,base64_decode($cw_image));
		echo "Class Work Uploaded";
		
	}

	else
	{
		echo "Error In Uploading Class Work";
	}
}
?>