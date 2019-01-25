<?php

$email = $_POST['email'];
$password = $_POST['password'];
$name = $_POST['name'];
$school = $_POST['school'];
$user = "root";
$pass = "";
$host= "localhost";
$dbname="check";

$con = mysqli_connect($host,$user,$pass,$dbname);
$sql="insert into userdata(email,password,name,school) values('".$email."','".$password."','".$name."','".$school."');";
if(mysqli_query($con,$sql)){
	echo "suss";  

}else{	
	echo json_encode("Failed") ;
}
?>