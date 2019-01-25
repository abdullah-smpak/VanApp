<?php
require "conn.php";


if($conn)
{
$van_no = $_POST["van_no"];
$make = $_POST["make"];
$dri_nam = $_POST["dri_nam"];
$model = $_POST["model"];
$mob_no = $_POST["mob_no"];

$dri_address = $_POST["dri_address"];
$school_nam = $_POST["school_nam"];
$school_loc = $_POST["school_loc"];
$password = $_POST["password"];
$dri_img = $_POST["dri_img"];
$dri_lat = $_POST["dri_lat"];
$dri_lon = $_POST["dri_lon"];

$mob_no=substr($mob_no,0);
	$mob_no = "0".$mob_no;	
	
	for($i=0;$i<4;$i++)
	{
	$ran[$i] = rand(1, 1000000);
	
	}
	
	$upload_path1 = "drivers/$ran[0].jpg";
$qwe2 = "select * from driver_reg where mob_no = '".$mob_no."'";
$check = mysqli_query($conn,$qwe2);
if(mysqli_num_rows($check) > 0)
	
{
	echo "Phone Number Already Exist";
}
else 
{
		
	

	$qwe = "insert into driver_reg (van_no,make,dri_nam,model,mob_no,dri_address,school_nam,school_loc,password,dri_img,driver_lat,driver_lon)
	values ('$van_no','$make','$dri_nam','$model','$mob_no','$dri_address','$school_nam','$school_loc','$password','$upload_path1','$dri_lat','$dri_lon')";

	if(mysqli_query($conn,$qwe))
	{
		file_put_contents($upload_path1,base64_decode($dri_img));
		echo "Record Inserted";
		
	}

	else
	{
		echo "Error In Inserting Record";
	}
}}
?>