<?php
require "conn.php";


if($conn)
{
$ass_class = $_POST["ass_class"];
$ass_subject = $_POST["ass_subject"];
$ass_image = $_POST["ass_image"];
$ass_date = $_POST["ass_date"];
$teacher_name = $_POST["teacher_name"];
$filename = $_POST["filename"];
$marks = $_POST["marks"];



	
$upload_path1 = "Class_Assignment/$filename.jpg";

		
	

	$qwe = "insert into assignment (ass_class,ass_subject,ass_image,ass_date,teacher_name,marks)
	values ('$ass_class','$ass_subject','$upload_path1','$ass_date','$teacher_name','$marks')";

	if(mysqli_query($conn,$qwe))
	{
		file_put_contents($upload_path1,base64_decode($ass_image));
		echo "Class Work Uploaded";
		
	}

	else
	{
		echo "Error In Uploading Class Work";
	}
}
?>