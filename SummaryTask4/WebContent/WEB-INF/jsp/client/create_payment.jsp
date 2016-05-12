<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Payment" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">
		<c:set var = "pageName" value = "create_payment.jsp"/>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%>
				
				<h1><fmt:message key='create_payment_jsp.label.paymentCreation'/></h1>
				<form name="createPaymentForm" id="Create Payment" action="controller" method="post">
					<input type="hidden" name="command" value="sendPayment" />
					
					<div>
						<p><fmt:message key='create_payment_jsp.label.creditCard'/>:</p>
						<select id="selectedCardId" onchange="this.form.submit()" name="selectedCardId">
							<option value="-1" selected="selected">Choose credit card</option>
							<c:forEach var="card" items="${creditCards}">
								<option value="${card.key}" ${card.key == creditCardId ? 'selected' : ''}>${card.value}</option>
							</c:forEach>
						</select>
						<st4tag:error fieldName="selectedCardId"/>
						<span id="cardError" class="error"></span>
					</div>
					
					<div>
						<p><fmt:message key='create_payment_jsp.label.account'/>:</p>
						<select id="selectedAccountId" name="selectedAccountId" onchange="this.form.submit()">
							<c:choose>
								<c:when test="${creditCardId == null || creditCardId < 0}">
									<option value="-1" selected="selected">Choose the
										credit card first.</option>
								</c:when>
								<c:otherwise>
									<option value="-1" selected="selected">Choose account</option>
									<c:forEach var="account" items="${accounts}">
										<c:set var="id" value="${account.key}" />
										<c:choose>
											<c:when test="${accountStatuses[id] == 'locked'}">
												<option value="${account.key}" disabled>${account.value}
													(locked)</option>
											</c:when>
											<c:when test="${accountStatuses[id] == 'waiting_for_unlock'}">
												<option value="${account.key}" disabled>${account.value}
													(waiting for lock)</option>
											</c:when>
											<c:otherwise>
												<option value="${account.key}" ${account.key == selectedAccountId ? 'selected' : ''}>${account.value}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</select>
						<st4tag:error fieldName="selectedAccountId"/>
						<span id="accountError" class="error"></span>
					</div>


					<div>
						<p><fmt:message key='create_payment_jsp.label.sumOfPayment'/>:</p>
						<c:choose>
							<c:when test="${sumOfMoney != null}">
								<input name="sumOfMoney" id="sumOfMoney" value="${sumOfMoney}"
									onChange="doNotSubmit(this)">
							</c:when>
							<c:otherwise>
								<input name="sumOfMoney" id="sumOfMoney"
									onChange="doNotSubmit(this)">
							</c:otherwise>
						</c:choose>
						<st4tag:error fieldName="sumOfMoney"/>
						<span id="sumOfMoneyError" class="error"></span>
						<c:if test="${error !=null}">
							<p class="error">${error}</p>
							<p class="error">Sum on account is only ${sumOnAccount}.</p>
						</c:if>
					</div>
					
					<br>
					<input type = "hidden" name="sumOnAccount" id="sumOnAccount" value="${sumOnAccount}">
					<input type="submit" name="cancelPayment" value="<fmt:message key='create_payment_jsp.button.cancelPayment'/>">
					<input type="submit" name="preparePayment" value="<fmt:message key='create_payment_jsp.button.preparePayment'/>"
						onclick="return validate(1)"> 
					<input type="submit" name="confirmPayment" value="<fmt:message key='create_payment_jsp.button.confirmPayment'/>"
						onclick="return validate(2)">
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

					function validate(i) {
						var result = true;
						document.getElementById('cardError').innerHTML = "";
						document.getElementById('accountError').innerHTML = "";
						document.getElementById('sumOfMoneyError').innerHTML = "";

						var a = document.forms["createPaymentForm"]["selectedCardId"].value;
						var b = document.forms["createPaymentForm"]["selectedAccountId"].value;
						var c = document.forms["createPaymentForm"]["sumOfMoney"].value;
						var d = document.forms["createPaymentForm"]["sumOnAccount"].value;
						var cancel = document.forms["createPaymentForm"]["cancelPayment"].value;
						if (a < 1) {
							document.getElementById('cardError').innerHTML = "You must select credit card!";
							result = false;
						}
						if (b < 1) {
							document.getElementById('accountError').innerHTML = "You must select account!";
							result = false;
						}
						if (c == null || c == "") {
							document.getElementById('sumOfMoneyError').innerHTML = "Sum must be filled out";
							result = false;
						}
						if (c != null && c == 0) {
							document.getElementById('sumOfMoneyError').innerHTML = "Sum can't be equal 0";
							result = false;
						}
						var regexp = /^\d+(?:\.?\d{0,2})$/;
						if (!regexp.test(c)) {
							document.getElementById('sumOfMoneyError').innerHTML = "Sum of money must be a number with or "
									+ "without decimal point. \nAfter decimal point there can be none, one or two signs.";
							result = false;
						} else {
							//var moneyToPay = parseFloat(c);
						var moneyOnAccount = parseFloat(d);
							if(a > 1 && b > 1 && c > moneyOnAccount && i == 2){
								document.getElementById('sumOfMoneyError').innerHTML = "Not enough money. Sum on account is only" +d;
							result = false;
							}
						}
						if (c > 1000000000000) {
							document.getElementById('sumOfMoneyError').innerHTML = "Too large sum of money\nMax sum for payment is 1 000 000 000 000";
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