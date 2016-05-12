<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Settings" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">
		<c:set var = "pageName" value = "registration.jsp"/>
		<c:set var = "registrationPage" value = "true"/>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%>
				<h1><fmt:message key='registration_jsp.label.registration'/></h1>
				<form id="registrationForm" name="registrationForm" action="controller">
					<input type="hidden" name="command" value="registrateUser" />

					<div>
						<p><fmt:message key='registration.label.firstName'/></p>
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
						<p><fmt:message key='registration_jsp.label.lastName'/></p>
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
						<p><fmt:message key='registration_jsp.label.login'/></p>
						<c:choose>
							<c:when test="${login!=null}">
								<input name="login" value="${login}">
							</c:when>
							<c:otherwise>
								<input name="login">
							</c:otherwise>
						</c:choose>
						<st4tag:error fieldName="login"/>
						<span id="loginError" class="error"></span>
					</div>

					<div>
						<p><fmt:message key='registration_jsp.label.password'/></p>
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
						<p><fmt:message key='registration_jsp.label.confirmPassword'/></p>
						<c:choose>
							<c:when test="${password2!=null}">
								<input type="password" name="password2" value="${password2}">
							</c:when>
							<c:otherwise>
								<input type="password" name="password2">
							</c:otherwise>
						</c:choose>
						<st4tag:error fieldName="password2"/>
						<span id="password2Error" class="error"></span> <span
							id="passwordIdentityError" class="error"></span>
					</div>
					
					 
					 
					 <div>
						<p><fmt:message key='registration_jsp.label.email'/></p>
						<c:choose>
							<c:when test="${email!=null}">
								<input type="email" name="email" value="${email}">
							</c:when>
							<c:otherwise>
								<input type="email" name="email">
							</c:otherwise>
						</c:choose>
						<st4tag:error fieldName="email"/>
						<span id="emailError" class="error"></span> 
					</div>
					
					<span id="emptyFieldsError" class="error"></span> <br />
					
					<input type="submit" name="registrate" value="<fmt:message key='registration_jsp.button.registrate'/>"
						onclick="return validate()"><br />
			
				</form> <script type="text/javascript">
					function validate() {
						document.getElementById('passwordError').innerHTML = "Invalid password. Password must have from 4 to 10 signs length. Password can contain numbers, enlish letters and signs: ! @ $ % ^ & *";
						var result = true;
						document.getElementById('emptyFieldsError').innerHTML = "";
						document.getElementById('passwordError').innerHTML = "";
						document.getElementById('password2Error').innerHTML = "";
						document.getElementById('passwordIdentityError').innerHTML = "";
						document.getElementById('firstNameError').innerHTML = "";
						document.getElementById('lastNameError').innerHTML = "";
						document.getElementById('emailError').innerHTML = "";
						document.getElementById('loginError').innerHTML = "";
						var a = document.forms["registrationForm"]["password"].value;
						var a2 = document.forms["registrationForm"]["password2"].value;
						var b = document.forms["registrationForm"]["firstName"].value;
						var c = document.forms["registrationForm"]["lastName"].value;
						var e = document.forms["registrationForm"]["email"].value;
						var l = document.forms["registrationForm"]["login"].value;
						
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
							var regexp2 = /^[a-zA-Z0-9\u0400-\u04FF!@$%^&*]{4,10}/;
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
						if (a == "" && a2 == "") {
								document.getElementById('passwordError').innerHTML = "Password is empty.";
								document.getElementById('password2Error').innerHTML = "Confirm password is empty.";
								result = false;
						}
						if (b != null && b != "") {
							var regexp = /[a-zA-Z\u0400-\u04FF ]{1,19}/;
							if (!regexp.test(b)) {
								document.getElementById('firstNameError').innerHTML = "Check your first name. First name max length must be 20 signs.Firast name can contain letters, spaces and sign \"-\"";
								result = false;
							}

						} else {
							document.getElementById('firstNameError').innerHTML = "First name is empty";
							result = false;
						}
						if (c != null && c != "") {
							var regexp = /[a-zA-Z\u0400-\u04FF ]{1,19}/;
							if (!regexp.test(c)) {
								document.getElementById('lastNameError').innerHTML = "Check your last name. Last name max length must be 20 signs.Last name can contain letters, spaces and sign \"-\"";
								result = false;
							}

						} else {
							document.getElementById('lastNameError').innerHTML = "Last name is empty";
							result = false;
						}
						if (l != null && l != "") {
							var regexp = /^[a-zA-Z\u0400-\u04FF0-9_-]{3,16}$/;
							if (!regexp.test(l)) {
								document.getElementById('loginError').innerHTML = "Check your login. Login length must be from 3 to 16 signs.Login can contain letters, underscore, numbers and sign \"-\"";
								result = false;
							}
						} else {
							document.getElementById('loginError').innerHTML = "Login is empty";
							result = false;
						}
						
						if (e != null && e != "") {
							var regexp = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
							if (!regexp.test(e)) {
								document.getElementById('emailError').innerHTML = "Check your email.";
								result = false;
							}
						} else {
							document.getElementById('emailError').innerHTML = "Last name is empty";
							result = false;
						}
						return result;
					}
				</script> <%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>