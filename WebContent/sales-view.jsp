<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.List, tum.in.dbpra.bean.SaleBean" %>

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
                <h1 style="font-size: 300%;">Sales Management</h1>
			</header>
             			<div class="productclass">
                            <% List<SaleBean> sales = (List<SaleBean>) request.getAttribute("sales");
                                int pid = (Integer) request.getAttribute("pid");
                                double total = 0.0;
                                double totalProduct = 0.0;
                                if (sales.size() == 0) {                            
                            %>
                                <strong>There aren't any sale yet </strong>
                            <% } else { %>
                            	<strong>This is the list of your products: </strong>
                                <% boolean diffProd = false;
                                   int i = 0;
                                   while (i < sales.size()) {
                                    if (i == 0 || !sales.get(i).getProductName().equals(sales.get(i - 1).getProductName())) {
                                    %>
                                <table border="2" style="margin-left: auto; margin-right: auto;">
                                    <tr>
                                        <th> Product Name </th>
                                        <th> Section </th>
                                        <th> Sold Quantity </th>
                                        <th> Income </th>
                                    </tr>
                                    <% } %>
                                    <tr>
                                        <td> <%= sales.get(i).getProductName() %> </td>
                                        <td> <%= sales.get(i).getSection() %> </td>
                                        <td> <%= sales.get(i).getSoldquantity() %> </td>
                                        <td> <%= sales.get(i).getIncome() %> </td>
                                    </tr>
                                    <% total += sales.get(i).getIncome();
                                        if (i == 0 || sales.get(i).getProductName().equals(sales.get(i - 1).getProductName())) {
                                            totalProduct += sales.get(i).getIncome();
                                        }
                                        else {
                
                                    %>
                                    <tr> Total for this product: <%= totalProduct %></tr>
                                    </table>
                                    <% totalProduct = sales.get(i).getIncome();
                                    } %> 
                                    <% i++; 
                                    } 
                                    %>
                                    <tr> Total for this product: <%= totalProduct %></tr>
                                    </table>
                                    <br /><br />
                                    Total income: <%= total %>
                                    <% } %>
                           </div>
                                <br>
                                <br>
                                   </div>
    </body>
</html>

