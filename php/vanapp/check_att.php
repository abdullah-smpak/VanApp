<?php
require "conn.php";


$result = mysqli_query($conn, "Select status from morning_att");

while($row = mysqli_fetch_assoc($result))
{
	$temp[] = $row;
}

echo ($temp);
mysqli_close($conn);

?>