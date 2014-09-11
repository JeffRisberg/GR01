<html>
	<head>
		<title>Game Plan: ${profile.fullName}</title>
		<meta name="layout" content="main" />
		<style>
			.profilePic {
			border: 1px dotted gray;
			background: lightyellow;
			padding: 1em;
			font-size: 1em;
			}
</style>
	</head>
	<body>
		<div class="profilePic">
			<g:if test="${profile.photo}">
				<img src="
					<g:createLink controller='image' action='renderImage'
						id='${user.userId}' />
					"/>
			</g:if>
			<p>
				Game Plan for <strong>${profile.fullName}</strong>
			</p>
			<p>Email: ${profile.email}</p>
			<p>Country: ${profile.country}</p>
			<p>Time Zone: ${profile.timeZone}</p>
		</div>

		<div class="gamePlan" style="margin: 5px 5px 10px 5px;">
			<table>
				<g:each var="a" in="${resultList}">
					<tr>
					    <td>${a[0].name}</td>
					    <td>${a[2]} times</td>
					    <g:if test="${a[2]}">
					    <td>${a[3]} total ${a[1]}</td>
					    </g:if>
					    <g:else>
					    <td></td>
					    </g:else>
					</tr>
				</g:each>
			</table>
		</div>
		<g:link class="buttons" controller="user" action="newActivity">Add New Activity</g:link>
		<g:link class="buttons" controller="user" action="selectActivityTypes">Change Activity Types</g:link>
	</body>
</html>
