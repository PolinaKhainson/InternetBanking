<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
	
<body>

<%--=========================================================================== 
Here we use a table layout.
Class page corresponds to the '.page' element in included CSS document.
===========================================================================--%> 
	<table id="main-container">

<%--=========================================================================== 
This is the HEADER, containing a top menu.
header.jspf contains all necessary functionality for it.
Just included it in this JSP document.
===========================================================================--%> 

		<%-- HEADER --%>
		<c:set var = "pageName" value = "login.jsp"/>
		<c:set var = "loginPage" value = "true"/>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>

<%--=========================================================================== 
This is the CONTENT, containing the main part of the page.
===========================================================================--%> 
		<tr >
			<td class="content center">
			<%-- CONTENT --%>
			
<%--=========================================================================== 
Defines the web form.
===========================================================================--%> 
				<form id="login_form" action="controller" method="post">

<%--=========================================================================== 
Hidden field. In the query it will act as command=login.
The purpose of this to define the command name, which have to be executed 
after you submit current form. 
===========================================================================--%> 
					<input type="hidden" name="command" value="login"/>

					<fieldset >
						<legend><fmt:message key='login_jsp.label.login'/></legend>
						<input name="login"/><br/>
					</fieldset><br/>
					<fieldset>
						<legend><fmt:message key='login_jsp.label.password'/></legend>
						<input type="password" name="password"/>
					</fieldset><br/>
					
					<input type="submit" value="<fmt:message key='login_jsp.button.login'/>">								
				</form>
				<a href="registration.jsp"><fmt:message key="login_jsp.link.registration"/></a>
			<%-- CONTENT --%>

			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
		
	</table>
</body>
</html>