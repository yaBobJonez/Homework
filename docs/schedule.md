---
layout: default
title: Расписание
nav_order: 2
description: "Информационный сайт 10-А класса."
has_toc: false
---

## 10-A, на Этой Неделе:

**Вторник:** 
{: .text-blue-100 }
<div id="Tue">LdErr</div>
{: .text-yellow-300 }

**Пятница:** 
{: .text-blue-100 }
<div id="Fri">LdErr</div>
{: .text-yellow-300 }

<script>
	function getMonday(d) {
		var diff = d.getDate() - d.getDay() + (d.getDay() === 0 ? -6 : 1);
		return new Date(d.setDate(diff)).getDate();
	}
	var mon = getMonday(new Date());
	var tue = mon % 2 == 0? "Всемирная История" : "Захист";
	var fri = mon % 2 == 0? "География" : "Химия";
	document.getElementById("Tue").innerHTML = tue;
	document.getElementById("Fri").innerHTML = fri;
</script>

