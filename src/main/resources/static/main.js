(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "./src/$$_lazy_route_resource lazy recursive":
/*!**********************************************************!*\
  !*** ./src/$$_lazy_route_resource lazy namespace object ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error("Cannot find module '" + req + "'");
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/app/app-routing/app-routing.module.ts":
/*!***************************************************!*\
  !*** ./src/app/app-routing/app-routing.module.ts ***!
  \***************************************************/
/*! exports provided: AppRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppRoutingModule", function() { return AppRoutingModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _ui_layout_main_main_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../ui/layout/main/main.component */ "./src/app/ui/layout/main/main.component.ts");
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
/* harmony import */ var _ui_layout_unauthorize_unauthorize_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../ui/layout/unauthorize/unauthorize.component */ "./src/app/ui/layout/unauthorize/unauthorize.component.ts");
/* harmony import */ var _service_auth_guard_service__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../service/auth-guard.service */ "./src/app/service/auth-guard.service.ts");








var routes = [
    {
        path: '',
        component: _ui_layout_main_main_component__WEBPACK_IMPORTED_MODULE_4__["MainComponent"],
        canActivate: [_service_auth_guard_service__WEBPACK_IMPORTED_MODULE_7__["AuthGuardService"]]
    },
    {
        path: '401',
        component: _ui_layout_unauthorize_unauthorize_component__WEBPACK_IMPORTED_MODULE_6__["UnauthorizeComponent"]
    } /*,
    { path: '**', redirectTo: '401' }*/
];
var AppRoutingModule = /** @class */ (function () {
    function AppRoutingModule() {
    }
    AppRoutingModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            declarations: [],
            imports: [
                _angular_router__WEBPACK_IMPORTED_MODULE_3__["RouterModule"].forRoot(routes),
                _angular_platform_browser__WEBPACK_IMPORTED_MODULE_5__["BrowserModule"],
                _angular_common__WEBPACK_IMPORTED_MODULE_2__["CommonModule"]
            ],
            exports: [
                _angular_router__WEBPACK_IMPORTED_MODULE_3__["RouterModule"]
            ]
        })
    ], AppRoutingModule);
    return AppRoutingModule;
}());



/***/ }),

/***/ "./src/app/app.component.css":
/*!***********************************!*\
  !*** ./src/app/app.component.css ***!
  \***********************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2FwcC5jb21wb25lbnQuY3NzIn0= */"

/***/ }),

/***/ "./src/app/app.component.html":
/*!************************************!*\
  !*** ./src/app/app.component.html ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<!--<router-outlet></router-outlet>-->\n<app-layout>\n  <h1>{{title}}</h1>\n</app-layout>\n"

/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");


var AppComponent = /** @class */ (function () {
    function AppComponent() {
        this.title = 'arnote';
    }
    AppComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-root',
            template: __webpack_require__(/*! ./app.component.html */ "./src/app/app.component.html"),
            providers: [],
            styles: [__webpack_require__(/*! ./app.component.css */ "./src/app/app.component.css")]
        })
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var _ui_ui_module__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./ui/ui.module */ "./src/app/ui/ui.module.ts");
/* harmony import */ var _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/platform-browser/animations */ "./node_modules/@angular/platform-browser/fesm5/animations.js");
/* harmony import */ var angular_font_awesome__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! angular-font-awesome */ "./node_modules/angular-font-awesome/dist/angular-font-awesome.es5.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _service_auth_guard_service__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./service/auth-guard.service */ "./src/app/service/auth-guard.service.ts");
/* harmony import */ var _service_auth_service__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./service/auth.service */ "./src/app/service/auth.service.ts");










var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_2__["NgModule"])({
            declarations: [
                _app_component__WEBPACK_IMPORTED_MODULE_3__["AppComponent"]
            ],
            imports: [
                _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__["BrowserModule"],
                _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_5__["BrowserAnimationsModule"],
                angular_font_awesome__WEBPACK_IMPORTED_MODULE_6__["AngularFontAwesomeModule"],
                _angular_common_http__WEBPACK_IMPORTED_MODULE_7__["HttpClientModule"],
                _ui_ui_module__WEBPACK_IMPORTED_MODULE_4__["UiModule"]
            ],
            providers: [_service_auth_guard_service__WEBPACK_IMPORTED_MODULE_8__["AuthGuardService"], _service_auth_service__WEBPACK_IMPORTED_MODULE_9__["AuthService"]],
            bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_3__["AppComponent"]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/dto/ChangeWishMonthOrderDto.ts":
/*!************************************************!*\
  !*** ./src/app/dto/ChangeWishMonthOrderDto.ts ***!
  \************************************************/
/*! exports provided: ChangeWishMonthOrderDto */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ChangeWishMonthOrderDto", function() { return ChangeWishMonthOrderDto; });
var ChangeWishMonthOrderDto = /** @class */ (function () {
    function ChangeWishMonthOrderDto(id) {
        this.id = id;
    }
    ChangeWishMonthOrderDto.prototype.setStep = function (step) {
        this.step = step;
    };
    ChangeWishMonthOrderDto.prototype.setMonth = function (month) {
        this.month = month;
    };
    return ChangeWishMonthOrderDto;
}());



/***/ }),

/***/ "./src/app/dto/salary.ts":
/*!*******************************!*\
  !*** ./src/app/dto/salary.ts ***!
  \*******************************/
/*! exports provided: Salary */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "Salary", function() { return Salary; });
var Salary = /** @class */ (function () {
    function Salary(fullsalary, residualSalary) {
        this.fullsalary = fullsalary;
        this.residualSalary = residualSalary;
    }
    return Salary;
}());



/***/ }),

/***/ "./src/app/dto/user.ts":
/*!*****************************!*\
  !*** ./src/app/dto/user.ts ***!
  \*****************************/
/*! exports provided: User */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "User", function() { return User; });
var User = /** @class */ (function () {
    function User() {
    }
    return User;
}());



/***/ }),

/***/ "./src/app/dto/wish.ts":
/*!*****************************!*\
  !*** ./src/app/dto/wish.ts ***!
  \*****************************/
/*! exports provided: Wish */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "Wish", function() { return Wish; });
var Wish = /** @class */ (function () {
    function Wish(id, wish, price, priority, archive, description, url, realized) {
        this.id = id;
        this.wish = wish;
        this.price = price;
        this.priority = priority;
        this.archive = archive;
        this.description = description;
        this.url = url;
        this.realized = realized;
    }
    return Wish;
}());



/***/ }),

/***/ "./src/app/service/auth-guard.service.ts":
/*!***********************************************!*\
  !*** ./src/app/service/auth-guard.service.ts ***!
  \***********************************************/
/*! exports provided: AuthGuardService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthGuardService", function() { return AuthGuardService; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _auth_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./auth.service */ "./src/app/service/auth.service.ts");




var AuthGuardService = /** @class */ (function () {
    function AuthGuardService(auth, router) {
        this.auth = auth;
        this.router = router;
    }
    AuthGuardService.prototype.canActivate = function () {
        if (!this.auth.isAuthenticated()) {
            console.log('is auth - ' + !this.auth.isAuthenticated());
            this.router.navigate(['401']);
            return false;
        }
        return true;
    };
    AuthGuardService = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_auth_service__WEBPACK_IMPORTED_MODULE_3__["AuthService"], _angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"]])
    ], AuthGuardService);
    return AuthGuardService;
}());



/***/ }),

/***/ "./src/app/service/auth.service.ts":
/*!*****************************************!*\
  !*** ./src/app/service/auth.service.ts ***!
  \*****************************************/
/*! exports provided: AuthService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthService", function() { return AuthService; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @auth0/angular-jwt */ "./node_modules/@auth0/angular-jwt/index.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../environments/environment */ "./src/environments/environment.ts");





var helper = new _auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_3__["JwtHelperService"]();
var myRawToken = localStorage.getItem('token');
var decodedToken = helper.decodeToken(myRawToken);
var isExpired = helper.isTokenExpired(myRawToken);
var AuthService = /** @class */ (function () {
    function AuthService(http, jwtHelper) {
        this.http = http;
        this.jwtHelper = jwtHelper;
        this.SERVER_URL = _environments_environment__WEBPACK_IMPORTED_MODULE_4__["environment"].serverUrl;
        this._loginURL = 'http://localhost:8080/login?';
        this.loginURL = this.SERVER_URL + '/login?';
    }
    AuthService.prototype.login = function (loginPayload) {
        console.log('loginPayload ->' + loginPayload);
        var headers = {
            'Content-type': 'application/x-www-form-urlencoded'
        };
        return this.http.get(this.loginURL + loginPayload, { observe: 'response' });
    };
    AuthService.prototype.register = function (newUser, url) {
        var httpOptions = {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                'Content-Type': 'application/json'
            })
        };
        return this.http.post(url, newUser, httpOptions);
    };
    AuthService.prototype.isAuthenticated = function () {
        return !isExpired;
    };
    AuthService.prototype.refreshToken = function () {
        myRawToken = localStorage.getItem('token');
        isExpired = helper.isTokenExpired(myRawToken);
        console.log('TOKEN EXPIRE - ' + isExpired);
    };
    AuthService = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"], _auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_3__["JwtHelperService"]])
    ], AuthService);
    return AuthService;
}());



/***/ }),

/***/ "./src/app/service/basicauthhtppInterceptorservice.ts":
/*!************************************************************!*\
  !*** ./src/app/service/basicauthhtppInterceptorservice.ts ***!
  \************************************************************/
/*! exports provided: BasicAuthHtppInterceptorService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "BasicAuthHtppInterceptorService", function() { return BasicAuthHtppInterceptorService; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");


var BasicAuthHtppInterceptorService = /** @class */ (function () {
    function BasicAuthHtppInterceptorService() {
    }
    BasicAuthHtppInterceptorService.prototype.intercept = function (req, next) {
        var idToken = localStorage.getItem('token');
        // console.log('token', idToken);
        if (idToken) {
            var cloned = req.clone({
                headers: req.headers.set('Authorization', idToken)
            });
            return next.handle(cloned);
        }
        else {
            return next.handle(req);
        }
    };
    BasicAuthHtppInterceptorService = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [])
    ], BasicAuthHtppInterceptorService);
    return BasicAuthHtppInterceptorService;
}());



/***/ }),

/***/ "./src/app/service/common.service.ts":
/*!*******************************************!*\
  !*** ./src/app/service/common.service.ts ***!
  \*******************************************/
/*! exports provided: CommonService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "CommonService", function() { return CommonService; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm5/index.js");
/* harmony import */ var crypto_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! crypto-js */ "./node_modules/crypto-js/index.js");
/* harmony import */ var crypto_js__WEBPACK_IMPORTED_MODULE_3___default = /*#__PURE__*/__webpack_require__.n(crypto_js__WEBPACK_IMPORTED_MODULE_3__);




var CommonService = /** @class */ (function () {
    function CommonService() {
        this.errorSubsciber = new rxjs__WEBPACK_IMPORTED_MODULE_2__["BehaviorSubject"](this.error);
        this.error$ = this.errorSubsciber.asObservable();
    }
    CommonService.prototype.pushError = function (error) {
        console.log('Incoming Error: ' + error.messageType);
        this.errorSubsciber.next(error);
    };
    CommonService.prototype.encrypt = function (keys, value) {
        var key = crypto_js__WEBPACK_IMPORTED_MODULE_3__["enc"].Utf8.parse(keys);
        var iv = crypto_js__WEBPACK_IMPORTED_MODULE_3__["enc"].Utf8.parse(keys);
        var encrypted = crypto_js__WEBPACK_IMPORTED_MODULE_3__["AES"].encrypt(crypto_js__WEBPACK_IMPORTED_MODULE_3__["enc"].Utf8.parse(value.toString()), key, {
            keySize: 128 / 8,
            iv: iv,
            mode: crypto_js__WEBPACK_IMPORTED_MODULE_3__["mode"].CBC,
            padding: crypto_js__WEBPACK_IMPORTED_MODULE_3__["pad"].Pkcs7
        });
        return encrypted.toString();
    };
    CommonService.prototype.convertText = function (conversion, what, key) {
        if (conversion === 'encrypt') {
            return crypto_js__WEBPACK_IMPORTED_MODULE_3__["AES"].encrypt(what.trim(), key.trim()).toString();
        }
        else {
            return crypto_js__WEBPACK_IMPORTED_MODULE_3__["AES"].decrypt(what.trim(), key.trim()).toString(crypto_js__WEBPACK_IMPORTED_MODULE_3__["enc"].Utf8);
        }
    };
    // The get method is use for decrypt the value.
    CommonService.prototype.decrypt = function (keys, value) {
        var key = crypto_js__WEBPACK_IMPORTED_MODULE_3__["enc"].Utf8.parse(keys);
        console.log('key - ' + key);
        var iv = crypto_js__WEBPACK_IMPORTED_MODULE_3__["enc"].Utf8.parse(keys);
        console.log('iv - ' + iv);
        var decrypted = crypto_js__WEBPACK_IMPORTED_MODULE_3__["AES"].decrypt(value, key, {
            keySize: 128 / 8,
            iv: iv,
            mode: crypto_js__WEBPACK_IMPORTED_MODULE_3__["mode"].CBC,
            padding: crypto_js__WEBPACK_IMPORTED_MODULE_3__["pad"].Pkcs7
        });
        console.log('decr - ' + decrypted);
        return decrypted.toString(crypto_js__WEBPACK_IMPORTED_MODULE_3__["enc"].Utf8);
    };
    CommonService = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])({
            providedIn: 'root'
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [])
    ], CommonService);
    return CommonService;
}());



/***/ }),

/***/ "./src/app/service/http.service.ts":
/*!*****************************************!*\
  !*** ./src/app/service/http.service.ts ***!
  \*****************************************/
/*! exports provided: HttpService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "HttpService", function() { return HttpService; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../environments/environment */ "./src/environments/environment.ts");




var HttpService = /** @class */ (function () {
    function HttpService(http) {
        this.http = http;
        this.SERVER_URL = _environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].serverUrl;
        this.loginURL = this.SERVER_URL + '/login?';
        this.isCryptoUserUrl = this.SERVER_URL + '/user/current';
    }
    HttpService.prototype.getData = function (url) {
        return this.http.get(url);
    };
    HttpService.prototype.toggleUserViewMode = function (url) {
        return this.http.post(url, null);
    };
    HttpService.prototype.changeMonthOrder = function (wish, url) {
        var httpOptions = {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                'Content-Type': 'application/json'
            })
        };
        return this.http.post(url, wish, httpOptions);
    };
    HttpService.prototype.sendData = function (wish, url) {
        var httpOptions = {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                'Content-Type': 'application/json'
            })
        };
        return this.http.post(url, wish, httpOptions);
    };
    HttpService.prototype.sendFile = function (formData, url) {
        return this.http.post(url, formData);
    };
    HttpService.prototype.sendSalary = function (salary, url) {
        var httpOptions = {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                'Content-Type': 'application/json'
            })
        };
        return this.http.post(url, salary, httpOptions);
    };
    HttpService.prototype.updateWish = function (wish, url) {
        var httpOptions = {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                'Content-Type': 'application/json'
            })
        };
        return this.http.put(url, wish, httpOptions);
    };
    HttpService.prototype.deleteWish = function (id, url) {
        var httpOptions = {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                'Content-Type': 'application/json'
            })
        };
        return this.http.delete(url + '/' + id, httpOptions);
    };
    HttpService.prototype.login = function (loginPayload) {
        console.log('loginPayload ->' + loginPayload);
        var headers = {
            'Content-type': 'application/x-www-form-urlencoded'
        };
        return this.http.get(this.loginURL + loginPayload, { observe: 'response' });
    };
    HttpService.prototype.isCryptoUser = function () {
        return this.http.get(this.isCryptoUserUrl);
    };
    HttpService.prototype.updateUserData = function (user, url) {
        var httpOptions = {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                'Content-Type': 'application/json'
            })
        };
        return this.http.put(url, user, httpOptions);
    };
    HttpService = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"]])
    ], HttpService);
    return HttpService;
}());



/***/ }),

/***/ "./src/app/service/message.code.ts":
/*!*****************************************!*\
  !*** ./src/app/service/message.code.ts ***!
  \*****************************************/
/*! exports provided: MessageCode */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MessageCode", function() { return MessageCode; });
var MessageCode = /** @class */ (function () {
    function MessageCode() {
        // ===================================================
        // ====================  КОДЫ   ОШИБОК ===============
        // ===================================================
        this.WRONG_LOGIN = 'Ошибка аутентификации! Не верные логин и/или пароль!';
        this.REGISTER_ERROR = 'Ошибка регистрации!';
        this.USER_DATA_CHANGE_SOME_ERROR = 'Невозможно изменить данные пользователя. Произошла техническая ошибка!';
        this.USER_DATA_CHANGE_SUCH_USER_EXISTS = 'Данное имя пользователя занято! Выберете другое!';
        this.UNDER_CONSTRACTION = 'Данная функция еще не реализована! Пождите совсем чуть-чуть....';
        this.SESSION_EXPIRED = 'Ваша сессия истекла!';
        // ===================================================
        // ============  КОДЫ  СЕРВИСНЫХ СООБЩЕНИЙ ===========
        // ===================================================
        this.AUTH_LOGOUT = 'LOGOUT';
        this.AUTH_LOGIN_OK = 'LOGIN';
        this.USER_DATA_CHANGE_OK = 'Пользовательские данные успешно изменены!';
        this.REGISTER_OK = 'Пользователь успешно зарегистрирован!';
    }
    return MessageCode;
}());



/***/ }),

/***/ "./src/app/ui/layout/edit/edit.component.css":
/*!***************************************************!*\
  !*** ./src/app/ui/layout/edit/edit.component.css ***!
  \***************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3VpL2xheW91dC9lZGl0L2VkaXQuY29tcG9uZW50LmNzcyJ9 */"

/***/ }),

/***/ "./src/app/ui/layout/edit/edit.component.html":
/*!****************************************************!*\
  !*** ./src/app/ui/layout/edit/edit.component.html ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<p>\n  edit works!\n</p>\n"

/***/ }),

/***/ "./src/app/ui/layout/edit/edit.component.ts":
/*!**************************************************!*\
  !*** ./src/app/ui/layout/edit/edit.component.ts ***!
  \**************************************************/
/*! exports provided: EditComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "EditComponent", function() { return EditComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");


var EditComponent = /** @class */ (function () {
    function EditComponent() {
    }
    EditComponent.prototype.ngOnInit = function () {
    };
    EditComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-edit',
            template: __webpack_require__(/*! ./edit.component.html */ "./src/app/ui/layout/edit/edit.component.html"),
            styles: [__webpack_require__(/*! ./edit.component.css */ "./src/app/ui/layout/edit/edit.component.css")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [])
    ], EditComponent);
    return EditComponent;
}());



/***/ }),

/***/ "./src/app/ui/layout/header/header.component.html":
/*!********************************************************!*\
  !*** ./src/app/ui/layout/header/header.component.html ***!
  \********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<!--<clr-main-container>-->\n<header class=\"header-1\">\n  <div class=\"branding\">\n    <a class=\"nav-link\">\n      <clr-icon shape=\"shield\"></clr-icon>\n      <span class=\"title\">arNote Project</span>\n    </a>\n  </div>\n  <div class=\"header-nav\">\n    <a *ngIf=\"idToken\" class=\"active nav-link nav-icon\" clrVerticalNavLink routerLink=\"../\" routerLinkActive=\"active\">\n      <clr-icon shape=\"home\"></clr-icon>\n    </a>\n  </div>\n  <div class=\"header-actions\">\n    <clr-dropdown class=\"dropdown bottom-right\">\n      <button class=\"nav-icon\" clrDropdownToggle>\n        <clr-icon shape=\"user\"></clr-icon>\n        <clr-icon shape=\"caret down\"></clr-icon>\n      </button>\n\n      <div class=\"dropdown-menu\">\n        <label class=\"dropdown-header\">Меню пользователя</label>\n        <a *ngFor=\"let item of loginDropDownMenu\" (click)=\"loginIconHandler(item)\"  clrDropdownItem>{{item}}</a>\n      </div>\n    </clr-dropdown>\n\n  </div>\n</header>\n\n\n<clr-modal [(clrModalOpen)]=\"isLogin\">\n  <h3 class=\"modal-title\">Войти</h3>\n  <div class=\"modal-body\">\n    <form clrForm [formGroup]=\"loginForm\">\n\n      <!--ПОЛЕ ЛОГИН-->\n\n      <clr-input-container>\n\n        <label #label for=\"login\" class=\"input-label clr-col-12\">Логин</label>\n        <input type=\"text\" clrInput formControlName=\"login\" id=\"login\" name=\"login\" autocomplete=\"off\" required\n               size=\"100\">\n      </clr-input-container>\n\n      <!--ПОЛЕ ПАРОЛЬ-->\n\n      <clr-input-container>\n\n        <label #label for=\"password\" class=\"input-label clr-col-12\">Пароль</label>\n        <input type=\"password\" clrInput formControlName=\"password\" id=\"password\" name=\"password\" autocomplete=\"off\" required\n               size=\"100\">\n      </clr-input-container>\n\n\n    </form>\n\n    <div class=\"row\">\n      <button type=\"button\" (click)=\"sendLogin()\" class=\"btn btn-primary mt-1\">Войти</button>\n    </div>\n\n  </div>\n</clr-modal>\n\n  <!-- ФОРМА РЕГИСТРАЦИИ ЮЗЕРА -->\n\n  <clr-modal [(clrModalOpen)]=\"isRegister\">\n    <h3 class=\"modal-title\">Новой юзер</h3>\n    <div class=\"modal-body\">\n      <form clrForm [formGroup]=\"registerForm\">\n\n        <!--ПОЛЕ ЛОГИН-->\n\n        <clr-input-container>\n\n          <label #label for=\"reglogin\" class=\"input-label clr-col-12\">Логин</label>\n          <input type=\"text\" clrInput formControlName=\"login\" id=\"reglogin\" name=\"login\" autocomplete=\"off\" required\n                 size=\"100\">\n        </clr-input-container>\n\n        <!--ПОЛЕ ПАРОЛЬ-->\n\n        <clr-input-container>\n\n          <label #label for=\"regpassword\" class=\"input-label clr-col-12\">Пароль</label>\n          <input type=\"password\" clrInput formControlName=\"password\" id=\"regpassword\" name=\"password\" autocomplete=\"off\" required\n                 size=\"100\">\n        </clr-input-container>\n\n        <!--ПОЛЕ EMAIL-->\n\n        <clr-input-container>\n\n          <label #label for=\"regemail\" class=\"input-label clr-col-12\">E-mail</label>\n          <input type=\"email\" clrInput formControlName=\"email\" id=\"regemail\" name=\"email\" autocomplete=\"off\" required\n                 size=\"100\">\n        </clr-input-container>\n        <br/>\n        <!--<span class=\"label label-danger\">Не используйте почтовые ящики сервиса mail.ru!</span>-->\n\n        <!--ПОЛЕ ПОЛНОЕ ИМЯ-->\n\n        <clr-input-container>\n\n          <label #label for=\"registerfullname\" class=\"input-label clr-col-12\">Полное имя</label>\n          <input type=\"text\" clrInput formControlName=\"fullname\" id=\"registerfullname\" name=\"fullname\" autocomplete=\"off\" size=\"100\">\n        </clr-input-container>\n\n      </form>\n\n      <div class=\"row\">\n        <button type=\"button\" (click)=\"register()\" class=\"btn btn-primary mt-1\">Зарегистрироваться</button>\n      </div>\n\n    </div>\n  </clr-modal>\n\n\n<!--ФОРМА РЕДАКТИРОВАНИЯ ПОЛЬЗОВАТЕЛЬСКИХ ДАННЫХ-->\n\n<clr-modal [(clrModalOpen)]=\"isUserDataEdit\">\n  <h3 class=\"modal-title\">Редактирование пользовательских данных</h3>\n  <div class=\"modal-body\">\n    <form clrForm [formGroup]=\"userInfoForm\">\n\n      <!--ПОЛЕ ЛОГИН-->\n\n      <clr-input-container>\n\n        <label #label for=\"editlogin\" class=\"input-label clr-col-12\">Логин</label>\n        <input type=\"text\" clrInput formControlName=\"editlogin\" id=\"editlogin\" name=\"editlogin\" autocomplete=\"off\" required\n               size=\"100\">\n        <clr-control-error *clrIfError=\"'required'\">Обязательно для заполнения</clr-control-error>\n      </clr-input-container>\n\n      <!--ПОЛЕ ПАРОЛЬ-->\n\n      <clr-password-container>\n\n        <label #label for=\"editpassword\" class=\"input-label clr-col-12\">Пароль</label>\n        <input type=\"password\" clrPassword formControlName=\"editpassword\" id=\"editpassword\" name=\"editpassword\" autocomplete=\"off\"\n               size=\"100\">\n        </clr-password-container>\n\n      <br/>\n\n      <!-- ENCRYPTED MODE -->\n\n      <clr-checkbox-wrapper>\n\n        <label #label for=\"isencrypted\" class=\"input-label clr-col-12\">Шифрование данных на фронте</label>\n        <input type=\"checkbox\" clrCheckbox  formControlName=\"isencrypted\" id=\"isencrypted\" name=\"isencrypted\">\n        </clr-checkbox-wrapper>\n\n      <!--ПОЛЕ EMAIL-->\n\n      <clr-input-container>\n\n        <label #label for=\"email\" class=\"input-label clr-col-12\">Email</label>\n        <input type=\"email\" clrInput formControlName=\"email\" id=\"email\" name=\"email\" autocomplete=\"off\" required\n               size=\"100\">\n        <clr-control-error *clrIfError=\"'required'\">Обязательно для заполнения</clr-control-error>\n      </clr-input-container>\n\n      <!-- ПОЛЕ ПОЛНОЕ ИМЯ -->\n\n      <clr-input-container>\n\n        <label #label for=\"fullname\" class=\"input-label clr-col-12\">Полное имя</label>\n        <input type=\"text\" clrInput formControlName=\"fullname\" id=\"fullname\" name=\"fullname\" autocomplete=\"off\"\n               size=\"100\">\n      </clr-input-container>\n\n      <!-- ПОЛЕ КЛЮЧ ШИФРОВАНИЯ -->\n\n      <clr-password-container *ngIf=\"user.userCryptoMode\">\n\n        <label #label for=\"cryptkey\" class=\"input-label clr-col-12\">Персональный ключ шифрования</label>\n        <input type=\"password\" clrPassword formControlName=\"cryptkey\" id=\"cryptkey\" name=\"cryptkey\" autocomplete=\"off\" required\n               size=\"100\">\n        <clr-control-error *clrIfError=\"'required'\">Обязательно для заполнения</clr-control-error>\n      </clr-password-container>\n      <br/>\n\n      <span *ngIf=\"user.userCryptoMode\" class=\"label label-warning\">Ключ хранится только у Вас. Запомните его и не теряйте.</span>\n\n\n\n    </form>\n\n    <div class=\"row\">\n      <button type=\"button\" (click)=\"changeUserData()\" class=\"btn btn-primary mt-1\"  [disabled]=\"!userInfoForm.valid\">ИЗМЕНИТЬ</button>\n      <button type=\"button\" *ngIf=\"user.userCryptoMode\" (click)=\"clearCryptoKey()\" class=\"btn btn-danger mt-1\"  [disabled]=\"!userInfoForm.valid\">СТЕРЕТЬ КЛЮЧ ШИФРОВАНИЯ</button>\n    </div>\n\n  </div>\n</clr-modal>\n\n<!--</clr-main-container>-->\n"

/***/ }),

/***/ "./src/app/ui/layout/header/header.component.ts":
/*!******************************************************!*\
  !*** ./src/app/ui/layout/header/header.component.ts ***!
  \******************************************************/
/*! exports provided: HeaderComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "HeaderComponent", function() { return HeaderComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm5/operators/index.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _service_auth_service__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ../../../service/auth.service */ "./src/app/service/auth.service.ts");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm5/index.js");
/* harmony import */ var _service_common_service__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../../../service/common.service */ "./src/app/service/common.service.ts");
/* harmony import */ var _service_message_code__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ../../../service/message.code */ "./src/app/service/message.code.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _service_http_service__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ../../../service/http.service */ "./src/app/service/http.service.ts");
/* harmony import */ var _dto_user__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ../../../dto/user */ "./src/app/dto/user.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ../../../../environments/environment */ "./src/environments/environment.ts");













var HeaderComponent = /** @class */ (function () {
    function HeaderComponent(commonService, authService, httpService, fb, router) {
        this.commonService = commonService;
        this.authService = authService;
        this.httpService = httpService;
        this.fb = fb;
        this.router = router;
        // --------------------------------- URL'ы -------------------------------------
        this.SERVER_URL = _environments_environment__WEBPACK_IMPORTED_MODULE_12__["environment"].serverUrl;
        this.cryptokey = '';
        this.myBaseUrl = this.SERVER_URL + '/rest/wishes';
        this._myBaseUrl = 'http://localhost:8080/rest/wishes';
        this.usersUrl = this.myBaseUrl + '/users'; // основная ссылка на api
        this.isLogin = false; // вывод диалогового окна логгирования
        this.isRegister = false; // вывод диалогового окна регистрации
        this.isUserDataEdit = false; // вывод диалогового информации о пользователе.
        this.loginForm = this.fb.group({
            login: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required
                ]],
            password: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required
                ]]
        });
        this.registerForm = this.fb.group({
            login: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required,
                    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].pattern(/^[A-Za-z0-9]+$/)
                ]],
            password: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required
                ]],
            email: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required,
                    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$')
                ]],
            fullname: ['', []],
        });
        this.userInfoForm = this.fb.group({
            editlogin: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required,
                    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].pattern(/^[A-Za-z0-9]+$/)
                ]],
            editpassword: ['', []],
            isencrypted: [false, []],
            email: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required,
                    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].email
                ]],
            fullname: ['', []],
            cryptkey: ['', [_angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required]]
        });
    }
    HeaderComponent.prototype.ngOnInit = function () {
        var _this = this;
        var idToken = localStorage.getItem('token');
        this.cryptokey = localStorage.getItem('cryptokey');
        console.log('cryptokey is - ' + this.cryptokey);
        this.user = new _dto_user__WEBPACK_IMPORTED_MODULE_11__["User"]();
        if (idToken) {
            if (this.authService.isAuthenticated()) {
                this.loginDropDownMenu = ['О пользователе', 'Выйти'];
            }
            else {
                this.loginDropDownMenu = ['Зарегистрироваться', 'Войти'];
            }
        }
        else {
            this.loginDropDownMenu = ['Зарегистрироваться', 'Войти'];
        }
        this.subscription = this.commonService.error$.subscribe(function (error) {
            console.log('message ->' + error);
            if (error != null) {
                _this.serviceMessage = error;
                if (_this.serviceMessage.messageType === _this.serviceMessage.SESSION_EXPIRED) {
                    console.log('pizdec ->' + error);
                    _this.loginDropDownMenu = ['Зарегистрироваться', 'Войти'];
                }
            }
        });
    };
    HeaderComponent.prototype.clearCryptoKey = function () {
        localStorage.removeItem('cryptokey');
        this.userInfoForm.patchValue({
            cryptkey: ''
        });
        this.isUserDataEdit = false;
    };
    HeaderComponent.prototype.loadUserData = function () {
        var _this = this;
        this.httpService.isCryptoUser().pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_3__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно получить крипто-статус пользователя!');
        })).subscribe(function (data) {
            _this.user.id = data.id;
            _this.user.login = data.login;
            _this.user.pwd = data.pwd;
            _this.user.userRole = data.userRole;
            _this.user.userCryptoMode = data.userCryptoMode;
            _this.user.creationDate = data.creationDate;
            _this.user.email = data.email;
            _this.user.fullname = data.fullname;
            _this.userInfoForm.patchValue({
                editlogin: _this.user.login,
                editpassword: _this.user.pwd,
                isencrypted: _this.user.userCryptoMode,
                email: _this.user.email,
                fullname: _this.user.fullname
            });
            if (data.userCryptoMode === false) {
                _this.userInfoForm.patchValue({
                    cryptkey: '111'
                });
            }
        });
    };
    HeaderComponent.prototype.loginIconHandler = function (item) {
        if (item === 'Войти') {
            this.isLogin = true;
        }
        else if (item === 'Выйти') {
            this.loginDropDownMenu = ['Зарегистрироваться', 'Войти'];
            localStorage.removeItem('token');
            this.router.navigate(['401']);
        }
        else if (item === 'Зарегистрироваться') {
            this.isRegister = true;
        }
        else if (item === 'О пользователе') {
            this.loadUserData();
            this.isUserDataEdit = true;
            var tempKey = localStorage.getItem('cryptokey');
            this.userInfoForm.patchValue({
                cryptkey: tempKey.toString(),
            });
        }
    };
    HeaderComponent.prototype.errorHandler = function (err, message) {
        this.isLogin = false;
        this.isUserDataEdit = false;
        this.isRegister = false; // прячем окно регистрации
        var errorType = new _service_message_code__WEBPACK_IMPORTED_MODULE_8__["MessageCode"]();
        if (message === 'LOGINERROR') {
            this.sendMessagePush(errorType.WRONG_LOGIN);
        }
        else {
            if (err.error === 'SUCH_USER_EXIST') {
                this.sendMessagePush(errorType.USER_DATA_CHANGE_SUCH_USER_EXISTS);
            }
            else {
                this.sendMessagePush(errorType.USER_DATA_CHANGE_SOME_ERROR);
            }
        }
        return Object(rxjs__WEBPACK_IMPORTED_MODULE_6__["throwError"])(err);
    };
    HeaderComponent.prototype.changeUserData = function () {
        var _this = this;
        if ((this.user.login === this.userInfoForm.value.editlogin)
            && (this.user.email === this.userInfoForm.value.email)
            && (this.user.fullname === this.userInfoForm.value.fullname)) {
            console.log('МЕНЯЕМ ТОЛЬКО CRYPTO-KEY');
            var messageType = new _service_message_code__WEBPACK_IMPORTED_MODULE_8__["MessageCode"]();
            this.sendMessagePush(messageType.USER_DATA_CHANGE_OK);
            this.isUserDataEdit = false;
            localStorage.setItem('cryptokey', this.userInfoForm.value.cryptkey);
            console.log('cryptokey is written - ' + this.userInfoForm.value.cryptkey);
        }
        else {
            this.user.login = this.userInfoForm.value.editlogin;
            this.user.userCryptoMode = this.userInfoForm.value.isencrypted;
            this.user.email = this.userInfoForm.value.email;
            this.user.fullname = this.userInfoForm.value.fullname;
            console.log('МЕНЯЕМ ВСЕ ЮЗЕРСКИЕ ДАННЫЕ');
            this.httpService.updateUserData(this.user, this.usersUrl + '/' + this.user.id).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_3__["catchError"])(function (err) {
                return _this.errorHandler(err, 'Невозможно выполнить редактирование пользовательских данных!');
            })).subscribe(function (hero) {
                var messageType = new _service_message_code__WEBPACK_IMPORTED_MODULE_8__["MessageCode"]();
                _this.sendMessagePush(messageType.USER_DATA_CHANGE_OK);
                _this.isUserDataEdit = false;
                localStorage.setItem('cryptokey', _this.userInfoForm.value.cryptkey);
                console.log('cryptokey is written - ' + _this.userInfoForm.value.cryptkey);
            });
        }
    };
    HeaderComponent.prototype.sendMessagePush = function (message) {
        var errorType = new _service_message_code__WEBPACK_IMPORTED_MODULE_8__["MessageCode"]();
        errorType.messageType = message;
        console.log('Error message- ' + errorType.messageType);
        this.commonService.pushError(errorType);
    };
    HeaderComponent.prototype.register = function () {
        var _this = this;
        var newUser = new _dto_user__WEBPACK_IMPORTED_MODULE_11__["User"]();
        newUser.login = this.registerForm.value.login;
        newUser.pwd = this.registerForm.value.password;
        newUser.fullname = this.registerForm.value.fullname;
        newUser.email = this.registerForm.value.email;
        newUser.userCryptoMode = false;
        newUser.userRole = 'USER';
        this.authService.register(newUser, this.usersUrl).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_3__["catchError"])(function (err) {
            return _this.errorHandler(err, 'REGISTERERROR');
        })).subscribe(function (res) {
            var messageType = new _service_message_code__WEBPACK_IMPORTED_MODULE_8__["MessageCode"]();
            _this.sendMessagePush(messageType.REGISTER_OK);
            _this.isRegister = false;
        });
    };
    HeaderComponent.prototype.sendLogin = function () {
        var _this = this;
        var body = new _angular_common_http__WEBPACK_IMPORTED_MODULE_4__["HttpParams"]()
            .set('username', this.loginForm.value.login)
            .set('password', this.loginForm.value.password);
        this.authService.login(body.toString())
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_3__["catchError"])(function (err) {
            return _this.errorHandler(err, 'LOGINERROR');
        }))
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_3__["tap"])(function (resp) {
            console.log('header', resp.headers.get('Authorization'));
            localStorage.removeItem('token');
            localStorage.setItem('token', resp.headers.get('Authorization'));
            console.log('storage', localStorage.getItem('token'));
            _this.isLogin = false;
            _this.authService.refreshToken();
            _this.router.navigate(['']);
            _this.loginDropDownMenu = ['О пользователе', 'Выйти'];
            var message = new _service_message_code__WEBPACK_IMPORTED_MODULE_8__["MessageCode"]();
            _this.sendMessagePush(message.AUTH_LOGIN_OK);
        }))
            .subscribe();
    };
    HeaderComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-header',
            template: __webpack_require__(/*! ./header.component.html */ "./src/app/ui/layout/header/header.component.html"),
            providers: [_service_auth_service__WEBPACK_IMPORTED_MODULE_5__["AuthService"], _service_http_service__WEBPACK_IMPORTED_MODULE_10__["HttpService"]]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_service_common_service__WEBPACK_IMPORTED_MODULE_7__["CommonService"], _service_auth_service__WEBPACK_IMPORTED_MODULE_5__["AuthService"], _service_http_service__WEBPACK_IMPORTED_MODULE_10__["HttpService"],
            _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"], _angular_router__WEBPACK_IMPORTED_MODULE_9__["Router"]])
    ], HeaderComponent);
    return HeaderComponent;
}());



/***/ }),

/***/ "./src/app/ui/layout/layout.component.html":
/*!*************************************************!*\
  !*** ./src/app/ui/layout/layout.component.html ***!
  \*************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<clr-main-container>\n  <clr-header class=\"header\">\n    <app-header></app-header>\n  </clr-header>\n  <div class=\"content-container\">\n    <clr-vertical-nav [clr-nav-level]=\"1\">\n      <a clrVerticalNavLink routerLink=\"../401\" routerLinkActive=\"active\">О проекте</a>\n      <a *ngIf=\"idToken\" clrVerticalNavLink routerLink=\"../\" routerLinkActive=\"active\">Главная</a>\n    </clr-vertical-nav>\n    <div class=\"content-area\">\n      <router-outlet></router-outlet>\n    </div>\n  </div>\n  <app-session-timeout-modal></app-session-timeout-modal>\n</clr-main-container>\n"

/***/ }),

/***/ "./src/app/ui/layout/layout.component.ts":
/*!***********************************************!*\
  !*** ./src/app/ui/layout/layout.component.ts ***!
  \***********************************************/
/*! exports provided: LayoutComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "LayoutComponent", function() { return LayoutComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _service_common_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../service/common.service */ "./src/app/service/common.service.ts");
/* harmony import */ var _auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @auth0/angular-jwt */ "./node_modules/@auth0/angular-jwt/index.js");




var LayoutComponent = /** @class */ (function () {
    function LayoutComponent() {
    }
    LayoutComponent.prototype.ngOnInit = function () {
    };
    LayoutComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-layout',
            template: __webpack_require__(/*! ./layout.component.html */ "./src/app/ui/layout/layout.component.html"),
            changeDetection: _angular_core__WEBPACK_IMPORTED_MODULE_1__["ChangeDetectionStrategy"].OnPush,
            providers: [_service_common_service__WEBPACK_IMPORTED_MODULE_2__["CommonService"], _auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_3__["JwtHelperService"]]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [])
    ], LayoutComponent);
    return LayoutComponent;
}());



/***/ }),

/***/ "./src/app/ui/layout/main/main.component.css":
/*!***************************************************!*\
  !*** ./src/app/ui/layout/main/main.component.css ***!
  \***************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n::ng-deep clr-icon:hover {\n  transform: scale(1.2);\n\n}\n\n.dropdown .dropdown-toggle {\n  margin: 5px;\n}\n\n.sumallrow {\n  background-color: #790909;\n  color: #ffffff;\n}\n\n::ng-deep  .main-container {\n}\n\n.sumpriorrow {\n  background-color: #299834;\n  color: #ffdb51;\n\n}\n\n@media screen and (min-width: 1447px) {\n  .ar-900-show {\n    display:none!important;\n  }\n}\n\n@media screen and (max-width: 1447px) {\n  .ar-900-hide {\n    display:none!important;\n  }\n  .ar-900-show {\n    display:initial!important;\n  }\n}\n\n@media screen and (max-width: 475px) {\n  .ar-475-hide {\n    display:none!important;\n  }\n\n  .string-truncate {\n    width: 120px;\n    white-space: nowrap;\n    overflow: hidden;\n    text-overflow: ellipsis;\n  }\n\n}\n\n@media screen and (min-width: 475px) {\n  .ar-475-show {\n    display:none!important;\n  }\n}\n\n@media screen and (max-width: 360px) {\n  .ar-360-hide {\n    display:none!important;\n  }\n}\n\n@media screen and (max-width: 500px){\n\n    /*.sidenav {*/\n      /*min-width: 1%;*/\n      /*visibility: hidden;*/\n    /*}*/\n\n\n    .table tr td:nth-child(3),\n    .table tr th:nth-child(3) {\n      display: none;\n    }\n    .table tr td:nth-child(4),\n    .table tr th:nth-child(4) {\n      display: none;\n    }\n\n    .table tr td:nth-child(5),\n    .table tr th:nth-child(5) {\n      display: none;\n    }\n\n    .table tr td:nth-child(6),\n    .table tr th:nth-child(6) {\n      display: none;\n    }\n\n    }\n\n.fas .fa-arrow-up{\n  background: #0c5460;\n}\n\n.fa, .fas:hover {\n  background: aqua;\n}\n\n:host{\n\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvdWkvbGF5b3V0L21haW4vbWFpbi5jb21wb25lbnQuY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7QUFDQTtFQUdFLHFCQUFxQjs7QUFFdkI7O0FBRUE7RUFDRSxXQUFXO0FBQ2I7O0FBRUE7RUFDRSx5QkFBeUI7RUFDekIsY0FBYztBQUNoQjs7QUFFQTtBQUNBOztBQUVBO0VBQ0UseUJBQXlCO0VBQ3pCLGNBQWM7O0FBRWhCOztBQUVBO0VBQ0U7SUFDRSxzQkFBc0I7RUFDeEI7QUFDRjs7QUFFQTtFQUNFO0lBQ0Usc0JBQXNCO0VBQ3hCO0VBQ0E7SUFDRSx5QkFBeUI7RUFDM0I7QUFDRjs7QUFFQTtFQUNFO0lBQ0Usc0JBQXNCO0VBQ3hCOztFQUVBO0lBQ0UsWUFBWTtJQUNaLG1CQUFtQjtJQUNuQixnQkFBZ0I7SUFDaEIsdUJBQXVCO0VBQ3pCOztBQUVGOztBQUVBO0VBQ0U7SUFDRSxzQkFBc0I7RUFDeEI7QUFDRjs7QUFJQTtFQUNFO0lBQ0Usc0JBQXNCO0VBQ3hCO0FBQ0Y7O0FBRUE7O0lBRUksYUFBYTtNQUNYLGlCQUFpQjtNQUNqQixzQkFBc0I7SUFDeEIsSUFBSTs7O0lBR0o7O01BRUUsYUFBYTtJQUNmO0lBQ0E7O01BRUUsYUFBYTtJQUNmOztJQUVBOztNQUVFLGFBQWE7SUFDZjs7SUFFQTs7TUFFRSxhQUFhO0lBQ2Y7O0lBRUE7O0FBR0o7RUFDRSxtQkFBbUI7QUFDckI7O0FBSUE7RUFDRSxnQkFBZ0I7QUFDbEI7O0FBQ0E7O0FBRUEiLCJmaWxlIjoic3JjL2FwcC91aS9sYXlvdXQvbWFpbi9tYWluLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyJcbjo6bmctZGVlcCBjbHItaWNvbjpob3ZlciB7XG4gIC13ZWJraXQtdHJhbnNmb3JtOiBzY2FsZSgxLjIpO1xuICAtbXMtdHJhbnNmb3JtOiBzY2FsZSgxLjIpO1xuICB0cmFuc2Zvcm06IHNjYWxlKDEuMik7XG5cbn1cblxuLmRyb3Bkb3duIC5kcm9wZG93bi10b2dnbGUge1xuICBtYXJnaW46IDVweDtcbn1cblxuLnN1bWFsbHJvdyB7XG4gIGJhY2tncm91bmQtY29sb3I6ICM3OTA5MDk7XG4gIGNvbG9yOiAjZmZmZmZmO1xufVxuXG46Om5nLWRlZXAgIC5tYWluLWNvbnRhaW5lciB7XG59XG5cbi5zdW1wcmlvcnJvdyB7XG4gIGJhY2tncm91bmQtY29sb3I6ICMyOTk4MzQ7XG4gIGNvbG9yOiAjZmZkYjUxO1xuXG59XG5cbkBtZWRpYSBzY3JlZW4gYW5kIChtaW4td2lkdGg6IDE0NDdweCkge1xuICAuYXItOTAwLXNob3cge1xuICAgIGRpc3BsYXk6bm9uZSFpbXBvcnRhbnQ7XG4gIH1cbn1cblxuQG1lZGlhIHNjcmVlbiBhbmQgKG1heC13aWR0aDogMTQ0N3B4KSB7XG4gIC5hci05MDAtaGlkZSB7XG4gICAgZGlzcGxheTpub25lIWltcG9ydGFudDtcbiAgfVxuICAuYXItOTAwLXNob3cge1xuICAgIGRpc3BsYXk6aW5pdGlhbCFpbXBvcnRhbnQ7XG4gIH1cbn1cblxuQG1lZGlhIHNjcmVlbiBhbmQgKG1heC13aWR0aDogNDc1cHgpIHtcbiAgLmFyLTQ3NS1oaWRlIHtcbiAgICBkaXNwbGF5Om5vbmUhaW1wb3J0YW50O1xuICB9XG5cbiAgLnN0cmluZy10cnVuY2F0ZSB7XG4gICAgd2lkdGg6IDEyMHB4O1xuICAgIHdoaXRlLXNwYWNlOiBub3dyYXA7XG4gICAgb3ZlcmZsb3c6IGhpZGRlbjtcbiAgICB0ZXh0LW92ZXJmbG93OiBlbGxpcHNpcztcbiAgfVxuXG59XG5cbkBtZWRpYSBzY3JlZW4gYW5kIChtaW4td2lkdGg6IDQ3NXB4KSB7XG4gIC5hci00NzUtc2hvdyB7XG4gICAgZGlzcGxheTpub25lIWltcG9ydGFudDtcbiAgfVxufVxuXG5cblxuQG1lZGlhIHNjcmVlbiBhbmQgKG1heC13aWR0aDogMzYwcHgpIHtcbiAgLmFyLTM2MC1oaWRlIHtcbiAgICBkaXNwbGF5Om5vbmUhaW1wb3J0YW50O1xuICB9XG59XG5cbkBtZWRpYSBzY3JlZW4gYW5kIChtYXgtd2lkdGg6IDUwMHB4KXtcblxuICAgIC8qLnNpZGVuYXYgeyovXG4gICAgICAvKm1pbi13aWR0aDogMSU7Ki9cbiAgICAgIC8qdmlzaWJpbGl0eTogaGlkZGVuOyovXG4gICAgLyp9Ki9cblxuXG4gICAgLnRhYmxlIHRyIHRkOm50aC1jaGlsZCgzKSxcbiAgICAudGFibGUgdHIgdGg6bnRoLWNoaWxkKDMpIHtcbiAgICAgIGRpc3BsYXk6IG5vbmU7XG4gICAgfVxuICAgIC50YWJsZSB0ciB0ZDpudGgtY2hpbGQoNCksXG4gICAgLnRhYmxlIHRyIHRoOm50aC1jaGlsZCg0KSB7XG4gICAgICBkaXNwbGF5OiBub25lO1xuICAgIH1cblxuICAgIC50YWJsZSB0ciB0ZDpudGgtY2hpbGQoNSksXG4gICAgLnRhYmxlIHRyIHRoOm50aC1jaGlsZCg1KSB7XG4gICAgICBkaXNwbGF5OiBub25lO1xuICAgIH1cblxuICAgIC50YWJsZSB0ciB0ZDpudGgtY2hpbGQoNiksXG4gICAgLnRhYmxlIHRyIHRoOm50aC1jaGlsZCg2KSB7XG4gICAgICBkaXNwbGF5OiBub25lO1xuICAgIH1cblxuICAgIH1cblxuXG4uZmFzIC5mYS1hcnJvdy11cHtcbiAgYmFja2dyb3VuZDogIzBjNTQ2MDtcbn1cblxuXG5cbi5mYSwgLmZhczpob3ZlciB7XG4gIGJhY2tncm91bmQ6IGFxdWE7XG59XG46aG9zdHtcblxufVxuIl19 */"

/***/ }),

/***/ "./src/app/ui/layout/main/main.component.html":
/*!****************************************************!*\
  !*** ./src/app/ui/layout/main/main.component.html ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<clr-alert [clrAlertType]=\"'success'\" *ngIf=\"result\">\n  <clr-alert-item>\n        <span class=\"alert-text\">\n            {{result}}\n        </span>\n  </clr-alert-item>\n</clr-alert>\n\n<clr-alert [clrAlertType]=\"'danger'\" *ngIf=\"error\">\n  <clr-alert-item>\n        <span class=\"alert-text\">\n            {{error}}\n        </span>\n  </clr-alert-item>\n</clr-alert>\n\n<clr-datagrid *ngIf=\"!monthOrdermode\">\n  <clr-dg-action-bar>\n    <div class=\"actions-container\">\n      <div class=\"ar-900-hide\">\n        <button class=\"btn btn-primary\" (click)=\"openEditWish($event, item, 2)\"> Добавить</button>\n        <button class=\"btn btn-secondary\" (click)=\"updateWishTable()\"> Обновить</button>\n        <button class=\"btn btn-secondary; hidden-md-down\" (click)=\"openAddSalaryModal($event)\"> Задать зарплату\n        </button>\n        <button class=\"btn btn-secondary; hidden-md-down\" (click)=\"openParseCsv($event)\" *ngIf=\"userRole==='ADMIN'\">\n          Парсинг csv\n        </button>\n        <button class=\"{{filterMode ? 'btn btn-danger; hidden-md-down' : 'btn btn-secondary; hidden-md-down'}}\"\n                (click)=\"filterWishes()\"> {{filterButtonText}}\n        </button>\n\n        <clr-dropdown class=\"hidden-md-down\">\n          <button type=\"button\" class=\"btn btn-outline-primary\" clrDropdownTrigger>\n            РЕЖИМ ОТОБРАЖЕНИЯ\n            <clr-icon shape=\"caret down\"></clr-icon>\n          </button>\n          <clr-dropdown-menu *clrIfOpen>\n            <label class=\"dropdown-header\">Режим отображения</label>\n            <a *ngFor=\"let item of filters\" (click)=\"changeFilter(item)\" clrDropdownItem>{{item}}</a>\n          </clr-dropdown-menu>\n        </clr-dropdown>\n\n        <clr-dropdown class=\"hidden-md-down\">\n          <button type=\"button\" class=\"btn btn-outline-primary\" clrDropdownTrigger>\n            СОРТИРОВКА\n            <clr-icon shape=\"caret down\"></clr-icon>\n          </button>\n          <clr-dropdown-menu *clrIfOpen>\n            <label class=\"dropdown-header\">Сортировка</label>\n            <a *ngFor=\"let item of mainSort\" (click)=\"sortMainList(item)\" clrDropdownItem>{{item}}</a>\n          </clr-dropdown-menu>\n        </clr-dropdown>\n      </div>\n\n      <!--МИНИ-КНОПКИ ДЛЯ МОБИЛКИ-->\n\n      <div class=\"ar-900-show\">\n        <div class=\"clr-row\" style=\"margin-left: 1%\">\n          <button type=\"button\" class=\"btn btn-icon\" (click)=\"openEditWish($event, item, 2)\"\n                  aria-label=\"Добавить желание\">\n            <clr-icon shape=\"plus\"></clr-icon>\n          </button>\n          <button type=\"button\" class=\"btn btn-icon\" (click)=\"getWishes(apiUrl)\" aria-label=\"Обновить\">\n            <clr-icon shape=\"refresh\"></clr-icon>\n          </button>\n          <div class=\"ar-360-hide\">\n            <button type=\"button\" (click)=\"openAddSalaryModal($event)\" class=\"btn btn-icon\"\n                    aria-label=\"Задать зарплату\">\n              <clr-icon shape=\"ruble\"></clr-icon>\n            </button>\n          </div>\n          <button type=\"button\" class=\"{{filterMode ? 'btn btn-icon btn-danger' : 'btn btn-icon'}}\"\n                  (click)=\"filterWishes()\" aria-label=\"Поиск\">\n            <clr-icon shape=\"search\"></clr-icon>\n          </button>\n          <button type=\"button\" class=\"{{filterMode ? 'btn btn-icon btn-danger' : 'btn btn-icon'}}\"\n                  (click)=\"summInfo()\" aria-label=\"Итоги\">\n            <clr-icon shape=\"help\"></clr-icon>\n          </button>\n\n          <clr-dropdown>\n            <button type=\"button\" class=\"btn btn-icon\" aria-label=\"Режимы\" clrDropdownTrigger>\n              <clr-icon shape=\"eye\"></clr-icon>\n              <clr-icon shape=\"caret down\"></clr-icon>\n            </button>\n            <clr-dropdown-menu clrPosition=\"bottom-right\" *clrIfOpen>\n              <label class=\"dropdown-header\">Режим отображения</label>\n              <a *ngFor=\"let item of filters\" (click)=\"changeFilter(item)\" clrDropdownItem>{{item}}</a>\n            </clr-dropdown-menu>\n          </clr-dropdown>\n\n          <div class=\"ar-475-hide\">\n\n            <clr-dropdown>\n              <button type=\"button\" class=\"btn btn-icon\" aria-label=\"Сортировка\" clrDropdownTrigger>\n                <clr-icon shape=\"sort-by\"></clr-icon>\n                <clr-icon shape=\"caret down\"></clr-icon>\n              </button>\n              <clr-dropdown-menu *clrIfOpen>\n                <label class=\"dropdown-header\">Сортировка</label>\n                <a *ngFor=\"let item of mainSort\" (click)=\"sortMainList(item)\" clrDropdownItem>{{item}}</a>\n              </clr-dropdown-menu>\n            </clr-dropdown>\n          </div>\n        </div>\n      </div>\n    </div>\n  </clr-dg-action-bar>\n  <clr-dg-column>Желание</clr-dg-column>\n  <clr-dg-column>Цена</clr-dg-column>\n  <clr-dg-column [style.width.px]=\"20\" class=\"hidden-md-down\">Приоритет</clr-dg-column>\n  <clr-dg-column [style.width.px]=\"10\" class=\"hidden-md-down\">.</clr-dg-column>\n  <clr-dg-column [style.width.px]=\"10\" class=\"hidden-md-down\">.</clr-dg-column>\n  <clr-dg-column [style.width.px]=\"10\" class=\"hidden-md-down\">Ред.</clr-dg-column>\n\n  <clr-dg-row *clrDgItems=\"let item of wishes\" [clrDgItem]=\"item\">\n    <clr-dg-cell><a (click)=\"openEditWish($event, item, 1)\">{{item.wish}}</a></clr-dg-cell>\n    <clr-dg-cell style=\"text-align: center\">{{item.price | number:'2.'}}</clr-dg-cell>\n    <clr-dg-cell class=\"hidden-md-down\" style=\"text-align: center\">{{item.priority}}</clr-dg-cell>\n    <clr-dg-cell class=\"hidden-md-down\" style=\"text-align: center\">\n      <clr-icon shape=\"upload\" (click)=\"changePriority(item, 'down')\"></clr-icon>\n    </clr-dg-cell>\n    <clr-dg-cell class=\"hidden-md-down\" style=\"text-align: center\">\n      <clr-icon shape=\"download\" (click)=\"changePriority(item, 'up')\"></clr-icon>\n    </clr-dg-cell>\n    <clr-dg-cell class=\"hidden-md-down\" style=\"text-align: center\">\n      <clr-icon shape=\"edit\" (click)=\"openEditWish($event, item, 1)\"></clr-icon>\n    </clr-dg-cell>\n  </clr-dg-row>\n\n  <clr-dg-footer>\n    <div class=\"clr-row\">\n      <div class=\"ar-900-hide\">\n      <span class=\"label label-danger\" style=\"margin-left: 1%\">ИТОГО<span\n        class=\"badge badge-orange\">{{summAll | number:'2.'}} руб.</span></span>\n        <span class=\"label label-danger\">Реализация<span class=\"badge badge-danger\">{{periodAll}} мес.</span></span>\n        <span class=\"label label-success\">ИТОГО (PRIOR)<span class=\"badge badge-orange\">{{summPriority | number:'2.'}}\n          руб.</span></span>\n        <span class=\"label label-success\">Реализация<span\n          class=\"badge badge-success\">{{periodPriority}} мес.</span></span>\n        <span class=\"label label-gray\">Среднее время<span\n          class=\"badge badge-blue\">{{implementationPeriod}}</span></span>\n      </div>\n\n      <div>\n        <clr-dg-pagination #pagination [clrDgPageSize]=\"15\">\n          <clr-dg-page-size [clrPageSizeOptions]=\"[3,5,10,15, 100]\" class=\"ar-475-hide\" style=\"margin-left: 4%\">\n            жел/стр.\n          </clr-dg-page-size>\n          <span class=\"ar-360-hide\"> {{pagination.firstItem + 1}} - {{pagination.lastItem + 1}}\n            из {{pagination.totalItems}} жел.</span>\n        </clr-dg-pagination>\n      </div>\n    </div>\n\n  </clr-dg-footer>\n</clr-datagrid>\n\n<div *ngIf=\"monthOrdermode\">\n\n  <div class=\"ar-900-hide\">\n    <button class=\"btn btn-primary\" (click)=\"toMainTableMode()\"> Вернуться в табличный режим</button>\n    <button class=\"btn btn-secondary\" (click)=\"getWishesWithMonthGroupping('?sortType=all')\"> Обновить</button>\n    <clr-dropdown>\n      <button type=\"button\" class=\"btn btn-outline-primary\" clrDropdownTrigger>\n        СОРТИРОВКА\n        <clr-icon shape=\"caret down\"></clr-icon>\n      </button>\n      <clr-dropdown-menu *clrIfOpen>\n        <label class=\"dropdown-header\">Сортировка</label>\n        <a *ngFor=\"let item of groupMonthSort\" (click)=\"sortGroupList(item)\" clrDropdownItem>{{item}}</a>\n      </clr-dropdown-menu>\n    </clr-dropdown>\n  </div>\n\n  <!--КНОПКИ ДЛЯ МОБИЛКИ-->\n  <div class=\"ar-900-show\">\n    <button type=\"button\" class=\"btn btn-icon\" (click)=\"toMainTableMode()\" aria-label=\"Вернуться в табличный режим\">\n      <clr-icon shape=\"undo\"></clr-icon>\n    </button>\n    <button type=\"button\" class=\"btn btn-icon\" (click)=\"getWishesWithMonthGroupping('?sortType=all')\"\n            aria-label=\"Обновить\">\n      <clr-icon shape=\"refresh\"></clr-icon>\n    </button>\n  </div>\n\n  <clr-tree-node *ngFor=\"let month of wishGroups\" [(clrExpanded)]=\"month.expanded\">\n    <span class=\"{{month.colorClass}}\">{{month.monthName}} <span class=\"badge\">{{month.sum}}</span></span>\n    <span [ngClass]=\"(month.balance>0)?'badge badge-success':'badge badge-danger'\">{{month.balance}}</span>\n    <span *ngIf=\"month.overflow\" class=\"badge badge-danger\">Перебор!</span>\n\n    <clr-tree-node *ngFor=\"let items of month.wishList\">\n      <a [routerLink]=\"\" (click)=\"editMonthGroupItem(items)\">\n        <div class=\"string-truncate\">{{items.wish}}</div>\n      </a>{{'&nbsp;| ' + items.price + ' |'}}\n      <clr-icon shape=\"upload\" (click)=\"changePriorityMonth(items, 'up')\"></clr-icon>\n      |\n      <clr-icon shape=\"download\" (click)=\"changePriorityMonth(items, 'down')\"></clr-icon>\n    </clr-tree-node>\n\n  </clr-tree-node>\n\n</div>\n\n<clr-modal [(clrModalOpen)]=\"isEdit\">\n  <h3 class=\"modal-title\">{{isEditMode ? 'Редактировать желание' : 'Добавить желание'}}</h3>\n  <div class=\"modal-body\">\n    <form clrForm [formGroup]=\"form\">\n\n      <!--ПОЛЕ ДАТА СОЗДАНИЯ-->\n\n      <clr-input-container>\n\n        <label #label for=\"creationDate\" class=\"input-label clr-col-12\">Дата создания</label>\n        <input type=\"text\" clrInput formControlName=\"creationDate\" id=\"creationDate\" name=\"creationDate\"\n               autocomplete=\"off\" readonly\n               size=\"50\">\n      </clr-input-container>\n\n      <!--ПОЛЕ ID-->\n\n      <clr-input-container hidden>\n\n        <label #label for=\"id\" class=\"input-label clr-col-12\">id</label>\n        <input type=\"text\" clrInput formControlName=\"id\" id=\"id\" name=\"id\" autocomplete=\"off\" readonly required\n               size=\"50\">\n      </clr-input-container>\n\n      <!--ПОЛЕ ИМЯ-->\n\n      <clr-input-container>\n\n        <label #label for=\"name\" class=\"input-label clr-col-12\">Название</label>\n        <input type=\"text\"\n               clrInput\n               formControlName=\"name\"\n               id=\"name\"\n               name=\"name\"\n               autocomplete=\"off\" required size=\"100\">\n\n        <clr-control-error *clrIfError=\"'required'\">Обязательно для заполнения</clr-control-error>\n      </clr-input-container>\n\n      <!--ПОЛЕ ОПИСАНИЕ-->\n\n      <clr-input-container>\n        <label #label for=\"description\" class=\"input-label clr-col-12\">Описание</label>\n        <input type=\"text\"\n               clrInput\n               formControlName=\"description\"\n               id=\"description\"\n               name=\"description\"\n               autocomplete=\"off\" required size=\"100\">\n      </clr-input-container>\n\n      <!--ПОЛЕ URL-->\n\n      <clr-input-container>\n        <label #label for=\"url\" class=\"input-label clr-col-12\">URL</label>\n        <input type=\"url\"\n               clrInput\n               formControlName=\"url\"\n               id=\"url\"\n               name=\"url\"\n               autocomplete=\"off\" required size=\"100\">\n      </clr-input-container>\n\n      <!--ПОЛЕ PRIORITY-->\n\n      <clr-input-container>\n        <label #label for=\"priority\" class=\"input-label clr-col-12\">Приоритет</label>\n        <input type=\"text\"\n               clrInput\n               formControlName=\"priority\"\n               id=\"priority\"\n               name=\"priority\"\n               autocomplete=\"off\">\n        <clr-control-error *clrIfError=\"'required'\">Обязательно для заполнения</clr-control-error>\n      </clr-input-container>\n\n      <!--ПОЛЕ PRICE-->\n\n      <clr-input-container>\n        <label #label for=\"price\" class=\"input-label clr-col-12\">Цена</label>\n        <input type=\"text\"\n               clrInput\n               formControlName=\"price\"\n               id=\"price\"\n               name=\"price\"\n               autocomplete=\"off\">\n      </clr-input-container>\n    </form>\n\n    <div class=\"row\">\n      <button type=\"button\" (click)=\"addEditWish()\" class=\"btn btn-primary mt-1\">Сохранить</button>\n      <button type=\"button\" (click)=\"deleteWish()\" *ngIf=\"isEditMode\" class=\"btn btn-danger mt-1\">Удалить</button>\n      <button type=\"button\" (click)=\"realizeWish()\" *ngIf=\"isEditMode\" class=\"btn btn-success mt-1\">Реализовано\n      </button>\n    </div>\n\n  </div>\n</clr-modal>\n\n<clr-modal [(clrModalOpen)]=\"isSalaryAdd\">\n  <h3 class=\"modal-title\">Добавить зарплату</h3>\n  <div class=\"modal-body\">\n    <form clrForm [formGroup]=\"salaryForm\">\n\n      <!--ПОЛЕ SALARY-->\n\n      <clr-input-container>\n        <label #label for=\"salary\" class=\"input-label clr-col-12\">Зарплата</label>\n        <input type=\"text\" clrInput formControlName=\"salary\" id=\"salary\" name=\"salary\" autocomplete=\"off\" required\n               size=\"100\">\n      </clr-input-container>\n\n      <!--ПОЛЕ RESIDUAL SALARY-->\n      <clr-input-container>\n        <label #label for=\"residualSalary\" class=\"input-label clr-col-12\">Остаточная зарплата</label>\n        <input type=\"text\" clrInput formControlName=\"residualSalary\" id=\"residualSalary\" name=\"residualSalary\"\n               autocomplete=\"off\" required\n               size=\"100\">\n      </clr-input-container>\n      <br/>\n      <span\n        class=\"label label-warning\">Зарплата, которая остается у вас после всех трат. Расчет идет именно по ней.</span>\n    </form>\n    <div class=\"row\">\n      <button type=\"button\" (click)=\"addSalary()\" class=\"btn btn-primary mt-1\">Добавить</button>\n    </div>\n  </div>\n</clr-modal>\n\n<clr-modal [(clrModalOpen)]=\"isCsvParse\">\n  <h3 class=\"modal-title\">Парсинг csv-файла</h3>\n  <div class=\"modal-body\">\n    <form clrForm [formGroup]=\"csvForm\">\n\n\n      <!--ПОЛЕ FILE-->\n\n      <clr-input-container>\n\n        <label #label for=\"csvfile\" class=\"input-label clr-col-12\">Укажите csv-файл</label>\n        <input type=\"file\" clrInput formControlName=\"csvfile\" id=\"csvfile\" name=\"csvfile\" autocomplete=\"off\"\n               required\n               size=\"100\">\n      </clr-input-container>\n\n      <form [formGroup]=\"uploadForm\" (ngSubmit)=\"onSubmit()\">\n\n        <input type=\"file\" name=\"profile\" (change)=\"onFileSelect($event)\"/>\n        <div>\n          <button type=\"submit\">Upload</button>\n        </div>\n      </form>\n\n    </form>\n\n    <div class=\"row\">\n      <button type=\"button\" (click)=\"sendCsvFile()\" class=\"btn btn-primary mt-1\">Парсить</button>\n    </div>\n\n  </div>\n</clr-modal>\n\n<!-- =================== ФОРМА ФИЛЬТРАЦИИ =================== -->\n\n<clr-modal [(clrModalOpen)]=\"isFilterModal\">\n  <h3 class=\"modal-title\">Фильтры и поиск</h3>\n  <div class=\"modal-body\">\n    <form clrForm [formGroup]=\"filterForm\">\n\n\n      <!-- ПОЛЕ ПОИСКА / ФИЛЬТРАЦИИ ПО ЖЕЛАНИЮ -->\n\n      <clr-input-container>\n\n        <label #label for=\"wish\" class=\"input-label clr-col-12\">Желание</label>\n        <input type=\"text\" clrInput formControlName=\"wish\" id=\"wish\" name=\"wish\" autocomplete=\"off\" required\n               size=\"100\">\n      </clr-input-container>\n\n    </form>\n\n    <div class=\"row\">\n      <button type=\"button\" (click)=\"applyFilter()\" class=\"btn btn-primary mt-1\">Фильтровать</button>\n    </div>\n\n  </div>\n</clr-modal>\n\n<!--ФОРМА РЕДАКТИРОВАНИЯ ЖЕЛАНИЯ ПРИ ПОМЕСЯЧНОЙ ГРУППИРОВКЕ -->\n\n<clr-modal [(clrModalOpen)]=\"isMonthGroupModeWishEdit\">\n  <h3 class=\"modal-title\">Изменить порядок желания</h3>\n  <div class=\"modal-body\">\n    <form clrForm [formGroup]=\"MonthGroupModeWishEdit\">\n\n      <!-- ПОЛЕ ЖЕЛАНИЕ -->\n\n      <clr-input-container>\n\n        <label #label for=\"selected_wish\" class=\"input-label clr-col-12\">Желание</label>\n        <input type=\"text\" clrInput formControlName=\"wish\" id=\"selected_wish\" name=\"wish\" autocomplete=\"off\"\n               required readonly\n               size=\"100%\">\n      </clr-input-container>\n\n      <div>\n        <clr-select-container>\n          <label #label for=\"month_field\" class=\"input-label clr-col-12\">Месяц</label>\n          <select id=\"month_field\" [ngModel]=\"month\" formControlName=\"month\" clrSelect>\n            <option *ngFor=\"let currentMonth of monthList\" [value]=\"currentMonth\">{{currentMonth}}</option>\n          </select>\n        </clr-select-container>\n      </div>\n\n    </form>\n\n    <div class=\"row\">\n      <button type=\"button\" (click)=\"applyMonthChange4Wish()\" class=\"btn btn-primary mt-1\">Принять</button>\n    </div>\n\n  </div>\n</clr-modal>\n\n\n<!--ФОРМА ВЫВОДА ИТОГОВЫХ ДАННЫХ -->\n\n<clr-modal [(clrModalOpen)]=\"isSummInfoForm\">\n  <h3 class=\"modal-title\">ИТОГО</h3>\n  <div class=\"modal-body\">\n\n    <div>\n      <span class=\"label label-danger\">ВСЕ ЖЕЛАНИЯ <span\n        class=\"badge badge-orange\">{{summAll | number:'2.'}} руб.</span></span>\n    </div>\n    <div>\n      <span class=\"label label-danger\">Реализация<span class=\"badge badge-danger\">{{periodAll}} мес.</span></span>\n    </div>\n    <div>\n      <span class=\"label label-success\">ИТОГО (PRIOR)<span class=\"badge badge-orange\">{{summPriority | number:'2.'}}\n        руб.</span></span>\n    </div>\n    <div>\n      <span class=\"label label-success\">Реализация<span\n        class=\"badge badge-success\">{{periodPriority}} мес.</span></span>\n    </div>\n    <div>\n      <span class=\"label label-gray\">Среднее время<span class=\"badge badge-blue\">{{implementationPeriod}}\n        дней.</span></span>\n    </div>\n    <div>\n      <span class=\"label label-purple\">Реализовано за все время<span class=\"badge badge-blue\">{{implemetedSummAllTime}}\n        руб.</span></span>\n    </div>\n    <div>\n      <span class=\"label label-purple\">Зарплата<span class=\"badge badge-blue\">{{lastSalary}}\n        руб.</span></span>\n    </div>\n    <div>\n      <span class=\"label label-light-blue\">Реализовано за тек.месяц<span\n        class=\"badge badge-blue\">{{implemetedSummMonth}} руб.</span></span>\n    </div>\n    <div class=\"row\">\n      <button type=\"button\" (click)=\"isSummInfoForm=false\" class=\"btn btn-primary mt-1\">Закрыть</button>\n    </div>\n\n\n  </div>\n</clr-modal>\n"

/***/ }),

/***/ "./src/app/ui/layout/main/main.component.ts":
/*!**************************************************!*\
  !*** ./src/app/ui/layout/main/main.component.ts ***!
  \**************************************************/
/*! exports provided: MainComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MainComponent", function() { return MainComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _service_http_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../service/http.service */ "./src/app/service/http.service.ts");
/* harmony import */ var _dto_wish__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../dto/wish */ "./src/app/dto/wish.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm5/index.js");
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm5/operators/index.js");
/* harmony import */ var _dto_salary__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../../../dto/salary */ "./src/app/dto/salary.ts");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _service_common_service__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ../../../service/common.service */ "./src/app/service/common.service.ts");
/* harmony import */ var _service_message_code__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ../../../service/message.code */ "./src/app/service/message.code.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ../../../../environments/environment */ "./src/environments/environment.ts");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _dto_ChangeWishMonthOrderDto__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! ../../../dto/ChangeWishMonthOrderDto */ "./src/app/dto/ChangeWishMonthOrderDto.ts");














var MainComponent = /** @class */ (function () {
    function MainComponent(commonService, httpService, fb, datePipe) {
        this.commonService = commonService;
        this.httpService = httpService;
        this.fb = fb;
        this.datePipe = datePipe;
        // --------------------------------- URL'ы -------------------------------------
        this.SERVER_URL = _environments_environment__WEBPACK_IMPORTED_MODULE_11__["environment"].serverUrl;
        this.myBaseUrl = this.SERVER_URL + '/rest/wishes'; // todo: переименовать
        this.apiUrl = this.myBaseUrl + '/default'; // все желания. Основная ссылка на api
        this.priorityWishesUrl = this.myBaseUrl + '/priority'; // приоритетные желания
        this.userViewModeUrl = this.SERVER_URL + '/user/mode';
        this.currentUserUrl = this.SERVER_URL + '/user/current';
        this.allWishesUrl = this.myBaseUrl + '/all'; // все желания
        this.apiGetSumm = this.SERVER_URL + '/statistic/sum'; // ссылка для получения сумм
        this.apiSalary = this.SERVER_URL + '/salary'; // ссылка для получения последней зарплаты
        this.parseUrl = this.myBaseUrl + '/parsecsv'; // url для парсинга csv
        this.searchWishUrl = this.myBaseUrl + '/filter'; // url для поиска желаний
        this.groupWishesUrl = this.SERVER_URL + '/month-grouping';
        this.changePriorityUrl = this.myBaseUrl + '/changepriority'; // url для быстрого изменения приоритета
        this.changeMonthOrderUrl = this.groupWishesUrl + '/one-step'; // url для быстрого изменения месяца у желания для помесячной группировки
        // --------------------------------- ПЕРЕМЕННЫЕ -------------------------------------
        this.cryptokey = ''; // пользовательский ключ шифрования
        this.summAll = 0; // отображение сум по всем желаниям
        this.summPriority = 0; // отображение сум по приоритетным желаниям
        this.periodAll = 0; // период реализации для всего
        this.periodPriority = 0; // период реализации для приоритетного
        this.implementationPeriod = ''; // средний период реализации желаний
        this.implemetedSummAllTime = ''; // общая сумма реализованного за все время
        this.implemetedSummMonth = ''; // общая сумма реализованного за текущий месяц
        this.filterMode = false; // период реализации для приоритетного
        this.filterButtonText = 'ПОИСК/ФИЛЬТР'; // период реализации для приоритетного
        this.monthOrdermode = false; // режим отображение дерева группировки по месяцам
        this.isSalaryExists = false;
        // priorityMode = false; // последний запрос по приоритетным желаниям или нет?
        this.lastSalary = 0;
        this.curDateFormated = '';
        this.sortMode = 'По имени'; // глобальный переключатель типов сортировки
        // --------------------------------- ВКЛЮЧЕНИЕ МОДАЛОВ -------------------------------------
        this.isEdit = false; // режим редактирования для отображения / или чтобы спрятать модальное окно
        this.isSalaryAdd = false; // режим добавления зп
        this.isEditMode = false; // редактировать или добавить
        this.isCsvParse = false; // отправить на парсинг csv
        this.isFilterModal = false; // вывести модал фильтрации
        this.isMonthGroupModeWishEdit = false; // вывод формы редактирования желания при помесячной группировке
        this.isSummInfoForm = false; // вывод формы с итоговой информацией (сумма всех желаний, время реализации)
        // --------------------------------- ХРАНИЛИЩА -------------------------------------
        this.wishes = []; // контейнер желаний
        this.wishGroups = []; // контейнер желаний
        this.monthList = []; // контейнер месяцов
        this.filters = ['Все', 'Приоритет', 'Помесячная группировка']; // фильтры
        this.groupMonthSort = ['Без сортировки', 'По имени', 'По сумме [1..10]', 'По сумме [10..1]']; // сортировка помесячной группировки
        this.mainSort = ['Без сортировки', 'По имени', 'По сумме [1..10]', 'По сумме [10..1]', 'По приоритету']; // сортировка помесячной группировки
        this.form = this.fb.group({
            id: ['', []],
            name: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].required,
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].maxLength(160),
                ]],
            description: ['', []],
            url: ['', []],
            priority: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].required,
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].pattern(/^[0-9]+$/)
                ]],
            price: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].required,
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].pattern(/^[0-9]+$/)
                ]],
            creationDate: ['', []]
        });
        this.salaryForm = this.fb.group({
            salary: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].required,
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].pattern(/^[0-9]+$/)
                ]],
            residualSalary: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].required,
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].pattern(/^[0-9]+$/)
                ]]
        });
        this.csvForm = this.fb.group({
            csvfile: ['', []]
        });
        this.filterForm = this.fb.group({
            wish: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].required
                ]]
        });
        this.MonthGroupModeWishEdit = this.fb.group({
            id: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].required
                ]],
            wish: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].required
                ]],
            month: ['', [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].required
                ]]
        });
        this.curDateFormated = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
    }
    MainComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.isUserCrypto = false;
        this.getWishes(this.apiUrl);
        this.uploadForm = this.fb.group({
            profile: ['']
        });
        this.getUserViewMode();
        this.subscription = this.commonService.error$.subscribe(function (error) {
            if (error == null) {
                _this.globalError = new _service_message_code__WEBPACK_IMPORTED_MODULE_10__["MessageCode"]();
                _this.globalError.messageType = 'NO ERRORS';
            }
            else {
                _this.globalError = error;
                _this.isEdit = false;
                _this.isSalaryAdd = false;
                if (_this.globalError.messageType === _this.globalError.AUTH_LOGIN_OK) {
                    _this.getWishes(_this.apiUrl);
                }
                else if (_this.globalError.messageType === _this.globalError.USER_DATA_CHANGE_OK) {
                    _this.isEdit = false;
                    _this.isSalaryAdd = false;
                    _this.isCsvParse = false;
                    _this.result = _this.globalError.USER_DATA_CHANGE_OK;
                    Object(rxjs__WEBPACK_IMPORTED_MODULE_5__["timer"])(4000).subscribe(function () {
                        _this.result = null;
                    });
                }
                else {
                    _this.error = error.messageType;
                    Object(rxjs__WEBPACK_IMPORTED_MODULE_5__["timer"])(4000).subscribe(function () {
                        _this.error = null;
                    });
                }
            }
        });
        // Закрываем пункт меню группировки по месяцам если нет зарплат
        console.log('this.isSalaryExists - > ', this.isSalaryExists);
        if (this.isSalaryExists) {
            this.filters = ['Все', 'Приоритет', 'Помесячная группировка']; // фильтры
        }
        else {
            this.filters = ['Все', 'Приоритет']; // фильтры
        }
        // Проверка ключа шифрования
        this.cryptokey = localStorage.getItem('cryptokey');
        if ((this.isUserCrypto) && (!this.cryptokey)) {
            this.error = 'Мы не смогли забрать с куки ваш ключ шифрования и при этом у вас включена эта настройка. ' +
                'Задайте ключ шифрования меню О пользователе';
            Object(rxjs__WEBPACK_IMPORTED_MODULE_5__["timer"])(4000).subscribe(function () {
                _this.error = null;
            });
        }
    };
    MainComponent.prototype.getUserViewMode = function () {
        var _this = this;
        this.httpService.getData(this.currentUserUrl).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно получить настройки пользовательского отображения!');
        })).subscribe(function (data) {
            console.log('data.viewMode => ' + data.viewMode);
            if (data.viewMode === 'TREE') {
                _this.monthOrdermode = true;
                _this.getWishesWithMonthGroupping('?sortType=all'); // todo - это конечно полный пиздец!!!!!!
            }
            else {
                _this.monthOrdermode = false;
            }
        });
    };
    MainComponent.prototype.setUserViewMode = function (mode) {
        var _this = this;
        this.httpService.toggleUserViewMode(this.userViewModeUrl + '?mode=' + mode).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно получить настройки пользовательского отображения!');
        })).subscribe(function (data) {
            console.log('data.viewMode => ' + data.viewMode);
        });
    };
    MainComponent.prototype.getWishesWithMonthGroupping = function (sorting) {
        var _this = this;
        this.httpService.getData(this.groupWishesUrl + sorting).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно получить желания!');
        })).subscribe(function (data) {
            _this.wishGroups = data.list;
            _this.monthOrdermode = true;
            _this.isCrypto();
            if (_this.isUserCrypto) {
                console.log('decrypt-mode');
                _this.decryptOrderedWishes();
            }
        });
    };
    // Вытащить список месяцев и лет, пришедший с бека
    MainComponent.prototype.getMonths = function () {
        var _this = this;
        this.monthList.length = 0;
        this.wishGroups.forEach(function (element) {
            _this.monthList.push(element.monthName + ' ' + element.year);
        });
    };
    /**
     * Применить изменение (месячного) порядка для желания.
     */
    MainComponent.prototype.applyMonthChange4Wish = function () {
        var _this = this;
        var moveWish = new _dto_ChangeWishMonthOrderDto__WEBPACK_IMPORTED_MODULE_13__["ChangeWishMonthOrderDto"](this.MonthGroupModeWishEdit.value.id);
        moveWish.setMonth(this.MonthGroupModeWishEdit.value.month);
        this.httpService.changeMonthOrder(moveWish, this.groupWishesUrl).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно изменить приоритет!');
        })).subscribe(function (res) {
            console.log(res);
            _this.showAlert('Приоритет успешно изменен! ', 'ADD MODE', res);
            _this.getWishes(_this.apiUrl);
            _this.getWishesWithMonthGroupping('?sortType=all');
            _this.isMonthGroupModeWishEdit = false;
        });
    };
    // Редактирование желания при помесячной группировке
    MainComponent.prototype.editMonthGroupItem = function (items) {
        this.getMonths();
        console.log(items.wish);
        this.isMonthGroupModeWishEdit = true;
        this.MonthGroupModeWishEdit.patchValue({
            id: items.id,
            wish: items.wish
        });
    };
    // Изменить сортировку помесячной группировки
    MainComponent.prototype.sortGroupList = function (item) {
        if (item === 'По имени') {
            /*this.wishGroups.forEach((element) => {
              element.wishList.sort((a, b): number => {
               // localStorage.setItem('monthGroupSort', 'NAME');
                if (a.wish < b.wish) return -1;
                if (a.wish > b.wish) return 1;
                return 0;
              });
            });*/
            this.getWishesWithMonthGroupping('?sortType=name');
        }
        else if (item === 'По сумме [1..10]') {
            this.getWishesWithMonthGroupping('?sortType=price-asc');
            /*  this.wishGroups.forEach((element) => {
                element.wishList.sort((a, b): number => {
               //   localStorage.setItem('monthGroupSort', 'PRICE-ASC');
                  if (a.price < b.price) return -1;
                  if (a.price > b.price) return 1;
                  return 0;
                });
              });*/
        }
        else if (item === 'Без сортировки') {
            this.getWishesWithMonthGroupping('?sortType=all');
        }
        else {
            this.getWishesWithMonthGroupping('?sortType=price-desc');
            /*this.wishGroups.forEach((element) => {
              element.wishList.sort((a, b): number => {
            //    localStorage.setItem('monthGroupSort', 'PRICE-DESC');
                return b.price - a.price;
              });
            });*/
        }
    };
    // Идем на бэк, чтобы там изменить сортировку
    MainComponent.prototype.sortMainListOnServer = function (sortType) {
        var _this = this;
        this.httpService.getData(this.userViewModeUrl + '/mainsort?mode=' + sortType).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Не удалось изменить тип сортировки!');
        })).subscribe(function (data) {
            console.log('Теперь режим сортировки => ' + data.sortMainMode);
        });
    };
    /**
     * Изменить сортировку основной таблицы.
     * @param item - тип сортировки.
     */
    MainComponent.prototype.sortMainList = function (item) {
        if (item === 'По имени') {
            console.log('Сортировка по имени - name');
            this.getWishesWithSort(this.apiUrl, 'NAME');
        }
        else if (item === 'По сумме [1..10]') {
            this.getWishesWithSort(this.apiUrl, 'PRICE_ASC');
            console.log('Сортировка по стоимости - price-asc');
        }
        else if (item === 'По сумме [10..1]') {
            this.getWishesWithSort(this.apiUrl, 'PRICE_DESC');
            console.log('Сортировка по стоимости - price-desc');
        }
        else if (item === 'По приоритету') {
            this.getWishesWithSort(this.apiUrl, 'PRIORITY');
            console.log('Сортировка по приоритету');
        }
        else { // Без сортировки
            this.getWishesWithSort(this.apiUrl, 'ALL');
            console.log('Без сортировки');
        }
    };
    MainComponent.prototype.changeFilter = function (item) {
        if (item === 'Все') {
            this.getWishes(this.allWishesUrl);
        }
        else if (item === 'Помесячная группировка') {
            // Выключаем фильтр
            this.filterMode = false;
            this.filterButtonText = 'ПОИСК/ФИЛЬТР';
            this.getWishesWithMonthGroupping('?sortType=all');
            this.setUserViewMode('TREE');
        }
        else {
            this.getWishes(this.priorityWishesUrl);
        }
    };
    /*up(event: any, item: Wish) {
      item.priority = item.priority + 1;
      this.wishes.sort((a, b) => a.priority - b.priority);
    }
  
  
    down(event: any, item: Wish) {
  
      item.priority = item.priority - 1;
      if (item.priority < 1) {
        item.priority = 1;
      }
      this.wishes.sort((a, b) => a.priority - b.priority);
    }*/
    MainComponent.prototype.isCrypto = function () {
        var _this = this;
        this.httpService.isCryptoUser().pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно получить крипто-статус пользователя!');
        })).subscribe(function (data) {
            _this.isUserCrypto = data.userCryptoMode;
            _this.userRole = data.userRole;
            console.log('crypto -> ' + data.userCryptoMode);
            console.log('userRole -> ' + data.userRole);
        });
    };
    MainComponent.prototype.decryptWishes = function () {
        var _this = this;
        console.log('decrypt method');
        this.wishes.forEach(function (element) {
            element.wish = _this.commonService.convertText('decr', element.wish, _this.cryptokey);
            element.description = _this.commonService.convertText('decr', element.description, _this.cryptokey);
            element.url = _this.commonService.convertText('decr', element.url, _this.cryptokey);
        });
    };
    MainComponent.prototype.decryptOrderedWishes = function () {
        var _this = this;
        console.log('decrypt method for ordered wishes');
        this.wishGroups.forEach(function (month) {
            month.wishList.forEach(function (element) {
                element.wish = _this.commonService.convertText('decr', element.wish, _this.cryptokey);
            });
        });
    };
    // Загрузить все желания в табличном режиме.
    MainComponent.prototype.getWishes = function (url) {
        var _this = this;
        this.isCrypto();
        this.httpService.getData(url).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно получить желания!');
        })).subscribe(function (data) {
            _this.wishes = null;
            _this.wishes = data.list;
            console.log(_this.wishes);
            if (_this.isUserCrypto) {
                console.log('decrypt-mode');
                _this.decryptWishes();
            }
            console.log('SORTING:' + _this.sortMode);
        });
        this.httpService.getData(this.apiGetSumm).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно посчитать итоговые стоимости!');
        })).subscribe(function (data) {
            _this.summAll = data.all;
            _this.summPriority = data.priority;
            _this.periodAll = data.allPeriodForImplementation;
            _this.periodPriority = data.priorityPeriodForImplementation;
            _this.implementationPeriod = data.averageImplementationTime;
            _this.implemetedSummAllTime = data.implemetedSummAllTime;
            _this.implemetedSummMonth = data.implemetedSummMonth;
            _this.isSalaryExists = true;
            _this.lastSalary = data.lastSalary;
            _this.filters = ['Все', 'Приоритет', 'Помесячная группировка'];
            console.log('Sal: ' + data.lastSalary);
        });
    };
    MainComponent.prototype.getWishesWithSort = function (url, sortType) {
        var _this = this;
        this.isCrypto();
        url = url + '?changeSortType=' + sortType;
        this.httpService.getData(url).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно получить желания!');
        })).subscribe(function (data) {
            _this.wishes = data.list;
            console.log(_this.wishes);
            if (_this.isUserCrypto) {
                console.log('decrypt-mode');
                _this.decryptWishes();
            }
            _this.sortMode = sortType;
            console.log('SORTING:' + _this.sortMode);
            // console.log('FILTERING:' + this.apiUrl);
        });
        this.httpService.getData(this.apiGetSumm).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно посчитать итоговые стоимости!');
        })).subscribe(function (data) {
            _this.summAll = data.all;
            _this.summPriority = data.priority;
            _this.periodAll = data.allPeriodForImplementation;
            _this.periodPriority = data.priorityPeriodForImplementation;
            _this.implementationPeriod = data.averageImplementationTime;
            _this.implemetedSummAllTime = data.implemetedSummAllTime;
            _this.implemetedSummMonth = data.implemetedSummMonth;
            _this.isSalaryExists = true;
            _this.lastSalary = data.lastSalary;
            _this.filters = ['Все', 'Приоритет', 'Помесячная группировка'];
            console.log('Sal: ' + data.lastSalary);
        });
    };
    MainComponent.prototype.deleteWish = function () {
        var _this = this;
        this.httpService.deleteWish(this.form.value.id, this.myBaseUrl).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно удалить желание!');
        }))
            .subscribe(function (res) {
            _this.showAlert('Желание с id [' + _this.form.value.id + '] успешно удалено!', 'ADD MODE', res);
        });
    };
    MainComponent.prototype.toMainTableMode = function () {
        this.monthOrdermode = false;
        this.setUserViewMode('TABLE');
    };
    MainComponent.prototype.errorHandler = function (err, message) {
        var _this = this;
        this.isEdit = false;
        this.isSalaryAdd = false;
        console.log('error - ' + err.error);
        if (err.error === 'ERR-01') {
            this.error = 'У вас нет сохраненных зарплат! Невозможно посчитать сроки реализации! Добавьте хотя бы одну зарплату!';
            this.isSalaryExists = false;
            this.filters = ['Все', 'Приоритет'];
        }
        else if (err.error === 'ERR-02') {
            this.error = 'У вас нет сохраненных желаний! Добавьте хотя бы одно желание!';
            this.isSalaryExists = false;
            this.filters = ['Все', 'Приоритет'];
        }
        else {
            this.error = message;
        }
        console.log(err);
        Object(rxjs__WEBPACK_IMPORTED_MODULE_5__["timer"])(4000).subscribe(function () {
            _this.error = null;
        });
        return Object(rxjs__WEBPACK_IMPORTED_MODULE_5__["throwError"])(err);
    };
    /**
     * Обновить желания.
     */
    MainComponent.prototype.updateWishTable = function () {
        // Выключаем фильтр
        this.filterMode = false;
        this.filterButtonText = 'ПОИСК/ФИЛЬТР';
        /*this.wishes = this.wishes.filter(
          wish => wish.wish.toLowerCase().includes(''));*/
        this.getWishes(this.apiUrl);
    };
    MainComponent.prototype.openEditWish = function (event, item, isedit) {
        var _this = this;
        // Выключаем фильтр
        this.filterMode = false;
        this.filterButtonText = 'ПОИСК/ФИЛЬТР';
        // Если это юзер с шифрованием на фронте и при этом у него не задан ключ
        if ((!this.cryptokey) && (this.isUserCrypto)) {
            this.error = 'Задайте ключ шифрование в меню О пользователе. ' +
                'Без этого при включенном режиме шифрования пользовательских данных мы не можем добавить желание!';
            Object(rxjs__WEBPACK_IMPORTED_MODULE_5__["timer"])(4000).subscribe(function () {
                _this.error = null;
            });
        }
        else {
            if (isedit === 1) {
                this.isEdit = true;
                this.isEditMode = true;
                this.form.patchValue({
                    id: item.id,
                    name: item.wish,
                    description: item.description,
                    url: item.url,
                    priority: item.priority,
                    price: item.price,
                    creationDate: item.creationDate
                });
            }
            else {
                this.isEdit = true;
                this.isEditMode = false;
                this.form.patchValue({
                    id: 1,
                    name: '',
                    description: 'какое-то описание',
                    url: '',
                    priority: 1,
                    price: 0,
                    creationDate: this.curDateFormated
                });
            }
        }
    };
    MainComponent.prototype.onFileSelect = function (event) {
        if (event.target.files.length > 0) {
            var file = event.target.files[0];
            this.uploadForm.get('profile').setValue(file);
        }
    };
    // Отправить файл на парсинг
    MainComponent.prototype.onSubmit = function () {
        var _this = this;
        var formData = new FormData();
        formData.append('csvfile', this.uploadForm.get('profile').value);
        console.log(this.uploadForm.get('profile').value);
        this.httpService.sendFile(formData, this.parseUrl).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно спарсить файл !');
        })).subscribe(function (res) {
            console.log(res);
            _this.showAlert('Парсинг выполнен! Добавлено: ' + res.itemsAdded + ' желаний!', 'PARSE MODE', res);
        });
    };
    /**
     * Открыть диалог выбора csv-файла для парсинга на сервере.
     */
    MainComponent.prototype.openParseCsv = function (event) {
        // Выключаем фильтр
        this.filterMode = false;
        this.filterButtonText = 'ПОИСК/ФИЛЬТР';
        this.isCsvParse = true;
    };
    // Добавить в Мультипар-форму подгруженый csv-файл
    MainComponent.prototype.sendCsvFile = function () {
        var reader = new FileReader();
        var file = this.csvForm.value.csvfile;
        reader.readAsArrayBuffer(file);
        console.log(file.name);
        this.isCsvParse = false;
    };
    MainComponent.prototype.openAddSalaryModal = function (event) {
        // Выключаем фильтр
        this.filterMode = false;
        this.filterButtonText = 'ПОИСК/ФИЛЬТР';
        this.isSalaryAdd = true;
        this.isEditMode = false;
        this.isEditMode = false;
        this.salaryForm.patchValue({
            salary: 1,
            residualSalary: 1
        });
    };
    MainComponent.prototype.showAlert = function (text, mode, result) {
        var _this = this;
        console.log(mode);
        console.log(result);
        this.isEdit = false;
        this.isSalaryAdd = false;
        this.isCsvParse = false;
        this.result = text;
        Object(rxjs__WEBPACK_IMPORTED_MODULE_5__["timer"])(4000).subscribe(function () {
            _this.result = null;
        });
    };
    MainComponent.prototype.addSalary = function () {
        var _this = this;
        var salary = new _dto_salary__WEBPACK_IMPORTED_MODULE_7__["Salary"](this.salaryForm.value.salary, this.salaryForm.value.residualSalary);
        console.log(salary);
        this.httpService.sendSalary(salary, this.apiSalary).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно добавить зарплату!');
        })).subscribe(function (hero) {
            _this.showAlert('Зарплата успешно обновлена!', 'ADD MODE', hero);
            _this.getWishes(_this.apiUrl);
        });
    };
    MainComponent.prototype.realizeWish = function () {
        var _this = this;
        var wish = new _dto_wish__WEBPACK_IMPORTED_MODULE_3__["Wish"](this.form.value.id, this.form.value.name, this.form.value.price, this.form.value.priority, false, this.form.value.description, this.form.value.url, true);
        if (!this.cryptokey) {
            console.log('cryptokey is null. Try to fix it');
            this.cryptokey = localStorage.getItem('cryptokey');
        }
        if (this.isUserCrypto) {
            wish.wish = this.commonService.convertText('encrypt', wish.wish, this.cryptokey);
            wish.description = this.commonService.convertText('encrypt', wish.description, this.cryptokey);
            wish.url = this.commonService.convertText('encrypt', wish.url, this.cryptokey);
            console.log('encrypted wish', wish.wish);
        }
        this.httpService.updateWish(wish, this.myBaseUrl).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно обновить желание!');
        })).subscribe(function (hero) {
            _this.showAlert('Желание с id [' + wish.id + '] успешно обновлено!', 'ADD MODE', hero);
            _this.getWishes(_this.apiUrl);
        });
    };
    MainComponent.prototype.addEditWish = function () {
        var _this = this;
        var wish = new _dto_wish__WEBPACK_IMPORTED_MODULE_3__["Wish"](this.form.value.id, this.form.value.name, this.form.value.price, this.form.value.priority, false, this.form.value.description, this.form.value.url, false);
        if (!this.cryptokey) {
            console.log('cryptokey is null. Try to fix it');
            this.cryptokey = localStorage.getItem('cryptokey');
        }
        if (this.isUserCrypto) {
            wish.wish = this.commonService.convertText('encrypt', wish.wish, this.cryptokey);
            wish.description = this.commonService.convertText('encrypt', wish.description, this.cryptokey);
            wish.url = this.commonService.convertText('encrypt', wish.url, this.cryptokey);
            console.log('encrypted wish', wish.wish);
        }
        if (this.isEditMode) {
            this.httpService.updateWish(wish, this.myBaseUrl).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
                return _this.errorHandler(err, 'Невозможно обновить желание!');
            })).subscribe(function (hero) {
                _this.showAlert('Желание с id [' + wish.id + '] успешно обновлено!', 'ADD MODE', hero);
                _this.getWishes(_this.apiUrl);
            });
        }
        else {
            this.httpService.sendData(wish, this.myBaseUrl).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
                return _this.errorHandler(err, 'Невозможно добавить желание!');
            })).subscribe(function (hero) {
                _this.showAlert('Желание успешно добавлено!', 'ADD MODE', hero);
                _this.getWishes(_this.apiUrl);
            });
        }
    };
    MainComponent.prototype.changePriority = function (item, move) {
        var _this = this;
        console.log('change priority');
        console.log('URL ->' + this.changePriorityUrl + '/' + item.id + '/' + move);
        this.httpService.getData(this.changePriorityUrl + '/' + item.id + '/' + move).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно изменить приоритет!');
        })).subscribe(function (res) {
            console.log(res);
            _this.showAlert('Приоритет успешно изменен на ' + res.priority, 'ADD MODE', res);
            _this.getWishes(_this.apiUrl);
        });
    };
    MainComponent.prototype.changePriorityMonth = function (item, move) {
        var _this = this;
        console.log('Меняем месяц для желания с ID: ', item);
        var moveWish = new _dto_ChangeWishMonthOrderDto__WEBPACK_IMPORTED_MODULE_13__["ChangeWishMonthOrderDto"](item.id);
        moveWish.setStep(move);
        this.httpService.changeMonthOrder(moveWish, this.changeMonthOrderUrl).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Невозможно изменить приоритет!');
        })).subscribe(function (res) {
            console.log(res);
            _this.showAlert('Приоритет успешно изменен! ', 'ADD MODE', res);
            _this.getWishes(_this.apiUrl);
            _this.getWishesWithMonthGroupping('?sortType=all');
        });
    };
    // Показать окно включения/выключения фильтров
    MainComponent.prototype.filterWishes = function () {
        if (!this.filterMode) {
            this.isFilterModal = true;
        }
        else {
            // this.wishes = null;
            this.getWishes(this.apiUrl);
            this.filterMode = false;
            this.filterButtonText = 'ПОИСК/ФИЛЬТР';
            console.log('WISHES: ', this.wishes);
        }
    };
    // Показать окно c итогами: стоимость всех желаний, время реализации и все такое
    MainComponent.prototype.summInfo = function () {
        this.isSummInfoForm = true;
    };
    // ЛОГИН и АВТОРИЗАЦИЯ
    MainComponent.prototype.login = function (login, pwd) {
        var body = new _angular_common_http__WEBPACK_IMPORTED_MODULE_8__["HttpParams"]()
            .set('username', login)
            .set('password', pwd);
        this.httpService.login(body.toString())
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["tap"])(function (resp) {
            console.log('header', resp.headers.get('Authorization'));
            sessionStorage.setItem('token', resp.headers.get('Authorization'));
            localStorage.setItem('token', resp.headers.get('Authorization'));
            console.log('storage', localStorage.getItem('token'));
        }))
            .subscribe();
    };
    MainComponent.prototype.applyFilter = function () {
        var _this = this;
        this.isFilterModal = false;
        this.filterMode = true; // включаем filtermode
        this.filterButtonText = 'ВЫКЛЮЧИТЬ ФИЛЬТР'; // период реализации для приоритетного
        this.httpService.getData(this.searchWishUrl + '?keyword=' + this.filterForm.value.wish.toLowerCase()).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["catchError"])(function (err) {
            return _this.errorHandler(err, 'Ошибка поиска!');
        })).subscribe(function (data) {
            _this.wishes = null;
            _this.wishes = data.list;
            console.log(_this.wishes);
            if (_this.isUserCrypto) {
                console.log('decrypt-mode');
                _this.decryptWishes();
            }
        });
        /*this.wishes = this.wishes.filter(
          wish => wish.wish.toLowerCase().includes(this.filterForm.value.wish.toLowerCase()));*/
    };
    MainComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-main',
            template: __webpack_require__(/*! ./main.component.html */ "./src/app/ui/layout/main/main.component.html"),
            providers: [_service_http_service__WEBPACK_IMPORTED_MODULE_2__["HttpService"], _angular_common__WEBPACK_IMPORTED_MODULE_12__["DatePipe"]],
            styles: [__webpack_require__(/*! ./main.component.css */ "./src/app/ui/layout/main/main.component.css")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_service_common_service__WEBPACK_IMPORTED_MODULE_9__["CommonService"], _service_http_service__WEBPACK_IMPORTED_MODULE_2__["HttpService"], _angular_forms__WEBPACK_IMPORTED_MODULE_4__["FormBuilder"], _angular_common__WEBPACK_IMPORTED_MODULE_12__["DatePipe"]])
    ], MainComponent);
    return MainComponent;
}());



/***/ }),

/***/ "./src/app/ui/layout/session-timeout-modal/session-timeout-modal.component.css":
/*!*************************************************************************************!*\
  !*** ./src/app/ui/layout/session-timeout-modal/session-timeout-modal.component.css ***!
  \*************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3VpL2xheW91dC9zZXNzaW9uLXRpbWVvdXQtbW9kYWwvc2Vzc2lvbi10aW1lb3V0LW1vZGFsLmNvbXBvbmVudC5jc3MifQ== */"

/***/ }),

/***/ "./src/app/ui/layout/session-timeout-modal/session-timeout-modal.component.html":
/*!**************************************************************************************!*\
  !*** ./src/app/ui/layout/session-timeout-modal/session-timeout-modal.component.html ***!
  \**************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<clr-modal [clrModalOpen]=\"userInactive | async\" [clrModalClosable]=\"false\" class=\"session-timeout-modal\">\n  <h3 class=\"modal-title\">\n    Сессия истекла\n  </h3>\n  <div class=\"modal-body\">\n    <p>\n      Ваша сессия истекла! Необходимо войти еще раз!\n    </p>\n  </div>\n  <div class=\"modal-footer\">\n    <button type=\"button\" class=\"btn btn-primary\" (click)=\"logout()\">\n      Продолжить\n    </button>\n  </div>\n\n</clr-modal>\n"

/***/ }),

/***/ "./src/app/ui/layout/session-timeout-modal/session-timeout-modal.component.ts":
/*!************************************************************************************!*\
  !*** ./src/app/ui/layout/session-timeout-modal/session-timeout-modal.component.ts ***!
  \************************************************************************************/
/*! exports provided: SessionTimeoutModalComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SessionTimeoutModalComponent", function() { return SessionTimeoutModalComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm5/index.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm5/operators/index.js");
/* harmony import */ var _service_message_code__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ../../../service/message.code */ "./src/app/service/message.code.ts");
/* harmony import */ var _service_common_service__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../../../service/common.service */ "./src/app/service/common.service.ts");







var SessionTimeoutModalComponent = /** @class */ (function () {
    function SessionTimeoutModalComponent(router, commonService, cdr) {
        var _this = this;
        this.router = router;
        this.commonService = commonService;
        this.cdr = cdr;
        this.sessionTimeout = 864000;
        // sessionTimeout: number = 3;
        this.userInactive = new rxjs__WEBPACK_IMPORTED_MODULE_2__["BehaviorSubject"](false);
        this.consoleWatcher = '';
        this.router.events
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["filter"])(function (e) { return e instanceof _angular_router__WEBPACK_IMPORTED_MODULE_3__["NavigationEnd"]; }))
            .subscribe(function () {
            clearTimeout(_this.userActivity);
            _this.setTimeout();
        });
    }
    SessionTimeoutModalComponent.prototype.setTimeout = function () {
        var _this = this;
        if (this.router.url.indexOf('/401') === -1) { // отключаем проверку сессии для 401-й странички
            this.userActivity = setTimeout(function () { return _this.userInactive.next(true); }, this.sessionTimeout * 1000);
        }
    };
    SessionTimeoutModalComponent.prototype.refreshUserState = function () {
        clearTimeout(this.userActivity);
        this.setTimeout();
    };
    SessionTimeoutModalComponent.prototype.logout = function () {
        clearTimeout(this.userActivity);
        localStorage.removeItem('token');
        this.router.navigate(['401']);
        clearTimeout(this.userActivity);
        this.userInactive.next(false);
        this.cdr.markForCheck();
        var errorType = new _service_message_code__WEBPACK_IMPORTED_MODULE_5__["MessageCode"]();
        this.sendMessagePush();
    };
    SessionTimeoutModalComponent.prototype.sendMessagePush = function () {
        var errorType = new _service_message_code__WEBPACK_IMPORTED_MODULE_5__["MessageCode"]();
        errorType.messageType = errorType.SESSION_EXPIRED;
        this.commonService.pushError(errorType);
    };
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["HostListener"])('window:mousemove'),
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["HostListener"])('window:click'),
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["HostListener"])('window:keyup'),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Function),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", []),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:returntype", void 0)
    ], SessionTimeoutModalComponent.prototype, "refreshUserState", null);
    SessionTimeoutModalComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-session-timeout-modal',
            template: __webpack_require__(/*! ./session-timeout-modal.component.html */ "./src/app/ui/layout/session-timeout-modal/session-timeout-modal.component.html"),
            changeDetection: _angular_core__WEBPACK_IMPORTED_MODULE_1__["ChangeDetectionStrategy"].OnPush,
            styles: [__webpack_require__(/*! ./session-timeout-modal.component.css */ "./src/app/ui/layout/session-timeout-modal/session-timeout-modal.component.css")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_3__["Router"],
            _service_common_service__WEBPACK_IMPORTED_MODULE_6__["CommonService"],
            _angular_core__WEBPACK_IMPORTED_MODULE_1__["ChangeDetectorRef"]])
    ], SessionTimeoutModalComponent);
    return SessionTimeoutModalComponent;
}());



/***/ }),

/***/ "./src/app/ui/layout/sidebar/sidebar.component.ts":
/*!********************************************************!*\
  !*** ./src/app/ui/layout/sidebar/sidebar.component.ts ***!
  \********************************************************/
/*! exports provided: SidebarComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SidebarComponent", function() { return SidebarComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");


var SidebarComponent = /** @class */ (function () {
    function SidebarComponent() {
    }
    SidebarComponent.prototype.ngOnInit = function () {
        var idToken = localStorage.getItem('token');
    };
    SidebarComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-sidebar',
            template: "\n    <!--<div class=\"content-container\">-->\n      <!--<div class=\"content-area\">-->\n      <!--</div>-->\n      <clr-vertical-nav [clr-nav-level]=\"1\">\n        <a clrVerticalNavLink routerLink=\"../401\" routerLinkActive=\"active\">\u041E \u043F\u0440\u043E\u0435\u043A\u0442\u0435</a>\n        <a *ngIf=\"idToken\" clrVerticalNavLink routerLink=\"../\" routerLinkActive=\"active\">\u0413\u043B\u0430\u0432\u043D\u0430\u044F</a>\n      </clr-vertical-nav>\n  "
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [])
    ], SidebarComponent);
    return SidebarComponent;
}());



/***/ }),

/***/ "./src/app/ui/layout/unauthorize/unauthorize.component.css":
/*!*****************************************************************!*\
  !*** ./src/app/ui/layout/unauthorize/unauthorize.component.css ***!
  \*****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "@media screen and (max-width: 500px){\n\n  .sidenav {\n    min-width: 1%;\n    visibility: hidden;\n    /*background: #ea1e2c;*/\n  }\n}\n\n:host{\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvdWkvbGF5b3V0L3VuYXV0aG9yaXplL3VuYXV0aG9yaXplLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7O0VBRUU7SUFDRSxhQUFhO0lBQ2Isa0JBQWtCO0lBQ2xCLHVCQUF1QjtFQUN6QjtBQUNGOztBQUVBO0FBQ0EiLCJmaWxlIjoic3JjL2FwcC91aS9sYXlvdXQvdW5hdXRob3JpemUvdW5hdXRob3JpemUuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbIkBtZWRpYSBzY3JlZW4gYW5kIChtYXgtd2lkdGg6IDUwMHB4KXtcblxuICAuc2lkZW5hdiB7XG4gICAgbWluLXdpZHRoOiAxJTtcbiAgICB2aXNpYmlsaXR5OiBoaWRkZW47XG4gICAgLypiYWNrZ3JvdW5kOiAjZWExZTJjOyovXG4gIH1cbn1cblxuOmhvc3R7XG59XG4iXX0= */"

/***/ }),

/***/ "./src/app/ui/layout/unauthorize/unauthorize.component.html":
/*!******************************************************************!*\
  !*** ./src/app/ui/layout/unauthorize/unauthorize.component.html ***!
  \******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"content-container\">\n  <div class=\"content-area\">\n\n    <clr-alert [clrAlertType]=\"'success'\" *ngIf=\"result\">\n      <clr-alert-item>\n        <span class=\"alert-text\">\n            {{result}}\n        </span>\n      </clr-alert-item>\n    </clr-alert>\n\n    <clr-alert [clrAlertType]=\"'danger'\" *ngIf=\"error\">\n      <clr-alert-item>\n        <span class=\"alert-text\">\n            {{error}}\n        </span>\n      </clr-alert-item>\n    </clr-alert>\n\n\n    <h1>Дорогой друг!</h1>\n    <p>\n      Сейчас ты находишься на страничке проекта arNote. Это сервис хранения желаний, позволяющий считать сроки реализации, различным образом категорировать и управлять вашими желаниями. Для работы с сервисом необходимо получить логин и пароль от автора сервиса, в будущем будет организована свободная регистрация.\n    </p>\n    <p>\n      Одни из Важных преимуществ сервиса планируется сделать шифрование данных на стороне клиента, что обеспечит полную конфиденциальность. То есть администратор / автор сервиса не сможет прочитать Ваши данные ни при каких обстоятельствах, так как все Ваши записи будет шифрованы по уникальному ключу, который знаете только Вы.\n    </p>\n    <p>\n      По всем вопросам просьба писать автору на <i>antonromanov@list.ru.</i>\n    </p>\n\n  </div>\n  <!--<app-sidebar class=\"sidenav\"></app-sidebar>-->\n</div>\n"

/***/ }),

/***/ "./src/app/ui/layout/unauthorize/unauthorize.component.ts":
/*!****************************************************************!*\
  !*** ./src/app/ui/layout/unauthorize/unauthorize.component.ts ***!
  \****************************************************************/
/*! exports provided: UnauthorizeComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UnauthorizeComponent", function() { return UnauthorizeComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _service_common_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../service/common.service */ "./src/app/service/common.service.ts");
/* harmony import */ var _service_message_code__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../service/message.code */ "./src/app/service/message.code.ts");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm5/index.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");






var UnauthorizeComponent = /** @class */ (function () {
    function UnauthorizeComponent(commonService, route) {
        this.commonService = commonService;
        this.route = route;
    }
    UnauthorizeComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.queryParams.subscribe(function (params) {
            var date = params['startdate'];
            console.log(date);
        });
        this.subscription = this.commonService.error$.subscribe(function (error) {
            if (error == null) {
                _this.globalError = new _service_message_code__WEBPACK_IMPORTED_MODULE_3__["MessageCode"]();
                _this.globalError.messageType = 'NO ERRORS';
            }
            else {
                _this.globalError = error;
                if (_this.globalError.messageType === _this.globalError.AUTH_LOGIN_OK) {
                }
                else if (_this.globalError.messageType === _this.globalError.REGISTER_OK) {
                    _this.result = _this.globalError.REGISTER_OK;
                    Object(rxjs__WEBPACK_IMPORTED_MODULE_4__["timer"])(4000).subscribe(function () {
                        _this.result = null;
                    });
                }
                else {
                    if (_this.globalError.messageType !== _this.globalError.SESSION_EXPIRED) {
                        _this.error = error.messageType;
                        Object(rxjs__WEBPACK_IMPORTED_MODULE_4__["timer"])(4000).subscribe(function () {
                            _this.error = null;
                        });
                    }
                }
            }
        });
    };
    UnauthorizeComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-unauthorize',
            template: __webpack_require__(/*! ./unauthorize.component.html */ "./src/app/ui/layout/unauthorize/unauthorize.component.html"),
            styles: [__webpack_require__(/*! ./unauthorize.component.css */ "./src/app/ui/layout/unauthorize/unauthorize.component.css")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_service_common_service__WEBPACK_IMPORTED_MODULE_2__["CommonService"], _angular_router__WEBPACK_IMPORTED_MODULE_5__["ActivatedRoute"]])
    ], UnauthorizeComponent);
    return UnauthorizeComponent;
}());



/***/ }),

/***/ "./src/app/ui/ui.module.ts":
/*!*********************************!*\
  !*** ./src/app/ui/ui.module.ts ***!
  \*********************************/
/*! exports provided: UiModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UiModule", function() { return UiModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
/* harmony import */ var _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/platform-browser/animations */ "./node_modules/@angular/platform-browser/fesm5/animations.js");
/* harmony import */ var _layout_layout_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./layout/layout.component */ "./src/app/ui/layout/layout.component.ts");
/* harmony import */ var _layout_header_header_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./layout/header/header.component */ "./src/app/ui/layout/header/header.component.ts");
/* harmony import */ var _layout_sidebar_sidebar_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./layout/sidebar/sidebar.component */ "./src/app/ui/layout/sidebar/sidebar.component.ts");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _clr_angular__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @clr/angular */ "./node_modules/@clr/angular/fesm5/clr-angular.js");
/* harmony import */ var _layout_main_main_component__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ./layout/main/main.component */ "./src/app/ui/layout/main/main.component.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _layout_edit_edit_component__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./layout/edit/edit.component */ "./src/app/ui/layout/edit/edit.component.ts");
/* harmony import */ var _service_basicauthhtppInterceptorservice__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! ../service/basicauthhtppInterceptorservice */ "./src/app/service/basicauthhtppInterceptorservice.ts");
/* harmony import */ var _app_routing_app_routing_module__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! ../app-routing/app-routing.module */ "./src/app/app-routing/app-routing.module.ts");
/* harmony import */ var _layout_unauthorize_unauthorize_component__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! ./layout/unauthorize/unauthorize.component */ "./src/app/ui/layout/unauthorize/unauthorize.component.ts");
/* harmony import */ var _auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! @auth0/angular-jwt */ "./node_modules/@auth0/angular-jwt/index.js");
/* harmony import */ var _layout_session_timeout_modal_session_timeout_modal_component__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! ./layout/session-timeout-modal/session-timeout-modal.component */ "./src/app/ui/layout/session-timeout-modal/session-timeout-modal.component.ts");


















var UiModule = /** @class */ (function () {
    function UiModule() {
    }
    UiModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            declarations: [_layout_layout_component__WEBPACK_IMPORTED_MODULE_5__["LayoutComponent"], _layout_header_header_component__WEBPACK_IMPORTED_MODULE_6__["HeaderComponent"], _layout_sidebar_sidebar_component__WEBPACK_IMPORTED_MODULE_7__["SidebarComponent"], _layout_main_main_component__WEBPACK_IMPORTED_MODULE_10__["MainComponent"], _layout_edit_edit_component__WEBPACK_IMPORTED_MODULE_12__["EditComponent"], _layout_unauthorize_unauthorize_component__WEBPACK_IMPORTED_MODULE_15__["UnauthorizeComponent"], _layout_session_timeout_modal_session_timeout_modal_component__WEBPACK_IMPORTED_MODULE_17__["SessionTimeoutModalComponent"]],
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_2__["CommonModule"],
                _angular_platform_browser__WEBPACK_IMPORTED_MODULE_3__["BrowserModule"],
                _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_4__["BrowserAnimationsModule"],
                _angular_common_http__WEBPACK_IMPORTED_MODULE_8__["HttpClientModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_11__["FormsModule"],
                _app_routing_app_routing_module__WEBPACK_IMPORTED_MODULE_14__["AppRoutingModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_11__["ReactiveFormsModule"],
                _clr_angular__WEBPACK_IMPORTED_MODULE_9__["ClarityModule"]
            ],
            providers: [
                { provide: _angular_common_http__WEBPACK_IMPORTED_MODULE_8__["HTTP_INTERCEPTORS"], useClass: _service_basicauthhtppInterceptorservice__WEBPACK_IMPORTED_MODULE_13__["BasicAuthHtppInterceptorService"], multi: true },
                { provide: _auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_16__["JWT_OPTIONS"], useValue: _auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_16__["JWT_OPTIONS"] },
                _auth0_angular_jwt__WEBPACK_IMPORTED_MODULE_16__["JwtHelperService"]
            ],
            exports: [_layout_layout_component__WEBPACK_IMPORTED_MODULE_5__["LayoutComponent"]]
        })
    ], UiModule);
    return UiModule;
}());



/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
var environment = {
    production: false,
    serverUrl: 'http://localhost:8080'
};


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ "./node_modules/@angular/platform-browser-dynamic/fesm5/platform-browser-dynamic.js");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");




if (_environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["enableProdMode"])();
}
Object(_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__["platformBrowserDynamic"])().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(function (err) { return console.error(err); });


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! /Users/a18502648/IdeaProjects/arNoteUI/src/main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main.js.map