<?php


$user = "root";
$pass = "";
$host= "localhost";
$dbname="check";
$conn = mysqli_connect($host,$user,$pass,$dbname);

$result = mysqli_query($conn, "Select email,name,school from userdata");

while($row = mysqli_fetch_assoc($result))
{
	$temp[] = $row;
}

echo json_encode($temp);
mysqli_close($conn);
?>