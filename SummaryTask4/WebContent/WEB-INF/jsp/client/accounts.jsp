<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="Accounts" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>

	<table id="main-container">
		<c:set var = "pageName" value = "accounts.jsp"/>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%>

				<h1><fmt:message key='accounts_jsp.label.myAccounts'/></h1> <c:choose>
					<c:when test="${fn:length(accountCreditCardsBeanList) == 0}">
							There are no accounts for this user
					</c:when>
					<c:otherwise>
						<table id="list_user_accounts_table">
							<thead>
								<tr>
									<c:if test="${orderAccountNumber == null||orderAccountNumber == 'asc'}">
										<td><a href="controller?command=sortAccounts&orderAccountNumber=desc&columnName=orderAccountNumber">№</a></td>
									</c:if>
									<c:if test="${orderAccountNumber  == 'desc'}">
										<td><a href="controller?command=sortAccounts&orderAccountNumber=asc&columnName=orderAccountNumber">№</a></td>
									</c:if>
									<c:if test="${orderInitAccountNumber == null||orderInitAccountNumber == 'asc'}">
										<td><a href="controller?command=sortAccounts&orderInitAccountNumber=desc&columnName=initAccountNumber">
											<fmt:message key='accounts_jsp.tableLabel.accountNumber'/></a>
										</td>
									</c:if>
									<c:if test="${orderInitAccountNumber  == 'desc'}">
										<td><a href="controller?command=sortAccounts&orderInitAccountNumber=asc&columnName=initAccountNumber">
											<fmt:message key='accounts_jsp.tableLabel.accountNumber'/></a>
										</td>
									</c:if>
									<c:if test="${orderAccountSumOfMoney == null||orderAccountSumOfMoney == 'asc'}">
										<td><a href="controller?command=sortAccounts&orderAccountSumOfMoney=desc&columnName=accountSumOfMoney">
											<fmt:message key='accounts_jsp.tableLabel.sumOnAccount'/></a>
										</td>
									</c:if>
									<c:if test="${orderAccountSumOfMoney  == 'desc'}">
										<td><a href="controller?command=sortAccounts&orderAccountSumOfMoney=asc&columnName=accountSumOfMoney">
											<fmt:message key='accounts_jsp.tableLabel.sumOnAccount'/></a>
										</td>
									</c:if>
									<td><fmt:message key='accounts_jsp.tableLabel.assignedCreditCardsNumberAndDate'/></td>
									<td><fmt:message key='accounts_jsp.tableLabel.lockStatus'/></td>
									<td><fmt:message key='accounts_jsp.tableLabel.locking_unlocking'/></td>
									<td><fmt:message key='accounts_jsp.tableLabel.addFunds'/></td>
									<td class="noBorder"></td>
								</tr>
							</thead>
							<c:if test="${orderAccountNumber == null||orderAccountNumber == 'asc'}">
								<c:set var="k" value="0" />
							</c:if>
							<c:if test="${orderAccountNumber == 'desc'}">
								<c:set var="k" value="${fn:length(accountCreditCardsBeanList)+1}" />
							</c:if>
							<c:forEach var="item" items="${accountCreditCardsBeanList}">
								<c:if test="${orderAccountNumber == null||orderAccountNumber == 'asc'}">
									<c:set var="k" value="${k+1}" />
								</c:if>
								<c:if test="${orderAccountNumber == 'desc'}">
									<c:set var="k" value="${k-1}" />
								</c:if>
								<tr>
									<td><c:out value="${k}" /></td>
									<td>${item.account.accountNumber}</td>
									<td>${item.account.sumOnAccount}</td>
									<td>
										<c:choose>
											<c:when test="${fn:length(item.creditCardslist) == 0}">
												No assingned credit cards.
											</c:when>
											<c:otherwise>
												<c:forEach var="card" items="${item.creditCardslist}">
													${card.cardNumber} /(${card.endingDate})<br>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<st4tag:getLockStatusName lockStatusId = "${item.account.lockStatusId}"/>
									</td>
									<td>
										<c:choose>
											<c:when test="${item.account.lockStatusId == 0 ||item.account.lockStatusId == 2}">
												<form id="unlockAccountForm" action="controller">
													<input type="hidden" name="command" value="unlockAccount" />
													<input type="hidden" name="accountId" value="${item.account.id}" />
													<input value="<fmt:message key='accounts_jsp.button.unlock'/>" type="submit" />
												</form>
											</c:when>
											<c:otherwise>
												<form id="lockAccountForm" action="controller">
													<input type="hidden" name="command" value="lockAccount" />
													<input type="hidden" name="accountId" value="${item.account.id}" />
													<input value="<fmt:message key='accounts_jsp.button.lock'/>" type="submit" />
												</form>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when
												test="${item.account.lockStatusId == 0||item.account.lockStatusId == 2}">
												-
											</c:when>
											<c:otherwise>
												<form name="addFundsForm${item.account.id}" id="Add Funds"
													action="controller" method="post">
													<input type="hidden" name="command" value="addFunds" /> 
													<input type="hidden" name="accountId" value="${item.account.id}" />	
													<c:choose>
														<c:when test="${accountId ==item.account.id && sumOfMoney != null}">
															<input name="sumOfMoney${item.account.id}"
																id="sumOfMoney" value="${sumOfMoney}">
														</c:when>
														<c:otherwise>
															<input name="sumOfMoney${item.account.id}"
																id="sumOfMoney">
														</c:otherwise>
													</c:choose>
													<input name="addFunds${item.account.id}" value="<fmt:message key='accounts_jsp.button.addFunds'/>"
														type="submit" onclick="return validate(<c:out value='${item.account.id}'/>)" />
												</form>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="noBorder">
										<c:if test="${errors.containsKey('sumOfMoney') && accountId == item.account.id}">
											<p class="error">${errors.get('sumOfMoney')}</p>

										</c:if> 
										<c:if test="${successfulAddFunds != null && accountId == item.account.id}">
											<span class = "successful">${successfulAddFunds}</span>
										</c:if> 
											<span id="sumOfMoneyError${item.account.id}" class="error"></span>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose> 
				<c:if test="${error !=null}">
					<p class="error">${error}</p>
				</c:if> 
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
					function validate(accountId) {
						result = true;
						var form = "addFundsForm" + accountId;
						var sumOfMoney = "sumOfMoney" + accountId; 
						var errorName = "sumOfMoneyError" + accountId;
						var d = document.forms[form]["accountId"].value;
						var c = document.forms[form][sumOfMoney].value;
						document.getElementById(errorName).innerHTML = "";
						if (c != null && c == 0) {
							document.getElementById(errorName).innerHTML = "Sum can't be equal 0";
							result = false;
						}
						if (c == null || c == "") {
							document.getElementById(errorName).innerHTML = "Sum must be filled out";
							result = false;
						} else {
							var regexp = /^\d+(?:\.?\d{0,2})$/;
							if (!regexp.test(c)) {
								document.getElementById(errorName).innerHTML = "Sum of money must be a number with or "
									+ "without decimal point. \nAfter decimal point there can be none, one or two signs.";
								result = false;
							} else {
								if (c > 1000000000000) {
									document.getElementById(errorName).innerHTML = "Too large sum of money\nMax sum is 1 000 000 000 000";
									result = false;
								}
							}
						}
						if(result == false) {
							document.getElementById("successful").innerHTML = "";
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
