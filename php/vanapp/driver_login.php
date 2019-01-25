<?php
require "conn.php";


if($conn)
{
$mob_no = $_POST["mobno"];
$password = $_POST["pwd"];


 $qwe = "SELECT * from driver_reg where mob_no like '$mob_no' and password like '$password'";
$result = mysqli_query($conn , $qwe);
if(mysqli_num_rows($result) > 0 )
{
	echo "Login Successfully";
}

else
{
	echo "Invalid Login";
}

}
?>