<?php

// Inclui o arquivo class.phpmailer.php localizado na pasta class
require_once("class/class.phpmailer.php");




$mail = new PHPMailer;

//Enable SMTP debugging.
$mail->SMTPDebug = 3;
//Set PHPMailer to use SMTP.
$mail->IsSMTP();

$mail->CharSet = 'UTF-8';
//Set SMTP host name
$mail->Host = "mx1.hostinger.com.br";
//Set this to true if SMTP host requires authentication to send email
$mail->SMTPAuth = true;
$mail->isSMTP();
//Provide username and password
$mail->Username = "helper@cdbola.com.br";
$mail->Password = "fethygoju1";
//If SMTP requires TLS encryption then set it
//Set TCP port to connect to
$mail->Port = 587;

$mail->From = "helper@cdbola.com.br";
$mail->FromName = $_POST["name"];

$mail->addAddress("fila@cdbola.com.br", "Fila");

$mail->isHTML(false);

$mail->Subject = "Mais uma pessoa aguardando";
$mail->Body = "Este email quer saber das novidades  " . $_POST["email"] . " - " . $_POST["name"] ;

$mail->send();



header("Location: http://cdbola.com.br");

die();





?>
