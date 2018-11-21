<%-- 
    Document   : Accueil
    Created on : 21 nov. 2018, 14:08:08
    Author     : pedago
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mini Projet</title>
    </head>
    <body>
        <h1> BIENVENUE </h1>
        <form method='GET'>
            <fieldset>
                <legend> Identifiants : </legend>  
                <label for="login" ><b>Login :</b></label><br/>
                    <input name="login" id="login" size="10" maxlength="255" title="Votre Email"><br/>
                    <label for="passw"><b>Password</b></label><br/>
        <input name="motDePasse" id="passwd" size="10" type="password" maxlength="255" title="Votre mot de passe"><br/>
        <br/>
        <input name="action" value="Connexion" type="SUBMIT">
            </fieldset>
        </form>
    </body>
</html>
