

<?php

require "conn.php";

$van_no = $_POST['van_no'];
//$van_no = "2192";
$date = $_POST['date'];
//$date = "2019-01-08";


//$van_no = "2192";

 $sql = "SELECT pr2.token,pr2.kid_nam FROM morning_att ma JOIN parent_reg pr2 ON ma.std_id = pr2.id WHERE pr2.van_no = '$van_no' AND DATE = '$date'";
$result2  = mysqli_query($conn, $sql);

		define( 'API_ACCESS_KEY', 'AAAAmYW1iXc:APA91bHcWFTL77Gc-2Vw-FL1mioxyStpJsKL22toJj2WN8MbpAVhEKeR_7AMSpSPiO1GrQSNFprUt1x1HZLkPxe9JPwXbx3AQZWH2DIgdJTbTfh0t6G_y7KeGMl9Kt7pUTziNk5rey9K');
 //   $registrationIds = ;
#prep the bundle


while($row = mysqli_fetch_array($result2))
{
	$token = $row['token'];
	
	$msg = array
          (
		'body' 	=> 'Your Kid Is Now In Van Safely',
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