<?php

if(!empty($_POST)) {
    if(isset($_POST['nom']) && $_POST['prenom']) {
    
    } else if (isset($_POST['a']) && isset($_POST['b'])) {

    }
} else if (!empty($_GET)) {
    if(isset($_GET['nom']) && isset($_GET['prenom'])) {

    } else if(isset($_GET['a']) && isset($_GET['b'])) {
    
    }
} else {
    echo 'Il a pas dit bonjour ! ';
}
?>