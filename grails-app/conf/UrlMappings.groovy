class UrlMappings {
	
	static mappings = {
		"/activities"(controller: "activityRest") {
			action = [GET: "list", POST: "save"]
		}
		
		"/activity/$id"(controller: "activityRest") {
			action = [GET: "show", PUT: "update", DELETE: "delete"]
			
			constraints {
				id(matches: /\d+/)
			}
		}
		
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		
		"/"(view:"/homepage/index")
		"500"(view:'/error')
	}
}
