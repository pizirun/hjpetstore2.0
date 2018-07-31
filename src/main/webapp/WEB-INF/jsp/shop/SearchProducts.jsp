<%@ include file="jspf/IncludeTop.jspf" %>

<table align="left" bgcolor="#000000" border="0" cellspacing="2" cellpadding="2">
    <tr><td bgcolor="#99FF22">
            <a href="<c:url value="/shop/index.html"/>"><b><font color="BLACK" size="2">&lt;&lt; Main Menu</font></b></a>
        </td></tr>
</table>

<table align="center" bgcolor="#000000" border="0" cellspacing="2" cellpadding="3">
    <tr bgcolor="#000000">  
        <td>&nbsp;</td>
        <td><b>Product ID</b></td>
        <td><b>Name</b></td>
    </tr>
    <c:forEach var="product" items="${productList.pageList}">
        <tr bgcolor="#99FF22">
            <td>
                <a href="<c:url value="/shop/viewProduct.html"><c:param name="productNumber" value="${product.productNumber}"/></c:url>">
                    <%--<c:out value="${product.productDesc}" escapeXml="false"/> --%>
                    <img src="../images/<c:out value='${product.image}'/>" border="0">
                    <c:out value="${product.productDesc}"/>
                </a>
            </td>
            <td>
                <b>
                    <a href="<c:url value="/shop/viewProduct.html"><c:param name="productNumber" value="${product.productNumber}"/></c:url>">
                        <c:out value="${product.productNumber}"/>
                    </a>
                </b>
            </td>
            <td><c:out value="${product.productName}"/></td>
        </tr>
    </c:forEach>
    <tr>
        <td>
            <c:if test="${!productList.firstPage}">
                <a href="?page=previous"><font color="white"><B>&lt;&lt; Prev</B></font></a>
            </c:if>
            <c:if test="${!productList.lastPage}">
                <a href="?page=next"><font color="white"><B>Next &gt;&gt;</B></font></a>
            </c:if>
        </td>
    </tr>

</table>

<%@ include file="jspf/IncludeBottom.jspf" %>
