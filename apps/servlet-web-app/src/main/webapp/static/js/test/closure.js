/**
 * Closure
 * 
 * This pattern allows you to create objects with methods that operate on data
 * that isn't visible to the outside world. It should be noted that data hiding
 * is the very basis of object-oriented programming.
 */
function create() {
	var counter = 0;

	return {
		increment : function() {
			counter++;
		},

		print : function() {
			console.log(counter);
		}
	}
}

var c = create();
c.increment();
c.print(); // ==> 1