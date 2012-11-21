<?php
			
                    
                      try
			{
				$bdd=new PDO('mysql:host=localhost;dbname=geolocalisation','root','');
			}
     			catch(Exception $e)
			{
				die("error:".$e->getMessagne());
			}
			if(strcmp($_REQUEST['table'],'hotels')==0){
				$req=$bdd->query('SELECT id,nom,tel_1,region,pays,longitude,etoile,latitude,mail,site_web,fax,url FROM '.$_REQUEST['table'].' WHERE nom LIKE "%'.$_REQUEST['nom'].'%"');
                       }
			else{
				$req=$bdd->query('SELECT id,nom,tel_1,region,pays,longitude,latitude,mail,site_web,fax,url FROM '.$_REQUEST['table'].' WHERE nom LIKE "%'.$_REQUEST['nom'].'%"');
			}
   			while($ligne=$req->fetch()){
				$output[]=$ligne;
						
			}
                        
			$req->closeCursor();
                      print(json_encode(array("hotels"=>$output)));
			
		?>
     
