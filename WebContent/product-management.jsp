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
            <div class="codrops-top"><header>
              <h1><strong>Database Practical Final Project</strong></h1></header>			
                  <span class="right">
                    <a href=" ">
                        <strong>Back to the Main Page</strong>
                    </a>
                </span>
                <div class="clr"></div>
            </div><!--/ Codrops top bar -->
            <header>
                <h1 style="font-size: 300%;">Provider Login</h1>
			</header>
            <section>				
                <div id="container_demo" >
                    <!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
                    <a class="hiddenanchor" id="toregister"></a>
                    <a class="hiddenanchor" id="tologin"></a>
                    <div id="wrapper">
                        <div id="login" class="animate form">
                            <% List<ProductBean> products = (List<ProductBean>) request.getAttribute("products");
                                int pid = (Integer) request.getAttribute("pid");
                                if (products.size() == 0) {                            
                            %>
                                <strong>There aren't any product registered </strong>
                            <% } else { %>
                                <table>
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
                                <form action="productManagement" method="post" autocomplete="on">
                                    <p>
                                    <input type="hidden" name="pid" value="<%= pid %>"/>
                                    <input type="hidden" name="change" value="y"/>
                                    <label for="prodName" class="name">Product name </label>
                                    <select name="name">
                                        <% for (int i = 0; i < products.size(); i++) { %>
                                            <option value="<%= products.get(i).getName() %>"> <%= products.get(i).getName() %> </option>
                                        <% } %>
                                    </select>
                                    <label for="quantity" class=quantity"> Quantity</label>
                                    <input type="text" name="quantity" required="required" placeholder="The new quantity should be greater than the actual one"/>
                                    </p>
                                    <p>
                                    <input type="submit" value="Change"/>
                                    </p>
                                </form>
                            <% } %>
                               <p class="change_link">
									Want to submit a product?
                                    <a href="product-add.jsp?pid=<%= pid %>" class="to_register">Submit product</a>
								</p>
                        </div>

                    </div>
                </div>  
            </section>
        </div>
    </body>
</html>
