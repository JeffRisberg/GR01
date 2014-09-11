<html>
    <head>
        <title>Upload Image</title>
        <meta name="layout" content="main"/>
    </head>
    
    <body>
        <g:if test="${flash.message}">
           <div class="flash">
              ${flash.message}
           </div>
        </g:if>
        <g:uploadForm action="upload">
            User Id:
            <g:select name="userId" from="${com.incra.User.list()}"
                      optionKey="id" optionValue="userId" />
            <p/>
            Photo: <input name="photo" type="file" />
            <g:submitButton name="upload" value="Upload"/>
        </g:uploadForm>
    </body>
</html>
