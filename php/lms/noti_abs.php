

<?php

require "conn.php";

//$class = $_POST['class'];
$class = "1";
//$date = $_POST['date'];
$date = "2019-01-10";


 $sql = "SELECT std1.name,std1.token FROM students std1 
WHERE std1.class_id='$class' AND std1.std_id NOT IN (SELECT std2.std_id FROM students std2 JOIN std_att ON std_att.std_id = std2.std_id 
WHERE std_att.status = 1 AND std_att.class = '$class' AND std_att.date = '$date')";
	
	
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
		'title'	=> 'School',
             	
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