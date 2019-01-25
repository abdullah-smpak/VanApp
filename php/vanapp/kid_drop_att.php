<?php
require "conn.php";


if($conn)
{
$date = $_POST["date"];
$kid = $_POST["kid"];
$time = $_POST["time"];
$status = $_POST["status"];
$van_no = $_POST["van_no"];



$qwe = "insert into drop_attandance (date,kid,time,status,van_no)
values ('$date','$kid','$time','$status','$van_no')";

if(mysqli_query($conn,$qwe))
{
	
	echo "Attendance Marked Successfully..";
	
}

else
{
	echo "Error In Marking Attendance...";
}
}
?>