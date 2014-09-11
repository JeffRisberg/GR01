<div id="sidebar">
	<div id="loginForm">
	  <g:if test="${flash.message}">
				<div class="flash">
					${flash.message}
        </div>
			</g:if>
		<g:form controller="user" action="login">
			  <p>
					User Id:
					<g:textField name="userId" size="12" />
				</p>
				<p>
					Password:
					<g:passwordField name="password" size="12" />
				</p>
				<p>
					<g:submitButton name="login" value="Login" />
				</p>
		</g:form>
	</div>
</div>