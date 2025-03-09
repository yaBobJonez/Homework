sig Person {
	name: one String,
	cars: set Car
}
sig Car {
	model: one String
}
fact {
	all p: Person | some p.cars and p.name != " "
	all c: Car | c.model != " "
}
fact createEntities {
	#Person > 0
	#Car > 0
}
pred drives(p: Person, c: Car) {
	c in p.cars
}
assert validCarAssignment {
	all c: Car | c.model != " "
}
check validCarAssignment
run {
	some p: Person, c: Car | drives[p, c]
} for 3 but 5 Car
