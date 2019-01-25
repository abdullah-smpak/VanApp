<?php
require "conn.php";


if($conn)
{
$std_id = $_POST["std_id"];
$date = $_POST["date"];


	
	

	$qwe = "DELETE from evening_att where std_id='$std_id' AND date='$date'";

	if(mysqli_query($conn,$qwe))
	{
		
		echo "Un Marked";
		
	}

	else
	{
		echo "Error In Inserting Record";
	}
}
?>