# AngularJS 1 Style Guide
## Назначение:
Этот краткий style-guide предназначен для внутреннего использования на проекте [edu.xored.tracker-web](https://github.com/edu-xored/tracker-web) и представляет из себя набор примеров и практик оформления JavaScript кода, использующего фреймворк **AngularJS**.

## Основа:
Основой данному руководству служат style-guide от [Google](https://google.github.io/styleguide/javascriptguide.xml) и [john_papa](https://github.com/johnpapa/angular-styleguide/blob/master/a1/README.md).
<!-- добавить остальные источники  -->

# Содержание
1. [Именование файлов](#_4)
2. [Отступы и табуляция](#_5)
3. [Анонимные функции против именованных](#_6)
4. [Объявления (Setters)](#setters)
5. [Getters](#getters)
6. [Разнесение компонентов по файлам](#_7)
7. [Файловая структура](#_8)
8. [сontrollerAs](#ontrolleras)
	* [controllerAs View](#controlleras-view-syntax)
	* [controllerAs Controller](#controlleras-controller-syntax)
9. [Расположение переменных и функций в теле объектов](#_9)
10. [Вынесение логики из контроллеров](#_10)

## Именование файлов

Пример именования файлов и сущностей:

* `functionsNamesLikeThis`;
* `variableNamesLikeThis`;
* `moduleNamesLikeThis`;
* `subModuleOf.parentModule`;
* `directiveNamesLikeThis`;
* `ControllerNamesLikeThis`;
* `filterNamesLikeThis`;
* `ServiceUsedAsConstructorNamesLikeThis`;
* `serviceNamesLikeThis`;
* `methodNamesLikeThis`;
* `CONSTANT_VALUES_LIKE_THIS`;
* `file-names-like-this.type.js`.

## Отступы и табуляция
* В качестве отступов используется символ табуляции, по длине равный четырём символам пробела.
* Каждый компонент (контроллер, сервис, фабрика, и т.д., кроме модуля) выделяется символами точки, переноса на следующую строку и табуляцией.

	**Важно**: всегда стоит убеждаться что используемый Вами редактор использует именно символ табуляции, а не заменяет его несколькими пробелами.

```javascript
/* стоит избегать */
angular.module('app').controller('SomeController', SomeController);

SomeController() {
	//some code here
}
```
```javascript
/* стоит избегать */
angular
	.module('app')
	.controller('SomeController', SomeController);

SomeController() {
	//some code here
}
```

```javascript
/* рекомендуется использовать */
angular.module('app')
	.controller('SomeController', SomeController);

SomeController() {
	//some code here
}
```

* В случае, если модуль имеет более одной зависимости, их стоит выносить в отдельные строки.

```javascript
/* стоит избегать */
angular.module('app', ['ngRoute', 'ngAnimate', 'app.shared', 'add.dashboard']);
```

```javascript
/* рекомендуется использовать */
angular.module('app', [
	'ngRoute',
	'ngAnimate'
]);
```

* При форматировании кода внутри тела функций, сервисов и т.д. **обязательно** использовать пробелы, а не табуляцию.

```javascript
/* рекомендуется использовать */
$http({
	method    :    'POST',
	url       :    '/api/issues/' + $routeParams.hash + '/comments',
	data      :    viewModel.comment
});
```

* Кроме того, не стоит забывать про использование пробелов вокруг операторов. Каждое выражение заканчивается символом точки с запятой `;`:

```javascript
/* стоит избегать */
var x=y+z;
var values=["Vault-101","Vault-69","Vault-13"];
```

```javascript
/* рекомендуется использовать */
var x = y + z;
var values = ["Vault-101", "Vault-69", "Vault-13"];
```

* Тело функции так же следует выделять табуляцией оговоренной длины (4 пробела).

```javascript
/* рекомендуется использовать */
function toCelsius(fahrenheit) {
	return (5 / 9) * (fahrenheit - 32);
}
```

**Важно отметить что**:
* Открывающая скобка располагается на той же строке, что и имя объекта;
* Открывающая скобка отделяется символом пробела;
* Закрывающая скобка `}` располагается на новой строке, без каких-либо символов (кроме отступов) перед ней;
* Выражения и объявления объектов всегда заканчивается символом `;`.
Стоит отметить, что закрывающая скобка для выражения подобного рода (объявленя функций, условные операторы, циклы и т.д.) не выделяется символом `;`.

С учётом сказанного, приведём пример написания циклов и условных операторов:

```javascript
/* рекомендуется использовать */
for (i = 0; i < 5; i++) {
	x = x + i;
}
```

```javascript
/* рекомендуется использовать */
if (time < 20) {
	greeting = "Good day";
} else {
	greeting = "Good evening";
}
```

## Анонимные функции против именованных
* Всегда используйте именованные функции вместо анонимных.

	Почему?: Полученный код будет более читаемым, а также снизится количество вложенных вызовов функций. Это упростит его отладку и поддержку.

```javascript
/* стоит избегать */
angular.module('app')
	.controller('DashboardController', function() {
		//some code here
	 });
```

```javascript
/* рекомендуется использовать */
angular.module('app')
	.controller('DashboardController', DashboardController);

function DashboardController() {
	//some code here
}
```

**Заметка**: далее в руководстве для описания контроллеров и сервисов будет приводиться не полный код соответствующих файлов, а лишь именованная функция и её тело, реализующая их.

## Объявления (Setters)

* Модули и компоненты следует определять без использования переменных.

```javascript
/* стоит избегать */
var app = angular.module('app', []);
```

```javascript
/* рекомендуется использовать */
angular.module('app', []);
```

## Getters

* Во время использования модулей стоит избегать использования переменных, отдавая предпочтение getter'ам.

	Почему?: Повышает читаемость кода.

```javascript
/* стоит избегать */
var app = angular.module('app');
app.controller('SomeController', SomeController);

function SomeController() {
//some code here
}
```

```javascript
 /* рекомендуется использовать */
angular.module('app')
	.controller('SomeController', SomeController);

function SomeController() {
	//some code here
}
```

## Разнесение компонентов по файлам

В прошлой секции можно было заметить что каждому компоненту соответствует отдельный файл, что и является рекомендуемой практикой для данного проекта.

* В одном файле не следует определять больше одного компонента.

	Почему?: Один компонент на файл упрощает чтение, процесс понимания его работы, структуры.

```javascript
/* стоит избегать */
angular.module('app', ['ngRoute'])
	.controller('SomeController', SomeController)
	.factory('someFactory', someFactory);

function SomeController() {
	//some code here
}

function someFactory() {
	//some code here
}
```
Те же компоненты, разделённые на несколько файлов:

```javascript
/* рекомендуется использовать */

// app.module.js
angular.module('app', ['ngRoute']);
```

```javascript
/* рекомендуется использовать */

// some.controller.js
angular.module('app')
	.controller('SomeController', SomeController);

function SomeController() {
	//some code here
}
```

```javascript
/* рекомендуется использовать */

// some.factory.js
angular.module('app')
	.factory('someFactory', someFactory);

function someFactory() {
	//some code here
}
```

Важно отметить что для объявления каждого нового компонента, выделяемого в отдельный файл (кроме модулей) используется существующий модуль.

## Файловая структура
В данном проекте компоненты размещаются в директории, соответствующей их назначению, а именуются согласно функциональности.

Например:

```
├── app.module.js
├── app.routes.js
├── assets
│   ├── css
│   └── img
├── controllers
│   ├── about.controller.js
│   └── home.controller.js
├── directives
│   ├── about.directive.js
│   └── home.directive.js
├── index.html
├── services
│   ├── about.service.js
│   └── home.service.js
└── views
	├── about.html
	└── home.html
```

## сontrollerAs
###controllerAs View Syntax
* Используйте синтаксис `controllerAs` вместо классического синтаксиса `$scope`.

	Почему?: Проще воспринимать, особенно в рамках контекста. Сравните: `customer.name` и `name`;

	Почему?: Помогает избежать вызова `$parent` в представлениях вложенных контроллеров.

```html
<!-- стоит избегать -->
<div ng-controller="CustomerController">
	{{name}}
</div>
```

```html
<!-- рекомендуется использовать -->
<div ng-controller="CustomerController as customer">
	{{customer.name}}
</div>
```

###controllerAs Controller Syntax
* Используйте синтаксис `controllerAs` вместо классического синтаксиса `$scope`;
* Синтаксис `controllerAs` использует ключевое слово `this` вместо `$scope`.
* Зафиксируйте переменную для ключевого слова `this` при использовании синтаксиса `controllerAs`. Это поможет избежать ошибок связанных с использованием `this` в несоответствующем контексте.

```javascript
/* стоит избегать */
function CustomerController($scope) {
	$scope.name = {};
	$scope.sendMessage = function() {
		//some code here
	};
}
```

```javascript
/* стоит избегать */
function CustomerController($scope) {
	this.name = {};
	this.sendMessage = function() {
		//some code here
	};
}
```

```javascript
/* рекомендуется использовать */
function CustomerController() {
	var viewModel = this;
	viewModel.name = {};
	viewModel.sendMessage = function() {
		//some code here
	};
}
```

## Расположение переменных и функций в теле объектов
* Располагайте связываемые единицы кода в самом начале контроллера, в алфавитном порядке.

	Почему?: Упрощает чтение и помогает мгновенно узнать содержимое контроллера.

	**Важно**: Стоит учитывать что хорошей практикой является вынесение логики из контроллеров в сервисы. При таком подходе вся реализация размещена внутри сервиса, а в контроллере происходит лишь вызов метода.

```javascript
/* стоит избегать */
function SessionsController() {
	var viewModel = this;

	viewModel.gotoSession = function() {
	  //some code here
	};
	viewModel.refresh = function() {
	  //some code here
	};
	viewModel.search = function() {
	  //some code here
	};
	viewModel.sessions = [];
	viewModel.title = 'Sessions';
}
```

```javascript
/* рекомендуется исопльзовать (есть недостатки, см. следующий пункт) */
function SessionsController() {
	var viewModel = this;

	viewModel.gotoSession = gotoSession;
	viewModel.refresh = refresh;
	viewModel.search = search;
	viewModel.sessions = [];
	viewModel.title = 'Sessions';

	function gotoSession() {
	  //some code here
	}

	function refresh() {
	  //some code here
	}

	function search() {
	  //some code here
	}
}
```

## Вынесение логики из контроллеров
* Выносите логику из контроллеров, делегируя её сервисам и фабрикам.

	Почему?: Одну и ту же логику могут использовать несколько контроллеров, следовательно, вынеся её в сервис мы избежим копипасты кода.

	Почему?: Изоляция кода упростит модульное тестирование.

	Почему?: Освободит контроллер от лишних зависимостей и скроет детали реализации.

```javascript
/* старайтесь избегать */
function OrderController($http, $q, config, userInfo) {
	var viewModel = this;
	viewModel.checkCredit = checkCredit;
	viewModel.isCreditOk;
	viewModel.total = 0;

	function checkCredit() {
		var settings = {};
		// Get the credit service base URL from config
		// Set credit service required headers
		// Prepare URL query string or data object with request data
		// Add user-identifying info so service gets the right credit limit for this user.
		// Use JSONP for this browser if it doesn't support CORS
		return $http.get(settings)
			.then(function(data) {
			 // Unpack JSON data in the response object
			   // to find maxRemainingAmount
			   viewModel.isCreditOk = viewModel.total <= maxRemainingAmount
			})
			.catch(function(error) {
			   // Interpret error
			});
	};
}
```

```javascript
/* рекомендуется использовать */
function OrderController(creditService) {
	var viewModel = this;
	viewModel.checkCredit = checkCredit;
	viewModel.isCreditOk;
	viewModel.total = 0;

	function checkCredit() {
	   return creditService.isOrderTotalOk(viewModel.total)
		  .then(function(isOk) { viewModel.isCreditOk = isOk; })
		  .catch(showError);
	};
}
```
