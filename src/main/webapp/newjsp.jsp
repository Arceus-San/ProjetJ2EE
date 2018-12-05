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
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
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
                                // Le code source du template est dans la page
                                var template = $('#codesTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('#codes').html(processedTemplate);
                            }
                });
            }

            // Ajouter un code
            function commander(code) {
                $.ajax({
                    url: "addCode",
                    // serialize() renvoie tous les paramètres saisis dans le formulaire
                    data: $("#codeForm").serialize(),
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
                    url: "deleteCode",
                    data: {"code": code},
                    dataType: "json",
                    success: 
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
                alert(JSON.parse(xhr.responseText).message);
            }

        </script>
        <!-- un CSS pour formatter la table -->
        <link rel="stylesheet" type="text/css" href="css/tableformat.css">
    </head>
    <body>
        <h1>
            Listes des Commandes :
        </h1>
         <!-- La zone où les résultats vont s'afficher -->
        <div id="codes"></div>
        <!-- Le template qui sert à formatter la liste des codes -->
        <script id="codesTemplate" type="text/template">
            <TABLE>
            <tr><th>Numero</th><th>Customer</th><th>Product_ID</th><th>Quantité</th><th>Shipping_Cost</th><th>Sales_date</th><th>Shipping_date</th><th>Freight_Company</th></tr>
            {{! Pour chaque enregistrement }}
            {{#records}}
                {{! Une ligne dans la table }}
                <TR><TD>{{orderNum}}</TD><TD>{{CustomerId}}</TD><TD>{{ProductId}}</TD><TD>{{Quantity}}</TD><TD>{{ShippingCost}}</TD><TD>{{SalesDate}}</TD><TD>{{ShippingDate}}</TD><TD>{{FreightCompany}}</TD><TD><button onclick="deleteCode('{{orderNum}}')">Commander</button></TD></TR>
            {{/records}}
            </TABLE>
        </script>
 
        <table border=2 >
                <tr> <th>Numero</th> <th>Customer</th> <th>Product_ID</th> <th>Quantité</th> <th>Shipping_Cost</th> <th>Sales_date</th> <th>Shipping_date</th> <th>Freight_Company</th></tr>
                <c:forEach var="customer" items="${customers}">
                    <tr> <td>${customer.order_num}</td> <td>${customer.customer_id}</td> <td>${customer.product_id}</td><td>${customer.quantity}</td><td>${customer.shipping_cost}</td><td>${customer.sales_date}</td><td>${customer.shipping_date}</td><td>${customer.freight_company}</td><TD><button onclick="commander('${customer.customerId}')">Commander</button></td></tr>
                </c:forEach>
        </table>

    </body>
</html>
