<?php
  try{
     $bdd=new PDO('mysql:host=localhost;dbname=geolocalisation','root','');
  }catch(Exception $e){
  }
  $req=$bdd->query('SELECT url,url2,url3,url4,url5,url6,url7,url8,url9,url10 FROM hotels WHERE id='.$_REQUEST['id'].'');

  while($ligne=$req->fetch()){
				$output[]=$ligne;
						
			}
                        
			$req->closeCursor();
                      print(json_encode(array("hotels"=>$output)));
 
?>
