<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Create account" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">
		<c:set var = "pageName" value = "create_account.jsp"/>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%>
				
				<h1><fmt:message key='create_account_jsp.label.accountCreation'/></h1>
				<form name="createAccountForm" id="Create Account" action="controller" method="post">
					<input type="hidden" name="command" value="sendAccount" />
					
					<div>
						<p><fmt:message key='create_account_jsp.label.user'/>:</p>
						<select id="selectedUserId" name="selectedUserId">
							<option value="-1" selected="selected"><fmt:message key='create_account_jsp.label.chooseUser'/></option>
							<c:forEach var="user" items="${users}">
								<option value="${user.key}" ${user.key == userId ? 'selected' : ''}>${user.value}</option>
							</c:forEach>
						</select>
						<st4tag:error fieldName="selectedUserId"/>
						<span id="userError" class="error"></span>
					</div>
					
					<div>
						<p><fmt:message key='create_account_jsp.label.accountNumber'/> (XXXXXX - 6 digits):</p>
						<c:choose>
							<c:when test="${accountNumber != null}">
								<input name="accountNumber" id="accountNumber" value="${accountNumber}"
									onChange="doNotSubmit(this)">
							</c:when>
							<c:otherwise>
								<input name="accountNumber" id="accountNumber"
									onChange="doNotSubmit(this)">
							</c:otherwise>
						</c:choose>
						<st4tag:error fieldName="accountNumber"/>
						<span id="accountNumberError" class="error"></span>
						<c:if test="${error !=null}">
							<p class="error">${error}</p>
						</c:if>
					</div>


					<div>
						<p><fmt:message key='create_account_jsp.label.sumOnAccount'/>:</p>
						<c:choose>
							<c:when test="${sumOnAccount != null}">
								<input name="sumOnAccount" id="sumOnAccount" value="${sumOnAccount}"
									onChange="doNotSubmit(this)">
							</c:when>
							<c:otherwise>
								<input name="sumOnAccount" id="sumOnAccount"
									onChange="doNotSubmit(this)">
							</c:otherwise>
						</c:choose>
						<st4tag:error fieldName="sumOnAccount"/>
						<span id="sumOnAccountError" class="error"></span>
						<c:if test="${error !=null}">
							<p class="error">${error}</p>
						</c:if>
					</div>
					
					<br>
					
					<input type="submit" name="cancel" value="<fmt:message key='create_account_jsp.button.cancel'/>">
					<input type="submit" name="create" value="<fmt:message key='create_account_jsp.button.create'/>"
						onclick="return validate()"> 
				</form> 
				
				<%-- CONTENT --%>
				
				<script type="text/javascript">
					function stopRKey(evt) {
						var evt = (evt) ? evt : ((event) ? event : null);
						var node = (evt.target) ? evt.target
								: ((evt.srcElement) ? evt.srcElement : null);
						if ((evt.keyCode == 13) && (node.type == "text")) {
							return false;
						}
					}

					document.onkeypress = stopRKey;

					function validate() {
						var result = true;
						document.getElementById('userError').innerHTML = "";
						document.getElementById('accountNumberError').innerHTML = "";
						document.getElementById('sumOnAccountError').innerHTML = "";

						var a = document.forms["createAccountForm"]["selectedUserId"].value;
						var b = document.forms["createAccountForm"]["accountNumber"].value;
						var c = document.forms["createAccountForm"]["sumOnAccount"].value;
						var cancel = document.forms["createAccountForm"]["cancel"].value;
						if (a < 1) {
							document.getElementById('userError').innerHTML = "You must select user!";
							result = false;
						}
						
						if (b == null || b== "") {
							document.getElementById('accountNumberError').innerHTML = "Account must be filled out";
							result = false;
						}
						var regexp = /[0-9]{6}/;
						if (b != null && !regexp.test(b)) {
							document.getElementById('accountNumberError').innerHTML = "Number of account can consist 6 numbers from 0 to 9";
							result = false;
						}
						
						if (c == null || c == "") {
							document.getElementById('sumOnAccountError').innerHTML = "Sum must be filled out";
							result = false;
						}
						
						var regexp = /^\d+(?:\.?\d{0,2})$/;
						if (!regexp.test(c)) {
							document.getElementById('sumOnAccountError').innerHTML = "Sum of money must be a number with or "
									+ "without decimal point. \nAfter decimal point there can be none, one or two signs.";
							result = false;
						} else {
							if (c > 1000000000000) {
								document.getElementById('sumOnAccountError').innerHTML = "Too large sum of money\nMax sum for payment is 1 000 000 000 000";
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