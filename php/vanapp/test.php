<?php
require "conn.php";


if($conn)
{
$lat = $_POST["lat"];
$lon = $_POST["lon"];


$qwe = "insert into testdemo (lat,lon)
	values ('$lat','$lon')";

	if(mysqli_query($conn,$qwe))
	{
		
		echo "Record Inserted";
		
	}

	else
	{
		echo "Error In Inserting Record";
	}
}
?>