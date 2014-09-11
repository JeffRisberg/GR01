<div id="sidebar">
	<div id="logoutForm">
		<g:form controller="user" action="logout">
			<g:submitButton name="logout" value="Logout" />
		</g:form>
		<p>&nbsp;</p>
		<g:form controller="user" action="gamePlan">
	    <g:submitButton name="gamePlan" value="Game Plan" />
		</g:form>
			<g:form controller="user" action="chart">
	    <g:submitButton name="chart" value="Chart" />
		</g:form>
		<g:form controller="user" action="profile">
	    <g:submitButton name="profile" value="Profile" />
		</g:form>
		<g:form controller="activity" action="timeline">
	    <g:submitButton name="timeline" value="Timeline" />
		</g:form>
	</div>
</div>