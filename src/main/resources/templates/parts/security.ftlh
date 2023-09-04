<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    person = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = person.getUsername()
    isAdmin = person.isAdmin()
    currentUserId = person.getId()
    isUser = true
    >
<#else>
    <#assign
    name = "guest"
    isAdmin = false
    currentUserId = -1
    isUser = false
    >
</#if>