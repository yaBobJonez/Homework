sig Resource {
	accessibleBy: some Role
}
sig Role {}
sig User {
	roles: set Role
}

pred hasAccess[u: User, r: Resource] {
	some (u.roles & r.accessibleBy)
}

assert CantAccessResourceWithoutRole {
	all u: User, res: Resource | no (u.roles & res.accessibleBy) => not hasAccess[u, res]
}
pred ResourceNotAvailableToAnyone {
	some res: Resource | all u: User | not hasAccess[u, res]
}

check CantAccessResourceWithoutRole for 5
run ResourceNotAvailableToAnyone for 5
