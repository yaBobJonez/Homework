sig Node {
	connections: some Node
}

fact NoConnectionsToSelf {
	no c: Node | c in c.connections
}

assert NoIsolatedComputer {
	no n1: Node | all n2: Node | n2 != n1 => {
		n2 not in n1.^connections
		n1 not in n2.^connections
	}
}
pred AcyclicNetwork {
	all n: Node | n not in n.^connections
}

check NoIsolatedComputer for 5
run AcyclicNetwork for 5
