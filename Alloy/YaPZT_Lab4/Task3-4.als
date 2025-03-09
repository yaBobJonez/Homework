sig Professor {}
sig Student {}
sig Course {
	professor: one Professor,
	students: some Student
}

assert NoCourseWithoutStudents {
	no c: Course | no c.students
}
pred AllCoursesAssignedToOneProfessor {
	some p: Professor | all c: Course | c.professor = p
}

check NoCourseWithoutStudents for 5
run AllCoursesAssignedToOneProfessor for 5
