<?php
require "conn.php";


if($conn)
{
$ct_class = $_POST["ct_class"];
$ct_subject = $_POST["ct_subject"];
$ct_image = $_POST["ct_image"];
$ct_date = $_POST["ct_date"];
$teacher_name = $_POST["teacher_name"];
$filename = $_POST["filename"];



	
$upload_path1 = "Class_Test/$filename.jpg";

		
	

	$qwe = "insert into test (test_class,test_subject,test_image,test_date,teacher_name)
	values ('$ct_class','$ct_subject','$upload_path1','$ct_date','$teacher_name')";

	if(mysqli_query($conn,$qwe))
	{
		file_put_contents($upload_path1,base64_decode($ct_image));
		echo "Class Test Uploaded";
		
	}

	else
	{
		echo "Error In Uploading Class Test";
	}
}
?>