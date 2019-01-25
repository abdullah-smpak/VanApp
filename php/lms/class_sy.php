<?php
require "conn.php";


if($conn)
{
$sy_class = $_POST["sy_class"];
$sy_subject = $_POST["sy_subject"];
$sy_image = $_POST["sy_image"];
$sy_date = $_POST["sy_date"];
$teacher_name = $_POST["teacher_name"];
$filename = $_POST["filename"];



	
$upload_path1 = "Class_Syllabus/$filename.jpg";

		
	

	$qwe = "insert into syllabus (sy_class,sy_subject,sy_image,sy_date,teacher_name)
	values ('$sy_class','sy_subject','$upload_path1','$sy_date','$teacher_name')";

	if(mysqli_query($conn,$qwe))
	{
		file_put_contents($upload_path1,base64_decode($sy_image));
		echo "Syllabus Uploaded";
		
	}

	else
	{
		echo "Error In Uploading Syllabus ";
	}
}
?>