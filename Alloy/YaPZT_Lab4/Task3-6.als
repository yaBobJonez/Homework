sig Employee {}
sig Manager extends Employee {
	employees: some Employee
}

fact NoCyclicManagement {
	all m: Manager | m not in m.^employees
}

pred AllEmployeesAreManagers {
	all e: Employee | e in Manager
}
pred NoTopManager {
    no m: Manager | m not in m.^employees
}

run AllEmployeesAreManagers for 5
run NoTopManager for 5
