package com.antonromanov.arnote.email.python;

import org.python.core.PyException;
import org.python.core.PyInstance;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class Testp {


	PythonInterpreter interpreter = null;


	public void InterpreterExample()
	{
		PythonInterpreter.initialize(System.getProperties(),
				System.getProperties(), new String[0]);

		this.interpreter = new PythonInterpreter();
	}

	void execfile( final String fileName )
	{
		this.interpreter.execfile(fileName);
	}

	PyInstance createClass(final String className, final String opts )
	{
		return (PyInstance) this.interpreter.eval(className + "(" + opts + ")");
	}

	public void $$doit()
	{


		execfile("hello.py");

		PyInstance hello = createClass("Hello", "None");

		hello.invoke("run");
	}


	public void $doIt() throws PyException {

		// Create an instance of the PythonInterpreter
		PythonInterpreter interp = new PythonInterpreter();

		// The exec() method executes strings of code
//		interp.exec("import sys");
//		interp.exec("import sys\nsys.path.append('C:\\CODING\\GIT\\arNote\\src\\main\\java\\com\\antonromanov\\arnote\\email\\python')\nimport sende");
		interp.exec("import sys\nsys.path.append('C:\\')\nimport hw");
//		interp.exec("from sende import em");
//		interp.exec("import sende.py");
		interp.exec("print sys");
		interp.exec("print sys.path\n" + "print" );
//		interp.exec("import sys");
//		interp.exec("from sende import SomeObject");
//		import yourModule

		// Set variable values within the PythonInterpreter instance
		interp.set("a", new PyInteger(42));
		interp.exec("print a");
		interp.exec("x = 2+2");

		// Obtain the value of an object from the PythonInterpreter and store it
		// into a PyObject.
		PyObject x = interp.get("x");
		System.out.println("x: " + x);
	}
}
