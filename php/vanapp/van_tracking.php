<?php
require "conn.php";


if($conn)
{
$van_no = $_POST["van_no"];
$latitude = $_POST["latitude"];
$longitude = $_POST["longitude"];
$time = $_POST["time"];
$date = $_POST["date"];


$qwe1 = "SELECT * FROM tracking WHERE van_no = '2192' and date = '$date'";
$check = mysqli_query($conn,$qwe1);

if(mysqli_num_rows($check) > 0)
	
{

$qwe2 = "UPDATE tracking SET latitude='$latitude', longitude='$longitude', time='$time' where van_no='$van_no'";
if(mysqli_query($conn,$qwe2))
	{
		
		echo "Record Updated";
		
	}

	else
	{
		echo "Error In Updating Record";
	}
	
}
else 
{
	$qwe = "insert into tracking (van_no,latitude,longitude,time,date)
	values ('$van_no ','$latitude','$longitude','$time','$date')";

	if(mysqli_query($conn,$qwe))
	{
		
		echo "Record Inserted";
		
	}

	else
	{
		echo "Error In Inserting Record";
	}
}




}
?>