// Статус книги (перелічуваний тип)
enum BookStatus { available, borrowed }

// Сигнатури
sig User {} // Підпис для користувача
sig Admin {} // Підпис для адміністратора
sig Book { // Підпис для книги
	takenBy: lone User, // Зв'язок книги з користувачем лінійний - 1 до 1
	status: one BookStatus, // Статус книги - доступна або позичена
	returnDate: lone Int // Дата повернення книги (через днів)
}

// Обмеження
fact BorrowLimit {
	all u: User | #u.~takenBy <= 3 // Користувач не може взяти більше 3 книг
}
fact NoDoubleBorrow {
	all b: Book | lone b.takenBy // Книга не може бути видана більше ніж одному користувачу
}
fact BookStatusValid {
	// Якщо статус «зайнятий», то хтось позичив книжку
	all b: Book | one b.takenBy <=> b.status = borrowed
}
fact NoOverdue {
	// Якщо статус позичений, то є дата повернення і вона не пізніше 14 днів,
	// інакше дати повернення не має бути
	all b: Book | b.status = borrowed
		implies b.returnDate >= 0 and b.returnDate <= 14
 		else no b.returnDate
}

// Предикати
pred borrowBook[u: User, b: Book] {
	b.status = available // Книга доступна
	#u.~takenBy < 3 // Користувач має менше 3 книг
}
pred changeBookStatus[a: Admin, b: Book, newStatus: BookStatus] {
    b.status = newStatus // Адміністратор може змінювати статус книги
}
// Тестування
run borrowBook for 5
run changeBookStatus for 3

// Перевірка тверджень:
assert NoOverBorrow {
	all u: User | #u.~takenBy <= 3
}
assert BorrowedBooksAssigned {
	all b: Book | b.status = available => no b.takenBy
}
assert ReturnDatesValid {
	all b: Book | not (b.returnDate < 0 or b.returnDate > 14)
}
check ReturnDatesValid for 5 // Перевірка обмеження
