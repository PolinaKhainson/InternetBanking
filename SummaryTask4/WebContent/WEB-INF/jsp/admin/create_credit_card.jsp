<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Create credit card" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">
		<c:set var = "pageName" value = "create_credit_card.jsp"/>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%>
				
				<h1><fmt:message key='create_cedit_card_jsp.label.creditCardCreation'/></h1>
				<form name="createCreditCardForm" id="Create Credit Card" action="controller" method="post">
					<input type="hidden" name="command" value="sendCreditCard" />
					
					<div>
						<p><fmt:message key='create_credit_card_jsp.label.user'/>:</p>
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
						<p><fmt:message key='create_account_jsp.label.creditCardNumber'/> (XXXXXXXX - 8 digits):</p>
						<c:choose>
							<c:when test="${creditCardNumber != null}">
								<input name="creditCardNumber" id="creditCardNumber" value="${creditCardNumber}"
									onChange="doNotSubmit(this)">
							</c:when>
							<c:otherwise>
								<input name="creditCardNumber" id="creditCardNumber"
									onChange="doNotSubmit(this)">
							</c:otherwise>
						</c:choose>
						<st4tag:error fieldName="creditCardNumber"/>
						<span id="creditCardNumberError" class="error"></span>
						<c:if test="${error !=null}">
							<p class="error">${error}</p>
						</c:if>
					</div>


					<div>
						<p><fmt:message key='create_account_jsp.label.endingDate'/>:</p>
						Year: 
						<select id="selectedYear" name="selectedYear">
							<option value="-1" selected="selected"><fmt:message key='create_account_jsp.label.chooseYear'/></option>
							<c:forEach var="i" begin="2016" end="2030">
								<option value="${i}" ${i == selectedYear ? 'selected' : ''}>${i}</option>
							</c:forEach>
						</select>
						Month:
						<select id="selectedMonth" name="selectedMonth">
							<option value="-1" selected="selected"><fmt:message key='create_account_jsp.label.chooseMonth'/></option>
							<c:forEach var="month" items="${months}">
								<option value="${month.key}" ${month.key == selectedMonth ? 'selected' : ''}>${month.value}</option>
							</c:forEach>
						</select>
						<st4tag:error fieldName="selectedMonth"/>
						<st4tag:error fieldName="selectedYear"/>
						<span id="selectedMonthError" class="error"></span>
						<span id="selectedYearError" class="error"></span>
						<c:if test="${error !=null}">
							<p class="error">${error}</p>
						</c:if>
					</div>
					
					<br>
					
					<input type="submit" name="cancel" value="<fmt:message key='create_credit_card_jsp.button.cancel'/>">
					<input type="submit" name="create" value="<fmt:message key='create_credit_card_jsp.button.create'/>"
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
						document.getElementById('creditCardNumberError').innerHTML = "";
						document.getElementById('selectedYearError').innerHTML = "";
						document.getElementById('selectedMonthError').innerHTML = "";

						var a = document.forms["createCreditCardForm"]["selectedUserId"].value;
						var b = document.forms["createCreditCardForm"]["creditCardNumber"].value;
						var c = document.forms["createCreditCardForm"]["selectedYear"].value;
						var d = document.forms["createCreditCardForm"]["selectedMonth"].value;
						
						if (a < 1) {
							document.getElementById('userError').innerHTML = "You must select user!";
							result = false;
						}
						
						if (b == null || b== "") {
							document.getElementById('creditCardNumberError').innerHTML = "Credit card number must be filled out";
							result = false;
						}
						var regexp = /[0-9]{8}/;
						if (b != null && !regexp.test(b)) {
							document.getElementById('creditCardNumberError').innerHTML = "Number of credit card can consist from 8 numbers from 0 to 9";
							result = false;
						}
						
						if (c < 1) {
							document.getElementById('selectedYearError').innerHTML = "You must select ending year";
							result = false;
						}
						
						if (d < 1) {
							document.getElementById('selectedMonthError').innerHTML = "You must select ending month";
							result = false;
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