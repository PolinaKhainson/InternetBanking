<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Successful Registration" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">
		<c:set var = "pageName" value = "successful_registration.jsp"/>
		<c:set var = "registrationPage" value = "true"/>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%>
				<h1 class = "successful"><fmt:message key='successful_registration_jsp.label.successful_registration'/></h1>
				<p><fmt:message key='successful_registration_jsp.label.hello'/> ${firstName}  ${lastName}!</p>
				<p><fmt:message key='successful_registration_jsp.label.backToLoginPage'/></p>
				
				<button type="button" onclick="location.href='login.jsp';"><fmt:message key='successful_registration_jsp.button.backToLoginPage'/></button>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>