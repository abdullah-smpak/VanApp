<?php
require "conn.php";


if($conn)
{
$van_route = $_POST["van_route"];
$kid_nam = $_POST["kid_nam"];
$school_name = $_POST["school_name"];
$school_location = $_POST["school_location"];
$class = $_POST["class"];
$address = $_POST["address"];
$mother_nam = $_POST["mother_nam"];
//$mother_no = $_POST["mother_no"];
$mother_no = "+923432933953";
$father_nam = $_POST["father_nam"];
$father_no = $_POST["father_no"];
$pass = $_POST["pass"];
$pro_img = $_POST["pro_img"];
$van_no = $_POST["van_no"];
$token = $_POST["token"];
$p_lat = $_POST["p_lat"];
$p_lon = $_POST["p_lon"];

$mother_no=substr($mother_no,3);
echo $mother_no = "0".$mother_no;	

	for($i=0;$i<4;$i++)
	{
	$ran[$i] = rand(1, 1000000);
	
	}
	
	$upload_path1 = "parents/$ran[0].jpg";
	
	
$qwe2 = "select * from parent_reg where mother_no = '".$mother_no."'";
$check = mysqli_query($conn,$qwe2);
if(mysqli_num_rows($check) > 0)
	
{
	echo "Phone Number Already Exist";
}
else 
{


echo $qwe = "insert into parent_reg (van_route,kid_nam,school_name,school_location,class,address,mother_nam,mother_no,father_nam,father_no,pass,pro_img,van_no,token,lat,lon)
values ('$van_route','$kid_nam','$school_name','$school_location','$class','$address','$mother_nam','$mother_no','$father_nam','$father_no','$pass','$upload_path1','$van_no','$token','$p_lat','$p_lon')";

if(mysqli_query($conn,$qwe))
{
	file_put_contents($upload_path1,base64_decode($pro_img));
	echo "Record Inserted";
	
}

else
{
	echo "Error In Inserting Record";
}
}
}
?>