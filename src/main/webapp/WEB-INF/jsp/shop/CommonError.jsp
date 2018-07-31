<%@ include file="jspf/IncludeTop.jspf" %>

<table width="60%" align="center" border="0" cellpadding="3" cellspacing="1" bgcolor="#000000">
    <tr bgcolor="#99FF22">
        <td align="center">
            <c:if test="${empty commonError.path}">
                Sorry, an error took place when access your requested resource, please try again later.
            </c:if>
            <c:if test="${!empty commonError.path}">
                Sorry, an error took place when access to  <c:out value="${commonError.path}"/>: &nbsp; <c:out value="${commonError.message}"/>
                <br />please try again later.
            </c:if>
        </td>
    </tr>
    <tr bgcolor="#99FF22">
        <td align="center">
            <c:if test="${!empty commonError.exception}">
                <center><b><c:out value="${commonError.exception}"/></b></center>
            </c:if>
        </td>
    </tr>
    <tr bgcolor="#99FF22">
        <td align="center">
            <a href="<c:url value="/shop/index.html"/>">Home</a>
        </td>
    </tr>
</table>

<%@ include file="jspf/IncludeBottom.jspf" %>