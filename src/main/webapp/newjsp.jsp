<%-- 
    Document   : newjsp
    Created on : 5 d�c. 2018, 13:57:18
    Author     : pedago
--%>

<!DOCTYPE html>

<html>
<head>
    <title>Edition des taux de remise (AJAX)</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- On charge jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
    <script>
    $(document).ready(// Ex�cut� � la fin du chargement de la page
            function () {
                // On montre la liste des codes
                showCodes();

            }
    );

    function showCodes() {
        // On fait un appel AJAX pour chercher les codes
        $.ajax({
            url: "allCodes",
            dataType: "json",
            error: showError,
            success: // La fonction qui traite les r�sultats
                    function (result) {
                        // Le code source du template est dans la page
                        var template = $('#codesTemplate').html();

                        var processedTemplate = Mustache.to_html(template, result);
                        // On combine le template avec le r�sultat de la requ�te
                        $('#codes').html(processedTemplate);


                    }
        });
    }

    function showCodes2() {
        // On fait un appel AJAX pour chercher les codes
        $.ajax({
            url: "allProduit",
            dataType: "json",
            error: showError,
            success: // La fonction qui traite les r�sultats
                    function (result) {
                        console.log(result);
                        var chartData = [];
                        var h = {};
                        // Le code source du template est dans la page
                        var template = $('#codesTemplate2').html();
                        for(var client in result.records) {
                            var res = result.records[client];
                            if(res.quantity > 0){
                                chartData.push(res);
                            }
                        }
                        h.records=chartData;

                        console.log(h);
                        var processedTemplate = Mustache.to_html(template, h);
                        // On combine le template avec le r�sultat de la requ�te
                        $('#Codes2').html(processedTemplate);


                    }
        });
    }
    function showCodes3() {
        $.ajax({
            url: "allCustomer",
            dataType: "json",
            error: showError,
            success: // La fonction qui traite les r�sultats
                    function (result) {
                        console.log(result);

                        // Le code source du template est dans la page
                        var template = $('#codesTemplate3').html();

                        var processedTemplate = Mustache.to_html(template, result);
                        // On combine le template avec le r�sultat de la requ�te
                        $('#Codes2').html(processedTemplate);


                    }
        });
    }



    // Ajouter un code
    function addCode(Code) {
        var quantite=  $('#Quantite-'+Code).val();
        var companie = $('#Companie-'+Code).val();
        $.ajax({
            url: "AddPurchaseOrder",
            // serialize() renvoie tous les param�tres saisis dans le formulaire
            data: {"code2": Code, "Quantite" : quantite , "Companie" : companie},
            dataType: "json",
            success: // La fonction qui traite les r�sultats
                    function (result) {
                        $('#message').html(result.message);
                        showCodes();
                        showCodes2();
                        console.log(result.message);
                    },
            error: showError
        });
        return false;
    }

    // Supprimer un code
    function deleteCode(code) {
        $.ajax({
            url: "deletePurchaseOrder",
            data: {"code": code},
            dataType: "json",
            success: 
                    function (result) {
                        $('#message').html(result.message);
                        showCodes();
                        showCodes2();
                    },
            error: showError
        });
        return false;
    }

    function Mofidcustomer(id) {
        var name = $('#Name').val();
        var adress1 = $('#adress1').val();
        var adress2 = $('#adress2').val();
        var city = $('#City').val();
        var state = $('#State').val();
        var phone = $('#Phone').val();
        var fax = $('#Fax').val();
        var email = $('#Email').val();
        var credit = $('#Credit').val();
        console.log(id, name,adress1, adress2, city, state, phone, fax, email, credit);
        $.ajax({
            url: "modifCustomers",
            dataType: "json",
            data: {"ID": id,
                "Name" : name,
                "Adress1" : adress1 ,
                "Adress2" : adress2,
                "City" : city ,
                "State" : state,
                "Phone" : phone ,
                "Fax" : fax ,
                "Email" : email ,
                "Credit" : credit},
            success: // La fonction qui traite les r�sultats
                    function (result) {
                        $('#message').html(result.message);
                        showCodes3();
                    },
            error: showError
        });
        return false;
    }


    // Fonction qui traite les erreurs de la requ�te
    function showError(xhr, status, message) {
        alert("Erreur: " + xhr.status + " : " + message);
    }
    
        function afficher2()
        {
        var h;
	document.getElementById("passer_commande").style.visibility = "";
        document.getElementById("donnes").style.visibility = "hidden";
        var template = $('#vide').html();
        var processedTemplate = Mustache.to_html(template, h);
        $('#codes2').html(processedTemplate);                   
        }
        function afficher3()
        {
        var h;
	document.getElementById("donnes").style.visibility = "";
        document.getElementById("passer_commande").style.visibility = "hidden";
        var template = $('#vide').html();
        var processedTemplate = Mustache.to_html(template, h);
        $('#codes2').html(processedTemplate);
        $('#codes').html(processedTemplate);
        }
        function disconnect(){
            $.ajax({
                data: {"action": "deconnexion"},
                success: function(){
                            window.location.href = "LoginController";
                            console.log("D�connexion...");
                        }
            });
            
            return false;
        }
    </script>
    <style>
        .bas{
            position:fixed;
            bottom:0px;
            background-color:green;
            text-align:center;
            padding:10px;
            width: 100%;
            height: 30px; 
            margin-right:10px;
            overflow:scroll;
        }
.navbar {
  overflow: hidden;
  background-color: #333;
  font-family: Arial, Helvetica, sans-serif;
  margin-bottom: 40px;
}

.navbar a {
  float: left;
  font-size: 16px;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

.dropdown {
  float: left;
  overflow: hidden;
  background-color: green;
}

.dropdown .dropbtn {
  font-size: 16px;  
  border: none;
  outline: none;
  color: white;
  padding: 14px 16px;
  background-color: inherit;
  font-family: inherit;
  margin: 0;
}

.navbar a:hover, .dropdown:hover .dropbtn {
  background-color: green;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content a {
  float: none;
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  text-align: left;
}

.dropdown-content a:hover {
  background-color: #00cc00;
}

.dropdown:hover .dropdown-content {
  display: block;
}

    </style>
</head>
<body>
<h1>Edition des taux de remise (AJAX)</h1>
                <Div style="background-color:green;padding:15px;text-align:center">
        <h1> CLIENT </h1>
        </Div>
        <div class="navbar" style="width: 100%;background-color: #555;overflow:auto;" >
            <a  onclick="disconnect()" href="#" name="home"> Home</a>
            <div class="dropdown" >
                    <button class="dropbtn">Client 
                        <i class="fa fa-caret-down"></i>
                    </button>
                <div class="dropdown-content">
                  <a href="#" onclick="afficher2()">Passer une commande</a>
                  <a href="#" onclick="afficher3()">Modifier ses donn�es</a>
                </div>
              </div>
            <a name="admin"> Admin</a> 
            
        </div>

    <h4 id="message"></h4>
        �<!-- La zone o� les r�sultats vont s'afficher -->
        <div id="passer_commande" style="visibility:hidden" >
        <div id="codes"></div>
        
        
        <input name="g" type="radio" onclick="showCodes2()" value="Passer une nouvelle commande">
        <label for="Passer une nouvelle commande">Passer une nouvelle commande</label>
        </div>
        <div id="donnes" style="visibility:">
        <input name="g" type="radio" onclick="showCodes3()" value="Changer ses Donn�es">
        <label for="Changer ses Donn�es">Changer ses Donn�es</label>
        </div>
        
        <div id="Codes2"></div>
        <script id="vide" type="text/template">
        </script>


        <!-- Le template qui sert � formatter la liste des codes -->
        <script id="codesTemplate" type="text/template">
            {{^records}}
            Vous n'avez aucune commande
            {{/records}}
            <TABLE border=2>
            <tr><th>Numero</th><th>Customer_id</th><th>Product_id</th><th>Quantity</th><th>Shipping_cost</th><th>Sales_date</th><th>Shipping_date</th><th>freight_company</th></tr>
            {{! Pour chaque enregistrement }}
            {{#records}}
                {{! Une ligne dans la table }}
                <TR><TD>{{order_num}}</TD><TD>{{customer_id}}</TD><TD>{{product_id}}</TD><TD>{{quantity}}</TD><TD>{{shipping_cost}}</TD><TD>{{sales_date}}</TD><TD>{{shipping_date}}</TD><TD>{{freight_company}}</TD><TD><button onclick="deleteCode('{{order_num}}')">Supprimer</button></TD></TR>
            {{/records}}
           
            </TABLE>
            
        </script>
    <script id="codesTemplate2" type="text/template">
            <TABLE border=2 style="margin-bottom:20px;">

                <tr>
                    <th>Numero du produit</th><th>Numero du fournisseur</th><th>Code du produit</th><th>Prix</th><th>Quantit� disponible</th>
                    <th>Balisage</th><th>Disponible</th><th>Description</th><th>Quantit�</th><th>Companie</th>
                </tr>
                
                {{#records}}
                    <tr>
                        <td>{{id}}</td><td>{{manuf_id}}</td><td>{{prod_code}}</td><td>{{cost}}</td><td>{{quantity}}</td>
                        <td>{{markup}}</td><td>{{available}}</td><td>{{description}}</td>
                        <td><input id="Quantite-{{id}}" type="number" min="1" max="{{quantity}}"></td>
                        <td><input id="Companie-{{id}}" type="text"></td>
                        <td><button onclick="addCode('{{id}}')">Commander</button></td>
                    </tr>
                {{/records}}
            
            </TABLE>
            
        </script>
        
 <script id="codesTemplate3" type="text/template">
            
            <TABLE border=2 style="margin-bottom:20px;">
            
                <tr>
                    <th>Son ID</th><th>Discount_Code</th><th>Zip</th><th>Name</th><th>Adress1</th>
                    <th>Adress2</th><th>City</th><th>State</th><th>Phone</th><th>Fax</th><th>Email</th><th>Credit_Limit</th><th></th>
                </tr>
                
                    <tr>
                        <td>{{id}}</td><td>{{discount_code}}</td><td>{{zip}}</td><td><input id="Name" type="text" value="{{name}}" /></td><td><input id="adress1" type="text" value="{{adress1}}" /></td>
                        <td><input id="adress2" type="text" value="{{adress2}}" /></td><td><input id="City" type="text" value="{{city}}" /></td><td><input id="State" type="text" value="{{state}}" /></td><th><input id="Phone" type="text" value="{{phone}}" /></th><th><input id="Fax" type="text" value="{{fax}}" /></th><th><input id="Email" type="text" value="{{email}}" /></th><th><input id="Credit" type="number" value="{{credit_limit}}" /></th>
                        <th><button onclick="Mofidcustomer({{id}})">Modifier</button></th>
                    </tr>
            
            </TABLE>
            
        </script>
        <footer class="bas">
            PROJET JAVA 
        </footer> 

</body>
</html>
