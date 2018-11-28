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
        .bas{
            position:absolute;
            bottom:0px;
            background-color:green;
            text-align:center;
            padding:10px;
            width: 100%;
            height: 30px; 
            margin-right:10px;
        }
        .b{
            list-style-type: square;
            position:absolute;
        }
        .expli{
            margin-top: 20px;
            padding:10px;
        }
        fieldset{
            margin-right:325px;
            padding-left: 20px;
            padding-top: 10px;
        }
        .createur{
            position:absolute;
            right:0px;
            background-color:#4CAF50;
            overflow:auto;
            width:300px;
            text-align:center;
            height:60%;
            padding:10px;
            margin-right:10px;
            margin-top: 9px;
        }
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
        <div class="navbar" style="width: 100%;background-color: #555;overflow:auto;" >
            <a class="active" name="home" type="SUBMIT" style="float: left;padding: 12px;color: white;text-decoration: none;font-size: 17px;"> Home</a>
            <a name="client" type="SUBMIT" style="float: left;padding: 12px;color: white;text-decoration: none;font-size: 17px;"> Client</a>
            <a name="admin" type="SUBMIT" style="float: left;padding: 12px;color: white;text-decoration: none;font-size: 17px;"> Admin</a> 
        </div>
        <br/>
        <div class="createur">
            <h1>
                <b>GROUPE JAVA</b> :
            </h1>
                <ul class="b">
                  <li>Dax Guillaume</li>
                  <br/>
                  <li>Gresse Maxime</li>
                  <br/>
                  <li>Bardy Benjamin</li>
                  <br/>
                </ul>
        </div>
        <form method='POST'>
            <fieldset >
                <legend> Identifiants : </legend>  
                <label for="login" ><b>Login :</b></label><br/>
                    <input name="login" id="login" size="10" maxlength="255" title="Votre Email"><br/>
                    <br/>
                    <label for="passw"><b>Password :</b></label><br/>
        <input name="motDePasse" id="passwd" size="10" type="password" maxlength="255" title="Votre mot de passe"><br/>
        <br/>
        <input name="action" value="Connexion" type="SUBMIT">
            </fieldset>
        </form>
        <div class="expli">
            <h1 style="font-size:25px">
                <u><b>LES CLIENTS :</b></u>
            </h1>
            <p>login : EMAIL<br/>
               password : CUSTOMER_ID
            </p>
            <h2 style="font-size:25px">
                <u><b>LES ADMINISTRATEURS :</b></u>
            </h2>
            <p>
                login : XXXXX<br/>
                password : XXXXX
            </p>
        </div>
        <footer class="bas">
            PROJET JAVA 
        </footer>
    </body>
</html>
