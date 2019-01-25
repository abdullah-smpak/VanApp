<?php
require "conn.php";


if($conn)
{
$user_name = $_POST["user_name"];
//$user_name = "abc";
$password = $_POST["password"];
//$password = "123";
$emp_id = 0;


$qwe = "SELECT * from users where username like '$user_name' and password like '$password'";
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