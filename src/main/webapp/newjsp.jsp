<%-- 
    Document   : newjsp
    Created on : 5 déc. 2018, 13:57:18
    Author     : pedago
--%>

<!DOCTYPE html>

<html>
 <title>Edition des taux de remise (AJAX)</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- On charge jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <script>
            $(document).ready(// Exécuté à la fin du chargement de la page
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
                    success: // La fonction qui traite les résultats
                            function (result) {
                                console.log(result);
                                var chartData = [];
                                var h = {};
                                // Le code source du template est dans la page
                                var template = $('#codesTemplate').html();
                                for(var client in result.records) {
                                chartData.push(result.records[client]);
                            }
                                h.records=chartData;
         
                                console.log(h);
                                var processedTemplate = Mustache.to_html(template, h);
                                // On combine le template avec le résultat de la requête
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
                    success: // La fonction qui traite les résultats
                            function (result) {
                                console.log(result);
                                var chartData = [];
                                var h = {};
                                // Le code source du template est dans la page
                                var template = $('#codesTemplate2').html();
                                for(var client in result.records) {
                                chartData.push(result.records[client]);
                            }
                                h.records=chartData;
         
                                console.log(h);
                                var processedTemplate = Mustache.to_html(template, h);
                                // On combine le template avec le résultat de la requête
                                $('#Codes2').html(processedTemplate);
                               
                                
                            }
                });
            }
            function showCodes3() {
                $.ajax({
                    url: "allCustomer",
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                console.log(result);
                                var chartData = [];
                                var h = {};
                                // Le code source du template est dans la page
                                var template = $('#codesTemplate3').html();
                                for(var client in result.records) {
                                chartData.push(result.records[client]);
                            }
                                h.records=chartData;
         
                                console.log(h);
                                var processedTemplate = Mustache.to_html(template, h);
                                // On combine le template avec le résultat de la requête
                                $('#Codes3').html(processedTemplate);
                               
                                
                            }
                });
            }



            // Ajouter un code
            function addCode(Code) {
                var quantite=  $('#Quantite').val();
                var companie = $('#Companie').val();
                $.ajax({
                    url: "AddPurchaseOrder",
                    // serialize() renvoie tous les paramètres saisis dans le formulaire
                    data: {"code2": Code, "Quantite" : quantite , "Companie" : companie},
                    dataType: "json",
                    success: // La fonction qui traite les résultats
                            function (result) {
                                showCodes();
                                console.log(result);
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
                                showCodes();
                            },
                    error: showError
                });
                return false;
            }
            
        function Mofidcustomer() {
                var id = $('#ID').val();
                var discount = $('#discount').val();
                var zip = $('#Zip').val();
                var name = $('#Name').val();
                var adress1 = $('#adress1').val();
                var adress2 = $('#adress2').val();
                var city = $('#City').val();
                var state = $('#State').val();
                var phone = $('#Phone').val();
                var fax = $('#Fax').val();
                var email = $('#Email').val();
                var credit = $('#Credit').val();
                $.ajax({
                    url: "ModifCustomer",
                    // serialize() renvoie tous les paramètres saisis dans le formulaire
                    data: {"code2": Code, "Quantite" : quantite , "Companie" : companie},
                    dataType: "json",
                    success: // La fonction qui traite les résultats
                            function (result) {
                                showCodes();
                                console.log(result);
                            },
                    error: showError
                });
                return false;
            }

            // Fonction qui traite les erreurs de la requête
            function showError(xhr, status, message) {
                alert("Erreur: " + status + " : " + message);
            }

        </script>
<body>
<h1>Edition des taux de remise (AJAX)</h1>
         <!-- La zone où les résultats vont s'afficher -->
        <div id="codes"></div>
        <a href='#' onclick="showCodes2()">Passer une nouvelle commande</a>
        <a href='#' onclick="showCodes3()">Changer ses Données</a>
        <div id="Codes2"></div>
        <div id="Codes3"></div>
        <!-- Le template qui sert à formatter la liste des codes -->
        <script id="codesTemplate" type="text/template">
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
                <label>Quantite<input id="Quantite" type="number" /></label>
                <label>Companie<input id="Companie" type="text"/></label>
            <TABLE border=2>

                <tr>
                    <th>Numero du produit</th><th>Numero du fournisseur</th><th>Code du produit</th><th>Prix</th><th>Quantité disponible</th>
                    <th>Balisage</th><th>Disponible</th><th>Description</th><th>Action</th>
                </tr>
                
                {{#records}}
                    <tr>
                        <td>{{id}}</td><td>{{manuf_id}}</td><td>{{prod_code}}</td><td>{{cost}}</td><td>{{quantity}}</td>
                        <td>{{markup}}</td><td>{{available}}</td><td>{{description}}</td><th>
                            <button onclick="addCode('{{id}}')">Commander</button>
                        </th>
                    </tr>
                {{/records}}
            
            </TABLE>
            
        </script>
        
 <script id="codesTemplate3" type="text/template">
            
            <TABLE border=2>
            
                <tr>
                    <th>Son ID</th><th>Discoun_Code</th><th>Zip</th><th>Name</th><th>Adress1</th>
                    <th>Adress2</th><th>City</th><th>State</th><th>Phone</th><th>Fax</th><th>Email</th><th>Credit_Limit</th>
                </tr>
                
                {{#records}}
                    <tr>
                        <td><input id="ID" type="text" value={{id}}></td><td><input id="discount" type="text" value={{discount_code}}></td><td><input id="Zip" type="text" value={{zip}}></td><td><input id="Name" type="text" value={{name}}></td><td><input id="adress1" type="text" value={{adress1}}></td>
                        <td><input id="adress2" type="text" value={{adress2}}></td><td><input id="City" type="text" value={{city}}></td><td><input id="State" type="text" value={{state}}></td><th><input id="Phone" type="text" value={{phone}}></th><th><input id="Fax" type="text" value={{fax}}></th><th><input id="Email" type="text" value={{email}}></th><th><input id="Credit" type="text" value={{credit_limit}}></th><th>
                            <button onclick="Mofidcustomer()">Commander</button>
                        </th>
                    </tr>
                {{/records}}
            
            </TABLE>
            
        </script>
</body>
</html>
