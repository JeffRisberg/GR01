<html>
	<head>
		<title>New Activity For ${profile.fullName}</title>
		<meta name="layout" content="main" />
		<g:javascript library="scriptaculous" />
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
	 <g:javascript>
	    function updateUomName(e) {
	      // find the uomName span and change its text
	      document.getElementById('uomName').innerHTML = e.responseText;
	    }
	    function toggleDescription() {
	      var descriptionDisplay = $('description').style.display;
	      var toggleText = $('showHideDescription');
	      
	      if (descriptionDisplay == 'none') {
	         new Effect.BlindDown('description', { duration: 0.5 });
	         toggleText.innerHTML = "Hide Description";
	      }
	      else {
	         new Effect.BlindUp('description', { duration: 0.5 });
	         toggleText.innerHTML = "Show Description";
	      }    
	    }
	 </g:javascript>	
	 <div class="profilePic">
			<g:if test="${profile.photo}">
				<img src="
					<g:createLink controller='image' action='renderImage'
						id='${user.userId}' />
					"/>
			</g:if>
			<p>
				New Activity for <strong>${profile.fullName}</strong>
			</p>
		</div>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${userInstance}">
        <div class="errors">
             <g:renderErrors bean="${userInstance}" as="list" />
        </div>
    </g:hasErrors>
		<g:form controller="user">
			What: 
			<g:select name="activityTypeId" from="${activityTypes}" optionKey="id" optionValue="name" 
			      onchange="${remoteFunction(
              controller:'activity', 
              action:'ajaxGetUomName', 
              params:'\'id=\' + escape(this.value)', 
              onComplete:'updateUomName(e)')}">
			</g:select>
			<p></p>
			Amount: <g:textField name="amount" value="${amount}" /> <span id="uomName">${unitName}</span>
			<p/>
			When: <g:datePicker name="startDate" precision="day" />
			<p/>
			<a href="#" id="showHideDescription" onClick="return toggleDescription()">Show Description</a>
			<div id="description" style="display:none">
			  Description: <g:textField name="description" value="${description}" size="40" maxlength="120" />
			</div>
			<p></p>
			<g:actionSubmit action="postActivity" value="Submit" />
			<g:actionSubmit action="cancelActivity" value="Cancel" />
		</g:form>
	</body>
</html>
