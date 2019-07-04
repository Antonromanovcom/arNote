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

module.exports = "<app-layout>\n  <h1>hbh {{title}}</h1>\n</app-layout>\n"

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
/* harmony import */ var _clr_angular__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @clr/angular */ "./node_modules/@clr/angular/fesm5/clr-angular.js");
/* harmony import */ var _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/platform-browser/animations */ "./node_modules/@angular/platform-browser/fesm5/animations.js");







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
                _clr_angular__WEBPACK_IMPORTED_MODULE_5__["ClarityModule"],
                _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_6__["BrowserAnimationsModule"],
                _ui_ui_module__WEBPACK_IMPORTED_MODULE_4__["UiModule"]
            ],
            providers: [],
            bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_3__["AppComponent"]]
        })
    ], AppModule);
    return AppModule;
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



var HttpService = /** @class */ (function () {
    function HttpService(http) {
        this.http = http;
    }
    HttpService.prototype.getData = function (url) {
        return this.http.get(url);
    };
    HttpService = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"]])
    ], HttpService);
    return HttpService;
}());



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


var HeaderComponent = /** @class */ (function () {
    function HeaderComponent() {
    }
    HeaderComponent.prototype.ngOnInit = function () {
    };
    HeaderComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-header',
            template: "\n    <header class=\"header-1\">\n      <div class=\"branding\">\n        <a class=\"nav-link\">\n          <clr-icon shape=\"shield\"></clr-icon>\n          <span class=\"title\">Angular CLI</span>\n        </a>\n      </div>\n      <div class=\"header-nav\">\n        <a class=\"active nav-link nav-icon\">\n          <clr-icon shape=\"home\"></clr-icon>\n        </a>\n        <a class=\" nav-link nav-icon\">\n          <clr-icon shape=\"cog\"></clr-icon>\n        </a>\n      </div>\n      <form class=\"search\">\n        <label for=\"search_input\">\n          <input id=\"search_input\" type=\"text\" placeholder=\"Search for keywords...\">\n        </label>\n      </form>\n      <div class=\"header-actions\">\n        <clr-dropdown class=\"dropdown bottom-right\">\n          <button class=\"nav-icon\" clrDropdownToggle>\n            <clr-icon shape=\"user\"></clr-icon>\n            <clr-icon shape=\"caret down\"></clr-icon>\n          </button>\n          <div class=\"dropdown-menu\">\n            <a clrDropdownItem>About</a>\n            <a clrDropdownItem>Preferences</a>\n            <a clrDropdownItem>Log out</a>\n          </div>\n        </clr-dropdown>\n      </div>\n    </header>\n    <nav class=\"subnav\">\n      <ul class=\"nav\">\n        <li class=\"nav-item\">\n          <a class=\"nav-link active\" href=\"#\">Dashboard</a>\n        </li>\n        <li class=\"nav-item\">\n          <a class=\"nav-link\" href=\"#\">Projects</a>\n        </li>\n        <li class=\"nav-item\">\n          <a class=\"nav-link\" href=\"#\">Reports</a>\n        </li>\n        <li class=\"nav-item\">\n          <a class=\"nav-link\" href=\"#\">Users</a>\n        </li>\n      </ul>\n    </nav>\n  "
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [])
    ], HeaderComponent);
    return HeaderComponent;
}());



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


var LayoutComponent = /** @class */ (function () {
    function LayoutComponent() {
    }
    LayoutComponent.prototype.ngOnInit = function () {
    };
    LayoutComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-layout',
            template: "\n    <div class=\"main-container\">\n      <app-header></app-header>\n      <app-main>\n        <ng-content></ng-content>\n      </app-main>\n    </div>\n  "
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [])
    ], LayoutComponent);
    return LayoutComponent;
}());



/***/ }),

/***/ "./src/app/ui/layout/main/main.component.html":
/*!****************************************************!*\
  !*** ./src/app/ui/layout/main/main.component.html ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"content-container\">\n  <div class=\"content-area\">\n    <ng-content></ng-content>\n\n    <div class=\"clr-row\">\n    <div class=\"clr-col-12\">\n    <table class=\"table\">\n      <thead>\n      <tr>\n        <th>Желание</th>\n        <th>Цена</th>\n        <th>Приоритет</th>\n        <th>.</th>\n        <th>.</th>\n        <th>Ред.</th>\n      </tr>\n      </thead>\n      <tbody>\n      <tr *ngFor=\"let item of wishes\">\n        <td style=\"width: 50%\"> {{item.name}} </td>\n        <td style=\"width: 10%\"> 100 000 </td>\n        <td > {{item.priority}} </td>\n        <td style=\"width: 5%\">  <clr-icon shape=\"upload\" (click)=\"up($event, item)\"></clr-icon> </td>\n        <td style=\"width: 5%\"> <clr-icon shape=\"download\" (click)=\"down($event, item)\"></clr-icon> </td>\n        <td > <clr-icon shape=\"edit\" (click)=\"down($event, item)\"></clr-icon> </td>\n      </tr>\n      <td style=\"width: 50%\"> ИТОГО </td>\n      <td style=\"width: 10%\"> 100 000 </td>\n      <td > - </td>\n      <td style=\"width: 5%\">  - </td>\n      <td style=\"width: 5%\"> - </td>\n      <td > <clr-icon shape=\"edit\" (click)=\"down($event, item)\"></clr-icon> </td>\n      <tr>\n      </tr>\n      </tbody>\n    </table>\n    </div>\n    </div>\n\n  </div>\n  <app-sidebar class=\"sidenav\"></app-sidebar>\n</div>\n"

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



var MainComponent = /** @class */ (function () {
    function MainComponent(httpService) {
        this.httpService = httpService;
        this.localJson = 'assets/data.json';
        this.wishes = [];
        this.po = [1, 2, 3, 4, 5];
    }
    MainComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.httpService.getData(this.localJson).subscribe(function (data) {
            _this.wishes = data['userList'];
            _this.wishes.sort(function (a, b) { return a.priority - b.priority; });
        });
    };
    MainComponent.prototype.up = function (event, item) {
        item.priority = item.priority + 1;
        /* if (item.priority > (this.findMax()).priority + 1) {
           item.priority = (this.findMax()).priority + 1;
         }*/
        this.wishes.sort(function (a, b) { return a.priority - b.priority; });
    };
    MainComponent.prototype.findMax = function () {
        return this.wishes.reduce(function (a, b) { return a.priority > b.priority ? a : b; });
    };
    MainComponent.prototype.down = function (event, item) {
        item.priority = item.priority - 1;
        if (item.priority < 1) {
            item.priority = 1;
        }
        this.wishes.sort(function (a, b) { return a.priority - b.priority; });
    };
    MainComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-main',
            template: __webpack_require__(/*! ./main.component.html */ "./src/app/ui/layout/main/main.component.html"),
            providers: [_service_http_service__WEBPACK_IMPORTED_MODULE_2__["HttpService"]]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_service_http_service__WEBPACK_IMPORTED_MODULE_2__["HttpService"]])
    ], MainComponent);
    return MainComponent;
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
    };
    SidebarComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-sidebar',
            template: "\n    <nav class=\"sidenav\">\n      <section class=\"sidenav-content\">\n        <a class=\"nav-link active\">Overview</a>\n        <section class=\"nav-group collapsible\">\n          <input id=\"tabexample1\" type=\"checkbox\">\n          <label for=\"tabexample1\">Content</label>\n          <ul class=\"nav-list\">\n            <li><a class=\"nav-link\">Projects</a></li>\n            <li><a class=\"nav-link\">Reports</a></li>\n          </ul>\n        </section>\n        <section class=\"nav-group collapsible\">\n          <input id=\"tabexample2\" type=\"checkbox\">\n          <label for=\"tabexample2\">System</label>\n          <ul class=\"nav-list\">\n            <li><a class=\"nav-link\">Users</a></li>\n            <li><a class=\"nav-link\">Settings</a></li>\n          </ul>\n        </section>\n      </section>\n    </nav>\n  "
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [])
    ], SidebarComponent);
    return SidebarComponent;
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
/* harmony import */ var _layout_layout_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./layout/layout.component */ "./src/app/ui/layout/layout.component.ts");
/* harmony import */ var _layout_header_header_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./layout/header/header.component */ "./src/app/ui/layout/header/header.component.ts");
/* harmony import */ var _layout_sidebar_sidebar_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./layout/sidebar/sidebar.component */ "./src/app/ui/layout/sidebar/sidebar.component.ts");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var clarity_angular__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! clarity-angular */ "./node_modules/clarity-angular/clarity-angular.es5.js");
/* harmony import */ var _layout_main_main_component__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./layout/main/main.component */ "./src/app/ui/layout/main/main.component.ts");









var UiModule = /** @class */ (function () {
    function UiModule() {
    }
    UiModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            declarations: [_layout_layout_component__WEBPACK_IMPORTED_MODULE_3__["LayoutComponent"], _layout_header_header_component__WEBPACK_IMPORTED_MODULE_4__["HeaderComponent"], _layout_sidebar_sidebar_component__WEBPACK_IMPORTED_MODULE_5__["SidebarComponent"], _layout_main_main_component__WEBPACK_IMPORTED_MODULE_8__["MainComponent"]],
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_2__["CommonModule"],
                _angular_common_http__WEBPACK_IMPORTED_MODULE_6__["HttpClientModule"],
                clarity_angular__WEBPACK_IMPORTED_MODULE_7__["ClarityModule"]
            ],
            exports: [_layout_layout_component__WEBPACK_IMPORTED_MODULE_3__["LayoutComponent"]]
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
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
var environment = {
    production: false
};
/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


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

module.exports = __webpack_require__(/*! C:\CODING\Angular\arNote\arnote\src\main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main.js.map