<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.List, tum.in.dbpra.bean.ProductBean" %>

<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="UTF-8" />
        <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
        <title>Database Final Project</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <link rel="shortcut icon" href="../favicon.ico"> 
        <link rel="stylesheet" type="text/css" href="css/demo.css" />
        <link rel="stylesheet" type="text/css" href="css/style.css" />
		<link rel="stylesheet" type="text/css" href="css/angtimate-custom.css" />
    <style>
    
    input[type=text], select {
	    width: 50%;
	    padding: 12px 20px;
	    margin: 8px 0;
	    display: inline-block;
	    border: 1px solid #ccc;
	    border-radius: 4px;
	    box-sizing: border-box;
	}
	
	input[type=submit] {
	    width: 50%;
	    background-color: #404040;
	    color: white;
	    padding: 14px 20px;
	    margin: 8px 0;
	    border: none;
	    border-radius: 4px;
	    cursor: pointer;
	}

	input[type=submit]:hover {
    	background-color: #5F9EA0;
	}
	
	.productclass {
	    border-radius: 5px;
	    background-color: #f2f2f2;
	    padding: 20px;
	}
	table {
	    border-collapse: collapse;
	    width: 100%;
	}
	th, td {
	    padding: 8px;
	    text-align: center;
	    border-bottom: 1px solid #ddd;
	}
	tr{background-color:white}
	tr:hover{background-color:#f5f5f5}
    
    </style>
    </head>
    <body>
    <%
    //allow access only if session exists
    String user = null;
    if(session.getAttribute("email") == null){
    	response.sendRedirect("login-provider.jsp");
    }else{
    	user = (String) session.getAttribute("email");
    }
    String userName = null;
    String sessionID = null;
    Cookie[] cookies = request.getCookies();
    if(cookies != null){
    	for(Cookie cookie : cookies){
    		if(cookie.getName().equals("email")){
    			userName = cookie.getValue();
    		}
    		if(cookie.getName().equals("JSESSIONID")){
    			sessionID = cookie.getValue();
    		}
    	}
    }
    %>

        
        <div class="container">
            <!-- Codrops top bar -->
            <div class="codrops-top">
            <header>
              <h1><strong>Database Practical Final Project</strong></h1>
            </header>			
                  <span class="right">
                    <a href=" ">
                        <strong>Back to the Main Page</strong>
                    </a>
                </span>
                <div class="clr"></div>
            </div><!--/ Codrops top bar -->
            <header>
                <h1 style="font-size: 300%;">Product Management</h1>
			</header>
             			<div class="productclass">
                            <% List<ProductBean> products = (List<ProductBean>) request.getAttribute("products");
                                int pid = (Integer) request.getAttribute("pid");
                                if (products.size() == 0) {                            
                            %>
                                <strong>There aren't any product registered </strong>
                            <% } else { %>
                            	<strong>This is the list of your products: </strong>
                                <table border="2" style="margin-left: auto; margin-right: auto;">
                                    <tr>
                                        <th> Name </th>
                                        <th> Price </th>
                                        <th> Category </th>
                                        <th> Quantity </th>
                                    </tr>
                                    
                                    <% for (int i = 0; i < products.size(); i++) { %>
                                    <tr>
                                        <td> <%= products.get(i).getName() %> </td>
                                        <td> <%= products.get(i).getPrice() %> </td>
                                        <td> <%= products.get(i).getCategory() %> </td>
                                        <td> <%= products.get(i).getQuantity() %> </td>
                                    </tr>
                                    <% } %>
                                </table>
                           </div>
                                <br>
                                <br>
                           <div class="productclass">
                                <strong>Would you like to edit the quantity of a specific product? </strong>
                                <form action="productManagement" method="post" autocomplete="on">
                                    <p>
                                    <input type="hidden" name="pid" value="<%= pid %>"/>
                                    <input type="hidden" name="change" value="y"/>
                                    <label for="prodName" class="name">Product name </label><br>
                                    <select name="name">
                                        <% for (int i = 0; i < products.size(); i++) { %>
                                            <option value="<%= products.get(i).getName() %>"> <%= products.get(i).getName() %> </option>
                                        <% } %>
                                    </select>
                                    <br>
                                    <label for="quantity" class=quantity"> Quantity</label><br>
                                    <input type="text" name="quantity" required="required" />
                                    </p>
                                    <p>
                                    <input type="submit" value="Change"/>
                                    </p>
                                </form>
                            <% } %>
                            </div>
                            <br><br>
                            <div class="productclass">
                               <p class="change_link">
									<strong>Would you like to add a new product?</strong><br>
                                    Click <a href="product-add.jsp?pid=<%= pid %>" class="to_register"><u>here</u></a>
								</p>
							</div>
							<br><br>
							<div class="productclass">
								<strong>Would you like to see your sales statistics?</strong><br>
								Click <a href="./salesManagement?pid=<%= pid %>"><u>here</u></a>
							</div>
							<br>
							<br>
            </form>
        </div>
    </body>
</html>

