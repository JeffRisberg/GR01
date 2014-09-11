<html>
	<head>
		<title>GR01 Home</title>
		<meta name="layout" content="home" />
	</head>
	<body>
		<div class="content">
			<g:hasErrors>
				<div class="errors">
					<g:renderErrors bean="${user}" as="list" />
				</div>
			</g:hasErrors>
			<br />
			<div style="float: left"><img src="${createLinkTo(dir: 'images', file: 'homeLogo.gif')}" /></div>
			<g:if test="${session.user == null}">
				<g:render template="/login" />
			</g:if>
			<g:else>
				<g:render template="/logout" />
			</g:else>
			<div style="clear:both"></div>
		</div>
	</body>
</html>