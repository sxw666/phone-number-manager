<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/jsp/layouts/header.jsp" />
		<title>修改用户 - 用户管理 - 社区居民联系电话管理系统</title>
	</head>
	<body>
		<form action="${pageContext.request.contextPath}/system/user_role/user/handle.action" method="post">
			<table class="table table-bordered font-size-14">
				<thead>
					<c:if test="${messageErrors != null}">  
					    <c:forEach items="${messageErrors}" var="error">  
					        <span style="color:red">${error.defaultMessage}</span><br/>  
					    </c:forEach>
					</c:if>
				</thead>
				<tbody>
					<tr>
						<td class="text-right">用户名</td>
						<td>
							<input type="text" name="username" value="${systemUser.username}" class="form-control" placeholder="请输入用户名" disabled>
						</td>
					</tr>
					<tr>
						<td class="text-right">密码</td>
						<td>
							<input type="password" name="password" class="form-control" placeholder="如果不修改密码，请留空！">
						</td>
					</tr>
					<tr>
						<td class="text-right">确认密码</td>
						<td>
							<input type="password" name="password_sure" class="form-control"  placeholder="如果不修改密码，请留空！">
						</td>
					</tr>
					<tr>
						<td class="text-right">用户角色</td>
						<td>
							<select name="roleId" class="form-control">
								<option value="0">请选择</option>
								<c:forEach items="${userRoles}" var="userRole">
									<option value="${userRole.roleId}"<c:if test="${systemUser.userRole.roleId == userRole.roleId}"> selected</c:if>>${userRole.roleName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="text-right">是否锁定用户</td>
						<td>
							<p class="radio">
								<label><input type="radio" name="isLocked" value="1"<c:if test="${systemUser.isLocked == 1}"> checked</c:if>>是</label>
								<label><input type="radio" name="isLocked" value="0"<c:if test="${systemUser.isLocked == 0}"> checked</c:if>>否</label>
							</p>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="text-center">
							<input type="hidden" name="systemUserId" value="${systemUser.systemUserId}">
							<input type="hidden" name="submissionToken" value="${submissionToken}">
							<input type="submit" value="保存" class="btn btn-primary">
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</body>
</html>