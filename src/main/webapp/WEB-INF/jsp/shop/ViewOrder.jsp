<%@ include file="jspf/IncludeTop.jspf" %>

<table align="left" bgcolor="#000000" border="0" cellspacing="2" cellpadding="2">
    <tr><td bgcolor="#99FF22">
            <a href="<c:url value="/shop/index.html"/>"><b><font color="BLACK" size="2">&lt;&lt; Main Menu</font></b></a>
        </td></tr>
    <tr><td bgcolor="#99FF22">
        </td></tr>
</table>

<c:if test="${!empty message}">
    <center><b><c:out value="${message}"/></b></center>
</c:if>

<p>

<table width="60%" align="center" border="0" cellpadding="3" cellspacing="1" bgcolor="#000000">
    <tr bgcolor="#99FF22"><td align="center" colspan="2">
            <font size="4"><b>Order #<c:out value="${order.id}"/></b></font>
            <br /><font size="3"><b><fmt:formatDate value="${order.createTime.time}" pattern="yyyy/MM/dd hh:mm:ss" /></b></font>
        </td></tr>
    <tr bgcolor="#99FF22"><td colspan="2">
            <font color="black" size="4"><b>Payment Details</b></font>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Card Type:</td><td>
            <c:out value="${order.cardType}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Card Number:</td><td><c:out value="${order.cardNumber}"/> <font color="red" size="2">* Fake number!</font>
        </td></tr>
    <tr bgcolor="#99FF22">
        <td>Expiry Date (MM/YYYY):</td>
        <td><fmt:formatDate value="${order.expireDate.time}" pattern="MM/yyyy" />"</td>
    </tr>
    <tr bgcolor="#99FF22"><td colspan="2">
            <font color="black" size="4"><b>Billing Address</b></font>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            First name:</td><td><c:out value="${order.billToFirstname}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Last name:</td><td><c:out value="${order.billToLastname}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Address 1:</td><td><c:out value="${order.billAddress.addr1}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Address 2:</td><td><c:out value="${order.billAddress.addr2}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            City: </td><td><c:out value="${order.billAddress.city}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            State:</td><td><c:out value="${order.billAddress.state}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Zip:</td><td><c:out value="${order.billAddress.zipcode}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Country: </td><td><c:out value="${order.billAddress.country}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td colspan="2">
            <font color="black" size="4"><b>Shipping Address</b></font>
        </td></tr><tr bgcolor="#99FF22"><td>
            First name:</td><td><c:out value="${order.shipToFirstname}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Last name:</td><td><c:out value="${order.shipToLastname}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Address 1:</td><td><c:out value="${order.shipAddress.addr1}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Address 2:</td><td><c:out value="${order.shipAddress.addr2}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            City: </td><td><c:out value="${order.shipAddress.city}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            State:</td><td><c:out value="${order.shipAddress.state}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Zip:</td><td><c:out value="${order.shipAddress.zipcode}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Country: </td><td><c:out value="${order.shipAddress.country}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Courier: </td><td><c:out value="${order.courier}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td colspan="2">
            <b><font color="black" size="4">Status:</font> <c:out value="${order.orderStatus}"/></b>
        </td></tr>
    <tr bgcolor="#99FF22"><td colspan="2">
            <table width="100%" align="center" bgcolor="#000000" border="0" cellspacing="2" cellpadding="3">
                <tr bgcolor="#99FF22">
                    <td><b>Item Name</b></td>
                    <td><b>Description</b></td>
                    <td><b>Quantity</b></td>
                    <td><b>Price</b></td>
                    <td><b>Total Cost</b></td>
                </tr>
                <c:forEach var="lineItem" items="${order.orderLineItems}">
                    <tr bgcolor="#99FF22">
                        <td><b><a href="<c:url value="/shop/viewItem.html"><c:param name="itemName" value="${lineItem.item.itemName}"/></c:url>">
                                    <font color="BLACK"><c:out value="${lineItem.item.itemName}"/></font>
                                </a></b></td>
                        <td>
                            <c:out value="${lineItem.item.attr1}"/>
                            <c:out value="${lineItem.item.attr2}"/>
                            <c:out value="${lineItem.item.attr3}"/>
                            <c:out value="${lineItem.item.attr4}"/>
                            <c:out value="${lineItem.item.attr5}"/>
                            <c:out value="${lineItem.item.product.productName}"/>
                        </td>
                        <td><c:out value="${lineItem.quantity}"/></td>
                        <td align="right"><fmt:formatNumber value="${lineItem.unitPrice}" pattern="$#,##0.00"/></td>
                        <td align="right"><fmt:formatNumber value="${lineItem.totalPrice}" pattern="$#,##0.00"/></td>
                    </tr>
                </c:forEach>
                <tr bgcolor="#99FF22">
                    <td colspan="5" align="right"><b>Total: <fmt:formatNumber value="${order.totalPrice}" pattern="$#,##0.00"/></b></td>
                </tr>
            </table>
        </td></tr>

</table>

<%@ include file="jspf/IncludeBottom.jspf" %>
