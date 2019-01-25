<?php
require "conn.php";


if($conn)
{
$hw_class = $_POST["hw_class"];
$hw_subject = $_POST["hw_subject"];
$hw_image = $_POST["hw_image"];
$hw_date = $_POST["hw_date"];
$teacher_name = $_POST["teacher_name"];
$filename = $_POST["filename"];



	
$upload_path1 = "Home_Work/$filename.jpg";

		
	

	$qwe = "insert into homework (hw_class,hw_subject,hw_image,hw_date,teacher_name)
	values ('$hw_class','$hw_subject','$upload_path1','$hw_date','$teacher_name')";

	if(mysqli_query($conn,$qwe))
	{
		file_put_contents($upload_path1,base64_decode($hw_image));
		echo "Home Work Uploaded";
		
	}

	else
	{
		echo "Error In Uploading Home Work";
	}
}
?>