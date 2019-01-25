<?php
require "conn.php";

$std_id = $_POST["std_id"];
$class = $_POST["class"];

$date = $_POST["date"];
$att_status = $_POST["status"];





	$qwe = "insert into std_att(std_id,class,date,status)values ('$std_id','$class','$date','$att_status')";

	if(mysqli_query($conn,$qwe))
	{
		
		echo "Att Marked";
		
	}

	else
	{
		echo "Error In Inserting Record";
	}


mysqli_close($conn);

?>