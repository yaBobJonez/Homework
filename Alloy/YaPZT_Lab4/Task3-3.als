sig Intersection {
	roads: some Intersection
}

fact RoadDoesntConnectToItself {
	all i: Intersection | i not in i.roads
}

assert NoIsolatedIntersections {
	no i: Intersection | no i.roads
}
pred RouteWithoutLoops {
	some i: Intersection | i not in i.^roads
}

check NoIsolatedIntersections for 5
run RouteWithoutLoops for 5
