

<?php
			try
			{
				$bdd=new PDO('mysql:host=localhost;dbname=test','root','');
			}
     			catch(Exception $e)
			{
				die("error:".$e->getMessagne());
			}

			$req=$bdd->query('SELECT * FROM pieceofshit');
   			while($ligne=$req->fetch()){
				$output[]=$ligne;
						
			}
                        
			$req->closeCursor();
                      print(json_encode(array("group"=>$output)));
			
		?>
  
