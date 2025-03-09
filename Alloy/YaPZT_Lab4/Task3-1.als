sig User {
	friends: set User // Користувач може мати друзів
}

fact TwoWayFriendship { // Дружба є взаємною
	all u1, u2: User | u1 in u2.friends => u2 in u1.friends
}
fact NoFriendingSelf { // Користувач не може дружити із собою
	all u: User | u not in u.friends
}

pred HasUserWithoutFriends { // Чи існує випадок користувача без друзів?
    some u: User | no u.friends
}
pred HasIsolatedGroups { // Чи можуть існувати ізольовані групи друзів?
	some disj u1, u2: User {
		some u1.friends
		some u2.friends
		u2 not in u1.^friends
	}
}

run HasUserWithoutFriends for 5
run HasIsolatedGroups for 5
