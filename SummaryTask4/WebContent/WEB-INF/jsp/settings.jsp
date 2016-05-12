<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Settings" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">
		<c:set var = "pageName" value = "settings.jsp"/>
		<c:set var = "commonPage" value = "true"/>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%>
				
				<h1><fmt:message key='settings_jsp.label.userInfo'/></h1>
					<form id="settings_form" name="settingsForm" action="controller">
						<input type="hidden" name="command" value="updateSettings" />

						<div>
							<p><fmt:message key='settings_jsp.label.firstName'/></p>
							<c:choose>
								<c:when test="${firstName!=null}">
									<input name="firstName" value="${firstName}">
								</c:when>
								<c:otherwise>
									<input name="firstName">
								</c:otherwise>
							</c:choose>
							<st4tag:error fieldName="firstName"/>
							<span id="firstNameError" class="error"></span>
						</div>

						<div>
							<p><fmt:message key='settings_jsp.label.lastName'/></p>
							<c:choose>
								<c:when test="${lastName!=null}">
									<input name="lastName" value="${lastName}">
								</c:when>
								<c:otherwise>
									<input name="lastName">
								</c:otherwise>
							</c:choose>
							<st4tag:error fieldName="lastName"/>
							<span id="lastNameError" class="error"></span>
						</div>

						<div>
							<p><fmt:message key='settings_jsp.label.password'/></p>
							<c:choose>
								<c:when test="${password!=null}">
									<input type="password" name="password" value="${password}">
								</c:when>
								<c:otherwise>
									<input type="password" name="password">
								</c:otherwise>
							</c:choose>
							<st4tag:error fieldName="password"/>
							<span id="passwordError" class="error"></span>
						</div>

						<div>
							<p><fmt:message key='settings_jsp.label.confirmPassword'/></p>
							<c:choose>
								<c:when test="${password2!=null}">
									<input type="password" name="password2" value="${password2}">
								</c:when>
								<c:otherwise>
									<input type="password" name="password2">
								</c:otherwise>
							</c:choose>
							<st4tag:error fieldName="password2"/>
							<span id="password2Error" class="error"></span> 
							<span id="passwordIdentityError" class="error"></span>
						</div>
						<span id="emptyFieldsError" class="error"></span> <br /> 
						<input type="submit" name="update" value="<fmt:message key='settings_jsp.button.update'/>"
							onclick="return validate()"><br />
						<c:if test="${updatePassword != null && not empty updatePassword}">
							<p class = "successful">${updatePassword}</p>
						</c:if>
						<c:if test="${updateFirstName != null && not empty updateFirstName}">
							<p class = "successful">${updateFirstName}</p>
						</c:if>
						<c:if test="${updateLastName!= null && not empty updateLastName}">
							<p class = "successful">${updateLastName}</p>
						</c:if>
					</form>
				
				 <%-- CONTENT --%>
				 
				 <script type="text/javascript">
					function validate() {
						var result = true;
						document.getElementById('emptyFieldsError').innerHTML = "";
						document.getElementById('passwordError').innerHTML = "";
						document.getElementById('password2Error').innerHTML = "";
						document.getElementById('passwordIdentityError').innerHTML = "";
						document.getElementById('firstNameError').innerHTML = "";
						document.getElementById('lastNameError').innerHTML = "";

						var a = document.forms["settingsForm"]["password"].value;
						var a2 = document.forms["settingsForm"]["password2"].value;
						var b = document.forms["settingsForm"]["firstName"].value;
						var c = document.forms["settingsForm"]["lastName"].value;
						if (a == "" && a2 == "" && b == "" && c == "") {
							document.getElementById('emptyFieldsError').innerHTML = "Please, fill at least one field.";
							result = false;
						}
						if (a != null && a != "") {
							var regexp = /^[a-zA-Z\u0400-\u04FF0-9!@$%^&*]{4,10}/;
							if (!regexp.test(a)) {
								document.getElementById('passwordError').innerHTML = "Invalid password. Password must have from 4 to 10 signs length. Password can contain numbers, enlish letters and signs: ! @ $ % ^ & *";
								result = false;
							} else {
								if (a2 == null || a2 == "") {
									document.getElementById('password2Error').innerHTML = "You must confirm the password second time.";
									result = false;
								}
							}
						}

						if (a2 != null && a2 != "") {
							var regexp2 = /^[a-zA-Z\u0400-\u04FF0-9!@$%^&*]{4,10}/;
							if (!regexp2.test(a2)) {
								document.getElementById('password2Error').innerHTML = "Invalid password. Password must have from 4 to 10 signs length. Password can contain numbers, enlish letters and signs: ! @ $ % ^ & *";
								result = false;
							} else {
								if (a == null || a == "") {
									document.getElementById('passwordError').innerHTML = "Enter the password";
									result = false;
								}
							}
						}
						if (a != null && a != "" && a2 != null && a2 != "") {
							if (a != a2) {
								document
										.getElementById('passwordIdentityError').innerHTML = "Passwords in first and second field must be the same";
								result = false;
							}
						}
						if (b != null && b != "") {
							var regexp = /[a-zA-Z\u0400-\u04FF ]{1,19}/;
							if (!regexp.test(b)) {
								document.getElementById('firstNameError').innerHTML = "Check your first name. First name max length must be 20 signs.Firast name can contain letters, spaces and sign \"-\"";
								result = false;
							}
						}
						if (c != null && c != "") {
							var regexp = /[a-zA-Z\u0400-\u04FF ]{1,19}/;
							if (!regexp.test(c)) {
								document.getElementById('lastNameError').innerHTML = "Check your last name. Last name max length must be 20 signs.Last name can contain letters, spaces and sign \"-\"";
								result = false;
							}
						}
						return result;
					}
				</script> 
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>