
<?php
			try
			{
				$bdd=new PDO('mysql:host=localhost;dbname=geolocalistaion','root','');
			}
     			catch(Exception $e)
			{
				die("error:".$e->getMessagne());
			}

			$req=$bdd->query('SELECT nom,lattitude,longitude FROM hotels WHERE nom=\''. $_REQUEST['hotel'].'\'');
   			while($ligne=$req->fetch()){
				$output[]=$ligne;
						
			}
                        
			$req->closeCursor();
                      print(json_encode($output));
			
		?>
        
