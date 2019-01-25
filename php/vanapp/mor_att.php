<?php
require "conn.php";


if($conn)
{
$std_id = $_POST["std_id"];
$van_no = $_POST["van_no"];

$date = $_POST["date"];
$time = $_POST["time"];
$status = $_POST["status"];

	
	

	$qwe = "insert into morning_att(std_id,van_no,date,time,status)	values ('$std_id','$van_no','$date','$time','$status')";

	if(mysqli_query($conn,$qwe))
	{
		
		echo "Att Marked";
		
	}

	else
	{
		echo "Error In Inserting Record";
	}
}
?>