<?php
require "conn.php";


if($conn)
{
$mob_no = $_POST['mobno'];
$new_pass = $_POST['pwd'];



$sql = "UPDATE parent_reg SET pass='$new_pass' WHERE mother_no='$mob_no'";

if (mysqli_query($conn, $sql)) {
    echo "Password Changed Successfully";
} else {
    echo "Unknown Error";
}


}
?>