<html>
    <head>
        <title>Register New User</title>
        <meta name="layout" content="main"/>
        <style>
            dd {
                text-align: left;
                margin-left: 80px;
                margin-top: 5px;
            }
        </style>
    </head>
    
    <body>
        <h1>Register New User</h1>

        <g:hasErrors>
            <div class="errors">
               <g:renderErrors bean="${user}" as="list" />
            </div>
        </g:hasErrors>

        <g:form action="register">
            <dl>
                <dt>User Id</dt>
                <dd><g:textField name="userId" value="${user?.userId}"/></dd>
                <dt>Password</dt>
                <dd><g:passwordField name="password" value="${user?.password}"/></dd>
                <dt>(repeat)</dt>
                <dd><g:passwordField name="passwordRepeat" value="${user?.passwordRepeat}"/></dd>
                <dt>Full Name</dt>
                <dd><g:textField name="fullName" value="${user?.fullName}"/></dd>
                <dt>Email</dt>
                <dd><g:textField name="email" value="${user?.email}"/></dd>
                <dt>Country</dt>
                <dd><g:textField name="country" value="${user?.country}"/></dd>
                <dt>Time Zone</dt>
                <dd><g:textField name="timeZone" value="${user?.timeZone}"/></dd>
                
                <dt><g:submitButton name="register" value="Register"/></dt>
            </dl>
        </g:form>
    </body>
</html>
