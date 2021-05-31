/**
 * Object Literals vs. Constructed Objects.
 */
// 1. Object Literals / Static Objects
var staticObject1 = {
	a : 123,
	b : 456
};

var staticObject2 = staticObject1;
staticObject2.b = "hats";
console.log(staticObject1, staticObject2);

// RB: Uncaught TypeError: staticObject1 is not a constructor
// var os = new staticObject1();

// 2. Constructed Objects / Instantiated Objects
var SomeObject = function() {
	var privateMember = "I am a private member";
	var privateMethod = function() {
		console.log(privateMember, this.publicMember);
	};

	this.publicMember = "I am a public member";
	this.publicMethod = function() {
		console.log(privateMember, this.publicMember);
	};
	this.privateMethodWrapper = function() {
		privateMethod.call(this);
		// RB: the value of this within a private method will refer to window
		// so use call(this) to pass in the correct context.
		// privateMethod();
	}
};

// use self to save the this.
var SomeObject2 = function() {
	var self = this;
	var privateMember = "I am a private member";
	var privateMethod = function() {
		console.log(self.publicMember);
	};

	this.publicMember = "I am a public member";
	this.publicMethod = function() {
		console.log(privateMember, this.publicMember);
	};
	this.privateMethodWrapper = function() {
		privateMethod();
	}
};

var o = new SomeObject();
console.log(typeof o.privateMethod, typeof o.publicMethod,
		typeof o.privateMethodWrapper);
o.privateMethodWrapper();