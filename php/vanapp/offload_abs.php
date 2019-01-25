

<?php

require "conn.php";

$van_no = $_POST['van_no'];
//$van_no = "2192";
$date = $_POST['date'];
//$date = "2019-01-17";



$sql = "SELECT pr.kid_nam,pr.token FROM parent_reg pr 
 WHERE pr.van_no = 2192 AND id NOT IN (SELECT std_id FROM morning_att ma JOIN parent_reg pr2 ON ma.std_id = pr2.id
    WHERE pr2.van_no = '$van_no' AND DATE = '$date')";
 $result2  = mysqli_query($conn, $sql);

		define( 'API_ACCESS_KEY', 'AAAAGvnWGRE:APA91bGQ2KYKElDt6_iUz1jQEDEVabGFn2UE02AEgu54_uxsIwKjiuXCOM0uULxqSzHsYJL6R2vO8YQmcBbTL3mkMEsDKtUyRDXrmrOwhP8-UXtmx-VPqYplQ6VhFUAx9coAY5rOVj85U7frFBAZH_wWHYJcrLqTbw');
 //   $registrationIds = ;
#prep the bundle


while($row = mysqli_fetch_array($result2))
{
	$token = $row['token'];
	
	$msg = array
          (
		'body' 	=> 'Your Kid Is Absent Today',
		'title'	=> 'Van App',
             	
          );
	$fields = array
			(
				'to'		=> $row['token'] ,
				'notification'	=> $msg
			);
	
	
	$headers = array
			(
				'Authorization: key=' . API_ACCESS_KEY,
				'Content-Type: application/json'
			);

			
			#Send Reponse To FireBase Server	
		$ch = curl_init();
		curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
		curl_setopt( $ch,CURLOPT_POST, true );
		curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
		curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
		$result = curl_exec($ch);
		print_r($result);
		curl_close( $ch );
}
	

	
	
	
	
	
/*
    $msg = array
          (
		'body' 	=> 'Firebase Push Notification',
		'title'	=> 'Vishal Thakkar',
             	
          );
	$fields = array
			(
				'to'		=> $key1 ,
				'notification'	=> $msg
			);
	
	
	$headers = array
			(
				'Authorization: key=' . API_ACCESS_KEY,
				'Content-Type: application/json'
			);

			
			#Send Reponse To FireBase Server	
		$ch = curl_init();
		curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
		curl_setopt( $ch,CURLOPT_POST, true );
		curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
		curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
		$result = curl_exec($ch );
		echo $result;
		//curl_close( $ch );
	

	

*/



	

	
?>