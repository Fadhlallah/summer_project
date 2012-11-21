<?php

echo $obj;
try{
	$bdd=new PDO('mysql:host=localhost;dbname=test','root','');
}
catch(Exception $e){
	die("erreur:".$e->getMessage());
}
$req=$bdd->prepare('INSERT INTO pieceofshit(id,nom,age) VALUES(?,?,?)');
$req->execute(array($_REQUEST['id'],$_REQUEST['nom'],$_REQUEST['age']));
//json_encode(array("result"=>"validé"));
$req->closeCursor();
?>
