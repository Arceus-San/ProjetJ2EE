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
    <style>
        .navbar a:hover {
        background-color: #000;
        }
        .active {
         background-color: #4CAF50;
        }
    </style>
    <body>
        <Div style="background-color:green;padding:15px;text-align:center">
        <h1> BIENVENUE </h1>
        </Div>
        <div class="navbar" style="  width: 100%;background-color: #555;overflow: auto;" >
        <a class="active" href="#" style="float: left;padding: 12px;color: white;text-decoration: none;font-size: 17px;"><i class="fa fa-fw fa-home"></i> Home</a>
        </div>
        <form method='GET'>
            <fieldset>
                <legend> Identifiants : </legend>  
                <label for="login" ><b>Login :</b></label><br/>
                    <input name="login" id="login" size="10" maxlength="255" title="Votre Email"><br/>
                    <label for="passw"><b>Password :</b></label><br/>
        <input name="motDePasse" id="passwd" size="10" type="password" maxlength="255" title="Votre mot de passe"><br/>
        <br/>
        <input name="action" value="Connexion" type="SUBMIT">
            </fieldset>
        </form>
        <footer style="background-color:green;text-align:center;padding:10px;margin-top:7px">
            PROJET JAVA 
        </footer>
    </body>
</html>
