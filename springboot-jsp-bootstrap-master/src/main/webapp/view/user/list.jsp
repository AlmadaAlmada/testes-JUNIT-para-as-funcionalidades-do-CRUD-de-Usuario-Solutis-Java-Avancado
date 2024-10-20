<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>


<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<link rel="stylesheet" href="${path}/webjars/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css">

<script type="text/javascript" src="${path}/webjars/jquery/3.2.0/jquery.min.js"></script>
<script type="text/javascript" src="${path}/js/jquery.boot.js"></script>


<div class="panel panel-default">
	<div class="panel-heading">
		<strong>
			<span class="glyphicon glyphicon-list"></span> User List
		</strong>
		<div class="pull-right">
			<a href="javascript:void(0);" onclick="addForm('user')">
				<span class="glyphicon glyphicon-plus-sign"></span> New User
			</a>
		</div>
	</div>
	<div class="panel-body">
		<table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
				<tr>
					<th>Full Name</th>
					<th>User Id</th>
					<th>User Name</th>
					<th>Email</th>
					<th>Mobile</th>
					<th>Role</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td>${user.fullName}</td>
						<td>${user.userId}</td>
						<td>${user.userName}</td>
						<td>${user.email}</td>
						<td>${user.mobile}</td>
						<td>${user.role.name}</td>
						<td><a href="javascript:void(0);" onclick="editForm('user', '${user.id}')"><span class="glyphicon glyphicon-edit"></span></a></td>
						<td><a href="javascript:void(0);" onclick="deleteData('user', '${user.id}')"><span class="glyphicon glyphicon-trash"></span></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>