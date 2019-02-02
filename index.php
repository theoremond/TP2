<?php

if(!empty($_POST)) {
    if(isset($_POST['nom']) && $_POST['prenom']) {
        echo 'Bonjour, ' . $_POST['nom'] . ' ' . $_POST['prenom'] . ' !';
    } else if (isset($_POST['a']) && isset($_POST['b'])) {
        $r = intval($_POST['a']) + intval($_POST['b']);
        echo $r;
    }
} else if (!empty($_GET)) {
    if(isset($_GET['nom']) && isset($_GET['prenom'])) {
        echo 'Bonjour, ' . $_GET['nom'] . ' ' . $_GET['prenom'] . ' !';
    } else if(isset($_GET['a']) && isset($_GET['b'])) {
        $r = intval($_GET['a']) + intval($_GET['b']);
        echo $r;
    }
} else {
    echo 'Il a pas dit bonjour ! ';
}
?>