<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Payments" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">	
	<c:set var = "pageName" value = "payments.jsp"/>
	<%@ include file="/WEB-INF/jspf/header.jspf"%> 
		<tr>
			<td class="content">
			
				<%-- CONTENT --%>
				<h1><fmt:message key='payments_jsp.label.myPaments'/></h1>
					<form id="create_payment" action="controller">
						<input type="hidden" name="command" value="createPayment" />
						<input value="<fmt:message key='payments_jsp.createPaymentForm.submit.createPayment'/>" type="submit" />
					</form> 
					<c:choose>
						<c:when test="${fn:length(userPaymentBeanList) == 0}">
							There are no payments for this user
						</c:when>
						<c:otherwise>
							<table id="list_user_payments_table">
								<thead>
									<tr>
										<c:if test="${orderPaymentNumber == null||orderPaymentNumber == 'asc'}">
											<td><a href="controller?command=sortPayments&orderPaymentNumber=desc&columnName=orderPaymentNumber">№</a></td>
										</c:if>
										<c:if test="${orderPaymentNumber  == 'desc'}">
											<td><a href="controller?command=sortPayments&orderPaymentNumber=asc&columnName=orderPaymentNumber">№</a></td>
										</c:if>
										<c:if test="${orderInitPaymentNumber == null||orderInitPaymentNumber == 'asc'}">
											<td><a href="controller?command=sortPayments&orderInitPaymentNumber=desc&columnName=initPaymentNumber">
											<fmt:message key='payments_jsp.tableLabel.paymentNumber'/></a></td>
										</c:if>
										<c:if test="${orderInitPaymentNumber  == 'desc'}">
											<td><a href="controller?command=sortPayments&orderInitPaymentNumber=asc&columnName=initPaymentNumber">
											<fmt:message key='payments_jsp.tableLabel.paymentNumber'/></a></td>
										</c:if>
										<td><fmt:message key='payments_jsp.tableLabel.numberOfCreditCard'/></td>
										<td><fmt:message key='payments_jsp.tableLabel.numberOfAccount'/></td>
										<td><fmt:message key='payments_jsp.tableLabel.accountStatus'/></td>
										<td><fmt:message key='payments_jsp.tableLabel.sumOfPayment'/></td>
										<c:if test="${orderPaymentDate == null||orderPaymentDate == 'asc'}">
											<td><a href="controller?command=sortPayments&orderPaymentDate=desc&columnName=paymentDate">
												<fmt:message key='payments_jsp.tableLabel.dateTimeOfPayment'/></a>
											</td>
										</c:if>
										<c:if test="${orderPaymentDate  == 'desc'}">
											<td><a href="controller?command=sortPayments&orderPaymentDate=asc&columnName=paymentDate">
												<fmt:message key='payments_jsp.tableLabel.dateTimeOfPayment'/></a>
											</td>
										</c:if>
										<td><fmt:message key='payments_jsp.tableLabel.pdfReport'/></td>
										<td><fmt:message key='payments_jsp.tableLabel.statusOfPayment'/></td>
										<td><fmt:message key='payments_jsp.tableLabel.confirmation'/></td>
										<td class="noBorder"></td>
									</tr>
								</thead>
								<tbody>
									<c:if test="${orderPaymentNumber == null||orderPaymentNumber == 'asc'}">
										<c:set var="k" value="0" />
									</c:if>
									<c:if test="${orderPaymentNumber == 'desc'}">
										<c:set var="k" value="${fn:length(userPaymentBeanList)+1}" />
									</c:if>
									<c:forEach var="item" items="${userPaymentBeanList}">
										<c:if test="${orderPaymentNumber == null||orderPaymentNumber == 'asc'}">
											<c:set var="k" value="${k+1}" />
										</c:if>
										<c:if test="${orderPaymentNumber == 'desc'}">
											<c:set var="k" value="${k-1}" />
										</c:if>
										<tr>
											<td><c:out value="${k}" /></td>
											<td>${item.paymentId}</td>
											<td>${item.creditCardNumber}</td>
											<td>${item.accountNumber}</td>
											<td><st4tag:getLockStatusName
													lockStatusId="${item.accountStatus}" /></td>
											<td>${item.sumOfMoney}</td>
											<td>${item.dateOfPayment} ${item.timeOfPayment}</td>
											<td>
												<form id="createPdfReport" action="controller">
													<input type="hidden" name="command" value="createPdfReport" />
													<input type="hidden" name="paymentId" value="${item.paymentId}" />
													<input type="submit" name="createPdfReport" value="<fmt:message key='payments_jsp.button.createPdfReport'/>"/>
												</form>
											</td>
											<td>${item.paymentStatusName}</td>
											<td>
												<c:choose>
													<c:when test="${item.paymentStatusName == 'confirmed'}">
      									 				successful
    												</c:when>
													<c:otherwise>
														<c:choose>
															<c:when
																test="${item.accountStatus == 0 || item.accountStatus == 2 }">
																<p class="locked">Can't confirm. Account is locked</p>
															</c:when>
															<c:otherwise>
																<form id="confirmPreparedPayment" action="controller">
																	<input type="hidden" name="command" value="confirmPreparedPayment" />
																	<input type="hidden" name="paymentId" value="${item.paymentId}" />
																	<input type="submit" name="confimPreparedPayment" value="<fmt:message key='payments_jsp.createPaymentForm.submit.confirm'/>"/>
																</form>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</td>

											<td class="noBorder">
												<c:if test="${error !=null && item.paymentId == paymentId}">
													<p class="error">${error}</p>
													<p class="error">Sum on account is only
														${sumOnAccount}.</p>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>
						
				<%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>
