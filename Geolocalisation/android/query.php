
<?php
			try
			{
				$bdd=new PDO('mysql:host=localhost;dbname=geolocalisation','root','');
			}
     			catch(Exception $e)
			{
				die("error:".$e->getMessagne());
			}

			$req=$bdd->query('SELECT id,nom,tel_1,region,pays,longitude,latitude,etoile,mail,site_web,fax,url FROM hotels');
   			while($ligne=$req->fetch()){
				$output[]=$ligne;
						
			}
                        
			$req->closeCursor();
                      print(json_encode(array("hotels"=>$output)));
			
		?>
     
