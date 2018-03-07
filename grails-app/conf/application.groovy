

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.wit.iris.users.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.wit.iris.users.UserRole'
grails.plugin.springsecurity.authority.className = 'com.wit.iris.users.Role'
grails.plugin.springsecurity.securityConfigType = 'InterceptUrlMap'
grails.plugin.springsecurity.interceptUrlMap = [
		[pattern: '/',               access: ['permitAll']],
		[pattern: '/error',          access: ['permitAll']],
		[pattern: '/index',          access: ['permitAll']],
		[pattern: '/index.gsp',      access: ['permitAll']],
		[pattern: '/shutdown',       access: ['permitAll']],
		[pattern: '/assets/**',      access: ['permitAll']],
		[pattern: '/**/js/**',       access: ['permitAll']],
		[pattern: '/**/css/**',      access: ['permitAll']],
		[pattern: '/**/images/**',   access: ['permitAll']],
		[pattern: '/**/favicon.ico', access: ['permitAll']],
		[pattern: '/login/**',               access: ['permitAll']],
		[pattern: '/api/login',          access: ['permitAll']],
		[pattern: '/api/logout',        access: ['isFullyAuthenticated()']],
		[pattern: '/rest/**',    access: ['isFullyAuthenticated()']],
		[pattern: '/schema/route/**',    access: ['isFullyAuthenticated()']],
		[pattern: '/schema/getAgentUrl',    access: ['isFullyAuthenticated()']],
		[pattern: '/**',             access: ['isFullyAuthenticated()']]

]



grails.plugin.springsecurity.filterChain.chainMap = [

		[pattern: '/api/**', filters:'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'],

		[pattern: '/schema/route/**', filters:'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'],
		[pattern: '/schema/getAgentUrl', filters:'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'],

		[pattern: '/**', filters:'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']

]



grails.plugin.springsecurity.rest.logout.endpointUrl = '/api/logout'

grails.plugin.springsecurity.rest.token.validation.useBearerToken = false

grails.plugin.springsecurity.rest.token.validation.headerName = 'X-Auth-Token'

grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = 'com.wit.iris.auth.AuthenticationToken'

grails.plugin.springsecurity.rest.token.storage.gorm.tokenValuePropertyName = 'tokenValue'

grails.plugin.springsecurity.rest.token.storage.gorm.usernamePropertyName = 'username'
